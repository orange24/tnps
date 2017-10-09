package th.co.nttdata.tki.blogic.mst.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MFgPart;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.mst.PRT_S02Logic;
import th.co.nttdata.tki.dao.MCustomerDao;
import th.co.nttdata.tki.dao.MFgPartMappingDao;

@Service
public class PRT_S02LogicImpl extends AbstractBaseLogic implements PRT_S02Logic {

	@Autowired
	private MCustomerDao mCustommerDao;
	@Autowired
	private MFgPartMappingDao mFgPartDao;

	@Override
	public MCustomer getAllCustomer() {
		MCustomer mCustomer = new MCustomer();
		mCustomer.setCustomerList(mCustommerDao.getCustomerList());
		return mCustomer;
	}

	@Override
	public MFgPart searchFgPartMappingByCustomerId(MFgPart mFgPart) {
		return mFgPartDao.searchFgPartMappingByCustomerId(mFgPart);
	}

	@Override
	public void saveList(List<MFgPart> mFgpart) {
		List<MFgPart> mfgpartList = new ArrayList<MFgPart>();
		for (MFgPart mfg : mFgpart) {
			mfgpartList.add(mfg);
		}

		if (0 < mfgpartList.size()) {
			MFgPart mfgSave = new MFgPart();
			mfgSave.setCustomerList(mfgpartList);
			/* get data from search criteria */
			mFgPartDao.updateMFgpart(mFgpart.get(0));
			for (MFgPart tmp : mfgSave.getCustomerList()) {
				if (tmp.isDelete()) {
					tmp.setPartId(null);
				}
				mFgPartDao.insertMFgpart(tmp);
			}
		}
	}

	@Override
	public List<MFgPart> getFgNoNameListByCustomerId(MFgPart mFgPart) {
		return mFgPartDao.getFgNoNameListByCustomerId(mFgPart);
	}

	@Override
	public List<MFgPart> selectPartListByFgId(MFgPart mFgPart) {
		return mFgPartDao.selectPartListByFgId(mFgPart);
	}
}
