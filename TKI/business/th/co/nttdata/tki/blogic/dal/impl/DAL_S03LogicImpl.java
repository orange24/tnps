package th.co.nttdata.tki.blogic.dal.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.bean.TDailyWK;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.dal.DAL_S03Logic;
import th.co.nttdata.tki.dao.MReasonDao;
import th.co.nttdata.tki.dao.TDailyWKDao;

@Service
public class DAL_S03LogicImpl extends AbstractBaseLogic implements DAL_S03Logic {

	@Autowired
	private TDailyWKDao dailyWKDao;
	@Autowired
	private MReasonDao MReasonDao;

	@Override
	public void delete( TDailyWK TDailyWK ) {
		dailyWKDao.delete(TDailyWK);

		TDailyWK.getInfos().add( new Message("inf.cmm.003", new String[] {}) );
	}

	@Override
	public TDailyWK search( TDailyWK TDailyWK ) {
		// <!-- Clear the 'dailyWKId'. -->
		TDailyWK.setDailyWKId(null);
		return dailyWKDao.queryList(TDailyWK);
	}
	
	@Override
	public TDailyWK exportDAL_R03( TDailyWK TDailyWK ) {
		return dailyWKDao.selectDAL_R03(TDailyWK);
	}
	
	@Override
	public Integer countDAL_R03() {
		
		return dailyWKDao.countDAL_R03();
	}

	@Override
	public List<MReason> getreasonList(TDailyWK TDailyWK) {
		MReason MReason = new MReason();
		MReason.setWip(TDailyWK.getWip());
		MReason.setReasonType(1);
		MReason.setWipType(2);
		List<MReason> reasonList = TDailyWK.getWip().length() == 0 ? MReasonDao.getReasonInWipType(MReason) : MReasonDao.getReasonInWip(MReason);
		return reasonList;
	}
}