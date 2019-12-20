package th.co.nttdata.tki.blogic.mst;

import th.co.nttdata.tki.bean.MLeadtime;

public interface LDT_S01Logic {
	
	public MLeadtime getLeadtimeList(MLeadtime mLeadtime);

	public MLeadtime getLeadtimeExportList(MLeadtime mLeadtime);
	
	public void saveLeadtime(MLeadtime mLeadtime);
	
	public void setSTUseAll(MLeadtime mLeadtime);

	public MLeadtime getLeadtimeConfig();
	
}
