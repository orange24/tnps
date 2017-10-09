package th.co.nttdata.tki.dao.impl;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.FgStockNirvana;
import th.co.nttdata.tki.bean.MNirvanaSyncMaster;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MNirvanaSyncMasterDao;

@Repository
public class MNirvanaSyncMasterDaoImpl extends AbstractBaseDao implements MNirvanaSyncMasterDao {

	/**
	 * 
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public MNirvanaSyncMaster queryNirS01(MNirvanaSyncMaster mNirvanaSyncMaster) {
		mNirvanaSyncMaster.setMNirvanaSyncMasterList(queryForList("m_nirvana_sync_master.queryNirS01",
				mNirvanaSyncMaster));
		return mNirvanaSyncMaster;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void updateSyncStatus(MNirvanaSyncMaster mNirvanaSyncMaster) {
		update("m_nirvana_sync_master.updateSyncStatus", mNirvanaSyncMaster);
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public Integer countNirR02Rows(FgStockNirvana fgStockNirvana) {
		return (Integer) queryForObject("m_nirvana_sync_master.countNirR02Rows", fgStockNirvana);
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public FgStockNirvana selectNirR02(FgStockNirvana fgStockNirvana) {
		fgStockNirvana.setFgStockNirvanaList(queryForList("m_nirvana_sync_master.queryNirR02", fgStockNirvana));
		return fgStockNirvana;
	}

}
