package th.co.nttdata.tki.blogic.mrdc;

import th.co.nttdata.tki.bean.TDeliveryPlan;

public interface MRDC_S14Logic {
	public TDeliveryPlan exportMRDC_R14(TDeliveryPlan TDeliveryPlan);
	
	public Integer countMRDC_R14();
}