package th.co.nttdata.tki.blogic.mst.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MMachine;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.mst.MCH_S01Logic;
import th.co.nttdata.tki.dao.MMachineDao;

@Service
public class MCH_S01LogicImpl extends AbstractBaseLogic implements MCH_S01Logic {

	@Autowired
	MMachineDao mMachineDao;

	@Override
	public MMachine getMachineList(MMachine MMchine) {
		return mMachineDao.queryList(MMchine);
	}

	@Override
	public void delete(MMachine MMchine) {
		mMachineDao.delete(MMchine);
	}

	@Override
	public List<MMachine> getPartMachineList(MMachine MMchine) {
		return mMachineDao.getPartMachineList(MMchine);
	}

	@Override
	public List<MMachine> getCopyPartMachineList(MMachine MMchine) {
		return mMachineDao.getCopyPartMachineList(MMchine);
	}

	@Override
	public List<MMachine> getWipList() {
		return mMachineDao.getWipList();
	}

	@Override
	public void saveCopyPartMappingList(MMachine MMchine) {
		mMachineDao.DeleteMachinePartmappingbyMachineId(MMchine);
		mMachineDao.insertCopyMachinePartmapping(MMchine);

	}

	@Override
	public List<MMachine> getCustomerList(MMachine MMchine) {
		return mMachineDao.getCustomerList(MMchine);
	}

	@Override
	public List<MMachine> getPartNoList(MMachine MMchine) {
		return mMachineDao.getPartNoList(MMchine);
	}

	@Override
	public void savePartMappingList(List<MMachine> mMchineParam) {
		MMachine mMachine = new MMachine();
		if (mMchineParam.size() > 0) {
			mMachineDao.deleteMachinePart(mMchineParam.get(0));
		}
		List<MMachine> insertMchine = new ArrayList<MMachine>();
		for (MMachine tmp : mMchineParam) {
			if (!tmp.isDelete()) {
				insertMchine.add(tmp);
			}
		}
		if (insertMchine.size() > 0) {
			mMachine.setMachineList(insertMchine);
			mMachineDao.insertMachinePart(mMachine);
		}
	}
}
