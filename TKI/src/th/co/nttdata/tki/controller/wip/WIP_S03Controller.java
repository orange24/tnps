package th.co.nttdata.tki.controller.wip;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.TWipStockAdjust;
import th.co.nttdata.tki.blogic.wip.WIP_S03Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class WIP_S03Controller extends AbstractBaseController {

	private static final String PATH_URI = "WIP/WIP_S03";
	@Autowired
	private CommonController commonController;
	@Autowired
	private WIP_S03Logic wipS03Logic;

	@RequestMapping("/WIP_S03")
	public ModelAndView init() {
		TWipStockAdjust tWipStockAdjust = new TWipStockAdjust();
		tWipStockAdjust.setReportDate(new Date());
		return new ModelAndView(PATH_URI).addObject("searchCriteria", tWipStockAdjust)
				.addObject("customerMap", commonController.getCustomerAll())
				.addObject("wipMap", commonController.getWIPAll());
	}

	@RequestMapping(value = "/WIP_S03_search", method = RequestMethod.POST)
	public ModelAndView search(TWipStockAdjust TWipStockAdjust) {

		TWipStockAdjust = wipS03Logic.search(TWipStockAdjust);

		return new ModelAndView(PATH_URI).addObject("searchCriteria", TWipStockAdjust)
				.addObject("customerMap", commonController.getCustomerAll())
				.addObject("wipMap", commonController.getWIPAll());
	}

	@RequestMapping(value = "/WIP_S03_save", method = RequestMethod.POST)
	public ModelAndView save(TWipStockAdjust TWipStockAdjust) {

		try {
			wipS03Logic.save(TWipStockAdjust);
			setSaveInfo(TWipStockAdjust);
		} catch (Exception e) {
			e.printStackTrace();
			setSystemError(TWipStockAdjust, e);
		}

		TWipStockAdjust = wipS03Logic.search(TWipStockAdjust);

		return new ModelAndView(PATH_URI).addObject("searchCriteria", TWipStockAdjust)
				.addObject("customerMap", commonController.getCustomerAll())
				.addObject("wipMap", commonController.getWIPAll());
	}

	@RequestMapping(value = "/WIP_S03_export", method = RequestMethod.POST)
	public ModelAndView export(TWipStockAdjust tWipStockAdjust) {
		tWipStockAdjust = wipS03Logic.exportWIP_R03(tWipStockAdjust);

		return new ModelAndView("WIP_R03ExcelView").addObject("tWipStockAdjust", tWipStockAdjust);
	}

	@RequestMapping(value = "/WIP_S03_import", method = RequestMethod.POST)
	public ModelAndView importWipR03(TWipStockAdjust tWipStockAdjust) {
		//validate after success read file to import data
		try {
			tWipStockAdjust = wipS03Logic.importWIP_R03(tWipStockAdjust);
			tWipStockAdjust.setReportDate(new Date());
			setSaveInfo(tWipStockAdjust);
		} catch (Exception e) {
			e.printStackTrace();
			tWipStockAdjust.setAdjustList(null);
			setSystemError(tWipStockAdjust, e);
		}

		return new ModelAndView(PATH_URI).addObject("searchCriteria", tWipStockAdjust)
				.addObject("customerMap", commonController.getCustomerAll())
				.addObject("wipMap", commonController.getWIPAll());
	}

	@RequestMapping(value = "/WIP_S03_download_template", method = RequestMethod.GET)
	public ModelAndView downloadTemplate() {
		return new ModelAndView("WIP_R03ExcelView").addObject("tWipStockAdjust", null);
	}

}
