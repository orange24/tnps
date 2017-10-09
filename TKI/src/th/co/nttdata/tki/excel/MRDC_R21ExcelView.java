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

import th.co.nttdata.tki.bean.MMoldDetail;

public class MRDC_R21ExcelView extends AbstractExcelView {

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {

		MMoldDetail mDetail = (MMoldDetail) model.get("mDetail");

		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) 11);
		HSSFFont fontHD = workbook.createFont();
		fontHD.setFontName("Calibri");
		fontHD.setFontHeightInPoints((short) 11);
		fontHD.setBoldweight(Font.BOLDWEIGHT_BOLD);

		HSSFSheet sheet = workbook.getSheetAt(0);

		short whiteBG = HSSFColor.WHITE.index;

		List<MMoldDetail> moldDetailList = mDetail.getmDetailList();
		final SimpleDateFormat dateFormatter = new SimpleDateFormat(
				"dd/MM/yyyy", Locale.US);

		Style c01Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,
				HSSFCellStyle.VERTICAL_CENTER).setBottomBorder()
				.setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style c02Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,
				HSSFCellStyle.VERTICAL_CENTER).setBottomBorder()
				.setRightBorder().setWrapText().setFont(font);
		Style cenStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER,
				HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setWrapText()
				.setFont(font);
		Style cenTopStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER,
				HSSFCellStyle.VERTICAL_CENTER).setTopBorder().setRightBorder()
				.setWrapText().setFont(font);
		Style c03Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,
				HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0")
				.setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style c03TopStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,
				HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0")
				.setTopBorder().setRightBorder().setWrapText().setFont(font);
		Style sumStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,
				HSSFCellStyle.VERTICAL_CENTER).setWrapText().setFont(fontHD);
		Style sumTopBorderStyle = createStyle(workbook,
				HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER)
				.setTopBorder().setWrapText().setFont(fontHD);
		Style sumNumStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,
				HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0")
				.setTopBorder().setFont(fontHD).setWrapText()
				.setBottomBorder(CellStyle.BORDER_DOUBLE);
		Style c04Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,
				HSSFCellStyle.VERTICAL_CENTER).setTopBorder().setLeftBorder()
				.setRightBorder().setWrapText().setFont(font);
		Style c05Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,
				HSSFCellStyle.VERTICAL_CENTER).setLeftBorder().setRightBorder()
				.setWrapText().setFont(font);

		int rowNumber = 3;
		HSSFRow row = sheet.createRow(rowNumber);
		int moldDetailListSize = moldDetailList.size();
		for (int i = 0; i < moldDetailListSize; i++) {
			row = sheet.createRow(rowNumber);

			MMoldDetail moldDetail = moldDetailList.get(i);
			if (i == 0) { // first line
				createCell(workbook, row, 0, c04Style).setValue(
						moldDetail.getMoldName());
				createCell(workbook, row, 1, c04Style).setValue(
						moldDetail.getMoldNo());
				createCell(workbook, row, 5, c03TopStyle).setValue(
						moldDetail.getProductionQty());
				createCell(workbook, row, 6, c03TopStyle).setValue(
						moldDetail.getSalesQty());
				createCell(workbook, row, 7, cenTopStyle).setValue(
						dateFormatter.format(moldDetail.getStartDate()));
				createCell(workbook, row, 8, c03TopStyle).setValue(
						moldDetail.getGuaranteeShot());
				createCell(workbook, row, 9, c03TopStyle).setValue(
						moldDetail.getQtyShot());
				createCell(workbook, row, 10, c03TopStyle).setValue(
						moldDetail.getInitialShot());
				createCell(workbook, row, 11, c03TopStyle).setValue(
						moldDetail.getTotalDCShot());
				createCell(workbook, row, 12, c03TopStyle).setValue(
						moldDetail.getTotalFGSold());
			} else if ((moldDetail.getMoldName() + moldDetail.getMoldNo())
					.equals((moldDetailList.get(i - 1).getMoldName() + moldDetail
							.getMoldNo()))) {
				createCell(workbook, row, 0, c05Style);
				createCell(workbook, row, 1, c05Style);
				createCell(workbook, row, 5, c05Style);
				createCell(workbook, row, 6, c05Style);
				createCell(workbook, row, 7, c05Style);
				createCell(workbook, row, 8, c05Style);
				createCell(workbook, row, 9, c05Style);
				createCell(workbook, row, 10, c05Style);
				createCell(workbook, row, 11, c05Style);
				createCell(workbook, row, 12, c05Style);
			} else {
				createCell(workbook, row, 0, c04Style).setValue(
						moldDetail.getMoldName());
				createCell(workbook, row, 1, c04Style).setValue(
						moldDetail.getMoldNo());
				createCell(workbook, row, 5, c03TopStyle).setValue(
						moldDetail.getProductionQty());
				createCell(workbook, row, 6, c03TopStyle).setValue(
						moldDetail.getSalesQty());
				createCell(workbook, row, 7, cenTopStyle).setValue(
						dateFormatter.format(moldDetail.getStartDate()));
				createCell(workbook, row, 8, c03TopStyle).setValue(
						moldDetail.getGuaranteeShot());
				createCell(workbook, row, 9, c03TopStyle).setValue(
						moldDetail.getQtyShot());
				createCell(workbook, row, 10, c03TopStyle).setValue(
						moldDetail.getInitialShot());
				createCell(workbook, row, 11, c03TopStyle).setValue(
						moldDetail.getTotalDCShot());
				createCell(workbook, row, 12, c03TopStyle).setValue(
						moldDetail.getTotalFGSold());
			}
			createCell(workbook, row, 2, c01Style).setValue(
					moldDetail.getCustomerCode());
			createCell(workbook, row, 3, c02Style).setValue(
					moldDetail.getPartNo());
			createCell(workbook, row, 4, c02Style).setValue(
					moldDetail.getPartName());

			rowNumber++;
		}
		if (moldDetailListSize > 0) {
			row = sheet.createRow(rowNumber);
			createCell(workbook, row, 0, sumTopBorderStyle);
			createCell(workbook, row, 1, sumTopBorderStyle);
			createCell(workbook, row, 2, sumStyle);
			createCell(workbook, row, 3, sumStyle);
			createCell(workbook, row, 4, sumStyle);
			createMergedRegion(sheet, rowNumber, rowNumber, 0, 4);
			sheet.getRow(rowNumber).getCell(0).setCellValue("Grand Total");
			createCell(workbook, row, 5, sumNumStyle);
			createCell(workbook, row, 6, sumNumStyle);
			row.getCell(5).setCellFormula("SUM(F4:F" + rowNumber + ")");
			row.getCell(6).setCellFormula("SUM(G4:G" + rowNumber + ")");
			createCell(workbook, row, 7, sumTopBorderStyle);
			createCell(workbook, row, 8, sumTopBorderStyle);
			createCell(workbook, row, 9, sumTopBorderStyle);
			createCell(workbook, row, 10, sumTopBorderStyle);
			createCell(workbook, row, 11, sumNumStyle);
			createCell(workbook, row, 12, sumNumStyle);
			row.getCell(11).setCellFormula("SUM(L4:L" + rowNumber + ")");
			row.getCell(12).setCellFormula("SUM(M4:M" + rowNumber + ")");
		}
		// <!-- Setup 'Print Area'. -->
		workbook.setPrintArea(0, 0, 12, 0, rowNumber - 1);

	}
}
