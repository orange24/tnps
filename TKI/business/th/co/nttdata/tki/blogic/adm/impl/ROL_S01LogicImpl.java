package th.co.nttdata.tki.blogic.adm.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MRole;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.adm.ROL_S01Logic;
import th.co.nttdata.tki.dao.MRoleDao;

@Service
public class ROL_S01LogicImpl extends AbstractBaseLogic implements ROL_S01Logic {

	@Autowired
	MRoleDao mRoleDAO;
	
	@Override
	public MRole search(MRole MRole) {
		return mRoleDAO.queryList(MRole); 
	}

	@Override
	public void delete(MRole MRole) {
		mRoleDAO.delete(MRole); 
	}

}
