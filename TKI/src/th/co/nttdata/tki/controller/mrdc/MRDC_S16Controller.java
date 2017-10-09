package th.co.nttdata.tki.controller.mrdc;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.TFGStock;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S16Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MRDC_S16Controller extends AbstractBaseController {

	private static final String PATH_URI = "MRDC/MRDC_S16";

	@Autowired
	private CommonController commonController;
	@Autowired
	private MRDC_S16Logic mrdc_s16Logic;

	@RequestMapping("/MRDC_S16")
	public ModelAndView init() {
		Calendar cal = new GregorianCalendar(Locale.US);

		TFGStock tfgStock = new TFGStock();
		tfgStock.setMonthFr(cal.get(Calendar.MONTH));
		tfgStock.setMonthTo(cal.get(Calendar.MONTH));
		tfgStock.setYearFr(cal.get(Calendar.YEAR));
		tfgStock.setYearTo(cal.get(Calendar.YEAR));

		return new ModelAndView(PATH_URI).addObject("criteria", tfgStock)
				.addObject("customerMap", commonController.getCustomerAll());
	}

	@RequestMapping("/MRDC_R16_export")
	public ModelAndView export(TFGStock tfgStock) {

		return new ModelAndView("MRDC_R16ExcelView").addObject("tfgStock",
				mrdc_s16Logic.exportMRDC_R16(tfgStock)).addObject("mwip",
				mrdc_s16Logic.queryWip(new MWip()));
	}

	@RequestMapping(value = "/MRDC_R16_export_count", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Integer> count(TFGStock tfgStock) {
		Integer maxRecord = Integer.parseInt(settings.getProperty(
				"CMM.MaxRecord", "40000"));
		Integer count = mrdc_s16Logic.countMRDC_R16();
		TFGStock t = new TFGStock();
		t = mrdc_s16Logic.exportMRDC_R16(tfgStock);
		Integer size = t.getTfgStockList().size();// Data of query export
													// MRDC_R16

		Map<String, Integer> map = new LinkedHashMap<String, Integer>();
		map.put("count", count == null ? 0 : count);
		map.put("maxRecord", maxRecord);
		map.put("size", size);

		return map;
	}
}
