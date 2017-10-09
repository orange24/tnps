package th.co.nttdata.tki.dao;

public interface CfgSystemConfigDao {

	/**
	 * Get start account fiscal month configuration.
	 * 
	 * @return number of month.
	 */
	Integer getNirAccountFiscalMonth();

	/**
	 * Get max row data per file to export report.
	 * 
	 * @return number of max row
	 */
	Integer getNirMaxRowExportPerfile();

	/**
	 * Get share path for FG stock report.
	 * 
	 * @return share path
	 */
	String getNirSharePath();

	/**
	 * Get temporary path for FG stock report.
	 * 
	 * @return temporary path
	 */
	String getNirTempPath();

}
