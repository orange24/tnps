package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MUser;

public interface MUserCustomerDao {

	public List<MCustomer> getCustomerList(MUser mUser);
	public void insert(MUser user);
	public void delete(MUser user);

}
