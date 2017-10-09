package th.co.nttdata.tki.blogic.mrdc.impl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S031Logic;
import th.co.nttdata.tki.dao.TDeliveryPlanDao;

@Service
public class MRDC_S031LogicImpl implements MRDC_S031Logic{
	
	@Autowired
	TDeliveryPlanDao tDeliveryPlanDao;
	
	@Autowired
	protected Properties settings;

	@Override
	public TDeliveryPlan searchMRDC_S031(TDeliveryPlan TDeliveryPlan) {
		if(TDeliveryPlan.getCustomerId() < 0) TDeliveryPlan.setCustomerId(null);
		if(TDeliveryPlan.getMaterialId() < 0) TDeliveryPlan.setMaterialId(null);
		
		return tDeliveryPlanDao.searchMRDC_S031(TDeliveryPlan);
	}

}
