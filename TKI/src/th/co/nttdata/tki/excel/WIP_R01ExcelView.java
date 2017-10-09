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

import th.co.nttdata.tki.bean.TWIPDeadline;
import th.co.nttdata.tki.bean.TWIPDeadlineDate;
@SuppressWarnings("unchecked")
public class WIP_R01ExcelView extends AbstractExcelView {

	@Override
	protected void build( Map<String, Object> model, HSSFWorkbook workbook ) throws Exception {

		TWIPDeadline deadline = (TWIPDeadline) model.get("deadline");
		List<Map<String,Integer>> deadlineMonthMap = (List<Map<String,Integer>>) model.get("deadlineMonthMap");
		String hour = (String) model.get("hour");
		Map<Integer,String> monthMap = (Map<Integer,String>) model.get("monthMap");
		
		final SimpleDateFormat dateFullFormatter = new SimpleDateFormat("d", Locale.US);
		
		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) 11);
		HSSFFont fontHD = workbook.createFont();
		fontHD.setFontName("Calibri");
		fontHD.setFontHeightInPoints((short)11);
		fontHD.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		// <!-- Assign: CellStyle. -->
		Style r00c00Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.ALIGN_LEFT).setWhiteBgColor().setFont(font);
		Style r02c04Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.ALIGN_CENTER).setTopBorder().setBottomBorder().setRightBorder().setBgColor().setFont(fontHD);
		Style rDNStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.ALIGN_CENTER).setTopBorder().setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setBgColor().setFont(fontHD);
		Style r05c00Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.ALIGN_RIGHT).setFormat("#,##0").setBottomBorder().setRightBorder().setFont(font);
		Style r05c02Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.ALIGN_RIGHT).setFormat("#,##0").setBottomBorder().setRightBorder().setFont(font);
		Style r00c01Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.ALIGN_RIGHT).setFormat("#,##0").setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setFont(font);
		Style r00c02Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.ALIGN_RIGHT).setFormat("#,##0").setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setFont(font);

		Style r00c03Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setFont(font);
		Style r00c04Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(font);
		Style r00c05Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWhiteBgColor().setWrapText().setFont(font);
		Style cusLastStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setLeftBorder().setWrapText().setFont(font);
		Style customerStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setLeftBorder().setWhiteBgColor().setWrapText().setFont(font);
		
		// <!-- Get 'First' Sheet. -->
		HSSFSheet sheet = workbook.getSheetAt(0);
		
		// <!-- Generate 'Header'. -->
		HSSFRow row1 = sheet.getRow(1);
		HSSFRow row2 = sheet.getRow(2);
		HSSFRow row3 = sheet.getRow(3);
		HSSFRow row4 = sheet.getRow(4);
		
		createCell(workbook, row1, 1, r00c00Style);
		createCell(workbook, row1, 8, r00c00Style);
		createCell(workbook, row1, 18, r00c00Style);
		String customer = "All";
		if(null != deadline.getCustomerCode() && !deadline.getCustomerCode().equals("")){
			customer = deadline.getCustomerCode();
		}
		row1.getCell(1).setCellValue(customer);
		row1.getCell(8).setCellValue(deadline.getWipName());
		row1.getCell(18).setCellValue("( "+hour + " Hours / Shift )");
		
		// <!-- set Value of dateList -->
		int rowNumber = 5;
		int strRowNumber = 0;
		int colStrHeader = 5;
		int colEndHeader = 0;
		Cell customerCell = null;
		Cell partCell = null;
		Style dr05c00Style = null;
		Style dr05c02Style = null;
		Style dr05c03Style = null;
		Style dr05c04Style = null;
		Style drCustoStyle = null;
		
		// part List
		List<TWIPDeadline> pList = deadline.getDeadlinePartList();
		TWIPDeadline[] details = pList.toArray( new TWIPDeadline[pList.size()] );
		for( int i = 0, max = pList.size(); i < max; i++ ) {
			TWIPDeadline detail = details[i];
			
			// start header date
			if(i == 0){
				List<TWIPDeadlineDate> dList = detail.getDeadlineWIPList().get(0).getDeadlineDateList();
				TWIPDeadlineDate[] detailsDate = dList.toArray( new TWIPDeadlineDate[dList.size()] );
				for(int h = 0,maxHeader = dList.size(); h < maxHeader; h++){
					createCell(workbook, row2, colStrHeader, r02c04Style);
					createCell(workbook, row3, colStrHeader, r02c04Style);
					
					colStrHeader++;
				}
				
				colStrHeader = 5;
				colEndHeader = 0;
				for(int h = 0,maxHeader = dList.size(); h < maxHeader; h++){
					TWIPDeadlineDate detailDate = detailsDate[h];
					// <!-- Generate: Merge Cells -->
					colEndHeader = colStrHeader + 1;
					createMergedRegion(sheet, 3, 3, colStrHeader, colEndHeader);
					row3.getCell(colStrHeader).setCellValue(dateFullFormatter.format(detailDate.getReportDate()));
					createCell(workbook, row4, colStrHeader, rDNStyle).setValue("D");
					createCell(workbook, row4, colEndHeader, rDNStyle).setValue("N");
					
					colStrHeader = colEndHeader+1;		
					h++; // need step increase 2  
				}
				
				colStrHeader = 5;
				colEndHeader = 0;
				for( int m = 0, maxDate = deadlineMonthMap.size(); m < maxDate; m++ ) {
					colEndHeader = colStrHeader + deadlineMonthMap.get(m).get("colspanPerMonth")-1;
					
					// <!-- Generate: Merge Cells -->
					createMergedRegion(sheet, 2, 2, colStrHeader, colEndHeader);
					row2.getCell(colStrHeader).setCellValue(monthMap.get(deadlineMonthMap.get(m).get("month")) + " " + deadlineMonthMap.get(m).get("year"));
					colStrHeader = colEndHeader+1;
				}
				
			}
			// end header date
			strRowNumber = rowNumber;
			// WIP List
			List<TWIPDeadline> wList = detail.getDeadlineWIPList();
			TWIPDeadline[] detailsWIP = wList.toArray( new TWIPDeadline[wList.size()] );
			for(int w = 0, maxWIP = wList.size(); w < maxWIP; w++){
				TWIPDeadline detailWIP = detailsWIP[w];
				HSSFRow fstRow = sheet.createRow(rowNumber);
				
				if(w == wList.size()-1){
					dr05c00Style = r00c01Style;
					dr05c02Style = r00c02Style;
					dr05c03Style = r00c04Style;
					dr05c04Style = r00c04Style;
					drCustoStyle = cusLastStyle;
				}else{
					dr05c00Style = r05c00Style;
					dr05c02Style = r05c02Style;
					dr05c03Style = r00c05Style;
					dr05c04Style = r00c03Style;
					drCustoStyle = customerStyle;
				}
				
				if(w == 0){
					customerCell = createCell(workbook, fstRow, 0, drCustoStyle);
					partCell = createCell(workbook, fstRow, 1, dr05c03Style);
				}else{
					createCell(workbook, fstRow, 0, drCustoStyle);
					createCell(workbook, fstRow, 1, dr05c03Style);
				}
				
				createCell(workbook, fstRow, 2, dr05c04Style).setValue(detailWIP.getWip());
				if(detailWIP.getWip().equals("+NG")){
					createCell(workbook, fstRow, 3, dr05c02Style).setValue(detailWIP.getCapacity() + "%");
				}else{
					createCell(workbook, fstRow, 3, dr05c02Style).setValue(detailWIP.getCapacity());
				}
				createCell(workbook, fstRow, 4, dr05c00Style).setValue(detailWIP.getStock());
				
				if(w == wList.size()-1){
					// <!-- Generate: Merge Cells -->
					createMergedRegion(sheet, strRowNumber, rowNumber, 0, 0);
					customerCell.setValue(detail.getCustomerCode());
					createMergedRegion(sheet, strRowNumber, rowNumber, 1, 1);
					partCell.setValue(detail.getPartNo() + " : " + detail.getPartName());
				}
				// date List
				colStrHeader = 5;
				colEndHeader = 0;
				List<TWIPDeadlineDate> dList = detailWIP.getDeadlineDateList();
				TWIPDeadlineDate[] detailsDate = dList.toArray( new TWIPDeadlineDate[dList.size()] );
				if(detailWIP.getIsWip() != 0){
					for(int d = 0,maxDate = dList.size(); d < maxDate; d++){
						TWIPDeadlineDate detailDate = detailsDate[d];
						createCell(workbook, fstRow, colStrHeader++, dr05c00Style).setValue(detailDate.getDeadlineQty());
					}
				}else{
					for(int d = 0,maxDate = dList.size(); d < maxDate; d++){
						TWIPDeadlineDate detailDate = detailsDate[d];
						colEndHeader = colStrHeader + 1;
						Cell str = createCell(workbook, fstRow, colStrHeader, dr05c00Style);
						createCell(workbook, fstRow, colEndHeader, dr05c00Style);
						// <!-- Generate: Merge Cells -->
						createMergedRegion(sheet, fstRow.getRowNum(), fstRow.getRowNum(), colStrHeader, colEndHeader);
						str.setValue(detailDate.getDeadlineQty());
						colStrHeader = colEndHeader+1;
						d++;
					}
				}
				rowNumber++;
			}
			
		}
	}
}