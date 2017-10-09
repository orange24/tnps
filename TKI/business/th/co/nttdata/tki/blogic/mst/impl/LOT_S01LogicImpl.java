package th.co.nttdata.tki.blogic.mst.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.mst.LOT_S01Logic;
import th.co.nttdata.tki.dao.MWorkOrderDao;

@Service
public class LOT_S01LogicImpl extends AbstractBaseLogic implements LOT_S01Logic {

	@Autowired
	MWorkOrderDao mWorkOrderDao;
	
	@Override
	public MWorkOrder search(MWorkOrder mWorkOrder) {
		return mWorkOrderDao.search(mWorkOrder);
	}

	@Override
	public void delete(MWorkOrder mWorkOrder) {
		mWorkOrderDao.delete(mWorkOrder);
	}

}
