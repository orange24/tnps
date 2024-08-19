package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MReasonDao;

@Repository
@SuppressWarnings("unchecked")
public class MReasonDaoImpl extends AbstractBaseDao implements MReasonDao {

	@Override
	public MReason getReason( MReason MReason ) {
		return (MReason) queryForObject("m_reason.queryReason", MReason);
	}

	@Override
	public List<MReason> getReasonList() {
		return getReasonList( null );
	}

	@Override
	public List<MReason> getReasonNGList() {
		MReason MReason = new MReason();
		MReason.setReasonType(1);

		return getReasonList( MReason );
	}

	@Override
	public List<MReason> getReasonList( MReason MReason ) {
		return (List<MReason>) queryForList("m_reason.queryReason", MReason);
	}

	@Override
	public List<MReason> getReasonMCStopList() {
		return (List<MReason>) queryForList("m_reason.queryReasonMCStopList");
	}

	@Override
	public List<MReason> getReasonInWip( MReason MReason ) {
		return (List<MReason>) queryForList("m_reason_wip.queryReasonInWip", MReason);
	}

	@Override
	public List<MReason> getParentReason() {
		return (List<MReason>) queryForList("m_reason.queryParentReason");
	}

	@Override
	public void insertReason(MReason mReason) {
		insert("m_reason.insertReason",mReason);
	}

	@Override
	public void updateReason(MReason mReason) {
		update("m_reason.updateReason",mReason);
	}

	@Override
	public void deleteReason(MReason mReason) {
		delete("m_reason.deleteReason",mReason);
	}

	@Override
	public void deleteReasonWip(MReason mReason) {
		delete("m_reason_wip.deleteReason",mReason);
	}
	
	@Override
	public MReason searchReason(MReason mReason) {
		mReason.setReasonList((List<MReason>) queryForList("m_reason_wip.queryReasonList",mReason,getSkipResult(mReason),mReason.getPageCount()));
		calPageTotal("m_reason_wip.count", mReason);
		return mReason;
	}

	@Override
	public MReason searchReasonOnly(MReason mReason) {
		mReason.setReasonList((List<MReason>) queryForList("m_reason.queryReasonList",mReason,getSkipResult(mReason),mReason.getPageCount()));
		calPageTotal("m_reason.count", mReason);
		return mReason;
	}

	@Override
	public MReason searchReasonUse(MReason mReason) {
		mReason.setReasonList((List<MReason>) queryForList("m_reason_wip.queryReasonUse",mReason));
		return mReason;
	}

	@Override
	public List<MReason> checkDupReasonCode(MReason mReason) {
		return (List<MReason>) queryForList("m_reason.queryCheckDupCode",mReason);
	}

	@Override
	public void deleteReasonWipByType(MReason mReason) {
		delete("m_reason_wip.deleteReasonWip",mReason);
	}

	@Override
	public void insertReasonWip(MReason mReason) {
		insert("m_reason_wip.insertReasonWip",mReason);
	}

	@Override
	public List<MReason> selectReasonMRDC_S17() {
		return (List<MReason>) queryForList("m_reason_wip.queryReasonMRDC_R17");
	}
	
	@Override
	public List<MReason> getReasonInWipType(MReason MReason) {
		return (List<MReason>) queryForList("m_reason.queryReasonInWipType", MReason);
	}

	@Override
	public List<MReason> getReasonMaster() {
		return queryForList("m_reason.select_reason_type3");
	}
	
}