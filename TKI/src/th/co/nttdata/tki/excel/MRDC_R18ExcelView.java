package th.co.nttdata.tki.excel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.bean.TDeliveryPlanDate;

public class MRDC_R18ExcelView extends AbstractExcelView{

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {
		
		TDeliveryPlan dSummary = (TDeliveryPlan) model.get("deliveryPlanSummary");
		TDeliveryPlan dProcessList2_Order = (TDeliveryPlan) model.get("deliveryPlanProcessList2_Order");
		TDeliveryPlan dProcessList2_Sales = (TDeliveryPlan) model.get("deliveryPlanProcessList2_Sales");
		TDeliveryPlan wipOfPart = (TDeliveryPlan) model.get("wipOfpart");
		TDeliveryPlan dProcessList3 = (TDeliveryPlan) model.get("deliveryPlanProcessList3");
		TDeliveryPlan dProcessList4 = (TDeliveryPlan) model.get("deliveryPlanProcessList4");
		TDeliveryPlan dProcessList5 = (TDeliveryPlan) model.get("deliveryPlanProcessList5");
		DecimalFormat dFormat = new DecimalFormat("00");
		DecimalFormat numFormat = new DecimalFormat("#,##0");
		DecimalFormat dobFormat = new DecimalFormat("#,##0.000");
		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		
		// set Font Header
		HSSFFont fontTotal = workbook.createFont();
		fontTotal.setFontName("Calibri");
		fontTotal.setFontHeightInPoints((short)11);
		fontTotal.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		// <!-- Assign: CellStyle. -->
		Style totalColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setTopBorder(CellStyle.BORDER_MEDIUM).setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder(CellStyle.BORDER_MEDIUM).setFont(fontTotal).setWrapText();
		Style totalTBL3Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setTopBorder(CellStyle.BORDER_MEDIUM).setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setFont(fontTotal).setWrapText();
		Style txtColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setLeftBorder().setBottomBorder().setRightBorder().setWrapText();
		Style txtBColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setLeftBorder().setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setWrapText();
		Style numColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText();
		Style numBColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setWrapText();
		Style leftColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setRightBorder(CellStyle.BORDER_MEDIUM).setFont(fontTotal).setWrapText();
		Style noColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setRightBorder(CellStyle.BORDER_MEDIUM).setWrapText();
		Style noBColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder(CellStyle.BORDER_MEDIUM).setWrapText();
		Style numNoColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setRightBorder(CellStyle.BORDER_MEDIUM).setWrapText();
		Style orderLstColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder(CellStyle.BORDER_MEDIUM).setWrapText();
		Style salesLstColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder(CellStyle.BORDER_MEDIUM).setWrapText();

		// <!-- Get 'First' Sheet. -->
		HSSFSheet sheet = workbook.getSheetAt(0);

		// header customer
		HSSFRow row2 = sheet.getRow(2);
		HSSFRow row4 = sheet.getRow(4);
		HSSFRow row6 = sheet.getRow(6);
		String dateCurr = dSummary.getYear().toString()+dFormat.format(dSummary.getMonth()+1);
		row2.getCell(0).setCellValue("Current Year/Month : "
				+dSummary.getYear().toString().substring(2)
				+"/"+dFormat.format(dSummary.getMonth()+1));
		row4.getCell(0).setCellValue(dSummary.getPartNo());
		row4.getCell(3).setCellValue(dSummary.getPartName());
		row4.getCell(9).setCellValue(dSummary.getCustomerCode());
		// Table 1
		if( dSummary.getPlanList().size() > 0 ){
			List<TDeliveryPlan> pList = dSummary.getPlanList();
			TDeliveryPlan[] details = pList.toArray( new TDeliveryPlan[pList.size()] );
			for(int p = 0; p < pList.size(); p++) {
				TDeliveryPlan detailSummary = details[p];
				if(detailSummary.getYearmonth().equals(dateCurr)){
					row4.getCell(15).setCellValue(detailSummary.getMaterialName());
					row4.getCell(19).setCellValue(dobFormat.format(detailSummary.getUnitWeight()));
					if(detailSummary.getNormalQty()!= null){
						row6.getCell(3).setCellValue(numFormat.format(detailSummary.getNormalQty()));
					}
					if(detailSummary.getOrderQty()!= null){
						row6.getCell(12).setCellValue(numFormat.format(detailSummary.getOrderQty()));
					}
					if(detailSummary.getRemainQty()!= null){
						row6.getCell(15).setCellValue(numFormat.format(detailSummary.getRemainQty()));
					}
				}else if(detailSummary.getYearmonth().equals(dSummary.getYearmonthFr())){
					if(detailSummary.getNormalQty()!= null){
						row6.getCell(0).setCellValue(numFormat.format(detailSummary.getNormalQty()));
					}
					if(detailSummary.getReturnQty()!= null){
						row6.getCell(6).setCellValue(numFormat.format(detailSummary.getReturnQty()));
					}
					if(detailSummary.getRemainQty()!= null){
						row6.getCell(9).setCellValue(numFormat.format(detailSummary.getRemainQty()));
					}
				}else if(detailSummary.getYearmonth().equals(dSummary.getYearmonthTo())){
					if(detailSummary.getForecastQty()!= null){
						row6.getCell(18).setCellValue(numFormat.format(detailSummary.getForecastQty()));
					}
				}
			}
		}// end if dSummary.getPlanList().size() > 0
		// Table 2
		int dayOfMonth = dSummary.getDayOfMonth();// day of month
		HSSFRow row8 = sheet.getRow(8);
		HSSFRow row9 = sheet.getRow(9);
		HSSFRow row10 = sheet.getRow(10);
		HSSFRow row12 = sheet.getRow(12);
		// <!-- set style header day -->
		if(dayOfMonth < 31){
			for(int i = 32; i > dayOfMonth+1; i--){
				row8.getCell(i).setCellValue("");
				row8.getCell(i).setCellStyle(workbook.createCellStyle());
				row9.getCell(i).setCellValue("");
				row9.getCell(i).setCellStyle(workbook.createCellStyle());
				row10.getCell(i).setCellValue("");
				row10.getCell(i).setCellStyle(workbook.createCellStyle());
				row12.getCell(i).setCellValue("");
				row12.getCell(i).setCellStyle(workbook.createCellStyle());
			}
		}
		createCell(workbook, row8, dayOfMonth+2,  totalColumnStyle).setValue("Total");
		createCell(workbook, row9, dayOfMonth+2,  orderLstColumnStyle).setValue(0,true);
		createCell(workbook, row10,dayOfMonth+2,  salesLstColumnStyle).setValue(0,true);
		createCell(workbook, row12,dayOfMonth+2,  totalTBL3Style).setValue("Total");
		createCell(workbook, row12,dayOfMonth+3,  totalColumnStyle).setValue("Stock Summary");
		sheet.setColumnWidth((dayOfMonth+3),(20*256));// set width last column
		int column = 2;
		int totalOrderQty = 0;
		// Order
		if( dProcessList2_Order.getDate2OrderList().size() > 0 ){
			List<TDeliveryPlanDate> dList2Order = dProcessList2_Order.getDate2OrderList();
			TDeliveryPlanDate[] detailsList2 = dList2Order.toArray( new TDeliveryPlanDate[dList2Order.size()] );
			Calendar dayFr = new GregorianCalendar(dSummary.getYear(),dSummary.getMonth(),1)//From
			, dayTo = new GregorianCalendar(dSummary.getYear(),dSummary.getMonth(),dayOfMonth);// to 
			for(; dayFr.compareTo(dayTo) < 1;) {
				String key = dateFormat.format(dayFr.getTime());
				for(int p = 0; p < dList2Order.size(); p++) {
					TDeliveryPlanDate detailList2= detailsList2[p];
					if(key.equals(dateFormat.format(detailList2.getDeliveryDate()))){
						if(detailList2.getOrderQty() != null){
							row9.getCell(column).setCellValue(numFormat.format(detailList2.getOrderQty()));
						}
						totalOrderQty += detailList2.getOrderQty() == null ? 0 :detailList2.getOrderQty();
					}
				}
				column++;
				dayFr.add(Calendar.DATE, 1);
			}
			row9.getCell(column).setCellValue(numFormat.format(totalOrderQty));
		}// end if dProcessList2.getDateList().size() > 0 
		// Sales
		int totalSalesQty = 0;
		column = 2;
		if( dProcessList2_Sales.getDate2SalesList().size() > 0 ){
			List<TDeliveryPlanDate> dList2Sales = dProcessList2_Sales.getDate2SalesList();
			TDeliveryPlanDate[] detailsList2 = dList2Sales.toArray( new TDeliveryPlanDate[dList2Sales.size()] );
			Calendar dayFr = new GregorianCalendar(dSummary.getYear(),dSummary.getMonth(),1)//From
			, dayTo = new GregorianCalendar(dSummary.getYear(),dSummary.getMonth(),dayOfMonth);// to 
			for(; dayFr.compareTo(dayTo) < 1;) {
				String key = dateFormat.format(dayFr.getTime());
				for(int p = 0; p < dList2Sales.size(); p++) {
					TDeliveryPlanDate detailList2= detailsList2[p];
					if(key.equals(dateFormat.format(detailList2.getDeliveryDate()))){
						if(detailList2.getSalesQty() != null){
							row10.getCell(column).setCellValue(numFormat.format(detailList2.getSalesQty()));
						}
						totalSalesQty += detailList2.getSalesQty() == null ? 0 : detailList2.getSalesQty();
					}
				}
				column++;
				dayFr.add(Calendar.DATE, 1);
			}
			row10.getCell(column).setCellValue(numFormat.format(totalSalesQty));
		}// end if dProcessList2.getDateList().size() > 0 
		
		// Table 3
		int sum1 = 0; // Plan
		int sum2 = 0; // In
		int sum3 = 0; // Out
		int sum4 = 0; // NG
		int rowNumber = 13;
		HSSFRow rowOne = null;
		HSSFRow rowTwo = null;
		HSSFRow rowThree = null;
		HSSFRow rowFour = null;
		HSSFRow rowFive = null;
		HSSFRow rowSix = null;
		for(MWip mWip : wipOfPart.getWipList()) {
			rowOne = sheet.createRow(rowNumber++);
			rowTwo = sheet.createRow(rowNumber++);
			rowThree = sheet.createRow(rowNumber++);
			rowFour = sheet.createRow(rowNumber++);
			rowFive = sheet.createRow(rowNumber++);
			rowSix = sheet.createRow(rowNumber++);
			column = 2;
			sum1 = 0;
			sum2 = 0;
			sum3 = 0;
			sum4 = 0;
			createCell(workbook, rowOne, 0,  txtColumnStyle).setValue(mWip.getWip()+" : "+mWip.getWipName());
			createCell(workbook, rowOne, 1,  txtColumnStyle).setValue("Plan");
			createCell(workbook, rowTwo, 0,  txtColumnStyle);
			createCell(workbook, rowTwo, 1,  txtColumnStyle);
			createCell(workbook, rowThree, 0,  txtColumnStyle);
			createCell(workbook, rowThree, 1,  txtColumnStyle).setValue("In");
			createCell(workbook, rowFour, 0,  txtColumnStyle);
			createCell(workbook, rowFour, 1,  txtColumnStyle).setValue("Out");
			createCell(workbook, rowFive, 0,  txtColumnStyle);
			createCell(workbook, rowFive, 1,  txtColumnStyle).setValue("NG");
			createCell(workbook, rowSix, 0,  txtBColumnStyle);
			createCell(workbook, rowSix, 1,  txtBColumnStyle);
			List<TDeliveryPlanDate> dList3 = dProcessList3.getDate3List();
			TDeliveryPlanDate[] detailsList3 = dList3.toArray( new TDeliveryPlanDate[dList3.size()] );
			List<TDeliveryPlanDate> dList5 = dProcessList5.getDate5List();
			TDeliveryPlanDate[] detailsList5 = dList5.toArray( new TDeliveryPlanDate[dList5.size()] );
			Calendar dayFr = new GregorianCalendar(dSummary.getYear(),dSummary.getMonth(),1)//From
			, dayTo = new GregorianCalendar(dSummary.getYear(),dSummary.getMonth(),dayOfMonth);// to 
			for(; dayFr.compareTo(dayTo) < 1;) {
				String key = dateFormat.format(dayFr.getTime())+":"+mWip.getWip();
				createCell(workbook, rowOne, column,  numColumnStyle);
				createCell(workbook, rowTwo, column,  numColumnStyle);
				createCell(workbook, rowThree, column,  numColumnStyle);
				createCell(workbook, rowFour, column,  numColumnStyle);
				createCell(workbook, rowFive, column,  numColumnStyle);
				createCell(workbook, rowSix, column,  numBColumnStyle);
				createMergedRegion(sheet,rowOne.getRowNum(),rowTwo.getRowNum(), column, column);
				//createMergedRegion(sheet,rowThree.getRowNum(),rowFour.getRowNum(), column, column);
				createMergedRegion(sheet,rowFive.getRowNum(),rowSix.getRowNum(), column, column);
				// Plan
				for(int p = 0; p < dList5.size(); p++) {
					TDeliveryPlanDate detailList5= detailsList5[p];
					String keyPlan = dateFormat.format(detailList5.getDeliveryDate())+":"+detailList5.getWip();
					if(key.equals(keyPlan)){
						if(detailList5.getDeadlineQty() != null){
							rowOne.getCell(column).setCellValue(detailList5.getDeadlineQty());
							sum1 += detailList5.getDeadlineQty();
						}
					}
				}
				// StockIn, StockOut, NG
				for(int p = 0; p < dList3.size(); p++) {
					TDeliveryPlanDate detailList3= detailsList3[p];
					String keyData = dateFormat.format(detailList3.getDeliveryDate())+":"+detailList3.getWip();
					if(key.equals(keyData)){
						if(detailList3.getStockInQty() != null){
							rowThree.getCell(column).setCellValue(detailList3.getStockInQty());
							sum2 += detailList3.getStockInQty();
						}
						if(detailList3.getStockOutQty() != null){
							rowFour.getCell(column).setCellValue(detailList3.getStockOutQty());
							sum3 += detailList3.getStockOutQty();
						}
						if(detailList3.getNg() != null){
							rowFive.getCell(column).setCellValue(detailList3.getNg());
							sum4 += detailList3.getNg();
						}
					}
				}
				column++;
				dayFr.add(Calendar.DATE, 1);
				// Total
				createCell(workbook, rowOne, column,  numColumnStyle).setValue(sum1,true);
				createCell(workbook, rowTwo, column,  numColumnStyle);
				createCell(workbook, rowThree, column,  numColumnStyle).setValue(sum2,true);
				createCell(workbook, rowFour, column,  numColumnStyle).setValue(sum3,true);
				createCell(workbook, rowFive, column,  numColumnStyle).setValue(sum4,true);
				createCell(workbook, rowSix, column,  numBColumnStyle);
			}
			// merge Total tabel3
				createMergedRegion(sheet,rowOne.getRowNum(),rowTwo.getRowNum(), column, column);
				createMergedRegion(sheet,rowFive.getRowNum(),rowSix.getRowNum(), column, column);
				column++;
			
			// Stock Summary
			createCell(workbook, rowOne, column,  leftColumnStyle).setValue("The Beginning :");
			createCell(workbook, rowTwo, column,  numNoColumnStyle);
			createCell(workbook, rowThree, column,  noColumnStyle);
			createCell(workbook, rowFour, column,  leftColumnStyle).setValue("The End of Month :");
			createCell(workbook, rowFive, column,  numNoColumnStyle);
			createCell(workbook, rowSix, column,  noBColumnStyle);
			List<TDeliveryPlanDate> dList4 = dProcessList4.getDate4List();
			TDeliveryPlanDate[] detailsList4 = dList4.toArray( new TDeliveryPlanDate[dList4.size()] );
				for(int p = 0; p < dList4.size(); p++) {
					TDeliveryPlanDate detailList4= detailsList4[p];
					if(mWip.getWip().equals(detailList4.getWip())){
						if(detailList4.getStartStockQty() != null){
							rowTwo.getCell(column).setCellValue(detailList4.getStartStockQty());
						}
						if(detailList4.getEndStockQty() != null){
							rowFive.getCell(column).setCellValue(detailList4.getEndStockQty());
						}
					}
				}
			
			createMergedRegion(sheet,rowOne.getRowNum(),rowSix.getRowNum(), 0, 0);
			createMergedRegion(sheet,rowOne.getRowNum(),rowTwo.getRowNum(), 1, 1);
			//createMergedRegion(sheet,rowThree.getRowNum(),rowFour.getRowNum(), 1, 1);
			createMergedRegion(sheet,rowFive.getRowNum(),rowSix.getRowNum(), 1, 1);
		}
		
		// <!-- Setup 'Print Area'. -->
		workbook.setPrintArea(0, 0, 33, 0, rowNumber);
	}
}