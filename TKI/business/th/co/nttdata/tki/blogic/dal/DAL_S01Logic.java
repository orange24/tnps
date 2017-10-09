package th.co.nttdata.tki.blogic.dal;

import java.util.List;

import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.TDailyMC;

public interface DAL_S01Logic {

	public void delete(TDailyMC TDailyMC);

	public TDailyMC search(TDailyMC TDailyMC);

	public TDailyMC exportDAL_R01(TDailyMC TDailyMC);
	
	public Integer countDAL_R01();
	
	public List<MReason> getreasonList(TDailyMC TDailyMC);
}
