package th.co.nttdata.tki.blogic.mst.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.FgMaster;
import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.mst.S_FGM_S02Logic;
import th.co.nttdata.tki.dao.MCustomerDao;
import th.co.nttdata.tki.dao.MFgMasterDao;

@Service
public class S_FGM_S02LogicImpl extends AbstractBaseLogic implements
		S_FGM_S02Logic {

	@Autowired
	private MCustomerDao mCustommerDao;
	@Autowired
	private MFgMasterDao mFgMasterDao;

	@Override
	public MCustomer getAllCustomer() {
		MCustomer mCustomer = new MCustomer();
		mCustomer.setCustomerList(mCustommerDao.getCustomerList());
		return mCustomer;
	}

	@Override
	public FgMaster searchCustomerFgMappingByCustomerId(FgMaster fgmaster) {
		fgmaster = mFgMasterDao.searchCustomerFgMappingByCustomerId(fgmaster);
		return fgmaster;
	}

	@Override
	public void SaveCustomerFgMaster(List<FgMaster> cutomerFgMappingList) {
		FgMaster fgmaster = new FgMaster();
		fgmaster.setFgMasterList(cutomerFgMappingList);
		mFgMasterDao.deleteCustomerFgMaster(fgmaster);
		List<FgMaster> insertList = new ArrayList<FgMaster>();
		for (FgMaster tmp : fgmaster.getFgMasterList()) {
			if (!tmp.isDelete()) {
				insertList.add(tmp);
			}
		}
		fgmaster.setFgMasterList(insertList);
		mFgMasterDao.insertCustomerFgMaster(fgmaster);
	}

	@Override
	public List<FgMaster> getFgList() {
		return mFgMasterDao.getFgList();
	}
}
