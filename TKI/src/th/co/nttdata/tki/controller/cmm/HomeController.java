package th.co.nttdata.tki.controller.cmm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.blogic.cmm.CommonLogic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class HomeController extends AbstractBaseController {

	@Autowired
	public CommonLogic commonLogic;
	
	@RequestMapping("index")
	public String index() {
		return "index";
	}

	@RequestMapping("menu")
	public ModelAndView menu() {
		return new ModelAndView("menu")
			.addObject("menuList", commonLogic.getMenuByLoginUser());
	}

	@RequestMapping("top")
	public String top() {
		return "top";
	}

	@RequestMapping("right")
	public String right() {
		return "right";
	}

	@RequestMapping("main")
	public String main() {
		return "main";
	}
}