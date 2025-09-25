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
import th.co.nttdata.tki.bean.TDailyWK;
import th.co.nttdata.tki.blogic.dal.DAL_S03Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;
import th.co.nttdata.tki.dao.MasterDao;

@Controller
public class DAL_S03Controller extends AbstractBaseController {

	private static final String PATH_URI = "DAL/DAL_S03";
	private static final Integer MAXRECORD = 15000;
	private final String BUTTON = "button";
	private final String BTNBACK = "back";
	private final String CRITERIA = "criteria";
	@Autowired
	private MasterDao masterDao;
	@Autowired
	private CommonController commonController;
	@Autowired
	private DAL_S03Logic dalS03Logic;

	@RequestMapping("/DAL_S03")
	public ModelAndView init(HttpSession session) {
		session.removeAttribute(CRITERIA);
		TDailyWK TDailyWK = new TDailyWK();
		// <!-- Initial. -->
		TDailyWK.setShifts( new String[] { "D", "N" } );

		return new ModelAndView(PATH_URI)
			.addObject("dailyWK", TDailyWK)
			.addObject("reportTypeList", masterDao.selectReportTypeList(new MReportType().setReportCategory(new String[]{"wip"})))
			.addObject("wipMap", commonController.getWIPAll(2));
	}

	@RequestMapping(value="/DAL_S03_delete", method=RequestMethod.POST)
	public ModelAndView delete( TDailyWK TDailyWK, HttpSession session, HttpServletRequest request ) {
		try {
			dalS03Logic.delete(TDailyWK);
		} catch( Exception e ) {
			setSystemError(TDailyWK,e);
		}

		return search(TDailyWK,session,request);
	}

	@RequestMapping(value="/DAL_S03_search")
	public ModelAndView search( TDailyWK TDailyWK , HttpSession session, HttpServletRequest request) {
		String param = request.getParameter(BUTTON);
		if(param != null && param.equals(BTNBACK) && session.getAttribute(CRITERIA)!= null){
			TDailyWK = (TDailyWK)session.getAttribute(CRITERIA);
			session.removeAttribute(CRITERIA);
			TDailyWK.getInfos().clear();
		}				
		session.setAttribute(CRITERIA, TDailyWK);
		TDailyWK = dalS03Logic.search(TDailyWK);

		// <!-- Assigning: Yesterday. -->
		Calendar calendar = new GregorianCalendar(Locale.US);
		calendar.add( Calendar.DAY_OF_YEAR, -30 );
		calendar = DateUtils.truncate(calendar, Calendar.DATE);

		return new ModelAndView(PATH_URI)
			.addObject("dailyWK", TDailyWK)
			.addObject("wipMap", commonController.getWIPAll(2))
			.addObject("reportTypeList", masterDao.selectReportTypeList(new MReportType().setReportCategory(new String[]{"wip"})))
			.addObject("minDate", calendar.getTime());
	}

	@RequestMapping(value="/DAL_S03_export", method=RequestMethod.POST)
	public ModelAndView export( TDailyWK TDailyWK ) {
		TDailyWK.setMaxRecord(MAXRECORD);

		return new ModelAndView("DAL_R03ExcelView")
		.addObject("dailyWK", dalS03Logic.exportDAL_R03(TDailyWK))
		.addObject("reasonList", dalS03Logic.getreasonList(TDailyWK));
	}
	
	@RequestMapping(value="/DAL_R02_export_count", method=RequestMethod.GET)
	public @ResponseBody Map<String,Integer> count( TDailyWK TDailyWK ) {
		
		TDailyWK.setMaxRecord(MAXRECORD);
		Integer maxRecord = MAXRECORD;
		Integer count = dalS03Logic.countDAL_R03();
		TDailyWK t = new TDailyWK();
		t =	dalS03Logic.exportDAL_R03(TDailyWK);
		Integer size = t.getDailyWKDetailList().size();// Data of query export DAL_R03
		
		Map<String,Integer> map = new LinkedHashMap<String,Integer>();
		map.put("count", count == null ? 0 : count);
		map.put("maxRecord",maxRecord);
		map.put("size",size);
		
		return map;
	}
}