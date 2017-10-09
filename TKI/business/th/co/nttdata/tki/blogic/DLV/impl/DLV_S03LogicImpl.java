package th.co.nttdata.tki.blogic.DLV.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.DLV.DLV_S03Logic;
import th.co.nttdata.tki.dao.TDeliveryPlanDao;

@Service
public class DLV_S03LogicImpl extends AbstractBaseLogic implements DLV_S03Logic  {
	
	@Autowired
	private TDeliveryPlanDao tDeliveryPlanDao;

	@Override
	public TDeliveryPlan exportDLV_R02( TDeliveryPlan TDeliveryPlan ) {

		return tDeliveryPlanDao.selectDLV_R02(TDeliveryPlan);
	}
}
