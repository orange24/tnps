package th.co.nttdata.tki.excel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import th.co.nttdata.tki.bean.TFGStock;

public class FNG_R04ExcelView extends AbstractExcelView {

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook) throws Exception {

		TFGStock stockAdj = (TFGStock) model.get("tfgStock");

		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		NumberFormat formatter = new DecimalFormat("#,##0");
		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) 11);

		Style firstCol = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setLeftBorder()
				.setRightBorder().setBottomBorder().setWrapText().setFont(font);
		Style txtCenter = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setRightBorder()
				.setBottomBorder().setWrapText().setFont(font);
		Style txtleft = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setRightBorder()
				.setBottomBorder().setWrapText().setFont(font);
		Style numRight = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setRightBorder()
				.setBottomBorder().setWrapText().setFont(font);

		int rowNumber = 1;
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow row = sheet.createRow(rowNumber);

		if(null != stockAdj){
			for (TFGStock tfgStock : stockAdj.getTfgStockList()) {
				row = sheet.createRow(rowNumber);
	
				createCell(workbook, row, 0, firstCol).setValue(tfgStock.getCustomerCode());
				createCell(workbook, row, 1, txtleft).setValue(tfgStock.getFgNo());
				createCell(workbook, row, 2, txtleft).setValue(tfgStock.getFgName());
				createCell(workbook, row, 3, txtCenter).setValue(dateFormat.format(tfgStock.getReportDate()));
				createCell(workbook, row, 4, numRight).setValue(formatter.format(tfgStock.getFgBalance()));
				createCell(workbook, row, 5, numRight);
				createCell(workbook, row, 6, txtleft);
	
				rowNumber++;
			}
		}
	}

}
