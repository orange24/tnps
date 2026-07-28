package th.co.nttdata.tki.batch.blogic;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import th.co.nttdata.batch.BatchLogicImpl;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * Batch: Refresh V_07_SalesOrderDetail Cache
 * - Truncate t_sales_order_detail_cache
 * - Re-populate from V_07_SalesOrderDetail
 * - Update statistics
 */
@Service("SalesOrderCacheRefresh")
public class SalesOrderCacheRefreshLogicImpl extends BatchLogicImpl {

	private static final String BATCH_CODE = "DAL_B02";
	private static final String BATCH_NAME = "19. Sales Order Detail Cache Refresh";

	@Autowired
	@Qualifier("sqlsvrMapClient")
	private SqlMapClient sqlMap;

	@Override
	public void processing() throws Exception {
		long startTime = System.currentTimeMillis();

		log.info("[DAL_B02] Starting cache refresh...");

		// [1/3] Truncate cache table
		log.info("[DAL_B02] [1/3] Clearing old cache...");
		sqlMap.update("v_sales_order_cache.truncateCache");
		log.info("[DAL_B02] Cache cleared");

		// [2/3] Re-populate from view
		log.info("[DAL_B02] [2/3] Populating cache from view (this may take several minutes)...");
		int rowCount = sqlMap.update("v_sales_order_cache.populateCache");
		log.info("[DAL_B02] Inserted " + rowCount + " rows");

		// [3/3] Update statistics
		log.info("[DAL_B02] [3/3] Updating statistics...");
		sqlMap.update("v_sales_order_cache.updateStatistics");
		log.info("[DAL_B02] Statistics updated");

		long elapsed = (System.currentTimeMillis() - startTime) / 1000;
		log.info("[DAL_B02] Cache refresh completed successfully in " + elapsed + " seconds");

		// Update batch control to SUCCESS
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 0);
		param.put("runby", getExecutedBy());
		sqlMap.update("m_batch_control.updateMBatchControl", param);
	}

	@Override
	public void preProcessing() throws Exception {
		// MERGE m_batch_control - set status to RUNNING
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 1);
		param.put("runby", getExecutedBy());
		sqlMap.insert("m_batch_control.insertMBatchControl", param);

		log.info("[DAL_B02] Batch started by: " + getExecutedBy());
	}

	@Override
	public void postException() throws Exception {
		// Update batch control to FAILED
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 2);
		param.put("runby", getExecutedBy());
		sqlMap.update("m_batch_control.updateMBatchControl", param);

		log.error("[DAL_B02] Batch failed");
	}
}
