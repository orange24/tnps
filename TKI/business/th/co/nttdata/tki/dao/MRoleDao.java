package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.MRole;
import th.co.nttdata.tki.bean.MultiChoice;

public interface MRoleDao {

	public MRole getRole(MRole role);

	public List<MRole> getComboBox();
	
	public MRole queryList(MRole role);

	public boolean existRoleName(MRole role);

	public List<MultiChoice> getRoleMenu(MRole role);

	public List<MultiChoice> getRoleAction(MRole role);

	public MRole update(MRole role);

	public void insert(MRole role);

	public void delete(MRole role);
}