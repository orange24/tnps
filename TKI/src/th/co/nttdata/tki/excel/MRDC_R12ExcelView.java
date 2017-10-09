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

import th.co.nttdata.tki.bean.TDailyWK;
import th.co.nttdata.tki.bean.TDailyWKDetail;

public class MRDC_R12ExcelView extends AbstractExcelView{

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {
		TDailyWK tDailyWK = (TDailyWK) model.get("tDailyWK");
		final SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

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
		Style fstColumnStyle   	= createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setLeftBorder().setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style rightColumnStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style totalColumnStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setLeftBorder().setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(fontHD);
		Style sumTimeStyle     	= createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(fontHD);
		Style sumColumnStyle   	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0").setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(fontHD);
		Style sumDobColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setWrapText().setFont(fontHD);
		Style lstColumnStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder(CellStyle.BORDER_MEDIUM).setWrapText().setFont(font);
		Style txtColumnStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style c13ColumnStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setRightBorder(CellStyle.BORDER_MEDIUM).setWrapText().setFont(font);
		Style leftColumnStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style numColumnStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dobColumnStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dobPencentColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00\"%\"").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		// Grand Total
		Style grandColumnStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setWrapText().setFont(fontHD);
		Style gSumColumnStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0").setBottomBorder(CellStyle.BORDER_DOUBLE).setWrapText().setFont(fontHD);
		Style gSumDoubleColumnStyle	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setBottomBorder(CellStyle.BORDER_DOUBLE).setWrapText().setFont(fontHD);
		Style gtxtColumnStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setFont(fontHD);
		Style GrandTimeStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setFont(fontHD).setWrapText().setBottomBorder(CellStyle.BORDER_DOUBLE).setFont(fontHD);

		// <!-- Get 'First' Sheet. -->
		HSSFSheet sheet = workbook.getSheetAt(0);
		
		int rowNumber = 4;
		int startPart = 4;
		// init time
		int hour = 0;
		int min  = 0;
		int sec  = 0;
		int hrTotal = 0;
		int minTotal  = 0;
		int secTotal  = 0;
		HSSFRow row = null;
		TDailyWKDetail currDetail = null;
		TDailyWKDetail nextDetail = null;
		
		if(tDailyWK.getReportDateFr() != null){
			sheet.getRow(2).getCell(0).setCellValue("Operation Date (From) : "+ dFormat.format(tDailyWK.getReportDateFr()));
		}
		if(tDailyWK.getReportDateTo() != null){
			sheet.getRow(2).getCell(2).setCellValue("Operation Date (To) : "+ dFormat.format(tDailyWK.getReportDateTo()));
		}
		
