package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.TReworkAdjust;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TReworkAdjustDao;

@Repository
@SuppressWarnings("unchecked")
public class TReworkAdjustDaoImpl extends AbstractBaseDao implements TReworkAdjustDao {

	@Override
	public int insert(TReworkAdjust reworkAdjust) {
		super.update("t_rework_adjust.insert", reworkAdjust);		
		return 1;
	}

	@Override
	public TReworkAdjust query(TReworkAdjust reworkAdjust) {
		query(reworkAdjust, "");
		return reworkAdjust;
	}

	@Override
	public TReworkAdjust queryHis(TReworkAdjust reworkAdjust) {
		return query(reworkAdjust, "His");
	}
	
	private TReworkAdjust query(TReworkAdjust reworkAdjust, String suffix) {
		reworkAdjust.setAdjustList((List<TReworkAdjust>) queryForList("t_rework_adjust.query"+suffix, reworkAdjust, 
				getSkipResult(reworkAdjust), reworkAdjust.getPageCount()));
		calPageTotal("t_rework_adjust.count"+suffix, reworkAdjust);		
		return reworkAdjust;
	}

}