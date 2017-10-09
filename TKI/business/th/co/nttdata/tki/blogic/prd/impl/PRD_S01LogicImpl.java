package th.co.nttdata.tki.blogic.prd.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MLeadtime;
import th.co.nttdata.tki.bean.MMachine;
import th.co.nttdata.tki.bean.MMold;
import th.co.nttdata.tki.bean.MMoldDetail;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.TDCPlan;
import th.co.nttdata.tki.blogic.prd.PRD_S01Logic;
import th.co.nttdata.tki.dao.MCustomerDao;
import th.co.nttdata.tki.dao.MLeadtimeDao;
import th.co.nttdata.tki.dao.MMachineDao;
import th.co.nttdata.tki.dao.MMoldDao;
import th.co.nttdata.tki.dao.MPartDao;
import th.co.nttdata.tki.dao.MReasonDao;
import th.co.nttdata.tki.dao.MWipDao;
import th.co.nttdata.tki.dao.TDCPlanDao;

/**
 * 
 * @author NDTH
 * @since July 18, 2013
 */
@Service
public class PRD_S01LogicImpl implements PRD_S01Logic {

	/* A MWipDao variable for accessing database layer */
	@Autowired
	private MWipDao mWipDao;

	/* A MMachineDao variable for accessing database layer */
	@Autowired
	private MMachineDao mMachineDao;

	/* A TDCPlanDao variable for accessing database layer */
	@Autowired
	private TDCPlanDao tDCPlanDao;

	/* A MReasonDao variable for accessing database layer */
	@Autowired
	private MReasonDao mReasonDao;

	/* A MCustomerDao variable for accessing database layer */
	@Autowired
	private MCustomerDao mCustomerDao;

	/* A MPartDao variable for accessing database layer */
	@Autowired
	private MPartDao mPartDao;

	/* A MLeadtimeDao variable for accessing database layer */
	@Autowired
	private MLeadtimeDao mLeadtimeDao;
	
	@Autowired
	private MMoldDao mMoldDao;
	

	/**
	 * Get machine list from DB based on the request parameter WIP
	 * 
	 * @param the
	 *            wip identity
	 * @return collection of machine objects based on WIP
	 */
	@Override
	public List<MMachine> getMachineByWip(String param) {
		return mMachineDao.getMachineByWip(param);
	}

	/**
	 * Get all WIP from back end
	 * 
	 * @param none
	 * @return a collection of WIP objects
	 */
	@Override
	public List<MWip> getWipByWipType() {
		return mWipDao.getWipByWipType();
	}

	/**
	 * Search all die cast plan based on DCPlan date, WIP, and machine identity
	 * 
	 * @param die
	 *            cast date, wip, and machine
	 * @return collection of die cast plan objects
	 */
	@Override
	public List<TDCPlan> search(List<String> param) {
		return tDCPlanDao.search(this.getParametersToSearch(param));
	}

	/**
	 * Convert parameters to the desired object
	 * 
	 * @param the
	 *            parameters from front end
	 * @return the desired object for getting data
	 */
	private TDCPlan getParametersToSearch(List<String> param) {
		TDCPlan object = new TDCPlan();
		for (int i = 0; i < param.size(); i++) {
			if (i == 0) {
				object.setDateString(param.get(i));
			} else if (i == 1) {
				object.setWip(param.get(i));
			} else if (i == 2) {
				object.setMachineId(this.convertToInteger(param.get(i)));
			}
		}
		return object;
	}

	/**
	 * Convert the parameter string to the desired data type Return NULL value
	 * if parameter is not the desired data type Prevent also from unnecessary
	 * Exceptions
	 * 
	 * @param the
	 *            parameter string
	 * @return the desired data type
	 */
	private Integer convertToInteger(String param) {
		try {
			return Integer.parseInt(param);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Get reason information from back end
	 * 
	 * @param none
	 * @return collection of reason objects
	 */
	@Override
	public List<MReason> getReasonMaster() {
		return mReasonDao.getReasonMaster();
	}

	/**
	 * Get machine information based on user selected wip
	 * 
	 * @param the
	 *            wip identity
	 * @return the collection of machine information
	 */
	@Override
	public List<MMachine> getMachineMaster(String param) {
		return mMachineDao.getMachineByWip(param);
	}

	/**
	 * Get customer information based on the machine identity
	 * 
	 * @param the
	 *            machine identity
	 * @return the collection of customer objects
	 */
	@Override
	public List<MCustomer> getCustomerMaster(String param) {
		return mCustomerDao.getCustomerMaster(param);
	}

	/**
	 * Get part information based on user selected customer identity and machine
	 * identity
	 * 
	 * @param the
	 *            collection of parameter strings
	 * @return collection of part objects
	 */
	@Override
	public List<MPart> getPartMaster(List<String> param) {
		// Add new query for get part master with has a relation with machine.
		List<MPart> mpartMapping = mPartDao.getPartMasterWithRelation(this
				.getParameterForPartMaster(param));
		if (mpartMapping.size() > 0) {
			return mpartMapping;
		} else {
			return mPartDao
					.getPartMaster(this.getParameterForPartMaster(param));
		}
	}

	/**
	 * Convert data to the desired data type for aceessing to back end
	 * 
	 * @param the
	 *            collection of list strings
	 * @return the part object for accessing back end
	 */
	private MPart getParameterForPartMaster(List<String> list) {
		MPart object = new MPart();
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				object.setCustomerId(this.convertToInteger(list.get(i)));
			} else if (i == 1) {
				object.setMachineId(this.convertToInteger(list.get(i)));
			}
		}
		return object;
	}

