package th.co.nttdata.tki.controller.fng;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.TFGStock;
import th.co.nttdata.tki.bean.TLotNo;
import th.co.nttdata.tki.bean.TLotSequence;
import th.co.nttdata.tki.bean.TTagLabelDetail;
import th.co.nttdata.tki.blogic.cmm.CommonLogic;
import th.co.nttdata.tki.blogic.fng.FNG_S06Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class FNG_S06Controller extends AbstractBaseController {

	private static final String PATH_URI = "FNG/FNG_S06";
	@Autowired
	private CommonLogic commonLogic;
	@Autowired
	private FNG_S06Logic fng_S06Logic;

	public Map<Integer, String> getCustomer() {
		List<MCustomer> customers = commonLogic.getCustomer();

		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(null, "-- All Customer --");
		for (MCustomer MCustomer : customers)
			map.put(MCustomer.getCustomerId(), MCustomer.getCustomerCode());
		return map;
	}

	@RequestMapping("/FNG_S06")
	public ModelAndView init() {
		String printerName = null;
		TTagLabelDetail tTagLabelDetail = new TTagLabelDetail();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		printerName = (String) session.getAttribute("FNG_S06printerName");
		if (org.apache.commons.lang.StringUtils.isNotEmpty(printerName)) {
			tTagLabelDetail.setPrinterName(printerName);
		}
		return new ModelAndView(PATH_URI).addObject("searchCriteria",
				tTagLabelDetail);
	}

	@RequestMapping("/FNG_S06_search")
	public ModelAndView search(TFGStock tfgStock) {
		tfgStock = fng_S06Logic.searchFGAdjustHistory(tfgStock);
		return new ModelAndView(PATH_URI).addObject("tfgStock", tfgStock)
				.addObject("customerMap", getCustomer());
	}

	@RequestMapping(value = "/FNG_S06_getLotNo")
	public @ResponseBody
	List<TLotNo> getLotNoAuto(String lotNo) {
		return fng_S06Logic.getLotNoAuto(lotNo);
	}

	@RequestMapping(value = "/FNG_S06_getLotNoDetail")
	public @ResponseBody
	TTagLabelDetail getLotNoDetail(String lotNo) {
		return fng_S06Logic.getLotNoDetail(lotNo);
	}

	@RequestMapping(value = "/FNG_S06_getLotNoDetailExisting")
	public @ResponseBody
	TTagLabelDetail getLotNoDetailExisting(String lotNo) {
		return fng_S06Logic.getLotNoDetailExisting(lotNo);
	}

	@RequestMapping(value = "/FNG_S06_getDetailByLotNo")
	public @ResponseBody
	TTagLabelDetail getDetailByLotNo(String lotNo) {
		TTagLabelDetail tagLabel = null;
		tagLabel = fng_S06Logic.getLotNoDetailExisting(lotNo);
		if (tagLabel != null) {
			Integer printQtyRemain = fng_S06Logic.getPrintQtyRemain(tagLabel
					.getTagId());
			if (null == printQtyRemain) {
				printQtyRemain = 0;
			}
			tagLabel.setPrintQtyRemain(printQtyRemain);
		} else {
			tagLabel = fng_S06Logic.getLotNoDetail(lotNo);
		}
		return tagLabel;
	}

	@RequestMapping(value = "/FNG_S06_preview", method = RequestMethod.POST)
	public @ResponseBody
	List<TLotSequence> preview(@RequestBody TTagLabelDetail taglabel) {
		List<TLotSequence> list = new ArrayList<TLotSequence>();
		try {
			taglabel.setLotNo(taglabel.getLotNo().trim());
			list = fng_S06Logic.preview(taglabel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(value = "/FNG_S06_printLabel", method = RequestMethod.POST)
	public @ResponseBody
	TTagLabelDetail printLabel(@RequestBody TTagLabelDetail tagLabel) {
		TTagLabelDetail tagLabelDetail = new TTagLabelDetail();
		try {
			tagLabelDetail = fng_S06Logic.printLabel(tagLabel);
			if (tagLabelDetail.getInfos().isEmpty()) {
				setSaveInfo(tagLabelDetail);
			}
		} catch (Exception e) {
			setSystemError(tagLabelDetail, e);
		}
		return tagLabelDetail;
	}

	@RequestMapping(value = "/FNG_S06_boxCustomer", method = RequestMethod.POST)
	public @ResponseBody
	Map<Integer, String> getCustomerSelPost() {
		return getCustomer("-- Select Customer --");
	}

	public @ResponseBody
	Map<Integer, String> getCustomer(String startValue) {
		List<MCustomer> customers = commonLogic.getCustomer();

		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(Integer.MAX_VALUE, startValue);
		for (MCustomer MCustomer : customers)
			map.put(MCustomer.getCustomerId(), MCustomer.getCustomerCode());
		return map;
	}
}
