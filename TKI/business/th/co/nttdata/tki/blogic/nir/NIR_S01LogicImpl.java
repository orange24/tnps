package th.co.nttdata.tki.blogic.nir;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MNirvanaSyncMaster;
import th.co.nttdata.tki.dao.CfgSystemConfigDao;
import th.co.nttdata.tki.dao.MNirvanaSyncMasterDao;

@Service
public class NIR_S01LogicImpl implements NIR_S01Logic {
	@Autowired
	private CfgSystemConfigDao cfgSystemConfigDao;

	@Autowired
	private MNirvanaSyncMasterDao mNirvanaSyncMasterDao;

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public MNirvanaSyncMaster search(MNirvanaSyncMaster mNirvanaSyncMaster) {
		return mNirvanaSyncMasterDao.queryNirS01(mNirvanaSyncMaster);
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void save(MNirvanaSyncMaster mNirvanaSyncMaster) {
		mNirvanaSyncMaster.setSyncStatus("F");
		mNirvanaSyncMasterDao.updateSyncStatus(mNirvanaSyncMaster);
	}
}
