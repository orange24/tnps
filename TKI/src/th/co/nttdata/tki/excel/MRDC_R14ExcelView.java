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

public class MRDC_R14ExcelView extends AbstractExcelView{

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
		// <!-- Assign: CellStyle. -->
		Style fstHeaderColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setLeftBorder(CellStyle.BORDER_MEDIUM).setTopBorder(CellStyle.BORDER_MEDIUM).setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder(CellStyle.BORDER_MEDIUM).setFont(fontHD).setBgColor();
		Style hdColFullDateFormattedStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setTopBorder(CellStyle.BORDER_MEDIUM).setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder(CellStyle.BORDER_MEDIUM).setFormat("yy/mm/dd").setFont(fontHD).setWrapText().setBgColor();
		Style hdColAbbrDateFormattedStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setTopBorder(CellStyle.BORDER_MEDIUM).setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder(CellStyle.BORDER_MEDIUM).setFormat("yy/mm").setFont(fontHD).setWrapText().setBgColor();
		Style cusColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setWrapText().setFont(fontHD);
		
		Style txtColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setLeftBorder().setBottomBorder().setRightBorder().setWrapText();
		Style numColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText();
		Style dobColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.0000").setBottomBorder().setRightBorder().setWrapText();
		// Grand Total
		Style grandColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setWrapText().setFont(fontHD);
		Style gNumColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder(CellStyle.BORDER_DOUBLE).setWrapText();

		// <!-- Get 'First' Sheet. -->
		HSSFSheet sheet = workbook.getSheetAt(0);

		HSSFRow cusRow = sheet.getRow(2);
		createCell(workbook, cusRow, 0, cusColumnStyle);
		cusRow.getCell(0).setCellValue("Delivery Year/Month : "
				+deliveryPlan.getYearFr().toString().substring(2)
				+"/"+dFormat.format(deliveryPlan.getMonthFr()+1)
				+" - "+deliveryPlan.getYearTo().toString().substring(2)
				+"/"+dFormat.format(deliveryPlan.getMonthTo()+1));
		
		int rowNumber = 3;
		int columnDay = 4;
		Calendar todayCal = new GregorianCalendar(Locale.US)// current calendar
		, frCal = new GregorianCalendar(deliveryPlan.getYearFr(),deliveryPlan.getMonthFr(),1)// from 
		, toCal = new GregorianCalendar(deliveryPlan.getYearTo(),deliveryPlan.getMonthTo()+1,1)// to 
		;
		toCal.add(Calendar.DAY_OF_MONTH, -1);
		
		// start header
		List<HeaderColumn> headerList = new ArrayList<HeaderColumn>();
		
		if( frCal.get(Calendar.YEAR) == toCal.get(Calendar.YEAR) && frCal.get(Calendar.MONTH) == toCal.get(Calendar.MONTH)) {
			// SAME: From - To 
			if( frCal.get(Calendar.YEAR) == todayCal.get(Calendar.YEAR) && frCal.get(Calendar.MONTH) == todayCal.get(Calendar.MONTH) ) {
				// is current month
				for(;frCal.get(Calendar.YEAR) == todayCal.get(Calendar.YEAR) && frCal.get(Calendar.MONTH) == todayCal.get(Calendar.MONTH); frCal.add(Calendar.DAY_OF_MONTH, 1)) {
					headerList.add(new HeaderColumn(frCal.get(Calendar.YEAR),frCal.get(Calendar.MONTH),frCal.get(Calendar.DAY_OF_MONTH)));
				}
			} else {
				// is not current month
				headerList.add(new HeaderColumn(frCal.get(Calendar.YEAR),frCal.get(Calendar.MONTH)));
			}
		} else {
			// NOT SAME: From - To - TO > FROM
			for(; frCal.compareTo(toCal) < 1;) {
				if(frCal.get(Calendar.YEAR) == todayCal.get(Calendar.YEAR) && frCal.get(Calendar.MONTH) == todayCal.get(Calendar.MONTH)) {
					// if current month
					for(; frCal.get(Calendar.YEAR) == todayCal.get(Calendar.YEAR) && frCal.get(Calendar.MONTH) == todayCal.get(Calendar.MONTH); frCal.add(Calendar.DAY_OF_MONTH, 1)) {
						// loop for generating 1 - 31
						headerList.add(new HeaderColumn(frCal.get(Calendar.YEAR),frCal.get(Calendar.MONTH),frCal.get(Calendar.DAY_OF_MONTH)));
					}
				} else {
					// if not current month
					headerList.add(new HeaderColumn(frCal.get(Calendar.YEAR),frCal.get(Calendar.MONTH)));
					frCal.add(Calendar.MONTH, 1);
				}
			}
		}
		// insert header data
		HSSFRow row = sheet.createRow(rowNumber);
		createCell(workbook, row, 0,  fstHeaderColumnStyle).setValue("Part No");
		createCell(workbook, row, 1,  fstHeaderColumnStyle).setValue("Part Name");
		createCell(workbook, row, 2,  fstHeaderColumnStyle).setValue("Customer");
		createCell(workbook, row, 3,  hdColFullDateFormattedStyle).setValue("Sales Unit Price");
		for(HeaderColumn hd : headerList) {
			createCell(workbook, row, columnDay++, hd.isFulldate() ? hdColFullDateFormattedStyle : hdColAbbrDateFormattedStyle).setValue( hd.getTime() );
		}
		createCell(workbook, row, columnDay++,  hdColFullDateFormattedStyle).setValue("Total");
		
