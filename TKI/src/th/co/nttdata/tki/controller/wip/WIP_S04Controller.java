package th.co.nttdata.tki.controller.wip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.TWipStockAdjust;
import th.co.nttdata.tki.blogic.wip.WIP_S04Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class WIP_S04Controller extends AbstractBaseController {

	private static final String PATH_URI = "WIP/WIP_S04";
	@Autowired
	private CommonController commonController;
	@Autowired
	private WIP_S04Logic wipS04Logic;

	@RequestMapping("/WIP_S04")
	public ModelAndView init() {
		return new ModelAndView(PATH_URI).addObject("searchCriteria", new TWipStockAdjust())
				.addObject("wipMap", commonController.getWIPAll())
				.addObject("customerMap", commonController.getCustomerAll());
	}

	@RequestMapping(value = "/WIP_S04_search", method = RequestMethod.POST)
	public ModelAndView search(TWipStockAdjust TWipStockAdjust) {
		TWipStockAdjust = wipS04Logic.search(TWipStockAdjust);

		return new ModelAndView(PATH_URI).addObject("searchCriteria", TWipStockAdjust)
				.addObject("wipMap", commonController.getWIPAll())
				.addObject("customerMap", commonController.getCustomerAll());
	}

	@RequestMapping(value = "/WIP_S04_export", method = RequestMethod.POST)
	public ModelAndView export(TWipStockAdjust TWipStockAdjust) {
		return new ModelAndView("WIP_R04ExcelView").addObject("tWipStockAdjust",
				wipS04Logic.exportWIP_R04(TWipStockAdjust));
	}
}
