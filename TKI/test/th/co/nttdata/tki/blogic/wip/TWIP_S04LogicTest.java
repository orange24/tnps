package th.co.nttdata.tki.blogic.wip;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import th.co.nttdata.tki.bean.TWipStockAdjust;

@ContextConfiguration(locations={"classpath:app-config.xml","classpath:/dao-config.xml"})
public class TWIP_S04LogicTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired private WIP_S04Logic wip_s04Logic;
	
	//search
	
	@Test
	public void searchNoCriteria(){
		
		TWipStockAdjust twip = new TWipStockAdjust();
		
		try {
			wip_s04Logic.search(twip);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void search(){
		
		TWipStockAdjust twip = new TWipStockAdjust();
		twip.setWip("DC");
		twip.setCustomerId(1);
		twip.setPartName("COLLAR RR GRAB");
		twip.setPartNo("AL-L AP");
		twip.setReportDateTo(new Date());
		
		try {
			wip_s04Logic.search(twip);
		} catch (Exception e) {
			Assert.assertTrue(false);
			e.printStackTrace();
		}
	}
	
}
