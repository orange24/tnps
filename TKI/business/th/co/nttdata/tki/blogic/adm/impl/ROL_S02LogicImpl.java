package th.co.nttdata.tki.blogic.adm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MRole;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.bean.MultiChoice;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.adm.ROL_S02Logic;
import th.co.nttdata.tki.dao.MRoleDao;

@Service
public class ROL_S02LogicImpl extends AbstractBaseLogic implements ROL_S02Logic  {

	private final String SAVE = "save";
	private final String EDIT = "edit";
	private final String ERROR = "error";
	
	@Autowired
	MRoleDao mRoleDao;
	
	@Override
	public List<MultiChoice> getRolMenu(MRole MRole) {
		return mRoleDao.getRoleMenu(MRole);
	}

	@Override
	public List<MultiChoice> getRolCommand(MRole MRole) {
		return mRoleDao.getRoleAction(MRole);
	}

	@Override
	public boolean existRoleName(MRole MRole) {
		return mRoleDao.existRoleName(MRole);
	}

	@Override
	public String save(MRole MRole) {
		
		String result = "";
		boolean isDup = false;
		
		if (MRole.getCreateDate() == null) {
			
			isDup = mRoleDao.existRoleName(MRole);
			
			if (!isDup) {
				mRoleDao.insert(MRole);
				result = SAVE;
			}else{
				MRole.getErrors().add( new Message("err.cmm.001", new String[] {"Give me code error: Duplicate Role Name "}) );
				result = ERROR;
			}
			
		}else{
			mRoleDao.update(MRole);
			result = EDIT;
		}
		
		return result;
	}

	@Override
	public MRole search(MRole MRole) {
		return mRoleDao.getRole(MRole);
	}

}
