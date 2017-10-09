package th.co.nttdata.tki.dao.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.bean.TDailyMCWK;
import th.co.nttdata.tki.bean.TDailyMCWKDetail;
import th.co.nttdata.tki.bean.TDailyMCWKNGReason;
import th.co.nttdata.tki.bean.TDailyMCWKStopMC;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TDailyMCWKDao;

@Repository
@SuppressWarnings("unchecked")
public class TDailyMCWKDaoImpl extends AbstractBaseDao implements TDailyMCWKDao {

	@Override
	public void delete( TDailyMCWK TDailyMCWK ) {
		// <!-- Delete from 't_dailymcwk_stopmc' -->
		deleteStopMC(TDailyMCWK);

		// <!-- Delete from 't_dailymcwk_ngreason' -->
		deleteNGReason(TDailyMCWK);

		// <!-- Delete from 't_dailymcwk_detail' -->
		deleteDetail(TDailyMCWK);

		// <!-- Delete from 't_dailymcwk' -->
		delete("t_dailymcwk.delete", TDailyMCWK);
	}

	@Override
	public void insert( TDailyMCWK TDailyMCWK ) {
		// <!-- Insert to 't_dailymcwk' -->
		Integer dailyMCWKId = (Integer) insert("t_dailymcwk.insert", TDailyMCWK);
		TDailyMCWK.setDailyMCWKId(dailyMCWKId);

		// <!-- Insert to 't_dailymcwk_detail' -->
		insertDetail(TDailyMCWK);

		// <!-- Insert to 't_dailymcwk_ngreason' -->
		insertNGReason(TDailyMCWK);

		// <!-- Insert to 't_dailymcwk_stopmc' -->
		insertStopMC(TDailyMCWK);
	}

	@Override
	public TDailyMCWK update( TDailyMCWK TDailyMCWK ) {
		// <!-- Delete from 't_dailymcwk_detail' & 't_dailymcwk_ngreason' & 't_dailymcwk_stopmc' -->
		deleteNGReason(TDailyMCWK);
		deleteStopMC(TDailyMCWK);
		deleteDetail(TDailyMCWK);

		// <!-- Insert into 't_dailymcwk_detail' & 't_dailymcwk_ngreason' & 't_dailymcwk_stopmc' -->
		insertDetail(TDailyMCWK);
		insertNGReason(TDailyMCWK);
		insertStopMC(TDailyMCWK);

		// <!-- Update to 't_dailymcwk' -->
		update("t_dailymcwk.update", TDailyMCWK);
		
		return TDailyMCWK;
	}

	@Override
	public TDailyMCWK query( TDailyMCWK TDailyMCWK ) {
		// <!-- Select from 't_dailymcwk' -->
		TDailyMCWK = (TDailyMCWK) queryForObject("t_dailymcwk.query", TDailyMCWK);

		// <!-- Select from 't_dailymcwk_detail' -->
		queryDetail(TDailyMCWK);

		// <!-- Select from 't_dailymcwk_ngreason' -->
		queryNGReason(TDailyMCWK);

		// <!-- Select from 't_dailymcwk_stopmc' -->
		queryStopMC(TDailyMCWK);
		
		return TDailyMCWK;
	}

	@Override
	public TDailyMCWK queryList( TDailyMCWK TDailyMCWK ) {
		TDailyMCWK.setDailyMCWKList( (List<TDailyMCWK>) queryForList("t_dailymcwk.query", TDailyMCWK, getSkipResult(TDailyMCWK), TDailyMCWK.getPageCount()) );
		calPageTotal("t_dailymcwk.count", TDailyMCWK);

		return TDailyMCWK;
	}

	@Override
	public List<MWorkOrder> queryWorkOrderNoList( MWorkOrder MWorkOrder ) {
		return (List<MWorkOrder>) queryForList("t_dailymcwk.queryWorkOrderNoList", MWorkOrder);
	}

	private void deleteDetail( TDailyMCWK TDailyMCWK ) {
		delete("t_dailymcwk.deleteDetail", TDailyMCWK);
	}

	private void deleteNGReason( TDailyMCWK TDailyMCWK ) {
		delete("t_dailymcwk.deleteNGReason", TDailyMCWK);
	}

	private void deleteStopMC( TDailyMCWK TDailyMCWK ) {
		delete("t_dailymcwk.deleteStopMC", TDailyMCWK);
	}

	private void insertDetail( TDailyMCWK TDailyMCWK ) {
		List<TDailyMCWKDetail> detailList = TDailyMCWK.getDetailList();

		// <!-- Providing the 'reportTime' data. -->
		int dailyMCWKDetailId = 1;
		for( TDailyMCWKDetail TDailyMCWKDetail : detailList )
			TDailyMCWKDetail.setDailyMCWKDetailId(dailyMCWKDetailId++);

		for (TDailyMCWKDetail TDailyMCWKDetail : detailList) {
			TDailyMCWKDetail.setDailyMCWKId(TDailyMCWK.getDailyMCWKId());
			insert("t_dailymcwk.insertDetail", TDailyMCWKDetail);
		}
	}

