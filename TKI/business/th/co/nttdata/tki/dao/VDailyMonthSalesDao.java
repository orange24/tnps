package th.co.nttdata.tki.dao;

import th.co.nttdata.tki.bean.VDailyMonthlySales;

public interface VDailyMonthSalesDao {
	
	public VDailyMonthlySales selectMRDC_R07(VDailyMonthlySales vdms);
	
	public VDailyMonthlySales sizeMRDC_R07(VDailyMonthlySales vdms);
	
	public Integer countMRDC_R07();
	
	public VDailyMonthlySales selectMRDC_R06(VDailyMonthlySales vdms);
	
	public VDailyMonthlySales sizeMRDC_R06(VDailyMonthlySales vdms);
	
	public Integer countMRDC_R06();

}
