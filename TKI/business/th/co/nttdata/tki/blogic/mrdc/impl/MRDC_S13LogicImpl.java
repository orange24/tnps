package th.co.nttdata.tki.blogic.mrdc.impl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.TDailyWK;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S13Logic;
import th.co.nttdata.tki.dao.TDailyWKDao;

@Service
public class MRDC_S13LogicImpl implements MRDC_S13Logic{
	
	@Autowired
	TDailyWKDao tDailyWKDao;
	
	@Autowired
	protected Properties settings;

	@Override
	public TDailyWK exportMRDC_R13(TDailyWK TDailyWK) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		TDailyWK.setMaxRecord(maxRecord);
		
		return tDailyWKDao.selectMRDC_R13(TDailyWK);
	}
	
	@Override
	public Integer countMRDC_R13() {
		
		return tDailyWKDao.countMRDC_R13();
	}

}