	/**
	 * Get snp based on the user selected wip
	 * 
	 * @param the
	 *            wip identity
	 * @return the collection of part objects
	 */
	@Override
	public List<MPart> getSnpByWip(String param) {
		return mPartDao.getSnpByWip(this.convertToInteger(param));
	}
	

	@Override
	public List<MMold> getCavByMold(String moldId, String moldNo) {
		return mMoldDao.getCavByMold(this.convertToInteger(moldId), moldNo);
	}
	

	/**
	 * Get the st information based on user selected part identity and wip
	 * 
	 * @param the
	 *            collection of parameters string
	 * @return the collection of part information
	 */
	@Override
	public List<MPart> getSTByPartIdAndWip(List<String> param) {
		MLeadtime object = new MLeadtime();
		for (int i = 0; i < param.size(); i++) {
			if (i == 0) {
				object.setWip(param.get(i));
			} else if (i == 1) {
				object.setPartId(this.convertToInteger(param.get(i)));
			}
		}
		return mLeadtimeDao.getSTByPartIdAndWip(object);
	}

	/**
	 * Make CUD operation based on the statusFlag
	 * 
	 * @param the
	 *            collection of JSON objects
	 * @return none
	 */
	@Override
	public TDCPlan saveData(List<Map<String, Object>> param, TDCPlan object)
			throws Exception {
		List<TDCPlan> insertList = new ArrayList<TDCPlan>();
		List<TDCPlan> updateList = new ArrayList<TDCPlan>();
		List<TDCPlan> deleteList = new ArrayList<TDCPlan>();
		List<TDCPlan> data = this.convertToSave(param);

		if (object.getErrors().size() == 0) {
			for (int i = 0; i < data.size(); i++) {
				if (data.get(i).isInsert()) {
					insertList.add(data.get(i));
				} else if (data.get(i).isUpdate()) {
					updateList.add(data.get(i));
				} else if (data.get(i).isDelete()) {
					deleteList.add(data.get(i));
				}
			}
			if (!insertList.isEmpty()) {
				object.setPlanList(insertList);
				tDCPlanDao.insertDCPlan(object);
			}
			if (!updateList.isEmpty()) {
				object.setPlanList(updateList);
				tDCPlanDao.updateDCPlan(object);
			}
			if (!deleteList.isEmpty()) {
				object.setPlanList(deleteList);
				tDCPlanDao.deleteDCPlan(object);
			}
		}
		return object;
	}

	/**
	 * Convert collection of JSON objects to the desired objects for making CUD
	 * operations
	 * 
	 * @param the
	 *            collection of JSON objects
	 * @return collection of the desired part objects to make CUD operations
	 * @throws Exception
	 */
	private List<TDCPlan> convertToSave(List<Map<String, Object>> param)
			throws Exception {
		List<TDCPlan> list = new ArrayList<TDCPlan>();
		TDCPlan object = null;
		for (int i = 0; i < param.size(); i++) {
			object = new TDCPlan();
			BeanUtils.populate(object, param.get(i));
			list.add(object);
		}
		
		return list;
	}

	@Override
	public List<MMachine> getMachineMasterByDiecastPlanDate(MMachine mMachine) {
		return mMachineDao.getMachineMasterByDiecastPlanDate(mMachine);
	}

	@Override
	public List<MMold> getMoldByPart(String param) {
		return mMoldDao.getMoldByPart(this.convertToInteger(param));
	}

	@Override
	public List<MMoldDetail> getMoldNoByMold(String moldId) {
		return mMoldDao.getMoldNoByMold(this.convertToInteger(moldId));
	}
}