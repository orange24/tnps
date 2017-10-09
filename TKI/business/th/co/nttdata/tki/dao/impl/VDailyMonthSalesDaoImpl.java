package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.VDailyMonthlySales;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.VDailyMonthSalesDao;

@Repository
@SuppressWarnings("unchecked")
public class VDailyMonthSalesDaoImpl extends AbstractBaseDao implements VDailyMonthSalesDao{

	@Override
	public VDailyMonthlySales selectMRDC_R07(VDailyMonthlySales vdms) {
		vdms.setvDailyMonthlySalesList((List<VDailyMonthlySales>)queryForList("v_daily_monthlysales.queryV_Daily_MonthlySales", vdms));
		return vdms;
	}
	
	@Override
	public Integer countMRDC_R07() {
		
		return (Integer) queryForObject("v_daily_monthlysales.countMRDC_R07");
	}
	
	@Override
	public VDailyMonthlySales sizeMRDC_R07(VDailyMonthlySales vdms) {
		vdms.setvDailyMonthlySalesList((List<VDailyMonthlySales>)queryForList("v_daily_monthlysales.sizeV_Daily_MonthlySales", vdms));
		return vdms;
	}

	@Override
	public VDailyMonthlySales selectMRDC_R06(VDailyMonthlySales vdms) {
		vdms.setvDailyMonthlySalesList((List<VDailyMonthlySales>)queryForList("v_daily_monthlysales.queryMRDC_R06", vdms));
		return vdms;
	}
	
	@Override
	public Integer countMRDC_R06() {
		
		return (Integer) queryForObject("v_daily_monthlysales.countMRDC_R06");
	}
	
	@Override
	public VDailyMonthlySales sizeMRDC_R06(VDailyMonthlySales vdms) {
		vdms.setvDailyMonthlySalesList((List<VDailyMonthlySales>)queryForList("v_daily_monthlysales.sizeMRDC_R06", vdms));
		return vdms;
	}

}
