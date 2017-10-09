package th.co.nttdata.tki.dao.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.TDailyMC;
import th.co.nttdata.tki.bean.TDailyMCDetail;
import th.co.nttdata.tki.bean.TDailyMCStopMC;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TDailyMCStopMCDao;

@Repository
@SuppressWarnings("unchecked")
public class TDailyMCStopMCDaoImpl extends AbstractBaseDao implements TDailyMCStopMCDao {

	@Override
	public void delete( TDailyMC TDailyMC ) {
		delete("t_dailymc_stopmc.delete", TDailyMC);
	}

	@Override
	public void insert( TDailyMC TDailyMC ) {
		List<TDailyMCStopMC> stops = new LinkedList<TDailyMCStopMC>();

		// <!-- Providing the data. -->
		int reportTime = 0;
		for( TDailyMCDetail TDailyMCDetail : TDailyMC.getDetails() ) {
			Map<Integer,Integer> map = TDailyMCDetail.getStops();
			reportTime += 1;

			// <!-- Check if 'map' is null. -->
			if( map == null )
				break;

			for( Map.Entry<Integer,Integer> entry : map.entrySet() ) {
				Integer key = entry.getKey();
				Integer val = entry.getValue();

				TDailyMCStopMC TDailyMCStopMC = new TDailyMCStopMC();
				TDailyMCStopMC.setDailyMCId(TDailyMC.getDailyMCId());
				TDailyMCStopMC.setReasonId(key);
				TDailyMCStopMC.setReportTime(reportTime);
				TDailyMCStopMC.setStopMinute(val);

				stops.add(TDailyMCStopMC);
			}
		}

		insert("t_dailymc_stopmc.insert", stops);
	}

	@Override
	public void query( TDailyMC TDailyMC ) {
		TDailyMCStopMC TDailyMCStopMC = new TDailyMCStopMC();
		TDailyMCStopMC.setDailyMCId(TDailyMC.getDailyMCId());
		List<TDailyMCStopMC> resultList = (List<TDailyMCStopMC>) queryForList("t_dailymc_stopmc.query", TDailyMCStopMC);

		// <!-- Assign the data. -->
		int lastTime = 0;
		Map<Integer,Integer> stops = null;
		for( TDailyMCStopMC stop : resultList ) {
			boolean isNextTime = lastTime != stop.getReportTime();
			if( isNextTime ) {
				lastTime = stop.getReportTime();

				// <!-- Initial. -->
				TDailyMCDetail TDailyMCDetail = TDailyMC.getDetails().get(lastTime - 1);
				TDailyMCDetail.setStops(stops = new LinkedHashMap<Integer,Integer>());
			}

			stops.put(stop.getReasonId(), stop.getStopMinute());
		}
	}
}