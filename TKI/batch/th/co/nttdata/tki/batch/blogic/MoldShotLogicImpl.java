package th.co.nttdata.tki.batch.blogic;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import th.co.nttdata.batch.BatchLogicImpl;
import th.co.nttdata.tki.batch.bean.MMoldShot;

import com.ibatis.sqlmap.client.SqlMapClient;

@Service("MoldShot")
public class MoldShotLogicImpl extends BatchLogicImpl {
	
	private static final String BATCH_CODE = "MLD_B01";
	private static final String BATCH_NAME = "Mold Shot Calculate";
	
	@Autowired
	@Qualifier("sqlsvrMapClient")
	private SqlMapClient sqlMap;
	
	@SuppressWarnings("unchecked")
	@Override
	public void processing() throws Exception {
		log.info("-----Total Shot-----");
		
		int DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
		Calendar cal = Calendar.getInstance();//Today
		Date maxDate = cal.getTime();
		
		log.info("|||--select min(reportDate)--|||");
		Map<String,Object> p = new HashMap<String,Object>();
		p.put("executeDate", this.getExecuteDate() != null ? this.getExecuteDate() : new Date());
		List<MMoldShot> mMoldShotDCList = (List<MMoldShot>) sqlMap.queryForList("m_mold_shot_batch.queryMinReportDateDC", p);
		log.info("find list DC : " + mMoldShotDCList.size());
		Date minReportDateFG = (Date) sqlMap.queryForObject("m_mold_shot_batch.queryMinReportDateFG", p);
		log.info("Min Report date FG : " + minReportDateFG);
		
		log.info("|||--start DC--|||");
		if(0 < mMoldShotDCList.size()){
			for(MMoldShot moldShot : mMoldShotDCList){
				log.info("|||--delete t_mold_history DC--|||");
				sqlMap.delete("m_mold_shot_batch.deleteTMoldHistoryDC", moldShot);
			
				log.info("|||--start loop DC--|||");
				Date minDateDC = moldShot.getReportDate();
				while (minDateDC.before(maxDate) || minDateDC.equals(maxDate)) {
					log.info("|||--select list tDailyMC--|||");
					List<MMoldShot> mMoldShotLst = (List<MMoldShot>) sqlMap.queryForList("m_mold_shot_batch.selectDailyMC", moldShot);
					for (MMoldShot mMoldShot : mMoldShotLst) {
						log.info("|||--insert t_mold_history DC--|||");
						sqlMap.insert("m_mold_shot_batch.insertMoldHistoryDC", mMoldShot);
						log.info("|||--update m_mold_detail DC--|||");
						sqlMap.update("m_mold_shot_batch.updateMoldDetailFG");
					}
					minDateDC.setTime(minDateDC.getTime() + DAY_IN_MILLIS);//add value reportDate
				}
			}
			log.info("|||--end loop DC--|||");
		}
		
		log.info("|||--start FG--|||");
		if (null != minReportDateFG) {
				log.info("|||--delete t_mold_history FG--|||");
				sqlMap.delete("m_mold_shot_batch.deleteTMoldHistoryFG", minReportDateFG);
				
				log.info("|||--start loop FG--|||");
				while (minReportDateFG.before(maxDate) || minReportDateFG.equals(maxDate)) {
					log.info("|||--select list tFGDetail--|||");
					sqlMap.insert("m_mold_shot_batch.insertMoldFGHistory", minReportDateFG);
					log.info("|||--update m_mold_detail FG--|||");
					sqlMap.update("m_mold_shot_batch.updateMoldDetailFG");
					minReportDateFG.setTime(minReportDateFG.getTime() + DAY_IN_MILLIS);//add value reportDate
				}
			log.info("|||--end loop FG--|||");
		}
			
		// update m_batch_control in case success
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 0);
		param.put("runby", getExecutedBy());
		sqlMap.update("m_batch_control.updateMBatchControl",param);
		
		log.info("-----End Total Shot-----");
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
