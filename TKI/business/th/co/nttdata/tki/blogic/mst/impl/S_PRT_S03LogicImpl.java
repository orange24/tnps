package th.co.nttdata.tki.blogic.mst.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.PartRoutingMaster;
import th.co.nttdata.tki.bean.filter.PartRoutingMasterFilter;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.mst.S_PRT_S03Logic;
import th.co.nttdata.tki.dao.MCustomerDao;
import th.co.nttdata.tki.dao.PartRoutingMasterDao;

@Service
public class S_PRT_S03LogicImpl extends AbstractBaseLogic implements
		S_PRT_S03Logic {
	@Autowired
	private MCustomerDao mCustommerDao;
	@Autowired
	private PartRoutingMasterDao partRoutingDao;
	private Integer partId;

	@Override
	public MCustomer getAllCustomer() {
		MCustomer mCustomer = new MCustomer();
		mCustomer.setCustomerList(mCustommerDao.getCustomerList());
		return mCustomer;
	}

	@Override
	public PartRoutingMaster searchFgPartWipList(PartRoutingMaster prMaster) {
		List<PartRoutingMaster> prMasterList = partRoutingDao
				.searchFgPartWipList(prMaster);

		// Set list for keep part routing data.
		List<PartRoutingMaster> prMasterListDetail = new ArrayList<PartRoutingMaster>();

		int processsize = prMasterList.size();

		String tmpPart = "";
		PartRoutingMaster prRoutingMaster = null;
		List<String> processList = null;
		List<String> wipList = null;
		List<String> remarkList = null;

		for (int i = 0; i < processsize; i++) {
			if (i == 0) {
				tmpPart = prMasterList
						.get(0)
						.getCustomerCode()
						.concat(prMasterList.get(0).getFgNo())
						.concat(String.valueOf(prMasterList.get(0).getPartId()));
				prRoutingMaster = new PartRoutingMaster();
				prRoutingMaster = prMasterList.get(0);
				processList = new ArrayList<String>();
				remarkList = new ArrayList<String>();
				wipList = new ArrayList<String>();
				if (null != prMasterList.get(0).getProcess()) {
					processList.add(prMasterList.get(0).getProcess());
				}
				if (null != prMasterList.get(0).getRemark()) {
					remarkList.add(prMasterList.get(0).getRemark());
				}
				if (null != prMasterList.get(0).getWipCalc()) {
					wipList.add(prMasterList.get(0).getWipCalc());
				}
			} else {
				String tmpNewPart = prMasterList
						.get(i)
						.getCustomerCode()
						.concat(prMasterList.get(i).getFgNo())
						.concat(String.valueOf(prMasterList.get(i).getPartId()));
				if (!tmpPart.equals(tmpNewPart)) {
					tmpPart = tmpNewPart;
					prRoutingMaster.setProcessList(processList);
					prRoutingMaster.setRemarkList(remarkList);
					prRoutingMaster.setWipCalcList(wipList);
					prMasterListDetail.add(prRoutingMaster);
					// new part
					prRoutingMaster = new PartRoutingMaster();
					prRoutingMaster = prMasterList.get(i);
					processList = new ArrayList<String>();
					remarkList = new ArrayList<String>();
					wipList = new ArrayList<String>();
					if (null != prMasterList.get(i).getProcess()) {
						processList.add(prMasterList.get(i).getProcess());
					}
					if (null != prMasterList.get(i).getRemark()) {
						remarkList.add(prMasterList.get(i).getRemark());
					}
					if (null != prMasterList.get(i).getWipCalc()) {
						wipList.add(prMasterList.get(i).getWipCalc());
					}
				} else {
					// if process not null set process to process list.
					if (null != prMasterList.get(i).getProcess()) {
						processList.add(prMasterList.get(i).getProcess());
					}
					if (null != prMasterList.get(i).getRemark()) {
						remarkList.add(prMasterList.get(i).getRemark());
					}
					// if WIP calculate not null set WIP calculate to WIP
					// calculate list.
					if (null != prMasterList.get(i).getWipCalc()) {
						wipList.add(prMasterList.get(i).getWipCalc());
					}
				}
			}

			// check last row
			if (i == processsize - 1) {
				prRoutingMaster.setProcessList(processList);
				prRoutingMaster.setWipCalcList(wipList);
				prRoutingMaster.setRemarkList(remarkList);
				prMasterListDetail.add(prRoutingMaster);
			}
		}
		prMaster.setPartRoutinglist(prMasterListDetail);
		return prMaster;
	}

	@Override
	public PartRoutingMaster searchProcessList(PartRoutingMaster prMaster) {
		prMaster.setPartRoutinglist(partRoutingDao.searchProcessList(prMaster));
		partId = prMaster.getPartId();
		return prMaster;
	}

	@Override
	public PartRoutingMaster selectProcess() {
		PartRoutingMaster prmaster = new PartRoutingMaster();
		prmaster.setPartRoutinglist(partRoutingDao.selectProcess());
		return prmaster;
	}

	@Override
	public PartRoutingMaster selectListForCopyPartRouting(
			PartRoutingMaster prMaster) {
		prMaster.setPartRoutinglist(partRoutingDao
				.selectListForCopyPartRouting(prMaster));
		return prMaster;
	}

	@Override
	public void insertPartDestination(PartRoutingMaster prMaster) {
		partRoutingDao.deletePartDestination(prMaster);
		partRoutingDao.insertPartDestination(prMaster);
	}

	@Override
	public void deletePartDestination(PartRoutingMaster prMaster) {
		partRoutingDao.deletePartDestination(prMaster);
	}

	@Override
	public List<PartRoutingMasterFilter> selectExportList(
			PartRoutingMasterFilter partRoutingMasterFilter) {
		partRoutingMasterFilter.setPartRoutinglist(partRoutingDao
				.selectExportList(partRoutingMasterFilter));
		return partRoutingMasterFilter.getPartRoutinglist();

	}

	@Override
	public void saveProcessList(List<PartRoutingMaster> prMasterList) {
		List<PartRoutingMaster> insertupdateprMaster = new ArrayList<PartRoutingMaster>();
		for (PartRoutingMaster prmaster : prMasterList) {
			if (prmaster.isInsert()) {
				prmaster.setPartId(partId);
				insertupdateprMaster.add(prmaster);
			} else if (prmaster.isUpdate()) {
				insertupdateprMaster.add(prmaster);
			} else if (!prmaster.isDelete()) {
				insertupdateprMaster.add(prmaster);
			}
		}
		// prepared for delete by part
		PartRoutingMaster deleteprmaster = new PartRoutingMaster();
		deleteprmaster.setPartId(partId);
		partRoutingDao.deleteProcessList(deleteprmaster);

		// prepared for insert
		PartRoutingMaster insertprmaster = new PartRoutingMaster();
		insertprmaster.setPartRoutinglist(insertupdateprMaster);
		partRoutingDao.insertProcessList(insertprmaster);
	}
}
