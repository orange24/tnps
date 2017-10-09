package th.co.nttdata.tki.blogic.mst.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.blogic.mst.RES_S01Logic;
import th.co.nttdata.tki.dao.MReasonDao;

@Service
public class RES_S01LogicImpl implements RES_S01Logic{
	
	@Autowired
	MReasonDao mReasonDao;

	@Override
	public MReason searchResson(MReason mReason) {	
		if(mReason.getWip()==null){
			mReason = mReasonDao.searchReasonOnly(mReason);
		}else{
			mReason = mReasonDao.searchReason(mReason);
		}
		return mReason;
	}

	@Override
	public void deleteReason(MReason mReason) {
		mReasonDao.deleteReasonWip(mReason);
		mReasonDao.deleteReason(mReason);
	}

}
