package th.co.nttdata.tki.blogic.wip;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import th.co.nttdata.tki.bean.TReworkAdjust;

@ContextConfiguration(locations={"classpath:app-config.xml","classpath:/dao-config.xml"})
public class TREW_S02LogicTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	REW_S02Logic rewS02Logic;
	
	@Test
	public void searchNoCriteria() {
		
		TReworkAdjust TReworkAdjust = new TReworkAdjust();
		
		try {
			rewS02Logic.search(TReworkAdjust);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void searchCriteria() throws ParseException {
		
		DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date from = dfm.parse("2010-12-01 00:00:00");
		Date to = dfm.parse("2011-03-16 00:00:00");

		
		TReworkAdjust TReworkAdjust = new TReworkAdjust();
//		TReworkAdjust.setWipFr("CM");
//		TReworkAdjust.setWipTo("CM");
		TReworkAdjust.setReportDateFr(from);
		TReworkAdjust.setReportDateTo(to);
		TReworkAdjust.setCustomerId(1);
		
		try {
			rewS02Logic.search(TReworkAdjust);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

}
