package th.co.nttdata.tki.controller.mst;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MFgPart;
import th.co.nttdata.tki.blogic.mst.PRT_S02Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class PRT_S02Controller extends AbstractBaseController {

	private static final String PATH_URI = "MST/PRT_S02";

	@Autowired
	CommonController commonController;
	@Autowired
	private PRT_S02Logic prt_S02Logic;

	@RequestMapping("/PRT_S02")
	public ModelAndView init(MFgPart mFGPart) {
		return new ModelAndView(PATH_URI).addObject("mfgpart", mFGPart)
				.addObject("custMap", getCustomerSel());
	}

	public Map<Integer, String> getCustomerSel() {
		List<MCustomer> custList = prt_S02Logic.getAllCustomer()
				.getCustomerList();
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(Integer.MIN_VALUE, "-- Select Customer --");
		for (MCustomer cust : custList) {
			map.put(cust.getCustomerId(), cust.getCustomerCode());
		}
		return map;
	}

	@RequestMapping(value = "/PRT_S02_searchList.html", method = RequestMethod.GET)
	public @ResponseBody
	List<MFgPart> getMFgLPartistByCustomerId(MFgPart mFgPart) {
		MFgPart mfgPartBean = new MFgPart();
		mfgPartBean = prt_S02Logic.searchFgPartMappingByCustomerId(mFgPart);
		return mfgPartBean.getCustomerList();
	}

	@RequestMapping(value = "/PRT_S02_saveList", method = RequestMethod.POST)
	@ResponseBody
	public MFgPart saveFgPartList(@RequestBody List<LinkedHashMap> param) {
		MFgPart mFgpart = new MFgPart();
		try {
			List<MFgPart> mFgpartList = new ArrayList<MFgPart>();
			for (LinkedHashMap<String, Object> map : param) {
				MFgPart mfg = new MFgPart();
				if (map.get("createDate") == null) {
					Date date = new Date();
					map.put("createDate", date);
					map.put("lastUpdate", date);
				}
				if (map.get("lastUpdate") == null) {
					Date date = new Date();
					map.put("lastUpdate", date);
				}
				BeanUtils.populate(mfg, map);
				mFgpartList.add(mfg);
			}
			prt_S02Logic.saveList(mFgpartList);
		} catch (Exception e) {
			setSystemError(mFgpart, e);
		}
		return mFgpart;
	}

	@RequestMapping(value = "/PRT_S02_selectFGNoList")
	public @ResponseBody
	List<MFgPart> getFgNoNameListByCustomerId(MFgPart mFgPart) {
		return prt_S02Logic.getFgNoNameListByCustomerId(mFgPart);

	}

	@RequestMapping(value = "/PRT_S02_selectPartList")
	public @ResponseBody
	List<MFgPart> getPartListByFgId(MFgPart mFgPart) {
		MFgPart mf = new MFgPart();
		mf.setCustomerList(prt_S02Logic.selectPartListByFgId(mFgPart));
		return mf.getCustomerList();
	}

}
