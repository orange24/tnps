package th.co.nttdata.tki.controller.mrdc;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MMoldDetail;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S21Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MRDC_S21Controller extends AbstractBaseController {
	
	private static final String PATH_URI = "MRDC/MRDC_S21";
	
	@Autowired
	CommonController controller;
	@Autowired
	MRDC_S21Logic mrdc_S21Logic;
	
	@RequestMapping("/MRDC_S21")
	public ModelAndView init(MMoldDetail mdDetail){
		return new ModelAndView(PATH_URI)
		.addObject("custMap", controller.getCustomerAll())
		.addObject("mDetail", mdDetail);
	}
	
	@RequestMapping(value="/MRDC_S21_export", method=RequestMethod.POST)
	public ModelAndView export(MMoldDetail mdDetail){
		return new ModelAndView("MRDC_R21ExcelView")
		.addObject("mDetail", mrdc_S21Logic.exportMRDC_R21(mdDetail));
	}

	@RequestMapping(value="/MRDC_R21_export_count", method=RequestMethod.GET)
	public @ResponseBody Map<String,Integer> count( MMoldDetail mdDetail ) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		Integer count = mrdc_S21Logic.countMRDC_R21();
		MMoldDetail t = new MMoldDetail();
		t  = mrdc_S21Logic.exportMRDC_R21(mdDetail);
		Integer size = t.getmDetailList().size();// Data of query export MRDC_R21
		
		Map<String,Integer> map = new LinkedHashMap<String,Integer>();
		map.put("count", count == null ? 0 : count);
		map.put("maxRecord",maxRecord);
		map.put("size",size);
		
		return map;
	}
}
