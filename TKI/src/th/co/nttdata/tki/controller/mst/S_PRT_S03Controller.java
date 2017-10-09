package th.co.nttdata.tki.controller.mst;

import java.util.ArrayList;
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
import th.co.nttdata.tki.bean.PartRoutingMaster;
import th.co.nttdata.tki.bean.filter.PartRoutingMasterFilter;
import th.co.nttdata.tki.blogic.mst.S_PRT_S03Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class S_PRT_S03Controller extends AbstractBaseController {
	private static final String PATH_URI = "MST/S_PRT_S03";
	@Autowired
	private S_PRT_S03Logic s_prt_S03Logic;

	@RequestMapping("/S_PRT_S03")
	public ModelAndView init(PartRoutingMaster prMaster) {
		return new ModelAndView(PATH_URI).addObject("prMaster", prMaster)
				.addObject("custMap", getCustomerSel());

	}

	public Map<Integer, String> getCustomerSel() {
		List<MCustomer> custList = s_prt_S03Logic.getAllCustomer()
				.getCustomerList();
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(Integer.MIN_VALUE, "-- Select Customer --");
		for (MCustomer cust : custList) {
			map.put(cust.getCustomerId(), cust.getCustomerCode());
		}
		return map;
	}

	@RequestMapping(value = "/S_PRT_S03_searchList", method = RequestMethod.GET)
	public @ResponseBody
	List<PartRoutingMaster> getPartRoutingMastertistByCustomerId(
			PartRoutingMaster prMaster) {
		PartRoutingMaster partRoutingMaster = new PartRoutingMaster();
		partRoutingMaster = s_prt_S03Logic.searchFgPartWipList(prMaster);
		return partRoutingMaster.getPartRoutinglist();
	}

	@RequestMapping(value = "/S_PRT_S03_serarchProcessList", method = RequestMethod.GET)
	public @ResponseBody
	List<PartRoutingMaster> getserarchProcessListBypartId(
			PartRoutingMaster prMaster) {
		PartRoutingMaster partRoutingMaster = new PartRoutingMaster();
		partRoutingMaster = s_prt_S03Logic.searchProcessList(prMaster);
		return partRoutingMaster.getPartRoutinglist();
	}

	@RequestMapping(value = "/S_PRT_S03_seletProcess")
	public @ResponseBody
	List<PartRoutingMaster> getProcessList(PartRoutingMaster prMaster) {
		PartRoutingMaster partRoutingMaster = new PartRoutingMaster();
		partRoutingMaster = s_prt_S03Logic.selectProcess();
		System.out.println(partRoutingMaster.getPartRoutinglist().get(1)
				.getProcess());
		return partRoutingMaster.getPartRoutinglist();
	}

	@RequestMapping(value = "/S_PRT_S03_saveList", method = RequestMethod.POST)
	@ResponseBody
	public PartRoutingMaster saveProcessList(@RequestBody List<Map> param) {
		PartRoutingMaster partRoutingMaster = new PartRoutingMaster();
		try {
			List<PartRoutingMaster> partRoutingMasterList = new ArrayList<PartRoutingMaster>();
			for (Map map : param) {
				PartRoutingMaster prmaster = new PartRoutingMaster();
				BeanUtils.populate(prmaster, map);
				partRoutingMasterList.add(prmaster);
			}
			s_prt_S03Logic.saveProcessList(partRoutingMasterList);
			setSaveInfo(partRoutingMaster);
		} catch (Exception e) {
			setSystemError(partRoutingMaster, e);
		}
		return partRoutingMaster;
	}

	@RequestMapping(value = "/S_PRT_S03_selectCopyForPartRoutingList", method = RequestMethod.GET)
	public @ResponseBody
	List<PartRoutingMaster> getCopyforPartRoutingList(PartRoutingMaster prMaster) {
		PartRoutingMaster partRoutingMaster = new PartRoutingMaster();
		partRoutingMaster = s_prt_S03Logic
				.selectListForCopyPartRouting(prMaster);
		return partRoutingMaster.getPartRoutinglist();
	}

	@RequestMapping(value = "/S_PRT_S03_savePartDestination", method = RequestMethod.POST)
	@ResponseBody
	public PartRoutingMaster savePartDestination(PartRoutingMaster prMaster) {
		PartRoutingMaster partRoutingMaster = new PartRoutingMaster();
		try {
			s_prt_S03Logic.insertPartDestination(prMaster);
			setSaveInfo(partRoutingMaster);
		} catch (Exception e) {
			setSystemError(partRoutingMaster, e);
		}
		return partRoutingMaster;
	}

	@RequestMapping(value = "/S_PRT_S03_export", method = RequestMethod.POST)
	public ModelAndView export(PartRoutingMasterFilter prMasterFilter) {
		return new ModelAndView("S_PRT_S03ExcelView").addObject(
				"partRoutingMasterExportList",
				s_prt_S03Logic.selectExportList(prMasterFilter));
	}
}
