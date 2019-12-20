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
import th.co.nttdata.tki.blogic.mrdc.MRDC_S10Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MRDC_S10Controller extends AbstractBaseController {

	private static final String PATH_URI = "MRDC/MRDC_S10";
	private static final Integer MAXRECORD = 1000000;

	@Autowired
	private MRDC_S10Logic mrdc_s10Logic;
	@Autowired
	private CommonController commonController;

	/*public Map<String,String> getWIP() {
		List<MWip> wips = mrdc_s10Logic.getWIP();

		Map<String,String> map = new LinkedHashMap<String,String>();
		map.put("", "-- Select WIP --");
		for( MWip MWip : wips )
			map.put(MWip.getWip(), MWip.getWip() + " : " + MWip.getWipName());
		return map;
	}
	
	@RequestMapping(value="/PartList", method=RequestMethod.GET)
	public @ResponseBody Map<Integer,String> getPart(String wip) {
		List<MPart> parts = mrdc_s10Logic.getPartWIP(wip);
		Map<Integer,String> map = new LinkedHashMap<Integer,String>();
		for( MPart MPart : parts )
			map.put(MPart.getPartId(), MPart.getPartName() +" : "+ MPart.getPartNo());
		return map;
	}*/

	@RequestMapping("/MRDC_S10")
	public ModelAndView init() {
		
        return new ModelAndView(PATH_URI)
        .addObject("dailyInspection",new TDailyWK())
        .addObject("wipMap", commonController.getWIPSel())
        .addObject("machineMap", commonController.getMachineAllInWIP(null));
    }
	
	@RequestMapping("/MRDC_R10_export")
	public ModelAndView export(TDailyWK tDailyWK) {
		tDailyWK.setMaxRecord(MAXRECORD);
		
        return new ModelAndView("MRDC_R10ExcelView")
        .addObject("tDailyWK", mrdc_s10Logic.exportMRDC_R10(tDailyWK))
		.addObject("reasonList", mrdc_s10Logic.getreasonList());
    }
	
	@RequestMapping(value="/MRDC_R10_export_count", method=RequestMethod.GET)
	public @ResponseBody Map<String,Integer> count( TDailyWK tDailyWK ) {
		
		tDailyWK.setMaxRecord(MAXRECORD);
		Integer maxRecord = MAXRECORD;
		Integer count = mrdc_s10Logic.countMRDC_R10();
		TDailyWK t = new TDailyWK();
		t  = mrdc_s10Logic.exportMRDC_R10(tDailyWK);
		Integer size = t.getDailyWKDetailList().size();// Data of query export MRDC_R10
		
		Map<String,Integer> map = new LinkedHashMap<String,Integer>();
		map.put("count", count == null ? 0 : count);
		map.put("maxRecord",maxRecord);
		map.put("size",size);
		
		return map;
	}
}
