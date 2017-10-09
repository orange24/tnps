package th.co.nttdata.tki.blogic.mst.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MMoldDetail;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.mst.MLD_S01Logic;
import th.co.nttdata.tki.dao.MMoldDao;
import th.co.nttdata.tki.dao.MMoldDetailDao;

@Service
public class MLD_S01LogicImpl extends AbstractBaseLogic implements MLD_S01Logic {

	@Autowired
	MMoldDetailDao mDetailDao;
	@Autowired
	MMoldDao mMoldDao;
	@Override
	public MMoldDetail search(MMoldDetail mdDetail) {
		return mDetailDao.search(mdDetail);
	}

	@Override
	public void delete(MMoldDetail mdDetail) {
		mDetailDao.delete(mdDetail);
		int countMoldNo = mDetailDao.checkMoldName(mdDetail);
		if (countMoldNo <= 0) {
			mDetailDao.deleteMoldPart(mdDetail);
			mMoldDao.delete(mdDetail);
		}
	}
	
	@Override
	public MMoldDetail exportMLD_R01( MMoldDetail mdDetail ) {
		return mDetailDao.selectMLD_R01(mdDetail);
	}
	
}
