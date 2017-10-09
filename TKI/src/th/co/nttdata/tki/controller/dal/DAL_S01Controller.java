package th.co.nttdata.tki.controller.dal;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MReportType;
import th.co.nttdata.tki.bean.TDailyMC;
import th.co.nttdata.tki.blogic.dal.DAL_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;
import th.co.nttdata.tki.dao.MasterDao;

@Controller
public class DAL_S01Controller extends AbstractBaseController {

	private static final String PATH_URI = "DAL/DAL_S01";
	private final String BUTTON = "button";
	private final String BTNBACK = "back";
	private final String CRITERIA = "criteria";
	@Autowired
	private MasterDao masterDao;
	@Autowired
	private CommonController commonController;
	@Autowired
	private DAL_S01Logic dalS01Logic;

	@RequestMapping("/DAL_S01")
	public ModelAndView init(HttpSession session) {
		session.removeAttribute(CRITERIA);
		TDailyMC TDailyMC = new TDailyMC();
		return new ModelAndView(PATH_URI)
			.addObject("dailyMC", TDailyMC)
			.addObject("reportTypeList", masterDao.selectReportTypeList(new MReportType().setReportCategory(new String[]{"wip"})))
			.addObject("wipMap", commonController.getWIPAll(1));
	}

	@RequestMapping(value="/DAL_S01_delete", method=RequestMethod.POST)
	public ModelAndView delete( TDailyMC TDailyMC, HttpSession session, HttpServletRequest request ) {
		try {
			dalS01Logic.delete(TDailyMC);
		} catch( Exception e ) {
			setSystemError(TDailyMC,e);
		}

		return search(TDailyMC,session,request);
	}

	@RequestMapping(value="/DAL_S01_search")
	public ModelAndView search( TDailyMC TDailyMC, HttpSession session, HttpServletRequest request ) {
		String param = request.getParameter(BUTTON);
		if(param != null && param.equals(BTNBACK) && session.getAttribute(CRITERIA)!= null){
			TDailyMC = (TDailyMC)session.getAttribute(CRITERIA);
			session.removeAttribute(CRITERIA);
			TDailyMC.getInfos().clear();
		}				
		session.setAttribute(CRITERIA, TDailyMC);
		TDailyMC = dalS01Logic.search(TDailyMC);

		// <!-- Assigning: Yesterday. -->
		Calendar calendar = new GregorianCalendar(Locale.US);
		calendar.add( Calendar.DAY_OF_YEAR, -1 );
		calendar = DateUtils.truncate(calendar, Calendar.DATE);

		return new ModelAndView(PATH_URI)
			.addObject("dailyMC", TDailyMC)
			.addObject("reportTypeList", masterDao.selectReportTypeList(new MReportType().setReportCategory(new String[]{"wip"})))
			.addObject("wipMap", commonController.getWIPAll(1))
			.addObject("minDate", calendar.getTime());
	}

	@RequestMapping(value="/DAL_R01_export", method=RequestMethod.POST)
	public ModelAndView export( TDailyMC TDailyMC ) {

		return new ModelAndView("DAL_R01ExcelView")
		.addObject("dailyMC", dalS01Logic.exportDAL_R01(TDailyMC))
		.addObject("reasonList", dalS01Logic.getreasonList(TDailyMC));
	}
	
	@RequestMapping(value="/DAL_R01_export_count", method=RequestMethod.GET)
	public @ResponseBody Map<String,Integer> count(TDailyMC TDailyMC) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		Integer count = dalS01Logic.countDAL_R01();
		TDailyMC t = new TDailyMC();
		t = dalS01Logic.exportDAL_R01(TDailyMC);
		Integer size = t.getDailyMCList().size();// Data of query export DAL_R01
		
		Map<String,Integer> map = new LinkedHashMap<String,Integer>();
		map.put("count", count == null ? 0 : count);
		map.put("maxRecord",maxRecord);
		map.put("size",size);
		
		return map;
	}
}