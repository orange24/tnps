package th.co.nttdata.tki.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import th.co.nttdata.tki.bean.MPart;

@ContextConfiguration(locations={"classpath:app-config.xml","classpath:/dao-config.xml"})
public class MPartDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private MPartDao mPartDao;
	
	@Test
	public void query_All() {
		MPart part = new MPart();
		part.setCustomerCode("ABLE");
		part.setPartNo("AL");
		
		try {
			MPart actual = mPartDao.getPartNotSync(part);
			System.out.println(actual.getPartList());
			System.out.println(actual.getPageTotal());
			Assert.assertNotNull(actual.getPartList());			
			Assert.assertEquals(5, actual.getPartList().size());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void query_Cust_AAP() {
		List<MPart> result = mPartDao.getPartList();
		System.out.println(result);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void query_Master_Cust_AAP() {
		MPart result = mPartDao.getPartMasterList(new MPart());
		System.out.println(result);
		Assert.assertNotNull(result);
	}
	
	/*
	@Test
	public void query_WipSB_Cust1() {
		TPending pending = new TPending();
		pending.setWip("SB");
		pending.setCustomerId(1);
		
		try {
			pending = pendingDao.query(pending);
			Assert.assertNotNull(pending.getAdjustList());
			Assert.assertEquals(0, pending.getAdjustList().size());
			Assert.assertEquals(1, pending.getInfos().size());
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

	@Test
	public void query_WipCM_WipList() {
		TPending pending = new TPending();
		pending.setWip("MC");
		
		try {
			pending = pendingDao.query(pending);
			Assert.assertNotNull(pending.getWipMap());
			System.out.println(pending.getWipMap().get(1).getWipList());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	// History

	@Test
	public void queryHis_All() {
		TPending pending = new TPending();
		pending.setWip("MC");
		pending.setCustomerId(1);
		
		try {
			pending = pendingDao.queryHis(pending);
			Assert.assertNotNull(pending.getAdjustList());
			Assert.assertNotNull(pending.getAdjustList().get(0).getReworkList());
//			Assert.assertEquals(new Integer(3), pending.getPageTotal());			
			Assert.assertEquals(1, pending.getAdjustList().size());
			Assert.assertTrue(pending.getAdjustList().get(0).getReworkList().size() > 0);
			System.out.println(pending.getAdjustList().get(0).getReworkList());
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
	*/
}