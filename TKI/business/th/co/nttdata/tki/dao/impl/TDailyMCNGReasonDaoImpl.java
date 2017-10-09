package th.co.nttdata.tki.dao.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.TDailyMC;
import th.co.nttdata.tki.bean.TDailyMCDetail;
import th.co.nttdata.tki.bean.TDailyMCNGReason;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TDailyMCNGReasonDao;

@Repository
@SuppressWarnings("unchecked")
public class TDailyMCNGReasonDaoImpl extends AbstractBaseDao implements TDailyMCNGReasonDao {

	@Override
	public void delete( TDailyMC TDailyMC ) {
		delete("t_dailymc_ngreason.delete", TDailyMC);
	}

	@Override
	public void insert( TDailyMC TDailyMC ) {
		List<TDailyMCNGReason> reasons = new LinkedList<TDailyMCNGReason>();

		// <!-- Providing the data. -->
		int reportTime = 0;
		for( TDailyMCDetail TDailyMCDetail : TDailyMC.getDetails() ) {
			Map<Integer,Integer> map = TDailyMCDetail.getReasons();
			reportTime += 1;

			// <!-- Check if 'map' is null. -->
			if( map == null )
				break;

			for( Map.Entry<Integer,Integer> entry : map.entrySet() ) {
				Integer key = entry.getKey();
				Integer val = entry.getValue();

				TDailyMCNGReason TDailyMCNGReason = new TDailyMCNGReason();
				TDailyMCNGReason.setDailyMCId(TDailyMC.getDailyMCId());
				TDailyMCNGReason.setNg(val);
				TDailyMCNGReason.setReasonId(key);
				TDailyMCNGReason.setReportTime(reportTime);

				reasons.add(TDailyMCNGReason);
			}
		}

		insert("t_dailymc_ngreason.insert", reasons);
	}

	@Override
	public void query( TDailyMC TDailyMC ) {
		TDailyMCNGReason TDailyMCNGReason = new TDailyMCNGReason();
		TDailyMCNGReason.setDailyMCId(TDailyMC.getDailyMCId());
		List<TDailyMCNGReason> resultList = (List<TDailyMCNGReason>) queryForList("t_dailymc_ngreason.query", TDailyMCNGReason);

		// <!-- Assign the data. -->
		int lastTime = 0;
		Map<Integer,Integer> reasons = null;
		for( TDailyMCNGReason reason : resultList ) {
			boolean isNextTime = lastTime != reason.getReportTime();
			if( isNextTime ) {
				lastTime = reason.getReportTime();

				// <!-- Initial. -->
				TDailyMCDetail TDailyMCDetail = TDailyMC.getDetails().get(lastTime - 1);
				TDailyMCDetail.setReasons(reasons = new LinkedHashMap<Integer,Integer>());
			}

			reasons.put(reason.getReasonId(), reason.getNg());
		}
	}
}