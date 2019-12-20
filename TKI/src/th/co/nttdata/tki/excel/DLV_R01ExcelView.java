package th.co.nttdata.tki.excel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import org.springframework.beans.factory.annotation.Autowired;

import th.co.nttdata.tki.bean.MDocControl;
import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.bean.TDeliveryPlanDate;
import th.co.nttdata.tki.blogic.cfg.DOC_S01Logic;

public class DLV_R01ExcelView extends AbstractExcelView {
	
	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {

		TDeliveryPlan deliveryPlan = (TDeliveryPlan) model.get("deliveryPlan");
		MDocControl docControl = (MDocControl) model.get("docControl");
		
		final SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy",
				Locale.US);
		// set Font Header
		HSSFFont fontHD = workbook.createFont();
		fontHD.setFontName("Calibri");
		fontHD.setFontHeightInPoints((short) 11);
		fontHD.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// <!-- Assign: CellStyle. -->
		Style fstHDRStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER,
				HSSFCellStyle.ALIGN_CENTER).setTopBorder(CellStyle.BORDER_THIN)
				.setBottomBorder(CellStyle.BORDER_THIN).setRightBorder()
				.setBgColor();
		Style cusStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,
				HSSFCellStyle.ALIGN_CENTER).setFont(fontHD);
		Style r01c00Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,
				HSSFCellStyle.ALIGN_LEFT).setLeftBorder().setRightBorder()
				.setWhiteBgColor().setWrapText();
		Style r01c01Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,
				HSSFCellStyle.ALIGN_LEFT).setBottomBorder().setLeftBorder()
				.setRightBorder();
		Style rFMc02Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,
				HSSFCellStyle.ALIGN_RIGHT).setFormat("#,##0").setBottomBorder()
				.setRightBorder();
		Style nFMc02Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,
				HSSFCellStyle.ALIGN_RIGHT).setBottomBorder().setRightBorder();
		Style rHDc11Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,
				HSSFCellStyle.ALIGN_LEFT).setBottomBorder(
				CellStyle.BORDER_MEDIUM).setRightBorder();
		Style r00c11Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,
				HSSFCellStyle.ALIGN_RIGHT).setLeftBorder()
				.setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder();
		Style r01c11Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,
				HSSFCellStyle.ALIGN_RIGHT).setBottomBorder(
				CellStyle.BORDER_MEDIUM).setRightBorder();
		// box sign
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
		// <!-- Get 'First' Sheet. -->
		HSSFSheet sheet = workbook.getSheetAt(0);

		// <!-- Generate 'Header'. -->
		HSSFRow titleHeader = sheet.getRow(0);
		HSSFRow fstHeader = sheet.getRow(5);
		HSSFRow sndHeader = sheet.getRow(6);
		int d = deliveryPlan.getDayOfMonth();// day of month

		// box sign
		HSSFRow row1 = sheet.createRow(1);
		HSSFRow row2 = sheet.createRow(2);
		HSSFRow row3 = sheet.createRow(3);
		HSSFRow row4 = sheet.getRow(4);
		String lastUpdate = "";
		String updateBy = "";
		int minColumn = (d + 4) - 6+1// 4 = column text
		, maxColumn = d + 4;
		// row1 and row2
		for (int h = minColumn; h < maxColumn; h = h + 3) {
			if (h == minColumn) {
				createCell(workbook, row1, h, leftTxtStyle).setValue("DOC.NO."); // col:
																					// 1,
																					// rwCnt:1
			} else {
				createCell(workbook, row1, h, leftStyle)
						.setValue(docControl.getDocNo()); // col: 2, rwCnt:1
			}
			createCell(workbook, row1, h + 1, leftStyle);
			createCell(workbook, row1, h + 2, leftStyle);
			createMergedRegion(sheet, row1.getRowNum(), row1.getRowNum(), h,
					(h + 2));
			if (h == minColumn) {
				createCell(workbook, row2, h, leftTxtStyle).setValue("REV.NO."); // col:
																					// 1,
																					// rwCnt:2
			} else {
				createCell(workbook, row2, h, leftStyle).setValue(docControl.getRevDocNo()); // col:
																		// 2,
																		// rwCnt:2
			}
			createCell(workbook, row2, h + 1, leftStyle);
			createCell(workbook, row2, h + 2, leftStyle);
			createMergedRegion(sheet, row2.getRowNum(), row2.getRowNum(), h,
					(h + 2));
		}
		// row3 and row4
		minColumn = (d + 4) - 12;
		for (int h = minColumn+1; h < maxColumn; h = h + 3) {
			createCell(workbook, row3, h, leftStyle);
			createCell(workbook, row3, h + 1, leftStyle);
			createCell(workbook, row3, h + 2, leftStyle);
			createMergedRegion(sheet, row3.getRowNum(), row3.getRowNum(), h,
					(h + 2));
			createCell(workbook, row4, h, leftStyle);
			createCell(workbook, row4, h + 1, leftStyle);
			createCell(workbook, row4, h + 2, leftStyle);
			createMergedRegion(sheet, row4.getRowNum(), row4.getRowNum(), h,
					(h + 2));

			if (h == minColumn+1) {
				row3.getCell(h).setCellValue("ACKNOWLEDGE SIGN");
			} else if (h == minColumn + 3+1) {
				row3.getCell(h).setCellValue("APPROVED BY");
			} else if (h == minColumn + 6+1) {
				row3.getCell(h).setCellValue("CHECKED BY");
			} else if (h == minColumn + 9+1) {
				row3.getCell(h).setCellValue("PLANING BY");
				Calendar now = new GregorianCalendar(Locale.US);
				lastUpdate = dFormat.format(now.getTime());
				updateBy = deliveryPlan.getUpdateBy();
				row4.getCell(h).setCellValue(updateBy + "\n" + lastUpdate);
			}
		}

		// customer
		createCell(workbook, row4, 0, cusStyle).setValue(
				"Customer : " + deliveryPlan.getCustomerName());
		// <!-- set style header day -->
		if (d < 31) {
			for (int i = 35; i > d + 4; i--) {
				titleHeader.getCell(i).setCellValue("");
				titleHeader.getCell(i).setCellStyle(workbook.createCellStyle());
				fstHeader.getCell(i).setCellValue("");
				fstHeader.getCell(i).setCellStyle(workbook.createCellStyle());
				sndHeader.getCell(i).setCellValue("");
				sndHeader.getCell(i).setCellStyle(workbook.createCellStyle());
			}
			createCell(workbook, fstHeader, d + 4, fstHDRStyle);
		}

		// <!-- Generate: Merge Cells -->
		createMergedRegion(sheet, 0, 0, 0, d + 4);
		createMergedRegion(sheet, 5, 5, 3, d + 4);
		fstHeader.getCell(2).setCellValue(
				"Revise " + deliveryPlan.getRevision());
		fstHeader.getCell(3).setCellValue(
				deliveryPlan.getMonthName() + " " + deliveryPlan.getYear());

		// <!-- Create Rows. -->
		int dayNumber = 5;
		int oldFgId = 0;
		int rowNumber = 7;
		int prevBdeli = 0;
		int prevBOrder = 0;
		int E = 8;
		int AI = 8;
		int rowNum = 0;
		int backOrder0 = 0;
		boolean bFlag = false;
		String balanceOrder0 = "";
		String balanceDeliveryQty = "";
		String balanceOrderQty = "";

		HSSFRow fstRow1 = null;
		HSSFRow fstRow2 = null;
		HSSFRow fstRow3 = null;
		HSSFRow fstRow4 = null;
		HSSFRow fstRow5 = null;
		HSSFRow fstRow6 = null;
		HSSFRow fstRow7 = null;
		HSSFRow fstRow8 = null;
		HSSFRow fstRow9 = null;
		HSSFRow fstRow10 = null;
		HSSFRow fstRow11 = null;

		// <!-- set Value of dateList -->
		List<TDeliveryPlanDate> dList = deliveryPlan.getDateList();
		TDeliveryPlanDate[] details = dList.toArray(new TDeliveryPlanDate[dList
				.size()]);
		boolean firstRowOfEachFg = true;
		for (int i = -1, cntFG = 1, cnt = 0, max = dList.size(); cnt < max; cnt++) {
			TDeliveryPlanDate detail = details[++i < max ? i : max - 1];

			if (oldFgId == 0) {// Initial Row
				oldFgId = detail.getFgId();

				fstRow1 = sheet.createRow(rowNumber++);
				fstRow2 = sheet.createRow(rowNumber++);
				fstRow3 = sheet.createRow(rowNumber++);
				fstRow4 = sheet.createRow(rowNumber++);
				fstRow5 = sheet.createRow(rowNumber++);
				fstRow6 = sheet.createRow(rowNumber++);
				fstRow7 = sheet.createRow(rowNumber++);
				fstRow8 = sheet.createRow(rowNumber++);
				fstRow9 = sheet.createRow(rowNumber++);
				fstRow10 = sheet.createRow(rowNumber++);
				fstRow11 = sheet.createRow(rowNumber++);

				Cell firstCell = createCell(workbook, fstRow1, 0, r01c00Style);
				createCell(workbook, fstRow2, 0, r01c00Style);
				createCell(workbook, fstRow3, 0, r01c00Style);
				createCell(workbook, fstRow4, 0, r01c00Style);
				createCell(workbook, fstRow5, 0, r01c00Style);
				createCell(workbook, fstRow6, 0, r01c00Style);
				createCell(workbook, fstRow7, 0, r01c00Style);
				createCell(workbook, fstRow8, 0, r01c00Style);
				createCell(workbook, fstRow9, 0, r01c00Style);
				createCell(workbook, fstRow10, 0, r01c00Style);
				createCell(workbook, fstRow11, 0, r00c11Style);

				// <!-- Generate: Merge Cells -->
				createMergedRegion(sheet, fstRow1.getRowNum(),
						fstRow11.getRowNum(), 0, 0);
				firstCell.setValue(detail.getFgName());

				Cell stCell = createCell(workbook, fstRow1, 1, r01c00Style);
				createCell(workbook, fstRow2, 1, r01c00Style);
				createCell(workbook, fstRow3, 1, r01c00Style);
				createCell(workbook, fstRow4, 1, r01c00Style);
				createCell(workbook, fstRow5, 1, r01c00Style);
				createCell(workbook, fstRow6, 1, r01c00Style);
				createCell(workbook, fstRow7, 1, r01c00Style);
				createCell(workbook, fstRow8, 1, r01c00Style);
				createCell(workbook, fstRow9, 1, r01c00Style);
				createCell(workbook, fstRow10, 1, r01c00Style);
				createCell(workbook, fstRow11, 1, r00c11Style);

				// <!-- Generate: Merge Cells -->
				createMergedRegion(sheet, fstRow1.getRowNum(),
						fstRow11.getRowNum(), 1, 1);
				stCell.setValue(detail.getFgNo());
				
				
				// <!-- check BalanceOrder(n-1)-->
				bFlag = true;
				if (bFlag == true) {
					if (detail.getBalanceOrderQty() == null) {
						// In case of 'no data'.
						balanceOrder0 = "";
						backOrder0 = 0;
					} else if (detail.getBalanceOrderQty() < 0) { 
						// In case of 'negative data'.
						backOrder0 = detail.getBalanceOrderQty();
						balanceOrder0 = "("
								+ String.format("%1$,d",
										Math.abs(detail.getBalanceOrderQty()))
								+ ")";
					} else { // In case of 'zero' & 'positive data'.
						backOrder0 = detail.getBalanceOrderQty();
						balanceOrder0 = String.format("%1$,d",
								detail.getBalanceOrderQty());
					}
					bFlag = false;
					continue;
				}

			}
			if (oldFgId != detail.getFgId()) {
				oldFgId = detail.getFgId();
				dayNumber = 4;
				rowNum = rowNum + 11;
				cntFG++;
				prevBdeli = 0;

				fstRow1 = sheet.createRow(rowNumber++);
				fstRow2 = sheet.createRow(rowNumber++);
				fstRow3 = sheet.createRow(rowNumber++);
				fstRow4 = sheet.createRow(rowNumber++);
				fstRow5 = sheet.createRow(rowNumber++);
				fstRow6 = sheet.createRow(rowNumber++);
				fstRow7 = sheet.createRow(rowNumber++);
				fstRow8 = sheet.createRow(rowNumber++);
				fstRow9 = sheet.createRow(rowNumber++);
				fstRow10 = sheet.createRow(rowNumber++);
				fstRow11 = sheet.createRow(rowNumber++);

				Cell firstCell = createCell(workbook, fstRow1, 0, r01c00Style);
				createCell(workbook, fstRow2, 0, r01c00Style);
				createCell(workbook, fstRow3, 0, r01c00Style);
				createCell(workbook, fstRow4, 0, r01c00Style);
				createCell(workbook, fstRow5, 0, r01c00Style);
				createCell(workbook, fstRow6, 0, r01c00Style);
				createCell(workbook, fstRow7, 0, r01c00Style);
				createCell(workbook, fstRow8, 0, r01c00Style);
				createCell(workbook, fstRow9, 0, r01c00Style);
				createCell(workbook, fstRow10, 0, r01c00Style);
				createCell(workbook, fstRow11, 0, r00c11Style);

				// <!-- Generate: Merge Cells -->
				createMergedRegion(sheet, fstRow1.getRowNum(),
						fstRow11.getRowNum(), 0, 0);
				firstCell.setValue(detail.getFgName() );
				
				Cell stCell = createCell(workbook, fstRow1, 1, r01c00Style);
				createCell(workbook, fstRow2, 1, r01c00Style);
				createCell(workbook, fstRow3, 1, r01c00Style);
				createCell(workbook, fstRow4, 1, r01c00Style);
				createCell(workbook, fstRow5, 1, r01c00Style);
				createCell(workbook, fstRow6, 1, r01c00Style);
				createCell(workbook, fstRow7, 1, r01c00Style);
				createCell(workbook, fstRow8, 1, r01c00Style);
				createCell(workbook, fstRow9, 1, r01c00Style);
				createCell(workbook, fstRow10, 1, r01c00Style);
				createCell(workbook, fstRow11, 1, r00c11Style);
				createMergedRegion(sheet, fstRow1.getRowNum(),
						fstRow11.getRowNum(), 1, 1);
				stCell.setValue(detail.getFgNo());
				
				firstRowOfEachFg = true;

				// <!-- check BalanceOrder(n-1)-->
				bFlag = true;
				if (bFlag == true) {
					if (detail.getBalanceOrderQty() == null) { // In case of 'no
																// data'.
						balanceOrder0 = "";
						backOrder0 = 0;
					} else if (detail.getBalanceOrderQty() < 0) { // In case of
																	// 'negative
																	// data'.
						backOrder0 = detail.getBalanceOrderQty();
						balanceOrder0 = "("
								+ String.format("%1$,d",
										Math.abs(detail.getBalanceOrderQty()))
								+ ")";
					} else { // In case of 'zero' & 'positive data'.
						backOrder0 = detail.getBalanceOrderQty();
						balanceOrder0 = String.format("%1$,d",
								detail.getBalanceOrderQty());
					}
					bFlag = false;
				}
			}

			createCell(workbook, fstRow1, 1+1, r01c01Style).setValue("Forecast");
			createCell(workbook, fstRow1, 2+1, rFMc02Style);
			fstRow1.getCell(2+1).setCellFormula(
					"IF(SUM(F" + (E + 0 + rowNum) + ":AJ" + (AI + 0 + rowNum)
							+ ")=0,0,SUM(F" + (E + 0 + rowNum) + ":AJ"
							+ (AI + 0 + rowNum) + "))");
			createCell(workbook, fstRow1, 3+1, rFMc02Style).setValue("");
			createCell(workbook, fstRow1, dayNumber, rFMc02Style).setValue(
					detail.getForCastQty(), true);

			createCell(workbook, fstRow2, 1+1, r01c01Style)
					.setValue("Cust. Req.");
			createCell(workbook, fstRow2, 2+1, rFMc02Style);
			fstRow2.getCell(2+1).setCellFormula(
					"IF(SUM(F" + (E + 1 + rowNum) + ":AJ" + (AI + 1 + rowNum)
							+ ")=0,0,SUM(F" + (E + 1 + rowNum) + ":AJ"
							+ (AI + 1 + rowNum) + "))");
			createCell(workbook, fstRow2, 3+1, rFMc02Style).setValue("");
			createCell(workbook, fstRow2, dayNumber, rFMc02Style).setValue(
					detail.getCustReqQty(), true);

			createCell(workbook, fstRow3, 1+1, r01c01Style).setValue("Commit");
			createCell(workbook, fstRow3, 2+1, rFMc02Style);
			fstRow3.getCell(2+1).setCellFormula(
					"IF(SUM(F" + (E + 2 + rowNum) + ":AJ" + (AI + 2 + rowNum)
							+ ")=0,0,SUM(F" + (E + 2 + rowNum) + ":AJ"
							+ (AI + 2 + rowNum) + "))");
			createCell(workbook, fstRow3, 3+1, rFMc02Style).setValue("");
			createCell(workbook, fstRow3, dayNumber, rFMc02Style).setValue(
					detail.getTkiCommitQty(), true);

			createCell(workbook, fstRow4, 1+1, r01c01Style).setValue(
					"Production Plan");
			createCell(workbook, fstRow4, 2+1, rFMc02Style);
			fstRow4.getCell(2+1).setCellFormula(
					"IF(SUM(F" + (E + 3 + rowNum) + ":AJ" + (AI + 3 + rowNum)
							+ ")=0,0,SUM(F" + (E + 3 + rowNum) + ":AJ"
							+ (AI + 3 + rowNum) + "))");
			createCell(workbook, fstRow4, 3+1, rFMc02Style).setValue("");
			createCell(workbook, fstRow4, dayNumber, rFMc02Style).setValue(
					detail.getProductionQty(), true);

			createCell(workbook, fstRow5, 1+1, r01c01Style).setValue(
					"Delivery Plan (Normal)");
			createCell(workbook, fstRow5, 2+1, rFMc02Style);
			fstRow5.getCell(2+1).setCellFormula(
					"IF(SUM(F" + (E + 4 + rowNum) + ":AJ" + (AI + 4 + rowNum)
							+ ")=0,0,SUM(F" + (E + 4 + rowNum) + ":AJ"
							+ (AI + 4 + rowNum) + "))");
			createCell(workbook, fstRow5, 3+1, rFMc02Style).setValue("");
			createCell(workbook, fstRow5, dayNumber, rFMc02Style).setValue(
					detail.getDeliveryQtyNormal(), true);

			createCell(workbook, fstRow6, 1+1, r01c01Style).setValue(
					"Delivery Plan (Back)");
			createCell(workbook, fstRow6, 2+1, rFMc02Style);
			fstRow6.getCell(2+1).setCellFormula(
					"IF(" + "SUM(" + backOrder0 + ",F" + (E + 5 + rowNum)
							+ ":AJ" + (AI + 5 + rowNum) + ")<=0,"
							+ " TEXT(ABS(SUM(" + backOrder0 + ",F"
							+ (E + 5 + rowNum) + ":AJ" + (AI + 5 + rowNum)
							+ ")),\"#,##0\")," + " \"(\" & TEXT(SUM("
							+ backOrder0 + ",F" + (E + 5 + rowNum) + ":AJ"
							+ (AI + 5 + rowNum) + "),\"#,##0\") & \")\")");

			// fstRow6.getCell(2).setCellFormula("IF(SUM(E"+(E+5+rowNum)+":AI"+(AI+5+rowNum)+")=0,0,SUM(E"+(E+5+rowNum)+":AI"+(AI+5+rowNum)+"))");
			if (backOrder0 <= 0) {
				String backOrder0Str = balanceOrder0.replace("(", "");
				backOrder0Str = backOrder0Str.replace(")", "");
				createCell(workbook, fstRow6, 3+1, nFMc02Style).setValue(
						backOrder0Str);
			} else {
				createCell(workbook, fstRow6, 3+1, rFMc02Style).setValue(
						"(" + backOrder0 + ")");
			}

			createCell(workbook, fstRow6, dayNumber, rFMc02Style).setValue(
					detail.getDeliveryQtyBack(), true);

			createCell(workbook, fstRow7, 1+1, r01c01Style).setValue(
					"Delivery Plan (Total)");
			createCell(workbook, fstRow7, 2+1, rFMc02Style);
			fstRow7.getCell(2+1).setCellFormula(
					"IF(SUM(F" + (E + 6 + rowNum) + ":AJ" + (AI + 6 + rowNum)
							+ ")=0,0,SUM(F" + (E + 6 + rowNum) + ":AJ"
							+ (AI + 6 + rowNum) + "))");
			createCell(workbook, fstRow7, 3+1, rFMc02Style).setValue("");
			createCell(workbook, fstRow7, dayNumber, rFMc02Style).setValue(
					detail.getDeliveryQtyTotal(), true);

			createCell(workbook, fstRow8, 1+1, r01c01Style).setValue(
					"Actual Delivery");
			createCell(workbook, fstRow8, 2+1, rFMc02Style);
			fstRow8.getCell(2+1).setCellFormula(
					"IF(SUM(F" + (E + 7 + rowNum) + ":AJ" + (AI + 7 + rowNum)
							+ ")=0,0,SUM(F" + (E + 7 + rowNum) + ":AJ"
							+ (AI + 7 + rowNum) + "))");
			createCell(workbook, fstRow8, 3+1, rFMc02Style).setValue("");
			createCell(workbook, fstRow8, dayNumber, rFMc02Style).setValue(
					nullToZero(detail.getFgOut()), true);

			createCell(workbook, fstRow9, 1+1, r01c01Style).setValue(
					"Balance Delivery");
			createCell(workbook, fstRow9, 2+1, nFMc02Style);
			fstRow9.getCell(2+1).setCellFormula(
					"IF(" + "SUM(E" + (E + 8 + rowNum) + ",0-D"
							+ (E + 1 + rowNum) + ",C" + (E + 7 + rowNum)
							+ ")<0," + " \"(\"  & TEXT(ABS(SUM(E"
							+ (E + 6 + rowNum) + ",0-D" + (E + 1 + rowNum)
							+ ",D" + (E + 7 + rowNum)
							+ ")),\"#,##0\") & \")\"," + "TEXT(SUM(E"
							+ (E + 6 + rowNum) + ",0-D" + (E + 1 + rowNum)
							+ ",C" + (E + 7 + rowNum) + "),\"#,##0\"))");

			createCell(workbook, fstRow9, 3+1, nFMc02Style).setValue("");

			// < !-- BalanceDelivery -->
			if(firstRowOfEachFg){
				// set date at 0 of each FG is Zero.
				prevBdeli = 0;
//				detail.setFgOut(0);
			}
			if(dayNumber == 4){
				detail.setCustReqQty(0);
			}
			prevBdeli = nullToZero(prevBdeli) - nullToZero(detail.getCustReqQty()) + nullToZero(detail.getFgOut());
			if (prevBdeli < 0) { 
				// In case of 'negative data'.
				balanceDeliveryQty = "("
						+ String.format("%1$,d", Math.abs(prevBdeli)) + ")";
			} else { // In case of 'zero' & 'positive data'.
				balanceDeliveryQty = String.format("%1$,d", prevBdeli);
			}

			createCell(workbook, fstRow9, dayNumber, nFMc02Style).setValue(
					balanceDeliveryQty);

			createCell(workbook, fstRow10, 1+1, r01c01Style).setValue(
					"Balance Order");
			createCell(workbook, fstRow10, 2+1, nFMc02Style);
			fstRow10.getCell(2+1).setCellFormula(
					"IF(" + "SUM(" + backOrder0 + ",0-D" + (E + 6 + rowNum)
							+ ",C" + (E + 7 + rowNum) + ")<0,"
							+ " \"(\"  & TEXT(ABS(SUM(" + backOrder0 + ",0-D"
							+ (E + 6 + rowNum) + ",D" + (E + 7 + rowNum)
							+ ")),\"#,##0\") & \")\"," + "TEXT(SUM("
							+ backOrder0 + ",0-D" + (E + 6 + rowNum) + ",D"
							+ (E + 7 + rowNum) + "),\"#,##0\"))");

			createCell(workbook, fstRow10, 3+1, nFMc02Style).setValue(
					balanceOrder0);

			// < !-- BalanceOrder -->
			if(firstRowOfEachFg){//first time
				prevBOrder = nullToZero(backOrder0);
				firstRowOfEachFg = false;
			}
			if(dayNumber == 4){
				detail.setDeliveryQtyTotal(0);
			}
			prevBOrder = nullToZero(prevBOrder) - nullToZero(detail.getDeliveryQtyTotal()) + nullToZero(detail.getFgOut());
			if (prevBOrder < 0) { 
				// In case of 'negative data'.
				balanceOrderQty = "("
						+ String.format("%1$,d", Math.abs(prevBOrder)) + ")";
			} else { // In case of 'zero' & 'positive data'.
				balanceOrderQty = String.format("%1$,d", prevBOrder);
			}

			createCell(workbook, fstRow10, dayNumber, nFMc02Style).setValue(
					balanceOrderQty);

			createCell(workbook, fstRow11, 1+1, rHDc11Style).setValue("Reason");
			createCell(workbook, fstRow11, 2+1, rHDc11Style).setValue("");
			createCell(workbook, fstRow11, 3+1, rHDc11Style).setValue("");
			createCell(workbook, fstRow11, dayNumber, r01c11Style).setValue(
					detail.getReason());

			dayNumber++;

			// <!-- Setup 'Page Breaks'. -->
			if (cntFG % 4 == 0) {
				sheet.setRowBreak(rowNumber - 1);
			}
		}
		// <!-- Setup 'Print Area'. -->
		workbook.setPrintArea(0, 0, 34+1, 0, rowNumber);
		sheet.getFooter()
				.setCenter(
						"* ถ้าได้รับเอกสารแล้วภายใน 2 วัน กรณีไม่ได้รับการติดต่อจากท่านจะถือว่าเอกสาร DELIVERY SCHEDULE ฉบับนี้ได้รับการตอบรับโดยอัตโนมัติค่ะ *\n"
								+ " A : ADD ORDER      B : BACK ORDER        C : CANCEL ORDER        R : REPLACEMENT");
	}
}