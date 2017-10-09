package th.co.nttdata.tki.blogic.mrdc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.VDailyMonthlySales;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S07Logic;
import th.co.nttdata.tki.dao.VDailyMonthSalesDao;

@Service
public class MRDC_S07LogicImpl implements MRDC_S07Logic{

	@Autowired
	VDailyMonthSalesDao vdmsDao;
	@Autowired
	protected java.util.Properties settings;
	
	@Override
	public VDailyMonthlySales exportMRDC_R07(VDailyMonthlySales vdms) {
		vdms.setsCustomerId(vdms.getsCustomerId() < 0 ? null:vdms.getsCustomerId());
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord","40000"));
		vdms.setMaxRecord(maxRecord);
		return vdmsDao.selectMRDC_R07(vdms);
	}
	
	@Override
	public VDailyMonthlySales sizeMRDC_R07(VDailyMonthlySales vdms) {
		vdms.setsCustomerId(vdms.getsCustomerId() < 0 ? null:vdms.getsCustomerId());
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord","40000"));
		vdms.setMaxRecord(maxRecord);
		return vdmsDao.selectMRDC_R07(vdms);
	}
	
	@Override
	public Integer countMRDC_R07() {
		
		return vdmsDao.countMRDC_R07();
	}

}