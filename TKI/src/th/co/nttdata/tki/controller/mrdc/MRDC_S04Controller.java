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
import th.co.nttdata.tki.blogic.mrdc.MRDC_S04Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MRDC_S04Controller extends AbstractBaseController{
	
	private static final String PATH_URI = "MRDC/MRDC_S04";
	
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private MRDC_S04Logic mrdc_s04Logic;
	
	@RequestMapping("/MRDC_S04")
	public ModelAndView init(){
		
		return new ModelAndView(PATH_URI)
		.addObject("deliveryPlan",new TDeliveryPlan())
		.addObject("categoryMap",commonController.getCategory())
		.addObject("customerMap", commonController.getCustomerAll());
	}
	
	@RequestMapping("/MRDC_R04_export")
	public ModelAndView export(TDeliveryPlan TDeliveryPlan){
		
		return new ModelAndView("MRDC_R04ExcelView")
		.addObject("productMaster",mrdc_s04Logic.exportMRDC_R04ProductMaster(TDeliveryPlan));
	}
	
	@RequestMapping(value="/MRDC_R04_export_count", method=RequestMethod.GET)
	public @ResponseBody Map<String,Integer> count( TDeliveryPlan TDeliveryPlan ) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		Integer count = mrdc_s04Logic.countMRDC_R04ProductMaster();
		TDeliveryPlan t = new TDeliveryPlan();
		t  = mrdc_s04Logic.exportMRDC_R04ProductMaster(TDeliveryPlan);
		Integer size = t.getPlanList().size();// Data of query export MRDC_R04
		
		Map<String,Integer> map = new LinkedHashMap<String,Integer>();
		map.put("count", count == null ? 0 : count);
		map.put("maxRecord",maxRecord);
		map.put("size",size);
		
		return map;
	}
}
