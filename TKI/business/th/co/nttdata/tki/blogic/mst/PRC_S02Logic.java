package th.co.nttdata.tki.blogic.mst;

import java.util.List;

import th.co.nttdata.tki.bean.MWip;

public interface PRC_S02Logic {
	public MWip search(MWip mWip);
	public void save(MWip mWip);
}
