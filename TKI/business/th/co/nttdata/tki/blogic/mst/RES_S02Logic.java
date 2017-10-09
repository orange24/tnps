package th.co.nttdata.tki.blogic.mst;

import java.util.List;

import th.co.nttdata.tki.bean.MReason;

public interface RES_S02Logic {
	
	public List<MReason> getParentReason();
	
	public MReason getReasonById(MReason mReason);
	
	public MReason save(MReason mReason);
	
	public MReason edit(MReason mReason);
	
	public void delete(MReason mReason);

}
