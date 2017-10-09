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

import th.co.nttdata.tki.bean.TFG;
import th.co.nttdata.tki.bean.TFGDetail;
import th.co.nttdata.tki.bean.TFGStock;
import th.co.nttdata.tki.excel.AbstractExcelView.Style;

public class FNG_R01ExcelView extends AbstractExcelView {

	@Override
	protected void build( Map<String, Object> model, HSSFWorkbook workbook ) throws Exception {
		
		final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

		TFG tfg = (TFG) model.get("TFG");
		List<TFGDetail> reportTypeList = (List<TFGDetail>) model.get("reportTypeList");
		
		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short)11);
		HSSFFont fontHD = workbook.createFont();
		fontHD.setFontName("Calibri");
		fontHD.setFontHeightInPoints((short)11);
		fontHD.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		Style fstHDRStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setTopBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setBgColor().setFont(fontHD).setWrapText();
		Style sndHDRStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setTopBorder().setRightBorder().setBgColor().setFont(fontHD).setWrapText();
		Style thrHDRStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setTopBorder().setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setBgColor().setFont(fontHD).setWrapText();
		Style r01c00Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style r01c01Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r01c02Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r01c03Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r01c04Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r01c05Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r01c06Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r01cTotalStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style r01cBalanceStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style gDobColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.00").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		
		
		HSSFSheet sheet = workbook.getSheetAt(0);
		
		HSSFRow fstHeader = sheet.getRow(2);
		HSSFRow sndHeader = sheet.getRow(3);
		HSSFRow thrHeader = sheet.getRow(4);
		
		int colNumber = 9;
		for(TFGDetail reportType : reportTypeList){
			
			createCell(workbook, fstHeader, colNumber, fstHDRStyle);
			createCell(workbook, sndHeader, colNumber, sndHDRStyle);
			createCell(workbook, thrHeader, colNumber, thrHDRStyle).setValue(reportType.getReportTypeName());
			
			colNumber+=1;
		}		
		createCell(workbook, fstHeader, colNumber, fstHDRStyle);
		createCell(workbook, sndHeader, colNumber, sndHDRStyle);
		createCell(workbook, thrHeader, colNumber, thrHDRStyle).setValue("Total");
		
		createMergedRegion(sheet, 2, 2, 7, colNumber);
		sheet.getRow(2).getCell(5).setCellValue("Mold No");

		createMergedRegion(sheet, 2, 4, 6, 6);
		sheet.getRow(2).getCell(6).setCellValue("Stock FG");

		sheet.getRow(3).getCell(7).setCellValue("In");

		createMergedRegion(sheet, 3, 3, 9, colNumber);
		sheet.getRow(3).getCell(9).setCellValue("Out");
		
		colNumber+=1;
		createCell(workbook, fstHeader, colNumber, fstHDRStyle);
		createCell(workbook, sndHeader, colNumber, sndHDRStyle);
		createCell(workbook, thrHeader, colNumber, thrHDRStyle);
				
		createMergedRegion(sheet, 2, 4, colNumber, colNumber);
		sheet.getRow(2).getCell(colNumber).setCellValue("Balance");
		
		createMergedRegion(sheet, 0, 0, 0, colNumber);
		sheet.getRow(0).getCell(0).setCellValue("Report Finish Goods IN/OUT");
		
		int rowNumber = 5;
		for(TFGStock detail : tfg.getStockList()){
			HSSFRow tfgRow = sheet.createRow(rowNumber);
			
			createCell(workbook, tfgRow, 0, r01c00Style).setValue(dateFormatter.format(detail.getReportDate()));
			createCell(workbook, tfgRow, 1, r01c01Style).setValue(detail.getCustomerCode());
			createCell(workbook, tfgRow, 2, r01c02Style).setValue(detail.getMaterial());
			createCell(workbook, tfgRow, 3, r01c02Style).setValue(detail.getFgNo());
			createCell(workbook, tfgRow, 4, r01c03Style).setValue(detail.getFgName());
			createCell(workbook, tfgRow, 5, r01c03Style).setValue(detail.getMoldNo());
			createCell(workbook, tfgRow, 6, r01c04Style).setValue(detail.getFgStock(),true);
			createCell(workbook, tfgRow, 7, r01c05Style).setValue(detail.getFgIn());
			createCell(workbook, tfgRow, 8, gDobColumnStyle).setValue(detail.getWeight());
			int colNum = 9;			
			String keyMap = "";
			for(TFGDetail reportType : reportTypeList){
				int fgOutStock = 0;
				keyMap = dateFormat(detail.getReportDate())+":"+detail.getFgId()+":"+detail.getCustomerId()+":"+reportType.getReportType();
				if(tfg.getDetailMap().get(keyMap)!= null){
					fgOutStock = tfg.getDetailMap().get(keyMap).getFgOut();
				}
				createCell(workbook, tfgRow, colNum, r01c06Style).setValue(fgOutStock);
				colNum += 1 ;
			}
			createCell(workbook, tfgRow, colNumber-1, r01cTotalStyle).setValue(detail.getFgOut());			
			createCell(workbook, tfgRow, colNumber, r01cBalanceStyle).setValue(detail.getFgBalance(),true);
			rowNumber += 1;
		}
		// <!-- Setup 'Print Area'. -->
		workbook.setPrintArea(0, 1, colNumber+1, 0, rowNumber-1);
	}
}