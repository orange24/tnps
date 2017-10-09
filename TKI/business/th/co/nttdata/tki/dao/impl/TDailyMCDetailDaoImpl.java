package th.co.nttdata.tki.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.TDCPlan;
import th.co.nttdata.tki.bean.TDailyMC;
import th.co.nttdata.tki.bean.TDailyMCDetail;
import th.co.nttdata.tki.bean.TDailyMCNGReason;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TDailyMCDetailDao;

@Repository
@SuppressWarnings("unchecked")
public class TDailyMCDetailDaoImpl extends AbstractBaseDao implements TDailyMCDetailDao {

	@Override
	public void delete( TDailyMC TDailyMC ) {
		delete("t_dailymc_detail.delete", TDailyMC);
	}

	@Override
	public void insert( TDailyMC TDailyMC ) {
		List<TDailyMCDetail> details = TDailyMC.getDetails();

		// <!-- Providing the 'reportTime' data. -->
		int reportTime = 1;
		for( TDailyMCDetail TDailyMCDetail : details )
			TDailyMCDetail.setReportTime(reportTime++);

		insert("t_dailymc_detail.insert", TDailyMC);
	}

	@Override
	public void query( TDailyMC TDailyMC ) {
		List<TDailyMCDetail> details = (List<TDailyMCDetail>) queryForList("t_dailymc_detail.query", TDailyMC);

		TDailyMC.setDetails(details);
	}

	@Override
	public TDailyMC selectMRDC_S17(TDailyMC TDailyMC) {
		TDailyMC.setDetails((List<TDailyMCDetail>) queryForList("t_dailymc_detail.queryMRDC_R17", TDailyMC));
		TDailyMC.setReasonMap((Map<String, TDailyMCNGReason>) queryForMap("t_dailymc_detail.queryMRDC_R17Reason", TDailyMC, "idRef"));
		return TDailyMC;
	}
	
	@Override
	public Integer countMRDC_R17() {
		
		return (Integer) queryForObject("t_dailymc_detail.countMRDC_R17");
	}
	
	@Override
	public List<MWip> getWip(MWip MWip) {
		return (List<MWip>) queryForList("t_dailymc_detail.getWip",MWip);
	}
	
	@Override
	public Integer selectCheckInputActual(TDCPlan object) {
		return (Integer)queryForObject("t_dailymc_detail.select_check_input_actual", object);
	}

}