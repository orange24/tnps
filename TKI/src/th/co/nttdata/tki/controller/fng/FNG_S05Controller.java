package th.co.nttdata.tki.controller.fng;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.TFGStock;
import th.co.nttdata.tki.blogic.cmm.CommonLogic;
import th.co.nttdata.tki.blogic.fng.FNG_S05Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class FNG_S05Controller extends AbstractBaseController {

	private static final String PATH_URI = "FNG/FNG_S05";
	@Autowired
	private CommonLogic commonLogic;
	@Autowired
	private FNG_S05Logic fng_S05Logic;

	public Map<Integer, String> getCustomer() {
		List<MCustomer> customers = commonLogic.getCustomer();

		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(null, "-- All Customer --");
		for (MCustomer MCustomer : customers)
			map.put(MCustomer.getCustomerId(), MCustomer.getCustomerCode());
		return map;
	}

	@RequestMapping("/FNG_S05")
	public ModelAndView init() {
		return new ModelAndView(PATH_URI).addObject("tfgStock", new TFGStock())
				.addObject("customerMap", getCustomer());
	}

	@RequestMapping("/FNG_S05_search")
	public ModelAndView search(TFGStock tfgStock) {
		tfgStock = fng_S05Logic.searchFGAdjustHistory(tfgStock);
		return new ModelAndView(PATH_URI).addObject("tfgStock", tfgStock)
				.addObject("customerMap", getCustomer());
	}
}
