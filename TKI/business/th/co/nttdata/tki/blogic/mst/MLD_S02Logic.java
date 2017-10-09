package th.co.nttdata.tki.blogic.mst;

import java.util.List;

import th.co.nttdata.tki.bean.MMoldDetail;
import th.co.nttdata.tki.bean.MPart;

public interface MLD_S02Logic {

	public List<MMoldDetail> getMoldName();

	public List<MMoldDetail> getMoldNo(MMoldDetail mDetail);

	public List<MPart> searchPart(MMoldDetail mDetail);

	public MMoldDetail getMoldDetail(MMoldDetail mDetail);

	public MMoldDetail add(MMoldDetail mDetail);

	public MMoldDetail edit(MMoldDetail mDetail);

	public int checkDupMoldNo(MMoldDetail mDetail);

	public int checkMoldName(MMoldDetail mDetail);
	
	public void addEditMoldName(MMoldDetail mDetail);

	public void delete(MMoldDetail mDetail);

	public boolean checkRelateMold(MMoldDetail mMoldDetail);

	public int checkDupMoldNoEdit(MMoldDetail mDetail);

}
