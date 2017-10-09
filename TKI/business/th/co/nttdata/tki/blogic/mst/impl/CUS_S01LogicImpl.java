package th.co.nttdata.tki.blogic.mst.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.filter.MCustomerFilter;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.mst.CUS_S01Logic;
import th.co.nttdata.tki.dao.MCustomerDao;

@Service
public class CUS_S01LogicImpl extends AbstractBaseLogic implements CUS_S01Logic {

	@Autowired
	private MCustomerDao mCustommerDao;

	@Override
	public List<MCustomer> search(MCustomerFilter mCustomerFilter) {
		MCustomer mCustomer = new MCustomer();
		mCustomer.setCustomerList(mCustommerDao
				.getCustomerList(mCustomerFilter));
		return mCustomer.getCustomerList();
	}

	@Override
	public List<MCustomer> search() {
		return mCustommerDao.getCustomerList();
	}

	@Override
	public void delete(MCustomer mCustomer) {
		mCustommerDao.delete(mCustomer);
	}

	@Override
	public void save(MCustomer mCustomer) {
		mCustommerDao.save(mCustomer);
	}

	@Override
	public void save(List<LinkedHashMap> mCustomers) throws Exception {
		MCustomer insertMCustomer = new MCustomer();
		MCustomer updateMCustomer = new MCustomer();
		MCustomer deleteMCustomer = new MCustomer();
		for (LinkedHashMap<String, Object> hashList : mCustomers) {
			MCustomer tmp = new MCustomer();
			BeanUtils.populate(tmp, hashList);
			if (tmp.isInsert()) {
				insertMCustomer.addCustomer(tmp);
			} else if (tmp.isUpdate()) {
				updateMCustomer.addCustomer(tmp);
			} else if (tmp.isDelete()) {
				deleteMCustomer.addCustomer(tmp);
			}
		}
		if (!insertMCustomer.getCustomerList().isEmpty()) {
			mCustommerDao.insert(insertMCustomer);
		}
		if (!updateMCustomer.getCustomerList().isEmpty()) {
			mCustommerDao.save(updateMCustomer);
		}
		if (!deleteMCustomer.getCustomerList().isEmpty()) {
			mCustommerDao.delete(deleteMCustomer);
		}
	}

	@Override
	public void insert(MCustomer mCustomer) {
		mCustommerDao.save(mCustomer);
	}
}
