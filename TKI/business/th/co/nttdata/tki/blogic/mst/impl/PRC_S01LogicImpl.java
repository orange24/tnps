package th.co.nttdata.tki.blogic.mst.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.bean.filter.MWipFilter;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.mst.PRC_S01Logic;
import th.co.nttdata.tki.dao.MWipDao;

@Service
public class PRC_S01LogicImpl extends AbstractBaseLogic implements PRC_S01Logic {

	@Autowired
	private MWipDao mWipDao;

	@Override
	public MWip search(MWip mWip) {
		mWip = mWipDao.getWipList(mWip);
		return mWip;
	}

	@Override
	public List<MWip> searchList(MWip mWip) {

		List<MWip> mWipList = mWipDao.getWipSearchList(mWip);
		return mWipList;
	}

	@Override
	public void delete(MWip mWip) {
		mWipDao.delete(mWip);
	}

	@Override
	public void save(MWip mWip) {
		mWipDao.save(mWip);
	}

	@Override
	public void saveList(List<LinkedHashMap> mWips) throws Exception {
		List<MWip> insertMwip = new ArrayList<MWip>();
		List<MWip> updateMwip = new ArrayList<MWip>();
		List<MWip> deleteMwip = new ArrayList<MWip>();

		MWip mWip = null;
		for (LinkedHashMap<String, Object> hashList : mWips) {
			mWip = new MWip();
			BeanUtils.populate(mWip, hashList);
			if (mWip.isInsert()) {
				insertMwip.add(mWip);
			} else if (mWip.isUpdate()) {
				updateMwip.add(mWip);
			} else if (mWip.isDelete()) {
				deleteMwip.add(mWip);
			}
		}
		if (0 < deleteMwip.size()) {

			MWip mwipSave = new MWip();
			mwipSave.setWipList(deleteMwip);
			mWipDao.deleteMWip(mwipSave);

		}
		if (0 < insertMwip.size()) {
			MWip mwipSave = new MWip();
			mwipSave.setWipList(insertMwip);
			mWipDao.insertMWip(mwipSave);
		}
		if (0 < updateMwip.size()) {
			MWip mwipSave = new MWip();
			mwipSave.setWipList(updateMwip);
			mWipDao.updateMWip(mwipSave);
		}

	}

	@Override
	public MWip check(MWip MWip) {
		// <!-- Check: if 'wip' have related. -->
		if (mWipDao.checkReason(MWip) || mWipDao.checkPart(MWip)
				|| mWipDao.checkMachine(MWip)) {
			MWip.getErrors().add(new Message("err.pm.001", new String[] {}));
		}
		return MWip;
	}

	@Override
	public MWip selectWipListByWip(MWip mWip) {
		return mWipDao.selectWipListByWip(mWip);

	}

	@Override
	public List<MWip> getWipTypeName() {

		return mWipDao.getWipTypeName();
	}

	@Override
	public MWip selectWipListByWipFilter(MWipFilter mwipFilter) {
		return mWipDao.selectWipListByWipFilter(mwipFilter);
	}

}
