package th.co.nttdata.tki.blogic.mst.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.blogic.mst.RES_S03Logic;
import th.co.nttdata.tki.dao.MReasonDao;

@Service
public class RES_S03LogicImpl implements RES_S03Logic{
	
	@Autowired
	MReasonDao mReasonDao;

	@Override
	public MReason searchResson(MReason mReason) {
		return mReasonDao.searchReasonUse(mReason);
	}

	@Override
	public void saveReasonUse(MReason mReason) {
		mReasonDao.deleteReasonWipByType(mReason);
		if(mReason.getReasonList() != null){
			mReasonDao.insertReasonWip(mReason);
		}
	}

}
