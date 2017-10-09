package th.co.nttdata.tki.controller.mrdc;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.TPending;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S08Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MRDC_S08Controller extends AbstractBaseController{
	
	private static final String PATH_URI = "MRDC/MRDC_S08";
	
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private MRDC_S08Logic mrdc_s08Logic;
	
	@RequestMapping("/MRDC_S08")
	public ModelAndView init(){
		return new ModelAndView(PATH_URI)
		.addObject("tPending",new TPending())
		.addObject("wip",commonController.getWIPAll());
	}
	
	@RequestMapping("/MRDC_S08_export")
	public ModelAndView export(TPending tPending){		
		return new ModelAndView("MRDC_R08ExcelView")
		.addObject("tPending",mrdc_s08Logic.exportMRDC_R08(tPending));
	}
	
	@RequestMapping(value="/MRDC_R08_export_count", method=RequestMethod.GET)
	public @ResponseBody Map<String,Integer> count( TPending tPending ) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		Integer count = mrdc_s08Logic.countMRDC_R08();
		TPending t = new TPending();
		t  = mrdc_s08Logic.exportMRDC_R08(tPending);
		Integer size = t.getAdjustList().size();// Data of query export MRDC_R02
		
		Map<String,Integer> map = new LinkedHashMap<String,Integer>();
		map.put("count", count == null ? 0 : count);
		map.put("maxRecord",maxRecord);
		map.put("size",size);
		
		return map;
	}
}
