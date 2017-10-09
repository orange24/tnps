package th.co.nttdata.tki.excel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


import th.co.nttdata.tki.bean.MWip;


public class PRC_S01ExcelView extends AbstractExcelView {

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {
		
		MWip  mwip = (MWip) model.get("mwipDetail");
		final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
		// <!-- Assign: CellStyle. -->
		Style r01c00Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setLeftBorder().setRightBorder().setWrapText();
		Style r01c01Style = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText();
		
		HSSFSheet sheet = workbook.getSheetAt(0);
		
		int rowNumber = 3;
		
		
		List<MWip> mList = mwip.getWipList();
		MWip[] details = mList.toArray( new MWip[mList.size()] );
		for( int i = 0, max = mList.size(); i < max; i++ ) {
			MWip   mWipDeatil = details[i];
		
			HSSFRow fstRow = sheet.createRow(rowNumber);
			createCell(workbook, fstRow, 0, r01c00Style).setValue(mWipDeatil.getWip());
			createCell(workbook, fstRow, 1, r01c01Style).setValue(mWipDeatil.getWipName());
			createCell(workbook, fstRow, 2, r01c01Style).setValue(new Boolean(mWipDeatil.getIsCalc()).toString());
			createCell(workbook, fstRow, 3, r01c01Style).setValue(mWipDeatil.getWipTypeName());
			createCell(workbook, fstRow, 4, r01c01Style).setValue(mWipDeatil.getRemark());
			createCell(workbook, fstRow, 5, r01c01Style).setValue(mWipDeatil.getCreateDate() == null ? "" : dateFormatter.format(mWipDeatil.getCreateDate()));
			createCell(workbook, fstRow, 6, r01c01Style).setValue(mWipDeatil.getCreateBy());
			createCell(workbook, fstRow, 7, r01c01Style).setValue(mWipDeatil.getLastUpdate() == null ? "" : dateFormatter.format(mWipDeatil.getLastUpdate()));
			createCell(workbook, fstRow, 8, r01c01Style).setValue(mWipDeatil.getUpdateBy());
			
			rowNumber++;
		}
		workbook.setPrintArea(0, 0, 15, 0, rowNumber-1);
	}

}
