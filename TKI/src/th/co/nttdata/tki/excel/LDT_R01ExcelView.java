package th.co.nttdata.tki.excel;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;

import th.co.nttdata.tki.bean.MLeadtime;

public class LDT_R01ExcelView extends AbstractExcelView {

	@Override
	protected void build( Map<String, Object> model, HSSFWorkbook workbook ) throws Exception {
		
		final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

		
		MLeadtime mLeadtime = (MLeadtime) model.get("mLeadtime");
		Map<String, String> wipMap = (Map<String, String>) model.get("wipMap");
		Map<Integer,String> customerMap = (Map<Integer,String>) model.get("customerMap");
		
		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short)11);
		HSSFFont fontHD = workbook.createFont();
		fontHD.setFontName("Calibri");
		fontHD.setFontHeightInPoints((short)11);
		fontHD.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		Style r01c00Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style r01c01Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r01c02Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r01c03Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r01c04Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r01c05Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style gDobColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.00").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		
		
		HSSFSheet sheet = workbook.getSheetAt(0);
		
		
		int rowNumber = 5;
		
		for(MLeadtime detail : mLeadtime.getLeadTimeList()){
			int colNumber = 0;
			HSSFRow tfgRow = sheet.createRow(rowNumber);
			
			createCell(workbook, tfgRow, colNumber++, r01c00Style).setValue(detail.getWip());
			createCell(workbook, tfgRow, colNumber++, r01c01Style).setValue(detail.getCustomerCode());
			createCell(workbook, tfgRow, colNumber++, r01c02Style).setValue(detail.getPartNo());
			createCell(workbook, tfgRow, colNumber++, r01c02Style).setValue(detail.getPartName());
			
			if(detail.getStUseNo() == null){
				createCell(workbook, tfgRow, colNumber++, r01c03Style).setValue("");
			}else{
				createCell(workbook, tfgRow, colNumber++, r01c03Style).setValue("ST"+detail.getStUseNo());	
			}
			
			
			if(detail.getStSec1() == null || detail.getStQty1() == null || detail.getStResult1() == null){
				createCell(workbook, tfgRow, colNumber++, r01c04Style).setValue("");
				createCell(workbook, tfgRow, colNumber++, r01c04Style).setValue("");
				createCell(workbook, tfgRow, colNumber++, r01c04Style).setValue("");
			}else{
				createCell(workbook, tfgRow, colNumber++, r01c04Style).setValue(detail.getStSec1());	
				createCell(workbook, tfgRow, colNumber++, r01c04Style).setValue(detail.getStQty1());	
				createCell(workbook, tfgRow, colNumber++, r01c04Style).setValue(detail.getStResult1());	
			}
			if(detail.getStSec2() == null || detail.getStQty2() == null || detail.getStResult2() == null){
				createCell(workbook, tfgRow, colNumber++, r01c04Style).setValue("");
				createCell(workbook, tfgRow, colNumber++, r01c04Style).setValue("");
				createCell(workbook, tfgRow, colNumber++, r01c04Style).setValue("");
			}else{
				createCell(workbook, tfgRow, colNumber++, r01c04Style).setValue(detail.getStSec2());	
				createCell(workbook, tfgRow, colNumber++, r01c04Style).setValue(detail.getStQty2());	
				createCell(workbook, tfgRow, colNumber++, r01c04Style).setValue(detail.getStResult2());	
			}
			if(detail.getStSec3() == null || detail.getStQty3() == null || detail.getStResult3() == null){
				createCell(workbook, tfgRow, colNumber++, r01c04Style).setValue("");
				createCell(workbook, tfgRow, colNumber++, r01c04Style).setValue("");
				createCell(workbook, tfgRow, colNumber++, r01c04Style).setValue("");
			}else{
				createCell(workbook, tfgRow, colNumber++, r01c04Style).setValue(detail.getStSec3());	
				createCell(workbook, tfgRow, colNumber++, r01c04Style).setValue(detail.getStQty3());	
				createCell(workbook, tfgRow, colNumber++, r01c04Style).setValue(detail.getStResult3());	
			}
			

			//ST4
			if(detail.getStResult4() == null || detail.getStResult4().isNaN()){
				createCell(workbook, tfgRow, colNumber++, gDobColumnStyle).setValue("0.00");
			}else{
				createCell(workbook, tfgRow, colNumber++, gDobColumnStyle).setValue(detail.getStResult4());	
			}
			if(detail.getStDateFr4() == null || detail.getStDateTo4() == null){
				createCell(workbook, tfgRow, colNumber++, r01c05Style).setValue("");
			}else{
				createCell(workbook, tfgRow, colNumber++, r01c05Style).setValue(dateFormatter.format(detail.getStDateFr4())+" - "+dateFormatter.format(detail.getStDateTo4()));	
			}

			//ST5
			if(detail.getStResult5() == null || detail.getStResult5().isNaN()){
				createCell(workbook, tfgRow, colNumber++, gDobColumnStyle).setValue("0.00");
			}else{
				createCell(workbook, tfgRow, colNumber++, gDobColumnStyle).setValue(detail.getStResult5());	
			}
			if(detail.getStDateFr5() == null || detail.getStDateTo5() == null){
				createCell(workbook, tfgRow, colNumber++, r01c05Style).setValue("");
			}else{
				createCell(workbook, tfgRow, colNumber++, r01c05Style).setValue(dateFormatter.format(detail.getStDateFr5())+" - "+dateFormatter.format(detail.getStDateTo5()));	
			}

			//ST6
			if(detail.getStResult6() == null || detail.getStResult6().isNaN()){
				createCell(workbook, tfgRow, colNumber++, gDobColumnStyle).setValue("0.00");
			}else{
				createCell(workbook, tfgRow, colNumber++, gDobColumnStyle).setValue(detail.getStResult6());	
			}
			if(detail.getStDateFr6() == null || detail.getStDateTo6() == null){
				createCell(workbook, tfgRow, colNumber++, r01c05Style).setValue("");
			}else{
				createCell(workbook, tfgRow, colNumber++, r01c05Style).setValue(dateFormatter.format(detail.getStDateFr6())+" - "+dateFormatter.format(detail.getStDateTo6()));	
			}
			
			rowNumber += 1;
		}
		// <!-- Setup 'Print Area'. -->
		workbook.setPrintArea(0, 1, 0, 0, rowNumber-1);
	}
}