package th.co.nttdata.tki.controller.fng;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.TFG;
import th.co.nttdata.tki.blogic.cmm.CommonLogic;
import th.co.nttdata.tki.blogic.fng.FNG_S03Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class FNG_S03Controller extends AbstractBaseController {

	private static final String PATH_URI = "FNG/FNG_S03";
	@Autowired
	private CommonController commonController;
	@Autowired
	private CommonLogic commonLogic;
	@Autowired
	private FNG_S03Logic fngS03Logic;

	@RequestMapping("/FNG_S03")
	public ModelAndView init() {
		TFG TFG = new TFG();

		// <!-- Initial. -->
		Calendar current = new GregorianCalendar(Locale.US);
		TFG.setMonth(current.get(Calendar.MONTH));
		TFG.setYear(current.get(Calendar.YEAR));

		return new ModelAndView(PATH_URI)
			.addObject("fng", TFG)
			.addObject("customerMap", commonController.getCustomerAll())
			.addObject("monthMap", getMonthMap())
			.addObject("yearMap", getYearMap());
	}

	@RequestMapping("/FNG_S03_search")
	public ModelAndView search( TFG TFG ) {
		TFG = fngS03Logic.search( TFG );

		return new ModelAndView(PATH_URI)
			.addObject("fng", TFG)
			.addObject("customerMap", commonController.getCustomerAll())
			.addObject("monthMap", getMonthMap())
			.addObject("yearMap", getYearMap());
	}
	
	@RequestMapping(value="/FNG_R02_export", method=RequestMethod.POST)
	public ModelAndView export( TFG TFG ){
		Integer customerId = TFG.getCustomerId();
		MCustomer mCustomer = new MCustomer();
		mCustomer.setCustomerId(customerId);
		
		TFG = fngS03Logic.exportFNG_R02( TFG );
		
		return new ModelAndView("FNG_R02ExcelView")
		.addObject("tfg",TFG)
		.addObject("customer", commonLogic.getMCustomer(mCustomer))
		.addObject("monthMap", getMonthMap())
		.addObject("yearMap", getYearMap());
	}
}