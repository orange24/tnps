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

public class WIP_R04ExcelView extends AbstractExcelView {

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook) throws Exception {

		TWipStockAdjust stockAdj = (TWipStockAdjust) model.get("tWipStockAdjust");

		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
		NumberFormat formatter = new DecimalFormat("#,##0");
		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short)11);

		Style firstCol = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setLeftBorder().setRightBorder().setBottomBorder().setWrapText().setFont(font);
		Style txtCenter = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setBottomBorder().setWrapText().setFont(font);
		Style numRight = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setBottomBorder().setWrapText().setFont(font);
		Style lastCol = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setBottomBorder().setWrapText().setFont(font);

		int rowNumber = 3;
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow row = sheet.createRow(rowNumber);

		for (TWipStockAdjust tWipStockAdj : stockAdj.getAdjustList()) {
			row = sheet.createRow(rowNumber);
			int result = ((tWipStockAdj.getAdjustStock()==null)?0:tWipStockAdj.getAdjustStock()) - ((tWipStockAdj.getCurrentStock()==null)?0:tWipStockAdj.getCurrentStock());

			createCell(workbook, row, 0, firstCol).setValue(tWipStockAdj.getWipName());
			createCell(workbook, row, 1, txtCenter).setValue(tWipStockAdj.getCustomerCode());
			createCell(workbook, row, 2, txtCenter).setValue(tWipStockAdj.getPartNo());
			createCell(workbook, row, 3, txtCenter).setValue(tWipStockAdj.getPartName());
			createCell(workbook, row, 4, txtCenter).setValue(dateFormat.format(tWipStockAdj.getReportDate()));
			createCell(workbook, row, 5, numRight);
			createCell(workbook, row, 6, numRight);
			createCell(workbook, row, 7, numRight);
			createCell(workbook, row, 8, txtCenter).setValue(tWipStockAdj.getAdjustReason());
			createCell(workbook, row, 9, txtCenter).setValue(tWipStockAdj.getUpdateBy());
			createCell(workbook, row, 10, lastCol).setValue(dateTimeFormat.format(tWipStockAdj.getLastUpdate()));
			
			sheet.getRow(rowNumber).getCell(5).setCellValue((tWipStockAdj.getCurrentStock() == null)?"":String.valueOf(formatter.format(tWipStockAdj.getCurrentStock())));
			sheet.getRow(rowNumber).getCell(6).setCellValue((tWipStockAdj.getAdjustStock() == null)?"":String.valueOf(formatter.format(tWipStockAdj.getAdjustStock())));
			sheet.getRow(rowNumber).getCell(7).setCellValue(formatter.format(result));
			rowNumber++;
		}
	}
}
