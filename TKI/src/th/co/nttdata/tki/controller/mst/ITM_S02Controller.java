package th.co.nttdata.tki.controller.mst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.blogic.mst.ITM_S02Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class ITM_S02Controller extends AbstractBaseController {

	private static final String PATH_URI = "MST/ITM_S02";
	
	@Autowired 
	private ITM_S02Logic itm_S02Logic;

	@RequestMapping("/ITM_S02")
	public ModelAndView init() {
		return new ModelAndView(PATH_URI)
			.addObject("mPart", new MPart())
			.addObject("customerMap", itm_S02Logic.getTpicCustomerList());
	}

	@RequestMapping("/ITM_S02_search")
	public ModelAndView search(MPart part) {
		try {
			part = itm_S02Logic.query(part);
		} catch (Exception e) {
			e.printStackTrace();
			setSystemError(part,e);
		}
		return new ModelAndView(PATH_URI)
			.addObject("mPart", part)
			.addObject("customerMap", itm_S02Logic.getTpicCustomerList());
	}

	@RequestMapping("/ITM_S02_sync")
	public ModelAndView sync(MPart part) {
		try {
			itm_S02Logic.save(part);	
			part.getInfos().add(new Message("inf.cmm.005", null));
		} catch (Exception e) {
			e.printStackTrace();
			setSystemError(part,e);
		}
		return search(part);
	}
}