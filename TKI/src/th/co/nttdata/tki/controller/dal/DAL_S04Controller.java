package th.co.nttdata.tki.controller.dal;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.MReportType;
import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.bean.TDailyWK;
import th.co.nttdata.tki.bean.TDailyWKDetail;
import th.co.nttdata.tki.blogic.dal.DAL_S04Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;
import th.co.nttdata.tki.dao.MasterDao;

@Controller
public class DAL_S04Controller extends AbstractBaseController {

	private static final String PATH_URI = "DAL/DAL_S04";
	@Autowired
	private MasterDao masterDao;
	@Autowired
	private CommonController commonController;
	@Autowired
	private DAL_S04Logic dalS04Logic;

	@RequestMapping("/DAL_S04")
	public ModelAndView init() {
		System.out.println("reason = " + commonController.getReasonMCStopList());
		return new ModelAndView(PATH_URI)
				.addObject("dailyWK", new TDailyWK())
				.addObject(
						"reportTypeList",
						masterDao.selectReportTypeList(new MReportType()
								.setReportCategory(new String[] { "wip" })))
				.addObject("wipMap", commonController.getWIPSel(2))
				.addObject("customerMap", commonController.getCustomerSel())
				.addObject("stopReasonMap", commonController.getReasonMCStopList())
				.addObject("partMap", commonController.getPartNameNo(null, null, null))
				.addObject("minReportDate", settings.getProperty("DAL.reportDate.minDate", "-40d"));
	}

	@RequestMapping("/DAL_S04_edit")
	public ModelAndView edit(TDailyWK TDailyWK) {
		TDailyWK = dalS04Logic.edit(TDailyWK);

		Map<Integer, Map<Integer, String>> partKeySet = new LinkedHashMap<Integer, Map<Integer, String>>();
		for (TDailyWKDetail TDailyWKDetail : TDailyWK.getDailyWKDetailList()) {
			partKeySet.put(TDailyWKDetail.getCustomerId(), commonController
					.getPartNameNo(TDailyWKDetail.getCustomerId(),
							TDailyWK.getWip(), TDailyWK.getReportType()));
		}

		List<MReason> lstMReason = commonController.getReasonNGList(TDailyWK
				.getWip());

		for (TDailyWKDetail dailyWKDetail : TDailyWK.getDailyWKDetailList()) {
			Map<Integer, Integer> ngReasonMap = new LinkedHashMap<Integer, Integer>();
			Map<Integer, Integer> reason = dailyWKDetail.getNgReasonMap();
			dailyWKDetail.setNgReasonMap(ngReasonMap);
			for (MReason MReason : lstMReason) {
				Integer key = MReason.getReasonId();
				if (reason != null && reason.containsKey(key)) {
					ngReasonMap.put(key, reason.get(key));
				} else {
					ngReasonMap.put(key, null);
				}
			}
		}

		// <!-- Assigning: Yesterday. -->
		Calendar calendar = new GregorianCalendar(Locale.US);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		calendar = DateUtils.truncate(calendar, Calendar.DATE);

		return new ModelAndView(PATH_URI)
				.addObject("dailyWK", TDailyWK)
				.addObject("partKeySet", partKeySet)
				.addObject(
						"reportTypeList",
						masterDao.selectReportTypeList(new MReportType()
								.setReportCategory(new String[] { "wip" })))
				.addObject("wipMap", commonController.getWIPSel(2))
				.addObject("customerMap", commonController.getCustomerSel())
				.addObject("stopReasonMap", commonController.getReasonMCStopList())
				.addObject("reasonNGList", commonController.getReasonNGList(TDailyWK.getWip()))
				.addObject("minDate", calendar.getTime());
	}

	@RequestMapping(value = "/DAL_S04_check", method = RequestMethod.POST)
	public @ResponseBody
	TDailyWK check(TDailyWK TDailyWK) {
		try {
			TDailyWK = dalS04Logic.check(TDailyWK);
		} catch (Exception e) {
			setSystemError(TDailyWK, e);
		}

		return TDailyWK;
	}

	@RequestMapping(value = "/DAL_S04_delete", method = RequestMethod.GET)
	public ModelAndView delete(TDailyWK TDailyWK) {
		try {
			dalS04Logic.delete(TDailyWK);
		} catch (Exception e) {
			setSystemError(TDailyWK, e);
		}

		return new ModelAndView(PATH_URI)
				.addObject("dailyWK", TDailyWK)
				.addObject(
						"reportTypeList",
						masterDao.selectReportTypeList(new MReportType()
								.setReportCategory(new String[] { "wip" })))
				.addObject("wipMap", commonController.getWIPSel(2))
				.addObject("customerMap", commonController.getCustomerSel())
				.addObject("partMap", commonController.getPartNameNo(null, null, null));
	}

	@RequestMapping(value = "/DAL_S04_save", method = RequestMethod.POST)
	public @ResponseBody
	TDailyWK save(TDailyWK TDailyWK) {
		try {
			dalS04Logic.save(TDailyWK);
		} catch (Exception e) {
			setSystemError(TDailyWK, e);
		}

		return TDailyWK;
	}

	@RequestMapping(value = "/DAL_S04_workOrderNoList")
	public @ResponseBody
	List<Map<String, Object>> workOrderNoList(MWorkOrder MWorkOrder) {

		List<Map<String, Object>> workOrderList = new LinkedList<Map<String, Object>>();
		for (MWorkOrder obj : dalS04Logic.getWorkOrderNoList(MWorkOrder)) {
			Map<String, Object> workOrder = new LinkedHashMap<String, Object>();

			workOrder.put("label", obj.getWorkOrderNo());
			workOrder.put("customerId", obj.getCustomerId());
			workOrder.put("partId", obj.getPartId());
			workOrder.put("workOrderNo", obj.getWorkOrderNo());
			workOrder.put("workOrderQty", obj.getWorkOrderQty());
			workOrderList.add(workOrder);
		}

		return workOrderList;
	}

	@RequestMapping(value = "/DAL_S04_getPartNameNo", method = RequestMethod.GET)
	public @ResponseBody
	Map<Integer, String> getPartNameNo(@RequestParam Integer customerId,
			@RequestParam String wip, Integer reportType) {
		if (reportType != null && reportType != 1)
			wip = "";
		List<MPart> parts = dalS04Logic.getPartNo(customerId, wip);

		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(Integer.MAX_VALUE, "-- Select Part --");
		for (MPart MPart : parts)
			map.put(MPart.getPartId(),
					MPart.getPartName() + " : " + MPart.getPartNo());
		return map;
	}
}