package th.co.nttdata.tki.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.CustomerLine;
import th.co.nttdata.tki.bean.filter.MCustomerFilter;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MCustomerLineDao;

@Repository
public class MCustomerLineDaoImpl extends AbstractBaseDao implements MCustomerLineDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerLine> getCustomerLineSearchList(CustomerLine customerLine) {
		List<CustomerLine> customerLineList = new ArrayList<CustomerLine>();
		customerLineList = (queryForList("m_customer_line.select_Init", customerLine));
		return customerLineList;
	}

	@Override
	public void insertCustomerLine(CustomerLine customerLine) {
		insert("m_customer_line.insert_cust_line", customerLine);
	}

	@Override
	public void updateCustomerLine(CustomerLine customerLine) {
		update("m_customer_line.update_cust", customerLine);
	}

	@Override
	public void deleteCustomerLine(CustomerLine customerLine) {
		delete("m_customer_line.delete_cust_line", customerLine);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MCustomerFilter> getCustomerLineList(MCustomerFilter customerLine) {
		return queryForList("m_customer_line.select_export", customerLine);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CustomerLine searchCustomerLine(CustomerLine custLine) {
		custLine.setCustomerLineList(queryForList("m_customer_line.select_search",
				custLine));
		return custLine;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerLine> getCustomerList() {
		return queryForList("m_customer_line.select_part");
	}

}
