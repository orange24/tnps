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

import th.co.nttdata.tki.bean.VDailyMonthlySales;

public class MRDC_R06ExcelView extends AbstractExcelView{

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
		
		Style txtCentStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style txtLeftStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style numberStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0").setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style double2Style 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style double3Style 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.000").setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style double4Style 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.0000").setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style txtSumStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setWrapText().setFont(fontHD);
		Style numTotalStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setFont(fontHD).setWrapText().setBottomBorder(CellStyle.BORDER_DOUBLE);
		Style big2TotalStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.00").setFont(fontHD).setWrapText().setBottomBorder(CellStyle.BORDER_DOUBLE);
		Style big3TotalStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.000").setFont(fontHD).setWrapText().setBottomBorder(CellStyle.BORDER_DOUBLE);
	
		HSSFSheet sheet = workbook.getSheetAt(0);
		if(vDailyMS.getReportDateFr() != null){
			sheet.getRow(1).getCell(0).setCellValue("Delivery Plan Date (From) : "+dateFormatter.format(vDailyMS.getReportDateFr()));
		}
		if(vDailyMS.getReportDateTo() != null){
			sheet.getRow(1).getCell(3).setCellValue("Delivery Plan Date (To) : "+dateFormatter.format(vDailyMS.getReportDateTo()));
		}
		int rowNum  = 3;
		int rowMerg = 3;
		VDailyMonthlySales sale = null;
		if(vdmsList.size() > 0){
			for(VDailyMonthlySales dms : vdmsList){
				rowMerg = rowNum;
				HSSFRow dtRow = sheet.createRow(rowNum);
				createCell(workbook, dtRow, 0, txtCentStyle).setValue(dateFormatter.format(dms.getdDeliveryDate()));
				createCell(workbook, dtRow, 1, txtLeftStyle).setValue(dms.getsCustomerCode());
				createCell(workbook, dtRow, 2, txtLeftStyle).setValue(dms.getsCustomerName());
				createCell(workbook, dtRow, 3, txtLeftStyle).setValue(dms.getsPartNo());
				createCell(workbook, dtRow, 4, txtLeftStyle).setValue(dms.getsPartName());
				createCell(workbook, dtRow, 5, txtLeftStyle).setValue(dms.getsMaterial());
				createCell(workbook, dtRow, 6, txtLeftStyle).setValue(dms.getsOrderReceiptType());
				createCell(workbook, dtRow, 7, double3Style).setValue(dms.getnUnitWeight(),true);
				createCell(workbook, dtRow, 8, numberStyle).setValue(dms.getnOrderReceiptionQty(),true);
				createCell(workbook, dtRow, 9, double4Style).setValue(dms.getnOrderReceiptionUnitPrice(),true);
				createCell(workbook, dtRow, 10, double2Style).setValue(dms.getnOrderReceiptionPrice(),true);
				for(int i=0;i<dms.getvDailyMonthlySalesList().size();i++){
					sale = dms.getvDailyMonthlySalesList().get(i);
					if(i != 0){
						dtRow = sheet.createRow(rowNum);
						createCell(workbook, dtRow, 0, txtCentStyle);
						createCell(workbook, dtRow, 1, txtLeftStyle);
						createCell(workbook, dtRow, 2, txtLeftStyle);
						createCell(workbook, dtRow, 3, txtLeftStyle);
						createCell(workbook, dtRow, 4, txtLeftStyle);
						createCell(workbook, dtRow, 5, txtLeftStyle);
						createCell(workbook, dtRow, 6, txtLeftStyle);
						createCell(workbook, dtRow, 7, double3Style);
						createCell(workbook, dtRow, 8, numberStyle);
						createCell(workbook, dtRow, 9, double4Style);
						createCell(workbook, dtRow, 10, double2Style);
					}
					if(sale.getdReportDate() != null){
						createCell(workbook, dtRow, 11, txtCentStyle).setValue(dateFormatter.format(sale.getdReportDate()));
					}else{
						createCell(workbook, dtRow, 11, txtCentStyle);
					}				
					createCell(workbook, dtRow, 12, numberStyle).setValue(sale.getnDeliveryQty(),true);
					createCell(workbook, dtRow, 13, double2Style).setValue(sale.getnProcessingCost(),true);
					createCell(workbook, dtRow, 14, double2Style).setValue(sale.getnMaterialCost(),true);
					createCell(workbook, dtRow, 15, txtCentStyle).setValue(sale.getsCareerSheetNo());
					rowNum++;
				}
				createMergedRegion(sheet, rowMerg, rowNum-1, 0, 0);
				createMergedRegion(sheet, rowMerg, rowNum-1, 1, 1);
				createMergedRegion(sheet, rowMerg, rowNum-1, 2, 2);
				createMergedRegion(sheet, rowMerg, rowNum-1, 3, 3);
				createMergedRegion(sheet, rowMerg, rowNum-1, 4, 4);
				createMergedRegion(sheet, rowMerg, rowNum-1, 5, 5);
				createMergedRegion(sheet, rowMerg, rowNum-1, 6, 6);
				createMergedRegion(sheet, rowMerg, rowNum-1, 7, 7);
				createMergedRegion(sheet, rowMerg, rowNum-1, 8, 8);
				createMergedRegion(sheet, rowMerg, rowNum-1, 9, 9);
				createMergedRegion(sheet, rowMerg, rowNum-1, 10, 10);
			}
			HSSFRow dtRow = sheet.createRow(rowNum);
			createCell(workbook, dtRow, 0, txtSumStyle);
			createCell(workbook, dtRow, 1, txtSumStyle);
			createCell(workbook, dtRow, 2, txtSumStyle);
			createCell(workbook, dtRow, 3, txtSumStyle);
			createCell(workbook, dtRow, 4, txtSumStyle);
			createCell(workbook, dtRow, 5, txtSumStyle);
			createCell(workbook, dtRow, 6, txtSumStyle);
			createMergedRegion(sheet, rowNum, rowNum, 0, 6);
			sheet.getRow(rowNum).getCell(0).setCellValue("Grand Total");
			createCell(workbook, dtRow, 7, big3TotalStyle);
			createCell(workbook, dtRow, 8, numTotalStyle);
			createCell(workbook, dtRow, 9, numTotalStyle);
			createCell(workbook, dtRow, 10, numTotalStyle);
			createCell(workbook, dtRow, 11, numTotalStyle);
			createCell(workbook, dtRow, 12, big2TotalStyle);
			createCell(workbook, dtRow, 13, big2TotalStyle);
			createCell(workbook, dtRow, 14, big2TotalStyle);
			dtRow.getCell(7).setCellFormula("SUM(H4:H"+rowNum+")");
			dtRow.getCell(8).setCellFormula("SUM(I4:I"+rowNum+")");
			dtRow.getCell(9).setCellFormula("SUM(J4:J"+rowNum+")");
			dtRow.getCell(10).setCellFormula("SUM(K4:K"+rowNum+")");
			dtRow.getCell(12).setCellFormula("SUM(M4:M"+rowNum+")");
			dtRow.getCell(13).setCellFormula("SUM(N4:N"+rowNum+")");
			dtRow.getCell(14).setCellFormula("SUM(O4:O"+rowNum+")");
		}
		// <!-- Setup 'Print Area'. -->
		workbook.setPrintArea(0, 0, 15, 0, rowNum);
	}

}
