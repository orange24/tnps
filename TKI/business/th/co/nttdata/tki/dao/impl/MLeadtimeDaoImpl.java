package th.co.nttdata.tki.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MLeadtime;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MLeadtimeDao;

@Repository
@SuppressWarnings("unchecked")
public class MLeadtimeDaoImpl extends AbstractBaseDao implements MLeadtimeDao{

	@Override
	public MLeadtime queryLeadtimeList(MLeadtime mLeadtime) {
		mLeadtime.setLeadTimeList((List<MLeadtime>)queryForList("m_leadtime.queryLeadtime",mLeadtime,getSkipResult(mLeadtime),mLeadtime.getPageCount()));
		calPageTotal("m_leadtime.count", mLeadtime);
		return mLeadtime;
	}

	@Override
	public MLeadtime queryLeadtimeExportList(MLeadtime mLeadtime) {
		mLeadtime.setLeadTimeList((List<MLeadtime>)queryForList("m_leadtime.queryLeadtime",mLeadtime));
		return mLeadtime;
	}

	@Override
	public void insertLeadtime(MLeadtime mLeadtime) {
		try {
			getSqlMapClient().startBatch();
		for(MLeadtime leadtime : mLeadtime.getLeadTimeList()){
			insert("m_leadtime.insertLeadtime",leadtime);
		}
		getSqlMapClient().executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateSTUseAll(MLeadtime mLeadtime) {
		update("m_leadtime.updateSTUseAll", mLeadtime);
	}

	@Override
	public List<MLeadtime> getWipPart() {
		return (List<MLeadtime>)queryForList("m_leadtime.queryPartWip");
	}

	@Override
	public void insertPartWip(MLeadtime mLeadtime) {
		insert("m_leadtime.insertPartWip",mLeadtime);
	}

	@Override
	public MLeadtime queryLeadTimeConfig() {
		MLeadtime mLeadtime = (MLeadtime)queryForObject("m_leadtime.queryLeadtimeConfig");
		return mLeadtime;
	}

	@Override
	public void deleteLeadTimeConfig() {
		delete("m_leadtime.deleteLeadtimeConfig");
		
	}

	@Override
	public void insertLeadTimeConfig(MLeadtime mLeadtime) {
		insert("m_leadtime.insertLeadtimeConfig", mLeadtime);
	}

	@Override
	public List<MPart> getSTByPartIdAndWip(MLeadtime object) {
		return queryForList("m_leadtime.select_st", object);
	}
}
