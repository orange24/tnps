package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MRole;
import th.co.nttdata.tki.bean.MultiChoice;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MRoleDao;

@Repository
@SuppressWarnings("unchecked")
public class MRoleDaoImpl extends AbstractBaseDao implements MRoleDao {
	
	@Override
	public MRole getRole( MRole role ) {
		return (MRole) queryForObject("m_role.queryRole", role);
	}
	
	@Override
	public List<MRole> getComboBox() {
		return (List<MRole>) queryForList("m_role.queryComboBox");
	}

	@Override
	public MRole queryList( MRole role ) {
		role.setRoleList( (List<MRole>) queryForList("m_role.query", role, getSkipResult(role), role.getPageCount()) );
		calPageTotal("m_role.count", role);

		return role;
	}

	@Override
	public void delete( MRole role ) {
		// <!-- Delete to 'm_role_action' -->
		deleteAction(role);
		
		// <!-- Delete to 'm_role_menu' -->
		deleteMenu(role);
		
		// <!-- Delete to 'm_role' -->
		delete("m_role.delete", role);
	}

	@Override
	public void insert( MRole role ) {
		// <!-- Insert to 'm_user' -->
		role.setRoleId((Integer)insert("m_role.insert", role));

		// <!-- Insert to 'm_role_action' -->
		insertAction(role);

		// <!-- Insert to 'm_role_menu' -->
		insertMenu(role);
	}

	@Override
	public MRole update( MRole role ) {
		// <!-- Delete/Insert to 'm_role_action' -->
		deleteAction(role);
		insertAction(role);

		// <!-- Delete/Insert to 'm_role_menu' -->
		deleteMenu(role);
		insertMenu(role);
		
		// <!-- Update to 'm_user' -->
		update("m_role.update", role);
		
		return role;
	}
	
	private void deleteAction( MRole role ) {
		delete("m_role_action.delete", role);
	}
	
	private void deleteMenu( MRole role ) {
		delete("m_role_menu.delete", role);
	}
	
	private void insertAction( MRole role ) {
		insert("m_role_action.insert", role);
	}
	
	private void insertMenu( MRole role ) {
		insert("m_role_menu.insert", role);
	}

	@Override
	public List<MultiChoice> getRoleAction( MRole role ) {
		// <!-- Select from 'm_role_action' -->
		return queryForList("m_role_action.queryMultiChoice", role);
	}

	@Override
	public List<MultiChoice> getRoleMenu( MRole role ) {
		// <!-- Select from 'm_role_menu' -->
		return queryForList("m_role_menu.queryMultiChoice", role);
	}

	@Override
	public boolean existRoleName( MRole role ) {
		return (Integer)queryForObject("m_role.countRoleName", role.getRoleName()) > 0?true:false;
	}
}