package th.co.nttdata.tki.blogic.mst;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.MPartWip;
import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.dao.DaoSpringTest;

@ContextConfiguration(locations={"classpath:app-config.xml","classpath:/dao-config.xml"})
public class ITM_S02LogicTest extends DaoSpringTest {

	@Autowired private ITM_S02Logic itm_S02Logic;

	@Test
	public void queryTPics() {
		MPart part = new MPart();
		MPart partTPics = new MPart();
		part.setCustomerCode("ALPINE (HK)");
		List<MPart> list = new ArrayList<MPart>();
		list.add(partTPics);
//		part.setPartList(list);
		
		try {
			partTPics = itm_S02Logic.query(part);
			Assert.assertNotNull(partTPics.getPartList());
//			Assert.assertEquals(7, tfg.getDetails().size());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void syncTPics_Update_PartAL_CustABLE() {
		MPart part = new MPart();
		MPart partTPics = new MPart();
		partTPics.setChoose(true);
		partTPics.setCustomerCode("ALPINE (HK)");
		partTPics.setCustomerName("ALPINE ELECTRONICS HONG KONG LTD.");
		partTPics.setFgNo("AL-EXAPEALPINEH-002");
		partTPics.setFgName("HEAT SINK 07-02702Z01-B.");
		partTPics.setPartNo("AL-EXAPEALPINEH-002");
		partTPics.setPartName("HEAT SINK 07-02702Z01-B-ALUMINIUM DIECAST.");
		List<MPartWip> wlist = new ArrayList<MPartWip>();
		MPartWip pw = new MPartWip();
		MWip w = new MWip();
		w.setWip("ALDC");
		w.setWipName("DIE CAST");
		w.setWipType(1);
		w.setWipOrder(1);
		pw.setWip(w);
		wlist.add(pw);
		pw = new MPartWip();
		w = new MWip();
		w.setWip("FN-3");
		w.setWipName("FINISHING , 3");		
		w.setWipType(2);
		w.setWipOrder(2);
		pw.setWip(w);
		wlist.add(pw);
		partTPics.setWipList(wlist);
		List<MPart> list = new ArrayList<MPart>();
		list.add(partTPics);
		part.setPartList(list);
		
		try {
			itm_S02Logic.save(part);
//			Assert.assertNotNull(result.get);
//			Assert.assertEquals(7, tfg.getDetails().size());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

}