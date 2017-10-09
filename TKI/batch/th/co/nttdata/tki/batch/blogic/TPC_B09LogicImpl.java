package th.co.nttdata.tki.batch.blogic;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import th.co.nttdata.batch.BatchLogicImpl;

import com.ibatis.sqlmap.client.SqlMapClient;

@Service("TPC_B09")
public class TPC_B09LogicImpl extends BatchLogicImpl {
	
	private static final String BATCH_CODE = "TPC_B09";
	private static final String BATCH_NAME = "09. TPICS - Delete ClotSeqNo ";
	
	@Autowired
	@Qualifier("sqlsvrMapClient")
	private SqlMapClient sqlMap;
	
	@Override
	public void processing() throws Exception {
		log.info("-----Start 09. TPICS - Delete ClotSeqNo-----");
		
		// delete tpics_clotseqno
		sqlMap.delete("tpics_clotseqno_batch.truncate");
		
		// update m_batch_control in case success
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 0);
		param.put("runby", getExecutedBy());
		sqlMap.update("m_batch_control.updateMBatchControl",param);
		log.info("-----End 08. TPICS - Lot Qty -----");
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
