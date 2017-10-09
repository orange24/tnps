package th.co.nttdata.tki.blogic.mrdc.impl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S032Logic;
import th.co.nttdata.tki.dao.TDeliveryPlanDao;

@Service
public class MRDC_S032LogicImpl implements MRDC_S032Logic{
	
	@Autowired
	TDeliveryPlanDao tDeliveryPlanDao;
	
	@Autowired
	protected Properties settings;

	@Override
	public TDeliveryPlan searchMRDC_S032(TDeliveryPlan TDeliveryPlan) {
		
		return tDeliveryPlanDao.searchMRDC_S032(TDeliveryPlan);
	}

}
