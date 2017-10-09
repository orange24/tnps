package th.co.nttdata.tki.controller.dal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.AbstractBaseBean;
import th.co.nttdata.tki.bean.TPending;
import th.co.nttdata.tki.blogic.dal.PND_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class PND_S01Controller extends AbstractBaseController {

	Logger log = Logger.getLogger(PND_S01Controller.class);
	
	private static final String PATH_URI = "DAL/PND_S01";
	@Autowired
	private CommonController commonController;
	@Autowired
	private PND_S01Logic pndS01Logic;
	
	@RequestMapping("/PND_S01")
	public ModelAndView init() {
		return new ModelAndView(PATH_URI)
		.addObject("searchCriteria", new TPending())
		/*.addObject("wipMap", commonController.getWIPSel())
		.addObject("custMap", commonController.getCustomerSel());*/
		.addObject("wipMap", commonController.getWIPAll())
		.addObject("custMap", commonController.getCustomerAll());
		
	}
	
	@RequestMapping(value="/PND_S01_search", method=RequestMethod.POST)
	public ModelAndView search( TPending tPending ) {
		pndS01Logic.search(tPending);
		return new ModelAndView(PATH_URI)
			.addObject("searchCriteria", tPending)
			/*.addObject("wipMap", commonController.getWIPSel())
			.addObject("custMap", commonController.getCustomerSel());*/
			.addObject("wipMap", commonController.getWIPAll())
			.addObject("custMap", commonController.getCustomerAll());
	}
	
	@RequestMapping(value="/PND_S01_searchAfterSave", method=RequestMethod.POST)
	public ModelAndView searchAfterSave( TPending tPending ) {
		pndS01Logic.search(tPending);
		setSaveInfo(tPending);
		return new ModelAndView(PATH_URI)
			.addObject("searchCriteria", tPending)
			/*.addObject("wipMap", commonController.getWIPSel())
			.addObject("custMap", commonController.getCustomerSel());*/
			.addObject("wipMap", commonController.getWIPAll())
			.addObject("custMap", commonController.getCustomerAll());
	}
	
	@RequestMapping(value="/PND_S01_save", method=RequestMethod.POST)
	public @ResponseBody AbstractBaseBean save( TPending tPending ) {
		try {
			pndS01Logic.save( tPending );
			setSaveInfo(tPending);
		} catch( Exception e ) {
			e.printStackTrace();
			setSystemError(tPending,e);
		}

		return tPending;
	}
}