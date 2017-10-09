package th.co.nttdata.tki.dao.impl;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.TDailyWK;
import th.co.nttdata.tki.bean.TDailyWKDetail;
import th.co.nttdata.tki.bean.TDailyWKNGReason;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TDailyWKNGReasonDao;

@Repository
@SuppressWarnings("unchecked")
public class TDailyWKNGReasonDaoImpl extends AbstractBaseDao implements TDailyWKNGReasonDao {

	@Override
	public void insert( TDailyWK TDailyWK ) {
		List<TDailyWKNGReason> reasons = new LinkedList<TDailyWKNGReason>();

		// <!-- Providing the data. -->
		for( TDailyWKDetail TDailyWKDetail : TDailyWK.getDailyWKDetailList() ) {
			Map<Integer,Integer> map = TDailyWKDetail.getNgReasonMap();

			// <!-- Checking if null. -->
			if( map == null )
				continue;
			for( Map.Entry<Integer,Integer> entry : map.entrySet() ) {
				Integer key = entry.getKey();
				Integer val = entry.getValue();

				TDailyWKNGReason TDailyWKNGReason = new TDailyWKNGReason();
				TDailyWKNGReason.setDailyWKId(TDailyWK.getDailyWKId());
				TDailyWKNGReason.setDailyWKDetailId(TDailyWKDetail.getDailyWKDetailId());
				TDailyWKNGReason.setNg(val);
				TDailyWKNGReason.setReasonId(key);

				reasons.add(TDailyWKNGReason);
			}
		}
		
		for(TDailyWKNGReason rs: reasons){
			insert("t_dailywk_ngreason.insert", rs);
		}
	}

	@Override
	public void query( TDailyWK TDailyWK ) {
		List<TDailyWKNGReason> resultList = (List<TDailyWKNGReason>) queryForList("t_dailywk_ngreason.query", TDailyWK);
		if(TDailyWK.getDailyWKDetailList().size() < 1){
			return;
		}
		// <!-- Assign the data. -->
		int totalReason = new BigDecimal( resultList.size() / TDailyWK.getDailyWKDetailList().size() ).intValue();
		int startReason = 0;
		for( TDailyWKDetail TDailyWKDetail : TDailyWK.getDailyWKDetailList() ) {
			Map<Integer,Integer> ngReasonMap = new LinkedHashMap<Integer,Integer>();
			TDailyWKDetail.setNgReasonMap(ngReasonMap);

			for( TDailyWKNGReason reason : resultList.subList(startReason, (startReason += totalReason)) ) {
				ngReasonMap.put(reason.getReasonId(), reason.getNg());
			}
		}
	}
}