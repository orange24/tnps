package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MFgPart;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MFgPartMappingDao;

@Repository
public class MFgPartMappingDaoImpl extends AbstractBaseDao implements
		MFgPartMappingDao {

	@SuppressWarnings("unchecked")
	@Override
	public MFgPart searchFgPartMappingByCustomerId(MFgPart mFgPart) {
		MFgPart mfgPart = new MFgPart();
		mfgPart.setCustomerList(queryForList("m_fg.select_search", mFgPart));
		return mfgPart;
	}

	@Override
	public void insertMFgpart(MFgPart mfg) {
		insert("m_fg.insert_fg_part_mapping", mfg);
	}

	@Override
	public void updateMFgpart(MFgPart mfg) {
		update("m_fg.update_fg_part_mapping", mfg);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MFgPart> getFgNoNameListByCustomerId(MFgPart mFgPart) {
		return queryForList("m_fg.select_fg_by_cust", mFgPart);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MFgPart> selectPartListByFgId(MFgPart mFgPart) {
		return queryForList("m_fg.select_part", mFgPart);
	}
}
