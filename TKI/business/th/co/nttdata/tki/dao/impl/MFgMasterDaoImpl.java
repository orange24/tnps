package th.co.nttdata.tki.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.FgMaster;
import th.co.nttdata.tki.bean.filter.FgMasterFilter;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MFgMasterDao;

@Repository
public class MFgMasterDaoImpl extends AbstractBaseDao implements MFgMasterDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<FgMaster> getFgMastertSearchList(FgMaster fgMaster) {
		List<FgMaster> fgMasterList = new ArrayList<FgMaster>();
		fgMasterList = (queryForList("m_mfgMaster.select_Init", fgMaster));
		return fgMasterList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FgMaster> getUomList() {
		return queryForList("m_mfgMaster.select_uom_auto");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FgMaster> getClassifyBusiness() {
		return queryForList("m_mfgMaster.select_classify");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FgMaster> getPlace() {
		return queryForList("m_mfgMaster.select_place");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FgMaster> getSubbusiness() {
		return queryForList("m_mfgMaster.select_subbusiness");
	}

	@Override
	public void insertFgMaster(FgMaster fgMaster) {
		insert("m_mfgMaster.insert_cust", fgMaster);
	}

	@Override
	public void updateFgMaster(FgMaster fgMaster) {
		update("m_mfgMaster.update_cust", fgMaster);
	}

	@Override
	public void deleteFgMaster(FgMaster fgMaster) {
		delete("m_mfgMaster.delete_cust", fgMaster);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FgMasterFilter> getFgMasterList(FgMasterFilter fgMaster) {
		return queryForList("m_mfgMaster.select_export", fgMaster);
	}

	@SuppressWarnings("unchecked")
	@Override
	public FgMaster searchCustomerFgMappingByCustomerId(FgMaster fgmaster) {
		fgmaster.setFgMasterList(queryForList("m_mfgMaster.select_search",
				fgmaster));
		return fgmaster;
	}

	@Override
	public void insertCustomerFgMaster(FgMaster fgMaster) {
		insert("m_mfgMaster.insert_fg_part_mapping", fgMaster);
	}

	@Override
	public void deleteCustomerFgMaster(FgMaster fgMaster) {
		delete("m_mfgMaster.delete_fg_part_mapping", fgMaster);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FgMaster> getFgList() {
		return queryForList("m_mfgMaster.select_part");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getAllFgMap() {
		List<FgMaster> mFgs = queryForList("m_mfgMaster.selectAllFg");
		Map<String, Integer> fgMap = new HashMap<String, Integer>();
		for (FgMaster fgMaster : mFgs) {
			fgMap.put(fgMaster.getFgNo(), fgMaster.getFgId());
		}

		return fgMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FgMaster> getCurrencyList() {
		return queryForList("m_mfgMaster.select_currency");
	}
}
