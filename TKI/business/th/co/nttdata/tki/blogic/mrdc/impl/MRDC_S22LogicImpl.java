package th.co.nttdata.tki.blogic.mrdc.impl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.TDailyWK;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S22Logic;
import th.co.nttdata.tki.dao.TDailyWKDao;

@Service
public class MRDC_S22LogicImpl implements MRDC_S22Logic{
	
	@Autowired
	TDailyWKDao tDailyWKDao;
	
	@Autowired
	protected Properties settings;

	@Override
	public TDailyWK exportMRDC_R22(TDailyWK TDailyWK) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		TDailyWK.setMaxRecord(maxRecord);
		TDailyWK.setMaterialId(TDailyWK.getMaterialId() < 0 ? null : TDailyWK.getMaterialId());
		
		return tDailyWKDao.selectMRDC_R22(TDailyWK);
	}
	
	@Override
	public Integer countMRDC_R22() {
		
		return tDailyWKDao.countMRDC_R22();
	}

}
