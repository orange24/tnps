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

	// Column index constants
	// Col 1  : Worker                     - รหัสพนักงาน (มาจาก sWorker ของ V_14)
	// Col 9  : Loss time                  - เวลาสูญเสียรวมตามรหัสพนักงาน
	// Col 10 : Grand Total Operation Time - Operation Time + Loss time
	// Col 11 : Standard Production Time   - เวลามาตรฐานที่ต้องใช้ผลิต
	// Col 12 : Production Rate (%)        - Col 10 / Col 11
	// Col 9-12 มีค่าเฉพาะงาน WK เท่านั้น (t_dailywk_detail) แถว DC/MC จะว่าง
	private static final int COL_REPORT_DATE   = 0;
	private static final int COL_WORKER        = 1;
	private static final int COL_PART_NO       = 2;
	private static final int COL_PART_NAME     = 3;
	private static final int COL_PROCESS       = 4;
	private static final int COL_TOTAL_QTY     = 5;
	private static final int COL_NG_QTY        = 6;
	private static final int COL_OK_QTY        = 7;
	private static final int COL_OPERATION     = 8;
	private static final int COL_LOSS_TIME     = 9;
	private static final int COL_GT_OPERATION  = 10;
	private static final int COL_STD_PROD_TIME = 11;
	private static final int COL_PROD_RATE     = 12;
	private static final int COL_ACT_PRODTVTY  = 13;
	private static final int COL_STD_PRODTVTY  = 14;
	private static final int COL_STD_PRICE     = 15;
	private static final int COL_ACT_PRICE     = 16;
	private static final int COL_DIFF          = 17;
	private static final int COL_PERCENTAGE    = 18;
	private static final int COL_LAST          = COL_PERCENTAGE;

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {
		TDailyWK tDailyWK = (TDailyWK) model.get("tDailyWK");
		final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		DecimalFormat dobFormat = new DecimalFormat("#,##0.00");
		DecimalFormat rateFormat = new DecimalFormat("#,##0.##");
		
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
		// worker time accumulators (minutes)
		long totalLossMin   = 0;
		long totalGtOpMin   = 0;
		long totalStdMin    = 0;
		for( int i = 0, max = dList.size(); i < max; i++ ) {
			TDailyWKDetail currDetail = details[i];
			HSSFRow row = sheet.createRow(rowNumber);
			createCell(workbook, row, COL_REPORT_DATE,  fstColumnStyle).setValue(dateFormatter.format(currDetail.getReportDate()));
			createCell(workbook, row, COL_WORKER,       txtCenterStyle).setValue(currDetail.getStaff());
			createCell(workbook, row, COL_PART_NO,      secColumnStyle).setValue(currDetail.getPartNo());
			createCell(workbook, row, COL_PART_NAME,    secColumnStyle).setValue(currDetail.getPartName());
			createCell(workbook, row, COL_PROCESS,      secColumnStyle).setValue(currDetail.getWipName());
			createCell(workbook, row, COL_TOTAL_QTY,    numColumnStyle).setValue(currDetail.getQty(),true);
			createCell(workbook, row, COL_NG_QTY,       numColumnStyle).setValue(currDetail.getNg(),true);
			createCell(workbook, row, COL_OK_QTY,       numColumnStyle).setValue(currDetail.getOk(),true);
			createCell(workbook, row, COL_OPERATION,    txtCenterStyle).setValue(currDetail.getnOperationTime());

			// Loss time / Grand Total Operation Time / Standard Production Time / Production Rate
			// มีเฉพาะงาน WK - แถว DC/MC ที่ไม่มีข้อมูลจะเว้นว่างไว้
			boolean hasWorkerTime = currDetail.getLossTime() != null || currDetail.getStdTime() != null;
			if (hasWorkerTime) {
				long lossMin  = currDetail.getLossTime() != null ? currDetail.getLossTime().longValue() : 0L;
				long opMin    = currDetail.getOperationTimeSec() != null
								? Math.round(currDetail.getOperationTimeSec().doubleValue() / 60.0) : 0L;
				long gtOpMin  = opMin + lossMin;
				long stdMin   = currDetail.getStdTime() != null ? currDetail.getStdTime().longValue() : 0L;

				createCell(workbook, row, COL_LOSS_TIME,     txtCenterStyle).setValue(minToTime(lossMin));
				createCell(workbook, row, COL_GT_OPERATION,  txtCenterStyle).setValue(minToTime(gtOpMin));
				createCell(workbook, row, COL_STD_PROD_TIME, txtCenterStyle).setValue(minToTime(stdMin));
				if (stdMin > 0) {
					double rate = ((double) gtOpMin / (double) stdMin) * 100.0;
					createCell(workbook, row, COL_PROD_RATE, perColumnStyle).setValue(rateFormat.format(rate)+"%");
				} else {
					createCell(workbook, row, COL_PROD_RATE, perColumnStyle);
				}
				totalLossMin += lossMin;
				totalGtOpMin += gtOpMin;
				totalStdMin  += stdMin;
			} else {
				createCell(workbook, row, COL_LOSS_TIME,     txtCenterStyle);
				createCell(workbook, row, COL_GT_OPERATION,  txtCenterStyle);
				createCell(workbook, row, COL_STD_PROD_TIME, txtCenterStyle);
				createCell(workbook, row, COL_PROD_RATE,     perColumnStyle);
			}

			createCell(workbook, row, COL_ACT_PRODTVTY,  dobColumnStyle).setValue(currDetail.getActualProductivity(),true);
			createCell(workbook, row, COL_STD_PRODTVTY,  dobColumnStyle).setValue(currDetail.getStandardProductivity(),true);
			createCell(workbook, row, COL_STD_PRICE,     dobColumnStyle).setValue(currDetail.getStandardProduction(),true);
			createCell(workbook, row, COL_ACT_PRICE,     dobColumnStyle).setValue(currDetail.getActualProduction(),true);
			createCell(workbook, row, COL_DIFF,          dobColumnStyle).setValue(currDetail.getDiff(),true);
			createCell(workbook, row, COL_PERCENTAGE,    perColumnStyle).setValue(dobFormat.format(currDetail.getPercentage())+"%");
			//sum time
			hour += Integer.parseInt(currDetail.getnOperationTime().substring(0,2));
			min += Integer.parseInt(currDetail.getnOperationTime().substring(3,5));
			sec += Integer.parseInt(currDetail.getnOperationTime().substring(6));

			rowNumber++;
			if(i == dList.size()-1){
				row = sheet.createRow(rowNumber);
				// Grand total
				createCell(workbook, row, COL_REPORT_DATE,   grandColumnStyle);
				createCell(workbook, row, COL_WORKER,        grandColumnStyle);
				createCell(workbook, row, COL_PART_NO,       grandColumnStyle);
				createCell(workbook, row, COL_PART_NAME,     grandColumnStyle);
				createCell(workbook, row, COL_PROCESS,       grandColumnStyle).setValue("Grand Total");
				createCell(workbook, row, COL_TOTAL_QTY,     gNumColumnStyle);
				createCell(workbook, row, COL_NG_QTY,        gNumColumnStyle);
				createCell(workbook, row, COL_OK_QTY,        gNumColumnStyle);
				createCell(workbook, row, COL_OPERATION,     grandTimeStyle).setValue(this.calTime(hour, min, sec));
				createCell(workbook, row, COL_LOSS_TIME,     grandTimeStyle).setValue(minToTime(totalLossMin));
				createCell(workbook, row, COL_GT_OPERATION,  grandTimeStyle).setValue(minToTime(totalGtOpMin));
				createCell(workbook, row, COL_STD_PROD_TIME, grandTimeStyle).setValue(minToTime(totalStdMin));
				createCell(workbook, row, COL_PROD_RATE,     grandTimeStyle);
				createCell(workbook, row, COL_ACT_PRODTVTY,  gDobColumnStyle);
				createCell(workbook, row, COL_STD_PRODTVTY,  gDobColumnStyle);
				createCell(workbook, row, COL_STD_PRICE,     gDobColumnStyle);
				createCell(workbook, row, COL_ACT_PRICE,     gDobColumnStyle);
				createCell(workbook, row, COL_DIFF,          gDobColumnStyle);
				row.getCell(COL_TOTAL_QTY   ).setCellFormula("SUM(F5:F"+rowNumber+")");
				row.getCell(COL_NG_QTY      ).setCellFormula("SUM(G5:G"+rowNumber+")");
				row.getCell(COL_OK_QTY      ).setCellFormula("SUM(H5:H"+rowNumber+")");
				row.getCell(COL_ACT_PRODTVTY).setCellFormula("SUM(N5:N"+rowNumber+")");
				row.getCell(COL_STD_PRODTVTY).setCellFormula("SUM(O5:O"+rowNumber+")");
				row.getCell(COL_STD_PRICE   ).setCellFormula("SUM(P5:P"+rowNumber+")");
				row.getCell(COL_ACT_PRICE   ).setCellFormula("SUM(Q5:Q"+rowNumber+")");
				row.getCell(COL_DIFF        ).setCellFormula("SUM(R5:R"+rowNumber+")");
			}
		}// for loop
		// <!-- Setup 'Print Area'. -->
		workbook.setPrintArea(0, 0, COL_LAST, 0, rowNumber);
	}

	/** นาที -> hh:mm:ss ให้อยู่ในรูปแบบเดียวกับคอลัมน์ Operation Time */
	private String minToTime(long minutes){
		long hh = minutes / 60;
		long mm = minutes % 60;
		return (hh < 10 ? "0"+hh : ""+hh) + ":" + (mm < 10 ? "0"+mm : ""+mm) + ":00";
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