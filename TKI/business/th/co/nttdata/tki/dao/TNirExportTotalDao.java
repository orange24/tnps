package th.co.nttdata.tki.dao;

import th.co.nttdata.tki.bean.TNirExportTotal;

public interface TNirExportTotalDao {
	/**
	 * Getting export total by month.
	 * 
	 * @param month - number of month (0-11)
	 * @return last of export total
	 */
	Integer getExportTotalByMonth(Integer month);

	/**
	 * delete all data in export total table.
	 * 
	 */
	void deleteAllExportTotalData();

	/**
	 * Insert for case new month.
	 * 
	 * @param tNirExportTotal - data.
	 */
	void insertNewMonth(TNirExportTotal tNirExportTotal);

	/**
	 * update last export total.
	 * 
	 * @param tNirExportTotal - data.
	 */
	void updateExportTotal(TNirExportTotal tNirExportTotal);
}
