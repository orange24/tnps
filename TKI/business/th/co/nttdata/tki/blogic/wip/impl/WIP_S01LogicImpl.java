package th.co.nttdata.tki.blogic.wip.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.TWIPDeadline;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.wip.WIP_S01Logic;
import th.co.nttdata.tki.dao.TWIPDeadlineDao;

@Service
public class WIP_S01LogicImpl extends AbstractBaseLogic implements WIP_S01Logic {

	@Autowired
	private TWIPDeadlineDao deadlineDao;
	
	@Override
	public TWIPDeadline search( TWIPDeadline TWIPDeadline ) {
		TWIPDeadline = deadlineDao.getDeadlineList(TWIPDeadline);
		
		return TWIPDeadline;
	}
	
	@Override
	public TWIPDeadline exportWIP_R01( TWIPDeadline TWIPDeadline ) {
		TWIPDeadline = deadlineDao.selectWIP_R01(TWIPDeadline);
		
		return TWIPDeadline;
	}
}