package th.co.nttdata.tki.blogic.adm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import th.co.nttdata.tki.bean.MRole;
import th.co.nttdata.tki.bean.MultiChoice;

@ContextConfiguration(locations={"classpath:app-config.xml","classpath:/dao-config.xml"})
public class TROL_S02LogicTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	ROL_S02Logic ROL_S02Logic;
	
	@Test
	public void add(){
		
		MRole MRole = new MRole();
		MultiChoice menu = new MultiChoice();
		List<MultiChoice> menuList = new ArrayList<MultiChoice>();
		menu.setId(0);
		menu.setId(3);
		menuList.add(menu);
		
		List<MultiChoice> actionList = new ArrayList<MultiChoice>();
		menu = new MultiChoice();
		menu.setId(0);
		menu.setId(3);
		actionList.add(menu);
		
		MRole.setRoleName("unittest");
		MRole.setRoleDesc("");
		MRole.setMenuList(menuList);
		MRole.setActionList(actionList);
		
		try {
			ROL_S02Logic.save(MRole);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void edit(){
		
		MRole MRole = new MRole();
		MultiChoice menu = new MultiChoice();
		List<MultiChoice> menuList = new ArrayList<MultiChoice>();
		menu.setId(0);
		menu.setId(3);
		menuList.add(menu);
		
		List<MultiChoice> actionList = new ArrayList<MultiChoice>();
		menu = new MultiChoice();
		menu.setId(0);
		menu.setId(3);
		actionList.add(menu);
		
		MRole.setRoleName("test");
		MRole.setRoleDesc("");
		MRole.setMenuList(menuList);
		MRole.setActionList(actionList);
		MRole.setCreateDate(new Date());
		
		try {
			ROL_S02Logic.save(MRole);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void search(){
		MRole MRole = new MRole();
		MRole.setRoleId(19);
		try {
			ROL_S02Logic.search(MRole);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
}
