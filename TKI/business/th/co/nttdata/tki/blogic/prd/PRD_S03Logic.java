package th.co.nttdata.tki.blogic.prd;

import java.util.List;
import java.util.Map;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MMachine;
import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.TDCPlan;

public interface PRD_S03Logic {

	/**
	 * Get all WIP from back end
	 * 
	 * @param none
	 * @return a collection of WIP objects
	 */
	List<MWip> getWipByWipType();

	/**
	 * Get machine list from DB based on the request parameter WIP
	 * 
	 * @param the
	 *            wip identity
	 * @return collection of machine objects based on WIP
	 */
	List<MMachine> getMachineByWip(String param);

	/**
	 * Get customer information based on the machine identity
	 * 
	 * @param the
	 *            machine identity
	 * @return the collection of customer objects
	 */
	List<MCustomer> getCustomer(String param);

	/**
	 * Set customer list and machine list for initial screen
	 * 
	 * @param none
	 * @return the form object for displaying
	 */
	TDCPlan getDefaultValues();

	List<TDCPlan> search(TDCPlan param);

	TDCPlan save(List<Map<String, Object>> param) throws Exception;

	TDCPlan print(List<Map<String, Object>> param, TDCPlan object)
			throws Exception;

	List<MCustomer> getAllCustomer();
}
