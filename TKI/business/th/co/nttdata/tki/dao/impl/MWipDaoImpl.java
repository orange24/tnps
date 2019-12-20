package th.co.nttdata.tki.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.filter.MWipFilter;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MWipDao;

@Repository
@SuppressWarnings("unchecked")
public class MWipDaoImpl extends AbstractBaseDao implements MWipDao {

	@Autowired
	protected Properties settings;

	@Override
	public List<MWip> getWip() {
		return getWip(new MWip());
	}

	@Override
	public List<MWip> getWipMachine() {
		MWip MWip = new MWip();
		MWip.setWipType(1);
		return getWip(MWip);
	}

	@Override
	public List<MWip> getWipWorker() {
		MWip MWip = new MWip();
		MWip.setWipType(2);
		return getWip(MWip);
	}

	@Override
	public List<MWip> getWipMachineWorker() {
		MWip MWip = new MWip();
		MWip.setWipType(3);
		return getWip(MWip);
	}

	@Override
	public List<MWip> getWip(MWip MWip) {
		return queryForList("m_wip.queryWip", MWip);
	}

	@Override
	public MWip getWipList(MWip mWip) {
		mWip.setWipList(queryForList("m_wip.queryWipSearch", mWip,
				getSkipResult(mWip), mWip.getPageCount()));
		calPageTotal("m_wip.count", mWip);
		return mWip;
	}

	@Override
	public List<MWip> getWipSearchList(MWip mWip) {

		return queryForList("m_wip.queryWipSearch", mWip);
	}

	@Override
	public void delete(MWip mWip) {
		delete("m_wip.delete", mWip);
	}

	@Override
	public void save(MWip mWip) {
		update("m_wip.updateMwip", mWip);
		update("m_wip.updateMPartWip", mWip);
	}

	@Override
	public MWip mergeWip(MWip wip) {
		update("m_wip.merge", wip);
		return wip;
	}

	@Override
	public void insert(MWip wip) {
		insert("m_wip.mergeWip", wip);
	}

	@Override
	public MWip getWipTpicList(MWip wip) {
		wip.setLinkDB(settings.getProperty("CMM.linkedServer",
				"\\\\172.16.12.200\\pipe\\sql\\query"));
		wip.setWipList(queryForList("m_wip.queryWipTpicSearch", wip,
				getSkipResult(wip), wip.getPageCount()));
		calPageTotal("m_wip.countTpic", wip);
		return wip;
	}

	@Override
	public boolean checkReason(MWip MWip) {
		return (Integer) queryForObject("m_wip.checkReason", MWip) > 0;
	}

	@Override
	public boolean checkPart(MWip MWip) {
		return (Integer) queryForObject("m_wip.checkPart", MWip) > 0;
	}

	@Override
	public boolean checkMachine(MWip MWip) {
		return (Integer) queryForObject("m_wip.checkPart", MWip) > 0;
	}

	@Override
	public List<MWip> getWipFlFn() {
		return queryForList("m_wip.queryWIPFlFn", new MWip());
	}

	@Override
	public void insertMWip(MWip wip) {
		insert("m_wip.insertMwip", wip);
	}

	@Override
	public void updateMWip(MWip wip) {
		update("m_wip.updateMwip", wip);
	}

	@Override
	public void deleteMWip(MWip wip) {
		delete("m_wip.deleteMwip", wip);
	}

	@Override
	public MWip selectWipListByWip(MWip mWip) {
		MWip mwp = new MWip();
		mwp.setWipList(queryForList("m_wip.selectWip", mWip));
		return mwp;
	}

	@Override
	public List<MWip> getWipTypeName() {
		return queryForList("m_wip.selectWipTypeName");
	}

	@Override
	public List<MWip> getWipByWipType() {
		return queryForList("m_wip.select_wip_type3");
	}

	@Override
	public Map<String, String> getAllWipMap() {
		List<MWip> wips = queryForList("m_wip.selectWip", null);
		Map<String, String> wipMap = new HashMap<String, String>();
		for (MWip wip : wips) {
			wipMap.put(wip.getWipName(), wip.getWip());
		}

		return wipMap;
	}

	@Override
	public MWip selectWipListByWipFilter(MWipFilter mWip) {
		MWip mwp = new MWip();
		mwp.setWipList(queryForList("m_wip.selectWipFilter", mWip));
		return mwp;
	}
}