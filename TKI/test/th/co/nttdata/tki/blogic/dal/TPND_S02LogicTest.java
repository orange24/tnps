package th.co.nttdata.tki.blogic.dal;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import th.co.nttdata.tki.bean.TPending;

@ContextConfiguration(locations={"classpath:app-config.xml","classpath:/dao-config.xml"})
public class TPND_S02LogicTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	PND_S02Logic pnd_s02Logic;
	
	@Test
	public void searchNoCriteria() {
		
		TPending TPending = new TPending();
		
		try {
			pnd_s02Logic.search(TPending);
		} catch (Exception e) {
			Assert.assertTrue(false);
			e.printStackTrace();
		}
	}
	
	@Test
	public void search() {
		TPending TPending = new TPending();
		TPending.setWip("DC");
		TPending.setCustomerId(1);
		TPending.setPartNo("zn");
		TPending.setPartName("juster");
		TPending.setReportDateFr(new Date());
		TPending.setReportDateTo(new Date());
		
		try {
			pnd_s02Logic.search(TPending);
		} catch (Exception e) {
			Assert.assertTrue(false);
			e.printStackTrace();
		}
	}

}
