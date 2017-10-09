package th.co.nttdata.tki.controller.fng;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.TFGStock;
import th.co.nttdata.tki.blogic.fng.FNG_S04Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class FNG_S04Controller extends AbstractBaseController {

	private static final String PATH_URI = "FNG/FNG_S04";
	@Autowired
	private CommonController commonController;
	@Autowired
	private FNG_S04Logic fng_S04Logic;

	@RequestMapping("/FNG_S04")
	public ModelAndView init() {
		TFGStock tfgStock = new TFGStock();
		tfgStock.setReportDate(new Date());
		return new ModelAndView(PATH_URI).addObject("tfgStock", tfgStock).addObject("customerMap",
				commonController.getCustomerAll());
	}

	@RequestMapping("/FNG_S04_search")
	public ModelAndView search(TFGStock tfgStock) {
		tfgStock = fng_S04Logic.searchStockAdjust(tfgStock);
		return new ModelAndView(PATH_URI).addObject("tfgStock", tfgStock).addObject("customerMap",
				commonController.getCustomerAll());
	}

	@RequestMapping("/FNG_S04_save")
	public ModelAndView save(TFGStock tfgStock) {
		try {
			fng_S04Logic.adjustStock(tfgStock);
			setSaveInfo(tfgStock);
			tfgStock = fng_S04Logic.searchStockAdjust(tfgStock);
		} catch (Exception e) {
			e.printStackTrace();
			setSystemError(tfgStock, e);
		}
		return new ModelAndView(PATH_URI).addObject("tfgStock", tfgStock).addObject("customerMap",
				commonController.getCustomerAll());
	}

	@RequestMapping(value = "/FNG_S04_export", method = RequestMethod.POST)
	public ModelAndView export(TFGStock tfgStock) {
		tfgStock = fng_S04Logic.exportFNG_R04(tfgStock);

		return new ModelAndView("FNG_R04ExcelView").addObject("tfgStock", tfgStock);
	}

	@RequestMapping(value = "/FNG_S04_import", method = RequestMethod.POST)
	public ModelAndView importFgR04(TFGStock tfgStock) {
		//validate after success read file to import data
		try {
			tfgStock = fng_S04Logic.importFNG_R04(tfgStock);
			tfgStock.setReportDate(new Date());
			setSaveInfo(tfgStock);
		} catch (Exception e) {
			e.printStackTrace();
			tfgStock.setTfgStockList(null);
			setSystemError(tfgStock, e);
		}

		return new ModelAndView(PATH_URI).addObject("tfgStock", tfgStock).addObject("customerMap",
				commonController.getCustomerAll());
	}

	@RequestMapping(value = "/FNG_S04_download_template", method = RequestMethod.GET)
	public ModelAndView downloadTemplate() {
		return new ModelAndView("FNG_R04ExcelView").addObject("tfgStock", null);
	}
}
