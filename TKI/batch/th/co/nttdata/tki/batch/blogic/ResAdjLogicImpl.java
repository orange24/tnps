package th.co.nttdata.tki.batch.blogic;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;

import th.co.nttdata.batch.BatchLogicImpl;

@Service("RES_ADJ")
public class ResAdjLogicImpl extends BatchLogicImpl {
	
	private static final String BATCH_CODE = "RES_ADJ";
	private static final String BATCH_NAME = "Reset zero for FG/Wip adjuestment";
	
	@Autowired
	@Qualifier("sqlsvrMapClient")
	private SqlMapClient sqlMap;
	
	@SuppressWarnings("unchecked")
	@Override
	public void processing() throws Exception {
		log.info("-----Starting reset zero-----");
		
		Map<String,Object> p = new HashMap<String,Object>();
		p.put("resetdate", this.getExecuteDate() != null ? this.getExecuteDate() : new Date());
		sqlMap.update("m_batch_control.resetZeroWip", p);
		sqlMap.update("m_batch_control.resetZeroFG", p);
		
		// update m_batch_control in case success
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 0);
		param.put("runby", getExecutedBy());
		sqlMap.update("m_batch_control.updateMBatchControl",param);
		
		log.info("-----End reset zero-----");
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
