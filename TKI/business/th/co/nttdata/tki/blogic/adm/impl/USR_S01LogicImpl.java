package th.co.nttdata.tki.blogic.adm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MRole;
import th.co.nttdata.tki.bean.MUser;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.adm.USR_S01Logic;
import th.co.nttdata.tki.dao.MRoleDao;
import th.co.nttdata.tki.dao.MUserAccessWipDao;
import th.co.nttdata.tki.dao.MUserDao;

@Service
public class USR_S01LogicImpl extends AbstractBaseLogic implements USR_S01Logic {

	@Autowired
	MUserDao mUserDAO;
	@Autowired
	MRoleDao mRoleDAO;
	@Autowired
	MUserAccessWipDao mUserAccessWipDAO;
	
	@Override
	public MUser search(MUser MUser) {
		return mUserDAO.queryList(MUser);
	}

	@Override
	public List<MRole> getComboBox() {
		return mRoleDAO.getComboBox();
	}

	@Override
	public void delete(MUser MUser) {
		mUserDAO.delete(MUser);
	}

	@Override
	public MUser getCurrentUser() {
		return mUserDAO.getUser();
	}

}
