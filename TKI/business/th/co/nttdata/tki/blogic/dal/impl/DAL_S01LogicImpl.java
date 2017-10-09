package th.co.nttdata.tki.blogic.dal.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.bean.TDailyMC;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.dal.DAL_S01Logic;
import th.co.nttdata.tki.dao.MReasonDao;
import th.co.nttdata.tki.dao.TDailyMCDao;

@Service
public class DAL_S01LogicImpl extends AbstractBaseLogic implements DAL_S01Logic {

	@Autowired
	private TDailyMCDao dailyMCDao;
	@Autowired
	private MReasonDao MReasonDao;

	@Override
	public void delete( TDailyMC TDailyMC ) {
		dailyMCDao.delete(TDailyMC);

		TDailyMC.getInfos().add( new Message("inf.cmm.003", new String[] {}) );
	}

	@Override
	public TDailyMC search( TDailyMC TDailyMC ) {
		// <!-- Clear the 'dailyMCId'. -->
		TDailyMC.setDailyMCId(null);
		return dailyMCDao.queryList(TDailyMC);
	}

	@Override
	public TDailyMC exportDAL_R01( TDailyMC TDailyMC ) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		TDailyMC.setMaxRecord(maxRecord);
		
		return dailyMCDao.selectDAL_R01(TDailyMC);
	}
	
	@Override
	public Integer countDAL_R01() {
		
		return dailyMCDao.countDAL_R01();
	}

	@Override
	public List<MReason> getreasonList(TDailyMC TDailyMC) {
		MReason MReason = new MReason();
		MReason.setWip(TDailyMC.getWip());
		MReason.setReasonType(1);
		MReason.setWipType(1);
		List<MReason> reasonList = TDailyMC.getWip().length() == 0 ? MReasonDao.getReasonInWipType(MReason) : MReasonDao.getReasonInWip(MReason);
		return reasonList;
	}
}
