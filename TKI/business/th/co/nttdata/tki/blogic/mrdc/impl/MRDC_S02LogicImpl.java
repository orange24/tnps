package th.co.nttdata.tki.blogic.mrdc.impl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.TDailyWK;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S02Logic;
import th.co.nttdata.tki.dao.TDailyWKDao;

@Service
public class MRDC_S02LogicImpl implements MRDC_S02Logic{
	
	@Autowired
	TDailyWKDao tDailyWKDao;
	
	@Autowired
	protected Properties settings;

	@Override
	public TDailyWK exportMRDC_R02(TDailyWK TDailyWK) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		TDailyWK.setMaxRecord(maxRecord);
		if(TDailyWK.getMaterialId() < 0) TDailyWK.setMaterialId(null);
		if(TDailyWK.getCustomerId() < 0) TDailyWK.setCustomerId(null);
		
		return tDailyWKDao.selectMRDC_R02(TDailyWK);
	}
	
	@Override
	public Integer countMRDC_R02() {
		
		return tDailyWKDao.countMRDC_R02();
	}

}
