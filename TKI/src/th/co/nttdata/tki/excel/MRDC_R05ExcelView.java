package th.co.nttdata.tki.excel;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;

import th.co.nttdata.tki.bean.MMold;
import th.co.nttdata.tki.bean.VProductProcesMaster;

public class MRDC_R05ExcelView extends AbstractExcelView{

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook)
			throws Exception {

		VProductProcesMaster		vProcess = (VProductProcesMaster)model.get("vPro");
		List<VProductProcesMaster> 	vProList = vProcess.getvList();
	
		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short)11);
		HSSFFont fontHD = workbook.createFont();
		fontHD.setFontName("Calibri");
		fontHD.setFontHeightInPoints((short)11);
		fontHD.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		Style txtCenStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style txtLeftStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP).setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style numberStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0").setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		Style doubleStyle 	= createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP).setFormat("#,##0.00").setBottomBorder().setLeftBorder().setRightBorder().setWrapText().setFont(font);
		
		HSSFSheet sheet = workbook.getSheetAt(0);

		int rowNum   = 3;
		int initMold = 0;
		int merkPart = 0;
		int merkWip  = 0;
		int merkMold = 0;
		int merkMach = 0;
		int maxRow   = 0;
		VProductProcesMaster vPro		= null;
		VProductProcesMaster vWip		= null;
		VProductProcesMaster vWipPrev	= null;
		if(vProList.size() > 0){
			for(int i=0;i<vProList.size();i++){
				vPro = vProList.get(i);
				initMold = rowNum;
				merkPart = rowNum;
				merkWip  = rowNum;
				vWipPrev	= null;
				// find max row of each part
				if(vPro.getMoldList().size() > vPro.getvList().size()){
					maxRow = vPro.getMoldList().size();
				}else{
					maxRow = vPro.getvList().size();
				}
				if(vPro.getvList().size() > 0){
					merkMold = rowNum;
					for(int j=0;j<vPro.getvList().size();j++){
						vWip = vPro.getvList().get(j);
						if(j > 0){
							vWipPrev = vPro.getvList().get(j-1);
						}
						HSSFRow dtRow = sheet.createRow(rowNum);
						//part
						createCell(workbook, dtRow, 0, txtCenStyle).setValue(vPro.getdUpdatedDate());
						createCell(workbook, dtRow, 1, txtCenStyle).setValue(vPro.getdUpdatedTime());
						createCell(workbook, dtRow, 2, txtLeftStyle).setValue(vPro.getsPartNo());
						createCell(workbook, dtRow, 3, txtLeftStyle).setValue(vPro.getsPartName());
						createCell(workbook, dtRow, 4, doubleStyle).setValue(vPro.getnMaterialCost());
						createCell(workbook, dtRow, 5, numberStyle).setValue(vPro.getSnp());
						//mold
						if(vPro.getMoldList().size() == 0){
							createCell(workbook, dtRow, 6, txtLeftStyle);
							createCell(workbook, dtRow, 7, txtLeftStyle);
							createCell(workbook, dtRow, 8, numberStyle);
						}
						//wip
						createCell(workbook, dtRow, 9, txtCenStyle).setValue(vWip.getnWIPSeq());
						createCell(workbook, dtRow, 10, txtLeftStyle).setValue(vWip.getsWIP());
						createCell(workbook, dtRow, 11, txtLeftStyle).setValue(vWip.getsProcessName());
						createCell(workbook, dtRow, 12, txtLeftStyle).setValue(vWip.getsLineMachineCD());
						createCell(workbook, dtRow, 13, txtLeftStyle).setValue(vWip.getsMachine());
						
						if((vWipPrev != null)&&(!vWip.getKeyWip().equals(vWipPrev.getKeyWip()))){
							createMergedRegion(sheet, merkWip, rowNum-1, 9, 9);
							createMergedRegion(sheet, merkWip, rowNum-1, 10, 10);
							createMergedRegion(sheet, merkWip, rowNum-1, 11, 11);
							merkWip = rowNum;
						}
						if((j != 0)&&(j == (vPro.getvList().size()-1))){
							createMergedRegion(sheet, merkWip, rowNum, 9, 9);
							createMergedRegion(sheet, merkWip, rowNum, 10, 10);
							createMergedRegion(sheet, merkWip, rowNum, 11, 11);							
						}
						rowNum++;
					}
					if(vPro.getMoldList().size() == 0){
						createMergedRegion(sheet, merkMold, rowNum-1, 6, 6);
						createMergedRegion(sheet, merkMold, rowNum-1, 7, 7);
						createMergedRegion(sheet, merkMold, rowNum-1, 8, 8);
					}
					//mold > machine
					if(maxRow > vPro.getvList().size()){
						merkMach = rowNum;
						for(int c=0;c<(maxRow-vPro.getvList().size());c++) {
							HSSFRow dtRow = sheet.createRow(rowNum);
							createCell(workbook, dtRow, 0, txtCenStyle);
							createCell(workbook, dtRow, 1, txtCenStyle);
							createCell(workbook, dtRow, 2, txtLeftStyle);
							createCell(workbook, dtRow, 3, txtLeftStyle);
							createCell(workbook, dtRow, 4, doubleStyle);
							createCell(workbook, dtRow, 5, numberStyle);
							//wip
							createCell(workbook, dtRow, 9, txtCenStyle);
							createCell(workbook, dtRow, 10, txtLeftStyle);
							createCell(workbook, dtRow, 11, txtLeftStyle);
							createCell(workbook, dtRow, 12, txtLeftStyle);
							createCell(workbook, dtRow, 13, txtLeftStyle);
							rowNum++;
						}
						createMergedRegion(sheet, merkMach, rowNum-1, 12, 12);
						createMergedRegion(sheet, merkMach, rowNum-1, 13, 13);
					}
				}else{
					rowNum++;
				}
				if(vPro.getMoldList().size() > 0){
				//mold
					for(MMold mold : vPro.getMoldList()){
						HSSFRow dtRow = sheet.getRow(initMold);						
						createCell(workbook, dtRow, 6, txtLeftStyle).setValue(mold.getMoldName());
						createCell(workbook, dtRow, 7, txtLeftStyle).setValue(mold.getMoldNo());
						createCell(workbook, dtRow, 8, numberStyle).setValue(mold.getCav());
						initMold++;
					}
					//machine > mold
					if(maxRow > vPro.getMoldList().size()){
						merkMold = initMold;
						while(rowNum > initMold) {
							HSSFRow dtRow = sheet.getRow(initMold);	
							createCell(workbook, dtRow, 6, txtLeftStyle);
							createCell(workbook, dtRow, 7, txtLeftStyle);
							createCell(workbook, dtRow, 8, numberStyle);
							initMold++;
						}
						createMergedRegion(sheet, merkMold, initMold-1, 6, 6);
						createMergedRegion(sheet, merkMold, initMold-1, 7, 7);
						createMergedRegion(sheet, merkMold, initMold-1, 8, 8);
					}
				}
				createMergedRegion(sheet, merkPart, rowNum-1, 0, 0);
				createMergedRegion(sheet, merkPart, rowNum-1, 1, 1);
				createMergedRegion(sheet, merkPart, rowNum-1, 2, 2);
				createMergedRegion(sheet, merkPart, rowNum-1, 3, 3);
				createMergedRegion(sheet, merkPart, rowNum-1, 4, 4);
				createMergedRegion(sheet, merkPart, rowNum-1, 5, 5);
			}
		}
		// <!-- Setup 'Print Area'. -->
		workbook.setPrintArea(0, 0, 13, 0, rowNum);
	}

}
