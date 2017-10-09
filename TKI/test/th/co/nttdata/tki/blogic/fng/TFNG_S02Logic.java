package th.co.nttdata.tki.blogic.fng;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import th.co.nttdata.tki.bean.TFG;

@ContextConfiguration(locations={"classpath:app-config.xml","classpath:/dao-config.xml"})
public class TFNG_S02Logic extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired private FNG_S02Logic fng_S02Logic;
	
	@Test
	public void searchNoCriteria() {
		TFG tfg = new TFG();
		
		try {
			tfg = fng_S02Logic.search(tfg);
			Assert.assertNotNull(tfg.getDetails());
			Assert.assertEquals(7, tfg.getDetails().size());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void searchCustId_1() {
		TFG tfg = new TFG();
		tfg.setCustomerId(1);
		try {
			tfg = fng_S02Logic.search(tfg);
			Assert.assertNotNull(tfg.getDetails());
			Assert.assertEquals(7, tfg.getDetails().size());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void searchPageCount_2() {
		TFG tfg = new TFG();
		tfg.setPageCount(2);
		try {
			tfg = fng_S02Logic.search(tfg);
			Assert.assertNotNull(tfg.getDetails());
			Assert.assertEquals(2, tfg.getDetails().size());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void searchPageNumber_4() {
		TFG tfg = new TFG();
		tfg.setPageCount(2);
		tfg.setPageNumber(4);
		try {
			tfg = fng_S02Logic.search(tfg);
			Assert.assertNotNull(tfg.getDetails());
			Assert.assertEquals(1, tfg.getDetails().size());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

}