	private void insertNGReason( TDailyMCWK TDailyMCWK ) {
		List<TDailyMCWKNGReason> reasons = new LinkedList<TDailyMCWKNGReason>();

		// <!-- Providing the data. -->
		for( TDailyMCWKDetail TDailyMCWKDetail : TDailyMCWK.getDetailList() ) {
			Map<Integer,Integer> map = TDailyMCWKDetail.getNgReasonMap();

			// <!-- Check if 'map' is null. -->
			if( map == null || map.size() < 1 )
				continue;

			for( Map.Entry<Integer,Integer> entry : map.entrySet() ) {
				Integer key = entry.getKey();
				Integer val = entry.getValue();

				TDailyMCWKNGReason TDailyMCWKNGReason = new TDailyMCWKNGReason();
				TDailyMCWKNGReason.setDailyMCWKId(TDailyMCWK.getDailyMCWKId());
				TDailyMCWKNGReason.setDailyMCWKDetailId(TDailyMCWKDetail.getDailyMCWKDetailId());
				TDailyMCWKNGReason.setNg(val);
				TDailyMCWKNGReason.setReasonId(key);

				reasons.add(TDailyMCWKNGReason);
			}
		}

		for (TDailyMCWKNGReason TDailyMCWKNGReason : reasons) {
			insert("t_dailymcwk.insertNGReason", TDailyMCWKNGReason);
		}
	}

	private void insertStopMC( TDailyMCWK TDailyMCWK ) {
		List<TDailyMCWKStopMC> stops = new LinkedList<TDailyMCWKStopMC>();

		// <!-- Providing the data. -->
		int reportTime = -1;
		for( Map<Integer,Integer> stopMC : TDailyMCWK.getStopMCList() ) {

			reportTime++;

			if( stopMC == null || stopMC.size() < 1 )
				continue;

			for( Map.Entry<Integer,Integer> entry : stopMC.entrySet() ) {
				Integer key = entry.getKey();
				Integer val = entry.getValue();

				TDailyMCWKStopMC TDailyMCWKStopMC = new TDailyMCWKStopMC();
				TDailyMCWKStopMC.setDailyMCWKId(TDailyMCWK.getDailyMCWKId());
				TDailyMCWKStopMC.setReasonId(key);
				TDailyMCWKStopMC.setReportTime(reportTime);
				TDailyMCWKStopMC.setStopMinute(val);

				stops.add(TDailyMCWKStopMC);
			}
		}

		for (TDailyMCWKStopMC TDailyMCWKStopMC : stops) {
			insert("t_dailymcwk.insertStopMC", TDailyMCWKStopMC);
		}
	}

	private void queryDetail( TDailyMCWK TDailyMCWK ) {
		List<TDailyMCWKDetail> detailList = (List<TDailyMCWKDetail>) queryForList("t_dailymcwk.queryDetail", TDailyMCWK);

		TDailyMCWK.setDetailList(detailList);
	}

	private void queryNGReason( TDailyMCWK TDailyMCWK ) {
		List<TDailyMCWKNGReason> resultList = (List<TDailyMCWKNGReason>) queryForList("t_dailymcwk.queryNGReason", TDailyMCWK);
		if(TDailyMCWK.getDetailList().size() < 1){
			return;
		}
		// <!-- Assign the data. -->		
		int totalReason = new BigDecimal( resultList.size() / TDailyMCWK.getDetailList().size() ).intValue();
		int startReason = 0;
		for( TDailyMCWKDetail TDailyMCWKDetail : TDailyMCWK.getDetailList() ) {
			Map<Integer,Integer> ngReasonMap = new LinkedHashMap<Integer,Integer>();
			TDailyMCWKDetail.setNgReasonMap(ngReasonMap);
			for( TDailyMCWKNGReason reason : resultList.subList(startReason, (startReason += totalReason)) ) {
				ngReasonMap.put(reason.getReasonId(), reason.getNg());
			}
		}
	}

	private void queryStopMC( TDailyMCWK TDailyMCWK ) {
		List<TDailyMCWKStopMC> resultList = (List<TDailyMCWKStopMC>) queryForList("t_dailymcwk.queryStopMC", TDailyMCWK);

		// <!-- Reordering: if shift is 'Night'. -->
		if( TDailyMCWK.getShift().charAt(0) == 'N' )
			Collections.sort(resultList, new Comparator<TDailyMCWKStopMC>() {

				@Override
				public int compare(TDailyMCWKStopMC o1, TDailyMCWKStopMC o2) {
					if( o1.getReportTime() > 20 && o2.getReportTime() < 21 )
						return -1;
					if( o1.getReportTime() < 21 && o2.getReportTime() > 20 )
						return 1;
					return o1.getReportTime().compareTo( o2.getReportTime() );
				}
			});

		// <!-- Assign the data. -->
		int lastTime = 0;
		Map<Integer,Integer> stopMap = null;
		for( TDailyMCWKStopMC stop : resultList ) {
			boolean isNextTime = lastTime != stop.getReportTime();
			if( isNextTime ) {
				lastTime = stop.getReportTime();

				// <!-- Initial. -->
				TDailyMCWK.getStopMCList().add(stopMap = new LinkedHashMap<Integer,Integer>());
			}

			stopMap.put(stop.getReasonId(), stop.getStopMinute());
		}
	}

	@Override
	public TDailyMCWK selectDAL_R02(TDailyMCWK tDailyMCWK) {
		tDailyMCWK.setDailyMCWKList( (List<TDailyMCWK>) queryForList("t_dailymcwk.selectDAL_R02",tDailyMCWK) );
		tDailyMCWK.setReasonMap( (Map<String, TDailyMCWKNGReason>) queryForMap("t_dailymcwk.queryReason", tDailyMCWK, "id") );
		return tDailyMCWK;
	}
	
	@Override
	public Integer countDAL_R02() {
		
		return (Integer) queryForObject("t_dailymcwk.countDAL_R02");
	}
}