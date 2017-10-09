package th.co.nttdata.tki.blogic.mrdc;

import th.co.nttdata.tki.bean.TDeliveryPlan;

public interface MRDC_S18Logic {
	public TDeliveryPlan exportMRDC_R18Summary(TDeliveryPlan TDeliveryPlan);
	
	public TDeliveryPlan exportMRDC_R18ProcessList2_Order(TDeliveryPlan TDeliveryPlan);
	
	public TDeliveryPlan exportMRDC_R18ProcessList2_Sales(TDeliveryPlan TDeliveryPlan);
	
	public TDeliveryPlan queryWipOfPart( TDeliveryPlan TDeliveryPlan );
	
	public TDeliveryPlan exportMRDC_R18ProcessList3(TDeliveryPlan TDeliveryPlan);
	
	public TDeliveryPlan exportMRDC_R18ProcessList4(TDeliveryPlan TDeliveryPlan);
	
	public TDeliveryPlan exportMRDC_R18ProcessList5(TDeliveryPlan TDeliveryPlan);
	
}