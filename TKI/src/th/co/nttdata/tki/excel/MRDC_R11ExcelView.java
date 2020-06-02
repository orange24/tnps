package th.co.nttdata.tki.excel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.bean.TDeliveryPlanDate;

public class MRDC_R11ExcelView extends AbstractExcelView{

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {
		
		TDeliveryPlan deliveryPlan = (TDeliveryPlan) model.get("deliveryPlan");
		DecimalFormat dFormat = new DecimalFormat("00");
		
		// set Font Header
		HSSFFont fontHD = workbook.createFont();
		fontHD.setFontName("Calibri");
		fontHD.setFontHeightInPoints((short)11);
		fontHD.setBoldweight(Font.BOLDWEIGHT_BOLD);
		// set Font Header name report
		HSSFFont fontHDName = workbook.createFont();
		fontHDName.setFontName("Calibri");
		fontHDName.setFontHeightInPoints((short)16);
		fontHDName.setBoldweight(Font.BOLDWEIGHT_BOLD);
		// set Font
		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short)11);
		
		// <!-- Assign: CellStyle. -->
		Style headerColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setTopBorder().setBottomBorder().setRightBorder().setFont(fontHD).setWrapText().setBgColor();
		Style nameColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setFont(fontHDName).setWrapText();
		Style cusColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setWrapText().setFont(fontHD);
		Style hdColAbbrDateFormattedStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setTopBorder().setBottomBorder().setRightBorder().setFormat("yy/mm").setFont(fontHD).setWrapText().setBgColor();
		
		Style txtColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setLeftBorder().setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style numColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dobColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.00").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		// Grand Total
		Style gTxtColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setWrapText().setFont(fontHD);
		Style grandColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setWrapText().setFont(fontHD);
		Style gNumColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder(CellStyle.BORDER_DOUBLE).setWrapText().setFont(font);
		Style gDobColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.00").setBottomBorder(CellStyle.BORDER_DOUBLE).setWrapText().setFont(font);

		// <!-- Get 'First' Sheet. -->
		HSSFSheet sheet = workbook.getSheetAt(0);

		// header customer
		HSSFRow cusRow = sheet.getRow(2);
		createCell(workbook, cusRow, 0, cusColumnStyle);
		cusRow.getCell(0).setCellValue("Year/Month : "
				+deliveryPlan.getYearFr().toString().substring(2)
				+"/"+dFormat.format(deliveryPlan.getMonthFr()+1)
				+" - "+deliveryPlan.getYearTo().toString().substring(2)
				+"/"+dFormat.format(deliveryPlan.getMonthTo()+1));
			
		if( deliveryPlan.getPlanList().size() > 0 ){
			int rowNumber = 5;
			int columnMonth = 0;
			int columnHeader = 10;
			Calendar frCal = new GregorianCalendar(deliveryPlan.getYearFr(),deliveryPlan.getMonthFr(),1)// from 
			, toCal = new GregorianCalendar(deliveryPlan.getYearTo(),deliveryPlan.getMonthTo()+1,1)// to 
			;
			toCal.add(Calendar.DAY_OF_MONTH, -1);
		
			// start header
			List<HeaderColumn> headerList = new ArrayList<HeaderColumn>();
			if( frCal.get(Calendar.YEAR) == toCal.get(Calendar.YEAR) && frCal.get(Calendar.MONTH) == toCal.get(Calendar.MONTH)) {
				// SAME: From - To
				headerList.add(new HeaderColumn(frCal.get(Calendar.YEAR),frCal.get(Calendar.MONTH)));
			} else {
				// NOT SAME: From - To - TO > FROM
				for(; frCal.compareTo(toCal) < 1;) {
					headerList.add(new HeaderColumn(frCal.get(Calendar.YEAR),frCal.get(Calendar.MONTH)));
					frCal.add(Calendar.MONTH, 1);
				}
			}
			// insert header
			HSSFRow headerMonthRow = sheet.getRow(3);
			HSSFRow headerRow = sheet.getRow(4);
			for(HeaderColumn hd : headerList) {
				createCell(workbook, headerMonthRow, columnHeader++,  hdColAbbrDateFormattedStyle);
				createCell(workbook, headerMonthRow, columnHeader++,  hdColAbbrDateFormattedStyle);
				createMergedRegion(sheet,headerMonthRow.getRowNum(),headerMonthRow.getRowNum(), columnHeader-2, columnHeader-1);
				headerMonthRow.getCell(columnHeader-2).setCellValue(hd.getTime());
				createCell(workbook, headerRow, columnHeader-2,  headerColumnStyle).setValue("Qty");
				createCell(workbook, headerRow, columnHeader-1,  headerColumnStyle).setValue("Amount");
			}
			// insert data
			List<TDeliveryPlan> pList = deliveryPlan.getPlanList();
			TDeliveryPlan[] details = pList.toArray( new TDeliveryPlan[pList.size()] );
			int sumQty = 0;
			int gradnTotalQty = 0;
			double gradnTotalAmount = 0.00;
			HSSFRow row = null;
			for(int p = 0; p < pList.size(); p++) {
				row = sheet.createRow(rowNumber++);
				columnMonth = 0;
				sumQty = 0;
				TDeliveryPlan currDetail = details[p];
				createCell(workbook, row, columnMonth++,  txtColumnStyle).setValue(currDetail.getCustomerName());
				createCell(workbook, row, columnMonth++,  txtColumnStyle).setValue(currDetail.getPartNo());
				createCell(workbook, row, columnMonth++,  txtColumnStyle).setValue(currDetail.getPartName());
				createCell(workbook, row, columnMonth++,  txtColumnStyle).setValue(currDetail.getCategory());
				createCell(workbook, row, columnMonth++,  txtColumnStyle).setValue(currDetail.getMaterial());
				createCell(workbook, row, columnMonth++,  dobColumnStyle).setValue(currDetail.getUnitWeight());
				createCell(workbook, row, columnMonth++,  dobColumnStyle).setValue(currDetail.getUnitPrice());
				createCell(workbook, row, columnMonth++,  dobColumnStyle).setValue(currDetail.getCurrency());
				
				Cell totalQtyCell = createCell(workbook, row, columnMonth++,  numColumnStyle);
				Cell totalAmountCell = createCell(workbook, row, columnMonth++,  dobColumnStyle);
				List<TDeliveryPlanDate> dList = currDetail.getDateList();
				TDeliveryPlanDate[] detailDates = dList.toArray( new TDeliveryPlanDate[dList.size()] );
				for( HeaderColumn hd : headerList ) {
					createCell(workbook, row, columnMonth++,  numColumnStyle);
					createCell(workbook, row, columnMonth++,  dobColumnStyle);
					for(int d = 0; d < dList.size(); d++) {
						TDeliveryPlanDate currDetailDate = detailDates[d];
						Calendar cal = new GregorianCalendar(currDetailDate.getYear(),currDetailDate.getMonth()-1,1);
						if(cal.getTime().equals(hd.getTime())){
							if(currDetailDate.getQty() != null){
								row.getCell(columnMonth-2).setCellValue(currDetailDate.getQty());
							}
							if(currDetailDate.getAmount() != null){
								row.getCell(columnMonth-1).setCellValue(currDetailDate.getAmount());
							}
							sumQty += (currDetailDate.getQty() == null ? 0 : currDetailDate.getQty());
						}
					}
				}
				totalQtyCell.setValue(sumQty);
				totalAmountCell.setValue((currDetail.getUnitPrice() == null ? 0 : currDetail.getUnitPrice())*sumQty);
				gradnTotalQty += sumQty;
				gradnTotalAmount += (currDetail.getUnitPrice() == null ? 0 : currDetail.getUnitPrice())*sumQty;
			}
			
			// GrandTotal
			row = sheet.createRow(rowNumber++);
			int lastColumn = columnMonth;
			columnMonth = 0;
			createCell(workbook, row, columnMonth++,  gTxtColumnStyle);
			createCell(workbook, row, columnMonth++,  gTxtColumnStyle);
			createCell(workbook, row, columnMonth++,  gTxtColumnStyle);
			createCell(workbook, row, columnMonth++,  gTxtColumnStyle);
			createCell(workbook, row, columnMonth++,  gTxtColumnStyle);
			createCell(workbook, row, columnMonth++,  gTxtColumnStyle);
			columnMonth++;
			createCell(workbook, row, columnMonth++,  grandColumnStyle).setValue("Grand Total");
			createCell(workbook, row, columnMonth++,  gNumColumnStyle).setValue(gradnTotalQty);
			createCell(workbook, row, columnMonth++,  gDobColumnStyle).setValue(gradnTotalAmount);
			int gQty = 0;
			double gAmount = 0.00;
			for(int col = 10; col < lastColumn; col++){
				gQty = 0;
				gAmount = 0.00;
				for(int r = 5; r < (rowNumber-1); r++){
					if(col % 2 == 0){
						gQty += sheet.getRow(r).getCell(col).getNumericCellValue();
					}else{
						gAmount += sheet.getRow(r).getCell(col).getNumericCellValue();
					}
				}
				if(col % 2 == 0){
					createCell(workbook, row, columnMonth++,  gNumColumnStyle).setValue(gQty,true);
				}else{
					createCell(workbook, row, columnMonth++,  gDobColumnStyle).setValue(gAmount,true);
				}
			}
			// header name report
			HSSFRow nameReportRow = sheet.getRow(0);
			for(int col = 0; col < lastColumn-1; col++){
				createCell(workbook, nameReportRow, col, nameColumnStyle);
			}
			createMergedRegion(sheet,0,0, 0, lastColumn-1);
			nameReportRow.getCell(0).setCellValue("Report Sales Summary");
		}// end if deliveryPlan.getPlanList().size() > 0
	}
	
	public class HeaderColumn {
		private int date;
		private int month;
		private int year;

		public HeaderColumn(int year, int month) {
			this.year = year;
			this.month = month;
			this.date = 1;
		}
		public int getDate() {
			return date;
		}
		public void setDate(int date) {
			this.date = date;
		}
		public int getMonth() {
			return month;
		}
		public void setMonth(int month) {
			this.month = month;
		}
		public int getYear() {
			return year;
		}
		public void setYear(int year) {
			this.year = year;
		}
		public Date getTime() {
			return new GregorianCalendar(year, month, date).getTime();
		}
		public String toString() {
			return yyMMFormatter.format(getTime());
		}
	}
	final SimpleDateFormat yyMMFormatter = new SimpleDateFormat("yy/MM", Locale.US);
}