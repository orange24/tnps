package th.co.nttdata.tki.excel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;

import th.co.nttdata.tki.bean.filter.FgMasterFilter;

public class FGM_R01ExcelView extends AbstractExcelView {

	@SuppressWarnings("unchecked")
	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {

		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.US);
		SimpleDateFormat formatter = new SimpleDateFormat(
				"dd/MM/yyyy HH:mm:ss", Locale.US);

		List<FgMasterFilter> list = (List<FgMasterFilter>) model
				.get("fgMasterExportList");

		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) 11);
		HSSFFont fontHD = workbook.createFont();
		fontHD.setFontName("Calibri");
		fontHD.setFontHeightInPoints((short) 11);
		fontHD.setBoldweight(Font.BOLDWEIGHT_BOLD);

		Style txtLeftStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,
				HSSFCellStyle.VERTICAL_CENTER).setRightBorder()
				.setBottomBorder().setWrapText().setFont(font);
		Style txtCenterStyle = createStyle(workbook,
				HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER)
				.setRightBorder().setBottomBorder().setWrapText().setFont(font);
		Style numRightStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,
				HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0")
				.setRightBorder().setBottomBorder().setWrapText().setFont(font);
		Style DoubleFormatStyle = createStyle(workbook,
				HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER)
				.setFormat("#,##0.00").setBottomBorder().setRightBorder()
				.setWrapText().setFont(font);

		HSSFSheet sheet = workbook.getSheetAt(0);

		int rowNumber = 3;
		int i = 0;
		Date date = new Date();
		String createDate = null;
		String lastUpdate = null;

		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		for (FgMasterFilter temp : list) {

			// Change dateTime format
			date = dateTimeFormat.parse(list.get(i).getCreateDate());
			createDate = formatter.format(date);
			date = dateTimeFormat.parse(list.get(i).getLastUpdate());
			lastUpdate = formatter.format(date);

			HSSFRow row = sheet.createRow(rowNumber);
			createCell(workbook, row, 0, txtLeftStyle).setValue(temp.getFgId());
			createCell(workbook, row, 1, txtLeftStyle).setValue(temp.getFgNo());
			createCell(workbook, row, 2, txtLeftStyle).setValue(
					temp.getFgName());
			createCell(workbook, row, 3, txtLeftStyle).setValue(temp.getUom());
			int snpFg = 0;
			if (StringUtils.isNotEmpty(temp.getSnpFG())) {
				snpFg = Integer.valueOf(temp.getSnpFG());
			}
			createCell(workbook, row, 4, numRightStyle).setValue(snpFg);
			double weight = 0.0;
			if (StringUtils.isNotEmpty(temp.getWeight())) {
				weight = Double.valueOf(temp.getWeight());
			}
			createCell(workbook, row, 5, DoubleFormatStyle).setValue(weight);
			double price = 0;
			if (StringUtils.isNotEmpty(temp.getPrice())) {
				price = Double.valueOf(temp.getPrice());
			}
			createCell(workbook, row, 6, DoubleFormatStyle).setValue(price);
			createCell(workbook, row, 7, txtLeftStyle).setValue(
					temp.getCurrency());
			createCell(workbook, row, 8, txtLeftStyle).setValue(
					temp.getVendorFgNo());
			createCell(workbook, row, 9, txtLeftStyle).setValue(
					temp.getClassifyBiz());
			createCell(workbook, row, 10, txtLeftStyle).setValue(
					temp.getPlace());
			createCell(workbook, row, 11, txtLeftStyle).setValue(
					temp.getSubBusiness());
			if (("1").equals(temp.getIsenable())) {
				createCell(workbook, row, 12, txtCenterStyle).setValue("Yes");
			} else {
				createCell(workbook, row, 12, txtCenterStyle).setValue("No");
			}
			createCell(workbook, row, 13, txtCenterStyle).setValue(createDate);
			createCell(workbook, row, 14, txtLeftStyle).setValue(
					temp.getCreateBy());
			createCell(workbook, row, 15, txtCenterStyle).setValue(lastUpdate);
			createCell(workbook, row, 16, txtLeftStyle).setValue(
					temp.getUpdateBy());
			rowNumber++;
			i++;
		}

	}
}
