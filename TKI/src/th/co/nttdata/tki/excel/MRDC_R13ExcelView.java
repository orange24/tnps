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

public class MRDC_R13ExcelView extends AbstractExcelView{

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
		Style headerColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setWrapText().setFont(fontHD);
		
		Style fstColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setLeftBorder().setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style secColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style txtCenterStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style numColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style dobColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.00").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style perColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.00").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		// Grand Total
		Style grandColumnStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setWrapText().setFont(fontHD);
		Style gNumColumnStyle  = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder(CellStyle.BORDER_DOUBLE).setWrapText().setFont(fontHD);
		Style gDobColumnStyle  = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0.00").setBottomBorder(CellStyle.BORDER_DOUBLE).setWrapText().setFont(fontHD);
		Style grandTimeStyle   = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFont(fontHD).setWrapText().setBottomBorder(CellStyle.BORDER_DOUBLE);

		// <!-- Get 'First' Sheet. -->
		HSSFSheet sheet = workbook.getSheetAt(0);

		int rowNumber = 4;

		// header
		String dateFrom = "";
		String dateTo = "";
		if(tDailyWK.getReportDateFr() != null){
			dateFrom = dateFormatter.format(tDailyWK.getReportDateFr());
		}
		if(tDailyWK.getReportDateTo() != null){
			dateTo = dateFormatter.format(tDailyWK.getReportDateTo());
		}
		HSSFRow headerRow = sheet.getRow(2);
		createCell(workbook, headerRow, 0, headerColumnStyle).setValue("Report Date (From) : "+dateFrom);
		createCell(workbook, headerRow, 2, headerColumnStyle).setValue("Report Date (To) : "+dateTo);

		// insert data
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
			createCell(workbook, row, 1,  fstColumnStyle).setValue(currDetail.getPartNo());
			createCell(workbook, row, 2,  secColumnStyle).setValue(currDetail.getPartName());
			createCell(workbook, row, 3,  secColumnStyle).setValue(currDetail.getWipName());
			createCell(workbook, row, 4,  numColumnStyle).setValue(currDetail.getQty(),true);
			createCell(workbook, row, 5,  numColumnStyle).setValue(currDetail.getNg(),true);
			createCell(workbook, row, 6,  numColumnStyle).setValue(currDetail.getOk(),true);
			createCell(workbook, row, 7,  txtCenterStyle).setValue(currDetail.getnOperationTime());
			createCell(workbook, row, 8,  dobColumnStyle).setValue(currDetail.getActualProductivity(),true);
			createCell(workbook, row, 9,  dobColumnStyle).setValue(currDetail.getStandardProductivity(),true);
			createCell(workbook, row, 10,  dobColumnStyle).setValue(currDetail.getStandardProduction(),true);
			createCell(workbook, row, 11,  dobColumnStyle).setValue(currDetail.getActualProduction(),true);
			createCell(workbook, row, 12, dobColumnStyle).setValue(currDetail.getDiff(),true);
			createCell(workbook, row, 13, perColumnStyle).setValue(dobFormat.format(currDetail.getPercentage())+"%");
			//sum time
			hour += Integer.parseInt(currDetail.getnOperationTime().substring(0,2));
			min += Integer.parseInt(currDetail.getnOperationTime().substring(3,5));
			sec += Integer.parseInt(currDetail.getnOperationTime().substring(6));

			rowNumber++;
			if(i == dList.size()-1){
				row = sheet.createRow(rowNumber);
				// Grand total
				createCell(workbook, row, 0,  grandColumnStyle);
				createCell(workbook, row, 1,  grandColumnStyle);
				createCell(workbook, row, 2,  grandColumnStyle);
				createCell(workbook, row, 3,  grandColumnStyle).setValue("Grand Total");
				createCell(workbook, row, 4,  gNumColumnStyle);
				createCell(workbook, row, 5,  gNumColumnStyle);
				createCell(workbook, row, 6,  gNumColumnStyle);
				createCell(workbook, row, 7,  grandTimeStyle).setValue(this.calTime(hour, min, sec));
				createCell(workbook, row, 8,  gDobColumnStyle);
				createCell(workbook, row, 9,  gDobColumnStyle);
				createCell(workbook, row, 10,  gDobColumnStyle);
				createCell(workbook, row, 11,  gDobColumnStyle);
				createCell(workbook, row, 12, gDobColumnStyle);
				row.getCell(4).setCellFormula("SUM(E5:E"+rowNumber+")");
				row.getCell(5).setCellFormula("SUM(F5:F"+rowNumber+")");
				row.getCell(6).setCellFormula("SUM(G5:G"+rowNumber+")");
				row.getCell(8).setCellFormula("SUM(I5:I"+rowNumber+")");
				row.getCell(9).setCellFormula("SUM(J5:J"+rowNumber+")");
				row.getCell(10).setCellFormula("SUM(K5:K"+rowNumber+")");
				row.getCell(11).setCellFormula("SUM(L5:L"+rowNumber+")");
				row.getCell(12).setCellFormula("SUM(M5:M"+rowNumber+")");
			}
		}// for loop
		// <!-- Setup 'Print Area'. -->
		workbook.setPrintArea(0, 0, 12, 0, rowNumber);
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