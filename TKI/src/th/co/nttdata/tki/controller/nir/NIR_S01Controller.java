package th.co.nttdata.tki.controller.nir;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MNirvanaSyncMaster;
import th.co.nttdata.tki.blogic.nir.NIR_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class NIR_S01Controller extends AbstractBaseController {

	private static final String PATH_URI = "NIR/NIR_S01";

	@Autowired
	private NIR_S01Logic nirS01Logic;

	/**
	 * Initial Nirvana Sync History screen.
	 * 
	 * @return Nirvana Sync History screen.
	 */
	@RequestMapping("/NIR_S01")
	public ModelAndView init() {
		MNirvanaSyncMaster mNirvanaSyncMaster = new MNirvanaSyncMaster();

		return new ModelAndView(PATH_URI).addObject("searchCriteria", mNirvanaSyncMaster);
	}

	/**
	 * Search Nirvana master data sync according criteria from screen.
	 * 
	 * @param mNirvanaSyncMaster - criteria search.
	 * @return list of Nirvana Sync Master data.
	 */
	@RequestMapping(value = "/NIR_S01_search.html", method = RequestMethod.GET)
	public @ResponseBody List<MNirvanaSyncMaster> search(MNirvanaSyncMaster mNirvanaSyncMaster) {
		mNirvanaSyncMaster = nirS01Logic.search(mNirvanaSyncMaster);

		return mNirvanaSyncMaster.getMNirvanaSyncMasterList();
	}

	/**
	 * Fixed sync status error to fixed.
	 * 
	 * @param mNirvanaSyncMasterMap - list of selected data from Nirvana Sync History screen.
	 * @return Nirvana Sync History data result.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/NIR_S01_save")
	@ResponseBody
	public MNirvanaSyncMaster save(@RequestBody List<LinkedHashMap> mNirvanaSyncMasterMap) {
		MNirvanaSyncMaster mNirvanaSyncMaster = new MNirvanaSyncMaster();
		try {
			List<MNirvanaSyncMaster> datas = new ArrayList<MNirvanaSyncMaster>();
			MNirvanaSyncMaster mNirvanaSync = new MNirvanaSyncMaster();
			for (LinkedHashMap map : mNirvanaSyncMasterMap) {
				mNirvanaSync = new MNirvanaSyncMaster();
				// Set default date data for support case date is null.
				map.put("syncDateFrom", new Date());
				map.put("syncDateTo", new Date());
				BeanUtils.copyProperties(mNirvanaSync, map);
				datas.add(mNirvanaSync);
			}
			mNirvanaSyncMaster.setMNirvanaSyncMasterList(datas);
			nirS01Logic.save(mNirvanaSyncMaster);
			setSaveInfo(mNirvanaSyncMaster);
		} catch (Exception e) {
			setSystemError(mNirvanaSyncMaster, e);
		}
		return mNirvanaSyncMaster;
	}
}