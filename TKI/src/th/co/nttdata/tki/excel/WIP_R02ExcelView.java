package th.co.nttdata.tki.excel;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.TWip;
import th.co.nttdata.tki.bean.TWipStock;

public class WIP_R02ExcelView extends AbstractExcelView {

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook) throws Exception {

		// Data
		TWip tWip = (TWip) model.get("tWipStockList");
		MCustomer mCust = (MCustomer)model.get("customer");
		MPart mPart = (MPart)model.get("part");

		NumberFormat formatter = new DecimalFormat("#,##0");
		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) 11);

		Style fstHDRStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFont(
				FONT_HEADR)
				.setTopBorder(CellStyle.BORDER_MEDIUM)
				.setRightBorder()
				.setBgColor()
				.setWrapText();
		Style sndHDRStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFont(
				FONT_HEADR)
				.setTopBorder()
				.setBottomBorder(CellStyle.BORDER_MEDIUM)
				.setRightBorder()
				.setBgColor()
				.setWrapText();

		Style detailStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setFont(font);
		Style r01Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder()
				.setRightBorder()
				.setFont(font)
				.setWrapText();
		Style r06Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder(
				CellStyle.BORDER_MEDIUM)
				.setRightBorder()
				.setFont(font)
				.setWrapText();
		Style txtLeftStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder()
				.setRightBorder()
				.setLeftBorder()
				.setFont(font)
				.setWrapText();
		Style r01c01Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder()
				.setRightBorder()
				.setFont(font)
				.setWrapText();
		Style r06c00Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder(
				CellStyle.BORDER_MEDIUM)
				.setLeftBorder()
				.setRightBorder()
				.setFont(font)
				.setWrapText();
		Style r06c01Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder(
				CellStyle.BORDER_MEDIUM)
				.setRightBorder()
				.setFont(font)
				.setWrapText();

		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow row = sheet.createRow(2);
		
		//Detail Top Header
		String customer = "All Customer";
		String partNo = "All Part";
		String partName = "All Part";
		if(null != mCust){
			customer = mCust.getCustomerName();
		}
		if(null != mPart){
			partNo = mPart.getPartNo();
			partName = mPart.getPartName();
		}
		createCell(workbook, row, 0, detailStyle).setValue("Customer : " + customer);
		createCell(workbook, row, 5, detailStyle).setValue("Part No : " + partNo);
		createCell(workbook, row, 10, detailStyle).setValue("Part Name : " + partName);
		
		//head WIP Stock
		HSSFRow fstHeader = sheet.getRow(4);
		HSSFRow sndHeader = sheet.getRow(5);
		int colNumber = 4;
		for (int i = 0; i <= tWip.getEndDay(); i++) {
			createCell(workbook, fstHeader, colNumber, fstHDRStyle);
			createCell(workbook, sndHeader, colNumber, sndHDRStyle);
			sheet.getRow(5).getCell(colNumber).setCellValue((i == 0) ? 0 : i);
			colNumber += 1;
		}
		createMergedRegion(sheet, 4, 4, 4, tWip.getEndDay() + 1);
		fstHeader.getCell(4).setCellValue(new DateFormatSymbols().getMonths()[tWip.getMonth()] + " " + tWip.getYear());

		int rowNumber = 6;
		int lastRow1 = rowNumber + 2;
		int lastRow2 = rowNumber + 3;
		HSSFRow lastHRow1 = sheet.createRow(lastRow1);
		HSSFRow lastHRow2 = sheet.createRow(lastRow2);
		Map<String, List<TWipStock>> stockMap = tWip.getStockMap();
		String customerCode = "";
		String part = "";
		String wip = "";

		for (List<TWipStock> stockList : stockMap.values()) {

			HSSFRow fgR01 = sheet.createRow(rowNumber);
			HSSFRow fgR02 = sheet.createRow(rowNumber + 1);
			HSSFRow fgR03 = sheet.createRow(rowNumber + 2);
			HSSFRow fgR04 = sheet.createRow(rowNumber + 3);
			HSSFRow fgR05 = sheet.createRow(rowNumber + 4);
			HSSFRow fgR06 = sheet.createRow(rowNumber + 5);
			lastHRow1 = sheet.createRow(rowNumber + 8);
			lastHRow2 = sheet.createRow(rowNumber + 9);
			lastRow1 = rowNumber + 8;
			lastRow2 = rowNumber + 9;
			
			customerCode = stockList.get(0).getCustomer();
			part = stockList.get(0).getPartNo() + ":" + stockList.get(0).getPartName();
			wip = stockList.get(0).getWipOrder() + ". " + stockList.get(0).getWipName();

			createCell(workbook, fgR01, 0, txtLeftStyle).setValue(customerCode);
			createCell(workbook, fgR01, 1, txtLeftStyle).setValue(part);
			createCell(workbook, fgR01, 2, txtLeftStyle).setValue(wip);
			createCell(workbook, fgR01, 3, r01Style).setValue("OK");

			createCell(workbook, fgR02, 0, txtLeftStyle).setValue(customerCode);
			createCell(workbook, fgR02, 1, txtLeftStyle).setValue(part);
			createCell(workbook, fgR02, 2, txtLeftStyle).setValue(wip);
			createCell(workbook, fgR02, 3, r01Style).setValue("Pending");

			createCell(workbook, fgR03, 0, txtLeftStyle).setValue(customerCode);
			createCell(workbook, fgR03, 1, txtLeftStyle).setValue(part);
			createCell(workbook, fgR03, 2, txtLeftStyle).setValue(wip);
			createCell(workbook, fgR03, 3, r01Style).setValue("NG");

			createCell(workbook, fgR04, 0, txtLeftStyle).setValue(customerCode);
			createCell(workbook, fgR04, 1, txtLeftStyle).setValue(part);
			createCell(workbook, fgR04, 2, txtLeftStyle).setValue(wip);
			createCell(workbook, fgR04, 3, r01Style).setValue("NextWIP");

			createCell(workbook, fgR05, 0, txtLeftStyle).setValue(customerCode);
			createCell(workbook, fgR05, 1, txtLeftStyle).setValue(part);
			createCell(workbook, fgR05, 2, txtLeftStyle).setValue(wip);
			createCell(workbook, fgR05, 3, r01Style).setValue("Stock");

			createCell(workbook, fgR06, 0, r06c00Style).setValue(customerCode);
			createCell(workbook, fgR06, 1, r06c00Style).setValue(part);
			createCell(workbook, fgR06, 2, r06c00Style).setValue(wip);
			createCell(workbook, fgR06, 3, r06Style).setValue("StockAdj.");

			colNumber = 4;
			for (int i = 0; i <= tWip.getEndDay(); i++) {
				for (TWipStock tWipStock : stockList) {
					if (tWipStock.getReportDay() == i) {
						createCell(workbook, fgR01, colNumber, r01c01Style);
						createCell(workbook, fgR02, colNumber, r01c01Style);
						createCell(workbook, fgR03, colNumber, r01c01Style);
						createCell(workbook, fgR04, colNumber, r01c01Style);
						createCell(workbook, fgR05, colNumber, r01c01Style);
						createCell(workbook, fgR06, colNumber, r06c01Style);

						sheet.getRow(rowNumber)
								.getCell(colNumber)
								.setCellValue(
										(tWipStock.getOk() == null) ? ""
												: String.valueOf(formatter.format(tWipStock.getOk())));
						sheet.getRow(rowNumber + 1)
								.getCell(colNumber)
								.setCellValue(
										(tWipStock.getPd() == null) ? ""
												: String.valueOf(formatter.format(tWipStock.getPd())));
						sheet.getRow(rowNumber + 2)
								.getCell(colNumber)
								.setCellValue(
										(tWipStock.getNg() == null) ? ""
												: String.valueOf(formatter.format(tWipStock.getNg())));
						sheet.getRow(rowNumber + 3)
								.getCell(colNumber)
								.setCellValue(
										(tWipStock.getNextWIPQty() == null) ? ""
												: String.valueOf(formatter.format(tWipStock.getNextWIPQty())));
						sheet.getRow(rowNumber + 4)
								.getCell(colNumber)
								.setCellValue(
										(tWipStock.getCurrentStock() == null) ? ""
												: String.valueOf(formatter.format(tWipStock.getCurrentStock())));
						sheet.getRow(rowNumber + 5)
								.getCell(colNumber)
								.setCellValue(
										(tWipStock.getAdjustStock() == null) ? ""
												: String.valueOf(formatter.format(tWipStock.getAdjustStock())));

						break;
					} else {
						createCell(workbook, fgR01, colNumber, r01c01Style);
						createCell(workbook, fgR02, colNumber, r01c01Style);
						createCell(workbook, fgR03, colNumber, r01c01Style);
						createCell(workbook, fgR04, colNumber, r01c01Style);
						createCell(workbook, fgR06, colNumber, r06c01Style);
						if (i == 0 && tWipStock.getReportDay() == 1) {
							createCell(workbook, fgR05, colNumber, r01c01Style);
							sheet.getRow(rowNumber + 4)
									.getCell(colNumber)
									.setCellValue(
											(tWipStock.getPrevStock() == null) ? ""
													: String.valueOf(formatter.format(tWipStock.getPrevStock())));
							break;
						} else {
							createCell(workbook, fgR05, colNumber, r01c01Style);
						}
					}
				}
				colNumber += 1;
			}

			rowNumber += 6;
		}

		// Footer
		createMergedRegion(sheet, lastRow1, lastRow1, 0, 5);
		createCell(workbook, lastHRow1, 0, detailStyle).setValue("Stock = Stock (N-1) + OK - Next WIP.");
		createMergedRegion(sheet, lastRow2, lastRow2, 0, 5);
		createCell(workbook, lastHRow2, 0, detailStyle).setValue("Next WIP = (OK + Pending + NG ) of next process.");
	}

}
