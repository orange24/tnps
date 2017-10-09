package th.co.nttdata.tki.blogic.nir;

import th.co.nttdata.tki.bean.MNirvanaSyncMaster;

public interface NIR_S01Logic {
	/**
	 * Search history Nirvana master data sync according criteria from screen.
	 * 
	 * @param mNirvanaSyncMaster - criteria search.
	 * @return - history Nirvana master data sync.
	 */
	MNirvanaSyncMaster search(MNirvanaSyncMaster mNirvanaSyncMaster);

	/**
	 * Fixed sync status error to fixed.
	 * 
	 * @param mNirvanaSyncMaster - data selected to update.
	 */
	void save(MNirvanaSyncMaster mNirvanaSyncMaster);
}
