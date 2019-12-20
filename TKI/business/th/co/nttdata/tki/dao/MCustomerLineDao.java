package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.CustomerLine;
import th.co.nttdata.tki.bean.filter.MCustomerFilter;

public interface MCustomerLineDao {
	public List<CustomerLine> getCustomerLineSearchList(CustomerLine CustomerLine);

	public List<MCustomerFilter> getCustomerLineList(MCustomerFilter fgCustomer);

	public void insertCustomerLine(CustomerLine CustomerLine);

	public void updateCustomerLine(CustomerLine CustomerLine);

	public void deleteCustomerLine(CustomerLine CustomerLine);

	public CustomerLine searchCustomerLine(CustomerLine CustomerLine);

	public List<CustomerLine> getCustomerList();

}
