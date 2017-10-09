package th.co.nttdata.tki.blogic.mrdc.impl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.TFGStock;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S16Logic;
import th.co.nttdata.tki.dao.TFGStockDao;

@Service
public class MRDC_S16LogicImpl implements MRDC_S16Logic{
	
	@Autowired
	TFGStockDao tfgStockDao;

	@Autowired
	protected Properties settings;
	
	@Override
	public TFGStock exportMRDC_R16(TFGStock tfgStock) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		tfgStock.setMaxRecord(maxRecord);
		if(tfgStock.getCustomerId() < 0){
			tfgStock.setCustomerId(null);
		}
		return tfgStockDao.selectMRDC_R16(tfgStock);
	}
	
	@Override
	public Integer countMRDC_R16() {
		
		return tfgStockDao.countMRDC_R16();
	}
	
	@Override
	public MWip queryWip(MWip MWip) {
		
		return tfgStockDao.queryWip(MWip);
	}
}
