package th.co.nttdata.tki.excel;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.TFG;
import th.co.nttdata.tki.bean.TFGStock;

public class FNG_R02ExcelView extends AbstractExcelView {

	@SuppressWarnings("unchecked")
	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook) throws Exception {

		TFG tfg = (TFG) model.get("tfg");
		MCustomer mCustomer = (MCustomer) model.get("customer");
		Map<Integer, String> monthMap = (Map<Integer, String>) model.get("monthMap");
		Map<Integer, String> yearMap = (Map<Integer, String>) model.get("yearMap");

		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) 11);
		HSSFFont fontHD = workbook.createFont();
		fontHD.setFontName("Calibri");
		fontHD.setFontHeightInPoints((short)11);
		fontHD.setBoldweight(Font.BOLDWEIGHT_BOLD);

		Style criteriaStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setFont(fontHD);
		Style fstHDRStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setTopBorder(
				CellStyle.BORDER_MEDIUM)
				.setBottomBorder()
				.setRightBorder(CellStyle.BORDER_MEDIUM)
				.setBgColor();
		Style sndHDRStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder(
				CellStyle.BORDER_MEDIUM)
				.setRightBorder()
				.setBgColor();
		Style lastSndHDRStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder(
				CellStyle.BORDER_MEDIUM)
				.setRightBorder(CellStyle.BORDER_MEDIUM)
				.setBgColor();
		Style r01c00Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setLeftBorder()
				.setRightBorder()
				.setLeftBorder(CellStyle.BORDER_MEDIUM)
				.setWrapText()
				.setFont(font);
		Style r01c02Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setRightBorder()
				.setFont(font).setWrapText();
		Style r01c03Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFormat(
				"#,##0")
				.setBottomBorder()
				.setRightBorder()
				.setFont(font);
		Style r03c02Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder()
				.setRightBorder()
				.setFont(font);
		Style r07c00Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder(
				CellStyle.BORDER_MEDIUM)
				.setLeftBorder(CellStyle.BORDER_MEDIUM)
				.setRightBorder()
				.setFont(font);
		Style r07c02Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder(
				CellStyle.BORDER_MEDIUM)
				.setRightBorder()
				.setFont(font)
				.setWrapText();
		Style r07c03Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFormat(
				"#,##0")
				.setBottomBorder(CellStyle.BORDER_MEDIUM)
				.setRightBorder()
				.setFont(font);
		Style rcLastStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFormat(
				"#,##0")
				.setBottomBorder()
				.setRightBorder(CellStyle.BORDER_MEDIUM)
				.setFont(font);
		Style r7cLasStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFormat(
				"#,##0")
				.setBottomBorder(CellStyle.BORDER_MEDIUM)
				.setRightBorder(CellStyle.BORDER_MEDIUM)
				.setFont(font);

		HSSFSheet sheet = workbook.getSheetAt(0);

		HSSFRow criteriaRow = sheet.getRow(1);
		HSSFRow fsrHD = sheet.getRow(2);
		HSSFRow sndHD = sheet.getRow(3);

		String customerCriteria= "All";
		if(null != mCustomer && null != mCustomer.getCustomerId() && 0 < mCustomer.getCustomerId()){
			customerCriteria = mCustomer.getCustomerName();
		}
		createCell(workbook, criteriaRow, 0, criteriaStyle).setValue("Customer : " + customerCriteria);

		for (int i = 0; i <= tfg.getEndDay(); i++) {
			createCell(workbook, fsrHD, i + 5, fstHDRStyle);
			if (i != tfg.getEndDay()) {
				createCell(workbook, sndHD, i + 5, sndHDRStyle).setValue("" + i);
			} else {
				createCell(workbook, sndHD, i + 5, lastSndHDRStyle).setValue("" + i);
			}
		}

		sheet.getRow(2).getCell(5).setCellValue(monthMap.get(tfg.getMonth()) + " " + yearMap.get(tfg.getYear()));
		createMergedRegion(sheet, 2, 2, 5, tfg.getEndDay() + 5);
		
		int rowNumber = 4;
		Map<String, List<TFGStock>> listStockMap = tfg.getStocksMap();
		Iterator<String> keyIterator = listStockMap.keySet().iterator();
		List<TFGStock> listStock;
		String key;
		String customer = "";
		String fgNo = "";
		String fgName = "";
		while (keyIterator.hasNext()) {
			HSSFRow fgR00 = sheet.createRow(rowNumber);
			HSSFRow fgR01 = sheet.createRow(rowNumber + 1);
			HSSFRow fgR02 = sheet.createRow(rowNumber + 2);
			HSSFRow fgR03 = sheet.createRow(rowNumber + 3);
			HSSFRow fgR04 = sheet.createRow(rowNumber + 4);
			HSSFRow fgR05 = sheet.createRow(rowNumber + 5);
			HSSFRow fgR06 = sheet.createRow(rowNumber + 6);
			HSSFRow fgR07 = sheet.createRow(rowNumber + 7);

			key = (String) keyIterator.next();
			listStock = (List<TFGStock>) listStockMap.get(key);
			customer = "";
			fgNo = "";
			fgName = "";
			for (TFGStock tf : listStock) {
				if (null != tf.getCustomerCode() && !tf.getCustomerCode().equals("")) {
					customer = tf.getCustomerCode();
				}
				if ((null != tf.getFgNo() && !("").equals(tf.getFgNo()))) {
					fgNo = tf.getFgNo();
					fgName = tf.getFgName();
				}
				if (!("").equals(customer) && !("").equals(fgNo) && !("").equals(fgName)) {
					break;
				}
			}

			createCell(workbook, fgR00, 0, r01c00Style).setValue(customer);
			createCell(workbook, fgR00, 1, r01c00Style).setValue(fgNo);
			createCell(workbook, fgR00, 2, r01c00Style).setValue(fgName);
			createCell(workbook, fgR00, 3, r01c02Style).setValue("Delivery");
			createCell(workbook, fgR00, 4, r01c02Style).setValue("Forecast");
			
			createCell(workbook, fgR01, 0, r01c00Style).setValue(customer);
			createCell(workbook, fgR01, 1, r01c00Style).setValue(fgNo);
			createCell(workbook, fgR01, 2, r01c00Style).setValue(fgName);
			createCell(workbook, fgR01, 3, r01c02Style).setValue("Delivery");
			createCell(workbook, fgR01, 4, r01c02Style).setValue("Plan");

			createCell(workbook, fgR02, 0, r01c00Style).setValue(customer);
			createCell(workbook, fgR02, 1, r01c00Style).setValue(fgNo);
			createCell(workbook, fgR02, 2, r01c00Style).setValue(fgName);
			createCell(workbook, fgR02, 3, r01c02Style).setValue("Delivery");
			createCell(workbook, fgR02, 4, r01c02Style).setValue("Actual");

			createCell(workbook, fgR03, 0, r01c00Style).setValue(customer);
			createCell(workbook, fgR03, 1, r01c00Style).setValue(fgNo);
			createCell(workbook, fgR03, 2, r01c00Style).setValue(fgName);
			createCell(workbook, fgR03, 3, r03c02Style).setValue("Delivery");
			createCell(workbook, fgR03, 4, r03c02Style).setValue("Balance");

			createCell(workbook, fgR04, 0, r01c00Style).setValue(customer);
			createCell(workbook, fgR04, 1, r01c00Style).setValue(fgNo);
			createCell(workbook, fgR04, 2, r01c00Style).setValue(fgName);
			createCell(workbook, fgR04, 3, r01c02Style).setValue("FG");
			createCell(workbook, fgR04, 4, r01c02Style).setValue("In");

			createCell(workbook, fgR05, 0, r01c00Style).setValue(customer);
			createCell(workbook, fgR05, 1, r01c00Style).setValue(fgNo);
			createCell(workbook, fgR05, 2, r01c00Style).setValue(fgName);
			createCell(workbook, fgR05, 3, r01c02Style).setValue("FG");
			createCell(workbook, fgR05, 4, r01c02Style).setValue("Out");

			createCell(workbook, fgR06, 0, r01c00Style).setValue(customer);
			createCell(workbook, fgR06, 1, r01c00Style).setValue(fgNo);
			createCell(workbook, fgR06, 2, r01c00Style).setValue(fgName);
			createCell(workbook, fgR06, 3, r01c02Style).setValue("FG");
			createCell(workbook, fgR06, 4, r01c02Style).setValue("Balance FG");

			createCell(workbook, fgR07, 0, r07c00Style).setValue(customer);
			createCell(workbook, fgR07, 1, r07c00Style).setValue(fgNo);
			createCell(workbook, fgR07, 2, r07c00Style).setValue(fgName);
			createCell(workbook, fgR07, 3, r07c02Style).setValue("FG");
			createCell(workbook, fgR07, 4, r07c02Style).setValue("Adjust");

			for (int day = 5; day <= tfg.getEndDay() + 5; day++) {
				createCell(workbook, fgR00, day, r01c03Style);
				createCell(workbook, fgR01, day, r01c03Style);
				createCell(workbook, fgR02, day, r01c03Style);
				createCell(workbook, fgR03, day, r01c03Style);
				createCell(workbook, fgR04, day, r01c03Style);
				createCell(workbook, fgR05, day, r01c03Style);
				createCell(workbook, fgR06, day, r01c03Style);
				createCell(workbook, fgR07, day, r07c03Style);
			}
			createCell(workbook, fgR00, tfg.getEndDay() + 5, rcLastStyle);
			createCell(workbook, fgR01, tfg.getEndDay() + 5, rcLastStyle);
			createCell(workbook, fgR02, tfg.getEndDay() + 5, rcLastStyle);
			createCell(workbook, fgR03, tfg.getEndDay() + 5, rcLastStyle);
			createCell(workbook, fgR04, tfg.getEndDay() + 5, rcLastStyle);
			createCell(workbook, fgR05, tfg.getEndDay() + 5, rcLastStyle);
			createCell(workbook, fgR06, tfg.getEndDay() + 5, rcLastStyle);
			createCell(workbook, fgR07, tfg.getEndDay() + 5, r7cLasStyle);

			for (int d = 5; d <= tfg.getEndDay() + 5; d++) {
				for (TFGStock stock : listStock) {
					if (stock.getReportDay() == (d - 5)) {
						fgR00.getCell(d).setCellValue((stock.getForcastQty() == null) ? "" : "" + stock.getForcastQty());
						fgR01.getCell(d).setCellValue((stock.getDeliveryQty() == null) ? "" : "" + stock.getDeliveryQty());
						fgR02.getCell(d).setCellValue((stock.getActualQty() == null) ? "" : "" + stock.getActualQty());
						fgR03.getCell(d).setCellValue((stock.getBalanceQty() == null) ? "" : "" + stock.getBalanceQty());
						fgR04.getCell(d).setCellValue((stock.getFgIn() == null) ? "" : "" + stock.getFgIn());
						fgR05.getCell(d).setCellValue((stock.getFgOut() == null) ? "" : "" + stock.getFgOut());
						fgR06.getCell(d).setCellValue((stock.getFgBalance() == null) ? "" : "" + stock.getFgBalance());
						fgR07.getCell(d).setCellValue((stock.getFgAdjust() == null) ? "" : "" + stock.getFgAdjust());
					}
				}
			}
			rowNumber += 8;
		}
		// <!-- Setup 'Print Area'. -->
		workbook.setPrintArea(0, 1, 36, 0, rowNumber);
	}
}
