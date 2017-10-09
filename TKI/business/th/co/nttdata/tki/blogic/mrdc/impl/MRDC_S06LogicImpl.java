package th.co.nttdata.tki.blogic.mrdc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.VDailyMonthlySales;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S06Logic;
import th.co.nttdata.tki.dao.VDailyMonthSalesDao;

@Service
public class MRDC_S06LogicImpl implements MRDC_S06Logic{

	@Autowired
	VDailyMonthSalesDao vdmsDao;
	@Autowired
	protected java.util.Properties settings;
	
	@Override
	public VDailyMonthlySales exportMRDC_R06(VDailyMonthlySales vdms) {
		vdms.setsCustomerId(vdms.getsCustomerId() < 0 ? null:vdms.getsCustomerId());
		
		return vdmsDao.selectMRDC_R06(vdms);
	}
	
	@Override
	public VDailyMonthlySales sizeMRDC_R06(VDailyMonthlySales vdms) {
		vdms.setsCustomerId(vdms.getsCustomerId() < 0 ? null:vdms.getsCustomerId());
		
		return vdmsDao.sizeMRDC_R06(vdms);
	}
	
	@Override
	public Integer countMRDC_R06() {
		
		return vdmsDao.countMRDC_R06();
	}

}
