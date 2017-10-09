package th.co.nttdata.tki.blogic.fng;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import th.co.nttdata.tki.bean.TFG;
import th.co.nttdata.tki.bean.TFGDetail;
import th.co.nttdata.tki.dao.TFGDetailDao;

@ContextConfiguration(locations={"classpath:app-config.xml","classpath:/dao-config.xml"})
public class TFNGDetailDaoImplTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private TFGDetailDao tfgDetailDao;
	
	@Test
	@Rollback(false)
	@Transactional
	public void insertFgDetail() throws ParseException {
		TFG tfg = new TFG();
		List<TFGDetail> details = new ArrayList<TFGDetail>();
		tfg.setDetails(details);
		Date reportDate = DateUtils.parseDate("2014-02-26", new String[] {"yyyy-MM-dd"});
		
		for (int i = 0; i < 1000; i++) {
			TFGDetail detail = new TFGDetail();
			detail.setFgType("fgIn");
			detail.setWorkOrderNo("W");
			detail.setLotNo("TestLot"+i);
			detail.setLotSeqNo(ObjectUtils.toString(i));
			detail.setFgId(1);
			detail.setReportDate(reportDate);
			detail.setFgIn(1);
			detail.setFgOut(-1);
			details.add(detail);
		}
		
		try {
			tfgDetailDao.insertFgDetail(tfg);
			Assert.assertNotNull(tfg.getDetails());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
}