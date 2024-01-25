package th.co.nttdata.tki.excel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.TDailyMC;
import th.co.nttdata.tki.bean.TDailyMCDetail;
import th.co.nttdata.tki.bean.TDailyMCNGReason;

public class MRDC_R17ExcelView extends AbstractExcelView{

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {
		final SimpleDateFormat 	  dateFormatter	= new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		TDailyMC 					   tDailyMC = (TDailyMC)model.get("TDailyMC");
		List<TDailyMCDetail>  		    details = (List<TDailyMCDetail>)tDailyMC.getDetails();
		Map<String, TDailyMCNGReason> reasonMap = tDailyMC.getReasonMap();
		List<MReason> 			     reasonList = (List<MReason>) model.get("reasonList");
		
		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short)11);
		HSSFFont fontHd = workbook.createFont();
		fontHd.setFontName("Calibri");
		fontHd.setFontHeightInPoints((short)11);
		fontHd.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		Style fstHDRStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFont(FONT_HEADR).setTopBorder().setRightBorder().setBgColor().setFont(fontHd).setWrapText();
		Style sndHDRStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFont(FONT_HEADR).setTopBorder().setBottomBorder().setRightBorder().setBgColor().setFont(fontHd).setWrapText();
		Style txtHeadStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setWrapText().setFont(fontHd);
		Style txtRighStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style txtLeftStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style txtCentStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style numberStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style doubleStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.00").setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style totalStyle	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setFont(fontHd).setWrapText().setBottomBorder(CellStyle.BORDER_DOUBLE);
		Style sumTimeStyle	= createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFont(fontHd).setWrapText().setBottomBorder(CellStyle.BORDER_DOUBLE);
		Style sumDouStyle	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.00").setFont(fontHd).setWrapText().setBottomBorder(CellStyle.BORDER_DOUBLE);
		Style sumPerStyle	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFont(fontHd).setWrapText().setBottomBorder(CellStyle.BORDER_DOUBLE);

		HSSFSheet sheet = workbook.getSheetAt(0);
		
		HSSFRow criteria = sheet.getRow(1);		
		if(tDailyMC.getReportDateFr() != null){
			criteria.getCell(0).setCellValue("Report Date (From) : " + dateFormatter.format(tDailyMC.getReportDateFr()));
		}
		if(tDailyMC.getReportDateTo() != null){
			criteria.getCell(3).setCellValue("Report Date (To) : " + dateFormatter.format(tDailyMC.getReportDateTo()));
		}		
		//header
		HSSFRow fstHeader = sheet.getRow(2);
		HSSFRow sndHeader = sheet.getRow(3);
		HSSFRow thrHeader = sheet.getRow(4);
		
		int colNum = 13;
		int colInit = 0;
		if((reasonList != null) && (reasonList.size() > 0)){
			for( MReason MReason : reasonList ){
				colInit = colNum;
				if(MReason.getReasonList().size() > 0){
					for(MReason reason : MReason.getReasonList()){
						createCell(workbook, fstHeader, colNum, fstHDRStyle);
						createCell(workbook, sndHeader, colNum, sndHDRStyle);
						createCell(workbook, thrHeader, colNum, sndHDRStyle).setValue(reason.getReasonCode());
						sheet.setColumnWidth(colNum, 13*256);
						colNum ++;
					}
				}else{
					createCell(workbook, fstHeader, colNum, fstHDRStyle);
					createCell(workbook, sndHeader, colNum, sndHDRStyle);
					createCell(workbook, thrHeader, colNum, sndHDRStyle);
					sheet.setColumnWidth(colNum, 13*256);
					colNum ++;
				}
				createMergedRegion(sheet, 3, 3, colInit, colNum-1);
				sndHeader.getCell(colInit).setCellValue(MReason.getReasonCode());
			}
			createMergedRegion(sheet, 2, 2, 13, colNum - 1);
			fstHeader.getCell(13).setCellValue("Machine Stop Reason");
		}
		createCell(workbook, fstHeader, colNum, fstHDRStyle);
		createCell(workbook, sndHeader, colNum, sndHDRStyle);
		createCell(workbook, thrHeader, colNum, sndHDRStyle);
		createMergedRegion(sheet, 2, 4, colNum, colNum);
		sheet.setColumnWidth(colNum, 12*256);
		fstHeader.getCell(colNum).setCellValue("OK Ratio");
		createCell(workbook, fstHeader, colNum+1, fstHDRStyle);
		createCell(workbook, sndHeader, colNum+1, sndHDRStyle);
		createCell(workbook, thrHeader, colNum+1, sndHDRStyle);
		createMergedRegion(sheet, 2, 4, colNum+1, colNum+1);
		sheet.setColumnWidth(colNum+1, 13*256);
		fstHeader.getCell(colNum+1).setCellValue("Actual Operation Time ");
		createCell(workbook, fstHeader, colNum+2, fstHDRStyle);
		createCell(workbook, sndHeader, colNum+2, sndHDRStyle);
		createCell(workbook, thrHeader, colNum+2, sndHDRStyle);
		createMergedRegion(sheet, 2, 4, colNum+2, colNum+2);
		sheet.setColumnWidth(colNum+2, 13*256);
		fstHeader.getCell(colNum+2).setCellValue("Machine Stop Time");
		createCell(workbook, fstHeader, colNum+3, fstHDRStyle);
		createCell(workbook, sndHeader, colNum+3, sndHDRStyle);
		createCell(workbook, thrHeader, colNum+3, sndHDRStyle);
		createMergedRegion(sheet, 2, 4, colNum+3, colNum+3);
		sheet.setColumnWidth(colNum+3, 13*256);
		fstHeader.getCell(colNum+3).setCellValue("OK Cycle Time");
		createCell(workbook, fstHeader, colNum+4, fstHDRStyle);
		createCell(workbook, sndHeader, colNum+4, sndHDRStyle);
		createCell(workbook, thrHeader, colNum+4, sndHDRStyle);
		createMergedRegion(sheet, 2, 4, colNum+4, colNum+4);
		sheet.setColumnWidth(colNum+4, 12*256);
		fstHeader.getCell(colNum+4).setCellValue("OK Diecasting Cost");
		
		createMergedRegion(sheet, 0, 0, 0, colNum+4);
		
		//detail
		int rowNum = 5;
		BigDecimal sumCost = new BigDecimal(0);
		MReason mReason = null;
		MReason rsDetail = null;
		String key = "";
		int countRs = 0;
		TDailyMCNGReason rs = new TDailyMCNGReason();
		int hour = 0;
		int min  = 0;
		//int sec  = 0;
		//int msec = 0; // minlisec
		List<Integer> hhList = new ArrayList<Integer>();
		List<Integer> mmList = new ArrayList<Integer>();
		//List<Integer> ssList = new ArrayList<Integer>();
		//List<Integer> msList = new ArrayList<Integer>();
	
		for(TDailyMCDetail detail : details){
			HSSFRow dtRow = sheet.createRow(rowNum);
			createCell(workbook, dtRow, 0, txtCentStyle).setValue(dateFormatter.format(detail.getOperationDate()));
			createCell(workbook, dtRow, 1, txtCentStyle).setValue(detail.getShift());
			createCell(workbook, dtRow, 2, txtLeftStyle).setValue(detail.getMachine());
			createCell(workbook, dtRow, 3, txtLeftStyle).setValue(detail.getMold());
			createCell(workbook, dtRow, 4, txtLeftStyle).setValue(detail.getsMoldNo());
			createCell(workbook, dtRow, 5, txtLeftStyle).setValue(detail.getPartNo());
			createCell(workbook, dtRow, 6, txtLeftStyle).setValue(detail.getPartName());
			createCell(workbook, dtRow, 7, txtLeftStyle).setValue(detail.getProcess());
			createCell(workbook, dtRow, 8, doubleStyle).setValue(detail.getShotQty(),true);
			createCell(workbook, dtRow, 9, doubleStyle).setValue(detail.getOkShotQty(),true);
			createCell(workbook, dtRow, 10, numberStyle).setValue(detail.getOk(),true);
			createCell(workbook, dtRow, 11, numberStyle).setValue(detail.getCavQty(),true);
			createCell(workbook, dtRow, 12, numberStyle).setValue(detail.getNg(),true);
			colNum = 13;
			if((reasonList != null) && (reasonList.size() > 0)){
				countRs = 0;
				for(int i=0; i<reasonList.size(); i++){
					mReason = reasonList.get(i);
					for(int j=0; j<mReason.getReasonList().size(); j++){
						rsDetail = mReason.getReasonList().get(j);
						key = detail.getIdRef()+":"+mReason.getParentReasonId()+":"+rsDetail.getReasonId();
						rs = reasonMap.get(key);
						createCell(workbook, dtRow, colNum, txtCentStyle);
						if(rs != null && key.equals(rs.getIdRef()) ){
							dtRow.getCell(colNum).setCellValue(rs.getnMCStopTime());
						}
						//sum time
						if(rs != null){
							hour = rs.getnMCStopTime() != null?Integer.parseInt(rs.getnMCStopTime().substring(0,2)):0;
							min  = rs.getnMCStopTime() != null?Integer.parseInt(rs.getnMCStopTime().substring(3,5)):0;
							// sec  = rs.getnMCStopTime() != null?Integer.parseInt(rs.getnMCStopTime().substring(6)):0;
							// msec = 0;
						}else{
							hour = 0;
							min  = 0;
							//sec  = 0;
							//msec  = 0;
						}
						if(hhList.size() <= countRs || hhList.get(countRs) == null){
							hhList.add(hour);
							mmList.add(min);
							//ssList.add(sec);
							//msList.add(msec);
						}else{
							hhList.set(countRs, hhList.get(countRs)+hour);
							mmList.set(countRs, mmList.get(countRs)+min);
							//ssList.set(countRs, ssList.get(countRs)+sec);
							//msList.set(countRs, msList.get(countRs)+msec);
						}
						countRs++;
						colNum++;
					}
				}
			}
			//after reason
			createCell(workbook, dtRow, colNum, txtRighStyle).setValue(detail.getnOKRatio());
			createCell(workbook, dtRow, colNum+1, txtCentStyle).setValue(detail.getnActualOperationTime());
			//sum ActualOperationTime
			hour = (detail.getnActualOperationTime() != null?Integer.parseInt(detail.getnActualOperationTime().substring(0,2)):0);
			min  = (detail.getnActualOperationTime() != null?Integer.parseInt(detail.getnActualOperationTime().substring(3,5)):0);
			//sec  = (detail.getnActualOperationTime() != null?Integer.parseInt(detail.getnActualOperationTime().substring(6)):0);
			
			if(hhList.size() <= countRs || hhList.get(countRs) == null){
				hhList.add(hour);
				mmList.add(min);
				//ssList.add(sec);
				//msList.add(msec);
			}else{
				hhList.set(countRs, hhList.get(countRs)+hour);
				mmList.set(countRs, mmList.get(countRs)+min);
				//ssList.set(countRs, ssList.get(countRs)+sec);
				//msList.set(countRs, msList.get(countRs)+msec);
			}
			
			createCell(workbook, dtRow, colNum+2, txtCentStyle).setValue(detail.getnMachineStopTime());
			//sum MachineStopTime
			hour = (detail.getnMachineStopTime() != null?Integer.parseInt(detail.getnMachineStopTime().substring(0,2)):0);
			min  = (detail.getnMachineStopTime() != null?Integer.parseInt(detail.getnMachineStopTime().substring(3,5)):0);
			//sec  = (detail.getnMachineStopTime() != null?Integer.parseInt(detail.getnMachineStopTime().substring(6)):0);
			
			if(hhList.size() <= countRs+1 || hhList.get(countRs+1) == null){
				hhList.add(hour);
				mmList.add(min);
				//ssList.add(sec);
				//msList.add(msec);
			}else{
				hhList.set(countRs+1, hhList.get(countRs+1)+hour);
				mmList.set(countRs+1, mmList.get(countRs+1)+min);
				//ssList.set(countRs+1, ssList.get(countRs+1)+sec);
				//msList.set(countRs+1, msList.get(countRs+1)+msec);
			}
			createCell(workbook, dtRow, colNum+3, txtCentStyle).setValue(detail.getnOKCycelTime());
			//sum OKCycelTime
			hour = (detail.getnOKCycelTime() != null?Integer.parseInt(detail.getnOKCycelTime().substring(0,2)):0);
			min  = (detail.getnOKCycelTime() != null?Integer.parseInt(detail.getnOKCycelTime().substring(3,5)):0);
			//sec  = (detail.getnOKCycelTime() != null?Integer.parseInt(detail.getnOKCycelTime().substring(6,8)):0);
			//msec = (detail.getnOKCycelTime() != null?Integer.parseInt(detail.getnOKCycelTime().substring(9)):0);
			
			if(hhList.size() <= countRs+2 || hhList.get(countRs+2) == null){
				hhList.add(hour);
				mmList.add(min);
				//ssList.add(sec);
				//msList.add(msec);
			}else{
				hhList.set(countRs+2, hhList.get(countRs+2)+hour);
				mmList.set(countRs+2, mmList.get(countRs+2)+min);
				//ssList.set(countRs+2, ssList.get(countRs+2)+sec);
				//msList.set(countRs+2, msList.get(countRs+2)+msec);
			}
			createCell(workbook, dtRow, colNum+4, doubleStyle).setValue(detail.getnOKDiecastingCost(),true);
			
			if(detail.getnOKDiecastingCost() != null){
				sumCost = sumCost.add(new BigDecimal(detail.getnOKDiecastingCost()));
			}
			rowNum++;
		}
		if(details.size() > 0){
			HSSFRow dtRow = sheet.createRow(rowNum);
			createCell(workbook, dtRow, 0, txtHeadStyle).setValue("Grand Total");
			createCell(workbook, dtRow, 1, txtHeadStyle);
			createCell(workbook, dtRow, 2, txtHeadStyle);
			createCell(workbook, dtRow, 3, txtHeadStyle);
			createCell(workbook, dtRow, 4, txtHeadStyle);
			createCell(workbook, dtRow, 5, txtHeadStyle);
			createCell(workbook, dtRow, 6, txtHeadStyle);
			createCell(workbook, dtRow, 7, txtHeadStyle);
			createMergedRegion(sheet, rowNum, rowNum, 0, 7);
			createCell(workbook, dtRow, 8, sumDouStyle);
			createCell(workbook, dtRow, 9, sumDouStyle);
			createCell(workbook, dtRow, 10, totalStyle);
			createCell(workbook, dtRow, 11, totalStyle);
			createCell(workbook, dtRow, 12, totalStyle);
			dtRow.getCell(8).setCellFormula("SUM(I6:I"+rowNum+")");
			dtRow.getCell(9).setCellFormula("SUM(J6:J"+rowNum+")");
			dtRow.getCell(10).setCellFormula("SUM(K6:K"+rowNum+")");
			dtRow.getCell(11).setCellFormula("SUM(L6:L"+rowNum+")");
			dtRow.getCell(12).setCellFormula("SUM(M6:M"+rowNum+")");
			// sum reason time
			colNum = 13;
			for(int a=0;a<hhList.size()-3;a++){
				createCell(workbook, dtRow, colNum, sumTimeStyle).setValue(this.calTime(hhList.get(a), mmList.get(a)));
				colNum++;
			}
			// sum after reason
			createCell(workbook, dtRow, colNum, sumPerStyle);
			createCell(workbook, dtRow, colNum+1, sumTimeStyle).setValue(this.calTime(hhList.get(hhList.size()-3), mmList.get(mmList.size()-3)));
			createCell(workbook, dtRow, colNum+2, sumTimeStyle).setValue(this.calTime(hhList.get(hhList.size()-2), mmList.get(mmList.size()-2)));
			createCell(workbook, dtRow, colNum+3, sumTimeStyle).setValue(this.calTime(hhList.get(hhList.size()-1), mmList.get(mmList.size()-1)));
			createCell(workbook, dtRow, colNum+4, sumDouStyle).setValue(sumCost.doubleValue(),true);
			dtRow.getCell(colNum).setCellFormula("FIXED(IF("+dtRow.getCell(10).getCellFormula()+"<>0,("+dtRow.getCell(10).getCellFormula()+"/("+dtRow.getCell(10).getCellFormula()+"+"+dtRow.getCell(12).getCellFormula()+")*100),0),2,FALSE) & \"%\"");
		}
		// <!-- Setup 'Print Area'. -->
		workbook.setPrintArea(0, 0, colNum+4, 0, rowNum);
	}
	
	public String calTime(int hour,int min){
		//calculate Time
		String time = "";
		//int ss = sec%60;
		int mm = (min)%60;
		int hh = hour+((min)/60);
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
//		if(ss < 10){
//			time += ":0"+ss;
//		}else{
//			time += ":"+ss;
//		}		
		return time;
	}
	
	public String calTime(int hour,int min,int sec, int msec){
		//calculate Time
		String time = "";
		int ms = msec%1000;
		int ss = (sec+(msec/1000))%60;
		int mm = (min+((sec+(msec/1000))/60))%60;
		int hh = hour+((min+((sec+(msec/1000))/60))/60);
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
		if(ms >= 100){
			time += ":"+ms;
		}else if(ms < 10){
			time += ":00"+ss;
		}else{
			time += ":0"+ms;
		}
		return time;
	}
}
