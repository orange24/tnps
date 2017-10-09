package th.co.nttdata.tki.blogic.mrdc;

import th.co.nttdata.tki.bean.TDeliveryPlan;

public interface MRDC_S15Logic {
	public TDeliveryPlan exportMRDC_R15(TDeliveryPlan TDeliveryPlan);
	
	public Integer countMRDC_R15();
}