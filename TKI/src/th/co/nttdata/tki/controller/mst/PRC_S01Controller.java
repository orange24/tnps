package th.co.nttdata.tki.controller.mst;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.bean.filter.MWipFilter;
import th.co.nttdata.tki.blogic.mst.PRC_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class PRC_S01Controller extends AbstractBaseController {

	private static final String PATH_URI = "MST/PRC_S01";

	@Autowired
	private PRC_S01Logic prc_S01Logic;

	@RequestMapping("/PRC_S01")
	public ModelAndView init() {
		return new ModelAndView(PATH_URI).addObject("searchCriteria",
				new MWip());
	}

	@RequestMapping(value = "/PRC_S01_search", method = RequestMethod.POST)
	public ModelAndView search(MWip mWip) {
		mWip = prc_S01Logic.search(mWip);
		return new ModelAndView(PATH_URI).addObject("searchCriteria", mWip)
				.addObject("wipTypeMap", getWipTypeMap(null));
	}

	@RequestMapping(value = "/PRC_S01_searchList", method = RequestMethod.GET)
	public @ResponseBody
	List<MWip> getMwipList(MWip mWip) {
		List<MWip> mWips = new ArrayList<MWip>();
		mWips = prc_S01Logic.searchList(mWip);
		return mWips;
	}

	@RequestMapping("/PRC_S01_delete")
	public ModelAndView delete(MWip mWip) {
		try {
			prc_S01Logic.delete(mWip);
			mWip.getInfos().add(new Message("inf.cmm.003", new String[] {}));
		} catch (Exception e) {
			setSystemError(mWip, e);
		}
		return search(mWip);
	}

	@RequestMapping(value = "/PRC_S01_save", method = RequestMethod.POST)
	public ModelAndView save(MWip mWip) {
		try {
			prc_S01Logic.save(mWip);
			mWip.getInfos().add(new Message("inf.cmm.002", new String[] {}));
		} catch (Exception e) {
			setSystemError(mWip, e);
		}
		return search(mWip);
	}

	@RequestMapping(value = "/PRC_S01_saveList", method = RequestMethod.POST)
	@ResponseBody
	public MWip saveList(@RequestBody List<LinkedHashMap> param) {
		MWip mwip = new MWip();
		try {
			prc_S01Logic.saveList(param);
			setSaveInfo(mwip);
		} catch (Exception e) {
			setSystemError(mwip, e);
		}
		return mwip;
	}

	@RequestMapping(value = "/PRC_S01_exportList", method = RequestMethod.POST)
	public ModelAndView export(MWipFilter mwipFilter) {
		return new ModelAndView("PRC_S01ExcelView").addObject("mwipDetail",
				prc_S01Logic.selectWipListByWipFilter(mwipFilter));
	}

	@RequestMapping("/PRC_S01_check")
	public @ResponseBody
	MWip check(MWip MWip) {
		try {
			MWip = prc_S01Logic.check(MWip);
		} catch (Exception e) {
			setSystemError(MWip, e);
		}
		return MWip;
	}

	@RequestMapping(value = "/PRC_S01_wipTypeNameList")
	public @ResponseBody
	List<MWip> getWipTypeNameList() {
		return prc_S01Logic.getWipTypeName();

	}
}
