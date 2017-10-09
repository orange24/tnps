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

import th.co.nttdata.tki.bean.TDeliveryPlan;

public class MRDC_R04ExcelView extends AbstractExcelView{

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {
		
		TDeliveryPlan productMaster = (TDeliveryPlan) model.get("productMaster");
		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		DecimalFormat dob3Format = new DecimalFormat("#,##0.000");
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
		Style txtColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style rightColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style centerColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style fstColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setLeftBorder().setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style numColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dob2ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dob3ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.000").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		// Grand Total
		Style gTxtColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setWrapText().setFont(fontHD);
		Style gNumColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0").setBottomBorder(CellStyle.BORDER_DOUBLE).setWrapText().setFont(font);
		Style gDobColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setBottomBorder(CellStyle.BORDER_DOUBLE).setWrapText().setFont(font);

		// <!-- Get 'First' Sheet. -->
		HSSFSheet sheet = workbook.getSheetAt(0);

		// Table 1
		HSSFRow row = null;
		boolean isMerged = false;
		int rowNumber = 3;
		int oldRow = 3;
		int count = 0;
		String prevKey = "";
		String currKey = "";
		Double saleUnitPriceTotal = 0.000;
		Double unitWeightTotal = 0.000;
		if(productMaster.getPlanList().size() > 0){
			List<TDeliveryPlan> pList = productMaster.getPlanList();
			TDeliveryPlan[] details = pList.toArray( new TDeliveryPlan[pList.size()] );
			for(int p = 0; p < pList.size(); p++) {
				row = sheet.createRow(rowNumber++);
				TDeliveryPlan detailList= details[p];
				if(p == 0){
					prevKey = detailList.getPartId()+":"+detailList.getCustomerId();
				}
				currKey = detailList.getPartId()+":"+detailList.getCustomerId();
				if(!prevKey.equals(currKey) || p == 0){
					createCell(workbook, row, 0,  fstColumnStyle).setValue(detailList.getCategory());
					createCell(workbook, row, 1,  txtColumnStyle).setValue(detailList.getCustomerCode());
					createCell(workbook, row, 2,  centerColumnStyle).setValue(detailList.getCustomerName());
					createCell(workbook, row, 3,  txtColumnStyle).setValue(detailList.getPartNo());
					createCell(workbook, row, 4,  txtColumnStyle).setValue(detailList.getPartName());
					createCell(workbook, row, 5,  txtColumnStyle).setValue(detailList.getRemark());
					createCell(workbook, row, 6,  txtColumnStyle).setValue(detailList.getMaterialName());
					createCell(workbook, row, 7,  dob3ColumnStyle).setValue(detailList.getSaleUnitPrice(),true);
					createCell(workbook, row, 8,  dob3ColumnStyle).setValue(detailList.getUnitWeight(),true);
					createCell(workbook, row, 9,  dob2ColumnStyle).setValue(detailList.getDiecastCost(),true);
					createCell(workbook, row, 10, dob2ColumnStyle).setValue(detailList.getMaterialCost(),true);
					createCell(workbook, row, 11, dob2ColumnStyle).setValue(detailList.getProcessingCost(),true);
					createCell(workbook, row, 12,  rightColumnStyle).setValue(detailList.getProductRate());
					createCell(workbook, row, 13,  numColumnStyle).setValue(detailList.getQty(),true);
					createCell(workbook, row, 14,  txtColumnStyle).setValue(detailList.getMoldName());
					createCell(workbook, row, 15,  rightColumnStyle).setValue(detailList.getMoldNo());
					createCell(workbook, row, 16,  rightColumnStyle).setValue(detailList.getCavNo());
					createCell(workbook, row, 17,  centerColumnStyle);
					createCell(workbook, row, 18,  numColumnStyle).setValue(detailList.getInitialShotQty(),true);
					createCell(workbook, row, 19,  numColumnStyle).setValue(detailList.getMaxShotQty(),true);
					if(detailList.getMoldUpdatedDate() != null){
						row.getCell(17).setCellValue(dateFormat.format(detailList.getMoldUpdatedDate()));
					}
					if(detailList.getSaleUnitPrice() != null){
						saleUnitPriceTotal += detailList.getSaleUnitPrice();
					}
					if(detailList.getUnitWeight() != null){
						unitWeightTotal += detailList.getUnitWeight();
					}
					isMerged = false;
				}else{
					createCell(workbook, row, 0,  fstColumnStyle).setValue(detailList.getCategory());
					createCell(workbook, row, 1,  txtColumnStyle).setValue(detailList.getCustomerCode());
					createCell(workbook, row, 2,  centerColumnStyle).setValue(detailList.getCustomerName());
					createCell(workbook, row, 3,  txtColumnStyle).setValue(detailList.getPartNo());
					createCell(workbook, row, 4,  txtColumnStyle).setValue(detailList.getPartName());
					createCell(workbook, row, 5,  txtColumnStyle).setValue(detailList.getRemark());
					createCell(workbook, row, 6,  txtColumnStyle).setValue(detailList.getMaterialName());
					createCell(workbook, row, 7,  dob3ColumnStyle);
					createCell(workbook, row, 8,  dob3ColumnStyle);
					createCell(workbook, row, 9,  dob2ColumnStyle);
					createCell(workbook, row, 10, dob2ColumnStyle);
					createCell(workbook, row, 11, dob2ColumnStyle);
					createCell(workbook, row, 12,  rightColumnStyle).setValue(detailList.getProductRate());
					createCell(workbook, row, 13,  numColumnStyle).setValue(detailList.getQty(),true);
					createCell(workbook, row, 14,  txtColumnStyle).setValue(detailList.getMoldName());
					createCell(workbook, row, 15,  rightColumnStyle).setValue(detailList.getMoldNo());
					createCell(workbook, row, 16,  rightColumnStyle).setValue(detailList.getCavNo());
					createCell(workbook, row, 17,  centerColumnStyle);
					createCell(workbook, row, 18,  numColumnStyle).setValue(detailList.getInitialShotQty(),true);
					createCell(workbook, row, 19,  numColumnStyle).setValue(detailList.getMaxShotQty(),true);
					if(detailList.getMoldUpdatedDate() != null){
						row.getCell(17).setCellValue(dateFormat.format(detailList.getMoldUpdatedDate()));
					}
					isMerged = false;
					count++;
				}
				if(!prevKey.equals(currKey)){
					if(count > 0){
						createMergedRegion(sheet,oldRow,(row.getRowNum()-1), 0, 0);
						createMergedRegion(sheet,oldRow,(row.getRowNum()-1), 1, 1);
						createMergedRegion(sheet,oldRow,(row.getRowNum()-1), 2, 2);
						createMergedRegion(sheet,oldRow,(row.getRowNum()-1), 3, 3);
						createMergedRegion(sheet,oldRow,(row.getRowNum()-1), 4, 4);
						createMergedRegion(sheet,oldRow,(row.getRowNum()-1), 5, 5);
						createMergedRegion(sheet,oldRow,(row.getRowNum()-1), 6, 6);
						createMergedRegion(sheet,oldRow,(row.getRowNum()-1), 7, 7);
						createMergedRegion(sheet,oldRow,(row.getRowNum()-1), 8, 8);
						createMergedRegion(sheet,oldRow,(row.getRowNum()-1), 9, 9);
						createMergedRegion(sheet,oldRow,(row.getRowNum()-1), 10, 10);
						createMergedRegion(sheet,oldRow,(row.getRowNum()-1), 11, 11);
						createMergedRegion(sheet,oldRow,(row.getRowNum()-1), 12, 12);
						createMergedRegion(sheet,oldRow,(row.getRowNum()-1), 13, 13);
					}
					oldRow = row.getRowNum();
					prevKey = detailList.getPartId()+":"+detailList.getCustomerId();
					isMerged = true;
					count = 0;
				}
			}
			if(!isMerged){
				createMergedRegion(sheet,oldRow,(row.getRowNum()), 0, 0);
				createMergedRegion(sheet,oldRow,(row.getRowNum()), 1, 1);
				createMergedRegion(sheet,oldRow,(row.getRowNum()), 2, 2);
				createMergedRegion(sheet,oldRow,(row.getRowNum()), 3, 3);
				createMergedRegion(sheet,oldRow,(row.getRowNum()), 4, 4);
				createMergedRegion(sheet,oldRow,(row.getRowNum()), 5, 5);
				createMergedRegion(sheet,oldRow,(row.getRowNum()), 6, 6);
				createMergedRegion(sheet,oldRow,(row.getRowNum()), 7, 7);
				createMergedRegion(sheet,oldRow,(row.getRowNum()), 8, 8);
				createMergedRegion(sheet,oldRow,(row.getRowNum()), 9, 9);
				createMergedRegion(sheet,oldRow,(row.getRowNum()), 10, 10);
				createMergedRegion(sheet,oldRow,(row.getRowNum()), 11, 11);
				createMergedRegion(sheet,oldRow,(row.getRowNum()), 12, 12);
				createMergedRegion(sheet,oldRow,(row.getRowNum()), 13, 13);
			}
		}
		// Grand Total
		row = sheet.createRow(rowNumber);
		createCell(workbook, row, 6,  gTxtColumnStyle).setValue("Grand Total");
		createCell(workbook, row, 7,  gDobColumnStyle).setValue(dob3Format.format(saleUnitPriceTotal));
		createCell(workbook, row, 8,  gDobColumnStyle).setValue(dob3Format.format(unitWeightTotal));
		createCell(workbook, row, 9,  gDobColumnStyle);
		createCell(workbook, row, 10,  gDobColumnStyle);
		createCell(workbook, row, 11,  gDobColumnStyle);
		createCell(workbook, row, 18,  gNumColumnStyle);
		createCell(workbook, row, 19,  gNumColumnStyle);
		row.getCell(9).setCellFormula("SUM(J3:J"+rowNumber+")");
		row.getCell(10).setCellFormula("SUM(K3:K"+rowNumber+")");
		row.getCell(11).setCellFormula("SUM(L3:L"+rowNumber+")");
		row.getCell(18).setCellFormula("SUM(S3:S"+rowNumber+")");
		row.getCell(19).setCellFormula("SUM(T3:T"+rowNumber+")");
		
		// <!-- Setup 'Print Area'. -->
		workbook.setPrintArea(0, 0, 19, 0, rowNumber);
	}
}