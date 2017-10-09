package th.co.nttdata.tki.blogic.mrdc;

import th.co.nttdata.tki.bean.VDailyMonthlySales;

public interface MRDC_S06Logic {
	
	public VDailyMonthlySales exportMRDC_R06(VDailyMonthlySales vdms);
	
	public VDailyMonthlySales sizeMRDC_R06(VDailyMonthlySales vdms);
	
	public Integer countMRDC_R06();
	
}
