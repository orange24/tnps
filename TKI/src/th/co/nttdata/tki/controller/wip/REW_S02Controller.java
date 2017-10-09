package th.co.nttdata.tki.controller.wip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.TReworkAdjust;
import th.co.nttdata.tki.blogic.wip.REW_S02Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class REW_S02Controller extends AbstractBaseController {

	private static final String PATH_URI = "WIP/REW_S02";
	
	@Autowired
	CommonController commonController;
	
	@Autowired
	REW_S02Logic rewS02Logic;
	
	@RequestMapping("/REW_S02.html")
	public ModelAndView init(){
		return new ModelAndView(PATH_URI)
		.addObject("searchCriteria", new TReworkAdjust())
		.addObject("wipMap", commonController.getWIPAll())
		.addObject("customerMap", commonController.getCustomerAll());
	}
	
	@RequestMapping("/REW_S02_search.html")
	public ModelAndView search(TReworkAdjust TReworkAdjust){
		
		TReworkAdjust = rewS02Logic.search(TReworkAdjust);
		
		return new ModelAndView(PATH_URI)
		.addObject("searchCriteria", TReworkAdjust)
		.addObject("wipMap", commonController.getWIPAll())
		.addObject("customerMap", commonController.getCustomerAll());
	}
}
