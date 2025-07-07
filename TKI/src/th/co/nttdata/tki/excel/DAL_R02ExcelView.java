package th.co.nttdata.tki.excel;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.TDailyMCWK;
import th.co.nttdata.tki.bean.TDailyMCWKDetail;

public class DAL_R02ExcelView extends AbstractExcelView {

	@Override
	protected void build( Map<String, Object> model, HSSFWorkbook workbook ) throws Exception {
		
		final String DAY = "Day";
		final String NIGHT = "Night";
		final String FORMAT_INT = "#,##0";
		final String FORMAT_DOUBLE = "#,##0.00";
		final SimpleDateFormat dateFormat = new SimpleDateFormat ("yy/MM/dd",Locale.US);
		final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		
		TDailyMCWK tDailyMCWK = (TDailyMCWK) model.get("detailList");
		List<MReason> reasonList = (List<MReason>) model.get("reasonList");
		
		// <!-- Assign: CellStyle. -->
		Style fstHDRStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFont(FONT_HEADR).setTopBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setBgColor().setWrapText();
		Style sndHDRStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFont(FONT_HEADR).setTopBorder().setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setBgColor().setWrapText();
		
		Style r01c00Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setLeftBorder().setRightBorder().setWhiteBgColor().setWrapText();
		Style r01c01Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setWhiteBgColor().setWrapText();
		Style r01c02Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setWhiteBgColor().setWrapText();
		Style r01c03Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setWhiteBgColor().setWrapText();
		Style r01c04Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setWhiteBgColor().setWrapText();
		Style r01c05Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r01c06Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r01c07Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat(FORMAT_INT).setBottomBorder().setRightBorder().setWrapText();
		Style r01c08Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat(FORMAT_INT).setBottomBorder().setRightBorder().setWrapText();
		Style r01c09Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat(FORMAT_INT).setBottomBorder().setRightBorder().setWrapText();
		Style r01c10Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat(FORMAT_INT).setBottomBorder().setRightBorder().setWrapText();
		Style r01c11Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat(FORMAT_DOUBLE).setBottomBorder().setRightBorder().setWrapText();
		Style r01c12Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat(FORMAT_DOUBLE).setBottomBorder().setRightBorder().setWrapText();
		Style r01c13Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat(FORMAT_INT).setRightBorder().setWhiteBgColor().setWrapText();
		Style r01c14Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat(FORMAT_INT).setRightBorder().setWhiteBgColor().setWrapText();
		Style r01c15Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat(FORMAT_INT).setRightBorder().setWhiteBgColor().setWrapText();
		Style r01c16Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat(FORMAT_INT).setRightBorder().setWhiteBgColor().setWrapText();
		
		Style r02c00Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setLeftBorder().setBottomBorder().setRightBorder().setWrapText();
		Style r02c01Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r02c02Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r02c03Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r02c04Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r02c05Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r02c06Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r02c07Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat(FORMAT_INT).setBottomBorder().setRightBorder().setWrapText();
		Style r02c08Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat(FORMAT_INT).setBottomBorder().setRightBorder().setWrapText();
		Style r02c09Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat(FORMAT_INT).setBottomBorder().setRightBorder().setWrapText();
		Style r02c10Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat(FORMAT_INT).setBottomBorder().setRightBorder().setWrapText();
		Style r02c11Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat(FORMAT_DOUBLE).setBottomBorder().setRightBorder().setWrapText();
		Style r02c12Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat(FORMAT_DOUBLE).setBottomBorder().setRightBorder().setWrapText();
		Style r02c13Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r02c14Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r02c15Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r02c16Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r02c13StyleDup = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r02c14StyleDup = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r02c15StyleDup = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r02c16StyleDup = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		
		Style r01ReasonStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setWhiteBgColor().setWrapText();
		Style r02ReasonStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		
		//get sheet at index(0)
		HSSFSheet sheet = workbook.getSheetAt(0);
		
		//head reason
		HSSFRow fstHeader = sheet.getRow(2);
		HSSFRow sndHeader = sheet.getRow(3);
		int colNumber = 17;
		if (reasonList.size() > 0) {
			for( MReason MReason : reasonList ) {
				
				createCell(workbook, fstHeader, colNumber, fstHDRStyle);
				createCell(workbook, sndHeader, colNumber, sndHDRStyle).setValue(MReason.getReasonCode());
				
				colNumber += 1;
			}
			//Generate: Merge Cells
			createMergedRegion(sheet, 2, 2, 17, colNumber - 1);
			sheet.getRow(2).getCell(17).setCellValue("NG Reason");
		}
		
		
		//start fill data at row-index(4)
		int rowNumber = 4;
		
		String key = "";
		Map<String,String> map = new HashMap<String, String>();
		TDailyMCWKDetail dayDetail = new TDailyMCWKDetail();
		TDailyMCWKDetail nightDetail =  new TDailyMCWKDetail();
		
		for (TDailyMCWK mcwk : tDailyMCWK.getDailyMCWKList()) {
			HSSFRow fstRow = sheet.createRow(rowNumber);
			HSSFRow sndRow = sheet.createRow(rowNumber+1);
			TDailyMCWKDetail shift = mcwk.getDetailList().get(0);
			
			key = String.valueOf(mcwk.getReportDate())+mcwk.getCustomerCode()+mcwk.getWip()+mcwk.getPartNo()+mcwk.getMachineNo();
			if (map.isEmpty()) {
				map.put(key, "");
			}
			
			if (!map.containsKey(key)) {
				dayDetail = new TDailyMCWKDetail();
				nightDetail =  new TDailyMCWKDetail();
			}
			
			//choose shiftDay/shiftNight
			if (shift != null) {

				String shiftName = mcwk.getShiftName();
				if( shiftName.equals(DAY) ) {
					dayDetail = shift;
				}
				if( shiftName.equals(NIGHT) ){
					nightDetail = shift;
				}
			}
			if (map.containsKey(key) && rowNumber != 4) {
				sndRow = sheet.createRow(rowNumber-1);
				Double divid = new Double(1);
				int totalOk = dayDetail.getOk()+nightDetail.getOk();
				int totalNg = dayDetail.getNg()+nightDetail.getNg();
				int totalPd = dayDetail.getPd()+nightDetail.getPd();
				int totalQty = dayDetail.getOk()+dayDetail.getNg()+dayDetail.getPd()+nightDetail.getOk()+nightDetail.getNg()+nightDetail.getPd();
				int row = 0;
				createCell(workbook, sndRow, row++, r02c00Style);
				createCell(workbook, sndRow, row++, r02c01Style);
				createCell(workbook, sndRow, row++, r02c02Style);
				createCell(workbook, sndRow, row++, r02c03Style);
				createCell(workbook, sndRow, row++, r02c04Style);
				createCell(workbook, sndRow, row++, r02c05Style).setValue("Night");
				createCell(workbook, sndRow, row++, r02c06Style).setValue(mcwk.getMachineNo() +" : "+ mcwk.getMachineName());
				createCell(workbook, sndRow, row++, r02c07Style).setValue(nightDetail.getOk());
				createCell(workbook, sndRow, row++, r02c08Style).setValue(nightDetail.getNg());
				createCell(workbook, sndRow, row++, r02c09Style).setValue(nightDetail.getPd());
				createCell(workbook, sndRow, row++, r02c10Style).setValue(nightDetail.getQty());
				createCell(workbook, sndRow, row++, r02c11Style).setValue(nightDetail.getTimeUsed());
				createCell(workbook, sndRow, row++, r02c12Style).setValue(nightDetail.getManPower());
				if (totalOk==0) {
					sheet.getRow(rowNumber-2).getCell(row).setCellValue("");
				}else{
					sheet.getRow(rowNumber-2).getCell(row).setCellValue(totalOk/divid);
				}
				createCell(workbook, sndRow, row++, r02c13StyleDup);
				if (totalNg==0) {
					sheet.getRow(rowNumber-2).getCell(row).setCellValue("");
				}else{
					sheet.getRow(rowNumber-2).getCell(row).setCellValue(totalNg/divid);
				}
				createCell(workbook, sndRow, row++, r02c14StyleDup);
				if (totalPd==0) {
					sheet.getRow(rowNumber-2).getCell(row).setCellValue("");
				}else{
					sheet.getRow(rowNumber-2).getCell(row).setCellValue(totalPd/divid);
				}
				createCell(workbook, sndRow, row++, r02c15StyleDup);
				if (totalQty==0) {
					sheet.getRow(rowNumber-2).getCell(row).setCellValue("");
				}else{
					sheet.getRow(rowNumber-2).getCell(row).setCellValue(totalQty/divid);
				}
				createCell(workbook, sndRow, row++, r02c16StyleDup);
				
				colNumber = 17;
				if (reasonList.size() > 0) {
					for (int i = 0; i < reasonList.size(); i++) {
						createCell(workbook, sndRow, colNumber, r02ReasonStyle);
						colNumber++;
					}
				}
			}else{
				createCell(workbook, fstRow,  0, r02c00Style);
				createCell(workbook, fstRow,  1, r02c01Style);
				createCell(workbook, fstRow,  2, r02c02Style);
				createCell(workbook, fstRow,  3, r02c03Style);
				createCell(workbook, fstRow,  4, r02c04Style);
				createCell(workbook, fstRow,  5, r02c05Style).setValue("Day");
				createCell(workbook, fstRow,  6, r02c06Style).setValue(mcwk.getMachineNo() +" : "+ mcwk.getMachineName());
				createCell(workbook, fstRow,  7, r02c07Style).setValue(dayDetail.getOk());
				createCell(workbook, fstRow,  8, r02c08Style).setValue(dayDetail.getNg());
				createCell(workbook, fstRow,  9, r02c09Style).setValue(dayDetail.getPd());
				createCell(workbook, fstRow, 10, r02c10Style).setValue(dayDetail.getQty());
				createCell(workbook, fstRow, 11, r02c11Style).setValue(dayDetail.getTimeUsed());
				createCell(workbook, fstRow, 12, r02c12Style).setValue(dayDetail.getManPower());
				Cell c13 = createCell(workbook, fstRow, 13, r01c13Style);
				Cell c14 = createCell(workbook, fstRow, 14, r01c14Style);
				Cell c15 = createCell(workbook, fstRow, 15, r01c15Style);
				Cell c16 = createCell(workbook, fstRow, 16, r01c16Style);
				
				createCell(workbook, sndRow,  0, r02c00Style);
				createCell(workbook, sndRow,  1, r02c01Style);
				createCell(workbook, sndRow,  2, r02c02Style);
				createCell(workbook, sndRow,  3, r02c03Style);
				createCell(workbook, sndRow,  4, r02c04Style);
				createCell(workbook, sndRow,  5, r02c05Style).setValue("Night");
				createCell(workbook, sndRow,  6, r02c06Style).setValue(mcwk.getMachineNo() +" : "+ mcwk.getMachineName());
				createCell(workbook, sndRow,  7, r02c07Style).setValue(nightDetail.getOk());
				createCell(workbook, sndRow,  8, r02c08Style).setValue(nightDetail.getNg());
				createCell(workbook, sndRow,  9, r02c09Style).setValue(nightDetail.getPd());
				createCell(workbook, sndRow, 10, r02c10Style).setValue(nightDetail.getQty());
				createCell(workbook, sndRow, 11, r02c11Style).setValue(nightDetail.getTimeUsed());
				createCell(workbook, sndRow, 12, r02c12Style).setValue(nightDetail.getManPower());
				createCell(workbook, sndRow, 13, r02c13Style);
				createCell(workbook, sndRow, 14, r02c14Style);
				createCell(workbook, sndRow, 15, r02c15Style);
				createCell(workbook, sndRow, 16, r02c16Style);

//				createMergedRegion(sheet, rowNumber, rowNumber+1, 0, 0);
				sheet.getRow(rowNumber).getCell(0).setCellValue(dateFormatter.format(mcwk.getReportDate()));
				
//				createMergedRegion(sheet, rowNumber, rowNumber+1, 1, 1);
				sheet.getRow(rowNumber).getCell(1).setCellValue(mcwk.getCustomerCode());
				
//				createMergedRegion(sheet, rowNumber, rowNumber+1, 2, 2);
				sheet.getRow(rowNumber).getCell(2).setCellValue(mcwk.getWip());
				
//				createMergedRegion(sheet, rowNumber, rowNumber+1, 3, 3);
				sheet.getRow(rowNumber).getCell(3).setCellValue(mcwk.getPartNo());
				
//				createMergedRegion(sheet, rowNumber, rowNumber+1, 4, 4);
				sheet.getRow(rowNumber).getCell(4).setCellValue(mcwk.getPartName());
				
//				createMergedRegion(sheet, rowNumber, rowNumber+1, 13, 13);
				c13.setValue(new Integer[] { dayDetail.getOk(), nightDetail.getOk() });
				
//				createMergedRegion(sheet, rowNumber, rowNumber+1, 14, 14);
				c14.setValue(new Integer[] { dayDetail.getNg(), nightDetail.getNg() });
				
//				createMergedRegion(sheet, rowNumber, rowNumber+1, 15, 15);
				c15.setValue(new Integer[] { dayDetail.getPd(), nightDetail.getPd() });
				
//				createMergedRegion(sheet, rowNumber, rowNumber+1, 16, 16);
				c16.setValue(new Integer[] { dayDetail.getOk(), dayDetail.getNg(), dayDetail.getPd(), nightDetail.getOk(), nightDetail.getNg(), nightDetail.getPd() });


				//Set double row value
				sheet.getRow(rowNumber+1).getCell(0).setCellValue(dateFormatter.format(mcwk.getReportDate()));
				sheet.getRow(rowNumber+1).getCell(1).setCellValue(mcwk.getCustomerCode());
				sheet.getRow(rowNumber+1).getCell(2).setCellValue(mcwk.getWip());
				sheet.getRow(rowNumber+1).getCell(3).setCellValue(mcwk.getPartNo());
				sheet.getRow(rowNumber+1).getCell(4).setCellValue(mcwk.getPartName());

				colNumber = 17;
				if (reasonList.size() > 0) {
					for (MReason reason : reasonList) {
						//key
						String id = new StringBuffer()
						.append(mcwk.getReportDate() == null ? 0 : dateFormat.format(mcwk.getReportDate()))
						.append(mcwk.getCustomerId() == null ? 0 : mcwk.getCustomerId())
						.append(mcwk.getWip() == null ? 0 : mcwk.getWip())
						.append(mcwk.getPartId() == null ? 0 : mcwk.getPartId())
						.append(mcwk.getMachineNo() == null ? 0 : mcwk.getMachineNo())
						.append(reason.getReasonId())
						.toString();

						Cell cNg = createCell(workbook, fstRow, colNumber, r01ReasonStyle);
						createCell(workbook, sndRow, colNumber, r02ReasonStyle);
//						createMergedRegion(sheet, rowNumber, rowNumber+1, colNumber, colNumber);
						if (tDailyMCWK.getReasonMap().get(id) != null) {
							cNg.setValue(tDailyMCWK.getReasonMap().get(id).getNg());
						}
						
						colNumber++;
					}
				}
				
				map.put(key, "");
				
				//add count row
				rowNumber += 2;
				
			}
		}
	}
}