		if( deliveryPlan.getPlanList().size() > 0 ){
			// Data
			int i = 0, j = 0;
			int showType = deliveryPlan.getOutputCategory();
			int rowAmt = deliveryPlan.getPlanList().size();
			int colAmt = headerList.size();
			String defaultSumValue = (showType == 1 ? "0" : (showType == 2 ? "0.00" : "0/0.00"));
			Order sumTotal = new Order();
			Order[] planTotal = new Order[rowAmt];
			Order[] dateTotal = new Order[colAmt];
			Order[][] orders = new Order[rowAmt][colAmt];
			for( TDeliveryPlan plan : deliveryPlan.getPlanList() ) {
				j = 0;
				for( HeaderColumn hd : headerList ) {
					for( TDeliveryPlanDate date : plan.getDateList() ) {
						Calendar cal = new GregorianCalendar();
						cal.setTime(date.getDeliveryDate());

						if( hd.isFulldate() && date.getDeliveryDate().equals(hd.getTime()) ) {

							// <!-- Checking: Order Object. -->
							if( planTotal[i] == null )
								planTotal[i] = new Order();
							if( dateTotal[j] == null )
								dateTotal[j] = new Order();
							if( orders[i][j] == null )
								orders[i][j] = new Order();

							// <!-- Checking: Output Type. -->
							switch(showType) {
							case 1 :
								orders[i][j].setQuantity(date.getQty());
								planTotal[i].addQuantity(date.getQty());
								dateTotal[j].addQuantity(date.getQty());
								sumTotal.addQuantity(date.getQty());
								break;
							case 2 :
								orders[i][j].setPrice(date.getPrice());
								planTotal[i].addPrice(date.getPrice());
								dateTotal[j].addPrice(date.getPrice());
								sumTotal.addPrice(date.getPrice());
								break;
							default :
								orders[i][j].setQuantity(date.getQty());
								orders[i][j].setPrice(date.getPrice());
								planTotal[i].addQuantity(date.getQty()).addPrice(date.getPrice());
								dateTotal[j].addQuantity(date.getQty()).addPrice(date.getPrice());
								sumTotal.addQuantity(date.getQty()).addPrice(date.getPrice());
								break;
							}
						}
						if( !hd.isFulldate() && cal.get(Calendar.YEAR) == hd.getYear() && cal.get(Calendar.MONTH) == hd.getMonth() ) {

							// <!-- Checking: Order Object. -->
							if( planTotal[i] == null )
								planTotal[i] = new Order();
							if( dateTotal[j] == null )
								dateTotal[j] = new Order();
							if( orders[i][j] == null )
								orders[i][j] = new Order();

							// <!-- Checking: Output Type. -->
							switch(showType) {
							case 1 :
								if( orders[i][j].getQuantity() == null )
									orders[i][j].setQuantity(date.getQty());
								else
									orders[i][j].addQuantity(date.getQty());
								planTotal[i].addQuantity(date.getQty());
								dateTotal[j].addQuantity(date.getQty());
								sumTotal.addQuantity(date.getQty());
								break;
							case 2 :
								if( orders[i][j].getPrice() == null )
									orders[i][j].setPrice(date.getPrice());
								else
									orders[i][j].addPrice(date.getPrice());
								planTotal[i].addPrice(date.getPrice());
								dateTotal[j].addPrice(date.getPrice());
								sumTotal.addPrice(date.getPrice());
								break;
							default :
								if( orders[i][j].getQuantity() == null )
									orders[i][j].setQuantity(date.getQty());
								else
									orders[i][j].addQuantity(date.getQty());
								if( orders[i][j].getPrice() == null )
									orders[i][j].setPrice(date.getPrice());
								else
									orders[i][j].addPrice(date.getPrice());
								planTotal[i].addQuantity(date.getQty()).addPrice(date.getPrice());
								dateTotal[j].addQuantity(date.getQty()).addPrice(date.getPrice());
								sumTotal.addQuantity(date.getQty()).addPrice(date.getPrice());
								break;
							}
						}
					}
					j++;
				}
				i++;
			}
			// insert data
			rowNumber++;
			int m = 0;
			int sum = 0;
			int grandTotal = 0;
			for( TDeliveryPlan plan : deliveryPlan.getPlanList() ) {
				columnDay = 4;
				sum = 0;
				row = sheet.createRow(rowNumber);
				createCell(workbook, row, 0,  txtColumnStyle).setValue(plan.getPartNo());
				createCell(workbook, row, 1,  txtColumnStyle).setValue(plan.getPartName());
				createCell(workbook, row, 2,  txtColumnStyle).setValue(plan.getCustomerName());
				createCell(workbook, row, 3,  dobColumnStyle).setValue(plan.getSaleUnitPrice());
				for(int n = 0; n < colAmt; n++) {
					createCell(workbook, row, columnDay++, numColumnStyle).setValue(orders[m][n] == null ? null : orders[m][n].toString(showType));
				}
				createCell(workbook, row, columnDay, numColumnStyle).setValue(planTotal[m] == null ? defaultSumValue : planTotal[m].toString(showType));
				grandTotal += sum;
				m++;
				rowNumber++;
			}
			columnDay = 4;
			row = sheet.createRow(rowNumber);
			createCell(workbook, row, 0,  grandColumnStyle);
			createCell(workbook, row, 1,  grandColumnStyle);
			createCell(workbook, row, 2,  grandColumnStyle);
			createCell(workbook, row, 3,  grandColumnStyle).setValue("Grand Total");
			for(int g = 0; g < colAmt; g++ ) {
				createCell(workbook, row, columnDay++, gNumColumnStyle).setValue(dateTotal[g] == null ? defaultSumValue : dateTotal[g].toString(showType));
			}
			createCell(workbook, row, columnDay++,  gNumColumnStyle).setValue(sumTotal == null ? defaultSumValue : sumTotal.toString(showType));
		}
	}

	class HeaderColumn {
		private boolean fulldate;
		private int date;
		private int month;
		private int year;

		public HeaderColumn(int year, int month, int date) {
			this.year = year;
			this.month = month;
			this.date = date;

			this.fulldate = true;
		}
		public HeaderColumn(int year, int month) {
			this.year = year;
			this.month = month;
			this.date = 1;

			this.fulldate = false;
		}
		public boolean isFulldate() {
			return fulldate;
		}
		public void setFulldate(boolean fulldate) {
			this.fulldate = fulldate;
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
			return fulldate ? dateFormatter.format(getTime()) : MyyFormatter.format(getTime());
		}
	}
	class Order {
		private Double price;
		private Integer quantity;

		public Order() {
			this.price = null;
			this.quantity = null;
		}
		public Order(Integer quantity, Double price) {
			this.price = price;
			this.quantity = quantity;
		}
		public Order addPrice(Double price) {
			if( this.price == null)
				this.price = 0D;
			this.price += (price == null ? 0D : price);
			return this;
		}
		public Order addQuantity(Integer quantity) {
			if( this.quantity == null)
				this.quantity = 0;
			this.quantity += (quantity == null ? 0 : quantity);
			return this;
		}
		public Double getPrice() {
			return price;
		}
		public Integer getQuantity() {
			return quantity;
		}
		public void setPrice(Double price) {
			this.price = price;
		}
		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}
		public String toString(int showType) {
			if( showType == 1 )
				return (quantity == null ? null : numberFormatter.format(quantity));
			if( showType == 2 )
				return (price == null ? null : doubleFormatter.format(price));
			if( showType == 3 )
				return (quantity == null ? "" : numberFormatter.format(quantity)) +"/\n"+ (price == null ? "" : doubleFormatter.format(price));

			return null;
		}
	}

	static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yy/MM/dd", Locale.US);
	static final SimpleDateFormat MyyFormatter = new SimpleDateFormat("yy/MM", Locale.US);
	static final DecimalFormat numberFormatter = new DecimalFormat("#,##0");
	static final DecimalFormat doubleFormatter = new DecimalFormat("#,##0.00");
}