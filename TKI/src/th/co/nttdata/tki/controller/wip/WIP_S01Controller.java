package th.co.nttdata.tki.controller.wip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.lf5.util.DateFormatManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.TWIPDeadline;
import th.co.nttdata.tki.bean.TWIPDeadlineDate;
import th.co.nttdata.tki.blogic.wip.WIP_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class WIP_S01Controller extends AbstractBaseController {

	private static final String PATH_URI = "WIP/WIP_S01";
	@Autowired
	private CommonController commonController;
	@Autowired
	private WIP_S01Logic wipS01Logic;
		
	@RequestMapping("/WIP_S01")
	public ModelAndView init() {
		TWIPDeadline TWIPDeadline = new TWIPDeadline();
		
		return new ModelAndView(PATH_URI)
		.addObject("deadline", TWIPDeadline)
		.addObject("wipMap",commonController.getWIPAll())
		.addObject("customerMap", commonController.getCustomerAll());
	}
	
	@RequestMapping(value="/WIP_S01_search")
	public ModelAndView search( TWIPDeadline TWIPDeadline ) {
		TWIPDeadline = wipS01Logic.search( TWIPDeadline );
		String hour = settings.getProperty("WIP.deadline.defaultHour", "12");

		// <!-- Providing the month name. -->
		Integer count = 0, prevMonth = -1, currMonth = -1,colspan = 0, day = 0, year = 0, runMonth = -1;
		HashMap<Integer, Integer>chkMonthPut = new HashMap<Integer, Integer>();
		
		List<Map<String,Integer>> columnSpan = new LinkedList<Map<String,Integer>>();
		if(TWIPDeadline.getDeadlinePartList() != null){
			
			Date maxDate = null;
			int maxMonth = 1;
			if(TWIPDeadline.getDeadlineDateList()!=null){
				if(TWIPDeadline.getDeadlineDateList().size()>0){
					maxDate = TWIPDeadline.getDeadlineDateList().get(0).getReportDate();
					Calendar calTmp = Calendar.getInstance();
					calTmp.setTime(maxDate);
					maxMonth = calTmp.get(Calendar.MONTH)+1;
				}
			}
			
			
			List<TWIPDeadline> partListArr = new ArrayList<TWIPDeadline>();
			for(TWIPDeadline partList : TWIPDeadline.getDeadlinePartList()){
				
				List<TWIPDeadline> wipListArr = new ArrayList<TWIPDeadline>();
				for(TWIPDeadline wipList : partList.getDeadlineWIPList()){
					
					Iterator<TWIPDeadlineDate> iter = wipList.getDeadlineDateList().listIterator();
					List<TWIPDeadlineDate> newDeadlineResult = new ArrayList<TWIPDeadlineDate>();
					int runDate = 1;
					boolean chkEnd = true;
					
					while( iter.hasNext() ) {
						TWIPDeadlineDate deadlineItem = iter.next();
						
						Calendar cal = Calendar.getInstance();
						Calendar cal2 = Calendar.getInstance();
						cal.setTime(deadlineItem.getReportDate());
						currMonth = cal.get(Calendar.MONTH);
						day = cal.get(Calendar.DAY_OF_MONTH);
						year = cal.get(Calendar.YEAR);
						
						//Set first date
						if(runDate==1){
							prevMonth = currMonth;
						}
							
						// Check  rundate < currentDay then fill zero to margin day.
						while(runDate<day){
							TWIPDeadlineDate deadlineTmp = new TWIPDeadlineDate();
							TWIPDeadlineDate deadlineTmp2 = new TWIPDeadlineDate();
							
							SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
							String reportDayStr = (runDate<10)?"0"+runDate:""+runDate; 
							String reportMonthStr = ((currMonth+1)<10)?"0"+(currMonth+1):""+(currMonth+1);
							String dateInString = reportDayStr+"-"+reportMonthStr+"-"+year;
							
							Date date = null;
							try {
								date = sdf.parse(dateInString);
								cal2.setTime(date);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							
							deadlineTmp.setReportDate(cal2.getTime());
							deadlineTmp2.setReportDate(cal2.getTime());
							newDeadlineResult.add(deadlineTmp);
							newDeadlineResult.add(deadlineTmp2);
							
//							System.out.println(deadlineTmp.getReportDate());
							
							cal2.add(Calendar.DAY_OF_MONTH, 1);
							++runDate;
							++count;
						}
						
						if(runDate>=day){
							newDeadlineResult.add(deadlineItem);
//							System.out.println(deadlineItem.getReportDate());
						}
								
						// count++ start at if( count++ != 0 && prevMonth != currMonth ) 
						// count != 0 because start index 1
						// iter.hasNext() check index last
						if( count != 0 && (prevMonth.intValue() != currMonth.intValue() || !iter.hasNext()) ) { // isNewMonth

							SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
							String reportDayStr = (runDate<10)?"0"+runDate:""+runDate; 
							String reportMonthStr = ((currMonth+1)<10)?"0"+(currMonth+1):""+(currMonth+1);
							String dateInString = reportDayStr+"-"+reportMonthStr+"-"+year;
							
							Date date = null;
							try {
								date = sdf.parse(dateInString);
								cal2.setTime(date);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							
							if(runDate==day){
								cal2.add(Calendar.DAY_OF_MONTH, 1);
								runDate++;
							}
							
							while(cal2.get(Calendar.MONTH)==currMonth.intValue() ){
								TWIPDeadlineDate deadlineTmp = new TWIPDeadlineDate();
								TWIPDeadlineDate deadlineTmp2 = new TWIPDeadlineDate();
								deadlineTmp.setReportDate(cal2.getTime());
								deadlineTmp2.setReportDate(cal2.getTime());
								
//								System.out.println(deadlineTmp.getReportDate());
								
								newDeadlineResult.add(deadlineTmp);
								newDeadlineResult.add(deadlineTmp2);
								
								cal2.add(Calendar.DAY_OF_MONTH, 1);
								++runDate;
								++count;
							}
							
							if(!iter.hasNext()){
								while(cal2.get(Calendar.MONTH)<maxMonth){
									TWIPDeadlineDate deadlineTmp = new TWIPDeadlineDate();
									TWIPDeadlineDate deadlineTmp2 = new TWIPDeadlineDate();
									deadlineTmp.setReportDate(cal2.getTime());
									deadlineTmp2.setReportDate(cal2.getTime());
									
//									System.out.println(deadlineTmp.getReportDate());
									
									newDeadlineResult.add(deadlineTmp);
									newDeadlineResult.add(deadlineTmp2);
									
									cal2.add(Calendar.DAY_OF_MONTH, 1);
								}
								
								currMonth++;
							}

							if(!chkMonthPut.containsKey(currMonth)){
								runMonth = currMonth;
								chkMonthPut.put(currMonth, currMonth);
								colspan +=count;
								Map<String,Integer> properties = new HashMap<String,Integer>();
								properties.put("colspanPerMonth", count*2);
								properties.put("month", prevMonth);
								properties.put("year", cal.get(Calendar.YEAR));
								
								columnSpan.add(properties);
							}
							
							count = 0;
							runDate = 1;
							chkEnd = false;
					
						}
						
						if(runDate>day){
							continue;
						}
						
						prevMonth = currMonth;
						if(chkEnd){
							++count;
							++runDate;							
						}
						chkEnd = true;
					}
					wipList.setDeadlineDateList(newDeadlineResult);
					wipListArr.add(wipList);
//					break;
				}
				partList.setDeadlineWIPList(wipListArr);
				partListArr.add(partList);
//				break;
			}
			
			TWIPDeadline.setDeadlinePartList(partListArr);
		}
		
		return new ModelAndView(PATH_URI)
		.addObject("customerMap", commonController.getCustomerAll())
		.addObject("colspan", colspan)
		.addObject("deadline", TWIPDeadline)
		.addObject("deadlineMonthMap", columnSpan)
		.addObject("hour", hour)
		.addObject("monthMap", getMonthMap())
		.addObject("wipMap",commonController.getWIPAll());
	}
	
	@RequestMapping(value="/WIP_S01_export", method=RequestMethod.POST)
	public ModelAndView export( TWIPDeadline TWIPDeadline ) {
		TWIPDeadline = wipS01Logic.search( TWIPDeadline );
		String hour = settings.getProperty("WIP.deadline.defaultHour", "12");

		// <!-- Providing the month name. -->
		Integer count = 0, prevMonth = -1, currMonth = -1;
		List<Map<String,Integer>> columnSpan = new LinkedList<Map<String,Integer>>();
		if(TWIPDeadline.getDeadlinePartList() != null){
			for(TWIPDeadline partList : TWIPDeadline.getDeadlinePartList()){
				for(TWIPDeadline wipList : partList.getDeadlineWIPList()){
					Iterator<TWIPDeadlineDate> iter = wipList.getDeadlineDateList().listIterator();
					while( iter.hasNext() ) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(iter.next().getReportDate());
						currMonth = cal.get(Calendar.MONTH);
								
						// count++ start at if( count++ != 0 && prevMonth != currMonth )
						// count != 0 because start index 1
						// iter.hasNext() check index last
						if( count != 0 && (prevMonth.intValue() != currMonth.intValue() || !iter.hasNext()) ) { // isNewMonth
							if(!iter.hasNext()){
								count++;
							}
							Map<String,Integer> properties = new HashMap<String,Integer>();
							properties.put("colspanPerMonth", count);
							properties.put("month", prevMonth);
							properties.put("year", cal.get(Calendar.YEAR));
							count = 0;
							
							columnSpan.add(properties);
						}
						prevMonth = currMonth;
						count++;
					}
					break;
				}
				break;
			}
		}
		
		return new ModelAndView("WIP_R01ExcelView")
		.addObject("deadline", wipS01Logic.exportWIP_R01(TWIPDeadline))
		.addObject("deadlineMonthMap", columnSpan)
		.addObject("hour", hour)
		.addObject("monthMap", getMonthMap());
	}

}