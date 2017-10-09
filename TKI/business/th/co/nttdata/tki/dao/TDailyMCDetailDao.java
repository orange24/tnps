package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.TDCPlan;
import th.co.nttdata.tki.bean.TDailyMC;

public interface TDailyMCDetailDao {

	public void delete(TDailyMC TDailyMC);

	public void insert(TDailyMC TDailyMC);

	public void query(TDailyMC TDailyMC);
	
	public TDailyMC selectMRDC_S17(TDailyMC TDailyMC);
	
	public Integer countMRDC_R17();
	
	public List<MWip> getWip(MWip MWip);
	
	public Integer selectCheckInputActual(TDCPlan object);
}
