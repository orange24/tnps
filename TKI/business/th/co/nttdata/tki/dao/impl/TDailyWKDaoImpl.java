package th.co.nttdata.tki.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.bean.TDCPlan;
import th.co.nttdata.tki.bean.TDailyWK;
import th.co.nttdata.tki.bean.TDailyWKDetail;
import th.co.nttdata.tki.bean.TDailyWKNGReason;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TDailyWKDao;
import th.co.nttdata.tki.dao.TDailyWKDetailDao;
import th.co.nttdata.tki.dao.TDailyWKNGReasonDao;

@Repository
@SuppressWarnings("unchecked")
public class TDailyWKDaoImpl extends AbstractBaseDao implements TDailyWKDao {

	@Autowired
	private TDailyWKDetailDao dailyWKDetailDao;
	@Autowired
	private TDailyWKNGReasonDao dailyWKNGReasonDao;

	@Override
	public void delete( TDailyWK TDailyWK ) {
		// <!-- Delete from 't_dailywk_detail' & 't_dailywk_ngreason' -->
		dailyWKDetailDao.delete(TDailyWK);

		// <!-- Delete from 't_dailywk' -->
		delete("t_dailywk.delete", TDailyWK);
	}

	@Override
	public void insert( TDailyWK TDailyWK ) {
		// <!-- Insert to 't_dailywk' -->
		Integer dailyWKId = (Integer) insert("t_dailywk.insert", TDailyWK);
		TDailyWK.setDailyWKId(dailyWKId);

		// <!-- Insert to 't_dailywk_detail' -->
		dailyWKDetailDao.insert(TDailyWK);

		// <!-- Insert to 't_dailywk_ngreason' -->
		dailyWKNGReasonDao.insert(TDailyWK);
	}

	@Override
	public TDailyWK update( TDailyWK TDailyWK ) {
		// <!-- Update to 't_dailywk_detail' & 't_dailywk_ngreason' -->
		dailyWKDetailDao.delete(TDailyWK);
		dailyWKDetailDao.insert(TDailyWK);
		dailyWKNGReasonDao.insert(TDailyWK);

		// <!-- Update to 't_dailywk' -->
		update("t_dailywk.update", TDailyWK);
		
		return TDailyWK;
	}

	@Override
	public TDailyWK query( TDailyWK TDailyWK ) {
		// <!-- Select from 't_dailywk' -->
		TDailyWK = (TDailyWK) queryForObject("t_dailywk.query", TDailyWK);

		// <!-- Select from 't_dailywk_detail' -->
		dailyWKDetailDao.query(TDailyWK);

		// <!-- Select from 't_dailywk_ngreason' -->
		dailyWKNGReasonDao.query(TDailyWK);
		
		return TDailyWK;
	}

	@Override
	public TDailyWK queryList( TDailyWK TDailyWK ) {
		TDailyWK.setDailyWKList( (List<TDailyWK>) queryForList("t_dailywk.query", TDailyWK, getSkipResult(TDailyWK), TDailyWK.getPageCount()) );
		calPageTotal("t_dailywk.count", TDailyWK);

		return TDailyWK;
	}

	@Override
	public List<MWorkOrder> queryWorkOrderNoList( MWorkOrder MWorkOrder ) {
		return (List<MWorkOrder>) queryForList("t_dailywk.queryWorkOrderNoList", MWorkOrder);
	}
	
	@Override
	public TDailyWK selectDAL_R03( TDailyWK TDailyWK ) {
		List<TDailyWKDetail> detailList = queryForList("t_dailywk.selectDAL_R03", TDailyWK);
		Map<String,TDailyWKNGReason> reasonMap = queryForMap("t_dailywk.selectDAL_R03NGReason", TDailyWK, "idRef");

		TDailyWK.setDailyWKDetailList(detailList);
		TDailyWK.setReasonMap(reasonMap);
		return TDailyWK;
	}
	
	@Override
	public Integer countDAL_R03() {
		
		return (Integer) queryForObject("t_dailywk.countDAL_R03");
	}

	@Override
	public TDailyWK selectMRDC_R10(TDailyWK tDailyWK) {
		tDailyWK.setDailyWKDetailList((List<TDailyWKDetail>)queryForList("t_dailywk.selectMRDC_R10",tDailyWK));
		tDailyWK.setReasonMap((Map<String,TDailyWKNGReason>)queryForMap("t_dailywk.selectMRDC_R10NGReason", tDailyWK, "idRef"));
		return tDailyWK;
	}
	
	@Override
	public Integer countMRDC_R10() {
		
		return (Integer) queryForObject("t_dailywk.countMRDC_R10");
	}
	
	@Override
	public TDailyWK selectMRDC_R12(TDailyWK tDailyWK) {
		List<TDailyWKDetail> dailyWKDetailList = (List<TDailyWKDetail>) queryForList("t_dailywk.selectMRDC_R12", tDailyWK);
		tDailyWK.setDailyWKDetailList(dailyWKDetailList);
		
		return tDailyWK;
	}
	
	@Override
	public Integer countMRDC_R12() {
		
		return (Integer) queryForObject("t_dailywk.countMRDC_R12");
	}
	
	@Override
	public TDailyWK selectMRDC_R13(TDailyWK tDailyWK) {
		List<TDailyWKDetail> dailyWKDetailList = (List<TDailyWKDetail>) queryForList("t_dailywk.selectMRDC_R13", tDailyWK);
		tDailyWK.setDailyWKDetailList(dailyWKDetailList);
		
		return tDailyWK;
	}
	
	@Override
	public Integer countMRDC_R13() {
		
		return (Integer) queryForObject("t_dailywk.countMRDC_R13");
	}
	
	@Override
	public TDailyWK selectMRDC_R22(TDailyWK tDailyWK) {
		List<TDailyWKDetail> dailyWKDetailList = (List<TDailyWKDetail>) queryForList("t_dailywk.selectMRDC_R22", tDailyWK);
		tDailyWK.setDailyWKDetailList(dailyWKDetailList);
		
		return tDailyWK;
	}
	
	@Override
	public Integer countMRDC_R22() {
		
		return (Integer) queryForObject("t_dailywk.countMRDC_R22");
	}
	
	@Override
	public TDailyWK selectMRDC_R02(TDailyWK tDailyWK) {
		List<TDailyWKDetail> dailyWKDetailList = (List<TDailyWKDetail>) queryForList("t_dailywk.selectMRDC_R02", tDailyWK);
		tDailyWK.setDailyWKDetailList(dailyWKDetailList);
		
		return tDailyWK;
	}
	
	@Override
	public Integer countMRDC_R02() {
		
		return (Integer) queryForObject("t_dailywk.countMRDC_R02");
	}
	

	@Override
	public TDailyWK getCategoryList(TDailyWK TDailyWK) {
		List<String> categoryList = (List<String>) queryForList("t_dailywk.selectCategory");
		TDailyWK.setCategoryList(categoryList);
		return TDailyWK;
	}

	@Override
	public Integer checkWorkOrder(TDCPlan object) {
		return (Integer) queryForObject("t_dailywk_detail.select_daily_actual", object);
	}
}