package th.co.nttdata.tki.controller.adm;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MRole;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.blogic.adm.ROL_S01Logic;
import th.co.nttdata.tki.blogic.adm.ROL_S02Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class ROL_S02Controller extends AbstractBaseController {

	private static final String PATH_URI = "ADM/ROL_S02";
	private static final String PATH_URI_S01 = "ADM/ROL_S01";
	private final String CRITERIA = "criteria";
	
	@Autowired
	ROL_S02Logic rolS02Logic;
	@Autowired
	ROL_S01Logic rolS01Logic;

	@RequestMapping("/ROL_S02")
	public ModelAndView init() {
        return new ModelAndView(PATH_URI)
        .addObject("mRole", new MRole())
        .addObject("menu", rolS02Logic.getRolMenu(new MRole()))
        .addObject("command", rolS02Logic.getRolCommand(new MRole()));
    }
	
	@RequestMapping("/ROL_S02_save")
	public ModelAndView save(MRole MRole) {
		
		String result = "";
		
		try {
			result = rolS02Logic.save(MRole);
			MRole = rolS02Logic.search(MRole);
			setSaveInfo(MRole);
		} catch (Exception e) {
			setSystemError(MRole,e);
		}
		
		return new ModelAndView(PATH_URI)
		.addObject("mRole", MRole)
		.addObject("menu", rolS02Logic.getRolMenu(MRole))
		.addObject("command", rolS02Logic.getRolCommand(MRole))
		.addObject("result", result);
	}
	
	@RequestMapping("/ROL_S02_edit")
	public ModelAndView edit(MRole MRole) {
		return new ModelAndView(PATH_URI)
		.addObject("mRole", rolS02Logic.search(MRole))
		.addObject("menu", rolS02Logic.getRolMenu(MRole))
		.addObject("command", rolS02Logic.getRolCommand(MRole));
	}
	
	@RequestMapping("/ROL_S02_delete")
	public ModelAndView delete(MRole MRole,HttpSession session){
		try {
			rolS01Logic.delete(MRole);
			
			if(session.getAttribute(CRITERIA)!=null){
				MRole = (MRole)session.getAttribute(CRITERIA);
				MRole = rolS01Logic.search(MRole);
			}
			MRole.getInfos().add(new Message("inf.cmm.003", null));
			return new ModelAndView(PATH_URI_S01)
			.addObject("searchCriteria", MRole);
		} catch (Exception e) {
			setSystemError(MRole,e);
			return new ModelAndView(PATH_URI)
			.addObject("mRole", MRole)
			.addObject("menu", rolS02Logic.getRolMenu(MRole))
			.addObject("command", rolS02Logic.getRolCommand(MRole));
		}
	}
	
	@RequestMapping(value="/ROL_S02_check", method=RequestMethod.POST)
	public @ResponseBody boolean check( MRole MRole ) {
		
		boolean result = false;
		
		try {
			result = rolS02Logic.existRoleName(MRole);
		} catch( Exception e ) {
			setSystemError(MRole,e);
		}

		return result;
	}
}
