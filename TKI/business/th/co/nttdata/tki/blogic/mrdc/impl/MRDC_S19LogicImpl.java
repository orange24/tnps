package th.co.nttdata.tki.blogic.mrdc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.TWip;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S19Logic;
import th.co.nttdata.tki.dao.TWipStockDao;

@Service
public class MRDC_S19LogicImpl implements MRDC_S19Logic{
	
	@Autowired
	public TWipStockDao tWipStockDao;

	@Override
	public TWip getWipStockInquiry(TWip tWip) {
		return tWipStockDao.selectMRDC_S19(tWip);
	}

}
