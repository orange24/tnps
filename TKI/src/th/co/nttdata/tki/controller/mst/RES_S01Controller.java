package th.co.nttdata.tki.controller.mst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.blogic.mst.RES_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class RES_S01Controller extends AbstractBaseController{
	
	private static final String PATH_URI = "MST/RES_S01";
	private final String BUTTON = "button";
	private final String BTNBACK = "back";
	private final String CRITERIA = "criteria";
	
	@Autowired
	private CommonController commonControl;
	
	@Autowired
	RES_S01Logic res_s01Logic;
	
	@RequestMapping("/RES_S01")
	public ModelAndView init(){
		Integer[] reasonTypeCode = {1,2,3};
		MReason mReason = new MReason();
		mReason.setReasonTypeCode(reasonTypeCode);
		return new ModelAndView(PATH_URI)
		.addObject("mReason",mReason)
		.addObject("wip",commonControl.getWIPAll());
	}
	
	@RequestMapping("/RES_S01_search")
	public ModelAndView search(MReason mReason, HttpSession session, HttpServletRequest request) {
		String param = request.getParameter(BUTTON);
		if(param != null && param.equals(BTNBACK) && session.getAttribute(CRITERIA)!= null){
			mReason = (MReason)session.getAttribute(CRITERIA);
			session.removeAttribute(CRITERIA);
			mReason.getInfos().clear();
		}
		session.setAttribute(CRITERIA, mReason);
		mReason = res_s01Logic.searchResson(mReason);	
		return new ModelAndView(PATH_URI)
		.addObject("mReason",mReason)
		.addObject("wip",commonControl.getWIPAll());
	}
	
	@RequestMapping("/RES_S01_delete")
	public ModelAndView delete(MReason mReason){
		try{
			res_s01Logic.deleteReason(mReason);
			mReason = res_s01Logic.searchResson(mReason);
			mReason.getInfos().clear();
			mReason.getInfos().add(new Message("inf.cmm.003", null));
		}catch(Exception e){
			mReason = res_s01Logic.searchResson(mReason);
			mReason.getErrors().add(new Message("err.res.001", null));
		}
		return new ModelAndView(PATH_URI)
		.addObject("mReason",mReason)
		.addObject("wip",commonControl.getWIPAll());
	}
}
