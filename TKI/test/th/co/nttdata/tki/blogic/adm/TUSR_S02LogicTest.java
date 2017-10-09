package th.co.nttdata.tki.blogic.adm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import th.co.nttdata.tki.bean.MUser;
import th.co.nttdata.tki.bean.MWip;

@ContextConfiguration(locations={"classpath:app-config.xml","classpath:/dao-config.xml"})
public class TUSR_S02LogicTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	USR_S02Logic USR_S02Logic;
	
	@Test
	public void Add() {
		
		MUser MUser = new MUser();
		MWip MWip = new MWip();
		
		List<MWip> listMWip = new ArrayList<MWip>();
		MWip.setWip("CM");
		listMWip.add(MWip);
		MWip.setWip("DC");
		listMWip.add(MWip);
		
		MUser.setUserName("unittest");
		MUser.setPassword("1234");
		MUser.setFullName("unit test");
		MUser.setStaffCode("ntt");
		MUser.setEmail("ntt@ntt.co.th");
		MUser.setWip("CM");
		MUser.setAccessWip(listMWip);
		MUser.setRoleId(1);
		try {
			USR_S02Logic.save(MUser);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void Edit() {
		
		MUser MUser = new MUser();
		MWip MWip = new MWip();
		
		List<MWip> listMWip = new ArrayList<MWip>();
		MWip.setWip("CM");
		listMWip.add(MWip);
		MWip.setWip("DC");
		listMWip.add(MWip);
		
		MUser.setUserName("test");
		MUser.setPassword("1234");
		MUser.setFullName("unit test");
		MUser.setStaffCode("ntt");
		MUser.setEmail("ntt@ntt.co.th");
		MUser.setWip("CM");
		MUser.setAccessWip(listMWip);
		MUser.setRoleId(1);
		MUser.setCreateDate(new Date());
		try {
			USR_S02Logic.save(MUser);
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
			USR_S02Logic.delete(MUser);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

}
