package th.co.nttdata.tki.controller.dlv;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.blogic.DLV.DLV_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class DLV_S01Controller extends AbstractBaseController {

	private static final String PATH_URI = "DLV/DLV_S01";
	
	@Autowired
	private CommonController commonController;
	@Autowired
	private DLV_S01Logic dlv_S01Logic;
	
	@RequestMapping("/DLV_S01")
	public ModelAndView init() {

		// <!-- Initial: -->
		Calendar cal = new GregorianCalendar(Locale.US);

		TDeliveryPlan TDeliveryPlan = new TDeliveryPlan();
		TDeliveryPlan.setMonth(cal.get(Calendar.MONTH));
		TDeliveryPlan.setYear(cal.get(Calendar.YEAR));

        return new ModelAndView(PATH_URI)
        .addObject("deliveryPlan", TDeliveryPlan)
        .addObject("customerMap", commonController.getCustomerSel())
		.addObject("monthMap", getMonthMap())
		.addObject("yearMap", getYearMap());
    }
	
	@RequestMapping(value="/DLV_S01_search", method=RequestMethod.POST)
	public ModelAndView search(TDeliveryPlan TDeliveryPlan) {
		TDeliveryPlan = dlv_S01Logic.search(TDeliveryPlan);
		
		return new ModelAndView(PATH_URI)
		.addObject("deliveryPlan", TDeliveryPlan)
        .addObject("customerMap", commonController.getCustomerSel())
		.addObject("monthMap", getMonthMap())
		.addObject("yearMap", getYearMap());
	}

	@RequestMapping(value="/DLV_S01_copy", method=RequestMethod.POST)
	public ModelAndView copy(TDeliveryPlan TDeliveryPlan) {
		TDeliveryPlan = dlv_S01Logic.copyPlan(TDeliveryPlan);
		
		return search(TDeliveryPlan);
	}
	
	@RequestMapping(value="/DLV_S01_deleteByPlan", method=RequestMethod.POST)
	public ModelAndView deleteByPlan(TDeliveryPlan TDeliveryPlan) {
		dlv_S01Logic.deleteByPlan(TDeliveryPlan);
		
		return search(TDeliveryPlan);
	}
}
