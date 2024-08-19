package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.MReason;

public interface MReasonDao {

	public MReason getReason(MReason MReason);

	public List<MReason> getReasonList();

	public List<MReason> getReasonNGList();

	public List<MReason> getReasonMCStopList();

	public List<MReason> getReasonList(MReason MReason);

	public List<MReason> getReasonInWip(MReason MReason);
	
	public List<MReason> getParentReason();
	
	public void insertReason(MReason MReason);
	
	public void updateReason(MReason MReason);
	
	public void deleteReasonWip(MReason MReason);
	
	public void deleteReason(MReason MReason);
	
	public MReason searchReason(MReason MReason);
	
	public MReason searchReasonUse(MReason MReason);
	
	public MReason searchReasonOnly(MReason MReason);
	
	public List<MReason> checkDupReasonCode(MReason MReason);
	
	public void deleteReasonWipByType(MReason MReason);
	
	public void insertReasonWip(MReason MReason);
	
	public List<MReason> selectReasonMRDC_S17();
	
	public List<MReason> getReasonInWipType(MReason MReason);
	
	public List<MReason> getReasonMaster();
}