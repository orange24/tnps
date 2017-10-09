package th.co.nttdata.tki.controller.mrdc;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.VDailyMonthlySales;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S07Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MRDC_S07Controller extends AbstractBaseController{
	
	private static final String PATH_URI = "MRDC/MRDC_S07";
	
	@Autowired
	private MRDC_S07Logic mrdc_s07Logic;
	@Autowired
	private CommonController commonController;
	
	@RequestMapping("/MRDC_S07")
	public ModelAndView init() {
        return new ModelAndView(PATH_URI)
        .addObject("dailyMonthly",new VDailyMonthlySales())
        .addObject("customerMap", commonController.getCustomerAll());
    }
	
	@RequestMapping("/MRDC_R07_export")
	public ModelAndView export(VDailyMonthlySales vdms) {
        return new ModelAndView("MRDC_R07ExcelView")
        .addObject("vDailyMS", mrdc_s07Logic.exportMRDC_R07(vdms));
    }
	
	@RequestMapping(value="/MRDC_R07_export_count", method=RequestMethod.GET)
	public @ResponseBody Map<String,Integer> count( VDailyMonthlySales vdms ) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));;
		Integer count = mrdc_s07Logic.countMRDC_R07();
		VDailyMonthlySales t = new VDailyMonthlySales();
		t  = mrdc_s07Logic.sizeMRDC_R07(vdms);
		Integer size = t.getvDailyMonthlySalesList().size();// Data of query export MRDC_R07
		
		Map<String,Integer> map = new LinkedHashMap<String,Integer>();
		map.put("count", count == null ? 0 : count);
		map.put("maxRecord",maxRecord);
		map.put("size",size);
		
		return map;
	}
}
