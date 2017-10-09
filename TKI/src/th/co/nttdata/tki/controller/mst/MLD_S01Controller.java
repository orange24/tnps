package th.co.nttdata.tki.controller.mst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MMoldDetail;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.blogic.mst.MLD_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MLD_S01Controller extends AbstractBaseController {

	private static final String PATH = "MST/MLD_S01";
	private final String BUTTON = "button";
	private final String BTNBACK = "back";
	private final String CRITERIA = "criteria";
	
	@Autowired
	CommonController controller;
	@Autowired
	MLD_S01Logic mS01Logic;
	
	@RequestMapping("/MLD_S01")
	public ModelAndView init(MMoldDetail mdDetail){
		Integer[] status = {1,2,3};
		mdDetail.setStatus(status);
		return new ModelAndView(PATH)
		.addObject("custMap", controller.getCustomerAll())
		.addObject("mDetail", mdDetail);
	}
	
	@RequestMapping("/MLD_S01_search")
	public ModelAndView search(MMoldDetail mdDetail, HttpServletRequest request, HttpSession session){
		
		String param = request.getParameter(BUTTON);
		if(param != null && param.equals(BTNBACK) && session.getAttribute(CRITERIA)!=null){
			mdDetail = (MMoldDetail)session.getAttribute(CRITERIA);
			session.removeAttribute(CRITERIA);
			mdDetail.getInfos().clear();
			mdDetail.getErrors().clear();
		}
		
		session.setAttribute(CRITERIA, mdDetail);
		mdDetail = mS01Logic.search(mdDetail);
		
		return new ModelAndView(PATH)
		.addObject("custMap", controller.getCustomerAll())
		.addObject("mDetail", mdDetail);
	}
	
	@RequestMapping("/MLD_S01_delete")
	public ModelAndView delete(MMoldDetail mdDetail, HttpServletRequest request, HttpSession session){
		
		try {
			mS01Logic.delete(mdDetail);
		} catch (Exception e) {
			setSystemError(mdDetail,e);
		}
		if(session.getAttribute(CRITERIA)!=null){
			mdDetail = (MMoldDetail)session.getAttribute(CRITERIA);
			mdDetail = mS01Logic.search(mdDetail);
		}
		mdDetail.getInfos().clear();
		mdDetail.getInfos().add(new Message("inf.cmm.003", null));
		
		return new ModelAndView(PATH)
		.addObject("custMap", controller.getCustomerAll())
		.addObject("mDetail", mdDetail);
	}
	
	@RequestMapping(value="/MLD_R01_export", method=RequestMethod.POST)
	public ModelAndView export( MMoldDetail mdDetail ) {
		
		return new ModelAndView("MLD_R01ExcelView")
		.addObject("moldDetail", mS01Logic.exportMLD_R01(mdDetail));
	}
	
}
