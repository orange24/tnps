package th.co.nttdata.tki.blogic.mst.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.MPartWip;
import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.blogic.mst.ITM_S02Logic;
import th.co.nttdata.tki.dao.MCustomerDao;
import th.co.nttdata.tki.dao.MPartDao;
import th.co.nttdata.tki.dao.MWipDao;

@Service
public class ITM_S02LogicImpl implements ITM_S02Logic {
	@Autowired 
	private MPartDao mPartDao;
	@Autowired
	private MCustomerDao mCustomerDao;
	@Autowired
	private MWipDao mWipDao;
	
	@Override
	public MPart query( MPart part ) {
		return mPartDao.getPartNotSync(part);
	}

	@Override
	public MPart save( MPart part ) {
		if (part.getPartList().size() > 0) {
			for (MPart partTpic : part.getPartList()) {	
				if (partTpic.getChoose()) {
					// Part
					partTpic.setPartId(mPartDao.save(partTpic));
					
					// WIP
					mergeWIP(partTpic);
					
					// Part-WIP
					mPartDao.savePartWip(partTpic);
					
					// Customer
					MCustomer customer = new MCustomer(partTpic.getCustomerCode(), partTpic.getCustomerName());
					partTpic.setCustomerId(mCustomerDao.merge(customer));
					
					// FG
					partTpic.setFgId(mPartDao.saveFg(partTpic));
					mPartDao.saveFgPart(partTpic);
					mPartDao.saveFgCustomer(partTpic);
				}
			}
		}
		return part;
	}
	
	private void mergeWIP(MPart partTpic) {
		List<MWip> wipList = new ArrayList<MWip>();
		for (MPartWip wip : partTpic.getWipList()) {
			wipList.add(wip.getWip());
		}
		MWip wip = new MWip();
		wip.setWipList(wipList);
		mWipDao.mergeWip(wip);
	}
	
	@Override
	public List<MCustomer> getTpicCustomerList() {
		return mCustomerDao.getTpicCustomerList();
	}
}
