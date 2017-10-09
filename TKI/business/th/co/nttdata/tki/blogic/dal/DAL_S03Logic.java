package th.co.nttdata.tki.blogic.dal;

import java.util.List;

import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.TDailyWK;

public interface DAL_S03Logic {

	public void delete(TDailyWK TDailyWK);

	public TDailyWK search(TDailyWK TDailyWK);
	
	public TDailyWK exportDAL_R03(TDailyWK TDailyWK);
	
	public Integer countDAL_R03();
	
	public List<MReason> getreasonList(TDailyWK TDailyWK);
}