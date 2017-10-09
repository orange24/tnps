package th.co.nttdata.tki.blogic.mst.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.mst.PRC_S02Logic;
import th.co.nttdata.tki.dao.MWipDao;

@Service
public class PRC_S02LogicImpl extends AbstractBaseLogic implements PRC_S02Logic  {
	
	@Autowired
	private MWipDao mWipDao;

	@Override
	public MWip search(MWip mWip) {
		mWip = mWipDao.getWipTpicList(mWip);
		return mWip;
	}

	@Override
	public void save(MWip mWip) {
		mWipDao.insert(mWip);
	}
}
