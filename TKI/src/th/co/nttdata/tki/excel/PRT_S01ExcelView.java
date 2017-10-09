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
import org.apache.poi.ss.usermodel.Font;

import th.co.nttdata.tki.bean.MPart;

public class PRT_S01ExcelView extends AbstractExcelView {
	@Override
	@SuppressWarnings("unchecked")
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {

		final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);

		List<MPart> list = (List<MPart>) model.get("data");

		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) 11);
		HSSFFont fontHD = workbook.createFont();
		fontHD.setFontName("Calibri");
		fontHD.setFontHeightInPoints((short) 11);
		fontHD.setBoldweight(Font.BOLDWEIGHT_BOLD);

		Style txtStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,
				HSSFCellStyle.VERTICAL_CENTER).setRightBorder()
				.setBottomBorder().setWrapText().setFont(font);
		Style txtCenterStyle = createStyle(workbook,
				HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER)
				.setRightBorder().setBottomBorder().setWrapText().setFont(font);
		Style numRightStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,
				HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setRightBorder()
				.setBottomBorder().setWrapText().setFont(font);

		HSSFSheet sheet = workbook.getSheetAt(0);
		int rowNumber = 3;
		String createDate = null;
		String lastUpdate = null;

		for (MPart obj : list) {
			// Change dateTime format
			createDate = formatter.format(obj.getCreateDate());
			lastUpdate = formatter.format(obj.getLastUpdate());

			HSSFRow row = sheet.createRow(rowNumber);
			createCell(workbook, row, 0, txtStyle).setValue(obj.getPartId());
			createCell(workbook, row, 1, txtStyle).setValue(obj.getPartNo());
			createCell(workbook, row, 2, txtStyle).setValue(obj.getPartName());
			createCell(workbook, row, 3, numRightStyle).setValue(Integer.valueOf(obj.getLot()));
			createCell(workbook, row, 4, txtStyle).setValue(obj.getMaterial());
			if (obj.getChoose()) {
				createCell(workbook, row, 5, txtCenterStyle).setValue("Yes");
			} else {
				createCell(workbook, row, 5, txtCenterStyle).setValue("No");
			}
			createCell(workbook, row, 6, txtCenterStyle).setValue(createDate);
			createCell(workbook, row, 7, txtStyle).setValue(obj.getCreatedBy());
			createCell(workbook, row, 8, txtCenterStyle).setValue(lastUpdate);
			createCell(workbook, row, 9, txtStyle).setValue(obj.getUpdatedBy());
			rowNumber++;
		}
	}
}