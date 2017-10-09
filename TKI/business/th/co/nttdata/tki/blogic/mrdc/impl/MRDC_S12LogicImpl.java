package th.co.nttdata.tki.blogic.mrdc.impl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.TDailyWK;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S12Logic;
import th.co.nttdata.tki.dao.TDailyWKDao;

@Service
public class MRDC_S12LogicImpl implements MRDC_S12Logic{
	
	@Autowired
	TDailyWKDao tDailyWKDao;
	
	@Autowired
	protected Properties settings;

	@Override
	public TDailyWK exportMRDC_R12(TDailyWK TDailyWK) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		TDailyWK.setMaxRecord(maxRecord);
		TDailyWK.setCustomerId(TDailyWK.getCustomerId() < 0 ? null : TDailyWK.getCustomerId());
		
		return tDailyWKDao.selectMRDC_R12(TDailyWK);
	}
	
	@Override
	public Integer countMRDC_R12() {
		
		return tDailyWKDao.countMRDC_R12();
	}

}
