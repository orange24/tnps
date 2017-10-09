package th.co.nttdata.tki.blogic.mrdc.impl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.TPending;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S08Logic;
import th.co.nttdata.tki.dao.TPendingDao;

@Service
public class MRDC_S08LogicImpl implements MRDC_S08Logic{
	
	@Autowired
	TPendingDao tPendingDao;
	@Autowired
	protected Properties settings;

	@Override
	public TPending exportMRDC_R08(TPending tPending) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord","40000"));
		tPending.setMaxRecord(maxRecord);
		return tPendingDao.queryViewPendingList(tPending);
	}
	
	@Override
	public Integer countMRDC_R08() {
		
		return tPendingDao.countMRDC_R08();
	}

}
