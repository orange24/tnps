package th.co.nttdata.tki.blogic.mrdc;

import th.co.nttdata.tki.bean.TDeliveryPlan;

public interface MRDC_S01Logic {
	public TDeliveryPlan exportMRDC_R01ProductCompositionList1(TDeliveryPlan TDeliveryPlan);
	public TDeliveryPlan exportMRDC_R01ProductCompositionList2(TDeliveryPlan TDeliveryPlan);
	public TDeliveryPlan queryWipOfPart( TDeliveryPlan TDeliveryPlan );
}