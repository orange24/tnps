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

import th.co.nttdata.tki.bean.TDailyWK;
import th.co.nttdata.tki.bean.TDailyWKDetail;

public class MRDC_R22ExcelView extends AbstractExcelView{

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {
		TDailyWK tDailyWK = (TDailyWK) model.get("tDailyWK");
		final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		DecimalFormat dobFormat = new DecimalFormat("#,##0.00");
		
		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short)11);
		// set Font Header
		HSSFFont fontHD = workbook.createFont();
		fontHD.setFontName("Calibri");
		fontHD.setFontHeightInPoints((short)11);
		fontHD.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		// <!-- Assign: CellStyle. -->
		Style headerColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setWrapText().setFont(fontHD);
		Style fstColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setLeftBorder().setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style txtColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style numColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dobColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.00").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style perColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.00").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style txtCenterStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		// Grand Total
		Style grandColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setWrapText().setFont(fontHD);
		Style gNumColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder(CellStyle.BORDER_DOUBLE).setWrapText().setFont(fontHD);
		Style gDobColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.00").setBottomBorder(CellStyle.BORDER_DOUBLE).setWrapText().setFont(fontHD);
		Style gtxtColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFont(fontHD);
		Style grandTimeStyle  = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFont(fontHD).setWrapText().setBottomBorder(CellStyle.BORDER_DOUBLE);

		// <!-- Get 'First' Sheet. -->
		HSSFSheet sheet = workbook.getSheetAt(0);

		// header
		String dateFrom = "";
		String dateTo = "";
		HSSFRow dateRow = sheet.getRow(2);
		if(tDailyWK.getReportDateFr() != null){
			dateFrom = dateFormatter.format(tDailyWK.getReportDateFr());
			createCell(workbook, dateRow, 0, headerColumnStyle).setValue("Operation Date (From) : "+dateFrom);
		}
		if(tDailyWK.getReportDateTo() != null){
			dateTo = dateFormatter.format(tDailyWK.getReportDateTo());
			createCell(workbook, dateRow, 3, headerColumnStyle).setValue("Operation Date (To) : "+dateTo);
		}
		
		int rowNumber = 4;
		List<TDailyWKDetail> dList = tDailyWK.getDailyWKDetailList();
		TDailyWKDetail[] details = dList.toArray( new TDailyWKDetail[dList.size()] );
		//init time
		int hour  = 0;
		int min   = 0;
		int sec   = 0;		
		for( int i = 0, max = dList.size(); i < max; i++ ) {
			TDailyWKDetail currDetail = details[i];
			HSSFRow row = sheet.createRow(rowNumber);

			createCell(workbook, row, 0,  fstColumnStyle).setValue(dateFormatter.format(currDetail.getReportDate()));
			createCell(workbook, row, 1,  txtColumnStyle).setValue(currDetail.getPartNo());
			createCell(workbook, row, 2,  txtColumnStyle).setValue(currDetail.getPartName());
			createCell(workbook, row, 3,  txtColumnStyle).setValue(currDetail.getMaterialType());
			createCell(workbook, row, 4,  txtColumnStyle).setValue(currDetail.getWipName());
			createCell(workbook, row, 5,  txtColumnStyle).setValue(currDetail.getMachineName());
			createCell(workbook, row, 6,  txtColumnStyle).setValue(currDetail.getStaff());
			createCell(workbook, row, 7,  numColumnStyle).setValue(currDetail.getTotalQty(),true);
			createCell(workbook, row, 8,  numColumnStyle).setValue(currDetail.getNg(),true);
			createCell(workbook, row, 9,  numColumnStyle).setValue(currDetail.getOk(),true);
			createCell(workbook, row, 10,  txtCenterStyle).setValue(currDetail.getnOperationTime());
			createCell(workbook, row, 11,  dobColumnStyle).setValue(currDetail.getActualProductivity(),true);
			createCell(workbook, row, 12, dobColumnStyle).setValue(currDetail.getStandardProductivity(),true);
			createCell(workbook, row, 13, dobColumnStyle).setValue(currDetail.getStandardProduction(),true);
			createCell(workbook, row, 14, dobColumnStyle).setValue(currDetail.getActualProduction(),true);
			createCell(workbook, row, 15, dobColumnStyle).setValue(currDetail.getDiff(),true);
			createCell(workbook, row, 16, perColumnStyle).setValue(dobFormat.format(currDetail.getPercentage())+"%");
			//sum time
			hour += Integer.parseInt(currDetail.getnOperationTime().substring(0,2));
			min += Integer.parseInt(currDetail.getnOperationTime().substring(3,5));
			sec += Integer.parseInt(currDetail.getnOperationTime().substring(6));
			rowNumber++;
			if(i == dList.size()-1){
				// Grand Total
				row = sheet.createRow(rowNumber);
				createCell(workbook, row, 0,  grandColumnStyle);
				createCell(workbook, row, 1,  gtxtColumnStyle);
				createCell(workbook, row, 2,  gtxtColumnStyle);
				createCell(workbook, row, 3,  gtxtColumnStyle);
				createCell(workbook, row, 4,  grandColumnStyle);
				createCell(workbook, row, 5,  grandColumnStyle);
				createCell(workbook, row, 6,  grandColumnStyle);
				createMergedRegion(sheet,row.getRowNum(), row.getRowNum(), 0, 6);
				row.getCell(0).setCellValue("Grand Total");
				createCell(workbook, row, 7,  gNumColumnStyle);
				createCell(workbook, row, 8,  gNumColumnStyle);
				createCell(workbook, row, 9,  gNumColumnStyle);
				createCell(workbook, row, 10,  grandTimeStyle).setValue(this.calTime(hour, min, sec));
				createCell(workbook, row, 11,  gDobColumnStyle);
				createCell(workbook, row, 12, gDobColumnStyle);
				createCell(workbook, row, 13, gDobColumnStyle);
				createCell(workbook, row, 14, gDobColumnStyle);
				createCell(workbook, row, 15, gDobColumnStyle);
				row.getCell(7).setCellFormula("SUM(H5:H"+rowNumber+")");
				row.getCell(8).setCellFormula("SUM(I5:I"+rowNumber+")");
				row.getCell(9).setCellFormula("SUM(J5:J"+rowNumber+")");
				row.getCell(11).setCellFormula("SUM(L5:L"+rowNumber+")");
				row.getCell(12).setCellFormula("SUM(M5:M"+rowNumber+")");
				row.getCell(13).setCellFormula("SUM(N5:N"+rowNumber+")");
				row.getCell(14).setCellFormula("SUM(O5:O"+rowNumber+")");
				row.getCell(15).setCellFormula("SUM(P5:P"+rowNumber+")");
			}
		}// for loop
		// <!-- Setup 'Print Area'. -->
		workbook.setPrintArea(0, 0, 16, 0, rowNumber);
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