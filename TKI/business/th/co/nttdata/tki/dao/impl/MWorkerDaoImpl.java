package th.co.nttdata.tki.dao.impl;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MWorker;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MWorkerDao;

@Repository
public class MWorkerDaoImpl extends AbstractBaseDao implements MWorkerDao{

	@Override
	public MWorker getMWorker() {		
		return (MWorker)queryForObject("m_worker.queryMWorker");
	}

	@Override
	public void update(MWorker mWorker) {
		update("m_worker.update",mWorker);
	}

}
