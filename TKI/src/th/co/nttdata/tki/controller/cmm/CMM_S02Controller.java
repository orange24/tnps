package th.co.nttdata.tki.controller.cmm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MUser;
import th.co.nttdata.tki.blogic.cmm.CMM_S02Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class CMM_S02Controller extends AbstractBaseController {

	private static final String PATH_URI = "CMM/CMM_S02";
	@Autowired
	private CMM_S02Logic cmmS02Logic;

	@RequestMapping("/CMM_S02")
	public ModelAndView init() {
		MUser MUser = cmmS02Logic.init();

		return new ModelAndView(PATH_URI)
			.addObject("user", MUser);
	}

	@RequestMapping(value="/CMM_S02_save", method=RequestMethod.POST)
	public @ResponseBody MUser save( MUser MUser ) {
		return cmmS02Logic.save( MUser );
	}
}