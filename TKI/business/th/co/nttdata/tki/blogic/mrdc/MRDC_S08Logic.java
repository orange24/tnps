package th.co.nttdata.tki.blogic.mrdc;

import th.co.nttdata.tki.bean.TPending;

public interface MRDC_S08Logic {
	public TPending exportMRDC_R08(TPending tPending);
	
	public Integer countMRDC_R08();
}