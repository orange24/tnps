package th.co.nttdata.tki.excel;

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
import th.co.nttdata.tki.bean.TDailyWK;
import th.co.nttdata.tki.bean.TDailyWKDetail;
import th.co.nttdata.tki.bean.TDailyWKNGReason;

public class MRDC_R10ExcelView extends AbstractExcelView{

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {
		TDailyWK 					tDailyWK 	= (TDailyWK) model.get("tDailyWK");
		List<TDailyWKDetail> 		detailWK 	= (List<TDailyWKDetail>)tDailyWK.getDailyWKDetailList();
		Map<String,TDailyWKNGReason> reasonMap 	= tDailyWK.getReasonMap();
		List<MReason> 				reasonList 	= (List<MReason>) model.get("reasonList");

		final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		
		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short)11);
		HSSFFont fontHD = workbook.createFont();
		fontHD.setFontName("Calibri");
		fontHD.setFontHeightInPoints((short)11);
		fontHD.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		Style fstHDRStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFont(FONT_HEADR).setTopBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setBgColor().setFont(fontHD).setWrapText();
		Style sndHDRStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFont(FONT_HEADR).setTopBorder().setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setBgColor().setFont(fontHD).setWrapText();
		Style txtLeftTOPStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style fstColumStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style txtCentStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style numberStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0").setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style txtSumStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setWrapText().setFont(fontHD);
		Style numSumStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setFont(fontHD).setWrapText().setBottomBorder(CellStyle.BORDER_DOUBLE);
		Style sumTimeStyle	= createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFont(fontHD).setWrapText().setBottomBorder(CellStyle.BORDER_DOUBLE);

		HSSFSheet sheet = workbook.getSheetAt(0);
		
		if(tDailyWK.getReportDateFr() != null){
			sheet.getRow(2).getCell(0).setCellValue("Operation Date (From) : " + dateFormatter.format(tDailyWK.getReportDateFr()));
		}
		if(tDailyWK.getReportDateTo() != null){
			sheet.getRow(2).getCell(3).setCellValue("Operation Date (To) : " + dateFormatter.format(tDailyWK.getReportDateTo()));
		}
		
		HSSFRow fstHeader = sheet.getRow(3);
		HSSFRow sndHeader = sheet.getRow(4);
		
		int colNum = 7;
		if (reasonList.size() > 0) {
			for( MReason MReason : reasonList ) {
				
				createCell(workbook, fstHeader, colNum, fstHDRStyle);
				createCell(workbook, sndHeader, colNum, sndHDRStyle).setValue(MReason.getReasonCode());
				
				colNum ++;
			}
			createMergedRegion(sheet, 3, 3, 7, colNum - 1);
			fstHeader.getCell(7).setCellValue("NG Qty per NG Reason");
		}
		
		createCell(workbook, fstHeader, colNum, fstHDRStyle);
		createCell(workbook, sndHeader, colNum, sndHDRStyle);
		createMergedRegion(sheet, 3, 4, colNum, colNum);
		sheet.setColumnWidth(colNum, 13*256);
		fstHeader.getCell(colNum).setCellValue("Sorting Time");
		createCell(workbook, fstHeader, colNum+1, fstHDRStyle);
		createCell(workbook, sndHeader, colNum+1, sndHDRStyle);
		createMergedRegion(sheet, 3, 4, colNum+1, colNum+1);
		sheet.setColumnWidth(colNum+1, 20*256);
		fstHeader.getCell(colNum+1).setCellValue("Machine");
		createCell(workbook, fstHeader, colNum+2, fstHDRStyle);
		createCell(workbook, sndHeader, colNum+2, sndHDRStyle);
		createMergedRegion(sheet, 3, 4, colNum+2, colNum+2);
		sheet.setColumnWidth(colNum+2, 13*256);
		fstHeader.getCell(colNum+2).setCellValue("Worker");
		createCell(workbook, fstHeader, colNum+3, fstHDRStyle);
		createCell(workbook, sndHeader, colNum+3, sndHDRStyle);
		createMergedRegion(sheet, 3,4, colNum+3, colNum+3);
		sheet.setColumnWidth(colNum+3, 15*256);
		fstHeader.getCell(colNum+3).setCellValue("Career Sheet No");
		sheet.setColumnWidth((colNum+3),(15*256));// set width last column
		
		int rowNum = 5;
		String key = "";
		TDailyWKNGReason reason = new TDailyWKNGReason();
		MReason mReason = null;
		int totalNg = 0;
		int sumSort = 0;
		int sumOk = 0;
		int sumNg = 0;
		int hour  = 0;
		int min   = 0;
		int sec   = 0;
		int rowPrev = 5;
		boolean isMerged = false;
		String keyPrev = "";
		String keyCurr = "";
		List<Integer> totalRs = new ArrayList<Integer>();
		HSSFRow dtRow = null;
		if(detailWK.size() > 0){
			for(int d=0; d<detailWK.size(); d++){
				TDailyWKDetail daily = detailWK.get(d);				
				dtRow = sheet.createRow(rowNum++);
				
				createCell(workbook, dtRow, 0, fstColumStyle).setValue(dateFormatter.format(daily.getReportDate()));
				createCell(workbook, dtRow, 1, txtLeftTOPStyle).setValue(daily.getPartNo());
				createCell(workbook, dtRow, 2, txtLeftTOPStyle).setValue(daily.getPartName());
				createCell(workbook, dtRow, 3, txtLeftTOPStyle).setValue(daily.getWipName());
				createCell(workbook, dtRow, 4, numberStyle).setValue(daily.getQty(),true);
				createCell(workbook, dtRow, 5, numberStyle).setValue(daily.getOk(),true);
				createCell(workbook, dtRow, 6, numberStyle).setValue(daily.getNg(),true);
				sumSort += daily.getQty();
				sumOk += daily.getOk();
				sumNg += daily.getNg();
				colNum = 7;

				if(reasonList.size() > 0){
					for(int i=0; i<reasonList.size(); i++){
						mReason = reasonList.get(i);
						key = daily.getDatailRef()+":"+mReason.getReasonId()+":"+daily.getWorkOrderNo()+":"+daily.getNg();
						reason = reasonMap.get(key);
						// System.out.println("key = " + key);
						if (reason != null) {
							// System.out.println("reason.getIdRef() = " + reason.getIdRef());
						}
						totalNg = 0;
						if(reason != null && key.equals(reason.getIdRef())){
							// System.out.println("dtRow = " + dtRow.getRowNum() + ", colNum = " + colNum);
							// System.out.println("match ng = " + reason.getNg() + "\n");
							totalNg += nullToZero(reason.getNg());
						}
						createCell(workbook, dtRow, colNum, numberStyle).setValue(totalNg);
						
						if(totalRs.size() <= i || totalRs.get(i) == null){
							totalRs.add(totalNg);
						}else{
							totalRs.set(i, totalRs.get(i) + totalNg);
						}
						colNum++;
					}
				}
				try{
					hour  += Integer.parseInt(daily.getSortingTime().substring(0,2));
					min   += Integer.parseInt(daily.getSortingTime().substring(3,5));
					sec   += Integer.parseInt(daily.getSortingTime().substring(6));
				}catch(Exception e){
					
				}
				createCell(workbook, dtRow, colNum, txtCentStyle).setValue(daily.getSortingTime());
				createCell(workbook, dtRow, colNum+1, txtLeftTOPStyle).setValue(daily.getMachineName());
				createCell(workbook, dtRow, colNum+2, txtLeftTOPStyle).setValue(daily.getStaff());
				createCell(workbook, dtRow, colNum+3, txtLeftTOPStyle).setValue(daily.getWorkOrderNo());
				if(d == 0){
					keyPrev = dateFormatter.format(daily.getReportDate())+":"+daily.getPartId()+":"+daily.getWip();
				}
				keyCurr = dateFormatter.format(daily.getReportDate())+":"+daily.getPartId()+":"+daily.getWip();
				// merge cell
				if(!keyCurr.equals(keyPrev)){// isSingleRow no merge
					if(rowPrev != dtRow.getRowNum()-1){
						createMergedRegion(sheet, rowPrev, dtRow.getRowNum()-1, 0, 0);
						createMergedRegion(sheet, rowPrev, dtRow.getRowNum()-1, 1, 1);
						createMergedRegion(sheet, rowPrev, dtRow.getRowNum()-1, 2, 2);
						createMergedRegion(sheet, rowPrev, dtRow.getRowNum()-1, 3, 3);
					}
					isMerged = true;
					rowPrev = dtRow.getRowNum();;
					keyPrev = dateFormatter.format(daily.getReportDate())+":"+daily.getPartId()+":"+daily.getWip();
				}
				isMerged = false;
			}
			
			if(!isMerged){
				createMergedRegion(sheet, rowPrev, dtRow.getRowNum(), 0, 0);
				createMergedRegion(sheet, rowPrev, dtRow.getRowNum(), 1, 1);
				createMergedRegion(sheet, rowPrev, dtRow.getRowNum(), 2, 2);
				createMergedRegion(sheet, rowPrev, dtRow.getRowNum(), 3, 3);
			}
			// grand total
			dtRow = sheet.createRow(rowNum);
			createCell(workbook, dtRow, 0, txtSumStyle);
			createCell(workbook, dtRow, 1, txtSumStyle);
			createCell(workbook, dtRow, 2, txtSumStyle);
			createCell(workbook, dtRow, 3, txtSumStyle);
			createMergedRegion(sheet, rowNum, rowNum, 0, 3);
			sheet.getRow(rowNum).getCell(0).setCellValue("Grand Total");
			createCell(workbook, dtRow, 4, numSumStyle).setValue(sumSort,true);
			createCell(workbook, dtRow, 5, numSumStyle).setValue(sumOk,true);
			createCell(workbook, dtRow, 6, numSumStyle).setValue(sumNg,true);
			colNum = 7;
			for(Integer sum :totalRs){
				createCell(workbook, dtRow, colNum, numSumStyle).setValue(sum,true);
				colNum++;
			}
			createCell(workbook, dtRow, colNum, sumTimeStyle).setValue(this.calTime(hour, min, sec));
		}
		createMergedRegion(sheet, 0, 0, 0, colNum+3);
		sheet.getRow(0).getCell(0).setCellValue("Daily Delivery Inspection Report");
		
		// <!-- Setup 'Print Area'. -->
		workbook.setPrintArea(0, 0, colNum+3, 0, rowNum);
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
