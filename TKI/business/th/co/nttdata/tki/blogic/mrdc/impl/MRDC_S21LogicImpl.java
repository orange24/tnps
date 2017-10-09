package th.co.nttdata.tki.blogic.mrdc.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MMoldDetail;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S21Logic;
import th.co.nttdata.tki.dao.MMoldDetailDao;

@Service
public class MRDC_S21LogicImpl extends AbstractBaseLogic implements MRDC_S21Logic {

	@Autowired
	MMoldDetailDao moldDetailDao;
	
	@Override
	public MMoldDetail exportMRDC_R21(MMoldDetail mdDetail) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		mdDetail.setMaxRecord(maxRecord);
		
		if(mdDetail.getPercent() != null){
			BigDecimal percent = new BigDecimal(mdDetail.getPercent()/100);
			mdDetail.setPercent(percent.doubleValue());
		}
		moldDetailDao.exportMRDC_R21(mdDetail);
		return mdDetail;
	}
	
	@Override
	public Integer countMRDC_R21() {
		
		return moldDetailDao.countMRDC_R21();
	}

}
