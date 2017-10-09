package th.co.nttdata.tki.batch.blogic;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import th.co.nttdata.batch.BatchLogicImpl;
import th.co.nttdata.tki.batch.bean.TpicsWorkorder;

import com.ibatis.sqlmap.client.SqlMapClient;

@Service("TPC_B03")
public class TPC_B03LogicImpl extends BatchLogicImpl {
	
	private static final String BATCH_CODE = "TPC_B03";
	private static final String BATCH_NAME = "TPICS - Workorder";
	
	@Autowired
	@Qualifier("sqlsvrMapClient")
	private SqlMapClient sqlMap;
	@Autowired
	@Qualifier("sqlsvrMapClientTpics")
	private SqlMapClient sqlMapTpics;
	
	@Override
	public void processing() throws Exception {
		log.info("-----Start TPC_B03 workorder-----");
		// delete tpics_workorder
		sqlMap.delete("tpics_workorder_batch.delete");
		// select v_tnps_cust_fg_part (TPics)
		TpicsWorkorder w = new TpicsWorkorder();
		
		Map<String,Object> p = new HashMap<String,Object>();
		p.put("executeDate",this.getExecuteDate()!=null ? this.getExecuteDate():new Date());
		w.setWorkorderList(sqlMapTpics.queryForList("workorder_batch.query",p));
		// insert tpics_cust_fg_part
		for(TpicsWorkorder c : w.getWorkorderList()){
			sqlMap.insert("tpics_workorder_batch.insert",c);
		}
		// update m_batch_control in case success
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 0);
		param.put("runby", getExecutedBy());
		sqlMap.update("m_batch_control.updateMBatchControl",param);
		log.info("-----End TPC_B03 workorder -----");
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
