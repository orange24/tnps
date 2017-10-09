package th.co.nttdata.tki.controller.fng;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.PrintTagChangedHistory;
import th.co.nttdata.tki.blogic.fng.FNG_S07Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class FNG_S07Controller extends AbstractBaseController {

	private static final String PATH_URI = "FNG/FNG_S07";

	@Autowired
	private CommonController commonController;

	@Autowired
	private FNG_S07Logic fng_s07Logic;

	@RequestMapping("/FNG_S07")
	public ModelAndView init() {
		return new ModelAndView(PATH_URI)
				.addObject("searchCriteria", new PrintTagChangedHistory())
				.addObject("monthMap", commonController.stockMonthYear())
				.addObject("customerMap", commonController.getCustomerAll());
	}

	@RequestMapping(value = "/FNG_S07_search", method = RequestMethod.POST)
	public @ResponseBody
	List<PrintTagChangedHistory> search(
			@RequestBody PrintTagChangedHistory tfgJunk) {

		if (0 > Integer.valueOf(tfgJunk.getCustomerFrom())) {
			tfgJunk.setCustomerFrom("");
		}
		if (0 > Integer.valueOf(tfgJunk.getCustomerTo())) {
			tfgJunk.setCustomerTo("");
		}
		return fng_s07Logic.searchTag(tfgJunk);
	}

	@RequestMapping(value = "/FNG_S07_Export", method = RequestMethod.POST)
	public ModelAndView export(PrintTagChangedHistory tfgJunk) {
		if (0 > Integer.valueOf(tfgJunk.getCustomerFrom())) {
			tfgJunk.setCustomerFrom("");
		}
		if (0 > Integer.valueOf(tfgJunk.getCustomerTo())) {
			tfgJunk.setCustomerTo("");
		}
		List<PrintTagChangedHistory> list = fng_s07Logic.searchTag(tfgJunk);
		return new ModelAndView("FNG_S07ExcelView").addObject("data", list);
	}
}
