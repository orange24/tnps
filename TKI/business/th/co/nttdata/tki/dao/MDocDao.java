package th.co.nttdata.tki.dao;

import th.co.nttdata.tki.bean.MDocControl;

public interface MDocDao {

	public MDocControl getDocNo();
	
	public int updateDocno(MDocControl doc);
	
}