package th.co.nttdata.tki.blogic.mst.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MWorker;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.mst.WOR_S01Logic;
import th.co.nttdata.tki.dao.MWorkerDao;

@Service
public class WOR_S01LogicImpl extends AbstractBaseLogic implements WOR_S01Logic {
	
	@Autowired
	private MWorkerDao mWorkerDao;

	@Override
	public MWorker getMWorker() {
		return mWorkerDao.getMWorker();
	}

	@Override
	public void save(MWorker mWorker) {
		mWorkerDao.update(mWorker);
	}

}
