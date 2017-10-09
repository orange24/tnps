package th.co.nttdata.tki.excel;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import th.co.nttdata.tki.bean.filter.PartRoutingMasterFilter;

public class S_PRT_S03ExcelView extends AbstractExcelView {

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {
		@SuppressWarnings("unchecked")
		List<PartRoutingMasterFilter> list = (List<PartRoutingMasterFilter>) model
				.get("partRoutingMasterExportList");

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

		for (PartRoutingMasterFilter temp : list) {
			HSSFRow row = sheet.createRow(rowNumber);
			createCell(workbook, row, 0, numRightStyle).setValue(
					temp.getCustomerId());
			createCell(workbook, row, 1, txtStyle).setValue(
					temp.getCustomerCode());
			createCell(workbook, row, 2, numRightStyle)
					.setValue(temp.getFgId());
			createCell(workbook, row, 3, txtStyle).setValue(temp.getFgNo());
			createCell(workbook, row, 4, txtStyle).setValue(temp.getFgName());
			createCell(workbook, row, 5, numRightStyle).setValue(
					temp.getPartId());
			createCell(workbook, row, 6, txtStyle).setValue(temp.getPartNo());
			createCell(workbook, row, 7, txtStyle).setValue(temp.getPartName());
			createCell(workbook, row, 8, txtCenterStyle).setValue(
					temp.getWipOrder());
			createCell(workbook, row, 9, txtStyle).setValue(temp.getWip());
			createCell(workbook, row, 10, txtStyle).setValue(temp.getRemark());
			createCell(workbook, row, 11, txtStyle).setValue(temp.getIsCalc());
			rowNumber++;
		}
	}
}
