package th.co.nttdata.tki.blogic.mst;

import th.co.nttdata.tki.bean.MReason;

public interface RES_S03Logic {
	
	public MReason searchResson(MReason mReason);
	
	public void saveReasonUse(MReason mReason);
}
