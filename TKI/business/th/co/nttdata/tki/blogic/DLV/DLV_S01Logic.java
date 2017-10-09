package th.co.nttdata.tki.blogic.DLV;

import th.co.nttdata.tki.bean.TDeliveryPlan;

public interface DLV_S01Logic {
	public TDeliveryPlan copyPlan(TDeliveryPlan TDeliveryPlan);
	public TDeliveryPlan search(TDeliveryPlan TDeliveryPlan);
	public void deleteByPlan(TDeliveryPlan TDeliveryPlan);
}
