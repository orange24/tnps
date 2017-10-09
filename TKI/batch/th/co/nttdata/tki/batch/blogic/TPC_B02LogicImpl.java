package th.co.nttdata.tki.batch.blogic;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import th.co.nttdata.batch.BatchLogic;
import th.co.nttdata.batch.BatchLogicImpl;
import th.co.nttdata.tki.batch.bean.TpicsCustFgPart;

import com.ibatis.sqlmap.client.SqlMapClient;

@Service("TPC_B02")
public class TPC_B02LogicImpl extends BatchLogicImpl {
	
	private static final String BATCH_CODE = "TPC_B02";
	private static final String BATCH_NAME = "TPICS - Customer/ FG/ Part Relation";
	
	@Autowired
	@Qualifier("sqlsvrMapClient")
	private SqlMapClient sqlMap;
	@Autowired
	@Qualifier("sqlsvrMapClientTpics")
	private SqlMapClient sqlMapTpics;
	
	@Override
	public void processing() throws Exception {
		log.info("-----Start TPC_B02 cust_fg_part-----");
		// delete tpics_cust_fg_part
		sqlMap.delete("tpics_cust_fg_part_batch.delete");
		// select v_tnps_cust_fg_part (TPics)
		TpicsCustFgPart cfp = new TpicsCustFgPart();
		cfp.setCustFgPartList(sqlMapTpics.queryForList("cust_fg_part_batch.query"));
		// insert tpics_cust_fg_part
		for(TpicsCustFgPart c : cfp.getCustFgPartList()){
			sqlMap.insert("tpics_cust_fg_part_batch.insert",c);
		}
		// update m_batch_control in case success
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 0);
		param.put("runby", getExecutedBy());
		sqlMap.update("m_batch_control.updateMBatchControl",param);
		log.info("-----End TPC_B02 cust_fg_part -----");
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
