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
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import th.co.nttdata.tki.bean.PrintTagChangedHistory;

public class FNG_S07ExcelView extends AbstractExcelView {
	@Override
	@SuppressWarnings("unchecked")
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {

		SimpleDateFormat formatter = new SimpleDateFormat(
				"dd/MM/yyyy HH:mm:ss", Locale.US);

		List<PrintTagChangedHistory> list = (List<PrintTagChangedHistory>) model
				.get("data");
		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) 11);
		HSSFFont fontHD = workbook.createFont();
		fontHD.setFontName("Calibri");
		fontHD.setFontHeightInPoints((short) 11);
		fontHD.setBoldweight(Font.BOLDWEIGHT_BOLD);

		Style fstHDRStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER,
				HSSFCellStyle.VERTICAL_CENTER)
				.setTopBorder(CellStyle.BORDER_MEDIUM).setRightBorder()
				.setBgColor().setFont(fontHD).setWrapText();
		Style txtStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,
				HSSFCellStyle.VERTICAL_CENTER).setRightBorder()
				.setBottomBorder().setWrapText().setFont(font);
		Style txtCenterStyle = createStyle(workbook,
				HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER)
				.setRightBorder().setBottomBorder().setWrapText().setFont(font);
		Style numRightStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,
				HSSFCellStyle.VERTICAL_CENTER).setRightBorder()
				.setBottomBorder().setWrapText().setFont(font);

		HSSFSheet sheet = workbook.getSheetAt(0);
		int rowNumber = 4;
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		String createDate = null;

		for (PrintTagChangedHistory temp : list) {

			// Change dateTime format
			createDate = formatter.format(temp.getCreateDate());

			HSSFRow row = sheet.createRow(rowNumber);
			createCell(workbook, row, 0, txtStyle).setValue(temp.getLotNo());
			createCell(workbook, row, 1, txtStyle).setValue(temp.getCustomerFrom());
			createCell(workbook, row, 2, txtStyle).setValue(temp.getCustomerTo());
			createCell(workbook, row, 3, txtStyle).setValue(temp.getFgNoFrom());
			createCell(workbook, row, 4, txtStyle).setValue(temp.getFgNameFrom());
			createCell(workbook, row, 5, txtStyle).setValue(temp.getFgNoTo());
			createCell(workbook, row, 6, txtStyle).setValue(temp.getFgNameTo());
			createCell(workbook, row, 7, numRightStyle).setValue(temp.getSnpFrom());
			if (null == temp.getSnpTo()) {
				createCell(workbook, row, 8, numRightStyle).setValue("");
			} else {
				createCell(workbook, row, 8, numRightStyle).setValue(temp.getSnpTo());
			}
			createCell(workbook, row, 9, numRightStyle).setValue(temp.getQty());
			createCell(workbook, row, 10, txtStyle).setValue(createDate);
			createCell(workbook, row, 11, txtStyle).setValue(temp.getCreateBy());
			
			rowNumber++;
		}
	}
}