package th.co.nttdata.tki.blogic.mst.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.mst.PRM_S01Logic;
import th.co.nttdata.tki.dao.MCustomerDao;

@Service
public class PRM_S01LogicImpl extends AbstractBaseLogic implements PRM_S01Logic{
	@Autowired
	private MCustomerDao mCustommerDao;
	
	@Override
	public MCustomer getAllCustomer() {
		MCustomer  mCustomer = new MCustomer();
		mCustomer.setCustomerList(mCustommerDao.getCustomerList());
		return mCustomer;
	}
	

}
