package th.co.nttdata.tki.excel;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.TDailyWK;
import th.co.nttdata.tki.bean.TDailyWKDetail;
import th.co.nttdata.tki.bean.TDailyWKNGReason;

public class DAL_R03ExcelView extends AbstractExcelView {

	@Override
	protected void build( Map<String, Object> model, HSSFWorkbook workbook ) throws Exception {

		TDailyWK dailyWK = (TDailyWK) model.get("dailyWK");
		List<MReason> reasonList = (List<MReason>) model.get("reasonList");

		final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

		Map<String,TDailyWKNGReason> reasonMap = new LinkedHashMap<String,TDailyWKNGReason>();
		reasonMap = dailyWK.getReasonMap();

		// <!-- Assign: CellStyle. -->
		Style fstHDRStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFont(FONT_HEADR).setTopBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setBgColor();
		Style sndHDRStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFont(FONT_HEADR).setTopBorder().setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setBgColor();
		Style r01c00Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setLeftBorder().setRightBorder().setWhiteBgColor().setWrapText();
		Style r01c01Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setWhiteBgColor().setWrapText();
		Style r01c02Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setWhiteBgColor().setWrapText();
		Style r01c03Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setWhiteBgColor().setWrapText();
		Style r01c04Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setWhiteBgColor().setWrapText();
		Style r01c05Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r01c07Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText();
		Style r01c08Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText();
		Style r01c09Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText();
		Style r01c10Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText();
		Style r01c11Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r01c12Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r01c13Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setRightBorder().setWhiteBgColor().setWrapText();
		Style r01c14Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setRightBorder().setWhiteBgColor().setWrapText();
		Style r01c15Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setRightBorder().setWhiteBgColor().setWrapText();
		Style r01c16Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setRightBorder().setWhiteBgColor().setWrapText();
		Style r01c17Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setWhiteBgColor().setWrapText();

		Style r02c00Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setLeftBorder().setBottomBorder().setRightBorder().setWrapText();
		Style r02c01Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r02c02Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r02c03Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r02c04Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r02c05Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r02c07Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText();
		Style r02c08Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText();
		Style r02c09Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText();
		Style r02c10Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText();
		Style r02c11Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r02c12Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r02c13Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r02c14Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r02c15Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r02c16Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r02c17Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style numberAllBorderStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setTopBorder().setBottomBorder().setWrapText();
		Style textAllBorderStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setTopBorder().setBottomBorder().setWrapText();

		// <!-- Get 'First' Sheet. -->
		HSSFSheet sheet = workbook.getSheetAt(0);

		// <!-- Generate 'Header'. -->
		HSSFRow fstHeader = sheet.getRow(2);
		HSSFRow sndHeader = sheet.getRow(3);

		int colNumber = 19;
		if (reasonList.size() > 0) {
			for( MReason MReason : reasonList ) {
				
				createCell(workbook, fstHeader, colNumber, fstHDRStyle);
				createCell(workbook, sndHeader, colNumber, sndHDRStyle).setValue(MReason.getReasonCode());
				
				colNumber += 1;
			}
			// <!-- Generate: Merge Cells -->
			createMergedRegion(sheet, 2, 2, 19, colNumber - 1);
			sheet.getRow(2).getCell(19).setCellValue("NG Reason");
		}


		// <!-- Create Rows. -->
		int rowNumber = 4;
		List<TDailyWKDetail> dList = dailyWK.getDailyWKDetailList();
		//var str="1:2:3:4";document.write(str.lastIndexOf(":") + "<br />");document.write(str.substr(0,(str.lastIndexOf(":"))) + "<br />");

		TDailyWKDetail[] details = dList.toArray( new TDailyWKDetail[dList.size()] );
		for( int i = -1, cnt = 0, max = dList.size(); cnt < max; cnt++ ) {
			HSSFRow fstRow = sheet.createRow(rowNumber);
//			HSSFRow sndRow = sheet.createRow(rowNumber + 1);
			TDailyWKDetail currDetail = details[cnt];

			String shift = currDetail.getShift();
			// <!-- First Row: Create Cells. -->
			createCell(workbook, fstRow,  0, r02c00Style);
			createCell(workbook, fstRow,  1, r02c01Style);
			createCell(workbook, fstRow,  2, r02c02Style);
			createCell(workbook, fstRow,  3, r02c03Style);
			createCell(workbook, fstRow,  4, r02c04Style);
			createCell(workbook, fstRow,  5, r02c05Style).setValue("Day");
			if ("N".equals(shift)) createCell(workbook, fstRow,  5, r02c05Style).setValue("Night");

			// <!-- Second Row: Create Cells. -->
//			createCell(workbook, sndRow,  0, r02c00Style);
//			createCell(workbook, sndRow,  1, r02c01Style);
//			createCell(workbook, sndRow,  2, r02c02Style);
//			createCell(workbook, sndRow,  3, r02c03Style);
//			createCell(workbook, sndRow,  4, r02c04Style);
//			createCell(workbook, sndRow,  5, r02c05Style).setValue("Night");

			//createMergedRegion(sheet, rowNumber, rowNumber+1, 0, 0);
			sheet.getRow(rowNumber).getCell(0).setCellValue(dateFormatter.format(currDetail.getReportDate()));

//			createMergedRegion(sheet, rowNumber, rowNumber+1, 1, 1);
			sheet.getRow(rowNumber).getCell(1).setCellValue(currDetail.getCustomerCode());

//			createMergedRegion(sheet, rowNumber, rowNumber+1, 2, 2);
			sheet.getRow(rowNumber).getCell(2).setCellValue(currDetail.getWip());

//			createMergedRegion(sheet, rowNumber, rowNumber+1, 3, 3);
			sheet.getRow(rowNumber).getCell(3).setCellValue(currDetail.getPartNo());

//			createMergedRegion(sheet, rowNumber, rowNumber+1, 4, 4);
			sheet.getRow(rowNumber).getCell(4).setCellValue(currDetail.getPartName());

				createCell(workbook, fstRow, 6, r02c07Style).setValue(currDetail.getOk());
				createCell(workbook, fstRow, 7, r02c08Style).setValue(currDetail.getNg());
				createCell(workbook, fstRow, 8, r02c09Style).setValue(currDetail.getPd());
				createCell(workbook, fstRow, 9, r02c10Style).setValue(new Integer[] { currDetail.getOk(), currDetail.getNg(), currDetail.getPd() });
				createCell(workbook, fstRow, 10, numberAllBorderStyle).setValue(currDetail.getTimeUsed());
				createCell(workbook, fstRow, 11, numberAllBorderStyle).setValue(currDetail.getManPower());
				createCell(workbook, fstRow, 12, numberAllBorderStyle).setValue(currDetail.getLossTime());
				createCell(workbook, fstRow, 13, textAllBorderStyle).setValue(currDetail.getLossTimeReasonName());
				createCell(workbook, fstRow, 14, numberAllBorderStyle).setValue(currDetail.getStaff());
//				Cell c11 = createCell(workbook, fstRow, 10, r01c11Style);
//				Cell c12 = createCell(workbook, fstRow, 11, r01c12Style);
				Cell c13 = createCell(workbook, fstRow, 15, r02c13Style);
				Cell c14 = createCell(workbook, fstRow, 16, r02c14Style);
				Cell c15 = createCell(workbook, fstRow, 17, r02c15Style);
				Cell c16 = createCell(workbook, fstRow, 18, r02c16Style);

//				createCell(workbook, sndRow,  6, r02c07Style).setValue(nextDetail.getOk());
//				createCell(workbook, sndRow,  7, r02c08Style).setValue(nextDetail.getNg());
//				createCell(workbook, sndRow,  8, r02c09Style).setValue(nextDetail.getPd());
//				createCell(workbook, sndRow,  9, r02c10Style).setValue(new Integer[] { nextDetail.getOk(), nextDetail.getNg(), nextDetail.getPd() });
//				createCell(workbook, sndRow, 10, numberAllBorderStyle).setValue(nextDetail.getTimeUsed());
//				createCell(workbook, sndRow, 11, numberAllBorderStyle).setValue(nextDetail.getManPower());
//				createCell(workbook, sndRow, 12, numberAllBorderStyle).setValue(nextDetail.getLossTime());
//				createCell(workbook, sndRow, 13, textAllBorderStyle).setValue(nextDetail.getLossTimeReasonName());
//				createCell(workbook, sndRow, 14, numberAllBorderStyle).setValue(nextDetail.getStaff());
//				createCell(workbook, sndRow, 15, r02c13Style);
//				createCell(workbook, sndRow, 16, r02c14Style);
//				createCell(workbook, sndRow, 17, r02c15Style);
//				createCell(workbook, sndRow, 18, r02c16Style);

			colNumber = 19;
			if (reasonList.size() > 0) {
				for( MReason MReason : reasonList ) {
					String key = currDetail.getDatailRef()+":"+MReason.getReasonId();
					TDailyWKNGReason reason = reasonMap.get(key);

					int totalNg = 0;
					if(reason != null && key.equals(reason.getIdRef())){
						totalNg += nullToZero(reason.getNg());
					}

					Cell cNg = createCell(workbook, fstRow, colNumber, r02c17Style);
//					createCell(workbook, sndRow, colNumber, r02c17Style);

//					createMergedRegion(sheet, rowNumber, rowNumber+1, colNumber, colNumber);
					cNg.setValue(totalNg);

					colNumber++;
				}
			}
			// <!-- check last index -->
			if( i >= max - 1 )
				break;

			rowNumber += 1;
		}
	}
}