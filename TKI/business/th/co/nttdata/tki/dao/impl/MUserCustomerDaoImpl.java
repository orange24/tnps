package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MUser;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MUserCustomerDao;

@Repository
@SuppressWarnings("unchecked")
public class MUserCustomerDaoImpl extends AbstractBaseDao implements MUserCustomerDao {
	
	@Override
	public List<MCustomer> getCustomerList(MUser mUser) {
		return (List<MCustomer>) queryForList("m_user_customer.queryCustomer",mUser);
	}

	@Override
	public void insert(MUser mUser) {
		insert("m_user_customer.insert",mUser);
	}

	@Override
	public void delete(MUser mUser) {
		delete("m_user_customer.delete",mUser);
	}
	
}
