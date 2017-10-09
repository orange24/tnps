package th.co.nttdata.tki.dao;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import th.co.nttdata.tki.bean.MRole;
import th.co.nttdata.tki.bean.MultiChoice;

@ContextConfiguration(locations={"classpath:app-config.xml","classpath:dao-config.xml"})
public class MRoleDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired private MRoleDao roleDao;

	@Test
	public void get_RoleAdmin() {
		MRole role = new MRole();
		role.setRoleId(1);
		
		roleDao.getRole(role);
		Assert.assertNotNull(role);

		List<MultiChoice> actualActions = roleDao.getRoleAction(role);
		Assert.assertTrue(actualActions.size() == 4);
		Assert.assertTrue(actualActions.get(0).getId() == 3);
		
		List<MultiChoice> actualMenus = roleDao.getRoleMenu(role);
		Assert.assertTrue(actualMenus.size() == 18);
		Assert.assertTrue(actualMenus.get(0).getId() == 14);
	}

	@Test
	public void insert_RoleAey() {
		MRole role = new MRole();
		role.setRoleName("Aey");
		List<MultiChoice> actionList = new ArrayList<MultiChoice>();
		List<MultiChoice> menuList = new ArrayList<MultiChoice>();
		role.setActionList(actionList);
		role.setMenuList(menuList);
		
		MultiChoice act1 = new MultiChoice();
		act1.setId(1);
		actionList.add(act1);
		
		MultiChoice mnu1 = new MultiChoice();
		mnu1.setId(15);
		menuList.add(mnu1);
				
		roleDao.insert(role);
		
		// Assert
		MRole actual = roleDao.getRole(role);
		Assert.assertEquals("Aey", actual.getRoleName());
		
		List<MultiChoice> actualActions = roleDao.getRoleAction(role);
		Assert.assertTrue(actualActions.size() == 4);
		Assert.assertTrue(actualActions.get(2).getChoose());
		
		List<MultiChoice> actualMenus = roleDao.getRoleMenu(role);
		Assert.assertTrue(actualMenus.size() == 18);
		Assert.assertTrue(actualMenus.get(1).getChoose());
	}
	
	@Test
	public void update_UserAdmin() {
		MRole role = new MRole();
		role.setRoleId(1);
		role.setRoleName("Update A&B's");
		List<MultiChoice> actionList = new ArrayList<MultiChoice>();
		List<MultiChoice> menuList = new ArrayList<MultiChoice>();
		role.setActionList(actionList);
		role.setMenuList(menuList);
		
		MultiChoice act1 = new MultiChoice();
		act1.setId(1);
		actionList.add(act1);
		
		MultiChoice mnu1 = new MultiChoice();
		mnu1.setId(15);
		menuList.add(mnu1);
						
		roleDao.update(role);
		
		// Assert
		MRole actual = roleDao.getRole(role);
		Assert.assertEquals("Update A&B's", actual.getRoleName());
		
		List<MultiChoice> actualActions = roleDao.getRoleAction(role);
		Assert.assertTrue(actualActions.size() == 4);
		Assert.assertTrue(actualActions.get(2).getChoose());
		
		List<MultiChoice> actualMenus = roleDao.getRoleMenu(role);
		Assert.assertTrue(actualMenus.size() == 18);
		Assert.assertTrue(actualMenus.get(1).getChoose());
	}

	@Test
	public void delete_RoleId2() {
		MRole role = new MRole();
		role.setRoleId(2);
		
		roleDao.delete(role);
	}
	
}