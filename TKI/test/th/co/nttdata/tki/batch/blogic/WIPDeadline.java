package th.co.nttdata.tki.batch.blogic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import th.co.nttdata.tki.batch.bean.BatchDeliveryPlan;
import th.co.nttdata.tki.dao.impl.BatchDaoImpl;

@ContextConfiguration(locations={"classpath:app-config.xml","classpath:/dao-config.xml"})
public class WIPDeadline extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private BatchDaoImpl dao;
	@Autowired
	protected Properties settings;
	
	@SuppressWarnings("unchecked")
	@Test
	public void batch() {
		Date startDate = new Date();
		startDate = DateUtils.round(startDate, Calendar.DATE);
		BigDecimal shiftHour = new BigDecimal(settings.getProperty("CMM.shiftHour", "12"));
		int shiftPerDay = 24/shiftHour.intValue();
		List<Map<String, Object>> productionPlan = new ArrayList<Map<String,Object>>();
		// delivery list group by FG
		List<BatchDeliveryPlan> allFgDlvList = dao.queryForList("t_deadline.getFgDeliveryPlan", startDate);
		Map<Integer, List<BatchDeliveryPlan>> allFgDlvMap = new LinkedHashMap<Integer, List<BatchDeliveryPlan>>();
		for (BatchDeliveryPlan b : allFgDlvList) {
			if (allFgDlvMap.containsKey(b.getFgId())) {
				allFgDlvMap.get(b.getFgId()).add(b);
			} else {
				List<BatchDeliveryPlan> list = new ArrayList<BatchDeliveryPlan>();
				list.add(b);
				allFgDlvMap.put(b.getFgId(), list);
			}
		}
		
		for (Integer fgId : allFgDlvMap.keySet()) {	
			List<BatchDeliveryPlan> fgDlvList = allFgDlvMap.get(fgId);
			// get WIP
			List<Map<String, Object>> wipCapStockList = dao.queryForList("t_deadline.getWipByFg", fgId);
			Integer remainStock = fgDlvList.get(0).getFgBalance()==null?
					0:fgDlvList.get(0).getFgBalance();
			for (int j=0;j<fgDlvList.size();j++) {
				BatchDeliveryPlan fgDlvDetail = fgDlvList.get(j);
				remainStock -= fgDlvDetail.getProductionQty();
				if (remainStock < 0) {
					Date dlvDate = (Date) fgDlvDetail.getDeliveryDate();
					Calendar dlvC = Calendar.getInstance();
					dlvC.setTime(dlvDate);		
					BigDecimal oldCap = null;
					// loop WIP
					for (int w=0;w<wipCapStockList.size();w++) {
						BigDecimal cap = (BigDecimal)wipCapStockList.get(w).get("capacity");
						if (oldCap != null && oldCap.compareTo(cap) < 0)
							cap = oldCap;
						cap = cap.multiply(shiftHour);
						int remainQtyPerCap = -remainStock; 
						while (remainQtyPerCap > 0) {				
							dlvC.add(Calendar.DATE, -1);
							// all shift in 1 day
							for (int s=0;s<shiftPerDay;s++) {
								Map<String, Object> plan = new HashMap<String, Object>();
								plan.put("shift", s);
								plan.put("day", dlvC);
								if (remainQtyPerCap > cap.intValue()) {
									remainQtyPerCap -= cap.intValue();
									plan.put("qty", cap.intValue());
								} else {									
									plan.put("qty", remainQtyPerCap);
									remainQtyPerCap = 0;
								}
								productionPlan.add(plan);
								if (remainQtyPerCap <= 0)
									break;
							}
						}
					}
				}
			}
		}
	}
}
