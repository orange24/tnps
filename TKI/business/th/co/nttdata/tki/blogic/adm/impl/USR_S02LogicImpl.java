package th.co.nttdata.tki.blogic.adm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MRole;
import th.co.nttdata.tki.bean.MUser;
import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.adm.USR_S02Logic;
import th.co.nttdata.tki.dao.MRoleDao;
import th.co.nttdata.tki.dao.MUserAccessWipDao;
import th.co.nttdata.tki.dao.MUserCustomerDao;
import th.co.nttdata.tki.dao.MUserDao;

@Service
public class USR_S02LogicImpl extends AbstractBaseLogic implements USR_S02Logic {

	@Autowired
	MRoleDao mRoleDAO;
	@Autowired
	MUserAccessWipDao mUserAccessWipDAO;
	@Autowired
	MUserDao mUserDAO;
	@Autowired
	private MUserCustomerDao userCustomerDao;
	
	private final String EDIT = "edit";
	private final String SAVE = "save";
	
	@Override
	public List<MRole> getComboBox() {
		return mRoleDAO.getComboBox();
	}

	@Override
	public List<MWip> getAcessWip(MUser MUser) {
		return mUserAccessWipDAO.query(MUser);
	}

	@Override
	public String save(MUser MUser) {
		
		String result = "";
		
		if (MUser.getPassword() != null && !"".equals(MUser.getPassword())) {
			PasswordEncoder encoder = new Md5PasswordEncoder();
		    String hashedPass = encoder.encodePassword(MUser.getPassword(), null);
		    MUser.setPassword(hashedPass);
		}
		
		if (MUser.getCreateDate() == null) {
			mUserDAO.insert(MUser);
			result = SAVE;
		}else{
			mUserDAO.update(MUser);
			result = EDIT;
		}
		return result;
	}

	@Override
	public MUser getUser(MUser MUser) {
		return mUserDAO.getUser(MUser);
	}

	@Override
	public void delete(MUser MUser) {
		mUserDAO.delete(MUser);		
	}

	@Override
	public MUser getCurrentUser() {
		return mUserDAO.getUser();
	}
	
	@Override
	public List<MCustomer> getCustomer(MUser mUser) {
		return userCustomerDao.getCustomerList(mUser);
	}

}
