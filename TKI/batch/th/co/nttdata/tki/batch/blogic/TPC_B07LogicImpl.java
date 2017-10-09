package th.co.nttdata.tki.batch.blogic;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import th.co.nttdata.batch.BatchLogicImpl;
import th.co.nttdata.tki.batch.bean.XITEM;

import com.ibatis.sqlmap.client.SqlMapClient;

@Service("TPC_B07")
public class TPC_B07LogicImpl extends BatchLogicImpl {
	
	private static final String BATCH_CODE = "TPC_B07";
	private static final String BATCH_NAME = "TPICS - Product (FG)";
	
	@Autowired
	@Qualifier("sqlsvrMapClient")
	private SqlMapClient sqlMap;
	@Autowired
	@Qualifier("sqlsvrMapClientTpics")
	private SqlMapClient sqlMapTpics;
	
	@Override
	public void processing() throws Exception {
		log.info("-----Start TPC_B07 XITEM-----");
		// delete tpics_workorder
		sqlMap.delete("tpics_XITEM_batch.delete");
		// select v_tnps_cust_fg_part (TPics)
		XITEM w = new XITEM();
		w.setXitemList(sqlMapTpics.queryForList("XITEM_batch.query"));
		// insert tpics_cust_fg_part
		for(XITEM c : w.getXitemList()){
			sqlMap.insert("tpics_XITEM_batch.insert",c);
		}
		// update m_batch_control in case success
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 0);
		param.put("runby", getExecutedBy());
		sqlMap.update("m_batch_control.updateMBatchControl",param);
		log.info("-----End TPC_B07 XITEM -----");
	}
	
	@Override
	public void preProcessing() throws Exception {
		// MERGE m_batch_control
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 1);
		param.put("runby", getExecutedBy());
		sqlMap.insert("m_batch_control.insertMBatchControl",param);
	}
	
	@Override
	public void postException() throws Exception {
		// update m_batch_controlin case fail
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 2);
		param.put("runby", getExecutedBy());
		sqlMap.update("m_batch_control.updateMBatchControl",param);
	}
}
