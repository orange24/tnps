package th.co.nttdata.tki.blogic.mst;

import th.co.nttdata.tki.bean.MMoldDetail;

public interface MLD_S01Logic {
	
	public MMoldDetail search(MMoldDetail mdDetail);

	public void delete(MMoldDetail mdDetail);
	
	public MMoldDetail exportMLD_R01(MMoldDetail mdDetail);
}
