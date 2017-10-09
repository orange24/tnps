package th.co.nttdata.tki.controller.adm;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MRole;
import th.co.nttdata.tki.bean.MUser;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.blogic.adm.USR_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class USR_S01Controller extends AbstractBaseController {

	private static final String PATH_URI = "ADM/USR_S01";
	private final String BUTTON = "button";
	private final String BTNBACK = "back";
	private final String CRITERIA = "criteria";
	private MUser currentUser;
	
	@Autowired
	private CommonController commonControl;
	@Autowired
	private USR_S01Logic usr_S01Logic;
	
	// <!-- Combobox Role -->
	public @ResponseBody Map<Integer,String> getRole() {
		List<MRole> mrList = usr_S01Logic.getComboBox();
		Map<Integer,String> map = new LinkedHashMap<Integer,String>();
		map.put(Integer.MIN_VALUE, "-- All Role --");
		for( MRole mr : mrList )
			map.put(mr.getRoleId(), mr.getRoleName());
		return map;
	}
	
	@RequestMapping("/USR_S01")
	public ModelAndView init(HttpSession session) {
		session.removeAttribute(CRITERIA);
		currentUser = usr_S01Logic.getCurrentUser();
        return new ModelAndView(PATH_URI)
        .addObject("searchCriteria",new MUser())
        .addObject("wipMap", commonControl.getWIPAll())
        .addObject("roleMap", getRole())
        .addObject("currentUser", currentUser);
    }

	@RequestMapping("/USR_S01_search")
	public ModelAndView search( MUser MUser, HttpSession session, HttpServletRequest request ) {
		
		String param = request.getParameter(BUTTON);
		
		if(param != null && param.equals(BTNBACK) && session.getAttribute(CRITERIA)!=null){
			MUser = (MUser)session.getAttribute(CRITERIA);
			session.removeAttribute(CRITERIA);
			MUser.getInfos().clear();
			MUser.getErrors().clear();
		}
		
		session.setAttribute(CRITERIA, MUser);
		MUser = usr_S01Logic.search(MUser);
		
        return new ModelAndView(PATH_URI)
        .addObject("searchCriteria",MUser)
        .addObject("wipMap", commonControl.getWIPAll())
        .addObject("roleMap", getRole())
        .addObject("currentUser", currentUser);
    }
	
	@RequestMapping("/USR_S01_delete")
	public ModelAndView delete(MUser MUser, HttpSession session) {
		
		try {
			usr_S01Logic.delete(MUser);
		} catch (Exception e) {
			setSystemError(MUser,e);
		}
		if(session.getAttribute(CRITERIA)!=null){
			MUser = (MUser)session.getAttribute(CRITERIA);
			MUser = usr_S01Logic.search(MUser);
		}
		MUser.getInfos().clear();
		MUser.getInfos().add(new Message("inf.cmm.003", null));
		
		return new ModelAndView(PATH_URI)
        .addObject("searchCriteria",MUser)
        .addObject("wipMap", commonControl.getWIPAll())
        .addObject("roleMap", getRole())
        .addObject("currentUser", currentUser);
	}
}
