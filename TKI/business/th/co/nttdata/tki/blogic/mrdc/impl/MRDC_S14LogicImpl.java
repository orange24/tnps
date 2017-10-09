package th.co.nttdata.tki.blogic.mrdc.impl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S14Logic;
import th.co.nttdata.tki.dao.TDeliveryPlanDao;

@Service
public class MRDC_S14LogicImpl implements MRDC_S14Logic{
	
	@Autowired
	TDeliveryPlanDao tDeliveryPlanDao;
	
	@Autowired
	protected Properties settings;

	@Override
	public TDeliveryPlan exportMRDC_R14(TDeliveryPlan TDeliveryPlan) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		TDeliveryPlan.setMaxRecord(maxRecord);
		TDeliveryPlan.setCustomerId(TDeliveryPlan.getCustomerId() < 0 ? null : TDeliveryPlan.getCustomerId());
		
		return tDeliveryPlanDao.selectMRDC_R14(TDeliveryPlan);
	}
	
	@Override
	public Integer countMRDC_R14() {
		
		return tDeliveryPlanDao.countMRDC_R14();
	}

}
