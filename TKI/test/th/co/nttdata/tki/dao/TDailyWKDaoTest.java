package th.co.nttdata.tki.dao;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import th.co.nttdata.tki.bean.TDailyWK;

@ContextConfiguration(locations={"classpath:app-config.xml","classpath:/dao-config.xml"})
public class TDailyWKDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired 
	private TDailyWKDao dailyWKDao;
	private static final ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void query_WipDC() {
		try {
			TDailyWK dailyWK = dailyWKDao.selectDAL_R03(new TDailyWK());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
}