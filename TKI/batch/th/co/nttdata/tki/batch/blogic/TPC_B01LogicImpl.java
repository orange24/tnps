package th.co.nttdata.tki.batch.blogic;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import th.co.nttdata.batch.BatchLogicImpl;
import th.co.nttdata.tki.batch.bean.TpicsCbom;

import com.ibatis.sqlmap.client.SqlMapClient;

@Service("TPC_B01")
public class TPC_B01LogicImpl extends BatchLogicImpl {
	
	private static final String BATCH_CODE = "TPC_B01";
	private static final String BATCH_NAME = "TPICS - BOM";
	
	@Autowired
	@Qualifier("sqlsvrMapClient")
	private SqlMapClient sqlMap;
	@Autowired
	@Qualifier("sqlsvrMapClientTpics")
	private SqlMapClient sqlMapTpics;
	
	@Override
	public void processing() throws Exception {
		log.info("-----Start TPC_B01 cbom -----");
		// delete tpics_cbom
		sqlMap.delete("tpics_cbom_batch.delete");
		// select CBOM (TPics)
		TpicsCbom cbom = new TpicsCbom();
		cbom.setCbomList(sqlMapTpics.queryForList("cbom_batch.query"));
		// insert tpics_cbom
		for(TpicsCbom bom : cbom.getCbomList()){
			sqlMap.insert("tpics_cbom_batch.insert",bom);
		}
		// update m_batch_control in case success
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 0);
		param.put("runby", getExecutedBy());
		sqlMap.update("m_batch_control.updateMBatchControl",param);
		log.info("-----End TPC_B01 cbom -----");
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
