package th.co.nttdata.tki.controller.mst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.blogic.mst.PRC_S02Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class PRC_S02Controller extends AbstractBaseController {

	private static final String PATH_URI = "MST/PRC_S02";
	
	@Autowired
	private PRC_S02Logic prc_S02Logic;
	
	@RequestMapping("/PRC_S02")
	public ModelAndView init() {
		return new ModelAndView(PATH_URI)
		.addObject("searchCriteria",new MWip());
	}

	@RequestMapping(value = "/PRC_S02_search", method = RequestMethod.POST)
	public ModelAndView search(MWip mWip) {
		mWip = prc_S02Logic.search(mWip);
		return new ModelAndView(PATH_URI)
		.addObject("searchCriteria", mWip);
	}
	
	@RequestMapping(value="/PRC_S02_save", method=RequestMethod.POST)
	public ModelAndView save(MWip mWip){		
		try {
			prc_S02Logic.save(mWip);	
			mWip.getInfos().add(new Message("inf.cmm.005", new String[] {}));
		} catch (Exception e) {
			setSystemError(mWip,e);
		}		
		return search(mWip);	
	}
}
