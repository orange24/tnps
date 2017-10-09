package th.co.nttdata.tki.controller.wip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.TReworkAdjust;
import th.co.nttdata.tki.blogic.wip.REW_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class REW_S01Controller extends AbstractBaseController {

	private static final String PATH_URI = "WIP/REW_S01";
	
	@Autowired
	CommonController commonController;
	
	@Autowired
	REW_S01Logic rewS01Logic;
	
	@RequestMapping("/REW_S01.html")
	public ModelAndView init(){
		return new ModelAndView(PATH_URI)
		.addObject("searchCriteria", new TReworkAdjust())
		.addObject("wipMap", commonController.getWIPAll())
		.addObject("customerMap", commonController.getCustomerAll());
	}
	
	@RequestMapping("/REW_S01_search.html")
	public ModelAndView search(TReworkAdjust TReworkAdjust){
		
		TReworkAdjust = rewS01Logic.search(TReworkAdjust);
		
		return new ModelAndView(PATH_URI)
		.addObject("searchCriteria", TReworkAdjust)
		.addObject("wipMap", commonController.getWIPAll())
		.addObject("customerMap", commonController.getCustomerAll());
	}
	
	@RequestMapping("/REW_S01_save")
	public ModelAndView save(TReworkAdjust TReworkAdjust){
		
		try {
			rewS01Logic.save(TReworkAdjust);
		} catch (Exception e) {
			setSystemError(TReworkAdjust,e);
		}
		
		return new ModelAndView(PATH_URI)
		.addObject("searchCriteria", rewS01Logic.search(TReworkAdjust))
		.addObject("wipMap", commonController.getWIPAll())
		.addObject("customerMap", commonController.getCustomerAll());
	}
}
