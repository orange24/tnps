package th.co.nttdata.tki.blogic.mrdc;

import java.util.List;

import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.TDailyWK;

public interface MRDC_S10Logic {
	
	public TDailyWK exportMRDC_R10(TDailyWK tDailyWK);
	
	public Integer countMRDC_R10();
	
	public List<MReason> getreasonList();
	
	public List<MWip> getWIP();
	
	public List<MPart> getPartWIP(String wip);
}

