package th.co.nttdata.tki.excel;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import th.co.nttdata.tki.bean.MDocControl;
import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.TFGStock;
import th.co.nttdata.tki.excel.AbstractExcelView.Style;

public class MRDC_R16ExcelView extends AbstractExcelView {

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy",
				Locale.US);
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd",
				Locale.US);

		TFGStock tfgStock = (TFGStock) model.get("tfgStock");
		Map<String, TFGStock> wipMap = new LinkedHashMap<String, TFGStock>();
		wipMap = tfgStock.getWipMap();
		MWip wip = (MWip) model.get("mwip");
		List<TFGStock> tfgStockList = tfgStock.getTfgStockList();
		List<MWip> wipList = wip.getWipList();

		DecimalFormat dFormat = new DecimalFormat("00");

		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) 11);
		HSSFFont fontHD = workbook.createFont();
		fontHD.setFontName("Calibri");
		fontHD.setFontHeightInPoints((short) 11);
		fontHD.setBoldweight(Font.BOLDWEIGHT_BOLD);

		Style fstHDRStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER,
				HSSFCellStyle.VERTICAL_CENTER).setFont(fontHD)
				.setTopBorder(CellStyle.BORDER_MEDIUM).setRightBorder()
				.setBgColor();
		Style sndHDRStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER,
				HSSFCellStyle.VERTICAL_CENTER).setFont(fontHD).setTopBorder()
				.setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder()
				.setBgColor();
		Style criteriaStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,
				HSSFCellStyle.VERTICAL_CENTER).setWrapText().setFont(fontHD);
		Style txtLeftStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,
				HSSFCellStyle.VERTICAL_CENTER).setBottomBorder()
				.setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style numberStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,
				HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0")
				.setBottomBorder().setLeftBorder().setRightBorder()
				.setWrapText().setFont(font);
		Style doubleStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,
				HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.00")
				.setBottomBorder().setLeftBorder().setRightBorder()
				.setWrapText().setFont(font);
		Style double4Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,
				HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.0000")
				.setBottomBorder().setLeftBorder().setRightBorder()
				.setWrapText().setFont(font);
		Style txtTotalStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,
				HSSFCellStyle.VERTICAL_CENTER).setWrapText().setFont(fontHD);
		Style numTotalStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,
				HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0")
				.setBottomBorder(CellStyle.BORDER_DOUBLE).setFont(fontHD)
				.setWrapText();
		Style douTotalStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,
				HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.00")
				.setBottomBorder(CellStyle.BORDER_DOUBLE).setFont(fontHD)
				.setWrapText();
		Style dou4TotalStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,
				HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.0000")
				.setBottomBorder(CellStyle.BORDER_DOUBLE).setFont(fontHD)
				.setWrapText();

		Style leftTxtStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,
				HSSFCellStyle.VERTICAL_CENTER)
				.setLeftBorder(CellStyle.BORDER_MEDIUM)
				.setTopBorder(CellStyle.BORDER_MEDIUM)
				.setBottomBorder(CellStyle.BORDER_MEDIUM)
				.setRightBorder(CellStyle.BORDER_MEDIUM).setWrapText()
				.setFont(fontHD);
		Style leftStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER,
				HSSFCellStyle.VERTICAL_CENTER)
				.setLeftBorder(CellStyle.BORDER_MEDIUM)
				.setTopBorder(CellStyle.BORDER_MEDIUM)
				.setBottomBorder(CellStyle.BORDER_MEDIUM)
				.setRightBorder(CellStyle.BORDER_MEDIUM).setWrapText()
				.setFont(fontHD);
		
		HSSFSheet sheet = workbook.getSheetAt(0);
		int firstRow = 4;
		
		HSSFRow criteriaRow = sheet.getRow(firstRow);
		criteriaRow.getCell(0).setCellValue(
				"Period : " + formatter.format(tfgStock.getDateFrom()) + " - "
						+ formatter.format(tfgStock.getDateTo()));

		// <!-- Generate 'Header'. -->
		HSSFRow fstHeader = sheet.getRow(firstRow+1);
		HSSFRow sndHeader = sheet.getRow(firstRow+2);
		int colNumber = 7;
		if (wipList.size() > 0) {
			for (MWip mwip : wipList) {

				createCell(workbook, fstHeader, colNumber, fstHDRStyle);
				createCell(workbook, sndHeader, colNumber, sndHDRStyle)
						.setValue(mwip.getWip());

				colNumber += 1;
			}
			// <!-- Generate: Merge Cells -->
			createMergedRegion(sheet, 3, 3, 7, colNumber - 1);// Process
			sheet.getRow(3).getCell(7).setCellValue("Process");
		}

		createCell(workbook, fstHeader, colNumber, fstHDRStyle).setValue(
				"Sale Unit Price");
		createCell(workbook, sndHeader, colNumber, sndHDRStyle);
		createMergedRegion(sheet, 5, 6, colNumber, colNumber);// Sale Unit Price
		sheet.setColumnWidth(colNumber, 15 * 256);
		colNumber += 1;
		createCell(workbook, fstHeader, colNumber, fstHDRStyle).setValue(
				"Currency");
		createCell(workbook, sndHeader, colNumber, sndHDRStyle);
		createMergedRegion(sheet, 5, 6, colNumber, colNumber);// Sale Unit Price
		sheet.setColumnWidth(colNumber, 15 * 256);
		colNumber += 1;
		createCell(workbook, fstHeader, colNumber, fstHDRStyle).setValue(
				"Material/WIP");
		createCell(workbook, sndHeader, colNumber, sndHDRStyle).setValue("Qty");
		sheet.setColumnWidth(colNumber, 20 * 256);
		colNumber += 1;
		createCell(workbook, fstHeader, colNumber, fstHDRStyle);
		createCell(workbook, sndHeader, colNumber, sndHDRStyle).setValue(
				"Value");
		createMergedRegion(sheet, 5, 5, colNumber - 1, colNumber);// Material/WIP
		sheet.setColumnWidth(colNumber, 20 * 256);
		colNumber += 1;
		createCell(workbook, fstHeader, colNumber, fstHDRStyle).setValue(
				"Product (FG)");
		createCell(workbook, sndHeader, colNumber, sndHDRStyle).setValue("Qty");
		sheet.setColumnWidth(colNumber, 20 * 256);
		colNumber += 1;
		createCell(workbook, fstHeader, colNumber, fstHDRStyle);
		createCell(workbook, sndHeader, colNumber, sndHDRStyle).setValue(
				"Value");
		createMergedRegion(sheet, 5, 5, colNumber - 1, colNumber);// Product
																	// (FG)
		sheet.setColumnWidth(colNumber, 20 * 256);
		colNumber += 1;
		createCell(workbook, fstHeader, colNumber, fstHDRStyle).setValue(
				"Total Stock Value");
		createCell(workbook, sndHeader, colNumber, sndHDRStyle);
		createMergedRegion(sheet, 5, 6, colNumber, colNumber);// Total Stock
																// Value
		sheet.setColumnWidth(colNumber, 20 * 256);

		createMergedRegion(sheet, 0, 0, 0, colNumber);// Name Report

		int rowNum = 4+3;
		int total = 0;
		MWip mwip = null;
		BigDecimal tUnitWeight = new BigDecimal("0");
		BigDecimal tWipQty = new BigDecimal("0");
		BigDecimal tWipVal = new BigDecimal("0");
		BigDecimal tFgQty = new BigDecimal("0");
		BigDecimal tFgVal = new BigDecimal("0");
		BigDecimal tStock = new BigDecimal("0");
		BigDecimal tSaleUnitPrice = new BigDecimal("0");
		List<Integer> totalRs = new ArrayList<Integer>();
		if (tfgStockList.size() > 0) {
			for (TFGStock tfg : tfgStockList) {
				HSSFRow dtRow = sheet.createRow(rowNum);
				createCell(workbook, dtRow, 0, txtLeftStyle).setValue(
						formatter.format(tfg.getReportDate()));
				createCell(workbook, dtRow, 1, txtLeftStyle).setValue(
						tfg.getCustomerCode());
				createCell(workbook, dtRow, 2, txtLeftStyle).setValue(
						tfg.getPartNo());
				createCell(workbook, dtRow, 3, txtLeftStyle).setValue(
						tfg.getPartName());
				createCell(workbook, dtRow, 4, txtLeftStyle).setValue(
						tfg.getMaterial());
				createCell(workbook, dtRow, 5, txtLeftStyle).setValue(
						tfg.getCategory());
				createCell(workbook, dtRow, 6, double4Style).setValue(
						tfg.getUnitWeight());
				tUnitWeight = tUnitWeight.add(new BigDecimal((tfg
						.getUnitWeight() == null ? 0 : tfg.getUnitWeight())));

				colNumber = 7;
				if (wipList.size() > 0) {
					for (int i = 0; i < wipList.size(); i++) {
						mwip = wipList.get(i);
						String key = dateFormatter.format(tfg.getReportDate())
								+ ":" + tfg.getPartId() + ":" + mwip.getWip();
						TFGStock wipDetail = wipMap.get(key);

						if (wipDetail == null) {
							createCell(workbook, dtRow, colNumber, numberStyle);
						} else {
							createCell(workbook, dtRow, colNumber, numberStyle)
									.setValue(wipDetail.getStockWIP(), true);
						}

						total = 0;
						if (wipDetail != null
								&& key.equals(wipDetail.getIdRef())) {
							total += nullToZero(wipDetail.getStockWIP());
						}
						if (totalRs.size() <= i || totalRs.get(i) == null) {
							totalRs.add(total);
						} else {
							totalRs.set(i, totalRs.get(i) + total);
						}

						colNumber++;
					}
				}
				createCell(workbook, dtRow, colNumber++, double4Style)
						.setValue(tfg.getSaleUnitPrice(), true);
				tSaleUnitPrice = tSaleUnitPrice.add(new BigDecimal((tfg
						.getSaleUnitPrice() == null ? 0 : tfg
						.getSaleUnitPrice())));
				createCell(workbook, dtRow, colNumber++, txtLeftStyle)
						.setValue(tfg.getCurrency());
				createCell(workbook, dtRow, colNumber++, numberStyle).setValue(
						tfg.getWipQty(), true);
				createCell(workbook, dtRow, colNumber++, double4Style)
						.setValue(tfg.getWipValue(), true);
				createCell(workbook, dtRow, colNumber++, numberStyle).setValue(
						tfg.getFgQty(), true);
				createCell(workbook, dtRow, colNumber++, double4Style)
						.setValue(tfg.getFgValue(), true);
				createCell(workbook, dtRow, colNumber++, doubleStyle).setValue(
						tfg.getTotalStock(), true);
				tWipQty = tWipQty.add(new BigDecimal(
						(tfg.getWipQty() == null ? 0 : tfg.getWipQty())));
				tWipVal = tWipVal.add(new BigDecimal(
						(tfg.getWipValue() == null ? 0 : tfg.getWipValue())));
				tFgQty = tFgQty.add(new BigDecimal((tfg.getFgQty() == null ? 0
						: tfg.getFgQty())));
				tFgVal = tFgVal.add(new BigDecimal(
						(tfg.getFgValue() == null ? 0 : tfg.getFgValue())));
				tStock = tStock
						.add(new BigDecimal((tfg.getTotalStock() == null ? 0
								: tfg.getTotalStock())));
				rowNum++;
			}
			HSSFRow dtRow = sheet.createRow(rowNum);
			createCell(workbook, dtRow, 0, txtTotalStyle);
			createCell(workbook, dtRow, 1, txtTotalStyle);
			createCell(workbook, dtRow, 2, txtTotalStyle);
			createCell(workbook, dtRow, 3, txtTotalStyle);
			createCell(workbook, dtRow, 4, txtTotalStyle);
			createCell(workbook, dtRow, 5, txtTotalStyle).setValue(
					"Grand Total");
			colNumber = 6;
			createCell(workbook, dtRow, colNumber++, dou4TotalStyle).setValue(
					tUnitWeight, true);
			for (Integer sum : totalRs) {
				createCell(workbook, dtRow, colNumber++, numTotalStyle)
						.setValue(sum, true);
			}
			createCell(workbook, dtRow, colNumber++, dou4TotalStyle).setValue(
					tSaleUnitPrice, true);
			createCell(workbook, dtRow, colNumber++, numTotalStyle);
			createCell(workbook, dtRow, colNumber++, numTotalStyle).setValue(
					tWipQty.doubleValue(), true);
			createCell(workbook, dtRow, colNumber++, dou4TotalStyle).setValue(
					tWipVal.doubleValue(), true);
			createCell(workbook, dtRow, colNumber++, numTotalStyle).setValue(
					tFgQty.doubleValue(), true);
			createCell(workbook, dtRow, colNumber++, dou4TotalStyle).setValue(
					tFgVal.doubleValue(), true);
			createCell(workbook, dtRow, colNumber++, douTotalStyle).setValue(
					tStock.doubleValue(), true);
		}
		

		HSSFRow row1 = sheet.getRow(1);
		HSSFRow row2 = sheet.getRow(2);
		colNumber = colNumber-3;
		MDocControl docControl = (MDocControl) model.get("docControl");
		
		createCell(workbook, row1, colNumber, leftTxtStyle).setValue("DOC.NO."); 
		createCell(workbook, row1, colNumber + 2, leftStyle);
		createMergedRegion(sheet, row1.getRowNum(), row1.getRowNum(), colNumber+1,(colNumber + 2));
		createCell(workbook, row1, colNumber + 1, leftStyle).setValue(docControl.getDocNoR3());
		
		createCell(workbook, row2, colNumber, leftTxtStyle).setValue("REV.NO."); 
		createCell(workbook, row2, colNumber + 2, leftStyle);
		createMergedRegion(sheet, row2.getRowNum(), row2.getRowNum(), colNumber+1, (colNumber + 2));
		createCell(workbook, row2, colNumber + 1, leftStyle).setValue(docControl.getRevDocNoR3());
		
		workbook.setPrintArea(0, 0, colNumber, 0, rowNum);
	}

}
