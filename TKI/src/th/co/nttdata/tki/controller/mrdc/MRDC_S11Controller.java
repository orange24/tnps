package th.co.nttdata.tki.controller.mrdc;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S11Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MRDC_S11Controller extends AbstractBaseController{
	
	private static final String PATH_URI = "MRDC/MRDC_S11";
	
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private MRDC_S11Logic mrdc_s11Logic;
	
	@RequestMapping("/MRDC_S11")
	public ModelAndView init(){
		// <!-- Initial: -->
		Calendar cal = new GregorianCalendar(Locale.US);

		TDeliveryPlan TDeliveryPlan = new TDeliveryPlan();
		TDeliveryPlan.setMonthFr(cal.get(Calendar.MONTH));
		TDeliveryPlan.setMonthTo(cal.get(Calendar.MONTH));
		TDeliveryPlan.setYearFr(cal.get(Calendar.YEAR));
		TDeliveryPlan.setYearTo(cal.get(Calendar.YEAR));
		
		return new ModelAndView(PATH_URI)
		.addObject("deliveryPlan",TDeliveryPlan)
		.addObject("monthMap", getMonthMap())
		.addObject("yearMap", getYearMap())
		.addObject("categoryMap", commonController.getCategory())
		.addObject("customerMap", commonController.getCustomerAll());
	}
	
	@RequestMapping("/MRDC_R11_export")
	public ModelAndView export(TDeliveryPlan TDeliveryPlan){
		
		return new ModelAndView("MRDC_R11ExcelView")
		.addObject("deliveryPlan",mrdc_s11Logic.exportMRDC_R11(TDeliveryPlan));
	}
}
