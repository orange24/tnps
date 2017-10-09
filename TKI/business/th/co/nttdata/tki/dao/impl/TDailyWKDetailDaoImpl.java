package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.TDailyWK;
import th.co.nttdata.tki.bean.TDailyWKDetail;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TDailyWKDetailDao;

@Repository
@SuppressWarnings("unchecked")
public class TDailyWKDetailDaoImpl extends AbstractBaseDao implements TDailyWKDetailDao {

	@Override
	public void delete( TDailyWK TDailyWK ) {
		delete("t_dailywk_detail.delete", TDailyWK);
	}

	@Override
	public void insert( TDailyWK TDailyWK ) {
		// <!-- Generating the 'dailyWKDetailId' identity. -->
		int index = 0;
		for( TDailyWKDetail TDailyWKDetail : TDailyWK.getDailyWKDetailList() ) {
			TDailyWKDetail.setDailyWKDetailId( index++ );
			TDailyWKDetail.setDailyWKId(TDailyWK.getDailyWKId());
			// <!-- VALIDATION: if value is null. -->
		}

		for (TDailyWKDetail TDailyWKDetail : TDailyWK.getDailyWKDetailList()) {
			insert("t_dailywk_detail.insert", TDailyWKDetail);
		}
	}

	@Override
	public void query( TDailyWK TDailyWK ) {
		List<TDailyWKDetail> details = (List<TDailyWKDetail>) queryForList("t_dailywk_detail.query", TDailyWK);

		TDailyWK.setDailyWKDetailList(details);
	}
}