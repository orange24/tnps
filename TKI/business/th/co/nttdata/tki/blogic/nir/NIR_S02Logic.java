package th.co.nttdata.tki.blogic.nir;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Workbook;

import th.co.nttdata.tki.bean.FgStockNirvana;

public interface NIR_S02Logic {
	static final SimpleDateFormat fileFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);
	// For internal reference format.
	final static SimpleDateFormat internalRefFormatter = new SimpleDateFormat("MM-yy-", Locale.US);

	/**
	 * Checking data to export.
	 * 
	 * @param fgStockNirvana - criteria to get data
	 * @return true when data more than max row and false when data less than max row.
	 */
	boolean isDataMoreThanMaxRow(FgStockNirvana fgStockNirvana);

	/**
	 * Generate FG stock report to export excel file.
	 * 
	 * @param fgStockNirvana - criteria to get data
	 * @return work book
	 * @throws IOException if occur error
	 */
	Workbook generateExcelFile(FgStockNirvana fgStockNirvana) throws IOException;

	/**
	 * Generate FG stock report to export, data more than 5000, Split data 5000/excel file then zip all to export zip
	 * file.
	 * 
	 * @param fgStockNirvana - criteria to get data
	 * @return directory path of zip file
	 * @throws IOException if occur error
	 */
	String generateZipFileExport(FgStockNirvana fgStockNirvana) throws IOException;
}
