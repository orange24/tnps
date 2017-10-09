package th.co.nttdata.tki.blogic.adm;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import th.co.nttdata.tki.bean.MUser;

@ContextConfiguration(locations={"classpath:app-config.xml","classpath:/dao-config.xml"})
public class TUSR_S01LogicTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	USR_S01Logic USR_S01Logic;
	
	@Test
	public void searchNoCriteria() {
		MUser MUser = new MUser();
		
		try {
			USR_S01Logic.search(MUser);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void delete() {
		
		MUser MUser = new MUser();
		List<MUser> listUser = new ArrayList<MUser>();
		MUser.setUserName("test");
		listUser.add(MUser);
		MUser.setUserList(listUser);
		
		try {
			USR_S01Logic.delete(MUser);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
}
