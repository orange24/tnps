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
import th.co.nttdata.tki.blogic.mrdc.MRDC_S22Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MRDC_S22Controller extends AbstractBaseController{
	
	private static final String PATH_URI = "MRDC/MRDC_S22";
	
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private MRDC_S22Logic mrdc_s22Logic;
	
	@RequestMapping("/MRDC_S22")
	public ModelAndView init(){
		return new ModelAndView(PATH_URI)
		.addObject("dailyWK",new TDailyWK())
		.addObject("wip",commonController.getWIPAll())
		.addObject("machineMap", commonController.getMachineAllInWIP(null))
		.addObject("MaterialTypeMap", commonController.getMaterialCodeName());
	}
	
	@RequestMapping("/MRDC_R22_export")
	public ModelAndView export(TDailyWK TDailyWK){
		
		return new ModelAndView("MRDC_R22ExcelView")
		.addObject("tDailyWK",mrdc_s22Logic.exportMRDC_R22(TDailyWK));
	}
	
	@RequestMapping(value="/MRDC_R22_export_count", method=RequestMethod.GET)
	public @ResponseBody Map<String,Integer> count( TDailyWK TDailyWK ) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		Integer count = mrdc_s22Logic.countMRDC_R22();
		TDailyWK t = new TDailyWK();
		t  = mrdc_s22Logic.exportMRDC_R22(TDailyWK);
		Integer size = t.getDailyWKDetailList().size();// Data of query export MRDC_R22
		
		Map<String,Integer> map = new LinkedHashMap<String,Integer>();
		map.put("count", count == null ? 0 : count);
		map.put("maxRecord",maxRecord);
		map.put("size",size);
		
		return map;
	}
}
