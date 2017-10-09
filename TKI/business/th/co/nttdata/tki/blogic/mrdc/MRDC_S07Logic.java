package th.co.nttdata.tki.blogic.mrdc;

import th.co.nttdata.tki.bean.VDailyMonthlySales;

public interface MRDC_S07Logic {
	
	public VDailyMonthlySales exportMRDC_R07(VDailyMonthlySales vdms);
	
	public VDailyMonthlySales sizeMRDC_R07(VDailyMonthlySales vdms);
	
	public Integer countMRDC_R07();
}
