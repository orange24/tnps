package th.co.nttdata.tki.blogic.mst.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.FgMaster;
import th.co.nttdata.tki.bean.filter.FgMasterFilter;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.mst.S_FGM_S01Logic;
import th.co.nttdata.tki.dao.MFgMasterDao;

@Service
public class S_FGM_S01LogicImpl extends AbstractBaseLogic implements
		S_FGM_S01Logic {

	@Autowired
	private MFgMasterDao mFgMasterDao;

	@Override
	public List<FgMaster> getFgMastertSearchList(FgMaster fgMaster) {
		return mFgMasterDao.getFgMastertSearchList(fgMaster);
	}

	@Override
	public List<FgMaster> getUomList() {
		return mFgMasterDao.getUomList();
	}

	@Override
	public List<FgMaster> getClassifyBusiness() {
		return mFgMasterDao.getClassifyBusiness();
	}

	@Override
	public List<FgMaster> getPlace() {
		return mFgMasterDao.getPlace();
	}

	@Override
	public List<FgMaster> getSubbusiness() {
		return mFgMasterDao.getSubbusiness();
	}

	@Override
	public void saveList(List<FgMaster> fgMasterList) {
		List<FgMaster> insertFgmaster = new ArrayList<FgMaster>();
		List<FgMaster> updateFgmaster = new ArrayList<FgMaster>();
		List<FgMaster> deleteFgmaster = new ArrayList<FgMaster>();

		for (FgMaster fgmaster : fgMasterList) {
			if (fgmaster.isInsert()) {
				insertFgmaster.add(fgmaster);
			} else if (fgmaster.isUpdate()) {
				updateFgmaster.add(fgmaster);
			} else if (fgmaster.isDelete()) {
				deleteFgmaster.add(fgmaster);
			}
		}
		if (0 < insertFgmaster.size()) {
			FgMaster fgInsert = new FgMaster();
			fgInsert.setFgMasterList(insertFgmaster);
			mFgMasterDao.insertFgMaster(fgInsert);
		}
		if (0 < updateFgmaster.size()) {
			FgMaster fgUpdate = new FgMaster();
			fgUpdate.setFgMasterList(updateFgmaster);
			mFgMasterDao.updateFgMaster(fgUpdate);
		}
		if (0 < deleteFgmaster.size()) {
			FgMaster fgDelete = new FgMaster();
			fgDelete.setFgMasterList(deleteFgmaster);
			mFgMasterDao.deleteFgMaster(fgDelete);
		}
	}

	@Override
	public List<FgMasterFilter> search(FgMasterFilter mFgmaster) {
		FgMasterFilter fgMaster = new FgMasterFilter();
		fgMaster.setFgMasterFilterList(mFgMasterDao.getFgMasterList(mFgmaster));
		return fgMaster.getFgMasterFilterList();
	}

	@Override
	public List<FgMaster> getCurrencyList() {
		return mFgMasterDao.getCurrencyList();
	}
}
