package th.co.nttdata.tki.controller.mrdc;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.TDailyWK;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S13Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MRDC_S13Controller extends AbstractBaseController{
	
	private static final String PATH_URI = "MRDC/MRDC_S13";
	
	@Autowired
	private CommonController commonController;

	@Autowired
	private MRDC_S13Logic mrdc_s13Logic;
	
	@RequestMapping("/MRDC_S13")
	public ModelAndView init(){
		return new ModelAndView(PATH_URI)
		.addObject("dailyWK",new TDailyWK())
		.addObject("wip",commonController.getWIPAll())
		.addObject("machineMap", commonController.getMachineNoAll())
		.addObject("customerMap", commonController.getCustomerAll());
	}
	
	@RequestMapping("/MRDC_R13_export")
	public ModelAndView export(TDailyWK TDailyWK){
		
		return new ModelAndView("MRDC_R13ExcelView")
		.addObject("tDailyWK",mrdc_s13Logic.exportMRDC_R13(TDailyWK));
	}
	
	@RequestMapping(value="/MRDC_R13_export_count", method=RequestMethod.GET)
	public @ResponseBody Map<String,Integer> count( TDailyWK TDailyWK ) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		Integer count = mrdc_s13Logic.countMRDC_R13();
		TDailyWK t = new TDailyWK();
		t  = mrdc_s13Logic.exportMRDC_R13(TDailyWK);
		Integer size = t.getDailyWKDetailList().size();// Data of query export MRDC_R13
		
		Map<String,Integer> map = new LinkedHashMap<String,Integer>();
		map.put("count", count == null ? 0 : count);
		map.put("maxRecord",maxRecord);
		map.put("size",size);
		
		return map;
	}
}
