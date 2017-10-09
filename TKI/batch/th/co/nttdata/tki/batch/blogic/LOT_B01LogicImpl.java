package th.co.nttdata.tki.batch.blogic;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import th.co.nttdata.batch.BatchLogicImpl;

import com.ibatis.sqlmap.client.SqlMapClient;

@Service("LOT_B01")
public class LOT_B01LogicImpl extends BatchLogicImpl {
	
	private static final String BATCH_CODE = "LOT_B01";
	private static final String BATCH_NAME = "Lot Sync";
	
	@Autowired
	@Qualifier("sqlsvrMapClient")
	private SqlMapClient sqlMap;
	
	//@Value("#{settings['CMM.linkedServer']}")
	//private String linkedServer = "\\\\172.16.12.200\\pipe\\sql\\query";
	
	@Override
	public void processing() throws Exception {
		log.info("-----Start LOT_B01-----");
			
			// MERGE m_workorder
		Map<String,Object> p = new HashMap<String,Object>();
		p.put("executeDate",this.getExecuteDate()!=null ? this.getExecuteDate():new Date());
		sqlMap.insert("m_workorder_batch.insertMWorkOrder",p);
		
		// update m_batch_control in case success
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 0);
		param.put("runby", getExecutedBy());
		sqlMap.update("m_batch_control.updateMBatchControl",param);
			
		log.info("-----End LOT_B01-----");
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
