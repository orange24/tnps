package th.co.nttdata.tki.controller.mrdc;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S20Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MRDC_S20Controller extends AbstractBaseController{
	
	private static final String PATH_URI = "MRDC/MRDC_S20";
	
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private MRDC_S20Logic mrdc_s20Logic;
	
	@RequestMapping("/MRDC_S20")
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
		.addObject("wip",commonController.getWIPAll())
		.addObject("customerMap", commonController.getCustomerAll());
	}
	
	@RequestMapping("/MRDC_R20_export")
	public ModelAndView export(TDeliveryPlan TDeliveryPlan){
		Calendar cal = new GregorianCalendar(TDeliveryPlan.getYear(), TDeliveryPlan.getMonth()+1, 1);
		cal.add(Calendar.MONTH, -6);
		TDeliveryPlan.setMonthFr(cal.get(Calendar.MONTH));
		TDeliveryPlan.setYearFr(cal.get(Calendar.YEAR));
		
		return new ModelAndView("MRDC_R20ExcelView")
		.addObject("deliveryPlan",mrdc_s20Logic.exportMRDC_R20(TDeliveryPlan));
	}
}
