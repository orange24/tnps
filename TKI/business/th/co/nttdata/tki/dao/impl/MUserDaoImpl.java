package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MUser;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MUserAccessWipDao;
import th.co.nttdata.tki.dao.MUserCustomerDao;
import th.co.nttdata.tki.dao.MUserDao;

@Repository
@SuppressWarnings("unchecked")
public class MUserDaoImpl extends AbstractBaseDao implements MUserDao {
	
	@Autowired
	private MUserAccessWipDao userAccessWipDao;
	@Autowired
	private MUserCustomerDao mUserCustomerDao;
	
	@Override
	public void delete( MUser user ) {
		// <!-- Delete to 'm_user_access_wip' -->
		userAccessWipDao.delete(user);

		// <!-- Delete to 'm_user' -->
		delete("m_user.delete", user);
		
		// <!-- Delete to 'm_user_customer' -->
		mUserCustomerDao.delete(user);
	}

	@Override
	public void insert( MUser user ) {
		// <!-- Insert to 'm_user' -->
		insert("m_user.insert", user);

		// <!-- Insert to 'm_user_access_wip' -->
		userAccessWipDao.insert(user);
		
		// <!-- Insert to 'm_user_customer' -->
		mUserCustomerDao.insert(user);
	}

	@Override
	public MUser update( MUser user ) {
		// <!-- Delete to 'm_user_access_wip' -->
		userAccessWipDao.delete(user);
		userAccessWipDao.insert(user);
		
		// <!-- Delete to 'm_user_customer' -->
		mUserCustomerDao.delete(user);
		mUserCustomerDao.insert(user);

		// <!-- Update to 'm_user' -->
		update("m_user.update", user);
		
		return user;
	}

	@Override
	public int updatePassword( MUser user ) {
		// <!-- New Object: Prevent the data has been changed. -->
		MUser MUser = new MUser();
		MUser.setUserName( user.getUserName() );
		MUser.setPassword( user.getPassword() );
		MUser.setOldPswrd( user.getOldPswrd() );

		return update("m_user.update", MUser);
	} 

	@Override
	public MUser getUser() {
		MUser MUser = new MUser();
		MUser.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());

		// <!-- Select Current User. -->
		return getUser(MUser);
	}

	@Override
	public MUser getUser( MUser user ) {
		// <!-- Select from 'm_user' -->
		user = (MUser) queryForObject("m_user.getUser", user);
		
		return user;
	}

	@Override
	public MUser queryList( MUser user ) {
		user.setUserList( (List<MUser>) queryForList("m_user.query", user, getSkipResult(user), user.getPageCount()) );
		calPageTotal("m_user.count", user);

		return user;
	}
}