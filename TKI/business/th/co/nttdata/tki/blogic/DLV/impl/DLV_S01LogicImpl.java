package th.co.nttdata.tki.blogic.DLV.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.DLV.DLV_S01Logic;
import th.co.nttdata.tki.dao.TDeliveryPlanDao;

@Service
public class DLV_S01LogicImpl extends AbstractBaseLogic implements DLV_S01Logic  {
	
	@Autowired
	private TDeliveryPlanDao tDeliveryPlanDao;

	@Override
	public TDeliveryPlan search(TDeliveryPlan TDeliveryPlan) {		
		TDeliveryPlan = tDeliveryPlanDao.getPlanList(TDeliveryPlan);
		if(TDeliveryPlan.getDeliveryPlanId() != null){
			TDeliveryPlan.setInsertFlag(tDeliveryPlanDao.selectInsertFlag(TDeliveryPlan));
		}
		return TDeliveryPlan;
	}
	
	@Override
	public TDeliveryPlan copyPlan(TDeliveryPlan TDeliveryPlan) {
		tDeliveryPlanDao.copyPlan(TDeliveryPlan);
		tDeliveryPlanDao.copyDate(TDeliveryPlan);
		tDeliveryPlanDao.copyTime(TDeliveryPlan);
		tDeliveryPlanDao.copyBalanceOrder(TDeliveryPlan);
				
		return TDeliveryPlan;
	}
	
	@Override
	public void deleteByPlan(TDeliveryPlan TDeliveryPlan) {
		tDeliveryPlanDao.deleteByPlan(TDeliveryPlan);
		TDeliveryPlan.getInfos().add( new Message("inf.cmm.003", new String[] {}) );
	}
}
