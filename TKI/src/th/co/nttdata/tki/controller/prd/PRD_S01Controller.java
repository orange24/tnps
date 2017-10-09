package th.co.nttdata.tki.controller.prd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MMachine;
import th.co.nttdata.tki.bean.MMold;
import th.co.nttdata.tki.bean.MMoldDetail;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.TDCPlan;
import th.co.nttdata.tki.blogic.prd.PRD_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

/**
 * 
 * @author NDTH
 * @since July 18, 2013
 * 
 */
@Controller
public class PRD_S01Controller extends AbstractBaseController {

	/* A variable for indicating the next screen */
	private static final String PATH_URI = "PRD/PRD_S01";

	/* A variable for accessing business layer */
	@Autowired
	private PRD_S01Logic prd_S01Logic;

	/**
	 * Indicate the page to choose
	 * 
	 * @return the model and view for indication the screen
	 * @param none
	 */
	@RequestMapping("/PRD_S01")
	public ModelAndView init() {
		return new ModelAndView(PATH_URI);
	}

	/**
	 * Get machine list from DB based on the request parameter WIP
	 * 
	 * @param the
	 *            HttpServletRequest
	 * @return collection of JSON objects for machine based on WIP
	 */
	@RequestMapping("/PRD_S01_getMachine")
	public @ResponseBody
	List<MMachine> getMachine(HttpServletRequest request) {
		return prd_S01Logic.getMachineByWip(request.getParameter("param"));
	}

	/**
	 * Get all WIP from back end Bind all WIP to list box
	 * 
	 * @param none
	 * @return a collection of WIP objects
	 */
	@RequestMapping("/PRD_S01_getWip")
	public @ResponseBody
	List<MWip> getWip() {
		return prd_S01Logic.getWipByWipType();
	}

	/**
	 * Search all die cast plan based on DCPlan date, WIP, and machine identity
	 * 
	 * @param the
	 *            HttpServletRequest
	 * @return collection of JSON objects from back end based on request
	 *         parameters
	 */
	@RequestMapping("/PRD_S01_search")
	public @ResponseBody
	List<TDCPlan> search(HttpServletRequest request) {
		List<String> list = new ArrayList<String>();
		list.add(request.getParameter("date"));
		list.add(request.getParameter("wip"));
		list.add(request.getParameter("machine"));
		return prd_S01Logic.search(list);
	}

	/**
	 * Get all reason list based on reason type 3
	 * 
	 * @param none
	 * @return collection of JSON objects
	 */
	@RequestMapping("/PRD_S01_getReasonMaster")
	public @ResponseBody
	List<MReason> getReasonMaster() {
		return prd_S01Logic.getReasonMaster();
	}

	/**
	 * Get machine objects based on user selected WIP
	 * 
	 * @param the
	 *            HttpServletRequest
	 * @return collection of JSON objects for machine
	 */
	@RequestMapping("/PRD_S01_getMachineMasterByDiecastPlanDate")
	public @ResponseBody
	List<MMachine> getMachineMaster(MMachine mMachine) {
		return prd_S01Logic.getMachineMasterByDiecastPlanDate(mMachine);
	}

	/**
	 * Get customer objects based on machine identity
	 * 
	 * @param the
	 *            HttpServletRequest
	 * @return collection of JSON objects for customers
	 */
	@RequestMapping("/PRD_S01_getCustomerMaster")
	public @ResponseBody
	List<MCustomer> getCustomerMaster(HttpServletRequest request) {
		// Change query from machineId to user name.
		return prd_S01Logic.getCustomerMaster(SecurityContextHolder
				.getContext().getAuthentication().getName());
	}

	/**
	 * Get part objects based on user selected customer identity and machine
	 * identity
	 * 
	 * @param the
	 *            HttpServletRequest
	 * @return collection of JSON objects for parts
	 */
	@RequestMapping("/PRD_S01_getPartMaster")
	public @ResponseBody
	List<MPart> getPartMaster(HttpServletRequest request) {
		List<String> param = new ArrayList<String>();
		param.add(request.getParameter("customerId"));
		param.add(request.getParameter("machineId"));
		return prd_S01Logic.getPartMaster(param);
	}

	/**
	 * Get snp information based on the user selected part identity
	 * 
	 * @param the
	 *            HttpServletRequest
	 * @return collection of objects
	 */
	@RequestMapping("/PRD_S01_getSnpByWip")
	public @ResponseBody
	List<MPart> getSnpByWip(HttpServletRequest request) {
		return prd_S01Logic.getSnpByWip(request.getParameter("partId"));
	}

	@RequestMapping("/PRD_S01_getCavByMold")
	public @ResponseBody
	List<MMold> getCavByMold(HttpServletRequest request) {
		return prd_S01Logic.getCavByMold(request.getParameter("moldId"),request.getParameter("moldNo"));
	}

	@RequestMapping("/PRD_S01_getMoldNoByMold")
	public @ResponseBody
	List<MMoldDetail> getMoldNoByMold(HttpServletRequest request) {
		return prd_S01Logic.getMoldNoByMold(request.getParameter("moldId"));
	}
	
	
	

	@RequestMapping("/PRD_S01_getMoldByPart")
	public @ResponseBody
	List<MMold> getMoldByPart(HttpServletRequest request) {
		return prd_S01Logic.getMoldByPart(request.getParameter("partId"));
	}

	/**
	 * Get st information based on user selected part identity and wip
	 * 
	 * @param the
	 *            HttpServletRequest
	 * @return collection of objects
	 */
	@RequestMapping("/PRD_S01_getSTByPartIdAndWip")
	public @ResponseBody
	List<MPart> getSTByPartIdAndWip(HttpServletRequest request) {
		List<String> param = new ArrayList<String>();
		param.add(request.getParameter("wip"));
		param.add(request.getParameter("partId"));
		return prd_S01Logic.getSTByPartIdAndWip(param);
	}

	/**
	 * Make CUD operation of die cast plan
	 * 
	 * @param the
	 *            collection of JSON objects for making CUD operations
	 * @return the information based on the status
	 */
	@RequestMapping("/PRD_S01_saveData")
	public @ResponseBody
	TDCPlan saveData(@RequestBody List<Map<String, Object>> param) {
		TDCPlan object = new TDCPlan();
		try {
			object = prd_S01Logic.saveData(param, object);
			setSaveInfo(object);
		} catch (Exception e) {
			setSystemError(object, e);
		}
		return object;
	}
}
