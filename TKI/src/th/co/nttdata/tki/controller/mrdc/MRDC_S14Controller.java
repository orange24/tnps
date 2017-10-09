package th.co.nttdata.tki.controller.mrdc;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S14Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MRDC_S14Controller extends AbstractBaseController{
	
	private static final String PATH_URI = "MRDC/MRDC_S14";
	
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private MRDC_S14Logic mrdc_s14Logic;
	
	@RequestMapping("/MRDC_S14")
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
		.addObject("customerMap", commonController.getCustomerAll());
	}
	
	@RequestMapping("/MRDC_R14_export")
	public ModelAndView export(TDeliveryPlan TDeliveryPlan){
		
		return new ModelAndView("MRDC_R14ExcelView")
		.addObject("deliveryPlan",mrdc_s14Logic.exportMRDC_R14(TDeliveryPlan));
	}
	
	@RequestMapping(value="/MRDC_R14_export_count", method=RequestMethod.GET)
	public @ResponseBody Map<String,Integer> count( TDeliveryPlan TDeliveryPlan ) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		Integer count = mrdc_s14Logic.countMRDC_R14();
		TDeliveryPlan t = new TDeliveryPlan();
		t  = mrdc_s14Logic.exportMRDC_R14(TDeliveryPlan);
		Integer size = t.getPlanList().size();// Data of query export MRDC_R02
		
		Map<String,Integer> map = new LinkedHashMap<String,Integer>();
		map.put("count", count == null ? 0 : count);
		map.put("maxRecord",maxRecord);
		map.put("size",size);
		
		return map;
	}
}
