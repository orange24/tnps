package th.co.nttdata.tki.blogic.dal;

import th.co.nttdata.tki.bean.TPending;

public interface PND_S01Logic {

	public TPending search(TPending TPending);
	
	public int save(TPending TPending);
}
