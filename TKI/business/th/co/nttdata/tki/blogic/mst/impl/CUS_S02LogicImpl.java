package th.co.nttdata.tki.blogic.mst.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.mst.CUS_S02Logic;
import th.co.nttdata.tki.dao.MCustomerDao;

@Service
public class CUS_S02LogicImpl extends AbstractBaseLogic implements CUS_S02Logic {

	@Autowired
	private MCustomerDao mCustommerDao;

	@Override
	public MCustomer search(MCustomer mCustomer) {
		mCustomer = mCustommerDao.getCustomerTpicList(mCustomer);
		return mCustomer;
	}

	@Override
	public void save(MCustomer mCustomer) {
		mCustommerDao.insert(mCustomer);
	}
}
