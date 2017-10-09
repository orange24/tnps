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
import th.co.nttdata.tki.bean.TDailyMCWK;
import th.co.nttdata.tki.bean.TDailyMCWKDetail;
import th.co.nttdata.tki.blogic.dal.DAL_S06Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;
import th.co.nttdata.tki.dao.MasterDao;

@Controller
public class DAL_S06Controller extends AbstractBaseController {

	private static final String PATH_URI = "DAL/DAL_S06";
	@Autowired
	private MasterDao masterDao;
	@Autowired
	private CommonController commonController;
	@Autowired
	private DAL_S06Logic dalS06Logic;

	@RequestMapping("/DAL_S06")
	public ModelAndView init() {
		return new ModelAndView(PATH_URI)
				.addObject("dailyMCWK", new TDailyMCWK())
				.addObject(
						"reportTypeList",
						masterDao.selectReportTypeList(new MReportType()
								.setReportCategory(new String[] { "wip" })))
				.addObject("wipMap", commonController.getWIPSel(3))
				.addObject("customerMap", commonController.getCustomerSel())
				.addObject("machineMap",
						commonController.getMachineNoActive(null))
				.addObject("minReportDate",
						settings.getProperty("DAL.reportDate.minDate", "-40d"));
	}

	@RequestMapping("/DAL_S06_edit")
	public ModelAndView edit(TDailyMCWK TDailyMCWK) {
		TDailyMCWK = dalS06Logic.edit(TDailyMCWK);

		Map<Integer, Integer> reasonInCatKeySet = new LinkedHashMap<Integer, Integer>();
		Map<Integer, Map<Integer, String>> reasonCatKeySet = new LinkedHashMap<Integer, Map<Integer, String>>();
		Map<Integer, Map<Integer, String>> partKeySet = new LinkedHashMap<Integer, Map<Integer, String>>();

		// <!-- Checking: if no 'stop machine'. -->
		Map<Integer, Integer> stopMap = TDailyMCWK.getStopMCList().size() < 1 ? null
				: TDailyMCWK.getStopMCList().get(0);
		if (stopMap != null && stopMap.size() > 0)
			for (Map.Entry<Integer, Integer> entry : stopMap.entrySet()) {
				MReason MReason = new MReason();
				MReason.setReasonId(entry.getKey());
				MReason = commonController.getReason(MReason);

				// <!-- Providing the data. -->
				Integer reasonId = entry.getKey();
				Integer parentId = MReason.getParentReasonId();
				Map<Integer, String> reasonInCatMap = commonController
						.getReasonInCat(parentId, TDailyMCWK.getWip());

				reasonInCatKeySet.put(reasonId, parentId);
				reasonCatKeySet.put(reasonId, reasonInCatMap);
			}
		for (TDailyMCWKDetail TDailyMCWKDetail : TDailyMCWK.getDetailList()) {
			partKeySet.put(
					TDailyMCWKDetail.getCustomerId(),
					commonController.getPartNameNo(
							TDailyMCWKDetail.getCustomerId(),
							TDailyMCWK.getWip(), TDailyMCWK.getReportType()));
		}

		// <!-- Assigning: Yesterday. -->
		Calendar calendar = new GregorianCalendar(Locale.US);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		calendar = DateUtils.truncate(calendar, Calendar.DATE);

		return new ModelAndView(PATH_URI)
				.addObject("dailyMCWK", TDailyMCWK)
				.addObject("partKeySet", partKeySet)
				.addObject("reasonCatKeySet", reasonCatKeySet)
				.addObject("reasonInCatKeySet", reasonInCatKeySet)
				.addObject(
						"reportTypeList",
						masterDao.selectReportTypeList(new MReportType()
								.setReportCategory(new String[] { "wip" })))
				.addObject("wipMap", commonController.getWIPSel(3))
				.addObject("customerMap", commonController.getCustomerSel())
				.addObject("machineMap",
						commonController.getMachineNo(TDailyMCWK.getWip()))
				.addObject(
						"partMap",
						commonController.getPartNameNo(
								TDailyMCWK.getCustomerId(),
								TDailyMCWK.getWip(), TDailyMCWK.getReportType()))
				.addObject("reasonCatMap",
						commonController.getReasonCat(TDailyMCWK.getWip()))
				.addObject("reasonNGList",
						commonController.getReasonNGList(TDailyMCWK.getWip()))
				.addObject("minDate", calendar.getTime());
	}

	@RequestMapping(value = "/DAL_S06_check", method = RequestMethod.POST)
	public @ResponseBody
	TDailyMCWK check(TDailyMCWK TDailyMCWK) {
		try {
			TDailyMCWK = dalS06Logic.check(TDailyMCWK);
		} catch (Exception e) {
			setSystemError(TDailyMCWK, e);
		}

		return TDailyMCWK;
	}

	@RequestMapping(value = "/DAL_S06_delete", method = RequestMethod.GET)
	public ModelAndView delete(TDailyMCWK TDailyMCWK) {
		try {
			dalS06Logic.delete(TDailyMCWK);
		} catch (Exception e) {
			setSystemError(TDailyMCWK, e);
		}

		return new ModelAndView(PATH_URI)
				.addObject("dailyMCWK", TDailyMCWK)
				.addObject(
						"reportTypeList",
						masterDao.selectReportTypeList(new MReportType()
								.setReportCategory(new String[] { "wip" })))
				.addObject("wipMap", commonController.getWIPSel(3))
				.addObject("customerMap", commonController.getCustomerSel())
				.addObject("machineMap",
						commonController.getMachineNoActive(null));
	}

	@RequestMapping(value = "/DAL_S06_save", method = RequestMethod.POST)
	public @ResponseBody
	TDailyMCWK save(TDailyMCWK TDailyMCWK) {
		try {
			dalS06Logic.save(TDailyMCWK);
		} catch (Exception e) {
			setSystemError(TDailyMCWK, e);
		}

		return TDailyMCWK;
	}

	@RequestMapping(value = "/DAL_S06_workOrderNoList")
	public @ResponseBody
	List<Map<String, Object>> workOrderNoList(MWorkOrder MWorkOrder) {

		List<Map<String, Object>> workOrderList = new LinkedList<Map<String, Object>>();
		for (MWorkOrder obj : dalS06Logic.getWorkOrderNoList(MWorkOrder)) {
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

	@RequestMapping(value = "/DAL_S06_getPartNameNo", method = RequestMethod.GET)
	public @ResponseBody
	Map<Integer, String> getPartNameNo(@RequestParam Integer customerId,
			@RequestParam String wip, Integer reportType) {
		if (reportType != null && reportType != 1)
			wip = "";
		List<MPart> parts = dalS06Logic.getPartNo(customerId, wip);

		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(Integer.MAX_VALUE, "-- Select Part --");
		for (MPart MPart : parts)
			map.put(MPart.getPartId(),
					MPart.getPartName() + " : " + MPart.getPartNo());
		return map;
	}
}