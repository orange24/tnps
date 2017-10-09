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

import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.bean.TDeliveryPlanDate;

public class MRDC_R15ExcelView extends AbstractExcelView{

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {
		
		TDeliveryPlan deliveryPlan = (TDeliveryPlan) model.get("deliveryPlan");
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
		Style txt1ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style txt2ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style txt3ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style txt5ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style txt6ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style txt8ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style txt9ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style rightColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style right13ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00\"%\"").setRightBorder().setWrapText().setFont(font).setWhiteBgColor();
		Style fstColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setLeftBorder().setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style num7ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style num10ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style num11ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dob214ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dob215ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dob216ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dob217ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dob218ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dob419ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.0000").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dob220ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dob3ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.000").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		// border medium top border
		Style txtBM1ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setTopBorder(HSSFCellStyle.BORDER_MEDIUM).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style txtBM2ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setTopBorder(HSSFCellStyle.BORDER_MEDIUM).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style txtBM3ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setTopBorder(HSSFCellStyle.BORDER_MEDIUM).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style txtBM5ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setTopBorder(HSSFCellStyle.BORDER_MEDIUM).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style txtBM6ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setTopBorder(HSSFCellStyle.BORDER_MEDIUM).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style txtBM8ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setTopBorder(HSSFCellStyle.BORDER_MEDIUM).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style txtBM9ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setTopBorder(HSSFCellStyle.BORDER_MEDIUM).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style rightBMColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setTopBorder(HSSFCellStyle.BORDER_MEDIUM).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style rightBM13ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00\"%\"").setTopBorder(HSSFCellStyle.BORDER_MEDIUM).setBottomBorder().setRightBorder().setWrapText().setFont(font).setWhiteBgColor();
		Style fstBMColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setTopBorder(HSSFCellStyle.BORDER_MEDIUM).setLeftBorder().setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style numBM7ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0").setTopBorder(HSSFCellStyle.BORDER_MEDIUM).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style numBM10ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0").setTopBorder(HSSFCellStyle.BORDER_MEDIUM).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style numBM11ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0").setTopBorder(HSSFCellStyle.BORDER_MEDIUM).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dob2BM14ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setTopBorder(HSSFCellStyle.BORDER_MEDIUM).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dob2BM15ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setTopBorder(HSSFCellStyle.BORDER_MEDIUM).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dob2BM16ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setTopBorder(HSSFCellStyle.BORDER_MEDIUM).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dob2BM17ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setTopBorder(HSSFCellStyle.BORDER_MEDIUM).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dob2BM18ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setTopBorder(HSSFCellStyle.BORDER_MEDIUM).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dob4BM19ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.0000").setTopBorder(HSSFCellStyle.BORDER_MEDIUM).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dob2BM20ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setTopBorder(HSSFCellStyle.BORDER_MEDIUM).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dob3BMColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.000").setTopBorder(HSSFCellStyle.BORDER_MEDIUM).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		// border medium button border
		Style txtBMList1ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder(HSSFCellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(font);
		Style txtBMList2ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder(HSSFCellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(font);
		Style txtBMList3ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder(HSSFCellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(font);
		Style txtBMList5ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder(HSSFCellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(font);
		Style txtBMList6ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder(HSSFCellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(font);
		Style txtBMList8ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder(HSSFCellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(font);
		Style txtBMList9ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder(HSSFCellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(font);
		Style rightBMListColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder(HSSFCellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(font);
		Style rightBM13ListColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00\"%\"").setRightBorder().setWrapText().setFont(font).setWhiteBgColor();
		Style fstBMListColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setLeftBorder().setBottomBorder(HSSFCellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(font);
		Style numBMList7ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0").setBottomBorder(HSSFCellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(font);
		Style numBMList10ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0").setBottomBorder(HSSFCellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(font);
		Style numBMList11ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0").setBottomBorder(HSSFCellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(font);
		Style dob2BMList14ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setBottomBorder(HSSFCellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(font);
		Style dob2BMList15ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setBottomBorder(HSSFCellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(font);
		Style dob2BMList16ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setBottomBorder(HSSFCellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(font);
		Style dob2BMList17ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setBottomBorder(HSSFCellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(font);
		Style dob2BMList18ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setBottomBorder(HSSFCellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(font);
		Style dob4BMList19ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.0000").setBottomBorder(HSSFCellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(font);
		Style dob2BMList20ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setBottomBorder(HSSFCellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(font);
		Style dob3BMListColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.000").setBottomBorder(HSSFCellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(font);
		// Grand Total
		Style gTxtColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setWrapText().setFont(fontHD);
		Style gNumColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder(CellStyle.BORDER_DOUBLE).setWrapText().setFont(font);
		Style gDobColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.00").setBottomBorder(CellStyle.BORDER_DOUBLE).setWrapText().setFont(font);
		Style gDob13ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.00").setTopBorder(CellStyle.BORDER_MEDIUM).setBottomBorder(CellStyle.BORDER_DOUBLE).setWrapText().setFont(font);

		// <!-- Get 'First' Sheet. -->
		HSSFSheet sheet = workbook.getSheetAt(0);

		HSSFRow row = null;
		boolean isMerged = false;
		int rowNumber = 3;
		int rowNumberOld = 3;
		String keyCurr = "";
		String keyPrev = "";
		if(deliveryPlan.getDateList().size() > 0){
			List<TDeliveryPlanDate> dList = deliveryPlan.getDateList();
			TDeliveryPlanDate[] details = dList.toArray( new TDeliveryPlanDate[dList.size()] );
			for(int p = 0; p < dList.size(); p++) {
				TDeliveryPlanDate detailList= details[p];
				row = sheet.createRow(rowNumber++);
				keyCurr = detailList.getCustomerId() + ":" + detailList.getPartId();
				if(p == 0){
					keyPrev = detailList.getCustomerId() + ":" + detailList.getPartId();
				}
				//Start new row
				if((!keyCurr.equals(keyPrev) && p != dList.size()-1) || p == 0){
					createCell(workbook, row, 0,  fstBMColumnStyle).setValue(detailList.getCustomerCode());
					createCell(workbook, row, 1,  txtBM1ColumnStyle).setValue(detailList.getCustomerName());
					createCell(workbook, row, 2,  txtBM2ColumnStyle).setValue(detailList.getPartNo());
					createCell(workbook, row, 3,  txtBM3ColumnStyle).setValue(detailList.getPartName());
					createCell(workbook, row, 4,  dob3BMColumnStyle).setValue(detailList.getUnitWeight());
					createCell(workbook, row, 5,  txtBM5ColumnStyle).setValue(detailList.getCategory());
					createCell(workbook, row, 6,  txtBM6ColumnStyle).setValue(detailList.getMaterialName());
					createCell(workbook, row, 7,  numBM7ColumnStyle).setValue(detailList.getSeq());
					createCell(workbook, row, 8,  txtBM8ColumnStyle).setValue(detailList.getWip());
					createCell(workbook, row, 9,  txtBM9ColumnStyle).setValue(detailList.getWipName());
					createCell(workbook, row, 10, numBM10ColumnStyle).setValue(detailList.getOk(), true);
					createCell(workbook, row, 11, numBM11ColumnStyle).setValue(detailList.getNg(), true);
					createCell(workbook, row, 12, rightBMColumnStyle).setValue(detailList.getNgRatio());
					Double ngYieldRatio = null;
					if(detailList.getNgYieldRatio() != null){
						ngYieldRatio = Double.parseDouble(detailList.getNgYieldRatio().split("%")[0]);
					}
					createCell(workbook, row, 13, rightBM13ColumnStyle).setValue(ngYieldRatio, true);
					createCell(workbook, row, 14, dob2BM14ColumnStyle).setValue(detailList.getNgCost(), true);
					createCell(workbook, row, 15, dob2BM15ColumnStyle).setValue(detailList.getMaterialCost(), true);
					createCell(workbook, row, 16, dob2BM16ColumnStyle).setValue(detailList.getDiecastCost(), true);
					createCell(workbook, row, 17, dob2BM17ColumnStyle).setValue(detailList.getProcessingCost(), true);
					createCell(workbook, row, 18, dob2BM18ColumnStyle);
					createCell(workbook, row, 19, dob4BM19ColumnStyle).setValue(detailList.getSellingPrice(), true);
					createCell(workbook, row, 20, dob2BM20ColumnStyle);
					isMerged = false;
				// Last row
				}else if(p == dList.size()-1 && !isMerged){
					createCell(workbook, row, 0,  fstBMListColumnStyle).setValue(detailList.getCustomerCode());
					createCell(workbook, row, 1,  txtBMList1ColumnStyle).setValue(detailList.getCustomerName());
					createCell(workbook, row, 2,  txtBMList2ColumnStyle).setValue(detailList.getPartNo());
					createCell(workbook, row, 3,  txtBMList3ColumnStyle).setValue(detailList.getPartName());
					createCell(workbook, row, 4,  dob3BMListColumnStyle).setValue(detailList.getUnitWeight());
					createCell(workbook, row, 5,  txtBMList5ColumnStyle).setValue(detailList.getCategory());
					createCell(workbook, row, 6,  txtBMList6ColumnStyle).setValue(detailList.getMaterialName());
					createCell(workbook, row, 7,  numBMList7ColumnStyle).setValue(detailList.getSeq());
					createCell(workbook, row, 8,  txtBMList8ColumnStyle).setValue(detailList.getWip());
					createCell(workbook, row, 9,  txtBMList9ColumnStyle).setValue(detailList.getWipName());
					createCell(workbook, row, 10, numBMList10ColumnStyle).setValue(detailList.getOk(), true);
					createCell(workbook, row, 11, numBMList11ColumnStyle).setValue(detailList.getNg(), true);
					createCell(workbook, row, 12, rightBMListColumnStyle).setValue(detailList.getNgRatio());
					createCell(workbook, row, 13, rightBM13ListColumnStyle);
					createCell(workbook, row, 14, dob2BMList14ColumnStyle).setValue(detailList.getNgCost(), true);
					createCell(workbook, row, 15, dob2BMList15ColumnStyle);
					createCell(workbook, row, 16, dob2BMList16ColumnStyle).setValue(detailList.getDiecastCost(), true);
					createCell(workbook, row, 17, dob2BMList17ColumnStyle).setValue(detailList.getProcessingCost(), true);
					createCell(workbook, row, 18, dob2BMList18ColumnStyle);
					createCell(workbook, row, 19, dob4BMList19ColumnStyle);
					createCell(workbook, row, 20, dob2BMList20ColumnStyle);
					isMerged = false;
				// Normal row
				}else{
					createCell(workbook, row, 0,  fstColumnStyle).setValue(detailList.getCustomerCode());
					createCell(workbook, row, 1,  txt1ColumnStyle).setValue(detailList.getCustomerName());
					createCell(workbook, row, 2,  txt2ColumnStyle).setValue(detailList.getPartNo());
					createCell(workbook, row, 3,  txt3ColumnStyle).setValue(detailList.getPartName());
					createCell(workbook, row, 4,  dob3ColumnStyle).setValue(detailList.getUnitWeight());
					createCell(workbook, row, 5,  txt5ColumnStyle).setValue(detailList.getCategory());
					createCell(workbook, row, 6,  txt6ColumnStyle).setValue(detailList.getMaterialName());
					createCell(workbook, row, 7,  num7ColumnStyle).setValue(detailList.getSeq());
					createCell(workbook, row, 8,  txt8ColumnStyle).setValue(detailList.getWip());
					createCell(workbook, row, 9,  txt9ColumnStyle).setValue(detailList.getWipName());
					createCell(workbook, row, 10, num10ColumnStyle).setValue(detailList.getOk(), true);
					createCell(workbook, row, 11, num11ColumnStyle).setValue(detailList.getNg(), true);
					createCell(workbook, row, 12, rightColumnStyle).setValue(detailList.getNgRatio());
					createCell(workbook, row, 13, right13ColumnStyle);
					createCell(workbook, row, 14, dob214ColumnStyle).setValue(detailList.getNgCost(), true);
					createCell(workbook, row, 15, dob215ColumnStyle);
					createCell(workbook, row, 16, dob216ColumnStyle).setValue(detailList.getDiecastCost(), true);
					createCell(workbook, row, 17, dob217ColumnStyle).setValue(detailList.getProcessingCost(), true);
					createCell(workbook, row, 18, dob218ColumnStyle);
					createCell(workbook, row, 19, dob419ColumnStyle);
					createCell(workbook, row, 20, dob220ColumnStyle);
					isMerged = false;
				}
				// Merged
				if(!keyCurr.equals(keyPrev)){
					keyPrev = detailList.getCustomerId() + ":" + detailList.getPartId();
					createMergedRegion(sheet,rowNumberOld, row.getRowNum()-1, 0, 0);
					createMergedRegion(sheet,rowNumberOld, row.getRowNum()-1, 1, 1);
					createMergedRegion(sheet,rowNumberOld, row.getRowNum()-1, 2, 2);
					createMergedRegion(sheet,rowNumberOld, row.getRowNum()-1, 3, 3);
					createMergedRegion(sheet,rowNumberOld, row.getRowNum()-1, 4, 4);
					createMergedRegion(sheet,rowNumberOld, row.getRowNum()-1, 5, 5);
					createMergedRegion(sheet,rowNumberOld, row.getRowNum()-1, 6, 6);
					createMergedRegion(sheet,rowNumberOld, row.getRowNum()-1, 15, 15);
					createMergedRegion(sheet,rowNumberOld, row.getRowNum()-1, 18, 18);
					createMergedRegion(sheet,rowNumberOld, row.getRowNum()-1, 19, 19);
					createMergedRegion(sheet,rowNumberOld, row.getRowNum()-1, 20, 20);
					HSSFRow rowOld = sheet.getRow(rowNumberOld);
					rowOld.getCell(18).setCellFormula("SUM(O" + (rowNumberOld + 1) + ":O" + (rowNumber - 1) + ") + SUM(P" + (rowNumberOld + 1) + ":P" + (rowNumber - 1) + ") + SUM(Q" + (rowNumberOld + 1) + ":Q" + (rowNumber - 1)+") + SUM(R" + (rowNumberOld + 1) + ":R" + (rowNumber - 1) + ")");
					rowOld.getCell(20).setCellFormula(rowOld.getCell(18).getCellFormula() + " - SUM(P" + (rowNumberOld + 1) + ":P" + (rowNumber - 1) + ")");
					rowNumberOld = row.getRowNum();
					isMerged = true;
				}
			}
			
			if(!isMerged){
				createMergedRegion(sheet,rowNumberOld, row.getRowNum(), 0, 0);
				createMergedRegion(sheet,rowNumberOld, row.getRowNum(), 1, 1);
				createMergedRegion(sheet,rowNumberOld, row.getRowNum(), 2, 2);
				createMergedRegion(sheet,rowNumberOld, row.getRowNum(), 3, 3);
				createMergedRegion(sheet,rowNumberOld, row.getRowNum(), 4, 4);
				createMergedRegion(sheet,rowNumberOld, row.getRowNum(), 5, 5);
				createMergedRegion(sheet,rowNumberOld, row.getRowNum(), 6, 6);
				createMergedRegion(sheet,rowNumberOld, row.getRowNum(), 15, 15);
				createMergedRegion(sheet,rowNumberOld, row.getRowNum(), 18, 18);
				createMergedRegion(sheet,rowNumberOld, row.getRowNum(), 19, 19);
				createMergedRegion(sheet,rowNumberOld, row.getRowNum(), 20, 20);
				HSSFRow rowOld = sheet.getRow(rowNumberOld);
				rowOld.getCell(18).setCellFormula("SUM(O" + (rowNumberOld + 1) + ":O" + rowNumber + ") + SUM(P" + (rowNumberOld + 1) + ":P" + rowNumber + ") + SUM(Q" + (rowNumberOld + 1) + ":Q" + rowNumber + ") + SUM(R" + (rowNumberOld + 1)+":R" + rowNumber + ")");
				rowOld.getCell(20).setCellFormula(rowOld.getCell(18).getCellFormula() + " - SUM(P" + (rowNumberOld + 1) + ":P" + rowNumber + ")");
			}
			
			// Grand Total
			row = sheet.createRow(rowNumber);
			createCell(workbook, row, 0,  gTxtColumnStyle).setValue("Grand Total");
			createCell(workbook, row, 1,  gTxtColumnStyle);
			createCell(workbook, row, 2,  gTxtColumnStyle);
			createCell(workbook, row, 3,  gTxtColumnStyle);
			createCell(workbook, row, 4,  gTxtColumnStyle);
			createCell(workbook, row, 5,  gTxtColumnStyle);
			createCell(workbook, row, 6,  gTxtColumnStyle);
			createCell(workbook, row, 7,  gTxtColumnStyle);
			createCell(workbook, row, 8,  gTxtColumnStyle);
			createCell(workbook, row, 9,  gTxtColumnStyle);
			createMergedRegion(sheet,row.getRowNum(),row.getRowNum(), 0, 9);
			createCell(workbook, row, 10, gNumColumnStyle);
			createCell(workbook, row, 11, gNumColumnStyle);
			createCell(workbook, row, 12, gDobColumnStyle);
			createCell(workbook, row, 13, gDob13ColumnStyle);
			createCell(workbook, row, 14, gDobColumnStyle);
			createCell(workbook, row, 15, gDobColumnStyle);
			createCell(workbook, row, 16, gDobColumnStyle);
			createCell(workbook, row, 17, gDobColumnStyle);
			createCell(workbook, row, 18, gDobColumnStyle);
			createCell(workbook, row, 19, gDobColumnStyle);
			createCell(workbook, row, 20, gDobColumnStyle);
			row.getCell(10).setCellFormula("SUM(K3:K"+rowNumber+")");
			row.getCell(11).setCellFormula("SUM(L3:L"+rowNumber+")");
			row.getCell(12).setCellFormula("FIXED(IF("+row.getCell(11).getCellFormula()+"<>0,("+row.getCell(11).getCellFormula()+"/("+row.getCell(10).getCellFormula()+"+"+row.getCell(11).getCellFormula()+")*100),0),2,FALSE) & \"%\"");
			row.getCell(13).setCellFormula("FIXED(SUM(N3:N"+rowNumber+"),2,FALSE)& \"%\"");
			row.getCell(14).setCellFormula("SUM(O3:O"+rowNumber+")");
			row.getCell(15).setCellFormula("SUM(P3:P"+rowNumber+")");
			row.getCell(16).setCellFormula("SUM(Q3:Q"+rowNumber+")");
			row.getCell(17).setCellFormula("SUM(R3:R"+rowNumber+")");
			row.getCell(18).setCellFormula("SUM(S3:S"+rowNumber+")");
			row.getCell(19).setCellFormula("FIXED(SUM(T3:T"+rowNumber+"),4,FALSE)");
			row.getCell(20).setCellFormula("SUM(U3:U"+rowNumber+")");
		}

		// <!-- Setup 'Print Area'. -->
		sheet.setRowBreak(rowNumber);
		workbook.setPrintArea(0, 0, 20, 0, rowNumber);
	}
}