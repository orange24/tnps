package th.co.nttdata.tki.excel;

import java.text.SimpleDateFormat;
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

import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.TDailyMC;
import th.co.nttdata.tki.bean.TDailyMCDetail;
import th.co.nttdata.tki.bean.TDailyMCNGReason;

public class DAL_R01ExcelView extends AbstractExcelView {

	@Override
	protected void build( Map<String, Object> model, HSSFWorkbook workbook ) throws Exception {

		TDailyMC dailyMC = (TDailyMC) model.get("dailyMC");
		List<MReason> reasonList = (List<MReason>) model.get("reasonList");
		
		final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		
		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short)11);
		HSSFFont fontHD = workbook.createFont();
		fontHD.setFontName("Calibri");
		fontHD.setFontHeightInPoints((short)11);
		fontHD.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// <!-- Assign: CellStyle. -->
		Style fstHDRStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFont(FONT_HEADR).setTopBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setBgColor().setWrapText().setFont(fontHD);
		Style sndHDRStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFont(FONT_HEADR).setTopBorder().setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setBgColor().setWrapText().setFont(fontHD);
		Style r01c00Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setLeftBorder().setRightBorder().setWhiteBgColor().setWrapText().setFont(font);
		Style r01c01Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setWhiteBgColor().setWrapText().setFont(font);
		Style r01c02Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setWhiteBgColor().setWrapText().setFont(font);
		Style r01c03Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setWhiteBgColor().setWrapText().setFont(font);
		Style r01c04Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setWhiteBgColor().setWrapText().setFont(font);
		Style r01c05Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setWhiteBgColor().setWrapText().setFont(font);
		Style r01c06Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setWhiteBgColor().setWrapText().setFont(font);
		Style r01c07Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setWhiteBgColor().setWrapText().setFont(font);
		Style r01c08Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setWhiteBgColor().setWrapText().setFont(font);
		Style r01c09Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r01c10Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r01c11Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r01c12Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r01c13Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r01c14Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setRightBorder().setWhiteBgColor().setWrapText().setFont(font);
		Style r01c15Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setRightBorder().setWhiteBgColor().setWrapText().setFont(font);
		Style r01c16Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setRightBorder().setWhiteBgColor().setWrapText().setFont(font);
		Style r01c17Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setRightBorder().setWhiteBgColor().setWrapText().setFont(font);
		Style r01c18Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setWhiteBgColor().setWrapText().setFont(font);
		Style r02c00Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setLeftBorder().setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r02c01Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r02c02Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r02c03Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r02c04Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r02c05Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r02c06Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r02c07Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r02c08Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r02c09Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r02c10Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r02c11Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r02c12Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r02c13Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r02c14Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r02c15Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r02c16Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r02c17Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r02c18Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);

		// <!-- Get 'First' Sheet. -->
		HSSFSheet sheet = workbook.getSheetAt(0);

		// <!-- Generate 'Header'. -->
		HSSFRow fstHeader = sheet.getRow(2);
		HSSFRow sndHeader = sheet.getRow(3);		

		int colNumber = 18;
		if (reasonList.size() > 0) {
			for( MReason MReason : reasonList ) {
				
				createCell(workbook, fstHeader, colNumber, fstHDRStyle);
				createCell(workbook, sndHeader, colNumber, sndHDRStyle).setValue(MReason.getReasonCode());
				
				colNumber += 1;
			}
			// <!-- Generate: Merge Cells -->
			createMergedRegion(sheet, 2, 2, 18, colNumber - 1);
			sheet.getRow(2).getCell(18).setCellValue("NG Reason");
		}


		// <!-- Create Rows. -->
		int rowNumber = 4;
		for( TDailyMC d : dailyMC.getDailyMCList() ) {
			HSSFRow fstRow = sheet.createRow(rowNumber);
			HSSFRow sndRow = sheet.createRow(rowNumber + 1);
			TDailyMCDetail dayDetail = d.getDetails().get(0);
			TDailyMCDetail nhtDetail = d.getDetails().get(1);

			// <!-- First Row: Create Cells. -->
			createCell(workbook, fstRow,  0, r02c00Style);
			createCell(workbook, fstRow,  1, r02c01Style);
			createCell(workbook, fstRow,  2, r02c02Style);
			createCell(workbook, fstRow,  3, r02c03Style);
			createCell(workbook, fstRow,  4, r02c04Style);
			createCell(workbook, fstRow,  5, r02c05Style);
			createCell(workbook, fstRow,  6, r02c06Style);
			createCell(workbook, fstRow,  7, r02c07Style);
			createCell(workbook, fstRow,  8, r02c08Style);
			createCell(workbook, fstRow,  9, r02c09Style).setValue("Day");
			createCell(workbook, fstRow, 10, r02c10Style).setValue(dayDetail.getOk());
			createCell(workbook, fstRow, 11, r02c11Style).setValue(dayDetail.getNg());
			createCell(workbook, fstRow, 12, r02c12Style).setValue(dayDetail.getPd());
			createCell(workbook, fstRow, 13, r02c13Style).setValue(new Integer[] { dayDetail.getOk(), dayDetail.getNg(), dayDetail.getPd() });
			Cell c14 = createCell(workbook, fstRow, 14, r01c14Style);
			Cell c15 = createCell(workbook, fstRow, 15, r01c15Style);
			Cell c16 = createCell(workbook, fstRow, 16, r01c16Style);
			Cell c17 = createCell(workbook, fstRow, 17, r01c17Style);

			// <!-- Second Row: Create Cells. -->
			createCell(workbook, sndRow,  0, r02c00Style);
			createCell(workbook, sndRow,  1, r02c01Style);
			createCell(workbook, sndRow,  2, r02c02Style);
			createCell(workbook, sndRow,  3, r02c03Style);
			createCell(workbook, sndRow,  4, r02c04Style);
			createCell(workbook, sndRow,  5, r02c05Style);
			createCell(workbook, sndRow,  6, r02c06Style);
			createCell(workbook, sndRow,  7, r02c07Style);
			createCell(workbook, sndRow,  8, r02c08Style);
			createCell(workbook, sndRow,  9, r02c09Style).setValue("Night");
			createCell(workbook, sndRow, 10, r02c10Style).setValue(nhtDetail.getOk());
			createCell(workbook, sndRow, 11, r02c11Style).setValue(nhtDetail.getNg());
			createCell(workbook, sndRow, 12, r02c12Style).setValue(nhtDetail.getPd());
			createCell(workbook, sndRow, 13, r02c13Style).setValue(new Integer[] { nhtDetail.getOk(), nhtDetail.getNg(), nhtDetail.getPd() });
			createCell(workbook, sndRow, 14, r02c14Style);
			createCell(workbook, sndRow, 15, r02c15Style);
			createCell(workbook, sndRow, 16, r02c16Style);
			createCell(workbook, sndRow, 17, r02c17Style);
			
//			createMergedRegion(sheet, rowNumber, rowNumber+1, 0, 0);
			sheet.getRow(rowNumber).getCell(0).setCellValue(dateFormatter.format(d.getReportDate()));
			
//			createMergedRegion(sheet, rowNumber, rowNumber+1, 1, 1);
			sheet.getRow(rowNumber).getCell(1).setCellValue(d.getCustomerCode());
			
//			createMergedRegion(sheet, rowNumber, rowNumber+1, 2, 2);
			sheet.getRow(rowNumber).getCell(2).setCellValue(d.getWip());
			
//			createMergedRegion(sheet, rowNumber, rowNumber+1, 3, 3);
			sheet.getRow(rowNumber).getCell(3).setCellValue(d.getMachineNo() +" : "+ d.getMachineName());
			
//			createMergedRegion(sheet, rowNumber, rowNumber+1, 4, 4);
			sheet.getRow(rowNumber).getCell(4).setCellValue(d.getPartNo());
			
//			createMergedRegion(sheet, rowNumber, rowNumber+1, 5, 5);
			sheet.getRow(rowNumber).getCell(5).setCellValue(d.getPartName());
			
//			createMergedRegion(sheet, rowNumber, rowNumber+1, 6, 6);
			sheet.getRow(rowNumber).getCell(6).setCellValue(d.getMoldName());
			
//			createMergedRegion(sheet, rowNumber, rowNumber+1, 7, 7);
			sheet.getRow(rowNumber).getCell(7).setCellValue(d.getMoldNo());
			
//			createMergedRegion(sheet, rowNumber, rowNumber+1, 8, 8);
			sheet.getRow(rowNumber).getCell(8).setCellValue(d.getCavUsed() + (d.getCavDefault() == null ? "" : "/"+d.getCavDefault()));

//			Set Double row value
			sheet.getRow(rowNumber+1).getCell(0).setCellValue(dateFormatter.format(d.getReportDate()));
			sheet.getRow(rowNumber+1).getCell(1).setCellValue(d.getCustomerCode());
			sheet.getRow(rowNumber+1).getCell(2).setCellValue(d.getWip());
			sheet.getRow(rowNumber+1).getCell(3).setCellValue(d.getMachineNo() +" : "+ d.getMachineName());
			sheet.getRow(rowNumber+1).getCell(4).setCellValue(d.getPartNo());
			sheet.getRow(rowNumber+1).getCell(5).setCellValue(d.getPartName());
			sheet.getRow(rowNumber+1).getCell(6).setCellValue(d.getMoldName());
			sheet.getRow(rowNumber+1).getCell(7).setCellValue(d.getMoldNo());
			sheet.getRow(rowNumber+1).getCell(8).setCellValue(d.getCavUsed() + (d.getCavDefault() == null ? "" : "/"+d.getCavDefault()));

//			createMergedRegion(sheet, rowNumber, rowNumber+1, 14, 14);
			c14.setValue(new Integer[] { dayDetail.getOk(), nhtDetail.getOk() });
			
//			createMergedRegion(sheet, rowNumber, rowNumber+1, 15, 15);
			c15.setValue(new Integer[] { dayDetail.getNg(), nhtDetail.getNg() });
			
//			createMergedRegion(sheet, rowNumber, rowNumber+1, 16, 16);
			c16.setValue(new Integer[] { dayDetail.getPd(), nhtDetail.getPd() });
			
//			createMergedRegion(sheet, rowNumber, rowNumber+1, 17, 17);
			c17.setValue(new Integer[] { dayDetail.getOk(), dayDetail.getNg(), dayDetail.getPd(), nhtDetail.getOk(), nhtDetail.getNg(), nhtDetail.getPd() });

			colNumber = 18;
			if (reasonList.size() > 0) {
				for( MReason MReason : reasonList ) {
					
					int dayNg = 0, nhtNg = 0;
					for( TDailyMCNGReason reason : dayDetail.getReasonList() )
						if( reason.getReasonId() != null && MReason.getReasonId().compareTo(reason.getReasonId()) == 0 ) {
							dayNg += nullToZero(reason.getNg());
							break;
						}
					for( TDailyMCNGReason reason : nhtDetail.getReasonList() )
						if( reason.getReasonId() != null && MReason.getReasonId().compareTo(reason.getReasonId()) == 0 ) {
							nhtNg += nullToZero(reason.getNg());
							break;
						}
					
					Cell cNg = createCell(workbook, fstRow, colNumber, r01c18Style);
					createCell(workbook, sndRow, colNumber, r02c18Style);
					
//					createMergedRegion(sheet, rowNumber, rowNumber+1, colNumber, colNumber);
					cNg.setValue(dayNg + nhtNg);
					
					colNumber++;
				}
			}
			rowNumber += 2;
		}
	}
}