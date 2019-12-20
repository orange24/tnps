package th.co.nttdata.tki.blogic.mst.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.CustomerLine;
import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.mst.CUS_S03Logic;
import th.co.nttdata.tki.dao.MCustomerDao;
import th.co.nttdata.tki.dao.MCustomerLineDao;

@Service
public class CUS_S03LogicImpl extends AbstractBaseLogic implements
		CUS_S03Logic {

	@Autowired
	private MCustomerDao mCustommerDao;
	@Autowired
	private MCustomerLineDao mCustomerLineDao;

	@Override
	public MCustomer getAllCustomer() {
		MCustomer mCustomer = new MCustomer();
		mCustomer.setCustomerList(mCustommerDao.getCustomerList());
		return mCustomer;
	}

	@Override
	public CustomerLine searchCustomerLineByCustomerId(CustomerLine CustomerLine) {
		CustomerLine = mCustomerLineDao.searchCustomerLine(CustomerLine);
		return CustomerLine;
	}

	@Override
	public void saveCustomerLine(List<CustomerLine> customerLineList) {
		CustomerLine customerLine = new CustomerLine();
		customerLine.setCustomerLineList(customerLineList);
		mCustomerLineDao.deleteCustomerLine(customerLine);
		List<CustomerLine> insertList = new ArrayList<CustomerLine>();
		for (CustomerLine tmp : customerLine.getCustomerLineList()) {
			if (!tmp.isDelete()) {
				insertList.add(tmp);
			}
		}
		customerLine.setCustomerLineList(insertList);
		mCustomerLineDao.insertCustomerLine(customerLine);
	}

	@Override
	public List<CustomerLine> getCustomerLineList() {
		return mCustomerLineDao.getCustomerList();
	}
}
