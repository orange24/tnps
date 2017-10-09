package th.co.nttdata.tki.excel;

import java.math.BigDecimal;
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

import th.co.nttdata.tki.bean.VDailyMonthlySales;

public class MRDC_R07ExcelView extends AbstractExcelView{

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {
		
		final SimpleDateFormat 	dateFormatter 	= new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		VDailyMonthlySales 		vDailyMS 		= (VDailyMonthlySales)model.get("vDailyMS");
		List<VDailyMonthlySales> vdmsList 		= vDailyMS.getvDailyMonthlySalesList();
	
		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short)11);
		HSSFFont fontHD = workbook.createFont();
		fontHD.setFontName("Calibri");
		fontHD.setFontHeightInPoints((short)11);
		fontHD.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		Style fstColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style txtCenterStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style txtLeftCENStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style txtLeftTOPStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style numberStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style bigdeciStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.00").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style bigdeci4Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.0000").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style txtSumStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setWrapText().setFont(fontHD);
		Style fstSumStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setFont(fontHD).setWrapText().setLeftBorder().setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder();
		Style numSumStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setFont(fontHD).setWrapText().setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder();
		Style bigSumStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.00").setFont(fontHD).setWrapText().setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder();
		Style numTotalStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setFont(fontHD).setWrapText().setBottomBorder(CellStyle.BORDER_DOUBLE);
		Style bigTotalStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.00").setFont(fontHD).setWrapText().setBottomBorder(CellStyle.BORDER_DOUBLE);
	
		HSSFSheet sheet = workbook.getSheetAt(0);
		
		if(vDailyMS.getReportDateFr() != null){
			sheet.getRow(1).getCell(0).setCellValue("Stock Out Date (From) : "+dateFormatter.format(vDailyMS.getReportDateFr()));
		}
		if(vDailyMS.getReportDateTo() != null){
			sheet.getRow(1).getCell(2).setCellValue("Stock Out Date (To) : "+dateFormatter.format(vDailyMS.getReportDateTo()));
		}
		
		int rowNum = 3;
		int rowNumPart = 3;
		int sumSale = 0;
		int totalSale = 0;
		BigDecimal sumPrice   = new BigDecimal(0);
		BigDecimal totalPrice = new BigDecimal(0);
		
		if(vdmsList.size() > 0){
			for(VDailyMonthlySales dms : vdmsList){
				rowNumPart = rowNum;
				for(int i=0; i<dms.getvDailyMonthlySalesList().size(); i++){
					VDailyMonthlySales detail = dms.getvDailyMonthlySalesList().get(i);
					HSSFRow dtRow = sheet.createRow(rowNum);
					createCell(workbook, dtRow, 0, fstColumnStyle).setValue(dateFormatter.format(dms.getdReportDate()));
					createCell(workbook, dtRow, 1, txtLeftTOPStyle).setValue(dms.getsCode());
					createCell(workbook, dtRow, 2, txtLeftTOPStyle).setValue(dms.getsCustomerName());
					createCell(workbook, dtRow, 3, txtLeftTOPStyle).setValue(dms.getsCustomerDeptCode());
					createCell(workbook, dtRow, 4, txtLeftTOPStyle).setValue(dms.getsPartName());
					createCell(workbook, dtRow, 5, numberStyle).setValue(detail.getnSalesQty(),true);
					createCell(workbook, dtRow, 6, bigdeci4Style).setValue((detail.getnSaleUnitPrice()==null?null:detail.getnSaleUnitPrice().doubleValue()),true);
					createCell(workbook, dtRow, 7, bigdeciStyle).setValue((detail.getnPrice()==null?null:detail.getnPrice().doubleValue()),true);
					createCell(workbook, dtRow, 8, txtLeftCENStyle).setValue(detail.getsCareerSheetNo());
					createCell(workbook, dtRow, 9, txtLeftCENStyle).setValue(detail.getsCategory());
					createCell(workbook, dtRow, 10, txtCenterStyle).setValue(detail.getsReportTypeName());
					sumSale += detail.getnSalesQty();
					sumPrice = sumPrice.add((detail.getnPrice()==null?new BigDecimal(0):detail.getnPrice()));
					rowNum++;
					
					if(i==dms.getvDailyMonthlySalesList().size()-1){
						dtRow = sheet.createRow(rowNum);
						createCell(workbook, dtRow, 0, fstSumStyle);
						createCell(workbook, dtRow, 1, numSumStyle);
						createCell(workbook, dtRow, 2, numSumStyle);
						createCell(workbook, dtRow, 3, numSumStyle);
						createCell(workbook, dtRow, 4, numSumStyle);
						createMergedRegion(sheet, rowNum, rowNum, 0, 4);
						sheet.getRow(rowNum).getCell(0).setCellValue("Total");
						createCell(workbook, dtRow, 5, numSumStyle).setValue(sumSale,true);
						createCell(workbook, dtRow, 6, numSumStyle);
						createCell(workbook, dtRow, 7, bigSumStyle).setValue(sumPrice.doubleValue(),true);
						createCell(workbook, dtRow, 8, numSumStyle);
						createCell(workbook, dtRow, 9, numSumStyle);
						createCell(workbook, dtRow, 10, numSumStyle);
						createMergedRegion(sheet, rowNum, rowNum, 8, 10);
						
						// merge cell
						if(rowNumPart != rowNum-1){// isSingleRow no merge
							createMergedRegion(sheet, rowNumPart, (rowNum-1), 0, 0);
							createMergedRegion(sheet, rowNumPart, (rowNum-1), 1, 1);
							createMergedRegion(sheet, rowNumPart, (rowNum-1), 2, 2);
							createMergedRegion(sheet, rowNumPart, (rowNum-1), 3, 3);
							createMergedRegion(sheet, rowNumPart, (rowNum-1), 4, 4);
						}
						
						totalSale += sumSale;
						totalPrice = totalPrice.add(sumPrice);
						rowNum++;
					}
				}
				sumSale = 0;
				sumPrice = new BigDecimal(0);
			}
			HSSFRow dtRow = sheet.createRow(rowNum);
			createCell(workbook, dtRow, 0, txtSumStyle);
			createCell(workbook, dtRow, 1, txtSumStyle);
			createCell(workbook, dtRow, 2, txtSumStyle);
			createCell(workbook, dtRow, 3, txtSumStyle);
			createCell(workbook, dtRow, 4, txtSumStyle);
			createMergedRegion(sheet, rowNum, rowNum, 0, 4);
			sheet.getRow(rowNum).getCell(0).setCellValue("Grand Total");
			createCell(workbook, dtRow, 5, numTotalStyle).setValue(totalSale,true);
			createCell(workbook, dtRow, 6, txtSumStyle);
			createCell(workbook, dtRow, 7, bigTotalStyle).setValue(totalPrice.doubleValue(),true);
			createCell(workbook, dtRow, 8, txtSumStyle);
			createCell(workbook, dtRow, 9, txtSumStyle);
			createCell(workbook, dtRow, 10, txtSumStyle);
			createMergedRegion(sheet, rowNum, rowNum, 8, 10);
		}
		// <!-- Setup 'Print Area'. -->
		workbook.setPrintArea(0, 0, 10, 0, rowNum);
	}
}
