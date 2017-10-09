package th.co.nttdata.tki.blogic.mrdc.impl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.TDailyMC;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S09Logic;
import th.co.nttdata.tki.dao.TDailyMCDao;

@Service
public class MRDC_S09LogicImpl implements MRDC_S09Logic{

	@Autowired
	protected Properties settings;
	
	@Autowired
	private TDailyMCDao TDailyMCDao;
	
	@Override
	public TDailyMC exportMRDC_R09(TDailyMC TDailyMC) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord","40000"));
		TDailyMC.setMaxRecord(maxRecord);
		return TDailyMCDao.selectMRDC_R09(TDailyMC);
	}
	
	@Override
	public Integer countMRDC_R09() {
		
		return TDailyMCDao.countMRDC_R09();
	}

}
