package th.co.nttdata.tki.blogic.dal;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import th.co.nttdata.tki.bean.TPending;
import th.co.nttdata.tki.bean.TPendingAdjust;
import th.co.nttdata.tki.bean.TPendingRework;

@ContextConfiguration(locations={"classpath:app-config.xml","classpath:/dao-config.xml"})
public class TPND_S01LogicTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	PND_S01Logic pnd_s01Logic;
	
	@Test
	public void searchWIPCust() {
		TPending TPending = new TPending();
		TPending.setWip("DC");
		TPending.setCustomerId(1);
		
		try {
			pnd_s01Logic.search(TPending);
			Assert.assertNotNull(TPending.getAdjustList());
			Assert.assertEquals(2, TPending.getAdjustList().size());
		} catch (Exception e) {
			Assert.assertTrue(false);
			e.printStackTrace();
		}
	}

	@Test
	public void searchWIPCustPartNo() {
		TPending TPending = new TPending();
		TPending.setWip("DC");
		TPending.setCustomerId(1);
		TPending.setPartNo("AL-L");
		
		try {
			pnd_s01Logic.search(TPending);
			Assert.assertNotNull(TPending.getAdjustList());
			Assert.assertEquals(1, TPending.getAdjustList().size());
		} catch (Exception e) {
			Assert.assertTrue(false);
			e.printStackTrace();
		}
	}
	
	@Test
	public void searchWIPCustPartName() {
		TPending TPending = new TPending();
		TPending.setWip("DC");
		TPending.setCustomerId(1);
		TPending.setPartName("ADJUSTER");
		
		try {
			pnd_s01Logic.search(TPending);
			Assert.assertNotNull(TPending.getAdjustList());
			Assert.assertEquals(1, TPending.getAdjustList().size());
		} catch (Exception e) {
			Assert.assertTrue(false);
			e.printStackTrace();
		}
	}
	
	@Test
	public void searchWIPCustWoNo() {
		TPending TPending = new TPending();
		TPending.setWip("DC");
		TPending.setCustomerId(1);
		TPending.setWorkorderNo("A1");
		
		try {
			pnd_s01Logic.search(TPending);
			Assert.assertNotNull(TPending.getAdjustList());
			Assert.assertEquals(2, TPending.getAdjustList().size());
		} catch (Exception e) {
			Assert.assertTrue(false);
			e.printStackTrace();
		}
	}
	
	@Test
	public void savePendingAdjust(){
		TPendingRework tPendingRework = new TPendingRework();
		tPendingRework.setWip("DC");
		tPendingRework.setReworkQty(1);
		List<TPendingRework> reworkList = new ArrayList<TPendingRework>();
		reworkList.add(tPendingRework);
		
		TPendingAdjust tPendingAdjust = new TPendingAdjust();
		tPendingAdjust.setReworkList(reworkList);
		tPendingAdjust.setPdAdjustQty(1);
		tPendingAdjust.setPdId(1);
		List<TPendingAdjust> adjustList = new ArrayList<TPendingAdjust>();
		adjustList.add(tPendingAdjust);
		
		TPending tPending = new TPending();
		tPending.setAdjustList(adjustList);
		
		try {
			pnd_s01Logic.save(tPending);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
}
