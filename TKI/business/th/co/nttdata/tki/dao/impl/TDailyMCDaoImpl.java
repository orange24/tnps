package th.co.nttdata.tki.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MMachine;
import th.co.nttdata.tki.bean.MMold;
import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.bean.TDailyMC;
import th.co.nttdata.tki.bean.TDailyMCDetail;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TDailyMCDao;
import th.co.nttdata.tki.dao.TDailyMCDetailDao;
import th.co.nttdata.tki.dao.TDailyMCNGReasonDao;
import th.co.nttdata.tki.dao.TDailyMCStopMCDao;

@Repository
@SuppressWarnings("unchecked")
public class TDailyMCDaoImpl extends AbstractBaseDao implements TDailyMCDao {

	@Autowired
	private TDailyMCDetailDao dailyMCDetailDao;
	@Autowired
	private TDailyMCNGReasonDao dailyMCNGReasonDao;
	@Autowired
	private TDailyMCStopMCDao dailyMCStopMCDao;

	@Override
	public void delete( TDailyMC TDailyMC ) {
		// <!-- Delete from 't_dailymc_stopmc' -->
		dailyMCStopMCDao.delete(TDailyMC);

		// <!-- Delete from 't_dailymc_ngreason' -->
		dailyMCNGReasonDao.delete(TDailyMC);

		// <!-- Delete from 't_dailymc_detail' -->
		dailyMCDetailDao.delete(TDailyMC);

		// <!-- Delete from 't_dailymc' -->
		delete("t_dailymc.delete", TDailyMC);
	}

	@Override
	public void insert( TDailyMC TDailyMC ) {
		// <!-- Insert to 't_dailymc' -->
		Integer dailyMCId = (Integer) insert("t_dailymc.insert", TDailyMC);
		TDailyMC.setDailyMCId(dailyMCId);

		// <!-- Insert to 't_dailymc_detail' -->
		dailyMCDetailDao.insert(TDailyMC);

		// <!-- Insert to 't_dailymc_ngreason' -->
		dailyMCNGReasonDao.insert(TDailyMC);

		// <!-- Insert to 't_dailymc_stopmc' -->
		dailyMCStopMCDao.insert(TDailyMC);
	}

	@Override
	public TDailyMC update( TDailyMC TDailyMC ) {
		// <!-- Update to 't_dailymc_stopmc' -->
		dailyMCStopMCDao.delete(TDailyMC);
		dailyMCStopMCDao.insert(TDailyMC);

		// <!-- Update to 't_dailymc_ngreason' -->
		dailyMCNGReasonDao.delete(TDailyMC);
		dailyMCNGReasonDao.insert(TDailyMC);

		// <!-- Update to 't_dailymc_detail' -->
		dailyMCDetailDao.delete(TDailyMC);
		dailyMCDetailDao.insert(TDailyMC);

		// <!-- Update to 't_dailymc' -->
		update("t_dailymc.update", TDailyMC);
		
		return TDailyMC;
	}

	@Override
	public TDailyMC query( TDailyMC TDailyMC ) {
		// <!-- Select from 't_dailymc' -->
		queryLotNo(TDailyMC = (TDailyMC) queryForObject("t_dailymc.query", TDailyMC));

		// <!-- Select from 't_dailymc_detail' -->
		dailyMCDetailDao.query(TDailyMC);

		// <!-- Select from 't_dailymc_ngreason' -->
		dailyMCNGReasonDao.query(TDailyMC);

		// <!-- Select from 't_dailymc_stopmc' -->
		dailyMCStopMCDao.query(TDailyMC);
		
		return TDailyMC;
	}

	@Override
	public TDailyMC queryList( TDailyMC TDailyMC ) {
		TDailyMC.setDailyMCList( (List<TDailyMC>) queryForList("t_dailymc.query", TDailyMC, getSkipResult(TDailyMC), TDailyMC.getPageCount()) );
		calPageTotal("t_dailymc.count", TDailyMC);

		return TDailyMC;
	}

	@Override
	public TDailyMC queryLotNo( TDailyMC TDailyMC ) {
		List<String> lotNo = (List<String>) queryForList("t_dailymc.queryLotNo", TDailyMC);
		TDailyMC.setLotNo(lotNo.toArray( new String[lotNo.size()] ));
		return TDailyMC;
	}
	
	@Override
	public MWorkOrder querysearchLotno( String lotNo, String wip,Integer reportType ) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("lotNo", lotNo);
		param.put("wip", wip);
		param.put("reportType", reportType);
		return (MWorkOrder) queryForObject("t_dailymc.querysearchLotno", param);
	}
	
	@Override
	public MWorkOrder getDCByLotno( String lotNo, String wip,Integer reportType ) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("lotNo", lotNo);
		param.put("wip", wip);
		param.put("reportType", reportType);
		return (MWorkOrder) queryForObject("t_dailymc.getDCByLotno", param);
	}
	
	@Override
	public boolean queryWorkOrder( MWorkOrder MWorkOrder ) {
		return (Integer) queryForObject("t_dailymc.queryWorkOrder", MWorkOrder)> 0;
	}

	@Override
	public MWorkOrder queryWorkOrderList(MWorkOrder MWorkOrder) {
		MWorkOrder.setmWorkOrderLst((List<MWorkOrder>) queryForList("m_workorder.queryWorkOrder", MWorkOrder));
		return MWorkOrder;
	}

	@Override
	public TDailyMC selectDAL_R01( TDailyMC TDailyMC ) {

		TDailyMC.setDailyMCList( (List<TDailyMC>) queryForList("t_dailymc.selectDAL_R01", TDailyMC) );
		return TDailyMC;
	}
	
	@Override
	public Integer countDAL_R01() {
		
		return (Integer) queryForObject("t_dailymc.countDAL_R01");
	}

	@Override
	public TDailyMC selectMRDC_R09(TDailyMC TDailyMC) {
		TDailyMC.setDetails((List<TDailyMCDetail>)queryForList("t_dailymc.queryMRDC_R09",TDailyMC));
		return TDailyMC;
	}
	
	@Override
	public Integer countMRDC_R09() {
		
		return (Integer) queryForObject("t_dailymc.countMRDC_R09");
	}

	@Override
	public MMachine getMachine(String machineId) {
		return (MMachine) queryForObject("t_dailymc.getMachine", machineId);
	}

	@Override
	public MWorkOrder getLotByMold(int moldId, String moldNo) {
		MMold m = new MMold();
		m.setMoldId(moldId);
		m.setMoldNo(moldNo);
				
		return (MWorkOrder) queryForObject("t_dailymc.queryLotByMold", m);
	}

	
}