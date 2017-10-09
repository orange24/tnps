package th.co.nttdata.tki.controller.mst;

import java.util.Calendar;
import java.util.Date;
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
import th.co.nttdata.tki.bean.TMoldHistory;
import th.co.nttdata.tki.blogic.mst.MLD_S02Logic;
import th.co.nttdata.tki.blogic.mst.MLD_S03Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MLD_S03Controller extends AbstractBaseController {
	private static final String PATH = "MST/MLD_S03";
	private final String BUTTON = "button";
	private final String BTNBACK = "back";
	private final String CRITERIA = "criteria";
	
	@Autowired
	CommonController commonControl;
	@Autowired
	MLD_S02Logic mld_S02Logic;
	@Autowired
	MLD_S03Logic mld_S03Logic;
	
	@RequestMapping(value="/boxPartAll", method=RequestMethod.GET)
	public @ResponseBody Map<Integer,String> getPartNo( Integer customerId, Integer moldId ) {
		Map<Integer,String> map = new LinkedHashMap<Integer,String>();
		map.put(Integer.MIN_VALUE, "-- All Part --");
		if (customerId == null || customerId == Integer.MIN_VALUE){
			List<MPart> parts = mld_S03Logic.getPartNo( Integer.MIN_VALUE, Integer.MIN_VALUE );
			for( MPart MPart : parts )
				map.put(MPart.getPartId(), MPart.getPartName() +" : "+ MPart.getPartNo());
			return map;
		}
		List<MPart> parts = mld_S03Logic.getPartNo( customerId, moldId );
		for( MPart MPart : parts )
			map.put(MPart.getPartId(), MPart.getPartName() +" : "+ MPart.getPartNo());
		return map;
	}
	
	@RequestMapping(value="/boxMoldNameAll", method=RequestMethod.GET)
	public @ResponseBody Map<Integer,String> getMoldName( Integer customerId, Integer partId){
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(Integer.MIN_VALUE , "-- All Mold Name --");
		if (customerId == null || customerId == Integer.MIN_VALUE){
			List<MMoldDetail> moldLst = mld_S03Logic.getMoldName(Integer.MIN_VALUE,Integer.MIN_VALUE);
			for (MMoldDetail mMoldDetail : moldLst) {
				map.put(mMoldDetail.getMoldId(), mMoldDetail.getMoldName());
			}
			return map;
		}
		List<MMoldDetail> moldLst = mld_S03Logic.getMoldName(customerId,partId);
		for (MMoldDetail mMoldDetail : moldLst) {
			map.put(mMoldDetail.getMoldId(), mMoldDetail.getMoldName());
		}
		return map;
	}
	
	@RequestMapping(value="/txtMoldNo", method=RequestMethod.GET)
	public @ResponseBody Map<String,String> getMoldNo(Integer moldId){
		Map<String, String> map = new LinkedHashMap<String,String>();
		map.put("" , "-- All Mold No. --");
		if (moldId == null || moldId == Integer.MIN_VALUE){
			//List<MMoldDetail> moldLst = mld_S03Logic.getMoldNo(Integer.MIN_VALUE);
			List<MMoldDetail> moldLst = mld_S03Logic.getMoldNoDistinct(Integer.MIN_VALUE);
			for (MMoldDetail mMoldDetail : moldLst) {
				map.put(mMoldDetail.getMoldNo(), mMoldDetail.getMoldNo());
			}
			return map;
		}
		List<MMoldDetail> moldLst = mld_S03Logic.getMoldNoDistinct(moldId);
		//List<MMoldDetail> moldLst = mld_S03Logic.getMoldNo(Integer.MIN_VALUE);
		for (MMoldDetail mMoldDetail : moldLst) {
			map.put(mMoldDetail.getMoldNo(), mMoldDetail.getMoldNo());
		}
		return map;
	}
	
	@RequestMapping("/MLD_S03")
	public ModelAndView init(TMoldHistory mHist){
		return new ModelAndView(PATH)
		.addObject("moldHist", mHist)
		//.addObject("custMap", commonControl.getCustomerSel())
		.addObject("custMap", commonControl.getCustomerAll())
		.addObject("partMap", getPartNo(null, null))
		.addObject("moldName",getMoldName(null,null))
		.addObject("moldNo", getMoldNo( null ));
	}
	
	@RequestMapping("/MLD_S03_search")
	public ModelAndView search(TMoldHistory mHist, HttpServletRequest request, HttpSession session){
		String param = request.getParameter(BUTTON);
		if(param != null && param.equals(BTNBACK) && session.getAttribute(CRITERIA)!=null){
			mHist = (TMoldHistory)session.getAttribute(CRITERIA);
			session.removeAttribute(CRITERIA);
			mHist.getInfos().clear();
			mHist.getErrors().clear();
		}
		session.setAttribute(CRITERIA, mHist);
		String page = request.getParameter("page");
		if (page != null && page.equals("viewHist")) {
			Calendar firstCal = Calendar.getInstance();  
			Calendar lastCal = Calendar.getInstance();  
			firstCal.setTime(new Date());  
			lastCal.setTime(new Date());  
			firstCal.set(Calendar.DAY_OF_MONTH, firstCal.getMinimum(Calendar.DAY_OF_MONTH));
			lastCal.set(Calendar.DAY_OF_MONTH, lastCal.getActualMaximum(Calendar.DAY_OF_MONTH));
			mHist.setStartDateHist(firstCal.getTime());
			mHist.setEndDateHist(lastCal.getTime());
		}
		mHist = mld_S03Logic.search(mHist);
		return new ModelAndView(PATH)
		.addObject("moldHist", mHist)
		.addObject("custMap", commonControl.getCustomerAll())
		.addObject("partMap", getPartNo(mHist.getCustomerId(), mHist.getMoldId()))
		.addObject("moldName",getMoldName(mHist.getCustomerId(), mHist.getPartId()))
		.addObject("moldNo", getMoldNo(mHist.getMoldId()));
	}
}
