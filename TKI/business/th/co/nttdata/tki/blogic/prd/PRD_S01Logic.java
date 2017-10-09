package th.co.nttdata.tki.blogic.prd;

import java.util.List;
import java.util.Map;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MMachine;
import th.co.nttdata.tki.bean.MMold;
import th.co.nttdata.tki.bean.MMoldDetail;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.TDCPlan;

/**
 * 
 * @author NDTH
 * @since July 18, 2013
 */
public interface PRD_S01Logic {

	/**
	 * Get machine list from DB based on the request parameter WIP
	 * 
	 * @param the
	 *            wip identity
	 * @return collection of machine objects based on WIP
	 */
	List<MMachine> getMachineByWip(String param);

	/**
	 * Get all WIP from back end
	 * 
	 * @param none
	 * @return a collection of WIP objects
	 */
	List<MWip> getWipByWipType();

	/**
	 * Search all die cast plan based on DCPlan date, WIP, and machine identity
	 * 
	 * @param die
	 *            cast date, wip, and machine
	 * @return collection of die cast plan objects
	 */
	List<TDCPlan> search(List<String> param);

	/**
	 * Get reason information from back end
	 * 
	 * @param none
	 * @return collection of reason objects
	 */
	List<MReason> getReasonMaster();

	/**
	 * Get machine information based on user selected wip
	 * 
	 * @param the
	 *            wip identity
	 * @return the collection of machine information
	 */
	List<MMachine> getMachineMaster(String param);

	/**
	 * Get customer information based on the machine identity
	 * 
	 * @param the
	 *            machine identity
	 * @return the collection of customer objects
	 */
	List<MCustomer> getCustomerMaster(String param);

	/**
	 * Get part information based on user selected customer identity and machine
	 * identity
	 * 
	 * @param the
	 *            collection of parameter strings
	 * @return collection of part objects
	 */
	List<MPart> getPartMaster(List<String> param);

	/**
	 * Get snp based on the user selected wip
	 * 
	 * @param the
	 *            wip identity
	 * @return the collection of part objects
	 */
	List<MPart> getSnpByWip(String parameter);
	
	List<MMold> getCavByMold(String parameter1, String parameter2);
	
	List<MMoldDetail> getMoldNoByMold(String parameter);
	
	List<MMold> getMoldByPart(String parameter);
	
	/**
	 * Get the st information based on user selected part identity and wip
	 * 
	 * @param the
	 *            collection of parameters string
	 * @return the collection of part information
	 */
	List<MPart> getSTByPartIdAndWip(List<String> param);

	/**
	 * Make CUD operation based on the statusFlag
	 * 
	 * @param object
	 * 
	 * @param the
	 *            collection of JSON objects
	 * @return none
	 */
	TDCPlan saveData(List<Map<String, Object>> param, TDCPlan object)
			throws Exception;

	List<MMachine> getMachineMasterByDiecastPlanDate(MMachine mMachine);
}
