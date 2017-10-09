package th.co.nttdata.tki.controller.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.TPending;
import th.co.nttdata.tki.blogic.dal.PND_S02Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class PND_S02Controller extends AbstractBaseController {

	private static final String PATH_URI = "DAL/PND_S02";
	
	@Autowired
	CommonController commonController;
	
	@Autowired
	PND_S02Logic pnd_s02Logic;

	@RequestMapping("/PND_S02")
	public ModelAndView init() {
		return new ModelAndView(PATH_URI)
		.addObject("searchCriteria", new TPending())
		/*.addObject("wipMap", commonController.getWIPSel())
		.addObject("customerMap", commonController.getCustomerSel());*/
		.addObject("wipMap", commonController.getWIPAll())
		.addObject("customerMap", commonController.getCustomerAll());
	}

	@RequestMapping(value="/PND_S02_search", method=RequestMethod.POST)
	public ModelAndView search(TPending TPending) {
		
		TPending = pnd_s02Logic.search(TPending);
		
		return new ModelAndView(PATH_URI)
		.addObject("searchCriteria", TPending)
		/*.addObject("wipMap", commonController.getWIPSel())
		.addObject("customerMap", commonController.getCustomerSel());*/
		.addObject("wipMap", commonController.getWIPAll())
		.addObject("customerMap", commonController.getCustomerAll());
	}
}