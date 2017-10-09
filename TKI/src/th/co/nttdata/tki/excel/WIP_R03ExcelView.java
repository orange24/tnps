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

import th.co.nttdata.tki.bean.TWipStockAdjust;

public class WIP_R03ExcelView extends AbstractExcelView {

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook) throws Exception {

		TWipStockAdjust stockAdj = (TWipStockAdjust) model.get("tWipStockAdjust");

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
			for (TWipStockAdjust tWipStockAdj : stockAdj.getAdjustList()) {
				row = sheet.createRow(rowNumber);
	
				createCell(workbook, row, 0, firstCol).setValue(tWipStockAdj.getWipName());
				createCell(workbook, row, 1, txtleft).setValue(tWipStockAdj.getCustomerCode());
				createCell(workbook, row, 2, txtleft).setValue(tWipStockAdj.getPartNo());
				createCell(workbook, row, 3, txtleft).setValue(tWipStockAdj.getPartName());
				createCell(workbook, row, 4, txtCenter).setValue(dateFormat.format(tWipStockAdj.getReportDate()));
				createCell(workbook, row, 5, numRight).setValue(formatter.format(tWipStockAdj.getCurrentStock()));
				createCell(workbook, row, 6, numRight);
				createCell(workbook, row, 7, txtleft);
	
				rowNumber++;
			}
		}
	}
}
