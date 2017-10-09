package th.co.nttdata.tki.blogic.prd.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MMachine;
import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.bean.TDCPlan;
import th.co.nttdata.tki.blogic.prd.PRD_S02Logic;
import th.co.nttdata.tki.dao.MCustomerDao;
import th.co.nttdata.tki.dao.MMachineDao;
import th.co.nttdata.tki.dao.MReasonDao;
import th.co.nttdata.tki.dao.MWipDao;
import th.co.nttdata.tki.dao.MWorkOrderDao;
import th.co.nttdata.tki.dao.TDCPlanDao;
import th.co.nttdata.tki.dao.TLotNoDao;
import th.co.nttdata.tki.dao.TLotnoGentmpDao;

/**
 * 
 * @author NDTH
 * @since July 29, 2013
 * 
 */
@Service
public class PRD_S02LogicImpl implements PRD_S02Logic {

	/* A MWipDao variable for accessing database layer */
	@Autowired
	private MWipDao mWipDao;

	/* A MReasonDao variable for accessing database layer */
	@Autowired
	private MReasonDao mReasonDao;

	/* A MMachineDao variable for accessing database layer */
	@Autowired
	private MMachineDao mMachineDao;

	/* A MCustomerDao variable for accessing database layer */
	@Autowired
	private MCustomerDao mCustomerDao;

	/* A TDCPlanDao variable for accessing database layer */
	@Autowired
	private TDCPlanDao tDCPlanDao;

	/* A MWorkOrderDao variable for accessing database layer */
	@Autowired
	private MWorkOrderDao mWorkOrderDao;

	/* A TLotNoDao variable for accessing database layer */
	@Autowired
	private TLotNoDao tLotNoDao;

	/* A TLotnoGentmpDao variable for accessing database layer */
	@Autowired
	private TLotnoGentmpDao tLotnoGentmpDao;

	/**
	 * Get all WIP from back end
	 * 
	 * @param none
	 * @return a collection of WIP objects
	 */
	@Override
	public List<MWip> getWipByWipType() {
		List<MWip> list = mWipDao.getWipByWipType();
		if (list != null) {
			list.add(0, new MWip("--All--", null));
		} else {
			list = new ArrayList<MWip>();
			list.add(new MWip("--All--", null));
		}
		return list;
	}

	/**
	 * Get reason information from back end
	 * 
	 * @param none
	 * @return collection of reason objects
	 */
	@Override
	public List<MReason> getRemark() {
		List<MReason> list = mReasonDao.getReasonMaster();
		if (list != null) {
			list.add(0, new MReason(null, "--All--"));
		} else {
			list = new ArrayList<MReason>();
			list.add(new MReason(null, "--All--"));
		}
		return list;
	}

