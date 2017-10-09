package th.co.nttdata.tki.controller.mrdc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S18Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MRDC_S18Controller extends AbstractBaseController{
	
	private static final String PATH_URI = "MRDC/MRDC_S18";
	
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private MRDC_S18Logic mrdc_s18Logic;
	
	@RequestMapping("/MRDC_S18")
	public ModelAndView init(){
		// <!-- Initial: -->
		Calendar cal = new GregorianCalendar(Locale.US);

		TDeliveryPlan TDeliveryPlan = new TDeliveryPlan();
		TDeliveryPlan.setMonth(cal.get(Calendar.MONTH));
		TDeliveryPlan.setYear(cal.get(Calendar.YEAR));
		
		return new ModelAndView(PATH_URI)
		.addObject("deliveryPlan",TDeliveryPlan)
		.addObject("monthMap", getMonthMap())
		.addObject("yearMap", getYearMap())
		.addObject("partMap", commonController.getPartNameNo(null, null, null))
		.addObject("customerMap", commonController.getCustomerSel());
	}
	
	@RequestMapping("/MRDC_R18_export")
	public ModelAndView export(TDeliveryPlan TDeliveryPlan){
		Calendar cal = new GregorianCalendar(TDeliveryPlan.getYear(),TDeliveryPlan.getMonth(),1);
		SimpleDateFormat yyyyMM = new SimpleDateFormat("yyyyMM");
		cal.add(Calendar.MONTH, -1);
		TDeliveryPlan.setYearmonthFr(yyyyMM.format(new Date(cal.getTimeInMillis())));
		cal.add(Calendar.MONTH, 2);
		TDeliveryPlan.setYearmonthTo(yyyyMM.format(new Date(cal.getTimeInMillis())));
		
		cal.add(Calendar.DAY_OF_YEAR, -1);
		TDeliveryPlan.setDayOfMonth(cal.get(Calendar.DAY_OF_MONTH));

		return new ModelAndView("MRDC_R18ExcelView")
		.addObject("deliveryPlanSummary",mrdc_s18Logic.exportMRDC_R18Summary(TDeliveryPlan))
		.addObject("deliveryPlanProcessList2_Order",mrdc_s18Logic.exportMRDC_R18ProcessList2_Order(TDeliveryPlan))
		.addObject("deliveryPlanProcessList2_Sales",mrdc_s18Logic.exportMRDC_R18ProcessList2_Sales(TDeliveryPlan))
		.addObject("wipOfpart",mrdc_s18Logic.queryWipOfPart(TDeliveryPlan))
		.addObject("deliveryPlanProcessList3",mrdc_s18Logic.exportMRDC_R18ProcessList3(TDeliveryPlan))
		.addObject("deliveryPlanProcessList4",mrdc_s18Logic.exportMRDC_R18ProcessList4(TDeliveryPlan))
		.addObject("deliveryPlanProcessList5",mrdc_s18Logic.exportMRDC_R18ProcessList5(TDeliveryPlan));
	}
	
}
