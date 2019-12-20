package th.co.nttdata.tki.blogic.wip.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.TWipCheckStock;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.wip.WIP_S06Logic;
import th.co.nttdata.tki.dao.MCustomerDao;
import th.co.nttdata.tki.dao.MPartDao;
import th.co.nttdata.tki.dao.TWipCheckStockDao;

@Service
public class WIP_S06LogicImpl extends AbstractBaseLogic implements WIP_S06Logic {

	@Autowired
	private TWipCheckStockDao wipCheckStockDao;
	@Autowired
	private MPartDao mPartDao;
	@Autowired
	private MCustomerDao mCustomerDao;
      
        @Override
        public TWipCheckStock search( TWipCheckStock TWipCheckStock ) {
            return wipCheckStockDao.getWipFg(TWipCheckStock);
        }
       
	@Override
	public List<MPart> getWIP(Integer customerId, Integer partId) {
		MPart MPart = new MPart();
		if(0 < customerId){
			MPart.setCustomerId(customerId);
		}
		if(0 < partId){
			MPart.setPartId(partId);
		}
		return mPartDao.getPartInWip(MPart);
	}

	@Override
	public MCustomer getMCustomer(Integer customerId) {
		return mCustomerDao.getMCustomer(customerId);
	}

	@Override
	public MPart getMPart(Integer partId) {
		return mPartDao.getMPart(partId);
	}
}