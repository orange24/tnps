package th.co.nttdata.tki.blogic.mst;

import java.util.LinkedHashMap;
import java.util.List;

import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.filter.MWipFilter;

public interface PRC_S01Logic {
	public List<MWip> searchList(MWip mWip);

	public MWip search(MWip mWip);

	public void delete(MWip mWip);

	public void save(MWip mWip);

	public void saveList(List<LinkedHashMap> mWip) throws Exception ;

	public MWip selectWipListByWip(MWip mWip);

	public MWip check(MWip mWip);

	public List<MWip> getWipTypeName();

	public MWip selectWipListByWipFilter(MWipFilter mwipFilter);
}
