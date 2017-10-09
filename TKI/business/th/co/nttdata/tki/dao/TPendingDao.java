package th.co.nttdata.tki.dao;

import th.co.nttdata.tki.bean.TPending;

public interface TPendingDao {

	public int insert(TPending pending);

	public TPending query(TPending pending);

	public TPending queryHis(TPending pending);
	
	public TPending queryViewPendingList(TPending tPending);
	
	public Integer countMRDC_R08();

}