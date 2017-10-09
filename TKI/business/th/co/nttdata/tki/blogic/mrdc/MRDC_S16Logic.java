package th.co.nttdata.tki.blogic.mrdc;

import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.TFGStock;

public interface MRDC_S16Logic {
	
	public TFGStock exportMRDC_R16(TFGStock tfgStock);
	
	public MWip queryWip(MWip MWip);
	
	public Integer countMRDC_R16();
	
}
