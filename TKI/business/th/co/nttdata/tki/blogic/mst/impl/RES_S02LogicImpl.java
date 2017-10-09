package th.co.nttdata.tki.blogic.mst.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.blogic.mst.RES_S02Logic;
import th.co.nttdata.tki.dao.MReasonDao;

@Service
public class RES_S02LogicImpl implements RES_S02Logic{
	@Autowired
	MReasonDao mReasonDao;

	@Override
	public List<MReason> getParentReason() {
		return mReasonDao.getParentReason();
	}
	
	public boolean checkDupReasonCode(MReason mReason){		
		return (mReasonDao.checkDupReasonCode(mReason).size())>0?true:false;
	}

	@Override
	public MReason save(MReason mReason) {
		boolean isDup = checkDupReasonCode(mReason);
		if(isDup){
			mReason.getErrors().add(new Message("err.cmm.011",new String[]{"Reason Code "}));
		}else{
			mReasonDao.insertReason(mReason);	
		}
		return mReason;
	}

	@Override
	public MReason edit(MReason mReason) {
		boolean isDup = checkDupReasonCode(mReason);
		if(isDup){
			mReason.getErrors().add(new Message("err.cmm.011",new String[]{"Reason Code "}));
		}else{
			mReasonDao.updateReason(mReason);	
		}
		return mReason;
	}

	@Override
	public void delete(MReason mReason) {
		mReasonDao.deleteReasonWip(mReason);
		mReasonDao.deleteReason(mReason);
	}

	@Override
	public MReason getReasonById(MReason mReason) {
		return mReasonDao.getReason(mReason);
	}

}
