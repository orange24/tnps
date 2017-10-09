package th.co.nttdata.tki.blogic.mrdc.impl;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.TDailyMC;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S17Logic;
import th.co.nttdata.tki.dao.MReasonDao;
import th.co.nttdata.tki.dao.TDailyMCDetailDao;

@Service
public class MRDC_S17LogicImpl implements MRDC_S17Logic{
	
	@Autowired
	protected Properties settings;
	@Autowired
	public TDailyMCDetailDao tDailyMCDetailDao;
	@Autowired
	public MReasonDao MReasonDao;

	@Override
	public TDailyMC exportMRDC_R17(TDailyMC TDailyMC) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord","40000"));
		TDailyMC.setMaxRecord(maxRecord);
		TDailyMC = tDailyMCDetailDao.selectMRDC_S17(TDailyMC);
		return TDailyMC;
	}
	
	@Override
	public Integer countMRDC_R17() {
		
		return tDailyMCDetailDao.countMRDC_R17();
	}

	@Override
	public List<MReason> getreasonList() {
		return MReasonDao.selectReasonMRDC_S17();
	}
	
	@Override
	public List<MWip> getWip(MWip MWip) {
		return tDailyMCDetailDao.getWip(MWip);
	}

}
