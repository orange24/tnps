package th.co.nttdata.tki.blogic.mst.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.blogic.mst.ITM_S01Logic;
import th.co.nttdata.tki.dao.MPartDao;

@Service
public class ITM_S01LogicImpl implements ITM_S01Logic {
	@Autowired 
	private MPartDao mPartDao;
	
	@Override
	public MPart query(MPart part) {
		return mPartDao.getPartMasterList(part);
	}	
	
	@Override
	public void delete(MPart part) {
		mPartDao.delete(part);
	}

	@Override
	public void deleteCustomer(MPart part) {		
		mPartDao.deleteFgCustomer(part);
//		mPartDao.deleteFgPart(part);
	}

}
