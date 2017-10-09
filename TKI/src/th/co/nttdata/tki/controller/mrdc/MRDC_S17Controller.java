package th.co.nttdata.tki.controller.mrdc;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.TDailyMC;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S17Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MRDC_S17Controller extends AbstractBaseController{
	
	private static final String PATH_URI = "MRDC/MRDC_S17";
	
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private MRDC_S17Logic mrdc_s17Logic;
	
	@RequestMapping("/MRDC_S17")
	public ModelAndView init(){
		return new ModelAndView(PATH_URI)
		.addObject("tDailyMC",new TDailyMC())
		.addObject("wip",getWIP())
		.addObject("machineMap", commonController.getMachineNo(null));
	}
	
	@RequestMapping("/MRDC_S17_export")
	public ModelAndView export(TDailyMC TDailyMC){
		return new ModelAndView("MRDC_R17ExcelView")
		.addObject("TDailyMC",mrdc_s17Logic.exportMRDC_R17(TDailyMC))
		.addObject("reasonList", mrdc_s17Logic.getreasonList());
	}
	
	private Map<String,String> getWIP() {
		List<MWip> wips = mrdc_s17Logic.getWip(new MWip());

		Map<String,String> map = new LinkedHashMap<String,String>();
		map.put("", "-- All WIP --");
		for( MWip MWip : wips )
			map.put(MWip.getWip(), MWip.getWip() + " : " + MWip.getWipName());
		return map;
	}
	
	@RequestMapping(value="/MRDC_R17_export_count", method=RequestMethod.GET)
	public @ResponseBody Map<String,Integer> count( TDailyMC TDailyMC ) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		Integer count = mrdc_s17Logic.countMRDC_R17();
		TDailyMC t = new TDailyMC();
		t  = mrdc_s17Logic.exportMRDC_R17(TDailyMC);
		Integer size = t.getDetails().size();// Data of query export MRDC_R02
		
		Map<String,Integer> map = new LinkedHashMap<String,Integer>();
		map.put("count", count == null ? 0 : count);
		map.put("maxRecord",maxRecord);
		map.put("size",size);
		
		return map;
	}
}
