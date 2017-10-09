package th.co.nttdata.tki.excel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import th.co.nttdata.tki.bean.MMoldDetail;

public class MLD_R01ExcelView extends AbstractExcelView {

	@Override
	protected void build( Map<String, Object> model, HSSFWorkbook workbook ) throws Exception {

		MMoldDetail moldDetail = (MMoldDetail) model.get("moldDetail");
		
		final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		
		// <!-- Assign: CellStyle. -->
		Style r01c00Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setLeftBorder().setRightBorder().setWrapText();
		Style r01c01Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r01c04Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		Style r01c06Style = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setFormat("#,##0").setWrapText();
		Style r01c15Style = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setFormat("#,##0").setWrapText();

		// <!-- Get 'First' Sheet. -->
		HSSFSheet sheet = workbook.getSheetAt(0);
		
		int rowNumber = 4;
		
		// <!-- set Value of dateList -->
		List<MMoldDetail> mList = moldDetail.getmDetailList();
		MMoldDetail[] details = mList.toArray( new MMoldDetail[mList.size()] );
		for( int i = 0, max = mList.size(); i < max; i++ ) {
			MMoldDetail detail = details[i];
			
			HSSFRow fstRow = sheet.createRow(rowNumber);
			createCell(workbook, fstRow, 0, r01c00Style).setValue(detail.getCustomerCode());
			createCell(workbook, fstRow, 1, r01c01Style).setValue(detail.getPartNo());
			createCell(workbook, fstRow, 2, r01c01Style).setValue(detail.getPartName());
			createCell(workbook, fstRow, 3, r01c01Style).setValue(detail.getMoldName());
			createCell(workbook, fstRow, 4, r01c04Style).setValue(detail.getMoldNo());
			createCell(workbook, fstRow, 5, r01c04Style).setValue(detail.getQtyShot());
			createCell(workbook, fstRow, 6, r01c04Style).setValue((detail.getStartDate() == null)?"":dateFormatter.format(detail.getStartDate()));
			createCell(workbook, fstRow, 7, r01c04Style).setValue((detail.getEndDate() == null)?"":dateFormatter.format(detail.getEndDate()));
			createCell(workbook, fstRow, 8, r01c06Style).setValue(detail.getAlertShot(),true);
			createCell(workbook, fstRow, 9, r01c06Style).setValue(detail.getGuaranteeShot(),true);
			createCell(workbook, fstRow, 10, r01c06Style).setValue(detail.getInitialShot(),true);
			createCell(workbook, fstRow, 11, r01c06Style).setValue(detail.getTotalDCShot(),true);
			createCell(workbook, fstRow, 12, r01c04Style).setValue(detail.getStatusDcName());
			createCell(workbook, fstRow, 13, r01c06Style).setValue(detail.getTotalFGSold(),true);
			createCell(workbook, fstRow, 14, r01c04Style).setValue(detail.getStatusFgName());
			createCell(workbook, fstRow, 15, r01c15Style).setValue(detail.getRemark());
			
			rowNumber++;
		}
		// <!-- Setup 'Print Area'. -->
		workbook.setPrintArea(0, 0, 15, 0, rowNumber-1);
	}
}