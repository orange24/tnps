package th.co.nttdata.tki.blogic.mrdc.impl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S15Logic;
import th.co.nttdata.tki.dao.TDeliveryPlanDao;

@Service
public class MRDC_S15LogicImpl implements MRDC_S15Logic{
	
	@Autowired
	TDeliveryPlanDao tDeliveryPlanDao;
	
	@Autowired
	protected Properties settings;

	@Override
	public TDeliveryPlan exportMRDC_R15(TDeliveryPlan TDeliveryPlan) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		TDeliveryPlan.setMaxRecord(maxRecord);
		if(TDeliveryPlan.getCustomerId() < 0) TDeliveryPlan.setCustomerId(null);
		
		return tDeliveryPlanDao.selectMRDC_R15(TDeliveryPlan);
	}
	
	@Override
	public Integer countMRDC_R15() {
		
		return tDeliveryPlanDao.countMRDC_R15();
	}

}
