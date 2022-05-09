package th.co.nttdata.tki.excel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import th.co.nttdata.tki.bean.TFG;
import th.co.nttdata.tki.bean.TFGStock;

public class FNG_R05ExcelView extends AbstractExcelView {

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook) throws Exception {
		TFG tfg = (TFG) model.get("TFG");

		final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);

		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short)11);
		Style txtCenterStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style txtLeftCenStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style numberStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);

		// <!-- Get 'First' Sheet. -->
		HSSFSheet sheet = workbook.getSheetAt(0);

		// header customer
		HSSFRow row2 = sheet.getRow(2);
		row2.getCell(0).setCellValue("Date : " + dateTimeFormatter.format(new Date()));

		int rowNumber = 4;
		int totalLotQty = 0;
		int totalRemain = 0;
		List<TFGStock> tfgList = tfg.getStockList();
		HSSFRow rowHSSF ;

		for(TFGStock detail: tfgList){
			rowHSSF = sheet.createRow(rowNumber);
			createCell(workbook, rowHSSF, 0, txtLeftCenStyle).setValue(detail.getCustomerCode());
			createCell(workbook, rowHSSF, 1, txtLeftCenStyle).setValue(detail.getFgNo());
			createCell(workbook, rowHSSF, 2, txtLeftCenStyle).setValue(detail.getFgName());
			createCell(workbook, rowHSSF, 3, txtLeftCenStyle).setValue(detail.getLotSeqNo());
			createCell(workbook, rowHSSF, 4, txtCenterStyle).setValue(detail.getMoldNo());
			createCell(workbook, rowHSSF, 5, numberStyle).setValue(detail.getLotSeqQty());
			createCell(workbook, rowHSSF, 6, numberStyle).setValue(detail.getRemain());
			rowNumber++;
			totalLotQty += detail.getLotSeqQty();
			totalRemain += detail.getRemain();
		}

		rowHSSF = sheet.createRow(rowNumber);
		createCell(workbook, rowHSSF, 5, numberStyle).setValue(totalLotQty);
		createCell(workbook, rowHSSF, 6, numberStyle).setValue(totalRemain);
	}
}
