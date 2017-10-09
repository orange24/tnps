package th.co.nttdata.tki.dao;

import th.co.nttdata.tki.bean.FgStockNirvana;
import th.co.nttdata.tki.bean.MNirvanaSyncMaster;

public interface MNirvanaSyncMasterDao {

	/**
	 * Search history Nirvana master data sync according criteria from screen.
	 * 
	 * @param mNirvanaSyncMaster - criteria search.
	 * @return - history Nirvana master data sync.
	 */
	MNirvanaSyncMaster queryNirS01(MNirvanaSyncMaster mNirvanaSyncMaster);

	/**
	 * Update sync status error to fixed.
	 * 
	 * @param mNirvanaSyncMaster - data selected to update.
	 */
	void updateSyncStatus(MNirvanaSyncMaster mNirvanaSyncMaster);

	/**
	 * Get count row data to export report.
	 * 
	 * @param fgStockNirvana - criteria to filter data.
	 * @return number of count row.
	 */
	Integer countNirR02Rows(FgStockNirvana fgStockNirvana);

	/**
	 * Select FG stock to Nirvana data for export report.
	 * 
	 * @param fgStockNirvana - criteria to filter data.
	 * @return FG stock to Nirvana data
	 */
	FgStockNirvana selectNirR02(FgStockNirvana fgStockNirvana);
}
