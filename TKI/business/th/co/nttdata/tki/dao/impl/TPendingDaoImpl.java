package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.TPending;
import th.co.nttdata.tki.bean.TPendingAdjust;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TPendingDao;

@Repository
@SuppressWarnings("unchecked")
public class TPendingDaoImpl extends AbstractBaseDao implements TPendingDao {

	@Override
	public int insert(TPending pending) {
		for (TPendingAdjust pendingAdjust : pending.getAdjustList()) {
			if (pendingAdjust.getPdAdjustQty() > 0) {
				String resultId = String.valueOf(super.insert("t_pending.insert", pendingAdjust));
				int id = Integer.parseInt(resultId);
				pendingAdjust.setPdAdjustId((id));
				super.update("t_pending.insertRework", pendingAdjust);
			}
		}
		return 1;
	}

	@Override
	public TPending query(TPending pending) {
		query(pending, "");
		if (pending.getPageTotal() > 0) {
			pending.setWipList((List<MWip>) queryForList("t_pending.queryWip"));
			pending.setReasonList((List<MReason>) queryForList("m_reason.select_reason_type1"));
		}
		return pending;
	}

	@Override
	public TPending queryHis(TPending pending) {
		return query(pending, "His");
	}
	
	private TPending query(TPending pending, String suffix) {
		pending.setAdjustList((List<TPendingAdjust>) queryForList("t_pending.query"+suffix, pending, 
				getSkipResult(pending), pending.getPageCount()));
		calPageTotal("t_pending.count"+suffix, pending);
		return pending;
	}

	@Override
	public TPending queryViewPendingList(TPending tPending) {
		tPending.setAdjustList((List<TPendingAdjust>) queryForList("t_pending.queryV_09_PendingList",tPending));
		return tPending;
	}
	
	@Override
	public Integer countMRDC_R08() {
		
		return (Integer) queryForObject("t_pending.countMRDC_R08");
	}

}