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

import th.co.nttdata.tki.bean.TDailyMC;
import th.co.nttdata.tki.bean.TDailyMCDetail;

public class MRDC_R09ExcelView extends AbstractExcelView{

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {
		
		final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		TDailyMC TDailyMC = (TDailyMC)model.get("TDailyMC");
		List<TDailyMCDetail> detailList = (List<TDailyMCDetail>)TDailyMC.getDetails();
		
		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short)11);
		HSSFFont fontHd = workbook.createFont();
		fontHd.setFontName("Calibri");
		fontHd.setFontHeightInPoints((short)11);
		fontHd.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		Style txtHeadStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setWrapText().setFont(fontHd);
		Style txtCenStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style txtLeftStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style numberStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style doubleStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.00").setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style double3Style 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.000").setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style totalStyle	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setFont(fontHd).setWrapText().setBottomBorder(CellStyle.BORDER_DOUBLE);
		Style sumDouStyle	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.00").setFont(fontHd).setWrapText().setBottomBorder(CellStyle.BORDER_DOUBLE);
		Style sumTimeStyle	= createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFont(fontHd).setWrapText().setBottomBorder(CellStyle.BORDER_DOUBLE);
		
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow criteria = sheet.getRow(2);
		if(TDailyMC.getReportDateFr() != null){
			criteria.getCell(0).setCellValue("Operation Date (From) : " + dateFormatter.format(TDailyMC.getReportDateFr()));
		}
		if(TDailyMC.getReportDateTo() != null){
			criteria.getCell(3).setCellValue("Operation Date (To) : " + dateFormatter.format(TDailyMC.getReportDateTo()));
		}
		
		int rowNum = 4;
		int hourAc = 0;
		int minAc  = 0;
		int secAc  = 0;
		int hourN  = 0;
		int minN   = 0;
		int secN   = 0;
		int init   = 4;
		String prevKey = "";
		String cerrKey = "";
		TDailyMCDetail cerrent = null;
		TDailyMCDetail previous = null;

		for(int i=0; i<detailList.size(); i++){
			cerrent = detailList.get(i);
			if(i == 0){
				previous = detailList.get(i);
			}else{
				previous = detailList.get(i-1);
			}
			prevKey = previous.getDailyMCId()+":"+previous.getOperationDate()+":"+previous.getReportTime()+":"+previous.getPartId();
			cerrKey = cerrent.getDailyMCId()+":"+cerrent.getOperationDate()+":"+cerrent.getReportTime()+":"+cerrent.getPartId();
			
			HSSFRow dtRow = sheet.createRow(rowNum);
			createCell(workbook, dtRow, 0, txtCenStyle);
			createCell(workbook, dtRow, 1, txtCenStyle);
			createCell(workbook, dtRow, 2, txtLeftStyle);
			createCell(workbook, dtRow, 3, txtLeftStyle);
			createCell(workbook, dtRow, 4, txtLeftStyle);
			createCell(workbook, dtRow, 5, txtLeftStyle);
			createCell(workbook, dtRow, 6, double3Style);
			createCell(workbook, dtRow, 7, txtLeftStyle).setValue(cerrent.getCareerSheetNo());
			createCell(workbook, dtRow, 8, txtLeftStyle);
			createCell(workbook, dtRow, 9, txtLeftStyle);
			createCell(workbook, dtRow, 10, txtLeftStyle);
			createCell(workbook, dtRow, 11, txtCenStyle);
			createCell(workbook, dtRow, 12, txtLeftStyle);
			createCell(workbook, dtRow, 13, doubleStyle);
			createCell(workbook, dtRow, 14, numberStyle);
			createCell(workbook, dtRow, 15, numberStyle);
			createCell(workbook, dtRow, 16, numberStyle);
			createCell(workbook, dtRow, 17, numberStyle);
			createCell(workbook, dtRow, 18, doubleStyle);
			createCell(workbook, dtRow, 19, txtCenStyle);
			createCell(workbook, dtRow, 20, txtCenStyle);
			createCell(workbook, dtRow, 21, doubleStyle);
			createCell(workbook, dtRow, 22, doubleStyle);
			createCell(workbook, dtRow, 23, doubleStyle);
			createCell(workbook, dtRow, 24, doubleStyle);
			createCell(workbook, dtRow, 25, doubleStyle);
			createCell(workbook, dtRow, 26, doubleStyle);
			
			if((!prevKey.equals(cerrKey)) || (detailList.size()==1)){
				sheet.getRow(init).getCell(0).setCellValue(dateFormatter.format(previous.getOperationDate()));
				sheet.getRow(init).getCell(1).setCellValue(previous.getShift());
				sheet.getRow(init).getCell(2).setCellValue(previous.getPartNo());
				sheet.getRow(init).getCell(3).setCellValue(previous.getPartName());
				sheet.getRow(init).getCell(4).setCellValue(previous.getCategory());
				sheet.getRow(init).getCell(5).setCellValue(previous.getMaterial());
				sheet.getRow(init).getCell(6).setCellValue(previous.getWeightPerUnit());
				sheet.getRow(init).getCell(8).setCellValue(previous.getProcess());
				sheet.getRow(init).getCell(9).setCellValue(previous.getMachine());
				sheet.getRow(init).getCell(10).setCellValue(previous.getWorker());
				sheet.getRow(init).getCell(11).setCellValue(previous.getsMoldNo());
				sheet.getRow(init).getCell(12).setCellValue(previous.getMold());
				sheet.getRow(init).getCell(13).setCellValue(previous.getShotQty());
				sheet.getRow(init).getCell(14).setCellValue(previous.getCavQty());
				sheet.getRow(init).getCell(15).setCellValue(previous.getNg());
				if(previous.getTrialQty() != null){
					sheet.getRow(init).getCell(16).setCellValue(previous.getTrialQty());
				}
				sheet.getRow(init).getCell(17).setCellValue(previous.getQty());
				sheet.getRow(init).getCell(18).setCellValue(previous.getOkShotQty());
				sheet.getRow(init).getCell(19).setCellValue(previous.getActualTime());
				sheet.getRow(init).getCell(20).setCellValue(previous.getNonTime());
				sheet.getRow(init).getCell(21).setCellValue(previous.getnActualOperatingProductivity());
				sheet.getRow(init).getCell(22).setCellValue(previous.getnStandardProductivity());
				if(previous.getnStandardProductionPrice()!=null){
					sheet.getRow(init).getCell(23).setCellValue(previous.getnStandardProductionPrice());					
				}
				sheet.getRow(init).getCell(24).setCellValue(previous.getnActualProductionPrice());
				if(previous.getnDiff()!=null){
					sheet.getRow(init).getCell(25).setCellValue(previous.getnDiff());					
				}
				sheet.getRow(init).getCell(26).setCellValue(previous.getnPercentage() + "%");
				//Merge
				if((init != (rowNum-1)) && (detailList.size()!=1)){
					createMergedRegion(sheet, init, rowNum-1, 0, 0);
					createMergedRegion(sheet, init, rowNum-1, 1, 1);
					createMergedRegion(sheet, init, rowNum-1, 2, 2);
					createMergedRegion(sheet, init, rowNum-1, 3, 3);
					createMergedRegion(sheet, init, rowNum-1, 4, 4);
					createMergedRegion(sheet, init, rowNum-1, 5, 5);
					createMergedRegion(sheet, init, rowNum-1, 6, 6);
					createMergedRegion(sheet, init, rowNum-1, 8, 8);
					createMergedRegion(sheet, init, rowNum-1, 9, 9);
					createMergedRegion(sheet, init, rowNum-1, 10, 10);
					createMergedRegion(sheet, init, rowNum-1, 11, 11);
					createMergedRegion(sheet, init, rowNum-1, 12, 12);
					createMergedRegion(sheet, init, rowNum-1, 13, 13);
					createMergedRegion(sheet, init, rowNum-1, 14, 14);
					createMergedRegion(sheet, init, rowNum-1, 15, 15);
					createMergedRegion(sheet, init, rowNum-1, 16, 16);
					createMergedRegion(sheet, init, rowNum-1, 17, 18);
					createMergedRegion(sheet, init, rowNum-1, 18, 18);
					createMergedRegion(sheet, init, rowNum-1, 19, 19);
					createMergedRegion(sheet, init, rowNum-1, 20, 20);
					createMergedRegion(sheet, init, rowNum-1, 21, 21);
					createMergedRegion(sheet, init, rowNum-1, 22, 22);
					createMergedRegion(sheet, init, rowNum-1, 23, 23);
					createMergedRegion(sheet, init, rowNum-1, 24, 24);
					createMergedRegion(sheet, init, rowNum-1, 25, 25);
					createMergedRegion(sheet, init, rowNum-1, 26, 26);
				}
				//sum time
				hourAc += Integer.parseInt(previous.getActualTime().substring(0,2));
				minAc += Integer.parseInt(previous.getActualTime().substring(3,5));
				secAc += Integer.parseInt(previous.getActualTime().substring(6));
				hourN += Integer.parseInt(previous.getNonTime().substring(0,2));
				minN += Integer.parseInt(previous.getNonTime().substring(3,5));
				secN += Integer.parseInt(previous.getNonTime().substring(6));
				init = rowNum;
			}
			if(i == (detailList.size()-1)){
				sheet.getRow(init).getCell(0).setCellValue(dateFormatter.format(cerrent.getOperationDate()));
				sheet.getRow(init).getCell(1).setCellValue(cerrent.getShift());
				sheet.getRow(init).getCell(2).setCellValue(cerrent.getPartNo());
				sheet.getRow(init).getCell(3).setCellValue(cerrent.getPartName());
				sheet.getRow(init).getCell(4).setCellValue(cerrent.getCategory());
				sheet.getRow(init).getCell(5).setCellValue(cerrent.getMaterial());
				sheet.getRow(init).getCell(6).setCellValue(cerrent.getWeightPerUnit());
				sheet.getRow(init).getCell(8).setCellValue(cerrent.getProcess());
				sheet.getRow(init).getCell(9).setCellValue(cerrent.getMachine());
				sheet.getRow(init).getCell(10).setCellValue(cerrent.getWorker());
				sheet.getRow(init).getCell(11).setCellValue(cerrent.getsMoldNo());
				sheet.getRow(init).getCell(12).setCellValue(cerrent.getMold());
				sheet.getRow(init).getCell(13).setCellValue(cerrent.getShotQty());
				sheet.getRow(init).getCell(14).setCellValue(cerrent.getCavQty());
				sheet.getRow(init).getCell(15).setCellValue(cerrent.getNg());
				if(cerrent.getTrialQty() != null){
					sheet.getRow(init).getCell(16).setCellValue(cerrent.getTrialQty());
				}
				sheet.getRow(init).getCell(17).setCellValue(cerrent.getQty());
				sheet.getRow(init).getCell(18).setCellValue(cerrent.getOkShotQty());
				sheet.getRow(init).getCell(19).setCellValue(cerrent.getActualTime());
				sheet.getRow(init).getCell(20).setCellValue(cerrent.getNonTime());
				sheet.getRow(init).getCell(21).setCellValue(cerrent.getnActualOperatingProductivity());
				sheet.getRow(init).getCell(22).setCellValue(cerrent.getnStandardProductivity());
				sheet.getRow(init).getCell(23).setCellValue(cerrent.getnStandardProductionPrice());
				sheet.getRow(init).getCell(24).setCellValue(cerrent.getnActualProductionPrice());
				sheet.getRow(init).getCell(25).setCellValue(cerrent.getnDiff());
				sheet.getRow(init).getCell(26).setCellValue(cerrent.getnPercentage() + "%");
				//Merg
				if(init != rowNum){
					createMergedRegion(sheet, init, rowNum, 0, 0);
					createMergedRegion(sheet, init, rowNum, 1, 1);
					createMergedRegion(sheet, init, rowNum, 2, 2);
					createMergedRegion(sheet, init, rowNum, 3, 3);
					createMergedRegion(sheet, init, rowNum, 4, 4);
					createMergedRegion(sheet, init, rowNum, 5, 5);
					createMergedRegion(sheet, init, rowNum, 6, 6);
					createMergedRegion(sheet, init, rowNum, 8, 8);
					createMergedRegion(sheet, init, rowNum, 9, 9);
					createMergedRegion(sheet, init, rowNum, 10, 10);
					createMergedRegion(sheet, init, rowNum, 11, 11);
					createMergedRegion(sheet, init, rowNum, 12, 12);
					createMergedRegion(sheet, init, rowNum, 13, 13);
					createMergedRegion(sheet, init, rowNum, 14, 14);
					createMergedRegion(sheet, init, rowNum, 15, 15);
					createMergedRegion(sheet, init, rowNum, 16, 16);
					createMergedRegion(sheet, init, rowNum, 17, 17);
					createMergedRegion(sheet, init, rowNum, 18, 18);
					createMergedRegion(sheet, init, rowNum, 19, 19);
					createMergedRegion(sheet, init, rowNum, 20, 20);
					createMergedRegion(sheet, init, rowNum, 21, 21);
					createMergedRegion(sheet, init, rowNum, 22, 22);
					createMergedRegion(sheet, init, rowNum, 23, 23);
					createMergedRegion(sheet, init, rowNum, 24, 24);
					createMergedRegion(sheet, init, rowNum, 25, 25);
					createMergedRegion(sheet, init, rowNum, 26, 26);
				}
				//sum time
				hourAc += Integer.parseInt(cerrent.getActualTime().substring(0,2));
				minAc  += Integer.parseInt(cerrent.getActualTime().substring(3,5));
				secAc  += Integer.parseInt(cerrent.getActualTime().substring(6));
				hourN  += Integer.parseInt(cerrent.getNonTime().substring(0,2));
				minN   += Integer.parseInt(cerrent.getNonTime().substring(3,5));
				secN   += Integer.parseInt(cerrent.getNonTime().substring(6));
			}
			rowNum++;
		}
		if(detailList.size() > 0){
			HSSFRow dtRow = sheet.createRow(rowNum);
			createCell(workbook, dtRow, 0, txtHeadStyle).setValue("Grand Total");
			createCell(workbook, dtRow, 1, txtHeadStyle);
			createCell(workbook, dtRow, 2, txtHeadStyle);
			createCell(workbook, dtRow, 3, txtHeadStyle);
			createCell(workbook, dtRow, 4, txtHeadStyle);
			createCell(workbook, dtRow, 5, txtHeadStyle);
			createCell(workbook, dtRow, 6, txtHeadStyle);
			createCell(workbook, dtRow, 7, txtHeadStyle);
			createCell(workbook, dtRow, 8, txtHeadStyle);
			createCell(workbook, dtRow, 9, txtHeadStyle);
			createCell(workbook, dtRow, 10, txtHeadStyle);
			createCell(workbook, dtRow, 11, txtHeadStyle);
			createCell(workbook, dtRow, 12, txtHeadStyle);
			createMergedRegion(sheet, rowNum, rowNum, 0, 12);
			createCell(workbook, dtRow, 13, sumDouStyle);
			createCell(workbook, dtRow, 14, totalStyle);
			createCell(workbook, dtRow, 15, totalStyle);
			createCell(workbook, dtRow, 16, totalStyle);
			createCell(workbook, dtRow, 17, totalStyle);
			createCell(workbook, dtRow, 18, sumDouStyle);
			dtRow.getCell(13).setCellFormula("SUM(N5:N"+rowNum+")");
			dtRow.getCell(14).setCellFormula("SUM(O5:O"+rowNum+")");
			dtRow.getCell(15).setCellFormula("SUM(P5:P"+rowNum+")");
			dtRow.getCell(16).setCellFormula("SUM(Q5:Q"+rowNum+")");
			dtRow.getCell(17).setCellFormula("SUM(R5:R"+rowNum+")");
			dtRow.getCell(18).setCellFormula("SUM(S5:S"+rowNum+")");			
			createCell(workbook, dtRow, 19, sumTimeStyle).setValue(this.calTime(hourAc, minAc, secAc));
			createCell(workbook, dtRow, 20, sumTimeStyle).setValue(this.calTime(hourN, minN, secN));
			createCell(workbook, dtRow, 21, sumDouStyle);
			createCell(workbook, dtRow, 22, sumDouStyle);
			createCell(workbook, dtRow, 23, sumDouStyle);
			createCell(workbook, dtRow, 24, sumDouStyle);
			createCell(workbook, dtRow, 25, sumDouStyle);
			dtRow.getCell(21).setCellFormula("SUM(V5:V"+rowNum+")");
			dtRow.getCell(22).setCellFormula("SUM(W5:W"+rowNum+")");
			dtRow.getCell(23).setCellFormula("SUM(X5:X"+rowNum+")");
			dtRow.getCell(24).setCellFormula("SUM(Y5:Y"+rowNum+")");
			dtRow.getCell(25).setCellFormula("SUM(Z5:Z"+rowNum+")");
		}
		// <!-- Setup 'Print Area'. -->
		workbook.setPrintArea(0, 0, 26, 0, rowNum);
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
