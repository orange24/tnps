package th.co.nttdata.tki.batch.blogic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import th.co.nttdata.batch.BatchLogic;
import th.co.nttdata.batch.BatchLogicImpl;
import th.co.nttdata.tki.batch.bean.TDailySummary;

import com.ibatis.sqlmap.client.SqlMapClient;

@Service("DaillySummary")
public class DailySummaryLogicImpl extends BatchLogicImpl {
	
	private static final String BATCH_CODE = "DAL_B01";
	private static final String BATCH_NAME = "Daily Actual and Pending Summary";
	
	@Autowired
	@Qualifier("sqlsvrMapClient")
	private SqlMapClient sqlMap;

	@Override
	public void processing() throws Exception {
		log.info("t_daily_summary_batch.queryMinDate is running.");
		Map<String,Object> p = new HashMap<String,Object>();
		p.put("executeDate",this.getExecuteDate()!=null ? this.getExecuteDate():new Date());
		String minDate = (String)sqlMap.queryForObject("t_daily_summary_batch.queryMinDate",p);
		log.info("t_daily_summary_batch.queryMinDate is "+minDate);
		
		log.info("t_daily_summary_batch.deleteDailySum is running.");
		sqlMap.delete("t_daily_summary_batch.deleteDailySum",minDate);
		
		TDailySummary daily 			= null;
		TDailySummary dailyDay 			= null;
		TDailySummary dailyNigth 		= null;
		DateFormat df 					= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.US);
		Date reportdate 				= df.parse(minDate);
		
		log.info("cal Date");
		Calendar calendar = Calendar.getInstance();
		Date today = calendar.getTime();
		long countDays = ( today.getTime() - reportdate.getTime() )/ (24 * 60 * 60 * 1000);
		log.info("countDays = "+countDays);
		
		log.info("t_daily_summary_batch.queryDailyDay and queryDailyNight running.");
		while(countDays >=0){
			log.info("t_daily_summary_batch - report date : "+reportdate);
			dailyDay = new TDailySummary();
			dailyDay.setReportDate(reportdate);
			sqlMap.insert("t_daily_summary_batch.insertQueryDailyDay",dailyDay);
			dailyNigth = new TDailySummary();
			dailyNigth.setReportDate(reportdate);
			sqlMap.insert("t_daily_summary_batch.insertQueryDailyNight",dailyNigth);
			calendar.setTime(reportdate);
			calendar.add(Calendar.DATE, 1);
			reportdate = calendar.getTime();
			countDays--;
		}
		
		log.info("Management t_pending");
		reportdate = df.parse(minDate);
		countDays = ( today.getTime() - reportdate.getTime() )/ (24 * 60 * 60 * 1000);
		log.info("t_pending_batch.insertFromDailyMC,insertFromDailyWK and insertFromDailyMCWK running.");
		while(countDays >=0){
			log.info("t_pending_batch - report date : "+reportdate);
			daily = new TDailySummary();
			daily.setReportDate(reportdate);
			sqlMap.insert("t_pending_batch.insertFromDailyMC",daily.getReportDate());
			sqlMap.insert("t_pending_batch.insertFromDailyWK",daily.getReportDate());
			sqlMap.insert("t_pending_batch.insertFromDailyMCWK",daily.getReportDate());
			calendar.setTime(reportdate);
			calendar.add(Calendar.DATE, 1);
			reportdate = calendar.getTime();
			countDays--;
		}
		
		// update m_batch_control in case success
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 0);
		param.put("runby", getExecutedBy());
		sqlMap.update("m_batch_control.updateMBatchControl",param);
	
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
