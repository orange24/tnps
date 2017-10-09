package th.co.nttdata.tki.excel;

import java.text.DecimalFormat;
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

import th.co.nttdata.tki.bean.MDocControl;
import th.co.nttdata.tki.bean.TDeliveryPlan;

public class DLV_R02ExcelView extends AbstractExcelView {

	@Override
	protected void build( Map<String, Object> model, HSSFWorkbook workbook ) throws Exception {

		TDeliveryPlan deliveryPlan = (TDeliveryPlan) model.get("deliveryPlan");
		MDocControl docControl = (MDocControl) model.get("docControl");
		
		DecimalFormat dFmt = new DecimalFormat("00");// format Time
		final SimpleDateFormat dateFullFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
		
		HSSFFont fontHD = workbook.createFont();
		fontHD.setFontName("Calibri");
		fontHD.setFontHeightInPoints((short)11);
		fontHD.setBoldweight(Font.BOLDWEIGHT_BOLD);
		// <!-- Assign: CellStyle. -->
		Style r01c00Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.ALIGN_RIGHT).setLeftBorder().setRightBorder().setWhiteBgColor().setWrapText();
		Style r02c00Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.ALIGN_LEFT).setTopBorder().setLeftBorder().setRightBorder().setWhiteBgColor().setWrapText();
		Style dayc01Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.ALIGN_CENTER).setFont(fontHD).setWrapText();
		Style user01Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.ALIGN_CENTER).setFont(fontHD).setWrapText();
		Style r02c01Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.ALIGN_LEFT).setBottomBorder(CellStyle.BORDER_THIN).setLeftBorder().setRightBorder().setWrapText();
		Style r02c02Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.ALIGN_LEFT).setTopBorder().setBottomBorder(CellStyle.BORDER_THIN).setLeftBorder().setRightBorder().setWrapText();
		Style r01c03Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.ALIGN_LEFT).setTopBorder(CellStyle.BORDER_THIN).setBottomBorder(CellStyle.BORDER_THIN).setRightBorder().setWrapText();
		Style r01c04Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.ALIGN_LEFT).setTopBorder(CellStyle.BORDER_THIN).setBottomBorder().setRightBorder().setWrapText();
		Style r01c05Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder(CellStyle.BORDER_THIN).setRightBorder().setWrapText();
		Style docStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.ALIGN_CENTER).setTopBorder(CellStyle.BORDER_THIN).setBottomBorder().setRightBorder().setWrapText();
		
		// <!-- Get 'First' Sheet. -->
		HSSFSheet sheet = workbook.getSheetAt(0);
		
		// <!-- Generate 'Header'. -->
		HSSFRow doc = sheet.getRow(0);
		HSSFRow doc2 = sheet.getRow(1);
		HSSFRow day = sheet.getRow(1);
		HSSFRow user = sheet.getRow(2);

		createCell(workbook, doc, 12, docStyle);
		createCell(workbook, doc2, 12, docStyle);
		createCell(workbook, day, 1, dayc01Style);
		createCell(workbook, user, 1, user01Style);
		
		day.getCell(1).setCellValue(dateFullFormatter.format(deliveryPlan.getDeliveryDate()));
		user.getCell(1).setCellValue(deliveryPlan.getUpdateBy());
		doc.getCell(12).setCellValue(docControl.getDocNoR2());
		doc2.getCell(12).setCellValue(docControl.getRevDocNoR2());
		
		int rowNumber = 7;
		int prevCustommerId  = 0;
		int num = 1;
		String prevTime = "";
		
		// <!-- set Value of dateList -->
		List<TDeliveryPlan> pList = deliveryPlan.getPlanList();
		TDeliveryPlan[] details = pList.toArray( new TDeliveryPlan[pList.size()] );
		for( int i = 0, max = pList.size(); i < max; i++ ) {
			TDeliveryPlan detail = details[i];
			HSSFRow fstRow = sheet.createRow(rowNumber);
			
			if(prevCustommerId != detail.getCustomerId()){
				prevCustommerId = detail.getCustomerId();
				prevTime = detail.getTimeHr() + "." + detail.getTimeMin();
				
				if(i == pList.size()-1){
					createCell(workbook, fstRow, 0, r02c02Style).setValue(num++);
					createCell(workbook, fstRow, 1, r02c02Style).setValue(detail.getCustomerCode());
				}else{
					createCell(workbook, fstRow, 0, r02c00Style).setValue(num++);
					createCell(workbook, fstRow, 1, r02c00Style).setValue(detail.getCustomerCode());
				}
				
				createCell(workbook, fstRow, 2, r01c04Style).setValue(detail.getTimeHr() + "." + dFmt.format(detail.getTimeMin()));
				createCell(workbook, fstRow, 3, r01c04Style).setValue(detail.getFgName());
				createCell(workbook, fstRow, 4, r01c05Style).setValue(detail.getDeliveryQty());
				createCell(workbook, fstRow, 5, r01c04Style).setValue("");
				createCell(workbook, fstRow, 6, r01c04Style).setValue("");
				createCell(workbook, fstRow, 7, r01c04Style).setValue("");
				createCell(workbook, fstRow, 8, r01c04Style).setValue("");
				createCell(workbook, fstRow, 9, r01c04Style).setValue("");
				createCell(workbook, fstRow, 10, r01c04Style).setValue("");
				createCell(workbook, fstRow, 11, r01c04Style).setValue("");
				createCell(workbook, fstRow, 12, r01c04Style).setValue("");
			}else{
				if(i == pList.size()-1){
					createCell(workbook, fstRow, 0, r02c01Style).setValue("");
					createCell(workbook, fstRow, 1, r02c01Style).setValue("");
					
				}else{
					createCell(workbook, fstRow, 0, r01c00Style).setValue("");
					createCell(workbook, fstRow, 1, r01c00Style).setValue("");
					
				}
				if(!prevTime.equals((detail.getTimeHr() + "." + detail.getTimeMin()))){
					prevTime = detail.getTimeHr() + "." + detail.getTimeMin();
					createCell(workbook, fstRow, 2, r01c04Style).setValue(detail.getTimeHr() + "." + dFmt.format(detail.getTimeMin()));
				}else{
					createCell(workbook, fstRow, 2, r01c04Style).setValue("");
				}
				
				createCell(workbook, fstRow, 3, r01c03Style).setValue(detail.getFgName());
				createCell(workbook, fstRow, 4, r01c05Style).setValue(detail.getDeliveryQty());
				createCell(workbook, fstRow, 5, r01c03Style).setValue("");
				createCell(workbook, fstRow, 6, r01c03Style).setValue("");
				createCell(workbook, fstRow, 7, r01c03Style).setValue("");
				createCell(workbook, fstRow, 8, r01c03Style).setValue("");
				createCell(workbook, fstRow, 9, r01c03Style).setValue("");
				createCell(workbook, fstRow, 10, r01c03Style).setValue("");
				createCell(workbook, fstRow, 11, r01c03Style).setValue("");
				createCell(workbook, fstRow, 12, r01c03Style).setValue("");
			}
			rowNumber++;
		}
		// <!-- Setup 'Print Area'. -->
		workbook.setPrintArea(0, 0, 12, 0, rowNumber);
	}
}