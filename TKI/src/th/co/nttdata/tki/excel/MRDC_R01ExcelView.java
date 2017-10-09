package th.co.nttdata.tki.excel;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import th.co.nttdata.tki.bean.TDeliveryPlan;

public class MRDC_R01ExcelView extends AbstractExcelView{

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {
		
		TDeliveryPlan productCompositionList1 = (TDeliveryPlan) model.get("productCompositionList1");
		TDeliveryPlan productCompositionList2 = (TDeliveryPlan) model.get("productCompositionList2");
		DecimalFormat dob2Format = new DecimalFormat("#,##0.00");
		DecimalFormat dob3Format = new DecimalFormat("#,##0.000");
		DecimalFormat dob4Format = new DecimalFormat("#,##0.0000");
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
		
		Style c0r0ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setTopBorder().setLeftBorder().setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style c1r0ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setTopBorder().setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style c2r0ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style c3r0ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.000").setTopBorder().setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style c4r0ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.000").setTopBorder().setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style c5r0ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0").setTopBorder().setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style c6r0ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0").setTopBorder().setBottomBorder().setRightBorder().setWrapText().setFont(font);
		// set white bg style
		Style c0r1ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setLeftBorder().setRightBorder().setWrapText().setFont(font).setWhiteBgColor();
		Style c1r1ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setRightBorder().setWrapText().setFont(font).setWhiteBgColor();
		Style c2r1ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style c3r1ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.000").setRightBorder().setWrapText().setFont(font).setWhiteBgColor();
		Style c4r1ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.000").setRightBorder().setWrapText().setFont(font).setWhiteBgColor();
		Style c5r1ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0").setRightBorder().setWrapText().setFont(font).setWhiteBgColor();
		Style c6r1ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0").setRightBorder().setWrapText().setFont(font).setWhiteBgColor();
		// set no bottom style
		Style c0r3ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setLeftBorder().setRightBorder().setWrapText().setFont(font).setWhiteBgColor();
		Style c1r3ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setRightBorder().setWrapText().setFont(font).setWhiteBgColor();
		Style c2r3ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style c3r3ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.000").setRightBorder().setWrapText().setFont(font).setWhiteBgColor();
		Style c4r3ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.000").setRightBorder().setWrapText().setFont(font).setWhiteBgColor();
		Style c5r3ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0").setRightBorder().setWrapText().setFont(font).setWhiteBgColor();
		Style c6r3ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0").setRightBorder().setWrapText().setFont(font).setWhiteBgColor();
		// Grand Total
		Style gTxtColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setWrapText().setFont(fontHD);
		Style gNumColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder(CellStyle.BORDER_DOUBLE).setWrapText().setFont(font);
		Style gDob3ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.000").setBottomBorder(CellStyle.BORDER_DOUBLE).setWrapText().setFont(font);

		// <!-- Get 'First' Sheet. -->
		HSSFSheet sheet = workbook.getSheetAt(0);

		// header customer
		HSSFRow row2 = sheet.getRow(2);
		row2.getCell(0).setCellValue("Customer : "+productCompositionList1.getCustomerCode()+" - "+productCompositionList1.getCustomerName());
		// Table 1
		HSSFRow row4 = sheet.getRow(4);
		if(productCompositionList1.getPlanList().size() > 0){
			List<TDeliveryPlan> pList = productCompositionList1.getPlanList();
			TDeliveryPlan[] details = pList.toArray( new TDeliveryPlan[pList.size()] );
			for(int p = 0; p < pList.size(); p++) {
				TDeliveryPlan detailList= details[p];
				row4.getCell(0).setCellValue(detailList.getPartNo());
				row4.getCell(2).setCellValue(detailList.getPartName());
				row4.getCell(3).setCellValue(detailList.getMaterialName());
				row4.getCell(4).setCellValue(detailList.getCategory());
				if(detailList.getUnitWeight() != null){
					row4.getCell(5).setCellValue(dob3Format.format(detailList.getUnitWeight()));
				}
				if(detailList.getSaleUnitPrice() != null){
					row4.getCell(6).setCellValue(dob4Format.format(detailList.getSaleUnitPrice()));
				}
				if(detailList.getDiecastCost() != null){
					row4.getCell(7).setCellValue(dob2Format.format(detailList.getDiecastCost()));
				}
				if(detailList.getMaterialCost() != null){
					row4.getCell(8).setCellValue(dob2Format.format(detailList.getMaterialCost()));
				}
				if(detailList.getProcessingCost() != null){
					row4.getCell(9).setCellValue(dob2Format.format(detailList.getProcessingCost()));
				}
			}
		}
		
		// Table 2
		int rowNumber = 7;
		int seq = 1;
		String keyPrev = "";
		HSSFRow row = null;
		List<TDeliveryPlan> p2List = productCompositionList2.getPlan2List();
		TDeliveryPlan[] details2 = p2List.toArray( new TDeliveryPlan[p2List.size()] );
		if(p2List.size() > 0){
			for(int p = 0, max = p2List.size(); p < max; p++) {
				TDeliveryPlan detailCurr= details2[p];
				row = sheet.createRow(rowNumber++);
				if(p == 0){
					keyPrev = detailCurr.getWip();
				}
				
				if(!detailCurr.getWip().equals(keyPrev) || p == 0){
					if( p == 0 ){
						TDeliveryPlan detailNext = details2[p+1 < max ? p+1 : max - 1];
						if( detailCurr.getWip().equals(detailNext.getWip()) ){
							createCell(workbook, row, 0,  c0r3ColumnStyle).setValue(seq);
							createCell(workbook, row, 1,  c1r3ColumnStyle).setValue(detailCurr.getWip()+" : "+detailCurr.getWipName());
							createCell(workbook, row, 2,  c2r3ColumnStyle).setValue(detailCurr.getOutSource());
							createCell(workbook, row, 3,  c3r3ColumnStyle).setValue(detailCurr.getMaterialCost(),true);
							createCell(workbook, row, 4,  c4r3ColumnStyle).setValue(detailCurr.getProcessingCost(),true);
							createCell(workbook, row, 5,  c5r3ColumnStyle).setValue(detailCurr.getCavQty());
							createCell(workbook, row, 6,  c6r3ColumnStyle).setValue(detailCurr.getStockControl());
							seq++;
						}else{
							createCell(workbook, row, 0,  c0r0ColumnStyle).setValue(seq);
							createCell(workbook, row, 1,  c1r0ColumnStyle).setValue(detailCurr.getWip()+" : "+detailCurr.getWipName());
							createCell(workbook, row, 2,  c2r0ColumnStyle).setValue(detailCurr.getOutSource());
							createCell(workbook, row, 3,  c3r0ColumnStyle).setValue(detailCurr.getMaterialCost(),true);
							createCell(workbook, row, 4,  c4r0ColumnStyle).setValue(detailCurr.getProcessingCost(),true);
							createCell(workbook, row, 5,  c5r0ColumnStyle).setValue(detailCurr.getCavQty());
							createCell(workbook, row, 6,  c6r0ColumnStyle).setValue(detailCurr.getStockControl());
							seq++;
						}
					}else{
						createCell(workbook, row, 0,  c0r0ColumnStyle).setValue(seq);
						createCell(workbook, row, 1,  c1r0ColumnStyle).setValue(detailCurr.getWip()+" : "+detailCurr.getWipName());
						createCell(workbook, row, 2,  c2r0ColumnStyle).setValue(detailCurr.getOutSource());
						createCell(workbook, row, 3,  c3r0ColumnStyle).setValue(detailCurr.getMaterialCost(),true);
						createCell(workbook, row, 4,  c4r0ColumnStyle).setValue(detailCurr.getProcessingCost(),true);
						createCell(workbook, row, 5,  c5r0ColumnStyle).setValue(detailCurr.getCavQty());
						createCell(workbook, row, 6,  c6r0ColumnStyle).setValue(detailCurr.getStockControl());
						seq++;
					}
				}else{
					createCell(workbook, row, 0,  c0r1ColumnStyle);
					createCell(workbook, row, 1,  c1r1ColumnStyle);
					createCell(workbook, row, 2,  c2r1ColumnStyle).setValue(detailCurr.getOutSource());
					createCell(workbook, row, 3,  c3r1ColumnStyle);
					createCell(workbook, row, 4,  c4r1ColumnStyle);
					createCell(workbook, row, 5,  c5r1ColumnStyle);
					createCell(workbook, row, 6,  c6r1ColumnStyle);
				}
					
				if(!detailCurr.getWip().equals(keyPrev)){
					keyPrev = detailCurr.getWip();
				}
			}
			//Grand Total
			row = sheet.createRow(rowNumber);
			createCell(workbook, row, 0,  gTxtColumnStyle).setValue("Grand Total");
			createCell(workbook, row, 1,  gTxtColumnStyle);
			createCell(workbook, row, 2,  gTxtColumnStyle);
			createMergedRegion(sheet,row.getRowNum(),row.getRowNum(), 0, 2);
			createCell(workbook, row, 3,  gDob3ColumnStyle);
			createCell(workbook, row, 4,  gDob3ColumnStyle);
			createCell(workbook, row, 5,  gNumColumnStyle);
			createCell(workbook, row, 6,  gNumColumnStyle);
			row.getCell(3).setCellFormula("SUM(D8:D"+rowNumber+")");
			row.getCell(4).setCellFormula("SUM(E8:E"+rowNumber+")");
			row.getCell(5).setCellFormula("SUM(F8:F"+rowNumber+")");
			row.getCell(6).setCellFormula("SUM(G8:G"+rowNumber+")");
		}
		// <!-- Setup 'Print Area'. -->
		workbook.setPrintArea(0, 0, 8, 0, rowNumber);
		sheet.getFooter().setCenter(
				"Note :\n" +
				" - Cost on Table 1 is summary of transaction history and Table 2 is summary of transaction by current process master\n"+			
				" - The Cycle time of Die-Cast Cost; calculate all of /\"OK Qty/\"  on daily actual(DC) "			
		);
	}
}