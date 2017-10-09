package th.co.nttdata.tki.dao;

import th.co.nttdata.tki.bean.TWIPDeadline;

public interface TWIPDeadlineDao {

	public TWIPDeadline getDeadlineList(TWIPDeadline TWIPDeadline);
	public TWIPDeadline selectWIP_R01(TWIPDeadline TWIPDeadline);
}