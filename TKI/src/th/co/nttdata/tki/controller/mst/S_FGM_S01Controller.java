package th.co.nttdata.tki.controller.mst;

import java.util.ArrayList;
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

import th.co.nttdata.tki.bean.FgMaster;
import th.co.nttdata.tki.bean.filter.FgMasterFilter;
import th.co.nttdata.tki.blogic.mst.S_FGM_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class S_FGM_S01Controller extends AbstractBaseController {

	private static final String PATH_URI = "MST/S_FGM_S01";
	@Autowired
	private S_FGM_S01Logic fg_S01Logic;

	@RequestMapping("/S_FGM_S01")
	public ModelAndView init(FgMaster fgMaster) {
		return new ModelAndView(PATH_URI)
				.addObject("mFgMaster", new FgMaster());
	}

	@RequestMapping(value = "/S_FGM_S01_searchList", method = RequestMethod.GET)
	public @ResponseBody
	List<FgMaster> getFgMasterList(FgMaster fgMaster) {
		List<FgMaster> fgmasterList = new ArrayList<FgMaster>();
		fgmasterList = fg_S01Logic.getFgMastertSearchList(fgMaster);
		return fgmasterList;
	}

	@RequestMapping(value = "/S_FGM_S01_getUomList")
	public @ResponseBody
	List<FgMaster> getUomList() {
		List<FgMaster> uomList = fg_S01Logic.getUomList();
		return uomList;
	}

	@RequestMapping(value = "/S_FGM_S01_getCurrencyList")
	public @ResponseBody
	List<FgMaster> getCurrencyList() {
		List<FgMaster> currencyList = fg_S01Logic.getCurrencyList();
		return currencyList;
	}

	@RequestMapping(value = "/S_FGM_S01_getClassifyBusiness")
	public @ResponseBody
	List<FgMaster> getClassifyBusiness() {
		List<FgMaster> classifyBusinessList = fg_S01Logic.getClassifyBusiness();
		return classifyBusinessList;
	}

	@RequestMapping(value = "/S_FGM_S01_getPlaceList")
	public @ResponseBody
	List<FgMaster> getPlaceList() {
		List<FgMaster> placeList = fg_S01Logic.getPlace();
		return placeList;
	}

	@RequestMapping(value = "/S_FGM_S01_getSubBusinessList")
	public @ResponseBody
	List<FgMaster> getSubBusinessList() {
		List<FgMaster> subBusinessList = fg_S01Logic.getSubbusiness();
		return subBusinessList;
	}

	@RequestMapping(value = "/S_FGM_S01_saveList", method = RequestMethod.POST)
	@ResponseBody
	public FgMaster saveList(@RequestBody List<Map> param) {
		FgMaster fgMaster = new FgMaster();
		try {
			List<FgMaster> fgMasterList = new ArrayList<FgMaster>();
			for (Map map : param) {
				FgMaster fgMasterBean = new FgMaster();
				BeanUtils.populate(fgMasterBean, map);

				if (fgMasterBean.getClassifyBusinessId() == null
						|| fgMasterBean.getClassifyBusinessId() == 0) {
					fgMasterBean.setClassifyBusinessId(null);
				}
				if (fgMasterBean.getPlaceId() == null
						|| fgMasterBean.getPlaceId() == 0) {
					fgMasterBean.setPlaceId(null);
				}
				if (fgMasterBean.getSubBusinessId() == null
						|| fgMasterBean.getSubBusinessId() == 0) {
					fgMasterBean.setSubBusinessId(null);
				}
				if (fgMasterBean.getCurrency() == null) {
					fgMasterBean.setCurrency(null);
				}
				fgMasterList.add(fgMasterBean);
			}
			fg_S01Logic.saveList(fgMasterList);
			setSaveInfo(fgMaster);
		} catch (Exception e) {
			setSystemError(fgMaster, e);
		}
		return fgMaster;
	}

	@RequestMapping(value = "/FGM_R01_export", method = RequestMethod.POST)
	public ModelAndView export(FgMasterFilter fgMasters) {
		return new ModelAndView("FGM_R01ExcelView").addObject(
				"fgMasterExportList", fg_S01Logic.search(fgMasters));
	}

}
