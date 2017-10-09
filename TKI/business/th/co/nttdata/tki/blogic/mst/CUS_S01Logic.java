package th.co.nttdata.tki.blogic.mst;

import java.util.LinkedHashMap;
import java.util.List;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.filter.MCustomerFilter;

public interface CUS_S01Logic {

	public List<MCustomer> search(MCustomerFilter MCustomer);

	public List<MCustomer> search();

	public void delete(MCustomer MCustomer);

	public void save(List<LinkedHashMap> mCustomers) throws Exception;

	public void save(MCustomer MCustomer);

	public void insert(MCustomer MCustomer);
}
