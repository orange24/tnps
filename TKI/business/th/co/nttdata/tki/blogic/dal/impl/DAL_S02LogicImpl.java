package th.co.nttdata.tki.blogic.dal.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MMachine;
import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.bean.TDailyMC;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.dal.DAL_S02Logic;
import th.co.nttdata.tki.dao.TDailyMCDao;

@Service
public class DAL_S02LogicImpl extends AbstractBaseLogic implements DAL_S02Logic {

	@Autowired
	private TDailyMCDao dailyMCDao;
	
	@Override
	public TDailyMC check( TDailyMC TDailyMC ) {

		// <!-- Check: if 'lotNo' is existing in system. -->
		MWorkOrder workOrder = new MWorkOrder();
		if(TDailyMC.getLotNo() != null)
		for (int i=0;i<TDailyMC.getLotNo().length;i++) {
			workOrder.setLotNo(TDailyMC.getLotNo()[i]);
			if (!dailyMCDao.queryWorkOrder(workOrder)) {
				TDailyMC.getErrors().add(new Message("err.cmm.004", new String[]{"Lot No "+workOrder.getLotNo()}));
			}
		}
		return TDailyMC;
	}

	@Override
	public void delete( TDailyMC TDailyMC ) {
		dailyMCDao.delete(TDailyMC);

		// <!-- Processing Successfully. -->
		TDailyMC.setDailyMCId(null);
		TDailyMC.setWip(null);
		TDailyMC.setMachineNo(null);
		TDailyMC.setReportDate(null);
		TDailyMC.setReportType(null);
		TDailyMC.setDetails(null);
		TDailyMC.getInfos().add( new Message("inf.cmm.003", new String[] {}) );
	}

	@Override
	public TDailyMC edit( TDailyMC TDailyMC ) {
		return dailyMCDao.query(TDailyMC);
	}

	@Override
	public void save( TDailyMC TDailyMC ) {
		// <!-- Check: if 'dailyMCId' is existing. -->
		if( TDailyMC.getDailyMCId() == null )
			dailyMCDao.insert(TDailyMC);
		else {
			dailyMCDao.update(TDailyMC);
		}

		TDailyMC.getInfos().add( new Message("inf.cmm.002", new String[] {}) );
	}
	
	@Override
	public MWorkOrder searchLotno( String lotNo, String wip,Integer reportType ) {
//		return dailyMCDao.querysearchLotno(lotNo,wip,reportType);
		return dailyMCDao.getDCByLotno(lotNo,wip,reportType);
	}

	@Override
	public MWorkOrder getWorkOrderList(MWorkOrder mWorkOrder) {
		return dailyMCDao.queryWorkOrderList(mWorkOrder);
	}

	@Override
	public MMachine getMachine(String machineId) {
		return dailyMCDao.getMachine(machineId);
	}

	@Override
	public MWorkOrder getLotByMold(String moldId, String moldNo) {
		return dailyMCDao.getLotByMold(Integer.parseInt(moldId), moldNo);
	}
}