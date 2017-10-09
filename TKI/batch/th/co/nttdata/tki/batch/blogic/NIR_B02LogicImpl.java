package th.co.nttdata.tki.batch.blogic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import th.co.nttdata.batch.BatchLogicImpl;
import th.co.nttdata.tki.batch.bean.CfgSystemConfig;
import th.co.nttdata.tki.batch.bean.FgDetail;
import th.co.nttdata.tki.batch.bean.FgStockNirvanaBatch;
import th.co.nttdata.tki.batch.bean.TStockExportHistory;
import th.co.nttdata.tki.batch.bean.TNirExportTotal;

import com.ibatis.sqlmap.client.SqlMapClient;

@Service("NIR_B02")
public class NIR_B02LogicImpl extends BatchLogicImpl {

	private static final String BATCH_CODE = "NIR_B02";
	private static final String BATCH_NAME = "17. Nirvana Sync – FG Stock";
	private static final SimpleDateFormat fileFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);
	// For internal reference format.
	final static SimpleDateFormat internalRefFormatter = new SimpleDateFormat("MM-yy-", Locale.US);

	@Autowired
	@Qualifier("sqlsvrMapClient")
	private SqlMapClient sqlMap;

	@Override
	public void processing() throws Exception {
		log.info("NIR_B02LogicImpl is running.");
		FgStockNirvanaBatch fgStockNirvana = new FgStockNirvanaBatch();
		Calendar current = Calendar.getInstance();
		Date execDate = null;

		if (getExecuteDate() != null) {
			execDate = getExecuteDate();
		} else {
			execDate = new Date();
		}
		current.setTime(execDate);

		fgStockNirvana.setTransDate(current.getTime());
		log.info("Set parameter.");
		current.set(Calendar.HOUR_OF_DAY, 8);
		current.set(Calendar.MINUTE, 0);
		current.set(Calendar.SECOND, 0);

		fgStockNirvana.setLastUpdateTo(current.getTime());
		current.add(Calendar.DATE, -1);
		fgStockNirvana.setLastUpdateFr(current.getTime());

		log.info("LastUpdateFrom: "
				+ fgStockNirvana.getLastUpdateFr()
				+ " and LastUpdateTo: "
				+ fgStockNirvana.getLastUpdateTo());

		String tempPath = getConfigValueByKey(CfgSystemConfig.VALUE_KEY_NIR_TMP_PATH);
		String sharePath = getConfigValueByKey(CfgSystemConfig.VALUE_KEY_NIR_SHARE_PATH);
		Integer maxRow = Integer.valueOf(getConfigValueByKey(CfgSystemConfig.VALUE_KEY_NIR_MAX_REC));
		log.info("Count row of data: countNirR01Rows is running.");
		Integer countData = (Integer) sqlMap.queryForObject("m_nirvana_sync_master_batch.countNirB02Rows",
				fgStockNirvana);
		File srcFile = null;
		try {
			Calendar transCal = Calendar.getInstance();
			String internalReference = internalRefFormatter.format(transCal.getTime())
					+ getExportTotal(transCal.get(Calendar.MONTH) + 1, "SYSTEM");
			fgStockNirvana.setInternalReference(internalReference);

			if (countData > maxRow) {
				log.info("NIR_B02LogicImpl: generateZipFile is running.");
				String zipPath = generateZipFile(fgStockNirvana, tempPath, sharePath, maxRow, countData);
				srcFile = new File(zipPath);
			} else {
				StringBuffer fileName = new StringBuffer(sharePath);
				log.info("NIR_B02LogicImpl: generateExcelFile is running.");
				fileName.append("FG_").append(fileFormatter.format(fgStockNirvana.getTransDate())).append(".xls");
				Workbook workbook = generateExcelFile(fgStockNirvana);
				writeExcelFile(workbook, fileName.toString());
				srcFile = new File(fileName.toString());
			}

			log.info("Insert t_stock_export_history is running.");
			TStockExportHistory tStockExportHistory = new TStockExportHistory();
			tStockExportHistory.setExportFileName(srcFile.getName());
			tStockExportHistory.setExportBy(getExecutedBy());
			tStockExportHistory.setLastUpdateFr(fgStockNirvana.getLastUpdateFr());
			tStockExportHistory.setLastUpdateTo(fgStockNirvana.getLastUpdateTo());
			sqlMap.insert("t_stock_export_history_batch.insert", tStockExportHistory);

			log.info("Update exported flag in t_fg_detail is running.");
			FgDetail fgDetail = new FgDetail();
			fgDetail.setExported(FgDetail.EXPORTED);
			fgDetail.setLastUpdateFr(fgStockNirvana.getLastUpdateFr());
			fgDetail.setLastUpdateTo(fgStockNirvana.getLastUpdateTo());
			sqlMap.update("t_fg_detail_batch.updateExported", fgDetail);

			log.info("End of business logic on NIR_B02LogicImpl.");

			// update m_batch_control in case success.
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("batchcode", BATCH_CODE);
			param.put("batchname", BATCH_NAME);
			param.put("batchstatus", 0);
			param.put("runby", getExecutedBy());
			sqlMap.update("m_batch_control.updateMBatchControl", param);

			// Delete file on temporary path.
			deleteFiles(tempPath);
			log.info("-----End NIR_B02-----");
		} catch (Exception e) {
			// Remove file when error occur after generate file.
			if (null != srcFile) {
				deleteFiles(srcFile.getPath());
			}
			deleteFiles(tempPath);
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Get value configuration by key.
	 * 
	 * @param key - key to get value.
	 * @return value configuration
	 * @throws SQLException - if error occur.
	 */
	private String getConfigValueByKey(String key) throws SQLException {
		return (String) sqlMap.queryForObject("cfg_system_config.getConfigValueByKey", key);
	}

	/**
	 * Generate FG stock report, data more than 5000, Split data 5000/excel file then zip all to export zip file.
	 * 
	 * @param fgStockNirvana - criteria to get data
	 * @param tempPath - path to keep file before zip
	 * @param sharePath - path to share file
	 * @param maxRow - Max row from configuration
	 * @param countData - count for split file
	 * @return directory path of zip file
	 * @throws Exception if occur error
	 */
	private String generateZipFile(FgStockNirvanaBatch fgStockNirvana, String tempPath, String sharePath,
			Integer maxRow, Integer countData) throws Exception {
		StringBuffer zipFile = new StringBuffer();
		zipFile.append("FG_").append(fileFormatter.format(fgStockNirvana.getTransDate())).append(".zip");

		Integer countFile = (new BigDecimal(countData).divide(new BigDecimal(maxRow), RoundingMode.UP)).intValue();
		Integer fromRecord = 1;
		Integer toRecord = 0;
		List<String> sourceFiles = new ArrayList<String>();
		for (int fileCount = 1; fileCount <= countFile; fileCount++) {
			if (1 != fileCount) {
				fromRecord = ((fileCount - 1) * maxRow) + 1;
			}
			toRecord = fileCount * maxRow;

			fgStockNirvana.setFromRecord(fromRecord);
			fgStockNirvana.setToRecord(toRecord);
			List<FgStockNirvanaBatch> data = getNirB02(fgStockNirvana);
			File tempDir = new File(tempPath);
			if (!tempDir.exists()) {
				tempDir.mkdir();
			}
			StringBuffer fileName = new StringBuffer(tempPath);
			if (fileCount < 10) {
				fileName.append("FG_0")
						.append(fileCount)
						.append("_")
						.append(fileFormatter.format(fgStockNirvana.getTransDate()))
						.append(".xls");
			} else {
				fileName.append("FG_")
						.append(fileCount)
						.append("_")
						.append(fileFormatter.format(fgStockNirvana.getTransDate()))
						.append(".xls");
			}
			Workbook workbook = new HSSFWorkbook();
			buildExcelfile(workbook, data);
			writeExcelFile(workbook, fileName.toString());
			sourceFiles.add(fileName.toString());
		}
		Calendar current = Calendar.getInstance();
		current.setTime(fgStockNirvana.getTransDate());
		// Providing input files with full path
		StringBuffer zipFilepath = new StringBuffer(sharePath);
		zipFilepath.append("FG_").append(fileFormatter.format(current.getTime())).append(".zip");
		byte[] buffer = new byte[1024];
		FileOutputStream fout = new FileOutputStream(zipFilepath.toString());
		ZipOutputStream zout = new ZipOutputStream(fout);
		for (int i = 0; i < sourceFiles.size(); i++) {
			File srcFile = new File(sourceFiles.get(i));
			FileInputStream fin = new FileInputStream(srcFile);

			// passing the files to ZipEntry
			zout.putNextEntry(new ZipEntry(srcFile.getName()));
			int length;
			while ((length = fin.read(buffer)) > 0) {
				zout.write(buffer, 0, length);
			}

			zout.closeEntry();
			fin.close();
		}
		zout.close();
		fout.close();

		return zipFilepath.toString();
	}

	/**
	 * update and get export total.
	 * 
	 * @param month - number of month(1(January)-12(December))
	 * @param updateBy - user name of user login.
	 * @return export total.
	 * @throws SQLException if error occur
	 */
	private String getExportTotal(Integer month, String updateBy) throws SQLException {
		Integer exportTotal = (Integer) sqlMap.queryForObject("t_nir_export_total_batch.getExportTotalByMonth", month);

		TNirExportTotal tNirExportTotal = new TNirExportTotal();
		tNirExportTotal.setMonth(month);
		tNirExportTotal.setUpdateBy(updateBy);

		if (exportTotal == null) {
			exportTotal = 1;
			tNirExportTotal.setExportTotal(exportTotal);
			sqlMap.delete("t_nir_export_total_batch.deleteAllExportTotalData");
			sqlMap.insert("t_nir_export_total_batch.insertNewMonth", tNirExportTotal);
		} else {
			exportTotal++;
			tNirExportTotal.setExportTotal(exportTotal);
			sqlMap.update("t_nir_export_total_batch.updateExportTotal", tNirExportTotal);
		}

		// Manage format
		String strExportTotal = exportTotal.toString();
		if (1 == strExportTotal.length()) {
			strExportTotal = "00" + strExportTotal;
		} else if (2 == strExportTotal.length()) {
			strExportTotal = "0" + strExportTotal;
		}

		return strExportTotal;
	}

	/**
	 * Get FG stock to Nirvana data for export report.
	 * 
	 * @param fgStockNirvana - criteria to filter data.
	 * @return list of FG stock to Nirvana data
	 * @throws SQLException - if error occur.
	 */
	@SuppressWarnings("unchecked")
	private List<FgStockNirvanaBatch> getNirB02(FgStockNirvanaBatch fgStockNirvana) throws SQLException {
		fgStockNirvana.setFgStockNirvanaList(sqlMap.queryForList("m_nirvana_sync_master_batch.queryNirB02",
				fgStockNirvana));
		boolean isExist = (null != fgStockNirvana) && (0 < fgStockNirvana.getFgStockNirvanaList().size());
		if (isExist) {
			Calendar transCal = Calendar.getInstance();
			int transMonth;
			Integer accontMonth = Integer.valueOf(getConfigValueByKey(CfgSystemConfig.VALUE_KEY_NIR_FINANCE_M));
			Calendar accontCal = Calendar.getInstance();
			for (FgStockNirvanaBatch rowData : fgStockNirvana.getFgStockNirvanaList()) {
				if (0 < rowData.getFgIn()) {
					rowData.setPrefix("IN");
					rowData.setTransId("PD");
					rowData.setTransactionId("RCFG");
					rowData.setQuantity(rowData.getFgIn());
				} else {
					rowData.setPrefix("OUT");
					rowData.setTransId("SH");
					rowData.setTransactionId("ADFG");
					rowData.setQuantity(Math.abs(rowData.getFgIn()));
				}

				rowData.setInternalReference(fgStockNirvana.getInternalReference());

				// Setting year bested on Accounting Fiscal Year.Accounting Fiscal Year.
				// such as Accounting Fiscal Year = April TransDate is 2014-02-04, Year is 2013.
				transCal.setTime(rowData.getTransDate());
				transMonth = transCal.get(Calendar.MONTH);
				if (accontMonth > transMonth) {
					rowData.setYear(transCal.get(Calendar.YEAR) - 1);
				} else {
					rowData.setYear(transCal.get(Calendar.YEAR));
				}

				accontCal.set(rowData.getYear(), accontMonth, 1);
				rowData.setPeriod(differenceInMonths(accontCal, transCal));
			}
		}
		return fgStockNirvana.getFgStockNirvanaList();
	}

	/**
	 * Calculate to get period of month between 2 date.
	 * 
	 * @param beginningDate - start date
	 * @param endingDate - end date
	 * @return period of month.
	 */
	private static Integer differenceInMonths(Calendar beginningDate, Calendar endingDate) {
		if (beginningDate == null || endingDate == null) {
			return 0;
		}
		final int monthInYear = 12;
		int m1 = (beginningDate.get(Calendar.YEAR) * monthInYear) + beginningDate.get(Calendar.MONTH);
		int m2 = (endingDate.get(Calendar.YEAR) * monthInYear) + endingDate.get(Calendar.MONTH);
		return (m2 - m1) + 1;
	}

	/**
	 * Generate FG stock report to export excel file.
	 * 
	 * @param fgStockNirvana - criteria to get data
	 * @return work book
	 * @throws Exception if occur error
	 */
	private Workbook generateExcelFile(FgStockNirvanaBatch fgStockNirvana) throws Exception {
		List<FgStockNirvanaBatch> data = getNirB02(fgStockNirvana);
		Workbook workbook = new HSSFWorkbook();
		workbook = buildExcelfile(workbook, data);

		return workbook;
	}

	/**
	 * Create excel file.
	 * 
	 * @param workbook - work book
	 * @param data - list of FG stock data.
	 * @return work flow
	 * @throws IOException - occur error.
	 */
	private Workbook buildExcelfile(Workbook workbook, List<FgStockNirvanaBatch> data) throws IOException {
		final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

		// create style
		CellStyle txtCenterStyle = createStyle(workbook);
		txtCenterStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		CellStyle txtLeftCenStyle = createStyle(workbook);
		txtLeftCenStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		CellStyle numberStyle = createStyle(workbook);
		numberStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		numberStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));

		HSSFSheet sheet = (HSSFSheet) workbook.createSheet("FG Stock Nivana");
		HSSFRow rowHSSF;
		FgStockNirvanaBatch detail = null;
		for (int row = 0; row < data.size(); row++) {
			detail = data.get(row);
			rowHSSF = sheet.createRow(row);

			createStringCell(rowHSSF, 0, txtLeftCenStyle, detail.getPrefix());
			createStringCell(rowHSSF, 1, txtLeftCenStyle, detail.getTransId());
			createStringCell(rowHSSF, 2, txtLeftCenStyle, detail.getDivisionId());
			createStringCell(rowHSSF, 3, txtLeftCenStyle, detail.getProject());
			createStringCell(rowHSSF, 4, txtCenterStyle, dateFormatter.format(detail.getTransDate()));
			createStringCell(rowHSSF, 5, txtLeftCenStyle, detail.getInternalReference());
			createStringCell(rowHSSF, 6, txtLeftCenStyle, detail.getExternalReference());
			createStringCell(rowHSSF, 7, txtLeftCenStyle, String.valueOf(detail.getYear()));
			createNumbericCell(rowHSSF, 8, numberStyle, detail.getPeriod().toString());
			createStringCell(rowHSSF, 9, txtLeftCenStyle, detail.getTransactionId());
			createStringCell(rowHSSF, 10, txtLeftCenStyle, detail.getWhsId());
			createStringCell(rowHSSF, 11, txtLeftCenStyle, detail.getItemId());
			createStringCell(rowHSSF, 12, txtLeftCenStyle, detail.getUomId());
			createNumbericCell(rowHSSF, 13, numberStyle, detail.getQuantity().toString());
			createNumbericCell(rowHSSF, 14, numberStyle, (detail.getAmount() == null) ? null : detail.getAmount()
					.toString());
			createStringCell(rowHSSF, 15, txtLeftCenStyle, detail.getLotNo());
			createStringCell(rowHSSF, 16, txtLeftCenStyle, detail.getBinId());
			createStringCell(rowHSSF, 17, txtLeftCenStyle, detail.getReference1());
			createStringCell(rowHSSF, 18, txtLeftCenStyle, detail.getReference2());
			createStringCell(rowHSSF, 19, txtLeftCenStyle, detail.getSource());
			createStringCell(rowHSSF, 20, txtLeftCenStyle, detail.getDocumentNo());
		}
		return workbook;
	}

	/**
	 * write file to directory path.
	 * 
	 * @param workbook - work book
	 * @param fileName - directory path file name
	 * @throws IOException - occur error..
	 */
	private void writeExcelFile(Workbook workbook, String fileName) throws IOException {
		// Write the output to a file
		FileOutputStream fileOut = null;
		fileOut = new FileOutputStream(fileName);
		workbook.write(fileOut);
		fileOut.close();
	}

	/**
	 * Create style of cell in excel report.
	 * 
	 * @param workbook - Workbook
	 * @return cell style
	 */
	private CellStyle createStyle(Workbook workbook) {
		final short fontHeight = 11;
		HSSFFont font = (HSSFFont) workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints(fontHeight);

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// set bottom border
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		// set right border
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		// set wrap text
		cellStyle.setWrapText(true);
		// set font
		cellStyle.setFont(font);

		return cellStyle;
	}

	/**
	 * create cell type string for report.
	 * 
	 * @param rowHSSF - row
	 * @param index - index of cell
	 * @param cellStyle - style
	 * @param value - value in cell
	 * @return cell
	 */
	private HSSFCell createStringCell(HSSFRow rowHSSF, Integer index, CellStyle cellStyle, String value) {
		HSSFCell cell = rowHSSF.createCell(index);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
		return cell;
	}

	/**
	 * create cell type number for report.
	 * 
	 * @param rowHSSF - row
	 * @param index - index of cell
	 * @param cellStyle - style
	 * @param value - value in cell
	 * @return cell
	 */
	private HSSFCell createNumbericCell(HSSFRow rowHSSF, Integer index, CellStyle cellStyle, String value) {
		HSSFCell cell = rowHSSF.createCell(index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellStyle(cellStyle);
		if (value != null) {
			if (Double.parseDouble(value) != 0.0) {
				cell.setCellValue(Double.parseDouble(value));
			}
		} else {
			cell.setCellValue("");
		}
		return cell;
	}

	/**
	 * Delete file according path of file. If path file is folder, delete all file in folder.
	 * 
	 * @param pathFile - path url of file.
	 */
	private void deleteFiles(String pathFile) {
		File file = new File(pathFile);
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				f.delete();
			}
		} else {
			file.delete();
		}
	}

	@Override
	public void preProcessing() throws Exception {
		// MERGE m_batch_control
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 1);
		param.put("runby", getExecutedBy());
		sqlMap.insert("m_batch_control.insertMBatchControl", param);
	}

	@Override
	public void postException() throws Exception {
		// update m_batch_controlin case fail
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 2);
		param.put("runby", getExecutedBy());
		sqlMap.update("m_batch_control.updateMBatchControl", param);
	}
}
