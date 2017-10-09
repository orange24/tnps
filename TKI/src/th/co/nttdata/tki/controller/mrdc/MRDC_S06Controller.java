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
import th.co.nttdata.tki.blogic.mrdc.MRDC_S06Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MRDC_S06Controller extends AbstractBaseController{
	
	private static final String PATH_URI = "MRDC/MRDC_S06";
	private static final Integer MAXRECORD = 35000;
	
	@Autowired
	private MRDC_S06Logic mrdc_s06Logic;
	@Autowired
	private CommonController commonController;
	
	@RequestMapping("/MRDC_S06")
	public ModelAndView init() {
        return new ModelAndView(PATH_URI)
        .addObject("tDelivery",new VDailyMonthlySales())
        .addObject("customerMap", commonController.getCustomerAll());
    }
	
	@RequestMapping("/MRDC_R06_export")
	public ModelAndView export(VDailyMonthlySales vdms) {
		vdms.setMaxRecord(MAXRECORD);
		
        return new ModelAndView("MRDC_R06ExcelView")
        .addObject("vDailyMS", mrdc_s06Logic.exportMRDC_R06(vdms));
    }
	
	@RequestMapping(value="/MRDC_R06_export_count", method=RequestMethod.GET)
	public @ResponseBody Map<String,Integer> count( VDailyMonthlySales vdms ) {
		
		vdms.setMaxRecord(MAXRECORD);
		Integer maxRecord = MAXRECORD;
		Integer count = mrdc_s06Logic.countMRDC_R06();
		VDailyMonthlySales t = new VDailyMonthlySales();
		t  = mrdc_s06Logic.sizeMRDC_R06(vdms);
		Integer size = t.getvDailyMonthlySalesList().size();// Data of query export MRDC_R06
		
		Map<String,Integer> map = new LinkedHashMap<String,Integer>();
		map.put("count", count == null ? 0 : count);
		map.put("maxRecord",maxRecord);
		map.put("size",size);
		
		return map;
	}
}
