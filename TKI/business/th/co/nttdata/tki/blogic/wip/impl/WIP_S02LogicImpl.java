package th.co.nttdata.tki.blogic.wip.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.TWip;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.wip.WIP_S02Logic;
import th.co.nttdata.tki.dao.MCustomerDao;
import th.co.nttdata.tki.dao.MPartDao;
import th.co.nttdata.tki.dao.TWipStockDao;

@Service
public class WIP_S02LogicImpl extends AbstractBaseLogic implements WIP_S02Logic {

	@Autowired
	private TWipStockDao wipStockDao;
	@Autowired
	private MPartDao mPartDao;
	@Autowired
	private MCustomerDao mCustomerDao;

	@Override
	public TWip search( TWip TWip ) {
		Calendar strCal = new GregorianCalendar(TWip.getYear(), TWip.getMonth(), 1);
		Calendar endCal = new GregorianCalendar(TWip.getYear(), TWip.getMonth()+1, 1);
		endCal.add(Calendar.DAY_OF_YEAR, -1);

		// <!-- Providing the report date. -->
		TWip.setReportDateFr(strCal.getTime());
		TWip.setReportDateTo(endCal.getTime());
		TWip.setStrDay(strCal.get(Calendar.DAY_OF_MONTH));
		TWip.setEndDay(endCal.get(Calendar.DAY_OF_MONTH));

		return wipStockDao.getWipStockList(TWip);
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