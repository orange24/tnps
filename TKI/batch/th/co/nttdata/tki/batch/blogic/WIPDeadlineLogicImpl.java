package th.co.nttdata.tki.batch.blogic;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import th.co.nttdata.batch.BatchLogicImpl;
import th.co.nttdata.tki.batch.bean.TWIPDeadline;
import th.co.nttdata.tki.batch.bean.TWIPDeadlinedDate;

import com.ibatis.sqlmap.client.SqlMapClient;

@Service("Deadline")
public class WIPDeadlineLogicImpl extends BatchLogicImpl {
	
	private static final String BATCH_CODE = "WIP_B02";
	private static final String BATCH_NAME = "WIP Deadline Calculate";
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
	private static final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	@Autowired
	@Qualifier("sqlsvrMapClient")
	private SqlMapClient sqlMap;
	
	@Autowired
	protected Properties settings;
	
	@Override
	public void processing() throws Exception {
		//initial
		List<TWIPDeadline> 		wipList 	= null;
		List<TWIPDeadlinedDate> tWIPDeList 	= null;
		TWIPDeadline 			tWIP 		= null;
		TWIPDeadlinedDate 		tWIPDe 		= null;
		Integer					dlNGRatio	= null;
		List<TWIPDeadline> deadlineList 	= new ArrayList<TWIPDeadline>();
		List<TWIPDeadlinedDate> fgQTYList 	= null;// for fg qty 
		List<TWIPDeadlinedDate> wipQTYList 	= null;// copy fgQTYList
		
		//start date
		Date startDate = new Date();		
		startDate = (Date)formatter.parse(dateFormatter.format(startDate));
		// shift/day
		BigDecimal shiftHour = new BigDecimal(settings.getProperty("WIP.deadline.defaultHour", "12"));
		int shiftPerDay = 24/shiftHour.intValue();
		int shiftStep = ((shiftPerDay==2) ? 2:1);
		int shiftLast =	((shiftPerDay==2) ? 3:4);
		// ngRatio
		Integer ngRatio = Integer.parseInt(settings.getProperty("Batch.NGRatio", "10"));
		//manage last deadline
		log.info("t_deadline_batch.deleteDeadlineDate is running.");
		Map<String,Object> p = new HashMap<String,Object>();
		p.put("executeDate",this.getExecuteDate()!=null ? this.getExecuteDate():new Date());
		sqlMap.delete("t_deadline_batch.deleteDeadlineDate",p);
		
		log.info("t_deadline_batch.deleteDeadlineTmp is running.");
		sqlMap.delete("t_deadline_batch.deleteDeadlineTmp");
		
		log.info("t_deadline_batch.copyDeadlineHeader is running.");
		sqlMap.delete("t_deadline_batch.copyDeadlineHeader");
		
		log.info("t_deadline_batch.copyDeadlineDate is running.");
		sqlMap.delete("t_deadline_batch.copyDeadlineDate");
		
		log.info("t_deadline_batch.deleteDeadline is running.");
		sqlMap.delete("t_deadline_batch.deleteDeadline");
// query delivery plan FG
		log.info("t_deadline_batch.queryDeliveryPlan is running.");
		List<TWIPDeadline> dlvList = sqlMap.queryForList("t_deadline_batch.queryDeliveryPlan");
		
		//loop Part
		for(TWIPDeadline dlvp : dlvList){
// query WIP by part
			log.info("t_deadline_batch.queryCapStockWIP is running.");
			wipList = sqlMap.queryForList("t_deadline_batch.queryCapStockWIP", dlvp.getPartId());
			
// make data 3 row for FG,NG and PROD	
			//NG
			tWIP = new TWIPDeadline();
			tWIP.setPartId(dlvp.getPartId());
			tWIP.setWip("+NG");
			tWIP.setWipOrder(250);
			tWIP.setIsWIP(0);
			tWIP.setCapacity(new BigDecimal(ngRatio));
			//set delivery*NGRatio
			tWIPDeList = new ArrayList<TWIPDeadlinedDate>();
			for(TWIPDeadlinedDate dl : dlvp.getWipDeadlinedDateList()){
				tWIPDe = new TWIPDeadlinedDate();
				tWIPDe.setShiftId(1);
				tWIPDe.setReportDate(dl.getReportDate());				
				dlNGRatio =	(new BigDecimal(ngRatio).add(new BigDecimal(100)).multiply(new BigDecimal(dl.getDeadlineQty())).divide(new BigDecimal(100))).setScale(0,BigDecimal.ROUND_UP).intValue();
				tWIPDe.setDeadlineQty(dlNGRatio);
				tWIPDeList.add(tWIPDe);
			}
			tWIP.setWipDeadlinedDateList(tWIPDeList);
			deadlineList.add(tWIP);
			//FG
			tWIP = new TWIPDeadline();
			tWIP.setPartId(dlvp.getPartId());
			tWIP.setWip("FG");
			tWIP.setWipOrder(245);
			tWIP.setIsWIP(0);
			tWIP.setStock(dlvp.getStock());
			int stockQTY = tWIP.getStock();
			fgQTYList 	= new ArrayList<TWIPDeadlinedDate>();	// for fg qty 
			wipQTYList 	= new ArrayList<TWIPDeadlinedDate>();	// copy fgQTYList
			//loop by data have NGRatio for subtraction with FG stock
			for(int i=tWIPDeList.size()-1;i>=0;i--){
				TWIPDeadlinedDate dl = tWIPDeList.get(i);
				tWIPDe = new TWIPDeadlinedDate();
				tWIPDe.setShiftId(1);
				tWIPDe.setWipOrder(245);
				tWIPDe.setReportDate(dl.getReportDate());
 				stockQTY-=dl.getDeadlineQty();
				if(stockQTY >= 0){
					tWIPDe.setDeadlineQty(0);
					tWIPDe.setColorId(3);
				}else{
					tWIPDe.setColorId(1);
					tWIPDe.setDeadlineQty(Math.abs(stockQTY));
					stockQTY = 0;
				}
				fgQTYList.add(tWIPDe);
				TWIPDeadlinedDate tWIPDeCopy = new TWIPDeadlinedDate();
				BeanUtils.copyProperties(tWIPDe, tWIPDeCopy);
				tWIPDeCopy.setShiftId(1);
				wipQTYList.add(tWIPDeCopy);
			}
			tWIP.setWipDeadlinedDateList(fgQTYList);
			deadlineList.add(tWIP);
			//PROD(data from query)
			dlvp.setWip("PROD");
			dlvp.setWipOrder(255);
			dlvp.setIsWIP(0);
			dlvp.setStock(null);
			
			for(TWIPDeadlinedDate tWIPdate : dlvp.getWipDeadlinedDateList()){
				tWIPdate.setShiftId(1);
			}			
			deadlineList.add(dlvp);
			
	// create data day is null.
			List<TWIPDeadlinedDate> dlList = null;
			int countDetail = shiftPerDay*30;
			for(TWIPDeadline twdl : deadlineList){
				dlList = new ArrayList<TWIPDeadlinedDate>();
				if(twdl.getWipDeadlinedDateList().size() != countDetail){
					Calendar stDate 	= new GregorianCalendar(Locale.US);
					stDate.setTime(startDate);
					//day
					for(int n=0;n<30;n++){
						//shift
						for(int s=shiftLast; s>=1; s-=shiftStep){
							tWIPDe = new TWIPDeadlinedDate();
							tWIPDe.setShiftId(s);
							tWIPDe.setReportDate(stDate.getTime());
							dlList.add(tWIPDe);
						}
						
						if(twdl.getWipDeadlinedDateList().size() > 0){
							for(TWIPDeadlinedDate tWIPdl :twdl.getWipDeadlinedDateList()){
								Calendar rpDate 	= new GregorianCalendar(Locale.US);
								rpDate.setTime(tWIPdl.getReportDate());
								if((rpDate.get(Calendar.YEAR)==stDate.get(Calendar.YEAR))&&(rpDate.get(Calendar.DAY_OF_YEAR)==stDate.get(Calendar.DAY_OF_YEAR))){
									dlList.remove(dlList.size()-1);
									dlList.add(tWIPdl);
									break;
								}
							}
						}
						stDate.add(Calendar.DATE, 1);
					}
					twdl.setWipDeadlinedDateList(dlList);					
				}
			}
	//add color for FG
			Integer redInit = null;
			for(int i = 0;i<deadlineList.get(1).getWipDeadlinedDateList().size();i++){
				TWIPDeadlinedDate tfg = deadlineList.get(1).getWipDeadlinedDateList().get(i);
				if(tfg.getColorId() != null){
					if(tfg.getColorId() == 1){
						redInit = i;
						break;
					}
				}
			}
			Integer lastIndexData = null;
			for(int i =deadlineList.get(1).getWipDeadlinedDateList().size()-1;i>=0;i--){
				TWIPDeadlinedDate tfg = deadlineList.get(1).getWipDeadlinedDateList().get(i);
				if(tfg.getColorId() != null){
					if(tfg.getColorId() == 1){
						lastIndexData = i;
						break;
					}
				}
			}
			if(redInit != null){
				int j = redInit+1;
				while(j<=lastIndexData){
					deadlineList.get(1).getWipDeadlinedDateList().get(j).setColorId(1);
					j++;
				}
				for(int a=1;a<=6;a++){
					redInit--;
					if(redInit >= 0){
						deadlineList.get(1).getWipDeadlinedDateList().get(redInit).setColorId(2);
					}else{
						break;
					}
				}
				if(redInit>0){
					while(redInit > 0){
						redInit--;
						deadlineList.get(1).getWipDeadlinedDateList().get(redInit).setColorId(3);
					}
				}
			}else{
				for(int i =deadlineList.get(1).getWipDeadlinedDateList().size()-1;i>=0;i--){
					TWIPDeadlinedDate tfg = deadlineList.get(1).getWipDeadlinedDateList().get(i);
					if(tfg.getColorId() != null){
						if(tfg.getColorId() == 3){
							while(i > 0){
								i--;
								deadlineList.get(1).getWipDeadlinedDateList().get(i).setColorId(3);
							}
							break;
						}
					}
				}
			}
			
//calculate by wip
			int sumStock = 0;
			//loop order by wipOrder DESC
			for(int j = wipList.size()-1; j >= 0; j--){
				TWIPDeadline wip = wipList.get(j);
				List<TWIPDeadlinedDate> wipShiftQTYList 	= new ArrayList<TWIPDeadlinedDate>();
				List<TWIPDeadlinedDate> wipShiftQTYPrevList = new ArrayList<TWIPDeadlinedDate>();
				wip.setCapacity(wip.getCapacity().setScale(0, BigDecimal.ROUND_UP));
				BigDecimal capacity = wip.getCapacity();
				if(capacity.compareTo(new BigDecimal(0)) != 0){
				//check Is last WIP ? for data capacity in calculate(per/hour)
					if (j != wipList.size()-1){
						TWIPDeadline wipNext = wipList.get(j+1);
						if(wipNext.getCapacity().compareTo(new BigDecimal(0))!=0 && capacity.compareTo(wipNext.getCapacity())==1){
							capacity = wipNext.getCapacity().setScale(0, BigDecimal.ROUND_UP);
						}
					}
					//calculate capacity/shift
					capacity = capacity.multiply(shiftHour);
					capacity = capacity.round(new MathContext(0, RoundingMode.UP));
					//loop by WIP each day
					for(int i=wipQTYList.size()-1; i >= 0; i--){
						TWIPDeadlinedDate tWIPDeDatePrev = wipQTYList.get(i);
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(tWIPDeDatePrev.getReportDate());
						// get last shift from wipOrder > wipCurrent
						int shift = tWIPDeDatePrev.getShiftId();
						int shiftRemainQty = 0;
						
						if(tWIPDeDatePrev.getWipOrder() != null &&
								tWIPDeDatePrev.getWipOrder() == 245){
							shift = shiftLast;
							calendar.add(Calendar.DATE, -1);
						}else{
							//case check qty in last shift
							if (wipShiftQTYList.size() > 0) {
								TWIPDeadlinedDate lastWipShift = wipShiftQTYList.get(wipShiftQTYList.size()-1);
								Calendar calendarLastWip = Calendar.getInstance();
								calendarLastWip.setTime(lastWipShift.getReportDate());
								
								if(calendarLastWip.before(calendar) || (calendarLastWip.equals(calendar) && (tWIPDeDatePrev.getShiftId() > lastWipShift.getShiftId()))){
									calendar = calendarLastWip;
								
									shift = lastWipShift.getShiftId();
									if(capacity.compareTo(new BigDecimal(lastWipShift.getDeadlineQty())) < 1){
										if (lastWipShift.getShiftId()==1) {
											shift = shiftLast;
											calendar.add(Calendar.DATE, -1);								
										} else {
											shift = Math.abs(shift - shiftStep);
										}
									}else{
										shiftRemainQty = lastWipShift.getDeadlineQty();
										wipShiftQTYList.remove(wipShiftQTYList.size()-1);
										wipShiftQTYPrevList.remove(wipShiftQTYPrevList.size()-1);
									}
								}else{
									if (tWIPDeDatePrev.getShiftId()==1) {
										shift = shiftLast;
										calendar.add(Calendar.DATE, -1);
									} else {
										shift = shift - shiftStep;
									}
								}
							}else{
								if (tWIPDeDatePrev.getShiftId()==1) {
									shift = shiftLast;
									calendar.add(Calendar.DATE, -1);
								} else {
									shift = shift - shiftStep;
								}
							}
						}
						// fill QTY by shift
						BigDecimal remainQTY = new BigDecimal(tWIPDeDatePrev.getDeadlineQty()+shiftRemainQty);
						while(remainQTY.intValue() > 0){
							TWIPDeadlinedDate tWIPDeShift = new TWIPDeadlinedDate();
							tWIPDeShift.setReportDate(calendar.getTime());
	 						tWIPDeShift.setShiftId(shift);
							if(capacity.compareTo(remainQTY)>=0){
								tWIPDeShift.setDeadlineQty(remainQTY.intValue());
							}else{
								tWIPDeShift.setDeadlineQty(capacity.intValue());
							}
							wipShiftQTYList.add(tWIPDeShift);
							TWIPDeadlinedDate target = new TWIPDeadlinedDate();
							BeanUtils.copyProperties(tWIPDeShift, target);
							wipShiftQTYPrevList.add(target);
							
							// for next shift
							remainQTY = remainQTY.subtract(capacity);
							shift -= shiftStep;
							if(shift <= 0){
								shift = shiftLast;
								calendar.add(Calendar.DATE, -1);
							}
						}
					}
				}
				//add row between data in 30 day
				dlList = new ArrayList<TWIPDeadlinedDate>();
				if(wipShiftQTYList.size() != countDetail){
					Calendar stDate 	= new GregorianCalendar(Locale.US);
					stDate.setTime(startDate);
					for(int n=0;n<30;n++){
						for(int s=1; s<=shiftLast; s+=shiftStep){
							tWIPDe = new TWIPDeadlinedDate();
							tWIPDe.setShiftId(s);
							tWIPDe.setReportDate(stDate.getTime());								
							dlList.add(tWIPDe);
							
							if(wipShiftQTYList.size() > 0){
								for(TWIPDeadlinedDate tWIPdl :wipShiftQTYList){
									Calendar rpDate 	= new GregorianCalendar(Locale.US);
									rpDate.setTime(tWIPdl.getReportDate());
									if((rpDate.get(Calendar.YEAR)==stDate.get(Calendar.YEAR))&&(rpDate.get(Calendar.DAY_OF_YEAR)==stDate.get(Calendar.DAY_OF_YEAR))){
										if(tWIPDe.getShiftId() == tWIPdl.getShiftId()){
											dlList.remove(dlList.size()-1);
											dlList.add(tWIPdl);
											break;
										}
									}
								}
							}
						}
						stDate.add(Calendar.DATE, 1);
					}
					// case last day last shift
					Integer remainPrev = 0;
					Calendar stDate1 	= new GregorianCalendar(Locale.US);
					stDate1.setTime(startDate);
					for(TWIPDeadlinedDate tWIPdl :wipShiftQTYList){
						Calendar rpDate 	= new GregorianCalendar(Locale.US);
						rpDate.setTime(tWIPdl.getReportDate());
						if(null != tWIPdl.getDeadlineQty()){
							if((rpDate.get(Calendar.YEAR) < stDate1.get(Calendar.YEAR)) || ((rpDate.get(Calendar.YEAR) == stDate1.get(Calendar.YEAR)) && (rpDate.get(Calendar.DAY_OF_YEAR) < stDate1.get(Calendar.DAY_OF_YEAR)))){
								remainPrev += tWIPdl.getDeadlineQty();
							}
						}
					}
					if(null != dlList.get(0).getDeadlineQty()){
						remainPrev += dlList.get(0).getDeadlineQty();
					}
					if(remainPrev != 0)
						dlList.get(0).setDeadlineQty(remainPrev);
				}

				//calculate color
				int sumDailly 	= 0;
					sumStock+=wip.getStock();
					int stepColor = ((shiftPerDay == 2) ? 1:2);
					int lastDataQTY = 0;
					boolean isRed = false;
					
					for(int t=dlList.size()-1;t>=0;t--){
						if(dlList.get(t).getDeadlineQty() != null){
							lastDataQTY = t ;
							break;
						}
					}
					
					for(int a=0; a<dlList.size(); a++){
						TWIPDeadlinedDate detail  = dlList.get(a);
						sumDailly += (detail.getDeadlineQty()==null ? 0:detail.getDeadlineQty());
							if((shiftPerDay == 4)&&(a < dlList.size())){
								a++;
								detail  = dlList.get(a);
								sumDailly += (detail.getDeadlineQty()==null ? 0:detail.getDeadlineQty());
							}
						
						if(sumDailly > sumStock && sumDailly != 0){
							if((shiftPerDay == 4) && ((detail.getShiftId()%2) == 0)){
								a--;
								detail  = dlList.get(a);
								
							}
							//1 = Red
							detail.setColorId(1);								
							int d = a+1;
							while(d >= 0 && d <= lastDataQTY){
								dlList.get(d).setColorId(1);
								d++;
							}
							//2 = Yellow
							for(int b=0; b<6; b++){
								a-=stepColor;
								if(a >= 0){
									if(dlList.get(a) != null){
										dlList.get(a).setColorId(2);
									}
								}else{
									break;
								}
							}
							//3 = Green
							for(int c=a; c>=0; c++){
								a-=stepColor;
								if(a >= 0){
									if(dlList.get(a) != null){
										dlList.get(a).setColorId(3);
									}
								}else{
									break;
								}
							}
							isRed = true;
							break;
						}
					}
					if(!isRed){
						if(lastDataQTY != 0 && (capacity.compareTo(new BigDecimal(0))>0)){
							for(int c=0; c<=lastDataQTY; c++){
								dlList.get(c).setColorId(3);
							}
						}else{
							if(dlList.size()>0){
								if(null != dlList.get(0).getDeadlineQty()){
									dlList.get(0).setColorId(3);
								}
							}
						}
					}

				wip.setWipDeadlinedDateList(dlList);
				deadlineList.add(wip);
				// assign data for check filling previous day
				if(wipShiftQTYPrevList.size() > 0){
					wipQTYList = new ArrayList<TWIPDeadlinedDate>();
					for(int t=wipShiftQTYPrevList.size()-1;t>=0;t--){
						wipQTYList.add(wipShiftQTYPrevList.get(t));
					}
				}
			}
//insert deadline
			log.info("t_deadline_batch.insertWIPDeadline is runing.");
			sqlMap.startBatch();
			for(TWIPDeadline deadline : deadlineList){
				sqlMap.insert("t_deadline_batch.insertWIPDeadline",deadline);
			}
		//insert deadline_date
			log.info("t_deadline_batch.insertDeadlineDate is runing.");
			for(TWIPDeadline deadline : deadlineList){
				for(TWIPDeadlinedDate d:deadline.getWipDeadlinedDateList()){
					d.setPartId(deadline.getPartId());
					d.setWip(deadline.getWip());
					d.setWipOrder(deadline.getWipOrder());
					sqlMap.insert("t_deadline_batch.insertDeadlineDate",d);
				}
			}
			sqlMap.executeBatch();
			deadlineList.clear();
		}
		
		//insert last deadline date
		log.info("t_deadline_batch.mergeInsertdaedline is runing.");
		sqlMap.insert("t_deadline_batch.mergeInsertdaedline");
		
		log.info("t_deadline_batch.insertDeadlineDateLast is runing.");
		sqlMap.insert("t_deadline_batch.insertDeadlineDateLast");
		
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
