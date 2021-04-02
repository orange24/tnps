package th.co.nttdata.tki.controller.mst;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MMoldDetail;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.blogic.mst.MLD_S01Logic;
import th.co.nttdata.tki.blogic.mst.MLD_S02Logic;
import th.co.nttdata.tki.blogic.mst.MLD_S03Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MLD_S02Controller extends AbstractBaseController {
	
	private static final String PATH = "MST/MLD_S02";
	private static final String PATH_S01 = "MST/MLD_S01";
	private final String CRITERIA = "criteria";
	
	@Autowired
	CommonController controller;
	@Autowired
	MLD_S01Logic mld_S01Logic;
	@Autowired
	MLD_S02Logic mld_S02Logic;
	@Autowired
	MLD_S03Logic mld_S03Logic;
	
	@RequestMapping(value="/moldNameSel", method=RequestMethod.GET)
	public @ResponseBody Map<Integer,String> getMoldNameSel(){
		List<MMoldDetail> moldLst = mld_S02Logic.getMoldName();
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(Integer.MIN_VALUE , "-- Add New Mold --");
		for (MMoldDetail mMoldDetail : moldLst) {
			map.put(mMoldDetail.getMoldId(), mMoldDetail.getMoldName());
		}
		return map;
	} 
	
	@RequestMapping(value="/partMapping", method=RequestMethod.GET)
	public @ResponseBody Map<Integer,String> getPartNo( Integer customerId, Integer moldId ) {
		Map<Integer,String> map = new LinkedHashMap<Integer,String>();
		List<MPart> parts = mld_S03Logic.getPartNo( customerId, moldId );
		for( MPart MPart : parts )
			map.put(MPart.getPartId(), MPart.getPartName() +" : "+ MPart.getPartNo());
		return map;
	}
	
	@RequestMapping("/MLD_S02")
	public ModelAndView init(MMoldDetail mDetail) {
		return new ModelAndView(PATH)
		.addObject("mDetail", mDetail)
		.addObject("custMap", controller.getCustomerSel())
		.addObject("moldNameMap", getMoldNameSel());
	}
	
	@RequestMapping("/MLD_S02_add")
	public @ResponseBody MMoldDetail add(MMoldDetail mDetail) {
		try {
			mld_S02Logic.add(mDetail);
			setSaveInfo(mDetail);
		} catch (Exception e) {
			setSystemError(mDetail, e);
		}
		return mDetail;
	}
	
	@RequestMapping("/checkRelateMold")
	public @ResponseBody boolean checkRelateMold(MMoldDetail mMoldDetail){
		return mld_S02Logic.checkRelateMold(mMoldDetail);
	}
	
	@RequestMapping("/MLD_S02_edit")
	public @ResponseBody MMoldDetail edit(MMoldDetail mDetail) {
		try {
			mld_S02Logic.edit(mDetail);
			mDetail = mld_S02Logic.getMoldDetail(mDetail);
			setSaveInfo(mDetail);
		} catch (Exception e) {
			setSystemError(mDetail,e);
		}
		return mDetail;
	}
	
	@RequestMapping("/MLD_S02_delete")
	public ModelAndView delete(MMoldDetail mDetail, HttpSession session) {
		try {
			mld_S02Logic.delete(mDetail);
		} catch (Exception e) {
			setSystemError(mDetail,e);
			return new ModelAndView(PATH)
			.addObject("mDetail", mDetail)
			.addObject("custMap", controller.getCustomerSel());
		}
		if(session.getAttribute(CRITERIA)!=null){
			mDetail = (MMoldDetail)session.getAttribute(CRITERIA);
			mDetail = mld_S01Logic.search(mDetail);
		}
		mDetail.getInfos().clear();
		mDetail.getInfos().add(new Message("inf.cmm.003", null));
		return new ModelAndView(PATH_S01)
		.addObject("custMap", controller.getCustomerSel())
		.addObject("mDetail", mDetail);
	}
	
	@RequestMapping("/checkMoldName")
	public @ResponseBody int checkMoldName(MMoldDetail mDetail){
		return mld_S02Logic.checkMoldName(mDetail);
	}
	
	@RequestMapping("/checkMoldNo")
	public @ResponseBody int checkMoldNo(MMoldDetail mDetail){
		return mld_S02Logic.checkDupMoldNo(mDetail);
	}
	
	@RequestMapping("/checkMoldNoEdit")
	public @ResponseBody int checkMoldNoEdit(MMoldDetail mDetail){
		return mld_S02Logic.checkDupMoldNoEdit(mDetail);
	}
	
	@RequestMapping("/MLD_S02_edit_page")
	public ModelAndView editPage(MMoldDetail mDetail,HttpServletRequest request){
		mDetail.setMoldId((request.getParameter("moldId")==null)?0:Integer.parseInt(request.getParameter("moldId")));
		mDetail.setMoldNo((request.getParameter("moldNo")==null)?"":request.getParameter("moldNo"));
		mDetail = mld_S02Logic.getMoldDetail(mDetail);
		System.out.println("mDetail.getMoldOrderSheet = "+mDetail.getMoldOrderSheet());
		String action = request.getParameter("action");
		return new ModelAndView(PATH)
		.addObject("mDetail", mDetail)
		.addObject("custMap", controller.getCustomerSel())
		.addObject("moldNameMap", getMoldNameSel())
		.addObject("action", action);
	}
	
	@RequestMapping("/MLD_S02_search")
	public @ResponseBody Map<Integer,Map<Integer,String>> search(MMoldDetail mDetail) {
		Map<Integer,Map<Integer,String>> partKeySet = new LinkedHashMap<Integer,Map<Integer,String>>();
		Map<Integer,String> part = new LinkedHashMap<Integer,String>();
		mDetail.setmPartList(mld_S02Logic.searchPart(mDetail));
		
		for( MPart mPart :  mDetail.getmPartList()) {
			part.put(mPart.getPartId(), mPart.getPartName() +" : "+ mPart.getPartNo());
			partKeySet.put(mPart.getCustomerId(), part);
		}
		return partKeySet;
	}
}
