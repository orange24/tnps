package th.co.nttdata.tki.controller.mst;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MMachine;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.blogic.mst.MCH_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MCH_S01Controller extends AbstractBaseController {

	private static final String PATH_URI = "MST/MCH_S01";
	private static final int wipType1 = 1;
	private static final int wipType3 = 3;
	private final String BUTTON = "button";
	private final String BTNBACK = "back";
	private final String CRITERIA = "criteria";
	private Map<String, String> wipMap;
	Date date = new Date();
	@Autowired
	private CommonController commonControl;

	@Autowired
	private MCH_S01Logic mch_S01Logic;

	@RequestMapping("/MCH_S01")
	public ModelAndView init(HttpSession session) {
		session.removeAttribute(CRITERIA);
		wipMap = commonControl.getWIPAll(wipType1);
		wipMap.putAll(commonControl.getWIPAll(wipType3));
		return new ModelAndView(PATH_URI).addObject("machine", new MMachine())
				.addObject("wipMap", wipMap);
	}

	@RequestMapping(value = "/MCH_S01_search", method = RequestMethod.POST)
	public ModelAndView search(MMachine mMachine, HttpSession session,
			HttpServletRequest request) {
		String param = request.getParameter(BUTTON);
		if (param != null && param.equals(BTNBACK)
				&& session.getAttribute(CRITERIA) != null) {
			mMachine = (MMachine) session.getAttribute(CRITERIA);
			session.removeAttribute(CRITERIA);
			mMachine.getInfos().clear();
		}
		session.setAttribute(CRITERIA, mMachine);
		mMachine = mch_S01Logic.getMachineList(mMachine);
		return new ModelAndView(PATH_URI).addObject("machine", mMachine)
				.addObject("wipMap", wipMap).addObject("wipList", getWipList());
	}

	@RequestMapping(value = "/MCH_S01_PartMappingSearchList", method = RequestMethod.GET)
	public @ResponseBody
	List<MMachine> getPartMappingListByMachineId(MMachine mMachine) {
		List<MMachine> machineList = new ArrayList<MMachine>();
		machineList = mch_S01Logic.getPartMachineList(mMachine);
		return machineList;
	}

	@RequestMapping(value = "/MCH_S01_copyPartMappingSearchList", method = RequestMethod.GET)
	public @ResponseBody
	List<MMachine> getCopyPartMappingListByWip(MMachine mMachine) {
		List<MMachine> machineList = new ArrayList<MMachine>();
		machineList = mch_S01Logic.getCopyPartMachineList(mMachine);
		return machineList;
	}

	public Map<String, String> getWipList() {
		List<MMachine> wipList = new ArrayList<MMachine>();
		wipList = mch_S01Logic.getWipList();
		Map<String, String> wipMap = new HashMap<String, String>();
		wipMap.put("", "-- Select Wip --");
		for (MMachine wip : wipList) {
			wipMap.put(wip.getWip(), wip.getWip());

		}
		Map<String, String> treeMap = new TreeMap<String, String>(wipMap);
		return treeMap;
	}

	@RequestMapping(value = "/MCH_01_getCustomerList.html")
	public @ResponseBody
	List<MMachine> getCustomerListByCustomerId(MMachine mmachine) {
		List<MMachine> mCustomerList = new ArrayList<MMachine>();
		mCustomerList = mch_S01Logic.getCustomerList(mmachine);
		return mCustomerList;
	}

	@RequestMapping(value = "/MCH_01_getPartList.html")
	public @ResponseBody
	List<MMachine> getPartListByCustomerId(MMachine mmachine) {
		List<MMachine> mPartList = new ArrayList<MMachine>();
		mPartList = mch_S01Logic.getPartNoList(mmachine);
		return mPartList;
	}

	@RequestMapping(value = "/MCH_S01saveList", method = RequestMethod.POST)
	@ResponseBody
	public MMachine saveCopyPartMappingList(MMachine mmachine) {
		MMachine mMachine = new MMachine();
		try {
			mch_S01Logic.saveCopyPartMappingList(mmachine);
			setSaveInfo(mMachine);
		} catch (Exception e) {
			setSystemError(mMachine, e);
		}
		return mMachine;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/MCH_S01savePartMappingList", method = RequestMethod.POST)
	@ResponseBody
	public MMachine savePartMappingList(@RequestBody List<Map> param) {
		MMachine mMachine = new MMachine();
		List<MMachine> partMappingList = new ArrayList<MMachine>();
		int size = param.size();
		try {
			for (int i = 0; i < size; i++) {
				MMachine partMapping = new MMachine();
				param.get(i).get(param.get(i).get("createdDate"));
				param.get(i).put("createDate", date);
				param.get(i).put("lastUpdate", date);
				param.get(i).put("endDate", date);
				param.get(i).put("startDate", date);
				BeanUtils.populate(partMapping, param.get(i));
				partMappingList.add(partMapping);
			}
			mch_S01Logic.savePartMappingList(partMappingList);
			setSaveInfo(mMachine);
		} catch (Exception e) {
			setSystemError(mMachine, e);
		}

		return mMachine;
	}

	@RequestMapping("/MCH_S01_delete")
	public ModelAndView delete(MMachine MMachine, HttpSession session,
			HttpServletRequest request) {
		try {
			mch_S01Logic.delete(MMachine);
			MMachine = mch_S01Logic.getMachineList(MMachine);
			MMachine.getInfos().clear();
			MMachine.getInfos().add(new Message("inf.cmm.003", null));
		} catch (Exception e) {
			setSystemError(MMachine, e);
		}
		return search(MMachine, session, request);
	}
}
