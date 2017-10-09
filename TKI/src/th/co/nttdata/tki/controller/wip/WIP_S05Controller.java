package th.co.nttdata.tki.controller.wip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.bean.TWipJunk;
import th.co.nttdata.tki.blogic.wip.WIP_S05Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class WIP_S05Controller extends AbstractBaseController {

	private static final String PATH_URI = "WIP/WIP_S05";
	
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private WIP_S05Logic wip_S05Logic;

	@RequestMapping("/WIP_S05")
	public ModelAndView init() {
		return new ModelAndView(PATH_URI)
			.addObject("searchCriteria", new TWipJunk())
			.addObject("wipMap", commonController.getWIPAll())
			.addObject("monthMap", commonController.stockMonthYear())
			.addObject("customerMap", commonController.getCustomerAll());
	}
	@RequestMapping(value="/WIP_S05_search",method=RequestMethod.POST)
	public ModelAndView search(TWipJunk tWipJunk){
		if(tWipJunk.getCustomerId() < 0)
			tWipJunk.setCustomerId(null);
		tWipJunk = wip_S05Logic.searchWipJunk(tWipJunk);
		return new ModelAndView(PATH_URI)
			.addObject("searchCriteria", tWipJunk)
			.addObject("wipMap", commonController.getWIPAll())
			.addObject("monthMap", commonController.stockMonthYear())
			.addObject("customerMap", commonController.getCustomerAll());
	}

	@RequestMapping(value="/WIP_S05_adjust",method=RequestMethod.POST)
	public ModelAndView adjust(TWipJunk tWipJunk){
		if(tWipJunk.getCustomerId() < 0)
			tWipJunk.setCustomerId(null);
		wip_S05Logic.adjustWipJunk(tWipJunk);
		tWipJunk = wip_S05Logic.searchWipJunk(tWipJunk);
		tWipJunk.getInfos().clear();
		tWipJunk.getInfos().add(new Message("inf.cmm.002",null));
		return new ModelAndView(PATH_URI)
			.addObject("searchCriteria", tWipJunk)
			.addObject("wipMap", commonController.getWIPAll())
			.addObject("monthMap", commonController.stockMonthYear())
			.addObject("customerMap", commonController.getCustomerAll());
	}
	
}