package th.co.nttdata.tki.blogic.mrdc.impl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S01Logic;
import th.co.nttdata.tki.dao.TDeliveryPlanDao;

@Service
public class MRDC_S01LogicImpl implements MRDC_S01Logic{
	
	@Autowired
	TDeliveryPlanDao tDeliveryPlanDao;
	
	@Autowired
	protected Properties settings;

	@Override
	public TDeliveryPlan exportMRDC_R01ProductCompositionList1(TDeliveryPlan TDeliveryPlan) {
		
		return tDeliveryPlanDao.selectMRDC_R01ProductCompositionList1(TDeliveryPlan);
	}
	
	@Override
	public TDeliveryPlan exportMRDC_R01ProductCompositionList2(TDeliveryPlan TDeliveryPlan) {
		TDeliveryPlan = tDeliveryPlanDao.selectMRDC_R01ProductCompositionList2(TDeliveryPlan);
		
		if(TDeliveryPlan.getPlan2List().size() == 0){
			TDeliveryPlan = tDeliveryPlanDao.getWipList(TDeliveryPlan);
		}		
		return TDeliveryPlan;
	}
	
	@Override
	public TDeliveryPlan queryWipOfPart( TDeliveryPlan TDeliveryPlan ) {
		
		return tDeliveryPlanDao.queryWipOfPart(TDeliveryPlan);
	}

}
