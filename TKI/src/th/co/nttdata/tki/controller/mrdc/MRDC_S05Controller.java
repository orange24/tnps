package th.co.nttdata.tki.controller.mrdc;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.VProductProcesMaster;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S05Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MRDC_S05Controller extends AbstractBaseController{
	private static final String PATH_URI = "MRDC/MRDC_S05";
	
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private MRDC_S05Logic mrdc_s05Logic;
	
	@RequestMapping("/MRDC_S05")
	public ModelAndView init(){
		return new ModelAndView(PATH_URI)
		.addObject("vPro",new VProductProcesMaster())
		.addObject("wip",commonController.getWIPAll())
		.addObject("categoryMap",commonController.getCategory())
		.addObject("machineMap", commonController.getMachineNo(null));
	}
	
	@RequestMapping("/MRDC_S05_export")
	public ModelAndView export(VProductProcesMaster vPro){
		return new ModelAndView("MRDC_R05ExcelView")
		.addObject("vPro",mrdc_s05Logic.exportMRDC_R05(vPro));
	}
	
	@RequestMapping(value="/MRDC_R05_export_count", method=RequestMethod.GET)
	public @ResponseBody Map<String,Integer> count( VProductProcesMaster vPro ) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		Integer count = mrdc_s05Logic.countMRDC_S05();
		VProductProcesMaster t = new VProductProcesMaster();
		t  = mrdc_s05Logic.exportMRDC_R05(vPro);
		Integer size = t.getvList().size();// Data of query export MRDC_R05
		
		Map<String,Integer> map = new LinkedHashMap<String,Integer>();
		map.put("count", count == null ? 0 : count);
		map.put("maxRecord",maxRecord);
		map.put("size",size);
		
		return map;
	}
}
