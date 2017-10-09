package th.co.nttdata.tki.batch.blogic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import th.co.nttdata.batch.BatchLogicImpl;
import th.co.nttdata.tki.batch.bean.MLeadtime;

import com.ibatis.sqlmap.client.SqlMapClient;

@Service("Leadtime")
public class LeadtimeLogicImpl extends BatchLogicImpl {
	
	private static final String BATCH_CODE = "LDT_B01";
	private static final String BATCH_NAME = "Leadtime Calculate";
	
	@Autowired
	@Qualifier("sqlsvrMapClient")
	private SqlMapClient sqlMap;

	@Override
	public void processing() throws Exception {
			log.info("Initial");
			MLeadtime 	leadtime 	= new MLeadtime ();
			log.info("m_leadtime_batch.queryLeadtime is running.");
			leadtime.setLeadtimeList((List<MLeadtime>)sqlMap.queryForList("m_leadtime_batch.queryLeadtime"));
			for(MLeadtime lt : leadtime.getLeadtimeList()){
				if(lt.getStFromDate4() != null && lt.getStToDate4() != null){
					lt.setStFromDate(lt.getStFromDate4());
					lt.setStToDate(lt.getStToDate4());
					lt.setStResult4(this.calAvgResult(lt));
				}
				if(lt.getStFromDate5() != null && lt.getStToDate5() != null){
					lt.setStFromDate(lt.getStFromDate5());
					lt.setStToDate(lt.getStToDate5());
					lt.setStResult5(this.calAvgResult(lt));
				}
				if(lt.getStFromDate6() != null && lt.getStToDate6() != null){
					lt.setStFromDate(lt.getStFromDate6());
					lt.setStToDate(lt.getStToDate6());
					lt.setStResult6(this.calAvgResult(lt));
				}
			}
			log.info("m_leadtime_batch.updateLeadtime is running.");
			sqlMap.startBatch();
			for(MLeadtime mLeadtime : leadtime.getLeadtimeList()){
				sqlMap.update("m_leadtime_batch.updateLeadtime",mLeadtime);
			}
			sqlMap.executeBatch();
			
			// update m_batch_control in case success
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("batchcode", BATCH_CODE);
			param.put("batchname", BATCH_NAME);
			param.put("batchstatus", 0);
			param.put("runby", getExecutedBy());
			sqlMap.update("m_batch_control.updateMBatchControl",param);
			
	}
	public BigDecimal calAvgResult(MLeadtime lt) throws Exception {
		log.info("Initial calculate average stResult");
		List<BigDecimal> avg	= null;
		BigDecimal 	avgSum		= new BigDecimal(0);
		
		log.info("m_leadtime_batch.queryAvg(machine and worker) is running.");
		avg = (List<BigDecimal>)sqlMap.queryForList("m_leadtime_batch.queryAvg",lt);
		
		log.info("m_leadtime_batch.queryDailly(DC) is running.");
		Integer qtyAll = 0;
		qtyAll = (Integer)sqlMap.queryForObject("m_leadtime_batch.queryDailly",lt);
		
		log.info("m_leadtime_batch.queryDaillySize(DC) is running.");
		Integer size = 0;
		size = (Integer)sqlMap.queryForObject("m_leadtime_batch.queryDaillySize",lt);
		
		log.info("For loop m_leadtime_batch.queryStopMC(DC) is running.");
		Integer stopMinute = 0;
		stopMinute = (Integer)sqlMap.queryForObject("m_leadtime_batch.queryStopMC",lt);// time stop machine
		
		BigDecimal time = new BigDecimal(0);
		time = new BigDecimal((size*12*60*60)-stopMinute);//time actual
		
		if(qtyAll == 0){
			avg.add(new BigDecimal(0));
		}else{
			avg.add(time.divide(new BigDecimal(qtyAll),5));// time / qty
		}
		
		for(int a=0,max=avg.size();a<max;a++){
			if(avg.get(a).compareTo(new BigDecimal(0))==0)
				avg.remove(a);
		}
		for(BigDecimal av : avg){
			avgSum = avgSum.add(av);
		}
		if((avgSum.compareTo(new BigDecimal(0))==1)&&(avg.size()>0)){
			return avgSum.divide(new BigDecimal(avg.size()),2,RoundingMode.HALF_UP);
		}else{
			return new BigDecimal(0);
		}
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
