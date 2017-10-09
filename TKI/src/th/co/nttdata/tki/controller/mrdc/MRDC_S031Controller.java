package th.co.nttdata.tki.controller.mrdc;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S031Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MRDC_S031Controller extends AbstractBaseController{
	
	private static final String PATH_URI = "MRDC/MRDC_S03";
	
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private MRDC_S031Logic mrdc_s031Logic;
	
	@RequestMapping("/MRDC_S03")
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
		.addObject("categoryMap",commonController.getCategory())
		.addObject("customerMap", commonController.getCustomerAll())
		.addObject("MaterialTypeMap", commonController.getMaterialCodeName());
	}
	@RequestMapping(value="/MRDC_S031_search", method=RequestMethod.POST)
	public ModelAndView searchMRDC_S031(TDeliveryPlan TDeliveryPlan){
		TDeliveryPlan = mrdc_s031Logic.searchMRDC_S031(TDeliveryPlan);
		
		return new ModelAndView(PATH_URI)
		.addObject("deliveryPlan",TDeliveryPlan)
		.addObject("monthMap", getMonthMap())
		.addObject("yearMap", getYearMap())
		.addObject("categoryMap",commonController.getCategory())
		.addObject("customerMap", commonController.getCustomerAll())
		.addObject("MaterialTypeMap", commonController.getMaterialCodeName());
	}
}
