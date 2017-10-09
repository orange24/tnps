package th.co.nttdata.tki.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import th.co.nttdata.tki.bean.TWipStockAdjust;
import th.co.nttdata.tki.dao.TWipStockAdjustDao;

@ContextConfiguration(locations={"classpath:app-config.xml","classpath:/dao-config.xml"})
public class TWipStockAdjustDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired 
	private TWipStockAdjustDao wipStockAdjustDao;
	
	@Test
	public void query_WipDC_Cus41_NoPart() {
		TWipStockAdjust wsa = new TWipStockAdjust();
		wsa.setWip("DC");
		wsa.setCustomerId(41);
		
		try {
			wsa = wipStockAdjustDao.query(wsa);
			Assert.assertNotNull(wsa.getAdjustList());
			Assert.assertEquals(new Integer(2), wsa.getPageTotal());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void query_WipDC_Cus41_PartNoAL() {
		TWipStockAdjust wsa = new TWipStockAdjust();
		wsa.setWip("DC");
		wsa.setCustomerId(41);
		wsa.setPartNo("AL");
		
		try {
			wsa = wipStockAdjustDao.query(wsa);
			Assert.assertNotNull(wsa.getAdjustList());
			Assert.assertEquals(4, wsa.getAdjustList().size());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void query_WipDC_Cus41_PartNameS9() {
		TWipStockAdjust wsa = new TWipStockAdjust();
		wsa.setWip("DC");
		wsa.setCustomerId(41);
		wsa.setPartName("S9");
		
		try {
			wsa = wipStockAdjustDao.query(wsa);
			Assert.assertNotNull(wsa.getAdjustList());
			Assert.assertEquals(3, wsa.getAdjustList().size());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void insert1Rows() {
		TWipStockAdjust wsa = new TWipStockAdjust();
		TWipStockAdjust adj1 = new TWipStockAdjust();
		adj1.setPartId(397);
		adj1.setWip("DC");
		adj1.setCurrentStock(50);
		adj1.setAdjustStock(60);
		adj1.setAdjustReason("found 10 pieces");
		List<TWipStockAdjust> list = new ArrayList<TWipStockAdjust>();
		list.add(adj1);
		wsa.setAdjustList(list);
		wsa.setCreateBy("aey");
		wsa.setUpdateBy("aey");
		
		try {
			wipStockAdjustDao.insert(wsa);
			Assert.assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	// WIP_S04 History

	@Test
	public void queryHis_All() {
		TWipStockAdjust wsa = new TWipStockAdjust();
		
		try {
			wsa = wipStockAdjustDao.queryHis(wsa);
			Assert.assertNotNull(wsa.getAdjustList());
			Assert.assertEquals(new Integer(1), wsa.getPageTotal());
			Assert.assertEquals(0, wsa.getAdjustList().size());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void queryHis_WipDC_Cus41_NoPart() {
		TWipStockAdjust wsa = new TWipStockAdjust();
		wsa.setWip("DC");
		wsa.setCustomerId(41);
		
		try {
			wsa = wipStockAdjustDao.queryHis(wsa);
			Assert.assertNotNull(wsa.getAdjustList());
			Assert.assertEquals(new Integer(2), wsa.getPageTotal());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void queryHis_WipDC_Cus41_PartNoAL() {
		TWipStockAdjust wsa = new TWipStockAdjust();
		wsa.setWip("DC");
		wsa.setCustomerId(41);
		wsa.setPartNo("AL");
		
		try {
			wsa = wipStockAdjustDao.queryHis(wsa);
			Assert.assertNotNull(wsa.getAdjustList());
			Assert.assertEquals(4, wsa.getAdjustList().size());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void queryHis_WipDC_Cus41_PartNameS9() {
		TWipStockAdjust wsa = new TWipStockAdjust();
		wsa.setWip("DC");
		wsa.setCustomerId(41);
		wsa.setPartName("S9");
		
		try {
			wsa = wipStockAdjustDao.queryHis(wsa);
			Assert.assertNotNull(wsa.getAdjustList());
			Assert.assertEquals(3, wsa.getAdjustList().size());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void queryHis_ReportDateFr_Today() {
		TWipStockAdjust wsa = new TWipStockAdjust();
		wsa.setReportDateFr(new Date());
		
		try {
			wsa = wipStockAdjustDao.queryHis(wsa);
			Assert.assertNotNull(wsa.getAdjustList());
			Assert.assertEquals(3, wsa.getAdjustList().size());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void queryHis_ReportDateTo_Today() {
		TWipStockAdjust wsa = new TWipStockAdjust();
		wsa.setReportDateTo(new Date());
		
		try {
			wsa = wipStockAdjustDao.queryHis(wsa);
			Assert.assertNotNull(wsa.getAdjustList());
			Assert.assertEquals(3, wsa.getAdjustList().size());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
}