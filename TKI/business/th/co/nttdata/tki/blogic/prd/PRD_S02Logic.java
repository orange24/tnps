package th.co.nttdata.tki.blogic.prd;

import java.util.List;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MMachine;
import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.TDCPlan;

public interface PRD_S02Logic {

	/**
	 * Get all WIP from back end 
	 * @param none
	 * @return a collection of WIP objects 
	 */
	List<MWip> getWipByWipType();

	/**
	 * Get reason information from back end
	 * @param none
	 * @return collection of reason objects
	 */
	List<MReason> getRemark();
	
	/**
	 * Get machine list from DB based on the request parameter WIP
	 * @param the wip identity
	 * @return collection of machine objects based on WIP
	 */
	List<MMachine> getMachineByWip(String param);
	
	/**
	 * Get customer information based on the machine identity
	 * @param the machine identity
	 * @return the collection of customer objects
	 */
	List<MCustomer> getCustomer(String param);
	
	/**
	 * Get form data for redisplaying the values in the screen
	 * Specify the customer identity because action is submit action
	 * @param the form object for searching values
	 * @return the form object for redisplaying values
	 */
	TDCPlan getFormValue(TDCPlan mProduction);
	
	/**
	 * Search the list of TDCPlan objects 
	 * @param the form object for searching 
	 * @return the form object for display the collection of objects
	 */
	TDCPlan search(TDCPlan parameter);

	/**
	 * Get list of TDCPlan objects for export
	 * @param the form object for search the list
	 * @return the form object for redirecting the MV
	 */
	TDCPlan getExportData(TDCPlan mPart);
	
	/**
	 * Set customer list and machine list for initial screen
	 * @param none
	 * @return the form object for displaying
	 */
	TDCPlan getDefaultValues();
	
	/**
	 * Generate work orders and lot numbers
	 * @param the form parameter
	 * @return the form results
	 */
	TDCPlan generateWorkOrderNoAndLotNo(TDCPlan param);
	
	/**
	 * Get form object form redisplaying values
	 * Get list of TDCPlan objects for showing in table
	 * @param the form parameter
	 * @return the rediplay from value
	 */
	TDCPlan getScreenValues(TDCPlan object);
	
}
