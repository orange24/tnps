package th.co.nttdata.tki.blogic.mst.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.blogic.mst.ITM_S03Logic;
import th.co.nttdata.tki.dao.MPartDao;

@Service
public class ITM_S03LogicImpl implements ITM_S03Logic {
	@Autowired 
	private MPartDao mPartDao;
	
	@Override
	public MPart query(MPart part) {
		MPart partDB = mPartDao.getPart(part);
		partDB.setWipList(mPartDao.getWipOfPart(part));
		return partDB;
	}

	@Override
	public void update(MPart part) {
		mPartDao.update(part);
		mPartDao.updatePartWip(part);
	}
}
