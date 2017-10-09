package th.co.nttdata.tki.controller.prd;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MMachine;
import th.co.nttdata.tki.bean.TDCPlan;
import th.co.nttdata.tki.blogic.prd.PRD_S03Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class PRD_S03Controller extends AbstractBaseController {

	/* A variable for indicating the next screen */
	private static final String PATH_URI = "PRD/PRD_S03";

	/* A variable for accessing business layer */
	@Autowired
	private PRD_S03Logic prd_S03Logic;

	@RequestMapping("/PRD_S03")
	public ModelAndView init() {
		TDCPlan mProduction = new TDCPlan();
		mProduction.setWipList(prd_S03Logic.getWipByWipType());
		mProduction.setMachineList(prd_S03Logic.getMachineByWip(mProduction
				.getWipList().get(0).getWip()));
		mProduction.setMachineToList(prd_S03Logic.getMachineByWip(mProduction
				.getWipList().get(0).getWip()));
		mProduction.setCustomerList(prd_S03Logic.getAllCustomer());
		return new ModelAndView(PATH_URI).addObject("mProduction", mProduction);
	}

	@RequestMapping("/PRD_S03_getMachine")
	public @ResponseBody
	List<MMachine> getMachine(HttpServletRequest request) {
		return prd_S03Logic.getMachineByWip(request.getParameter("param"));
	}

	@RequestMapping("/PRD_S03_getCustomer")
	public @ResponseBody
	List<MCustomer> getCustomerMaster(HttpServletRequest request) {
		return prd_S03Logic.getCustomer(request.getParameter("machineId"));
	}

	@RequestMapping("/PRD_S03_search")
	public @ResponseBody
	List<TDCPlan> search(@RequestBody TDCPlan param) {
		return prd_S03Logic.search(param);
	}

	@RequestMapping("/PRD_S03_save")
	public @ResponseBody
	TDCPlan save(@RequestBody List<Map<String, Object>> param) {
		TDCPlan object = new TDCPlan();
		try {
			object = prd_S03Logic.save(param);
			setSaveInfo(object);
		} catch (Exception e) {
			setSystemError(object, e);
		}
		return object;
	}

	@RequestMapping("/PRD_S03_print")
	public @ResponseBody
	TDCPlan print(@RequestBody List<Map<String, Object>> param)
			throws Exception {
		TDCPlan object = new TDCPlan();
		try {
			object = prd_S03Logic.print(param, object);
		} catch (Exception e) {
			setSystemError(object, e);
		}
		return object;
	}
}
