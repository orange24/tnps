package th.co.nttdata.tki.controller.mrdc;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.TDailyMC;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S09Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MRDC_S09Controller extends AbstractBaseController{
	
	private static final String PATH_URI = "MRDC/MRDC_S09";
	
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private MRDC_S09Logic mrdc_s09Logic;
	
	@RequestMapping("/MRDC_S09")
	public ModelAndView init(){
		return new ModelAndView(PATH_URI)
		.addObject("tDailyMC",new TDailyMC())
		.addObject("wip",commonController.getWIPAll(1));
	}
	
	@RequestMapping("/MRDC_S09_export")
	public ModelAndView export(TDailyMC TDailyMC){		
		return new ModelAndView("MRDC_R09ExcelView")
		.addObject("TDailyMC",mrdc_s09Logic.exportMRDC_R09(TDailyMC));
	}
	
	@RequestMapping(value="/MRDC_R09_export_count", method=RequestMethod.GET)
	public @ResponseBody Map<String,Integer> count( TDailyMC TDailyMC ) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		Integer count = mrdc_s09Logic.countMRDC_R09();
		TDailyMC t = new TDailyMC();
		t  = mrdc_s09Logic.exportMRDC_R09(TDailyMC);
		Integer size = t.getDetails().size();// Data of query export MRDC_R02
		
		Map<String,Integer> map = new LinkedHashMap<String,Integer>();
		map.put("count", count == null ? 0 : count);
		map.put("maxRecord",maxRecord);
		map.put("size",size);
		
		return map;
	}
}
