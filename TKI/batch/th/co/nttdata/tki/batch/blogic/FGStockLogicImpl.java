package th.co.nttdata.tki.batch.blogic;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import th.co.nttdata.batch.BatchLogicImpl;
import th.co.nttdata.tki.batch.bean.TFGStock;

import com.ibatis.sqlmap.client.SqlMapClient;

@Service("FGStock")
public class FGStockLogicImpl extends BatchLogicImpl {

	private static final String BATCH_CODE = "FNG_B01";
	private static final String BATCH_NAME = "FG Stock Calculate";

	@Autowired
	@Qualifier("sqlsvrMapClient")
	private SqlMapClient sqlMap;

	@Override
	public void processing() throws Exception {

		log.info("t_fg_stock_batch.mindate is running.");
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("executeDate",
				this.getExecuteDate() != null ? this.getExecuteDate()
						: new Date());
		Date minDate = (Date) sqlMap.queryForObject("t_fg_stock_batch.mindate",
				p);

		log.info("t_fg_stock_batch.maxDate is running.");
		Date maxDate = (Date) sqlMap.queryForObject("t_fg_stock_batch.maxDate");

		log.info("t_fg_stock_batch.delect is running.");
		sqlMap.delete("t_fg_stock_batch.deleteFGStock", minDate);

		log.info("config data for t_fg_stock_batch.queryFGStock");
		TFGStock tfg = null;

		log.info("cal Date");
		Calendar maxCale = Calendar.getInstance();
		maxCale.setTime(maxDate);
		Calendar minCale = Calendar.getInstance();
		minCale.setTime(minDate);

		log.info("t_fg_stock_batch.queryFGStock and t_fg_stock_batch.insertQueryFGStock running.");
		for (; maxCale.compareTo(minCale) != -1; minCale.add(
				Calendar.DAY_OF_YEAR, 1)) {
			tfg = new TFGStock();
			tfg.setReportDate(minCale.getTime());
			sqlMap.insert("t_fg_stock_batch.insertQueryFGStock", tfg);
		}

		// update m_batch_control in case success
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 0);
		param.put("runby", getExecutedBy());
		sqlMap.update("m_batch_control.updateMBatchControl", param);
	}

	@Override
	public void preProcessing() throws Exception {
		// MERGE m_batch_control
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 1);
		param.put("runby", getExecutedBy());
		sqlMap.insert("m_batch_control.insertMBatchControl", param);
	}

	@Override
	public void postException() throws Exception {
		// update m_batch_controlin case fail
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 2);
		param.put("runby", getExecutedBy());
		sqlMap.update("m_batch_control.updateMBatchControl", param);
	}

}
