package th.co.nttdata.tki.blogic.wip;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import th.co.nttdata.tki.bean.TWipStockAdjust;

@ContextConfiguration(locations={"classpath:app-config.xml","classpath:/dao-config.xml"})
public class TWIP_S03LogicTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired private WIP_S03Logic wip_s03Logic;
	
	//search
	
	@Test
	public void searchNoCriteria() {
		TWipStockAdjust twip = new TWipStockAdjust();
		try {
			twip = wip_s03Logic.search(twip);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void searchWIP(){
		TWipStockAdjust twip = new TWipStockAdjust();
		twip.setWip("CM");
		try {
			twip = wip_s03Logic.search(twip);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void searchWIP_Customer(){
		TWipStockAdjust twip = new TWipStockAdjust();
		twip.setWip("CM");
		twip.setCustomerId(1);
		try {
			twip = wip_s03Logic.search(twip);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	//save
	
	@Test
	public void save() {
		TWipStockAdjust twip1 = new TWipStockAdjust();
		TWipStockAdjust twip2 = new TWipStockAdjust();
		List<TWipStockAdjust> adjustList = new ArrayList<TWipStockAdjust>();
		
		twip2.setWip("DC");
		twip2.setPartId(1);
		twip2.setCurrentStock(1);
		twip2.setAdjustStock(1);
		twip2.setAdjustReason("reason");
		adjustList.add(twip2);
		
		twip1.setWip("DC");
		twip1.setPartId(1);
		twip1.setCreateBy("21022010");
		twip1.setUpdateBy("test");
		twip1.setAdjustList(adjustList);
		
		try {
			wip_s03Logic.save(twip1);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
}
