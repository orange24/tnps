package th.co.nttdata.tki.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import th.co.nttdata.tki.bean.TPending;
import th.co.nttdata.tki.bean.TPendingAdjust;
import th.co.nttdata.tki.bean.TPendingRework;

@ContextConfiguration(locations={"classpath:app-config.xml","classpath:/dao-config.xml"})
public class TPendingDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired 
	private TPendingDao pendingDao;
	
	@Test
	public void query_WipDC() {
		TPending pending = new TPending();
		pending.setWip("DC");
//		pending.setCustomerId(1);
		
		try {
			pending = pendingDao.query(pending);
			Assert.assertNotNull(pending.getAdjustList());
			Assert.assertEquals(2, pending.getAdjustList().size());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void query_WipDC_Cus41_PartNoAL() {
		TPending wsa = new TPending();
		wsa.setWip("DC");
		wsa.setCustomerId(41);
		wsa.setPartNo("AL");
		
		try {
			wsa = pendingDao.query(wsa);
			Assert.assertNotNull(wsa.getAdjustList());
			Assert.assertEquals(4, wsa.getAdjustList().size());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void query_WipDC_Cus41_PartNameS9() {
		TPending wsa = new TPending();
		wsa.setWip("DC");
		wsa.setCustomerId(41);
		wsa.setPartName("S9");
		
		try {
			wsa = pendingDao.query(wsa);
			Assert.assertNotNull(wsa.getAdjustList());
			Assert.assertEquals(3, wsa.getAdjustList().size());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void insert1Rows() {
		TPending pd = new TPending();
		TPendingAdjust adj1 = new TPendingAdjust();
		adj1.setPdId(1);
		adj1.setOk(10);
		adj1.setNg(50);		
		List<TPendingAdjust> list = new ArrayList<TPendingAdjust>();
		list.add(adj1);
		pd.setAdjustList(list);
		TPendingRework rew1 = new TPendingRework();
		rew1.setWip("FL");
		rew1.setReworkQty(11);
		List<TPendingRework> reworkList = new ArrayList<TPendingRework>();
		reworkList.add(rew1);
		adj1.setReworkList(reworkList);
		
		try {
			pendingDao.insert(pd);
			Assert.assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	// History

	@Test
	public void queryHis_All() {
		TPending pending = new TPending();
		
		try {
			pending = pendingDao.queryHis(pending);
			Assert.assertNotNull(pending.getAdjustList());
			Assert.assertNotNull(pending.getAdjustList().get(0).getReworkList());
			Assert.assertEquals(new Integer(1), pending.getPageTotal());
			Assert.assertEquals(1, pending.getAdjustList().size());
			Assert.assertEquals(1, pending.getAdjustList().get(0).getReworkList().size());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void queryHis_WipDC_Cus41_NoPart() {
		TPending wsa = new TPending();
		wsa.setWip("DC");
		wsa.setCustomerId(41);
		
		try {
			wsa = pendingDao.queryHis(wsa);
			Assert.assertNotNull(wsa.getAdjustList());
			Assert.assertEquals(new Integer(2), wsa.getPageTotal());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void queryHis_WipDC_Cus41_PartNoAL() {
		TPending wsa = new TPending();
		wsa.setWip("DC");
		wsa.setCustomerId(41);
		wsa.setPartNo("AL");
		
		try {
			wsa = pendingDao.queryHis(wsa);
			Assert.assertNotNull(wsa.getAdjustList());
			Assert.assertEquals(4, wsa.getAdjustList().size());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void queryHis_WipDC_Cus41_PartNameS9() {
		TPending wsa = new TPending();
		wsa.setWip("DC");
		wsa.setCustomerId(41);
		wsa.setPartName("S9");
		
		try {
			wsa = pendingDao.queryHis(wsa);
			Assert.assertNotNull(wsa.getAdjustList());
			Assert.assertEquals(3, wsa.getAdjustList().size());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void queryHis_ReportDateFr_Today() {
		TPending wsa = new TPending();
		wsa.setReportDateFr(new Date());
		
		try {
			wsa = pendingDao.queryHis(wsa);
			Assert.assertNotNull(wsa.getAdjustList());
			Assert.assertEquals(3, wsa.getAdjustList().size());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void queryHis_ReportDateTo_Today() {
		TPending wsa = new TPending();
		wsa.setReportDateTo(new Date());
		
		try {
			wsa = pendingDao.queryHis(wsa);
			Assert.assertNotNull(wsa.getAdjustList());
			Assert.assertEquals(3, wsa.getAdjustList().size());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
}