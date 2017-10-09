package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.TMoldHistory;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TMoldHistoryDao;

@Repository
@SuppressWarnings("unchecked")
public class TMoldHistoryDaoImpl extends AbstractBaseDao implements TMoldHistoryDao {

	@Override
	public TMoldHistory search(TMoldHistory mHist) {
		List<TMoldHistory> resultList = (List<TMoldHistory>) queryForList("t_mold_history.search", mHist, getSkipResult(mHist), mHist.getPageCount());
		mHist.settMoldHistList(resultList);
		calPageTotal("t_mold_history.count", mHist);
		return mHist;
	}

}
