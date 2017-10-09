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

import th.co.nttdata.tki.bean.TFG;
import th.co.nttdata.tki.bean.TFGDetail;

public class FNG_R03ExcelView extends AbstractExcelView {

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook) throws Exception {
		
		final SimpleDateFormat dateFormatter	 = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
		
		TFG tfg = (TFG) model.get("TFG");
		Map<Integer,String> reportTypeMap = (Map<Integer,String>) model.get("reportTypeMap");
		
		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short)11);
		Style txtCenterStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style txtLeftCenStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style numberStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setRightBorder().setWrapText().setFont(font);
		
		HSSFSheet sheet = workbook.getSheetAt(0);
		
		int rowNumber = 4;
		List<TFGDetail> tfgList = tfg.getDetails();
		HSSFRow rowHSSF ;
		
		for(TFGDetail detail: tfgList){
			rowHSSF = sheet.createRow(rowNumber);
			
			createCell(workbook, rowHSSF, 0, txtCenterStyle).setValue(dateFormatter.format(detail.getReportDate()));
			createCell(workbook, rowHSSF, 1, txtLeftCenStyle).setValue(detail.getCustomerCode());
			createCell(workbook, rowHSSF, 2, txtLeftCenStyle).setValue(detail.getFgNo());
			createCell(workbook, rowHSSF, 3, txtLeftCenStyle).setValue(detail.getFgName());
			createCell(workbook, rowHSSF, 4, txtCenterStyle).setValue(reportTypeMap.get(detail.getReportType()));
			createCell(workbook, rowHSSF, 5, txtLeftCenStyle).setValue(detail.getLotSeqNo());
			createCell(workbook, rowHSSF, 6, txtCenterStyle).setValue(detail.getMoldNo());
			createCell(workbook, rowHSSF, 7, numberStyle).setValue(detail.getFgIn());
			createCell(workbook, rowHSSF, 8, numberStyle).setValue(detail.getFgOut());
			createCell(workbook, rowHSSF, 9, txtCenterStyle).setValue(detail.getUpdateBy());
			createCell(workbook, rowHSSF, 10, txtCenterStyle).setValue(dateTimeFormatter.format(detail.getLastUpdate()));
			
			rowNumber++;
		}
	}
}
