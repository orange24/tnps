package th.co.nttdata.tki.blogic.mst.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.mst.LOT_S02Logic;
import th.co.nttdata.tki.dao.MWorkOrderDao;

@Service
public class LOT_S02LogicImpl extends AbstractBaseLogic implements LOT_S02Logic {

	@Autowired
	MWorkOrderDao mWorkOrderDao;
	
	@Override
	public MWorkOrder searchTPics(MWorkOrder mWorkOrder) {
		mWorkOrderDao.searchTPics(mWorkOrder);
		
		return mWorkOrder;
	}

	@Override
	public void syncLot(MWorkOrder mWorkOrder) {
		mWorkOrderDao.syncLot(mWorkOrder);
	}

}
