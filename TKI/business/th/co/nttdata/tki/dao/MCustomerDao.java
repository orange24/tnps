package th.co.nttdata.tki.dao;

import java.util.List;
import java.util.Map;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.filter.MCustomerFilter;

public interface MCustomerDao {

	public MCustomer getCustomer(MCustomer mCustomer);

	public List<MCustomer> getCustomerList();

	public List<MCustomer> getCustomerList(MCustomerFilter mCustomer);

	public void delete(MCustomer mCustomer);

	public void save(MCustomer mCustomer);

	public Integer merge(MCustomer customer);

	public List<MCustomer> getTpicCustomerList();

	public void insert(MCustomer mCustomer);

	public MCustomer getCustomerTpicList(MCustomer mCustomer);

	public MCustomer getMCustomer(Integer customerId);

	public List<MCustomer> getCustomerMaster(String param);

	public List<MCustomer> getAllCustomer();

	Map<String, Integer> getCustomerMap();

}