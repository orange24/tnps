package th.co.nttdata.tki.blogic.dal;

import java.util.List;

import th.co.nttdata.tki.bean.MReason;

import th.co.nttdata.tki.bean.TDailyMCWK;

public interface DAL_S05Logic {

	public void delete(TDailyMCWK TDailyMCWK);

	public TDailyMCWK search(TDailyMCWK TDailyMCWK);

	public TDailyMCWK exportDAL_R02(TDailyMCWK tDailyMCWK);
	
	public Integer countDAL_R02();

	public List<MReason> getreasonList(TDailyMCWK TDailyMCWK);

}