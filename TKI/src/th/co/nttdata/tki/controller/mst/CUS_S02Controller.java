package th.co.nttdata.tki.controller.mst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.blogic.mst.CUS_S02Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class CUS_S02Controller extends AbstractBaseController {

	private static final String PATH_URI = "MST/CUS_S02";

	@Autowired
	private CUS_S02Logic cus_S02Logic;

	@RequestMapping("/CUS_S02")
	public ModelAndView init() {
		return new ModelAndView(PATH_URI)
		.addObject("searchCriteria",new MCustomer());
	}

	@RequestMapping(value = "/CUS_S02_search", method = RequestMethod.POST)
	public ModelAndView search(MCustomer mCustomer) {
		mCustomer = cus_S02Logic.search(mCustomer);
		return new ModelAndView(PATH_URI)
		.addObject("searchCriteria", mCustomer);
	}
	
	@RequestMapping(value="/CUS_S02_save", method=RequestMethod.POST)
	public ModelAndView save(MCustomer mCustomer){		
		try {
			cus_S02Logic.save(mCustomer);
			mCustomer.getInfos().add(new Message("inf.cmm.005", new String[] {}));
		} catch (Exception e) {
			setSystemError(mCustomer,e);
		}		
		return search(mCustomer);	
	}
}
