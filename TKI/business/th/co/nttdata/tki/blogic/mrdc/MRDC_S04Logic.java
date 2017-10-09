package th.co.nttdata.tki.blogic.mrdc;

import th.co.nttdata.tki.bean.TDeliveryPlan;

public interface MRDC_S04Logic {
	public TDeliveryPlan exportMRDC_R04ProductMaster(TDeliveryPlan TDeliveryPlan);
	
	public Integer countMRDC_R04ProductMaster();
}