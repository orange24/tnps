package th.co.nttdata.tki.dao;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import th.co.nttdata.tki.bean.MUser;
import th.co.nttdata.tki.bean.MWip;

@ContextConfiguration(locations={"classpath:app-config.xml","classpath:dao-config.xml"})
public class MUserDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired private MUserDao userDao;

	@Test
	public void insert_RoleAdmin_WipDC() {
		MUser user = new MUser();
		user.setUserName("aey");
		user.setPassword("aey");
		user.setFullName("Oh Aey");
		user.setRoleId(1);
		user.setWip("");
		
		MWip wip = new MWip();
		wip.setAccessWip(true);
		wip.setWip("DC");
		List<MWip> accessWip = new ArrayList<MWip>();
		accessWip.add(wip);
		user.setAccessWip(accessWip);
		
		userDao.insert(user);
		MUser actual = userDao.queryList(user);
		Assert.assertNotNull(actual.getUserList());
		Assert.assertTrue(actual.getUserList().size() == 1);
	}
	
	@Test
	public void update_UserAdmin() {
		MUser user = new MUser();
		user.setUserName("admin");
		user.setPassword("aey");
		user.setFullName("Oh Aey");
		user.setRoleId(1);

		MWip wip = new MWip();
		wip.setAccessWip(true);
		wip.setWip("DC");
		List<MWip> accessWip = new ArrayList<MWip>();
		accessWip.add(wip);
		user.setAccessWip(accessWip);
		
		userDao.update(user);
	}

	@Test
	public void delete_UserAdmin() {
		MUser user = new MUser();
		user.setUserName("");
		MUser delUser = new MUser();
		delUser.setUserName("admin");
		
		List<MUser> userList = new ArrayList<MUser>();
		userList.add(delUser);
		user.setUserList(userList);
		
		userDao.delete(user);
	}

	@Test
	public void get_UserAdmin() {
		MUser user = new MUser();
		user.setUserName("admin");
		
		userDao.getUser(user);
		Assert.assertNotNull(user);
	}

	@Test
	public void search_All() {
		MUser user = new MUser();
		
		userDao.queryList(user);
		Assert.assertNotNull(user);
		Assert.assertTrue(user.getUserList().size()>0);
		System.out.println(user.getUserList().size());
	}
	
}