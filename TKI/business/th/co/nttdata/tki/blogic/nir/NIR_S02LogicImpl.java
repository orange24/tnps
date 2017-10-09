package th.co.nttdata.tki.blogic.nir;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
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
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.FgStockNirvana;
import th.co.nttdata.tki.bean.TNirExportTotal;
import th.co.nttdata.tki.dao.CfgSystemConfigDao;
import th.co.nttdata.tki.dao.MNirvanaSyncMasterDao;
import th.co.nttdata.tki.dao.TNirExportTotalDao;

@Service
public class NIR_S02LogicImpl implements NIR_S02Logic {
	@Autowired
	private CfgSystemConfigDao cfgSystemConfigDao;

	@Autowired
	private MNirvanaSyncMasterDao mNirvanaSyncMasterDao;

	@Autowired
	private TNirExportTotalDao tNirExportTotalDao;

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public boolean isDataMoreThanMaxRow(FgStockNirvana fgStockNirvana) {
		Integer maxRow = cfgSystemConfigDao.getNirMaxRowExportPerfile();
		// count for split file
		Integer countData = mNirvanaSyncMasterDao.countNirR02Rows(fgStockNirvana);
		if (countData > maxRow) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}

	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public Workbook generateExcelFile(FgStockNirvana fgStockNirvana) throws IOException {
		Calendar transCal = Calendar.getInstance();
		String internalReference = internalRefFormatter.format(transCal.getTime())
				+ getExportTotal(transCal.get(Calendar.MONTH) + 1, fgStockNirvana.getUpdateBy());
		fgStockNirvana.setInternalReference(internalReference);

		List<FgStockNirvana> data = getNirR01(fgStockNirvana);
		Workbook workbook = new HSSFWorkbook();
		workbook = buildExcelfile(workbook, data);

		return workbook;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public String generateZipFileExport(FgStockNirvana fgStockNirvana) throws IOException {
		String tempPath = cfgSystemConfigDao.getNirTempPath();

		Integer maxRow = cfgSystemConfigDao.getNirMaxRowExportPerfile();
		// count for split file
		Integer countData = mNirvanaSyncMasterDao.countNirR02Rows(fgStockNirvana);
		Integer countFile = (new BigDecimal(countData).divide(new BigDecimal(maxRow), RoundingMode.UP)).intValue();
		Integer fromRecord = 1;
		Integer toRecord = 0;
		List<String> sourceFiles = new ArrayList<String>();

		Calendar transCal = Calendar.getInstance();
		String internalReference = internalRefFormatter.format(transCal.getTime())
				+ getExportTotal(transCal.get(Calendar.MONTH) + 1, fgStockNirvana.getUpdateBy());
		fgStockNirvana.setInternalReference(internalReference);

		for (int fileCount = 1; fileCount <= countFile; fileCount++) {
			if (1 != fileCount) {
				fromRecord = ((fileCount - 1) * maxRow) + 1;
			}
			toRecord = fileCount * maxRow;

			fgStockNirvana.setFromRecord(fromRecord);
			fgStockNirvana.setToRecord(toRecord);
			List<FgStockNirvana> data = getNirR01(fgStockNirvana);
			File realDir = new File(tempPath);
			if (!realDir.exists()) {
				realDir.mkdir();
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
		String zipPath = bulidZipFile(tempPath, sourceFiles, current);

		return zipPath;
	}

	/**
	 * Get FG stock to Nirvana data for export report.
	 * 
	 * @param fgStockNirvana - criteria to filter data.
	 * @return list of FG stock to Nirvana data
	 */
	private List<FgStockNirvana> getNirR01(FgStockNirvana fgStockNirvana) {
		fgStockNirvana = mNirvanaSyncMasterDao.selectNirR02(fgStockNirvana);
		boolean isExist = (null != fgStockNirvana) && (0 < fgStockNirvana.getFgStockNirvanaList().size());
		if (isExist) {
			Calendar transCal = Calendar.getInstance();
			int transMonth;
			Integer accontMonth = cfgSystemConfigDao.getNirAccountFiscalMonth();
			Calendar accontCal = Calendar.getInstance();
			for (FgStockNirvana rowData : fgStockNirvana.getFgStockNirvanaList()) {
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
				// Such as Accounting Fiscal Year = April TransDate is 2014-02-04, Year is 2013.
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
	 * Create excel file.
	 * 
	 * @param workbook - work book
	 * @param data - list of FG stock data.
	 * @return work flow
	 * @throws IOException - occur error.
	 */
	private Workbook buildExcelfile(Workbook workbook, List<FgStockNirvana> data) throws IOException {
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
		FgStockNirvana detail = null;
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
		try {
			fileOut = new FileOutputStream(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
	 * Zip file.
	 * 
	 * @param sharePath - path to share zip file
	 * @param sourceFiles - list of path files
	 * @param current - calendar of current
	 * @return path of zip file
	 */
	private String bulidZipFile(String sharePath, List<String> sourceFiles, Calendar current) {
		try {
			// Providing input files with full path
			StringBuffer zipFile = new StringBuffer(sharePath);
			zipFile.append("FG_").append(fileFormatter.format(current.getTime())).append(".zip");
			byte[] buffer = new byte[1024];
			FileOutputStream fout = new FileOutputStream(zipFile.toString());
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

			return zipFile.toString();
		} catch (IOException ioe) {
			System.out.println("IOException :" + ioe);
			return null;
		}
	}

	/**
	 * update and get export total.
	 * 
	 * @param month - number of month(1(January)-12(December))
	 * @param updateBy - user name of user login.
	 * @return export total.
	 */
	private String getExportTotal(Integer month, String updateBy) {
		Integer exportTotal = tNirExportTotalDao.getExportTotalByMonth(month);

		TNirExportTotal tNirExportTotal = new TNirExportTotal();
		tNirExportTotal.setMonth(month);
		tNirExportTotal.setUpdateBy(updateBy);

		if (exportTotal == null) {
			exportTotal = 1;
			tNirExportTotal.setExportTotal(exportTotal);
			tNirExportTotalDao.deleteAllExportTotalData();
			tNirExportTotalDao.insertNewMonth(tNirExportTotal);
		} else {
			exportTotal++;
			tNirExportTotal.setExportTotal(exportTotal);
			tNirExportTotalDao.updateExportTotal(tNirExportTotal);
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
}
