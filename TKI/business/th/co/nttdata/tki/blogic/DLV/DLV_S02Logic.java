package th.co.nttdata.tki.blogic.DLV;

import java.util.List;

import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.TDeliveryPlan;

public interface DLV_S02Logic {
	public TDeliveryPlan save(TDeliveryPlan TDeliveryPlan);
	public TDeliveryPlan search(TDeliveryPlan TDeliveryPlan);
	public TDeliveryPlan exportDLV_R01(TDeliveryPlan TDeliveryPlan);
	public void deleteByFg(TDeliveryPlan TDeliveryPlan);
	public TDeliveryPlan countDate(TDeliveryPlan TDeliveryPlan);
	public TDeliveryPlan insertFlag(TDeliveryPlan TDeliveryPlan);
	public List<MPart> getFgList(TDeliveryPlan TDeliveryPlan);
	public TDeliveryPlan deliveryPlanReport(
			TDeliveryPlan tDeliveryPlan);
}
