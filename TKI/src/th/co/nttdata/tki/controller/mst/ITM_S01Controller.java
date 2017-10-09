package th.co.nttdata.tki.controller.mst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.blogic.mst.ITM_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class ITM_S01Controller extends AbstractBaseController {

	private static final String PATH_URI = "MST/ITM_S01";
	private static final String SESSION  = "ITM_S03_SEARCH"; 
	
	@Autowired
	private CommonController commonController;	
	@Autowired 
	private ITM_S01Logic itm_S01Logic;

	@RequestMapping("/ITM_S01")
	public ModelAndView init(HttpSession session) {
		session.removeAttribute(SESSION);
		return new ModelAndView(PATH_URI)
			.addObject("mPart", new MPart())
			.addObject("customerMap", commonController.getCustomerAll());
	}

	@RequestMapping("/ITM_S01_search")
	public ModelAndView search(MPart part, HttpSession session, HttpServletRequest request) {
		if (request.getParameter("back") != null && session.getAttribute(SESSION) != null) {
			part = (MPart) session.getAttribute(SESSION);
		} else {
			session.setAttribute(SESSION, part);
		}
		
		try {
			part = itm_S01Logic.query(part);
		} catch (Exception e) {
			setSystemError(part,e);
			e.printStackTrace();
		}
		return new ModelAndView(PATH_URI)
			.addObject("mPart", part)
			.addObject("customerMap", commonController.getCustomerAll());
	}
	@RequestMapping("/ITM_S01_delete")
	public ModelAndView delete(MPart part, HttpSession session, HttpServletRequest request){
		try{
			itm_S01Logic.delete(part);
			part.getInfos().clear();
			part.getInfos().add(new Message("inf.cmm.003", null));
		}catch(Exception e){
			part.getErrors().add(new Message("err.cmm.007", null));
		}
		return search(part,session,request);
	}
	@RequestMapping("/ITM_S01_deleteCustomer")
	public ModelAndView deleteCustomer(MPart part, HttpSession session, HttpServletRequest request){
		try{
			itm_S01Logic.deleteCustomer(part);
			part.getInfos().clear();
			part.getInfos().add(new Message("inf.cmm.003", null));
		}catch(Exception e){
			part.getErrors().add(new Message("err.cmm.007", null));
		}
		return search(part,session,request);
	}
}