package th.co.nttdata.tki.controller.mrdc;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.TWip;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S19Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MRDC_S19Controller extends AbstractBaseController {
	
private static final String PATH_URI = "MRDC/MRDC_S19";
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private MRDC_S19Logic mrdc_s19Logic;
	
	@RequestMapping("/MRDC_S19")
	public ModelAndView init(){
		Calendar cal = new GregorianCalendar(Locale.US);

		TWip tWip = new TWip();
		tWip.setYear(cal.get(Calendar.YEAR));
		tWip.setMonth(cal.get(Calendar.MONTH));
		
		return new ModelAndView(PATH_URI)
		.addObject("tWip",tWip)
		.addObject("monthMap", getMonthMap())
		.addObject("yearMap", getYearMap())
		.addObject("processMap",commonController.getWIPAll())
		.addObject("categoryMap",commonController.getCategory());
	}

	@RequestMapping("/MRDC_S19_Search")
	public ModelAndView search(TWip tWip){
		return new ModelAndView(PATH_URI)
		.addObject("tWip",mrdc_s19Logic.getWipStockInquiry(tWip))
		.addObject("monthMap", getMonthMap())
		.addObject("yearMap", getYearMap())
		.addObject("processMap",commonController.getWIPSel())
		.addObject("categoryMap",commonController.getCategory());
	}
}
