package th.co.nttdata.tki.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.filter.MCustomerFilter;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MCustomerDao;

@Repository
@SuppressWarnings("unchecked")
public class MCustomerDaoImpl extends AbstractBaseDao implements MCustomerDao {

	@Value("#{settings['CMM.linkedServer']}")
	private final String linkedServer = "\\\\172.16.12.200\\pipe\\sql\\query";

	@Override
	public MCustomer getCustomer(MCustomer mCustomer) {
		return (MCustomer) queryForObject("m_customer.queryCustomer", mCustomer);
	}

	@Override
	public List<MCustomer> getCustomerList() {
		MCustomer MCustomer = new MCustomer();
		MCustomer.setUpdateBy(getUsername());
		List<MCustomer> result = queryForList("m_customer.queryCustomer", MCustomer);
		return result; 
	}

	@Override
	public List<MCustomer> getCustomerList(MCustomerFilter mCustomer) {
		return queryForList("m_customer.select_export", mCustomer);
	}

	@Override
	public List<MCustomer> getTpicCustomerList() {
		// // return queryForList("m_customer.queryTpicCustomer",
		// // linkedServer);
		// // //settings.getProperty("CMM.linkedServer",
		// // "\\\\172.16.12.200\\pipe\\sql\\query"));
		return null;
	}

	@Override
	public void delete(MCustomer mCustomer) {
		delete("m_customer.delete_cust", mCustomer);
	}

	@Override
	public void save(MCustomer mCustomer) {
		update("m_customer.update_cust", mCustomer);
	}

	@Override
	public Integer merge(MCustomer customer) {
		return (Integer) queryForObject("m_customer.merge", customer);
	}

	@Override
	public void insert(MCustomer mCustomer) {
		insert("m_customer.insert_cust", mCustomer);
	}

	@Override
	public MCustomer getCustomerTpicList(MCustomer mCustomer) {
		// mCustomer.setLinkDB(linkedServer);
		// mCustomer.setCustomerList(queryForList(
		// "m_customer.queryCustomerTpicSearch", mCustomer,
		// getSkipResult(mCustomer), mCustomer.getPageCount()));
		// calPageTotal("m_customer.countTpic", mCustomer);
		return mCustomer;
	}

	@Override
	public MCustomer getMCustomer(Integer customerId) {
		return (MCustomer) queryForObject("m_customer.queryMCustomer", customerId);
	}

	@Override
	public List<MCustomer> getCustomerMaster(String param) {
		return queryForList("m_customer.select_customer", param);
	}

	@Override
	public List<MCustomer> getAllCustomer() {
		return queryForList("m_customer.prdS03_select_customer");
	}

	@Override
	public Map<String, Integer> getCustomerMap() {
		List<MCustomer> mCustomers = getAllCustomer();
		Map<String, Integer> customerMap = new HashMap<String, Integer>();
		for (MCustomer customer : mCustomers) {
			customerMap.put(customer.getCustomerCode(), customer.getCustomerId());
		}

		return customerMap;
	}

}