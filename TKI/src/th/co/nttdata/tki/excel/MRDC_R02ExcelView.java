package th.co.nttdata.tki.excel;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import th.co.nttdata.tki.bean.TDailyWK;
import th.co.nttdata.tki.bean.TDailyWKDetail;

public class MRDC_R02ExcelView extends AbstractExcelView{

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {
		TDailyWK tDailyWK = (TDailyWK) model.get("tDailyWK");

		// set Font Header
		HSSFFont fontHD = workbook.createFont();
		fontHD.setFontName("Calibri");
		fontHD.setFontHeightInPoints((short)11);
		fontHD.setBoldweight(Font.BOLDWEIGHT_BOLD);
		// set Font
		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short)11);
		
		// <!-- Assign: CellStyle. -->
		Style fstColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setLeftBorder().setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style txtColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dob3ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.000").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dob4ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.0000").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		// Grand Total
		Style gTxtColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setWrapText().setFont(fontHD);
		Style gDob3ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.000").setBottomBorder(CellStyle.BORDER_DOUBLE).setWrapText().setFont(fontHD);
		Style gDob4ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.0000").setBottomBorder(CellStyle.BORDER_DOUBLE).setWrapText().setFont(fontHD);
		// <!-- Get 'First' Sheet. -->
		HSSFSheet sheet = workbook.getSheetAt(0);

		int rowNumber = 3;
		HSSFRow row = null;
//		// customer
//		HSSFRow row1 = sheet.getRow(1);
//		row1.getCell(0).setCellValue("Customer : "+tDailyWK.getCustomerCode());
//		row1.getCell(1).setCellValue("Customer Dept Code : "+tDailyWK.getPartNo());
//		row1.getCell(3).setCellValue("Category : "+tDailyWK.getCategory());
		
		List<TDailyWKDetail> dList = tDailyWK.getDailyWKDetailList();
		TDailyWKDetail[] details = dList.toArray( new TDailyWKDetail[dList.size()] );
		if(dList.size() > 0){
			for( int i = 0, max = dList.size(); i < max; i++ ) {
				TDailyWKDetail currDetail = details[i];
				row = sheet.createRow(rowNumber);
				createCell(workbook, row, 0,  fstColumnStyle).setValue(currDetail.getCustomerCode());
				createCell(workbook, row, 1,  txtColumnStyle).setValue(currDetail.getPartNo());
				createCell(workbook, row, 2,  txtColumnStyle).setValue(currDetail.getPartName());
				createCell(workbook, row, 3,  txtColumnStyle).setValue(currDetail.getMaterialName());
				createCell(workbook, row, 4,  txtColumnStyle).setValue(currDetail.getCategory());
				createCell(workbook, row, 5,  dob3ColumnStyle).setValue(currDetail.getUnitWeight());
				createCell(workbook, row, 6,  dob4ColumnStyle).setValue(currDetail.getSaleUnitPrice());
				createCell(workbook, row, 7,  txtColumnStyle).setValue(currDetail.getCurrency());
				rowNumber++;
			}// for loop
			row = sheet.createRow(rowNumber);
			createCell(workbook, row, 0,  gTxtColumnStyle).setValue("Grand Total");
			createCell(workbook, row, 1,  gTxtColumnStyle);
			createCell(workbook, row, 2,  gTxtColumnStyle);
			createCell(workbook, row, 3,  gTxtColumnStyle);
			createCell(workbook, row, 4,  gTxtColumnStyle);
			createMergedRegion(sheet,row.getRowNum(),row.getRowNum(), 0, 4);
			createCell(workbook, row, 5,  gDob3ColumnStyle);
			createCell(workbook, row, 6,  gDob4ColumnStyle);
			row.getCell(5).setCellFormula("SUM(F3:F"+rowNumber+")");
			row.getCell(6).setCellFormula("SUM(G3:G"+rowNumber+")");
		}
		
		// <!-- Setup 'Print Area'. -->
		workbook.setPrintArea(0, 0, 6, 0, rowNumber);
	}
}