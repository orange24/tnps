package th.co.nttdata.tki.blogic.mrdc.impl;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.TDailyWK;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S10Logic;
import th.co.nttdata.tki.dao.MPartDao;
import th.co.nttdata.tki.dao.MReasonDao;
import th.co.nttdata.tki.dao.MWipDao;
import th.co.nttdata.tki.dao.TDailyWKDao;

@Service
public class MRDC_S10LogicImpl implements MRDC_S10Logic{

	@Autowired
	TDailyWKDao tDailyWKDao;
	@Autowired
	MReasonDao MReasonDao;
	@Autowired
	MPartDao mPartDao;
	@Autowired
	MWipDao mWipDao;
	@Autowired
	protected Properties settings;
	
	@Override
	public TDailyWK exportMRDC_R10(TDailyWK tDailyWK) {
				
		return tDailyWKDao.selectMRDC_R10(tDailyWK);
	}
	
	@Override
	public Integer countMRDC_R10() {
		
		return tDailyWKDao.countMRDC_R10();
	}

	@Override
	public List<MReason> getreasonList() {
		List<MReason> reasonList = MReasonDao.getReasonNGList();
		return reasonList;
	}

	@Override
	public List<MPart> getPartWIP(String wip) {
		MPart mPart = new MPart();
		mPart.setWip(wip);		
		return mPartDao.getPartInWip(mPart);
	}

	@Override
	public List<MWip> getWIP() {
		return mWipDao.getWipFlFn();
	}
	
}