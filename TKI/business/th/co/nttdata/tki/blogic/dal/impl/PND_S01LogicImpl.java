package th.co.nttdata.tki.blogic.dal.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.TPending;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.dal.PND_S01Logic;
import th.co.nttdata.tki.dao.TPendingDao;

@Service
public class PND_S01LogicImpl extends AbstractBaseLogic implements PND_S01Logic {

	@Autowired
	private TPendingDao tPendingDao;

	@Override
	public TPending search( TPending tPending ) {
		return tPendingDao.query(tPending);
	}
	
	@Override
	public int save( TPending tPending ) {
		int adjustId = tPendingDao.insert(tPending);
		return adjustId;
	}
}
