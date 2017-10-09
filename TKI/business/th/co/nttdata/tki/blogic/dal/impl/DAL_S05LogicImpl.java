package th.co.nttdata.tki.blogic.dal.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.bean.TDailyMCWK;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.dal.DAL_S05Logic;
import th.co.nttdata.tki.dao.TDailyMCWKDao;

@Service
public class DAL_S05LogicImpl extends AbstractBaseLogic implements DAL_S05Logic {

	@Autowired
	private TDailyMCWKDao dailyMCWKDao;
	@Autowired
	private th.co.nttdata.tki.dao.MReasonDao MReasonDao;

	@Override
	public void delete( TDailyMCWK TDailyMCWK ) {
		dailyMCWKDao.delete(TDailyMCWK);

		TDailyMCWK.getInfos().add( new Message("inf.cmm.003", new String[] {}) );
	}

	@Override
	public TDailyMCWK search( TDailyMCWK TDailyMCWK ) {
		// <!-- Clear the 'dailyMCWKId'. -->
		TDailyMCWK.setDailyMCWKId(null);
		// <!-- Clear the 'customerId'. -->
		if( TDailyMCWK.getCustomerId() != null && TDailyMCWK.getCustomerId() < 0 )
			TDailyMCWK.setCustomerId( null );

		return dailyMCWKDao.queryList(TDailyMCWK);
	}

	@Override
	public TDailyMCWK exportDAL_R02(TDailyMCWK tDailyMCWK) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord", "40000"));
		tDailyMCWK.setMaxRecord(maxRecord);
		
		return dailyMCWKDao.selectDAL_R02(tDailyMCWK);
	}
	
	@Override
	public Integer countDAL_R02() {
		
		return dailyMCWKDao.countDAL_R02();
	}
	
	@Override
	public List<MReason> getreasonList(TDailyMCWK TDailyMCWK){
		MReason MReason = new MReason();
		MReason.setWip(TDailyMCWK.getWip());
		MReason.setReasonType(1);
		MReason.setWipType(3);
		List<MReason> reasonList = TDailyMCWK.getWip().length() == 0 ? MReasonDao.getReasonInWipType(MReason) : MReasonDao.getReasonInWip(MReason);
		return reasonList;
	}
}