package th.co.nttdata.tki.batch.blogic;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import th.co.nttdata.batch.BatchLogicImpl;
import th.co.nttdata.tki.batch.bean.TpicsWorkorder;
import th.co.nttdata.tki.batch.bean.XBAIK;

import com.ibatis.sqlmap.client.SqlMapClient;

@Service("TPC_B06")
public class TPC_B06LogicImpl extends BatchLogicImpl {
	
	private static final String BATCH_CODE = "TPC_B06";
	private static final String BATCH_NAME = "TPICS - Price";
	
	@Autowired
	@Qualifier("sqlsvrMapClient")
	private SqlMapClient sqlMap;
	@Autowired
	@Qualifier("sqlsvrMapClientTpics")
	private SqlMapClient sqlMapTpics;
	
	@Override
	public void processing() throws Exception {
		log.info("-----Start TPC_B06 XBAIK-----");
		// delete tpics_workorder
		sqlMap.delete("tpics_XBAIK_batch.delete");
		// select v_tnps_cust_fg_part (TPics)
		XBAIK w = new XBAIK();
		w.setXbaikList(sqlMapTpics.queryForList("XBAIK_batch.query"));
		// insert tpics_cust_fg_part
		for(XBAIK c : w.getXbaikList()){
			sqlMap.insert("tpics_XBAIK_batch.insert",c);
		}
		// update m_batch_control in case success
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 0);
		param.put("runby", getExecutedBy());
		sqlMap.update("m_batch_control.updateMBatchControl",param);
		log.info("-----End TPC_B06 XBAIK -----");
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
