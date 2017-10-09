package th.co.nttdata.tki.controller.adm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MRole;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.blogic.adm.ROL_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class ROL_S01Controller extends AbstractBaseController {

	private static final String PATH_URI = "ADM/ROL_S01";
	private final String CRITERIA = "criteria";
	private final String BUTTON = "button";
	private final String BTNBACK = "back";
	
	@Autowired
	public ROL_S01Logic rol_S01Logic;

	@RequestMapping("/ROL_S01")
	public ModelAndView init() {
        return new ModelAndView(PATH_URI)
        .addObject("searchCriteria", new MRole());
    }
	
	@RequestMapping("/ROL_S01_search")
	public ModelAndView search(MRole MRole,HttpSession session,HttpServletRequest request){
		
		String param = request.getParameter(BUTTON);
		
		if(param != null && param.equals(BTNBACK) && session.getAttribute(CRITERIA)!=null){
			MRole = (MRole)session.getAttribute(CRITERIA);
			session.removeAttribute(CRITERIA);
		}
		
		session.setAttribute(CRITERIA, MRole);
		MRole = rol_S01Logic.search(MRole);
		
		return new ModelAndView(PATH_URI)
		.addObject("searchCriteria", MRole);
	}
	
	@RequestMapping("/ROL_S01_delete.html")
	public ModelAndView delete(MRole MRole) {
		
		try {
			rol_S01Logic.delete(MRole);
			MRole.getInfos().add(new Message("inf.cmm.003", null));
		} catch (Exception e) {
			setSystemError(MRole,e);
		}
		MRole = rol_S01Logic.search(MRole);
		
		return new ModelAndView(PATH_URI)
		.addObject("searchCriteria", MRole);

	}
	
}
