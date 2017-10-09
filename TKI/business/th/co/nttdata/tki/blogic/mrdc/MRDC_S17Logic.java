package th.co.nttdata.tki.blogic.mrdc;

import java.util.List;

import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.TDailyMC;

public interface MRDC_S17Logic {
	
	public TDailyMC exportMRDC_R17(TDailyMC TDailyMC);
	
	public Integer countMRDC_R17();
	
	public List<MReason> getreasonList();
	
	public List<MWip> getWip(MWip MWip);
}
