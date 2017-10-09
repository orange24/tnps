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
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import th.co.nttdata.tki.bean.TPending;
import th.co.nttdata.tki.bean.TPendingAdjust;

public class MRDC_R08ExcelView extends AbstractExcelView{

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {
		final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		TPending tPending = (TPending) model.get("tPending");
		List<TPendingAdjust> pendingList = (List<TPendingAdjust>)tPending.getAdjustList();

		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short)11);
		HSSFFont fontHd = workbook.createFont();
		fontHd.setFontName("Calibri");
		fontHd.setFontHeightInPoints((short)11);
		fontHd.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		Style txtHeadStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setWrapText().setFont(fontHd);
		Style fstLeftStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style txtLeftTOPStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style txtLeftCENStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder().setRightBorder().setWrapText().setFont(font);
		Style numberStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style totalStyle	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setFont(fontHd).setWrapText().setBottomBorder(CellStyle.BORDER_DOUBLE);

		HSSFSheet sheet = workbook.getSheetAt(0);
		
		HSSFRow criteria = sheet.getRow(2);
		if(tPending.getReportDateFr() != null){
			criteria.getCell(0).setCellValue("Operation Date (From) : " + dateFormatter.format(tPending.getReportDateFr()));
		}
		if(tPending.getReportDateTo() != null){
			criteria.getCell(2).setCellValue("Operation Date (To) : " + dateFormatter.format(tPending.getReportDateTo()));
		}
		
		int rowNum = 4;
		int sumQty = 0;
		int sumOk = 0;
		int sumNg = 0;
		int sumDiff = 0;
		int rowPrev = 4;
		boolean isMerged = false;
		HSSFRow dtRow = null;
		String keyPrev = "";
		String keyCurr = "";
		if(pendingList.size() > 0){
			TPendingAdjust[] details = pendingList.toArray( new TPendingAdjust[pendingList.size()] );
			for(int p = 0; p < pendingList.size(); p++) {
				TPendingAdjust tPend= details[p];
				dtRow = sheet.createRow(rowNum++);
				if(p == 0){
					keyPrev = dateFormatter.format(tPend.getOperationDate())+":"+tPend.getPartId()+":"+tPend.getWip();
				}
				keyCurr = dateFormatter.format(tPend.getOperationDate())+":"+tPend.getPartId()+":"+tPend.getWip(); 
				
				createCell(workbook, dtRow, 0, fstLeftStyle).setValue(dateFormatter.format(tPend.getOperationDate()));
				createCell(workbook, dtRow, 1, txtLeftTOPStyle).setValue(tPend.getPartNo());
				createCell(workbook, dtRow, 2, txtLeftTOPStyle).setValue(tPend.getPartName());
				createCell(workbook, dtRow, 3, txtLeftTOPStyle).setValue(tPend.getWipName());
				createCell(workbook, dtRow, 4, txtLeftCENStyle).setValue(tPend.getWorkorderNo());
				createCell(workbook, dtRow, 5, numberStyle).setValue(tPend.getPdQty());
				createCell(workbook, dtRow, 6, numberStyle).setValue(tPend.getOk());
				createCell(workbook, dtRow, 7, numberStyle).setValue(tPend.getNg());
				createCell(workbook, dtRow, 8, numberStyle).setValue(tPend.getnDiffQty(),true);
				sumQty += tPend.getPdQty();
				sumOk += tPend.getOk();
				sumNg += tPend.getNg();
				sumDiff += tPend.getnDiffQty();
				isMerged = false;
				
				// merge cell
				if(!keyCurr.equals(keyPrev)){// isSingleRow no merge
					if(rowPrev != dtRow.getRowNum()-1){
						createMergedRegion(sheet, rowPrev, dtRow.getRowNum()-1, 0, 0);
						createMergedRegion(sheet, rowPrev, dtRow.getRowNum()-1, 1, 1);
						createMergedRegion(sheet, rowPrev, dtRow.getRowNum()-1, 2, 2);
						createMergedRegion(sheet, rowPrev, dtRow.getRowNum()-1, 3, 3);
					}
					isMerged = true;
					rowPrev = dtRow.getRowNum();;
					keyPrev = dateFormatter.format(tPend.getOperationDate())+":"+tPend.getPartId()+":"+tPend.getWip();
				}
			}
			
			if(!isMerged){
				createMergedRegion(sheet, rowPrev, dtRow.getRowNum(), 0, 0);
				createMergedRegion(sheet, rowPrev, dtRow.getRowNum(), 1, 1);
				createMergedRegion(sheet, rowPrev, dtRow.getRowNum(), 2, 2);
				createMergedRegion(sheet, rowPrev, dtRow.getRowNum(), 3, 3);
			}
			
			// grand total
			dtRow = sheet.createRow(rowNum);
			createCell(workbook, dtRow, 0, txtHeadStyle);
			createCell(workbook, dtRow, 1, txtHeadStyle);
			createCell(workbook, dtRow, 2, txtHeadStyle);
			createCell(workbook, dtRow, 3, txtHeadStyle);
			createCell(workbook, dtRow, 4, txtHeadStyle);
			createMergedRegion(sheet, rowNum, rowNum, 0, 4);
			sheet.getRow(rowNum).getCell(0).setCellValue("Grand Total");
			createCell(workbook, dtRow, 5, totalStyle).setValue(sumQty,true);
			createCell(workbook, dtRow, 6, totalStyle).setValue(sumOk,true);
			createCell(workbook, dtRow, 7, totalStyle).setValue(sumNg,true);
			createCell(workbook, dtRow, 8, totalStyle).setValue(sumDiff,true);
		}
		// <!-- Setup 'Print Area'. -->
		workbook.setPrintArea(0, 0, 8, 0, rowNum);
	}
}
