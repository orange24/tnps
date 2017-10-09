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
import th.co.nttdata.tki.bean.TDailyMCWK;
import th.co.nttdata.tki.blogic.dal.DAL_S05Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;
import th.co.nttdata.tki.dao.MasterDao;

@Controller
public class DAL_S05Controller extends AbstractBaseController {

	private static final String PATH_URI = "DAL/DAL_S05";
	private final String BUTTON = "button";
	private final String BTNBACK = "back";
	private final String CRITERIA = "criteria";
	@Autowired
	private MasterDao masterDao;
	@Autowired
	private CommonController commonController;
	@Autowired
	private DAL_S05Logic dalS05Logic;

	@RequestMapping("/DAL_S05")
	public ModelAndView init(HttpSession session) {
		TDailyMCWK TDailyMCWK = new TDailyMCWK();

		// <!-- Initial. -->
		TDailyMCWK.setShifts( new String[] { "D", "N" } );

		return new ModelAndView(PATH_URI)
			.addObject("dailyMCWK", TDailyMCWK)
			.addObject("reportTypeList", masterDao.selectReportTypeList(new MReportType().setReportCategory(new String[]{"wip"})))
			.addObject("wipMap", commonController.getWIPAll(3))
			.addObject("customerMap", commonController.getCustomerAll());
	}

	@RequestMapping(value="/DAL_S05_delete", method=RequestMethod.POST)
	public ModelAndView delete( TDailyMCWK TDailyMCWK, HttpSession session, HttpServletRequest request ) {
		try {
			dalS05Logic.delete(TDailyMCWK);
		} catch( Exception e ) {
			setSystemError(TDailyMCWK,e);
		}

		return search(TDailyMCWK,session,request);
	}

	@RequestMapping(value="/DAL_S05_search")
	public ModelAndView search( TDailyMCWK TDailyMCWK, HttpSession session, HttpServletRequest request ) {
		String param = request.getParameter(BUTTON);
		if(param != null && param.equals(BTNBACK) && session.getAttribute(CRITERIA)!= null){
			TDailyMCWK = (TDailyMCWK)session.getAttribute(CRITERIA);
			session.removeAttribute(CRITERIA);
			TDailyMCWK.getInfos().clear();
		}				
		session.setAttribute(CRITERIA, TDailyMCWK);
		TDailyMCWK = dalS05Logic.search(TDailyMCWK);

		// <!-- Assigning: Yesterday. -->
		Calendar calendar = new GregorianCalendar(Locale.US);
		calendar.add( Calendar.DAY_OF_YEAR, -1 );
		calendar = DateUtils.truncate(calendar, Calendar.DATE);

		return new ModelAndView(PATH_URI)
			.addObject("dailyMCWK", TDailyMCWK)
			.addObject("reportTypeList", masterDao.selectReportTypeList(new MReportType().setReportCategory(new String[]{"wip"})))
			.addObject("wipMap", commonController.getWIPAll(3))
			.addObject("customerMap", commonController.getCustomerAll())
			.addObject("minDate", calendar.getTime());
	}
	
	@RequestMapping(value="/DAL_S05_export" , method=RequestMethod.POST)
	public ModelAndView export(TDailyMCWK TDailyMCWK){
		return new ModelAndView("DAL_R02ExcelView")
		.addObject("detailList", dalS05Logic.exportDAL_R02(TDailyMCWK))
		.addObject("reasonList", dalS05Logic.getreasonList(TDailyMCWK));
	}
	
	@RequestMapping(value="/DAL_R05_export_count", method=RequestMethod.GET)
	public @ResponseBody Map<String,Integer> count( TDailyMCWK TDailyMCWK ) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		Integer count = dalS05Logic.countDAL_R02();
		TDailyMCWK t = new TDailyMCWK();
		t = dalS05Logic.exportDAL_R02(TDailyMCWK);
		Integer size = t.getDailyMCWKList().size();// Data of query export DAL_R05
		
		Map<String,Integer> map = new LinkedHashMap<String,Integer>();
		map.put("count", count == null ? 0 : count);
		map.put("maxRecord",maxRecord);
		map.put("size",size);
		
		return map;
	}
}