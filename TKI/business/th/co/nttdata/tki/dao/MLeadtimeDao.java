package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.MLeadtime;
import th.co.nttdata.tki.bean.MPart;

public interface MLeadtimeDao {
	
	public MLeadtime queryLeadtimeList(MLeadtime mLeadtime);
	
	public void insertLeadtime(MLeadtime mLeadtime);
	
	public void updateSTUseAll(MLeadtime mLeadtime);
	
	public List<MLeadtime> getWipPart();
	
	public void insertPartWip(MLeadtime mLeadtime);
	
	public MLeadtime queryLeadTimeConfig();
	
	public void deleteLeadTimeConfig();

	public void insertLeadTimeConfig(MLeadtime mLeadtime);

	public List<MPart> getSTByPartIdAndWip(MLeadtime object);
}
