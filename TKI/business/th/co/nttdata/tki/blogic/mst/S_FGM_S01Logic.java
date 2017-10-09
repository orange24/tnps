package th.co.nttdata.tki.blogic.mst;

import java.util.List;

import th.co.nttdata.tki.bean.FgMaster;
import th.co.nttdata.tki.bean.filter.FgMasterFilter;

public interface S_FGM_S01Logic {
	public List<FgMaster> getFgMastertSearchList(FgMaster fgMaster);

	public List<FgMaster> getUomList();

	public List<FgMaster> getClassifyBusiness();

	public List<FgMaster> getPlace();

	public List<FgMaster> getSubbusiness();

	public void saveList(List<FgMaster> fgMasterList);

	public List<FgMasterFilter> search(FgMasterFilter mFgmaster);

	public List<FgMaster> getCurrencyList();
}
