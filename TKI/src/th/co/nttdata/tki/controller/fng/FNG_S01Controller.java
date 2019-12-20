package th.co.nttdata.tki.controller.fng;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MReportType;
import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.bean.TFG;
import th.co.nttdata.tki.bean.TFGDetail;
import th.co.nttdata.tki.bean.TLotSequence;
import th.co.nttdata.tki.blogic.fng.FNG_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;
import th.co.nttdata.tki.dao.MasterDao;

@Controller
public class FNG_S01Controller extends AbstractBaseController {

	private static final String PATH_URI = "FNG/FNG_S01";
	@Autowired
	private MasterDao masterDao;
	@Autowired
	private FNG_S01Logic fng_S01Logic;
	@Autowired
	private CommonController commonController;

	private static final Log logger = LogFactory.getLog("th.co.nttdata.tki");
	
	@RequestMapping("/FNG_S01")
	public ModelAndView init() {
		TFG TFG = new TFG();
		TFG.setFgStockType("fgin");
		
		return new ModelAndView(PATH_URI).addObject("tfg", new TFG())
				.addObject(
						"reportTypeList",
						masterDao.selectReportTypeList(new MReportType()
								.setReportCategory(new String[] { TFG.getFgStockType() })))
				.addObject("minReportDate",
						settings.getProperty("DAL.reportDate.minDate", "-40d"));
	}

	@RequestMapping("/FNG_S01_AddRow")
	public ModelAndView addRow(TFG TFG) {
		return new ModelAndView(PATH_URI)
				.addObject("tfg", TFG)
				.addObject(
						"reportTypeList",
						masterDao.selectReportTypeList(new MReportType()
								.setReportCategory(new String[] { TFG.getFgStockType() })))
				
				.addObject("customerMap", commonController.getCustomerSel());
	}

	@RequestMapping(value = "/FNG_S01_Save", method = RequestMethod.POST)
	public @ResponseBody
	TFG save(TFG tfg) {
		try {
			fng_S01Logic.save(tfg);
		} catch (Exception e) {
			logger.debug(e);
			setSystemError(tfg, e);
		}
		return tfg;
	}

	@RequestMapping("/FNG_S01_LotQty")
	public @ResponseBody
	Map<String, Object> lotNo(@RequestParam String lotNo) {
		return fng_S01Logic.findLotQty(lotNo);
	}

	@RequestMapping(value = "/FNG_S01_txtWorkOrderDetail", method = RequestMethod.GET)
	public @ResponseBody
	MWorkOrder getWorkOrderDetail(MWorkOrder workOrder) {
		return fng_S01Logic.getWorkOrderDetail(workOrder);
	}

	@RequestMapping(value = "/FNG_S01_CheckLotNoAndSave")
	public @ResponseBody
	TFG checkLotNo(TFG tfg) {
		tfg = fng_S01Logic.clearDuplicateLotSeqNo(tfg);
		tfg = fng_S01Logic.skipBlankRow(tfg);

		Boolean isDuplicate = false;
		String lotSeqNo = null;
		List<TFGDetail> listNotDupFromDb = new ArrayList<TFGDetail>();
		try {
			int detailSize = tfg.getDetails().size();
			for (int i = 0; i < detailSize; i++) {
				TFGDetail t = tfg.getDetails().get(i);
				if (null == t.getFgIn()) {
					t.setFgIn(0);
				}
				if (null == t.getFgOut()) {
					t.setFgOut(0);
				}
				isDuplicate = fng_S01Logic.checkLotNo(t);
				lotSeqNo = "";
				if (!isDuplicate) {
					listNotDupFromDb.add(t);
				} else {
					tfg.getErrors().add(
							new Message("err.fng.006", new String[] {
									String.valueOf(i + 1),
									t.getWorkOrderNo() + t.getLotNo()
											+ lotSeqNo }));
				}
			}
			tfg.getDetails().clear();
			tfg.setDetails(listNotDupFromDb);

			tfg = fng_S01Logic.checkNotExist(tfg);

			// if (tfg.getErrors().size() == 0) {
			tfg = this.save(tfg);
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tfg;
	}

	@RequestMapping(value = "/FNG_S01_getOnlyLotSeqNo")
	public @ResponseBody
	List<TLotSequence> getLotSeqNoAuto(String lotSeqNo) {
		return fng_S01Logic.getOnlyLotSeqNoAuto(lotSeqNo);
	}

}