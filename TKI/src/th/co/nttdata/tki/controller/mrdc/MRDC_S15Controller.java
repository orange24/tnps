package th.co.nttdata.tki.controller.mrdc;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S15Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MRDC_S15Controller extends AbstractBaseController{
	
	private static final String PATH_URI = "MRDC/MRDC_S15";
	
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private MRDC_S15Logic mrdc_s15Logic;
	
	@RequestMapping("/MRDC_S15")
	public ModelAndView init(){
		
		return new ModelAndView(PATH_URI)
		.addObject("deliveryPlan",new TDeliveryPlan())
		.addObject("customerMap", commonController.getCustomerAll());
	}
	
	@RequestMapping("/MRDC_R15_export")
	public ModelAndView export(TDeliveryPlan TDeliveryPlan){
		
		return new ModelAndView("MRDC_R15ExcelView")
		.addObject("deliveryPlan",mrdc_s15Logic.exportMRDC_R15(TDeliveryPlan));
	}
	
	@RequestMapping(value="/MRDC_R15_export_count", method=RequestMethod.GET)
	public @ResponseBody Map<String,Integer> count( TDeliveryPlan TDeliveryPlan ) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		Integer count = mrdc_s15Logic.countMRDC_R15();
		TDeliveryPlan t = new TDeliveryPlan();
		t  = mrdc_s15Logic.exportMRDC_R15(TDeliveryPlan);
		Integer size = t.getDateList().size();// Data of query export MRDC_R15
		
		Map<String,Integer> map = new LinkedHashMap<String,Integer>();
		map.put("count", count == null ? 0 : count);
		map.put("maxRecord",maxRecord);
		map.put("size",size);
		
		return map;
	}
}
