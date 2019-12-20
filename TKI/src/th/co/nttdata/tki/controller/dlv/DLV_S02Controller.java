package th.co.nttdata.tki.controller.dlv;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.CustomerLine;
import th.co.nttdata.tki.bean.MDocControl;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.blogic.DLV.DLV_S01Logic;
import th.co.nttdata.tki.blogic.DLV.DLV_S02Logic;
import th.co.nttdata.tki.blogic.cfg.DOC_S01Logic;
import th.co.nttdata.tki.blogic.mst.CUS_S03Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class DLV_S02Controller extends AbstractBaseController {

	private static final String PATH_URI = "DLV/DLV_S02";
	private static final String PATH_URI_DLV_S01 = "DLV/DLV_S01";
	private static final ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private CommonController commonController;

	@Autowired
	public DOC_S01Logic doc_S01Logic;
	
	@Autowired
	private DLV_S02Logic dlv_S02Logic;
	@Autowired
	private DLV_S01Logic dlv_S01Logic;
	@Autowired
	private CUS_S03Logic cus_s03Logic;
	
	@RequestMapping("/DLV_S02")
	public ModelAndView init(TDeliveryPlan TDeliveryPlan) {
		// "10.00;14.00;" is default in case not find from getProperty
		String timePlan = settings.getProperty("DLV.DLV.defaultTimePlan",
				"10.00;14.00;");

		// find day of month
		Calendar dayOfMonth = new GregorianCalendar(TDeliveryPlan.getYear(),
				TDeliveryPlan.getMonth() + 1, 1);
		dayOfMonth.add(Calendar.DAY_OF_YEAR, -1);
		TDeliveryPlan.setDayOfMonth(dayOfMonth.get(Calendar.DAY_OF_MONTH));

		return new ModelAndView(PATH_URI)
				.addObject("deliveryPlan", TDeliveryPlan)
				.addObject("monthMap", getMonthMap())
				.addObject("timePlan", timePlan);
	}

	@RequestMapping(value = "/DLV_S02_search")
	public ModelAndView search(TDeliveryPlan TDeliveryPlan) {

		String reason = settings.getProperty("DLV.DLV.defaultReason",
				"A:A;B:B;C:C;R:R;");

		String plan = null;
		try {
			TDeliveryPlan = dlv_S02Logic.search(TDeliveryPlan);
			plan = mapper.writeValueAsString(TDeliveryPlan);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String customerLine = null;
		try {
			CustomerLine custLine = new CustomerLine();
			custLine.setCustomerId(TDeliveryPlan.getCustomerId());
			custLine = cus_s03Logic.searchCustomerLineByCustomerId(custLine);
			
			customerLine = "";
			for (CustomerLine item : custLine.getCustomerLineList()) {
				customerLine += item.getCustomerLineId() + ":" + item.getCustomerLineName() + ";";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return init(TDeliveryPlan).addObject("reasonMap", reason).addObject(
				"plan", plan).addObject("customerLine", customerLine);
	}

	@RequestMapping(value = "/DLV_S02_save", method = RequestMethod.POST)
	@ResponseBody
	public TDeliveryPlan save(TDeliveryPlan TDeliveryPlan) {
		try {
			dlv_S02Logic.save(TDeliveryPlan);
		} catch (Exception e) {
			setSystemError(TDeliveryPlan, e);
		}
		return TDeliveryPlan;
	}

	@RequestMapping(value = "/DLV_S01_export", method = RequestMethod.POST)
	public ModelAndView export(TDeliveryPlan TDeliveryPlan) {
		// find day of month
		Calendar dayOfMonth = new GregorianCalendar(TDeliveryPlan.getYear(),
				TDeliveryPlan.getMonth() + 1, 1);
		dayOfMonth.add(Calendar.DAY_OF_YEAR, -1);
		TDeliveryPlan.setDayOfMonth(dayOfMonth.get(Calendar.DAY_OF_MONTH));
		// month name
		Map<Integer, String> monthMap = getMonthMap();
		TDeliveryPlan.setMonthName(monthMap.get(TDeliveryPlan.getMonth()));
		TDeliveryPlan.setCustomerCode(TDeliveryPlan.getCustomerCode());

		TDeliveryPlan report = dlv_S02Logic.deliveryPlanReport(TDeliveryPlan);
		MDocControl docControl = doc_S01Logic.getDocNo();
		ModelAndView mv = new ModelAndView("DLV_R01ExcelView");
		mv.addObject("deliveryPlan",report);
		mv.addObject("docControl",docControl);
		return mv;
	}

	@RequestMapping(value = "/DLV_S02_deleteByFg", method = RequestMethod.POST)
	public ModelAndView deleteByFg(TDeliveryPlan TDeliveryPlan) {
		dlv_S02Logic.deleteByFg(TDeliveryPlan);
		dlv_S01Logic.search(TDeliveryPlan);

		return new ModelAndView(PATH_URI_DLV_S01)
				.addObject("deliveryPlan", TDeliveryPlan)
				.addObject("customerMap", commonController.getCustomerSel())
				.addObject("monthMap", getMonthMap())
				.addObject("yearMap", getYearMap());
	}

	@RequestMapping(value = "/DLV_S02_checkButtonDelete")
	@ResponseBody
	public TDeliveryPlan checkButtonDelete(TDeliveryPlan TDeliveryPlan) {
		try {
			dlv_S02Logic.countDate(TDeliveryPlan);
			dlv_S02Logic.insertFlag(TDeliveryPlan);
		} catch (Exception e) {
			setSystemError(TDeliveryPlan, e);
		}
		return TDeliveryPlan;
	}

	@RequestMapping(value = "/DLV_S02_boxFgNameNo")
	@ResponseBody
	public List<MPart> getFgNameNo(TDeliveryPlan TDeliveryPlan) {

		return dlv_S02Logic.getFgList(TDeliveryPlan);
	}
}
