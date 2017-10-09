package th.co.nttdata.tki.blogic.adm;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import th.co.nttdata.tki.bean.MRole;

@ContextConfiguration(locations={"classpath:app-config.xml","classpath:/dao-config.xml"})
public class TROL_S01LogicTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	ROL_S01Logic ROL_S01Logic;
	
	@Test
	public void searchNoCriteria(){
		
		MRole MRole = new MRole();
		try {
			ROL_S01Logic.search(MRole);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void search(){
		MRole MRole = new MRole();
		MRole.setRoleName("test");
		try {
			ROL_S01Logic.search(MRole);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void delete(){
		MRole MRole = new MRole();
		
		List<MRole> listRole = new ArrayList<MRole>();
		MRole.setRoleId(19);
		listRole.add(MRole);
		MRole.setRoleList(listRole);
		
		try {
			ROL_S01Logic.delete(MRole);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
