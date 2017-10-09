package th.co.nttdata.tki.blogic.mrdc.impl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S18Logic;
import th.co.nttdata.tki.dao.MPartDao;
import th.co.nttdata.tki.dao.TDeliveryPlanDao;

@Service
public class MRDC_S18LogicImpl implements MRDC_S18Logic{
	
	@Autowired
	TDeliveryPlanDao tDeliveryPlanDao;
	
	@Autowired
	MPartDao mPartDao;
	
	@Autowired
	protected Properties settings;

	@Override
	public TDeliveryPlan exportMRDC_R18Summary(TDeliveryPlan TDeliveryPlan) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		TDeliveryPlan.setMaxRecord(maxRecord);
		
		return tDeliveryPlanDao.selectMRDC_R18Summary(TDeliveryPlan);
	}
	
	@Override
	public TDeliveryPlan exportMRDC_R18ProcessList2_Order(TDeliveryPlan TDeliveryPlan) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		TDeliveryPlan.setMaxRecord(maxRecord);
		
		return tDeliveryPlanDao.selectMRDC_R18ProcessList2_Order(TDeliveryPlan);
	}
	
	@Override
	public TDeliveryPlan exportMRDC_R18ProcessList2_Sales(TDeliveryPlan TDeliveryPlan) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		TDeliveryPlan.setMaxRecord(maxRecord);
		
		return tDeliveryPlanDao.selectMRDC_R18ProcessList2_Sales(TDeliveryPlan);
	}
	
	@Override
	public TDeliveryPlan queryWipOfPart( TDeliveryPlan TDeliveryPlan ) {
		
		return tDeliveryPlanDao.queryWipOfPart(TDeliveryPlan);
	}
	
	@Override
	public TDeliveryPlan exportMRDC_R18ProcessList3(TDeliveryPlan TDeliveryPlan) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		TDeliveryPlan.setMaxRecord(maxRecord);
		
		return tDeliveryPlanDao.selectMRDC_R18ProcessList3(TDeliveryPlan);
	}
	
	@Override
	public TDeliveryPlan exportMRDC_R18ProcessList4(TDeliveryPlan TDeliveryPlan) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		TDeliveryPlan.setMaxRecord(maxRecord);
		
		return tDeliveryPlanDao.selectMRDC_R18ProcessList4(TDeliveryPlan);
	}
	
	@Override
	public TDeliveryPlan exportMRDC_R18ProcessList5(TDeliveryPlan TDeliveryPlan) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		TDeliveryPlan.setMaxRecord(maxRecord);
		
		return tDeliveryPlanDao.selectMRDC_R18ProcessList5(TDeliveryPlan);
	}

}
