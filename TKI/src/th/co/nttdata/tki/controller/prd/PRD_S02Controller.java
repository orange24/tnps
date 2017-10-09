package th.co.nttdata.tki.controller.prd;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MMachine;
import th.co.nttdata.tki.bean.TDCPlan;
import th.co.nttdata.tki.blogic.prd.PRD_S02Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

/**
 * 
 * @author NDTH
 * @since July 29, 2013
 * 
 */
@Controller
public class PRD_S02Controller extends AbstractBaseController {

	/* A variable for indicating the next screen */
	private static final String PATH_URI = "PRD/PRD_S02";

	/* A variable for accessing business layer */
	@Autowired
	private PRD_S02Logic prd_S02Logic;

	/**
	 * Indicate the page to choose
	 * 
	 * @return the model and view for indication the screen
	 * @param none
	 */
	@RequestMapping("/PRD_S02")
	public ModelAndView init() {
		/* Set initial values for form */
		TDCPlan mProduction = prd_S02Logic.getDefaultValues();
		mProduction.setWipList(prd_S02Logic.getWipByWipType());
		mProduction.setReasonList(prd_S02Logic.getRemark());
		mProduction.setCustomerList(prd_S02Logic
				.getCustomer(SecurityContextHolder.getContext()
						.getAuthentication().getName()));
		return new ModelAndView(PATH_URI).addObject("mProduction", mProduction);
	}

	/**
	 * Get machine list from DB based on the request parameter WIP Call by
	 * Asynchronous and Javascrip XML
	 * 
	 * @param the
	 *            HttpServletRequest
	 * @return collection of JSON objects for machine based on WIP
	 */
	@RequestMapping("/PRD_S02_getMachine")
	public @ResponseBody
	List<MMachine> getMachine(HttpServletRequest request) {
		return prd_S02Logic.getMachineByWip(request.getParameter("param"));
	}

	/**
	 * Get customer objects based on machine identity Call by Asynchronous and
	 * Javascrip XML
	 * 
	 * @param the
	 *            HttpServletRequest
	 * @return collection of JSON objects for customers
	 */
	@RequestMapping("/PRD_S02_getCustomer")
	public @ResponseBody
	List<MCustomer> getCustomerMaster(HttpServletRequest request) {
		return prd_S02Logic.getCustomer(SecurityContextHolder.getContext()
				.getAuthentication().getName());
	}

	/**
	 * Search the collection of TDCPlan objects based on the form
	 * 
	 * @param the
	 *            form object
	 * @return the MV with the form for redisplaying the values
	 */
	@RequestMapping("/PRD_S02_search")
	public ModelAndView search(TDCPlan mProduction) {
		mProduction.setPartNo(mProduction.getPartNo().trim());
		mProduction.setPartName(mProduction.getPartName().trim());
		if (org.apache.commons.lang.StringUtils
				.isNotEmpty(mProduction.getWip())) {
			if ("--All--".equals(mProduction.getWip())) {
				mProduction.setWip(null);
			}
		}
		return new ModelAndView(PATH_URI).addObject("mProduction",
				prd_S02Logic.getFormValue(mProduction));
	}

	/**
	 * Get export information based on filtered rows
	 * 
	 * @param the
	 *            from object to search the consistent objects for exporting
	 *            data
	 * @return the model and view for indication the screen
	 * @throws Exception
	 */
	@RequestMapping(value = "/PRD_S02_export", method = RequestMethod.POST)
	public ModelAndView export(TDCPlan mProduction) throws Exception {
		mProduction.setPartNo(mProduction.getPartNo().trim());
		mProduction.setPartName(mProduction.getPartName().trim());
		if (org.apache.commons.lang.StringUtils
				.isNotEmpty(mProduction.getWip())) {
			if ("--All--".equals(mProduction.getWip())) {
				mProduction.setWip(null);
			}
		}
		return new ModelAndView("PRD_S02ExcelView").addObject("data",
				prd_S02Logic.getExportData(mProduction).getPlanList());
	}

	/**
	 * Get generate information based on generate status
	 * 
	 * @param request
	 *            : HttpServletRequest for getting request parameters
	 * @return the model and view for indication the screen
	 * @throws Exception
	 */
	@RequestMapping(value = "/PRD_S02_generate", method = RequestMethod.POST)
	public ModelAndView generate(TDCPlan mProduction) throws Exception {
		try {
			setSaveInfo(mProduction);
			mProduction.setPartNo(mProduction.getPartNo().trim());
			mProduction.setPartName(mProduction.getPartName().trim());
			if (org.apache.commons.lang.StringUtils.isNotEmpty(mProduction
					.getWip())) {
				if ("--All--".equals(mProduction.getWip())) {
					mProduction.setWip(null);
				}
			}
			new ModelAndView(PATH_URI).addObject("mProduction",
					prd_S02Logic.generateWorkOrderNoAndLotNo(mProduction));
		} catch (Exception e) {
			setSystemError(mProduction, e);
		}
		/* Return the form if exception occured */
		return new ModelAndView(PATH_URI).addObject("mProduction",
				prd_S02Logic.getFormValue(mProduction));
	}

}
