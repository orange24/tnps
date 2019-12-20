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

import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.bean.TDeliveryPlanDate;

public class MRDC_R20ExcelView extends AbstractExcelView{

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {
		
		TDeliveryPlan deliveryPlan = (TDeliveryPlan) model.get("deliveryPlan");
		final SimpleDateFormat yyMMFormatter = new SimpleDateFormat("yy/MM", Locale.US);
		DecimalFormat dFormat = new DecimalFormat("00");
		DecimalFormat dobFormat = new DecimalFormat("#,##0.00");
		
		// set Font Header
		HSSFFont fontHD = workbook.createFont();
		fontHD.setFontName("Calibri");
		fontHD.setFontHeightInPoints((short)11);
		fontHD.setBoldweight(Font.BOLDWEIGHT_BOLD);
		// set Font
		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short)11);
		
		Style txtColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style txtCenterStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style fstColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setLeftBorder().setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style lstColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setRightBorder(CellStyle.BORDER_MEDIUM).setWrapText().setFont(font);
		Style numColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dob2ColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		// Total
		Style tFstTxtColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setLeftBorder().setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(fontHD);
		Style tLstTxtColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder(CellStyle.BORDER_MEDIUM).setWrapText().setFont(fontHD);
		Style tTxtColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(fontHD);
		Style tCenColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(fontHD);
		Style tNumColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(font);
		Style tDobColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.00").setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(font);
		// Grand Total
		Style gTxtColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setWrapText().setFont(fontHD);
		Style gCenColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder(CellStyle.BORDER_DOUBLE).setWrapText().setFont(font);
		Style gNumColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder(CellStyle.BORDER_DOUBLE).setWrapText().setFont(font);
		Style gDobColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.00").setBottomBorder(CellStyle.BORDER_DOUBLE).setWrapText().setFont(font);

		// <!-- Get 'First' Sheet. -->
		HSSFSheet sheet = workbook.getSheetAt(0);

		// header customer
		HSSFRow row1 = sheet.getRow(1);
		row1.getCell(0).setCellValue("Year/Month : "
				+deliveryPlan.getYear().toString().substring(2)
				+"/"+dFormat.format(deliveryPlan.getMonth()+1));
		HSSFRow row2 = sheet.getRow(2);
		int columnHeader = 4;
		Calendar frCal = new GregorianCalendar(deliveryPlan.getYearFr(),deliveryPlan.getMonthFr(),1)// from 
		, toCal = new GregorianCalendar(deliveryPlan.getYear(),deliveryPlan.getMonth(),1)// to 
		;
		// start header
		for(; frCal.compareTo(toCal) < 1;) {
			row2.getCell(columnHeader).setCellValue(yyMMFormatter.format(frCal.getTime()));
			columnHeader++;
			frCal.add(Calendar.MONTH, 1);
		}
		// Start Detail
		HSSFRow row = null;
		boolean isMerged = false;
		
		int rowNumber 	= 4;
		int rowNumberOld = 4;
		int hour 		= 0; // Time (Second) No yield
		int min  		= 0; // Time (Second) No yield
		int sec  		= 0; // Time (Second) No yield
		int hrTotal 	= 0; // Time (Second) No yield
		int minTotal  	= 0; // Time (Second) No yield
		int secTotal  	= 0; // Time (Second) No yield
		int hour2 		= 0; // Time (Second) yield
		int min2		= 0; // Time (Second) yield
		int sec2  		= 0; // Time (Second) yield
		int hrTotal2 	= 0; // Time (Second) yield
		int minTotal2  	= 0; // Time (Second) yield
		int secTotal2  	= 0; // Time (Second) yield
		String keyCurr 	= "";
		String keyPrev 	= "";
		if( deliveryPlan.getDateList().size() > 0 ){
			List<TDeliveryPlanDate> dList = deliveryPlan.getDateList();
			TDeliveryPlanDate[] details = dList.toArray( new TDeliveryPlanDate[dList.size()] );
			for(int p = 0; p < dList.size(); p++) {
				TDeliveryPlanDate detailList= details[p];
				row = sheet.createRow(rowNumber++);
				keyCurr = detailList.getCustomerId()+":"+detailList.getPartId();
				if(p == 0){
					keyPrev = detailList.getCustomerId()+":"+detailList.getPartId();
				}
				if(!keyCurr.equals(keyPrev)){
					createMergedRegion(sheet,rowNumberOld, row.getRowNum()-1, 0, 0);
					createMergedRegion(sheet,rowNumberOld, row.getRowNum()-1, 1, 1);
					createMergedRegion(sheet,rowNumberOld, row.getRowNum()-1, 2, 2);
					createCell(workbook, row, 0,  tFstTxtColumnStyle).setValue("Total");
					createCell(workbook, row, 1,  tTxtColumnStyle);
					createCell(workbook, row, 2,  tTxtColumnStyle);
					createCell(workbook, row, 3,  tTxtColumnStyle);
					createMergedRegion(sheet,row.getRowNum(), row.getRowNum(), 0, 3);
					createCell(workbook, row, 4,  tNumColumnStyle);
					createCell(workbook, row, 5,  tNumColumnStyle);
					createCell(workbook, row, 6,  tNumColumnStyle);
					createCell(workbook, row, 7,  tNumColumnStyle);
					createCell(workbook, row, 8,  tNumColumnStyle);
					createCell(workbook, row, 9,  tNumColumnStyle);
					createCell(workbook, row, 10, tNumColumnStyle);
					createCell(workbook, row, 11, tNumColumnStyle);
					createCell(workbook, row, 12, tNumColumnStyle);
					createCell(workbook, row, 13, tNumColumnStyle);
					createCell(workbook, row, 14, tNumColumnStyle);
					createCell(workbook, row, 15, tNumColumnStyle);
					createCell(workbook, row, 16, tNumColumnStyle);
					createCell(workbook, row, 17, tNumColumnStyle);
					createCell(workbook, row, 18, tNumColumnStyle);
					createCell(workbook, row, 19, tDobColumnStyle);
					createCell(workbook, row, 20, tCenColumnStyle).setValue(this.calTime(hour, min, sec));
					createCell(workbook, row, 21, tCenColumnStyle).setValue(this.calTime(hour2, min2, sec2));
					createCell(workbook, row, 22, tLstTxtColumnStyle);
					row.getCell(4).setCellFormula("SUM(E"+(rowNumberOld+1)+":E"+row.getRowNum()+")");
					row.getCell(5).setCellFormula("SUM(F"+(rowNumberOld+1)+":F"+row.getRowNum()+")");
					row.getCell(6).setCellFormula("SUM(G"+(rowNumberOld+1)+":G"+row.getRowNum()+")");
					row.getCell(7).setCellFormula("SUM(H"+(rowNumberOld+1)+":H"+row.getRowNum()+")");
					row.getCell(8).setCellFormula("SUM(I"+(rowNumberOld+1)+":I"+row.getRowNum()+")");
					row.getCell(9).setCellFormula("SUM(J"+(rowNumberOld+1)+":J"+row.getRowNum()+")");
					row.getCell(10).setCellFormula("SUM(K"+(rowNumberOld+1)+":K"+row.getRowNum()+")");
					row.getCell(12).setCellFormula("SUM(M"+(rowNumberOld+1)+":M"+row.getRowNum()+")");
					row.getCell(13).setCellFormula("SUM(N"+(rowNumberOld+1)+":N"+row.getRowNum()+")");
					row.getCell(14).setCellFormula("SUM(O"+(rowNumberOld+1)+":O"+row.getRowNum()+")");
					row.getCell(15).setCellFormula("SUM(P"+(rowNumberOld+1)+":P"+row.getRowNum()+")");
					row.getCell(16).setCellFormula("SUM(Q"+(rowNumberOld+1)+":Q"+row.getRowNum()+")");
					row.getCell(17).setCellFormula("SUM(R"+(rowNumberOld+1)+":R"+row.getRowNum()+")");
					row.getCell(18).setCellFormula("SUM(S"+(rowNumberOld+1)+":S"+row.getRowNum()+")");
					row.getCell(19).setCellFormula("SUM(T"+(rowNumberOld+1)+":T"+row.getRowNum()+")");
//					row.getCell(20).setCellFormula("SUM(U"+(rowNumberOld+1)+":U"+row.getRowNum()+")");
//					row.getCell(21).setCellFormula("SUM(V"+(rowNumberOld+1)+":V"+row.getRowNum()+")");
					keyPrev = detailList.getCustomerId()+":"+detailList.getPartId();
					row = sheet.createRow(rowNumber++);
					rowNumberOld = row.getRowNum();
					isMerged = true;
					// grand total
					hrTotal += hour;
					minTotal += min;
					secTotal += sec;
					hour = 0;
					min  = 0;
					sec  = 0;
					hrTotal2 += hour2;
					minTotal2 += min2;
					secTotal2 += sec2;
					hour2 = 0;
					min2  = 0;
					sec2  = 0;
				}
				createCell(workbook, row, 0,  fstColumnStyle).setValue(detailList.getCustomerName());
				createCell(workbook, row, 1,  txtColumnStyle).setValue(detailList.getPartNo());
				createCell(workbook, row, 2,  txtColumnStyle).setValue(detailList.getPartName());
				createCell(workbook, row, 3,  txtColumnStyle).setValue(detailList.getWipName());
				createCell(workbook, row, 4,  numColumnStyle).setValue(detailList.getQty1(),true);
				createCell(workbook, row, 5,  numColumnStyle).setValue(detailList.getQty2(),true);
				createCell(workbook, row, 6,  numColumnStyle).setValue(detailList.getQty3(),true);
				createCell(workbook, row, 7,  numColumnStyle).setValue(detailList.getQty4(),true);
				createCell(workbook, row, 8,  numColumnStyle).setValue(detailList.getQty5(),true);
				createCell(workbook, row, 9,  numColumnStyle).setValue(detailList.getQty6(),true);
				createCell(workbook, row, 10, numColumnStyle).setValue(detailList.getTotalQty(),true);
				createCell(workbook, row, 11, dob2ColumnStyle).setValue(dobFormat.format(detailList.getYieldPercent())+"%");
				createCell(workbook, row, 12, numColumnStyle).setValue(detailList.getForecastNoYield(),true);
				createCell(workbook, row, 13, numColumnStyle).setValue(detailList.getForecastYield(),true);
				createCell(workbook, row, 14, numColumnStyle).setValue(detailList.getCustreqNoYield(),true);
				createCell(workbook, row, 15, numColumnStyle).setValue(detailList.getCustreqYield(),true);
				createCell(workbook, row, 16, numColumnStyle).setValue(detailList.getCommitNoYield(),true);
				createCell(workbook, row, 17, numColumnStyle).setValue(detailList.getCommitYield(),true);
				createCell(workbook, row, 18, numColumnStyle).setValue(detailList.getStock(),true);
				createCell(workbook, row, 19, dob2ColumnStyle).setValue(detailList.getSt(),true);
				createCell(workbook, row, 20, txtCenterStyle).setValue(detailList.getStTimeNoYield());
				createCell(workbook, row, 21, txtCenterStyle).setValue(detailList.getStTimeYield());
				createCell(workbook, row, 22, lstColumnStyle).setValue(detailList.getMachineName());
				isMerged = false;
				
				// sum time
				
				if(detailList.getStTimeNoYield() != null){
					String[] h1Tmp = detailList.getStTimeNoYield().split(":");
					hour += Integer.parseInt(h1Tmp[0]);
					min += Integer.parseInt(h1Tmp[1]);
					sec += Integer.parseInt(h1Tmp[2]);
				}
				
//				hour += (detailList.getStTimeNoYield() != null?Integer.parseInt(detailList.getStTimeNoYield().substring(0,2)):0);
//				min  += (detailList.getStTimeNoYield() != null?Integer.parseInt(detailList.getStTimeNoYield().substring(3,5)):0);
//				sec  += (detailList.getStTimeNoYield() != null?Integer.parseInt(detailList.getStTimeNoYield().substring(6)):0);

				// sum time2

				if(detailList.getStTimeYield() != null){
					String[] h1Tmp = detailList.getStTimeYield().split(":");
					hour2 += Integer.parseInt(h1Tmp[0]);
					min2 += Integer.parseInt(h1Tmp[1]);
					sec2 += Integer.parseInt(h1Tmp[2]);
				}
				
//				hour2 += (detailList.getStTimeYield() != null?Integer.parseInt(detailList.getStTimeYield().substring(0,2)):0);
//				min2  += (detailList.getStTimeYield() != null?Integer.parseInt(detailList.getStTimeYield().substring(3,5)):0);
//				sec2  += (detailList.getStTimeYield() != null?Integer.parseInt(detailList.getStTimeYield().substring(6)):0);
				
			}
			if(!isMerged){
				createMergedRegion(sheet,rowNumberOld, row.getRowNum(), 0, 0);
				createMergedRegion(sheet,rowNumberOld, row.getRowNum(), 1, 1);
				createMergedRegion(sheet,rowNumberOld, row.getRowNum(), 2, 2);
				row = sheet.createRow(rowNumber++);
				createCell(workbook, row, 0,  tFstTxtColumnStyle).setValue("Total");
				createCell(workbook, row, 1,  tTxtColumnStyle);
				createCell(workbook, row, 2,  tTxtColumnStyle);
				createCell(workbook, row, 3,  tTxtColumnStyle);
				createMergedRegion(sheet,row.getRowNum(), row.getRowNum(), 0, 3);
				createCell(workbook, row, 4,  tNumColumnStyle);
				createCell(workbook, row, 5,  tNumColumnStyle);
				createCell(workbook, row, 6,  tNumColumnStyle);
				createCell(workbook, row, 7,  tNumColumnStyle);
				createCell(workbook, row, 8,  tNumColumnStyle);
				createCell(workbook, row, 9,  tNumColumnStyle);
				createCell(workbook, row, 10, tNumColumnStyle);
				createCell(workbook, row, 11, tNumColumnStyle);
				createCell(workbook, row, 12, tNumColumnStyle);
				createCell(workbook, row, 13, tNumColumnStyle);
				createCell(workbook, row, 14, tNumColumnStyle);
				createCell(workbook, row, 15, tNumColumnStyle);
				createCell(workbook, row, 16, tNumColumnStyle);
				createCell(workbook, row, 17, tNumColumnStyle);
				createCell(workbook, row, 18, tNumColumnStyle);
				createCell(workbook, row, 19, tDobColumnStyle);
				createCell(workbook, row, 20, tCenColumnStyle).setValue(this.calTime(hour, min, sec));
				createCell(workbook, row, 21, tCenColumnStyle).setValue(this.calTime(hour2, min2, sec2));
				createCell(workbook, row, 22, tLstTxtColumnStyle);
				row.getCell(4).setCellFormula("SUM(E"+(rowNumberOld+1)+":E"+row.getRowNum()+")");
				row.getCell(5).setCellFormula("SUM(F"+(rowNumberOld+1)+":F"+row.getRowNum()+")");
				row.getCell(6).setCellFormula("SUM(G"+(rowNumberOld+1)+":G"+row.getRowNum()+")");
				row.getCell(7).setCellFormula("SUM(H"+(rowNumberOld+1)+":H"+row.getRowNum()+")");
				row.getCell(8).setCellFormula("SUM(I"+(rowNumberOld+1)+":I"+row.getRowNum()+")");
				row.getCell(9).setCellFormula("SUM(J"+(rowNumberOld+1)+":J"+row.getRowNum()+")");
				row.getCell(10).setCellFormula("SUM(K"+(rowNumberOld+1)+":K"+row.getRowNum()+")");
				row.getCell(12).setCellFormula("SUM(M"+(rowNumberOld+1)+":M"+row.getRowNum()+")");
				row.getCell(13).setCellFormula("SUM(N"+(rowNumberOld+1)+":N"+row.getRowNum()+")");
				row.getCell(14).setCellFormula("SUM(O"+(rowNumberOld+1)+":O"+row.getRowNum()+")");
				row.getCell(15).setCellFormula("SUM(P"+(rowNumberOld+1)+":P"+row.getRowNum()+")");
				row.getCell(16).setCellFormula("SUM(Q"+(rowNumberOld+1)+":Q"+row.getRowNum()+")");
				row.getCell(17).setCellFormula("SUM(R"+(rowNumberOld+1)+":R"+row.getRowNum()+")");
				row.getCell(18).setCellFormula("SUM(S"+(rowNumberOld+1)+":S"+row.getRowNum()+")");
				row.getCell(19).setCellFormula("SUM(T"+(rowNumberOld+1)+":T"+row.getRowNum()+")");
//				row.getCell(20).setCellFormula("SUM(U"+(rowNumberOld+1)+":U"+row.getRowNum()+")");
//				row.getCell(21).setCellFormula("SUM(V"+(rowNumberOld+1)+":V"+row.getRowNum()+")");
				// grand total
				hrTotal += hour;
				minTotal += min;
				secTotal += sec;
				hour = 0;
				min  = 0;
				sec  = 0;
				hrTotal2 += hour2;
				minTotal2 += min2;
				secTotal2 += sec2;
				hour2 = 0;
				min2  = 0;
				sec2  = 0;
			}
			// Grand Total
			row = sheet.createRow(rowNumber);
			createCell(workbook, row, 0,  gTxtColumnStyle).setValue("Grand Total");
			createCell(workbook, row, 1,  gTxtColumnStyle);
			createCell(workbook, row, 2,  gTxtColumnStyle);
			createCell(workbook, row, 3,  gTxtColumnStyle);
			createMergedRegion(sheet,row.getRowNum(), row.getRowNum(), 0, 3);
			createCell(workbook, row, 4,  gNumColumnStyle);
			createCell(workbook, row, 5,  gNumColumnStyle);
			createCell(workbook, row, 6,  gNumColumnStyle);
			createCell(workbook, row, 7,  gNumColumnStyle);
			createCell(workbook, row, 8,  gNumColumnStyle);
			createCell(workbook, row, 9,  gNumColumnStyle);
			createCell(workbook, row, 10, gNumColumnStyle);
			createCell(workbook, row, 11, gTxtColumnStyle);
			createCell(workbook, row, 12, gNumColumnStyle);
			createCell(workbook, row, 13, gNumColumnStyle);
			createCell(workbook, row, 14, gNumColumnStyle);
			createCell(workbook, row, 15, gNumColumnStyle);
			createCell(workbook, row, 16, gNumColumnStyle);
			createCell(workbook, row, 17, gNumColumnStyle);
			createCell(workbook, row, 18, gNumColumnStyle);
			createCell(workbook, row, 19, gDobColumnStyle);
			createCell(workbook, row, 20, gCenColumnStyle).setValue(this.calTime(hrTotal, minTotal, secTotal));
			createCell(workbook, row, 21, gCenColumnStyle).setValue(this.calTime(hrTotal2, minTotal2, secTotal2));
			createCell(workbook, row, 22, gTxtColumnStyle);
			row.getCell(4).setCellFormula("SUMIF(A5:A"+ rowNumber +",\"Total\",E5:E"+ rowNumber +")");
			row.getCell(5).setCellFormula("SUMIF(A5:A"+ rowNumber +",\"Total\",F5:F"+ rowNumber +")");
			row.getCell(6).setCellFormula("SUMIF(A5:A"+ rowNumber +",\"Total\",G5:G"+ rowNumber +")");
			row.getCell(7).setCellFormula("SUMIF(A5:A"+ rowNumber +",\"Total\",H5:H"+ rowNumber +")");
			row.getCell(8).setCellFormula("SUMIF(A5:A"+ rowNumber +",\"Total\",I5:I"+ rowNumber +")");
			row.getCell(9).setCellFormula("SUMIF(A5:A"+ rowNumber +",\"Total\",J5:J"+ rowNumber +")");
			row.getCell(10).setCellFormula("SUMIF(A5:A"+ rowNumber +",\"Total\",K5:K"+ rowNumber +")");
			row.getCell(12).setCellFormula("SUMIF(A5:A"+ rowNumber +",\"Total\",M5:M"+ rowNumber +")");
			row.getCell(13).setCellFormula("SUMIF(A5:A"+ rowNumber +",\"Total\",N5:N"+ rowNumber +")");
			row.getCell(14).setCellFormula("SUMIF(A5:A"+ rowNumber +",\"Total\",O5:O"+ rowNumber +")");
			row.getCell(15).setCellFormula("SUMIF(A5:A"+ rowNumber +",\"Total\",P5:P"+ rowNumber +")");
			row.getCell(16).setCellFormula("SUMIF(A5:A"+ rowNumber +",\"Total\",Q5:Q"+ rowNumber +")");
			row.getCell(17).setCellFormula("SUMIF(A5:A"+ rowNumber +",\"Total\",R5:R"+ rowNumber +")");
			row.getCell(18).setCellFormula("SUMIF(A5:A"+ rowNumber +",\"Total\",S5:S"+ rowNumber +")");
			row.getCell(19).setCellFormula("SUMIF(A5:A"+ rowNumber +",\"Total\",T5:T"+ rowNumber +")");
//			row.getCell(20).setCellFormula("SUMIF(A5:A"+ rowNumber +",\"Total\",U5:U"+ rowNumber +")");
//			row.getCell(21).setCellFormula("SUMIF(A5:A"+ rowNumber +",\"Total\",V5:V"+ rowNumber +")");
		}// end if deliveryPlan.getPlanList().size() > 0
		
		// <!-- Setup 'Print Area'. -->
		sheet.setRowBreak(rowNumber);
		workbook.setPrintArea(0, 0, 20, 0, rowNumber);
	}
	
	public String calTime(int hour,int min,int sec){
		//calculate Time
		String time = "";
		int ss = sec%60;
		int mm = (min+(sec/60))%60;
		int hh = hour+((min+(sec/60))/60);
		if(hh < 10){
			time = "0"+hh;
		}else{
			time = ""+hh;
		}
		if(mm < 10){
			time += ":0"+mm;
		}else{
			time += ":"+mm;
		}
		if(ss < 10){
			time += ":0"+ss;
		}else{
			time += ":"+ss;
		}		
		return time;
	}
}