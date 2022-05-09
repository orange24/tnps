package th.co.nttdata.tki.controller.fng;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MReportType;
import th.co.nttdata.tki.bean.TFG;
import th.co.nttdata.tki.blogic.fng.FNG_S02Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;
import th.co.nttdata.tki.dao.MasterDao;

@Controller
public class FNG_S02Controller extends AbstractBaseController {

	private static final String PATH_URI = "FNG/FNG_S02";
	@Autowired
	private MasterDao masterDao;
	@Autowired
	private CommonController commonController;
	@Autowired
	private FNG_S02Logic fng_S02Logic;

	@RequestMapping("/FNG_S02")
	public ModelAndView init() {
		return new ModelAndView(PATH_URI)
				.addObject("searchCriteria", new TFG())
				.addObject(
						"reportTypeList",
						masterDao.selectReportTypeList(new MReportType()
								.setReportCategory(new String[] { "fgin",
										"fgout" })))
				.addObject("customerMap", commonController.getCustomerAll());
	}

	@RequestMapping("/FNG_S02_search")
	public ModelAndView search(TFG criteria) {
		TFG searchResult = null;
		try {
			searchResult = fng_S02Logic.search(criteria);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// <!-- Generating: reportTypeMap -->
		List<MReportType> reportTypeList = masterDao
				.selectReportTypeList(new MReportType()
						.setReportCategory(new String[] { "fgin", "fgout" }));
		Map<Integer, String> reportTypeMap = new LinkedHashMap<Integer, String>();
		for (MReportType MReportType : reportTypeList) {
			reportTypeMap.put(MReportType.getTypeCode().intValue(),
					MReportType.getTypeName());
		}

		return new ModelAndView(PATH_URI).addObject("searchCriteria", criteria)
				.addObject("reportTypeList", reportTypeList)
				.addObject("reportTypeMap", reportTypeMap)
				.addObject("customerMap", commonController.getCustomerAll())
				.addObject("searchResult", searchResult);
	}

	@RequestMapping(value = "/FNG_S02_export", method = RequestMethod.POST)
	public ModelAndView export(TFG criteria) {
		return new ModelAndView("FNG_R01ExcelView").addObject("TFG",
				fng_S02Logic.exportFNG_R01(criteria)).addObject(
				"reportTypeList", fng_S02Logic.getReportTypeList());
	}

	@RequestMapping(value = "/FNG_S03_export", method = RequestMethod.POST)
	public ModelAndView exportFNG_S03(TFG criteria) {

		// <!-- Generating: reportTypeMap -->
		List<MReportType> reportTypeList = masterDao
				.selectReportTypeList(new MReportType()
						.setReportCategory(new String[] { "fgin", "fgout" }));
		Map<Integer, String> reportTypeMap = new LinkedHashMap<Integer, String>();
		for (MReportType MReportType : reportTypeList) {
			reportTypeMap.put(MReportType.getTypeCode().intValue(),
					MReportType.getTypeName());
		}

		return new ModelAndView("FNG_R03ExcelView").addObject("TFG",
				fng_S02Logic.exportFNG_R03(criteria)).addObject(
				"reportTypeMap", reportTypeMap);
	}

	@RequestMapping(value = "/FNG_S05_export", method = RequestMethod.POST)
	public ModelAndView exportFNG_S05(TFG criteria) {

		// <!-- Generating: reportTypeMap -->
		List<MReportType> reportTypeList = masterDao.selectReportTypeList(new MReportType().setReportCategory(new String[] { "fgin", "fgout" }));

		return new ModelAndView("FNG_R05ExcelView").addObject("TFG",
				fng_S02Logic.exportFNG_R05(criteria));
	}

}