	/**
	 * Get the collection of machine objects based on wip
	 * 
	 * @param the
	 *            parameter string for searching
	 * @return the collection of machine objects for list box
	 */
	@Override
	public List<MMachine> getMachineByWip(String param) {
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
	public List<MCustomer> getCustomer(String param) {
		List<MCustomer> list = mCustomerDao.getCustomerMaster(param);
		if (list != null) {
			list.add(0, new MCustomer(null, "--All--", null));
		} else {
			list = new ArrayList<MCustomer>();
			list.add(new MCustomer(null, "--All--", null));
		}
		return list;
	}

	/**
	 * Get form data for redisplaying the values in the screen Specify the
	 * customer identity because action is submit action
	 * 
	 * @param the
	 *            form object for searching values
	 * @return the form object for redisplaying values
	 */
	@Override
	public TDCPlan getFormValue(TDCPlan mProduction) {
		mProduction.setWipList(this.getWipByWipType());
		mProduction.setReasonList(this.getRemark());
		mProduction.setMachineList(this.getMachineByWip(mProduction.getWip()));
		mProduction.getMachineList().add(0, new MMachine(null, "--All--"));
		mProduction.setCustomerList(this.getCustomer(SecurityContextHolder
				.getContext().getAuthentication().getName()));
		mProduction.setPlanList(this.search(mProduction).getPlanList());
		return mProduction;
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
	 * Search the list of TDCPlan objects
	 * 
	 * @param the
	 *            form object for searching
	 * @return the form object for display the collection of objects
	 */
	@Override
	public TDCPlan search(TDCPlan param) {
		return tDCPlanDao.prdS02Search(param);
	}

	/**
	 * Get list of TDCPlan objects for export
	 * 
	 * @param the
	 *            form object for search the list
	 * @return the form object for redirecting the MV
	 */
	@Override
	public TDCPlan getExportData(TDCPlan object) {
		return tDCPlanDao.getExportData(object);
	}

	/**
	 * Set customer list and machine list for initial screen
	 * 
	 * @param none
	 * @return the form object for displaying
	 */
	@Override
	public TDCPlan getDefaultValues() {
		TDCPlan object = new TDCPlan();
		List<MCustomer> customerList = new ArrayList<MCustomer>();
		customerList.add(new MCustomer(null, "--All--", null));
		object.setCustomerList(customerList);

		List<MMachine> machineList = new ArrayList<MMachine>();
		machineList.add(new MMachine(null, "--All--"));
		object.setMachineList(machineList);

		return object;
	}

	/**
	 * Generate work orders and lot numbers
	 * 
	 * @param the
	 *            form parameter
	 * @return the form results
	 */
	@Override
	public TDCPlan generateWorkOrderNoAndLotNo(TDCPlan param) {
		List<TDCPlan> list = tDCPlanDao.getDataForGenerate(param);
		if (!list.isEmpty()) {
			for (TDCPlan object : list) {
				TDCPlan saveObject = new TDCPlan();
				try {
					BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
					BeanUtils.copyProperties(saveObject, object);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				/* Check generate status for false */
				if (!saveObject.getGenStatus()) {
					/* check snp is not equal zero */
					if (saveObject.getSnpWip() == 0) {
						saveObject.setSnpWip(saveObject.getQuantity());
					}
					/* check qty/snp mustn't over 999 */
					if ((saveObject.getQuantity() / saveObject.getSnpWip()) < 999) {
						/* Set generated work order */
						saveObject.setWorkOrderNo(generateWorkOrder(saveObject));
						/* Set lot quantity */
						saveObject.setRecordFound(getLotTotal(saveObject));
						/* Set start lot number */
						saveObject.setStartLotNo("001");
						/* Set end lot number */
						saveObject.setEndLotNo(getLotNo(saveObject
								.getRecordFound()));
						/* insert work order 1 Line */
						mWorkOrderDao.insertWorkOrder(saveObject);
						List<List<TDCPlan>> totalLotNo = this
								.generateLotNo(saveObject);
						for (List<TDCPlan> tmp : totalLotNo) {
							TDCPlan tdcp = new TDCPlan();
							tdcp.setPlanList(tmp);
							tLotNoDao.insertLotNo(tdcp);
						}
						tLotnoGentmpDao.updateMergeLotNo();
						tDCPlanDao.updateGenStatus(saveObject);
					} else {
						param.getErrors().add(
								new Message("err.prd.008", new String[] {
										saveObject.getDcPlanDate(),
										saveObject.getWip(), saveObject.getMachineNo(),
										saveObject.getSeq() + "",
										saveObject.getShift(),
										saveObject.getCustomerCode(),
										saveObject.getPartNo() }));
					}
				}
			}
		}
		return getFormValue(param);
	}

	/**
	 * Get lot total based on quantity and snpWip
	 * 
	 * @param the
	 *            form object
	 * @return the integer value or null value to search data from DB
	 */
	private Integer getLotTotal(TDCPlan object) {
		try {
			// Change for check SnpWip because SnpWip can be 0, if SnpWip is 0
			// LotTotal is maximum value of integer.
			return (int) Math.ceil(object.getQuantity()
					/ (double) object.getSnpWip());

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Generate lot number for the records Combine work order number and lot
	 * number Generate sequence of lot number if quantity/snpWip > 1
	 * 
	 * @param the
	 *            collection of generated TDCPlan objects with the generated
	 *            work order
	 * @return the collection of generated TDCPlan objects for the lot numbers
	 */
	private List<List<TDCPlan>> generateLotNo(TDCPlan object) {
		int count = 0;
		TDCPlan obj = null;
		List<List<TDCPlan>> totalLot = new ArrayList<List<TDCPlan>>();
		List<TDCPlan> dataList = new ArrayList<TDCPlan>();
		if (object.getRecordFound() == 1) {
			object.setLotNo(object.getWorkOrderNo()
					+ this.getLotNo(object.getRecordFound()));
			dataList.add(object);
		} else {
			count = 0;
			for (int i = 1; i <= object.getRecordFound(); i++) {
				count++;
				obj = new TDCPlan();
				obj.setWorkOrderNo(object.getWorkOrderNo());
				obj.setDcPlanId(object.getDcPlanId());
				obj.setRecordFound(object.getRecordFound());
				obj.setLotNo(object.getWorkOrderNo() + this.getLotNo(count));
				if (i == object.getRecordFound()) {
					if ((object.getQuantity() % object.getSnpWip()) != 0) {
						obj.setQuantity(object.getQuantity()
								% object.getSnpWip());
					} else {
						obj.setQuantity(object.getSnpWip());
					}
				} else {
					obj.setQuantity(object.getSnpWip());
				}
				dataList.add(obj);
				/* split lot not over 400 */
				if (dataList.size() % 400 == 0) {
					totalLot.add(dataList);
					dataList = new ArrayList<TDCPlan>();
				}
			}
		}
		totalLot.add(dataList);
		return totalLot;
	}

	/**
	 * Generate lot number for 3 digit
	 * 
	 * @param the
	 *            integer lotNum value
	 * @return the formatted lot number for the work order
	 */
	private String getLotNo(Integer lotNum) {
		StringBuilder builder = new StringBuilder();
		String st = lotNum.toString();
		for (int i = st.length(); i < 3; i++) {
			builder.append("0");
		}
		builder.append(st);
		return builder.toString();
	}

	/**
	 * Generate work order based on the genStatus : false Generate work order
	 * from DB values of each record
	 * 
	 * @param the
	 *            record from DB
	 * @return the generated work order number for the specified record
	 */
	private String generateWorkOrder(TDCPlan object) {
		StringBuilder builder = new StringBuilder();
		builder.append(getMaterial(object));
		builder.append(getMachineNo(object));
		builder.append(getDateValue(object));
		builder.append(object.getShift());
		builder.append("X");
		builder.append("-");
		builder.append(object.getSeq());
		return builder.toString();
	}

	/**
	 * Get the first character of material code for the record from DB Generate
	 * the material for work order and lot number
	 * 
	 * @param the
	 *            record from DB
	 * @return the generated material code
	 */
	private String getMaterial(TDCPlan object) {
		return (object.getMaterialGroup() != null) ? object.getMaterialGroup()
				: "A";
	}

	/**
	 * Get machine number for the record from DB Get the last two characters of
	 * machine number
	 * 
	 * @param the
	 *            record from DB
	 * @return the substring value of machine number
	 */
	private String getMachineNo(TDCPlan object) {
		return (object.getMachineNo() != null && object.getMachineNo().length() > 2) ? object
				.getMachineNo().substring(object.getMachineNo().length() - 2,
						object.getMachineNo().length()) : "??";
	}

	/**
	 * Get year, month, and day for the generated work order number and lot
	 * number
	 * 
	 * @param the
	 *            record from DB
	 * @return the pre specified value of YY-MM-DD string
	 */
	private String getDateValue(TDCPlan object) {
		StringBuilder builder = new StringBuilder();
		if (object.getDcPlanDate() != null) {
			List<String> list = Arrays
					.asList(object.getDcPlanDate().split("/"));
			if (list.size() == 3) {
				for (int i = list.size() - 1; i >= 0; i--) {
					if (i == 0) {
						if (10 > Integer.parseInt(list.get(i))) {
							builder.append("0");
							builder.append(Integer.parseInt(list.get(i)));
						} else {
							builder.append(Integer.parseInt(list.get(i)));
						}
					} else if (i == 1) {
						int temp = Integer.parseInt(list.get(i));
						if (temp >= 10) {
							if (temp == 10)
								builder.append("X");
							else if (temp == 11)
								builder.append("Y");
							else if (temp == 12)
								builder.append("Z");
						} else {
							builder.append(temp);
						}
					} else if (i == 2) {
						builder.append(list.get(i).substring(
								list.get(i).length() - 1, list.get(i).length()));
					}
				}
			}
		} else {
			builder.append("???");
		}
		return builder.toString();
	}

	/**
	 * Get form object form redisplaying values Get list of TDCPlan objects for
	 * showing in table
	 * 
	 * @param the
	 *            form parameter
	 * @return the form for redisplaying values
	 */
	@Override
	public TDCPlan getScreenValues(TDCPlan param) {
		TDCPlan object = new TDCPlan();
		object.setMachineList(this.getMachineByWip(param.getWip()));
		object.setCustomerList(object.getCustomerList());
		object.setMachineList(object.getMachineList());
		object.setWipList(this.getWipByWipType());
		object.setReasonList(this.getRemark());
		object.setPlanList(this.search(param).getPlanList());
		return object;
	}
}
