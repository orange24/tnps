package th.co.nttdata.tki.dao;

import junit.framework.Assert;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import th.co.nttdata.tki.bean.TWIPDeadline;

@ContextConfiguration(locations={"classpath:app-config.xml","classpath:/dao-config.xml"})
public class TWIPDeadlineDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired 
	private TWIPDeadlineDao tWIPdeadlineDao;
	private static final ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void getDeadlineList() {
		try {
			TWIPDeadline TWipDeadline = (TWIPDeadline)tWIPdeadlineDao.getDeadlineList(new TWIPDeadline());
			Assert.assertEquals(1, TWipDeadline.getDeadlinePartList().size());
			/*Assert.assertEquals(5, TWipDeadline.get(0).getDeadlineList().size());
			Assert.assertEquals(2, TWipDeadline.get(0).getDeadlineList().get(0).getDeadlineDateList().size());
			Assert.assertEquals(101, TWipDeadline.get(0).getDeadlineList().get(0).getDeadlineDateList().get(0).getDeadlineQty().intValue());
		*/
		} catch (Exception e) {
			e.printStackTrace();
			//Assert.assertTrue(false);
		}
	}
	
}