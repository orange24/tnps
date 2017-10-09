package th.co.nttdata.tki.controller.adm;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MRole;
import th.co.nttdata.tki.bean.MUser;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.blogic.adm.USR_S01Logic;
import th.co.nttdata.tki.blogic.adm.USR_S02Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class USR_S02Controller extends AbstractBaseController {

	private static final String PATH_URI = "ADM/USR_S02";
	private static final String PATH_URI_S01 = "ADM/USR_S01";
	private final String CRITERIA = "criteria";
	private MUser currentUser;
	
	@Autowired
	public CommonController commonController;
	@Autowired
	private USR_S02Logic usr_S02Logic;
	@Autowired
	private USR_S01Logic usr_S01Logic;

	// <!-- Combobox Role -->
	public @ResponseBody Map<Integer,String> getRole() {
		List<MRole> mrList = usr_S02Logic.getComboBox();
		Map<Integer,String> map = new LinkedHashMap<Integer,String>();
		map.put(Integer.MIN_VALUE, "-- Select Role --");
		for( MRole mr : mrList )
			map.put(mr.getRoleId(), mr.getRoleName());
		return map;
	}
	
	@RequestMapping("/USR_S02")
	public ModelAndView init() {
		currentUser = usr_S02Logic.getCurrentUser();
		return new ModelAndView(PATH_URI)
        .addObject("mUser", new MUser())
        .addObject("wipMap", commonController.getWIPSel())
        .addObject("roleMap", getRole())
        .addObject("acessWip",  usr_S02Logic.getAcessWip(new MUser()))
        .addObject("customerMap", usr_S02Logic.getCustomer(new MUser()))
        .addObject("currentUser", currentUser);
    }
	
	@RequestMapping("/USR_S02_save")
	public ModelAndView save(MUser MUser){
		
		String result = "";
		
		try {
			result = usr_S02Logic.save(MUser);
			MUser = usr_S02Logic.getUser(MUser);
			setSaveInfo(MUser);
		} catch (Exception e) {
			setSystemError(MUser,e);
		}
		
		return new ModelAndView(PATH_URI)
		.addObject("mUser", MUser)
		.addObject("result", result)
        .addObject("wipMap", commonController.getWIPSel())
        .addObject("roleMap", getRole())
        .addObject("acessWip",  usr_S02Logic.getAcessWip(MUser))
        .addObject("customerMap", usr_S02Logic.getCustomer(MUser))
        .addObject("currentUser", currentUser);
	}
	
	@RequestMapping("/USR_S02_edit")
	public ModelAndView edit(MUser MUser) {
		currentUser = usr_S02Logic.getCurrentUser();
		return new ModelAndView(PATH_URI)
		.addObject("mUser", usr_S02Logic.getUser(MUser))
        .addObject("wipMap", commonController.getWIPSel())
        .addObject("roleMap", getRole())
        .addObject("acessWip", usr_S02Logic.getAcessWip(MUser) )
        .addObject("customerMap", usr_S02Logic.getCustomer(MUser))
        .addObject("currentUser", currentUser);
	}
	
	@RequestMapping("USR_S02_delete")
	public ModelAndView delete(MUser MUser, HttpSession session){
		
		try {
			
			usr_S02Logic.delete(MUser);
			
			if(session.getAttribute(CRITERIA)!=null){
				MUser = (MUser)session.getAttribute(CRITERIA);
				MUser = usr_S01Logic.search(MUser);
			}
			MUser.getInfos().clear();
			MUser.getInfos().add(new Message("inf.cmm.003", null));
			return new ModelAndView(PATH_URI_S01)
			.addObject("searchCriteria",MUser)
			.addObject("wipMap", commonController.getWIPAll())
			.addObject("roleMap", getRole())
			.addObject("currentUser", currentUser);
		} catch (Exception e) {
			setSystemError(MUser,e);
			return new ModelAndView(PATH_URI)
			.addObject("mUser", MUser)
			.addObject("wipMap", commonController.getWIPSel())
			.addObject("roleMap", getRole())
			.addObject("acessWip", usr_S02Logic.getAcessWip(MUser) )
			.addObject("customerMap", usr_S02Logic.getCustomer(MUser))
			.addObject("currentUser", currentUser);
		}
		

	}
	
	@RequestMapping(value="/USR_S02_check", method=RequestMethod.POST)
	public @ResponseBody MUser check( MUser MUser ) {
		
		try {
			MUser = usr_S02Logic.getUser(MUser);
			if (MUser == null) {
				MUser = new MUser();
			}
		} catch( Exception e ) {
			setSystemError(MUser,e);
		}

		return MUser;
	}

}