		List<TDailyWKDetail> dList = tDailyWK.getDailyWKDetailList();
		TDailyWKDetail[] details = dList.toArray( new TDailyWKDetail[dList.size()] );
		if(dList.size() > 0){
			for( int i = 0, max = dList.size(); i < max ; i++ ) {
				currDetail = details[i];
				nextDetail = details[i+1 < max ? i+1 : max - 1];
				row = sheet.createRow(rowNumber++);
				
				// detail
				createCell(workbook, row, 0,  fstColumnStyle).setValue(currDetail.getPartNo());
				createCell(workbook, row, 1,  leftColumnStyle).setValue(currDetail.getPartName());
				createCell(workbook, row, 2,  leftColumnStyle).setValue(currDetail.getCustomerName());
				createCell(workbook, row, 3,  leftColumnStyle).setValue(currDetail.getWipName());
				createCell(workbook, row, 4,  txtColumnStyle).setValue(dFormat.format(currDetail.getReportDate()));
				createCell(workbook, row, 5,  leftColumnStyle).setValue(currDetail.getMachineName());
				createCell(workbook, row, 6,  txtColumnStyle).setValue(currDetail.getStaff());
				createCell(workbook, row, 7,  numColumnStyle).setValue(currDetail.getOk());
				createCell(workbook, row, 8,  numColumnStyle).setValue(currDetail.getNg());
				createCell(workbook, row, 9,  rightColumnStyle).setValue(currDetail.getNgRatio());
				createCell(workbook, row, 10,  txtColumnStyle).setValue(currDetail.getnOperationTime());
				createCell(workbook, row, 11, dobColumnStyle).setValue(currDetail.getSecondQty());
				createCell(workbook, row, 12, dobColumnStyle).setValue(currDetail.getShotQty());
				createCell(workbook, row, 13, dobColumnStyle).setValue(currDetail.getActualOperatingProductivity());
				createCell(workbook, row, 14, dobColumnStyle).setValue(currDetail.getStandardProductivity());
				createCell(workbook, row, 15, dobColumnStyle).setValue(currDetail.getStandardProductionPrice());
				createCell(workbook, row, 16, dobColumnStyle).setValue(currDetail.getActualProductionPrice());
				createCell(workbook, row, 17, dobColumnStyle).setValue(currDetail.getDiff());
				createCell(workbook, row, 18, dobPencentColumnStyle).setValue(currDetail.getPercentage());
				createCell(workbook, row, 19, txtColumnStyle).setValue(currDetail.getWorkOrderNo());
				createCell(workbook, row, 20, c13ColumnStyle).setValue(currDetail.getTypeName());
//				createCell(workbook, row, 21, txtColumnStyle).setValue(currDetail.getId());
//				sheet.setColumnHidden(21,true);
				
				// show total
				if((!currDetail.getPartId().equals(nextDetail.getPartId())) || i == max-1){
					row = sheet.createRow(rowNumber++);
					createCell(workbook, row, 0,  totalColumnStyle).setValue("Total");
					createCell(workbook, row, 1,  totalColumnStyle);
					createCell(workbook, row, 2,  totalColumnStyle);
					createCell(workbook, row, 3,  totalColumnStyle);
					createCell(workbook, row, 4,  totalColumnStyle);
					createCell(workbook, row, 5,  totalColumnStyle);
					createCell(workbook, row, 6,  totalColumnStyle);
					createMergedRegion(sheet,row.getRowNum(),row.getRowNum(), 0, 6);
					createCell(workbook, row, 7,  sumColumnStyle);
					row.getCell(7).setCellFormula("SUM(H"+(startPart+1)+":H"+(rowNumber-1)+")");
					createCell(workbook, row, 8,  sumColumnStyle);
					row.getCell(8).setCellFormula("SUM(I"+(startPart+1)+":I"+(rowNumber-1)+")");
					createCell(workbook, row, 9,  sumDobColumnStyle);
					createCell(workbook, row, 10, sumTimeStyle);
					createCell(workbook, row, 11, sumColumnStyle);
					createCell(workbook, row, 12, sumColumnStyle);
					createCell(workbook, row, 13, sumDobColumnStyle);
					row.getCell(13).setCellFormula("SUM(N"+(startPart+1)+":N"+(rowNumber-1)+")");
					createCell(workbook, row, 14, sumDobColumnStyle);
					row.getCell(14).setCellFormula("SUM(O"+(startPart+1)+":O"+(rowNumber-1)+")");
					createCell(workbook, row, 15, sumDobColumnStyle);
					row.getCell(15).setCellFormula("SUM(P"+(startPart+1)+":P"+(rowNumber-1)+")");
					createCell(workbook, row, 16, sumDobColumnStyle);
					row.getCell(16).setCellFormula("SUM(Q"+(startPart+1)+":Q"+(rowNumber-1)+")");
					createCell(workbook, row, 17, sumDobColumnStyle);
					row.getCell(17).setCellFormula("SUM(R"+(startPart+1)+":R"+(rowNumber-1)+")");
					createCell(workbook, row, 18, sumColumnStyle);
					createCell(workbook, row, 19, totalColumnStyle);
					createCell(workbook, row, 20, lstColumnStyle);
//					createCell(workbook, row, 21, lstColumnStyle).setValue(-1);
					startPart = rowNumber ;
				}
			}
			
			// merge partNo, partName, customer
			for( int i = 4, startIndex = 4, endIndex = 4; i <= sheet.getLastRowNum() ; i++ ) {
				HSSFRow currRow = sheet.getRow(i);
				if( "Total".equals(currRow.getCell(0).getStringCellValue()) ) {
					createMergedRegion(sheet,startIndex, (endIndex-1), 0, 0);
					createMergedRegion(sheet,startIndex, (endIndex-1), 1, 1);
					createMergedRegion(sheet,startIndex, (endIndex-1), 2, 2);
					startIndex = endIndex+1;
					endIndex = endIndex+1;
				} else {
					endIndex++;
				}
			}
			
			// merge wip
			for( int i = 4,max = sheet.getLastRowNum(), startIndex = 4, endIndex = 4; i <= max ; i++ ) {
				HSSFRow currRow = sheet.getRow(i);
				HSSFRow nextRow = sheet.getRow(i+1 < max ? i+1 : max);
				if(!(nextRow.getCell(0).getStringCellValue()+":"+nextRow.getCell(3).getStringCellValue()).equals(currRow.getCell(0).getStringCellValue()+":"+currRow.getCell(3).getStringCellValue()) ) {

					boolean isSingleRow = startIndex == endIndex;
					boolean isLastRow = "Total".equals(nextRow.getCell(0).getStringCellValue());

					if( isSingleRow && isLastRow ) {
						startIndex = i+2;
						endIndex = startIndex;
					}
					if( isSingleRow && !isLastRow ) {
						startIndex = i+1;
						endIndex = startIndex;
					}
					if( !isSingleRow && isLastRow ) {
						createMergedRegion(sheet,startIndex, endIndex, 3, 3);
						startIndex = i+2;
						endIndex = startIndex;
					}
					if( !isSingleRow && !isLastRow ) {
						createMergedRegion(sheet,startIndex, endIndex, 3, 3);
						startIndex = i+1;
						endIndex = startIndex;
					}
					
				} else {
					endIndex++;
				}
				
			}
			
			String keyCurr = "";
			String keyNext = "";
			
			// merge operation date and machine name
			for( int i = 4,max = sheet.getLastRowNum(), startIndex = 4, endIndex = 4; i <= max ; i++ ) {
				HSSFRow currRow = sheet.getRow(i);
				HSSFRow nextRow = sheet.getRow(i+1 < max ? i+1 : max);
				keyCurr = currRow.getCell(0).getStringCellValue()+":"+currRow.getCell(3).getStringCellValue()+":"+currRow.getCell(4).getStringCellValue()+":"+currRow.getCell(5).getStringCellValue();
				keyNext = nextRow.getCell(0).getStringCellValue()+":"+nextRow.getCell(3).getStringCellValue()+":"+nextRow.getCell(4).getStringCellValue()+":"+nextRow.getCell(5).getStringCellValue();
				if(!(keyCurr).equals(keyNext) ) {

					boolean isSingleRow = startIndex == endIndex;
					boolean isLastRow = "Total".equals(nextRow.getCell(0).getStringCellValue());

					if( isSingleRow && isLastRow ) {
						startIndex = i+2;
						endIndex = startIndex;
					}
					if( isSingleRow && !isLastRow ) {
						startIndex = i+1;
						endIndex = startIndex;
					}
					if( !isSingleRow && isLastRow ) {
						createMergedRegion(sheet,startIndex, endIndex, 4, 4);
						createMergedRegion(sheet,startIndex, endIndex, 5, 5);
						startIndex = i+2;
						endIndex = startIndex;
					}
					if( !isSingleRow && !isLastRow ) {
						createMergedRegion(sheet,startIndex, endIndex, 4, 4);
						createMergedRegion(sheet,startIndex, endIndex, 5, 5);
						startIndex = i+1;
						endIndex = startIndex;
					}
					
				} else {
					endIndex++;
				}
				
			}
			
			/*// merge worker and qty
			for( int i = 4,max = sheet.getLastRowNum(), startIndex = 4, endIndex = 4; i <= max ; i++ ) {
				HSSFRow currRow = sheet.getRow(i);
				HSSFRow nextRow = sheet.getRow(i+1 < max ? i+1 : max);
				keyCurr = currRow.getCell(0).getStringCellValue()+":"+currRow.getCell(3).getStringCellValue()+":"+currRow.getCell(4).getStringCellValue()+":"+currRow.getCell(5).getStringCellValue()+":"+currRow.getCell(21).getNumericCellValue();
				keyNext = nextRow.getCell(0).getStringCellValue()+":"+nextRow.getCell(3).getStringCellValue()+":"+nextRow.getCell(4).getStringCellValue()+":"+nextRow.getCell(5).getStringCellValue()+":"+nextRow.getCell(21).getNumericCellValue();
				
				if(!(keyCurr).equals(keyNext) ) {
					boolean isSingleRow = startIndex == endIndex;
					boolean isLastRow = "Total".equals(nextRow.getCell(0).getStringCellValue());
					
					if( isSingleRow && isLastRow ) {
						startIndex = i+2;
						endIndex = startIndex;
					}
					if( isSingleRow && !isLastRow ) {
						startIndex = i+1;
						endIndex = startIndex;
					}
					if( !isSingleRow && isLastRow ) {
						createMergedRegion(sheet,startIndex, endIndex, 6, 6).cleanUp();
						createMergedRegion(sheet,startIndex, endIndex, 7, 7).cleanUp();
						createMergedRegion(sheet,startIndex, endIndex, 8, 8).cleanUp();
						createMergedRegion(sheet,startIndex, endIndex, 9, 9).cleanUp();
						createMergedRegion(sheet,startIndex, endIndex, 10, 10).cleanUp();
						createMergedRegion(sheet,startIndex, endIndex, 11, 11).cleanUp();
						createMergedRegion(sheet,startIndex, endIndex, 12, 12).cleanUp();
						startIndex = i+2;
						endIndex = startIndex;
					}
					if( !isSingleRow && !isLastRow ) {
						createMergedRegion(sheet,startIndex, endIndex, 6, 6).cleanUp();
						createMergedRegion(sheet,startIndex, endIndex, 7, 7).cleanUp();
						createMergedRegion(sheet,startIndex, endIndex, 8, 8).cleanUp();
						createMergedRegion(sheet,startIndex, endIndex, 9, 9).cleanUp();
						createMergedRegion(sheet,startIndex, endIndex, 10, 10).cleanUp();
						createMergedRegion(sheet,startIndex, endIndex, 11, 11).cleanUp();
						createMergedRegion(sheet,startIndex, endIndex, 12, 12).cleanUp();
						startIndex = i+1;
						endIndex = startIndex;
					}
				} else {
					endIndex++;
				}
			}*/
			
			//sum time
			for( int i = 4 ;i <= sheet.getLastRowNum() ; i++ ) {
				HSSFRow currRow = sheet.getRow(i);
				if( "Total".equals(currRow.getCell(0).getStringCellValue()) ) {
					currRow.getCell(10).setCellValue(this.calTime(hour, min, sec));
					// grand total
					hrTotal += hour;
					minTotal += min;
					secTotal += sec;
					hour = 0;
					min  = 0;
					sec  = 0;
				} else {
					if(currRow.getCell(10).getStringCellValue() != ""){
						hour += Integer.parseInt(currRow.getCell(10).getStringCellValue().substring(0,2));
						min += Integer.parseInt(currRow.getCell(10).getStringCellValue().substring(3,5));
						sec += Integer.parseInt(currRow.getCell(10).getStringCellValue().substring(6));
					}
				}
			}
			
			// Grand Total
			row = sheet.createRow(rowNumber);
			createCell(workbook, row, 0,  grandColumnStyle);
			createCell(workbook, row, 1,  grandColumnStyle);
			createCell(workbook, row, 2,  grandColumnStyle);
			createCell(workbook, row, 3,  grandColumnStyle);
			createCell(workbook, row, 4,  grandColumnStyle);
			createCell(workbook, row, 5,  grandColumnStyle);
			createCell(workbook, row, 6,  grandColumnStyle);
			createCell(workbook, row, 7,  gSumColumnStyle);
			createCell(workbook, row, 8,  gSumColumnStyle);
			createCell(workbook, row, 9,  gtxtColumnStyle);
			createCell(workbook, row, 10,  GrandTimeStyle).setValue(this.calTime(hrTotal, minTotal, secTotal));
			createCell(workbook, row, 11, gtxtColumnStyle);
			createCell(workbook, row, 12, gtxtColumnStyle);
			createCell(workbook, row, 13, gSumDoubleColumnStyle);
			createCell(workbook, row, 14, gSumDoubleColumnStyle);
			createCell(workbook, row, 15, gSumDoubleColumnStyle);
			createCell(workbook, row, 16, gSumDoubleColumnStyle);
			createCell(workbook, row, 17, gSumDoubleColumnStyle);
			createCell(workbook, row, 18, gtxtColumnStyle);
			createCell(workbook, row, 19, gtxtColumnStyle);
			createCell(workbook, row, 20, gtxtColumnStyle);
			createMergedRegion(sheet,rowNumber,rowNumber, 0, 6);
			row.getCell(0).setCellValue("Grand Total");
			row.getCell(7).setCellFormula("SUMIF(A5:A"+ rowNumber +",\"Total\",H5:H"+ rowNumber +")");
			row.getCell(8).setCellFormula("SUMIF(A5:A"+ rowNumber +",\"Total\",I5:I"+ rowNumber +")");
			row.getCell(13).setCellFormula("SUMIF(A5:A"+ rowNumber +",\"Total\",N5:N"+ rowNumber +")");
			row.getCell(14).setCellFormula("SUMIF(A5:A"+ rowNumber +",\"Total\",O5:O"+ rowNumber +")");
			row.getCell(15).setCellFormula("SUMIF(A5:A"+ rowNumber +",\"Total\",P5:P"+ rowNumber +")");
			row.getCell(16).setCellFormula("SUMIF(A5:A"+ rowNumber +",\"Total\",Q5:Q"+ rowNumber +")");
			row.getCell(17).setCellFormula("SUMIF(A5:A"+ rowNumber +",\"Total\",R5:R"+ rowNumber +")");
		}
	
		// <!-- Setup 'Print Area'. -->
		workbook.setPrintArea(0, 0, 14, 0, sheet.getLastRowNum());
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