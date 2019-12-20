package th.co.nttdata.tki.excel;

import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.TWipCheckStock;
import th.co.nttdata.tki.bean.TWipFgMaping;

public class WIP_R06ExcelView extends AbstractExcelView {

	@Override
	protected void build(Map<String, Object> model, HSSFWorkbook workbook) throws Exception {

		// Data
		TWipCheckStock TWipCheckStock = (TWipCheckStock) model.get("tWipFgStockList");
		MCustomer mCust = (MCustomer)model.get("customer");
		MPart mPart = (MPart)model.get("part");

		NumberFormat formatter = new DecimalFormat("#,##0");
		HSSFFont font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) 11);

		Style fstHDRStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFont(
				FONT_HEADR)
				.setTopBorder(CellStyle.BORDER_MEDIUM)
				.setRightBorder()
				.setBgColor()
				.setWrapText();
		Style sndHDRStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFont(
				FONT_HEADR)
				.setTopBorder()
				.setBottomBorder(CellStyle.BORDER_MEDIUM)
				.setRightBorder()
				.setBgColor()
				.setWrapText();

		Style detailStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setFont(font);

		Style txtLeftStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder()
				.setRightBorder()
				.setLeftBorder()
				.setFont(font)
				.setWrapText();
		Style txtLeftBoldStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder()
				.setRightBorder()
				.setLeftBorder()
				.setFont(FONT_HEADR)
				.setWrapText();        
		Style txtLeftMediumBorderStyle = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder(
				CellStyle.BORDER_MEDIUM)
				.setLeftBorder()
				.setRightBorder()
				.setFont(FONT_HEADR)
				.setWrapText();                
                Style txtRightStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder()
				.setRightBorder()
				.setLeftBorder()
				.setFont(font)
				.setWrapText();
		Style txtRightMediumBorderStyle = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder(
				CellStyle.BORDER_MEDIUM)
				.setLeftBorder()
				.setRightBorder()
				.setFont(font)
				.setWrapText();                      
		Style txtCenterStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder()
				.setRightBorder()
				.setLeftBorder()
				.setFont(font)
				.setWrapText(); 
		Style txtCenterMediumBorderStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setBottomBorder(
				CellStyle.BORDER_MEDIUM)
				.setLeftBorder()
				.setRightBorder()
				.setFont(font)
				.setWrapText();                       
		
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow row = sheet.createRow(2);
		
		//Top Header Detail
		String customer = "All Customer";
		String partNo = "All Part";
		String partName = "All Part";
		if(null != mCust){
			customer = mCust.getCustomerName();
		}
		if(null != mPart){
			partNo = mPart.getPartNo();
			partName = mPart.getPartName();
		}
		createCell(workbook, row, 0, detailStyle).setValue("Customer : " + customer);
		createCell(workbook, row, 3, detailStyle).setValue("Part No : " + partNo);
		createCell(workbook, row, 5, detailStyle).setValue("Part Name : " + partName);
		
		//Rows Header
		HSSFRow fstHeader = sheet.getRow(4);
		HSSFRow sndHeader = sheet.getRow(5);
                
		int colNumber = 4;
                
                Map<Date, List<TWipFgMaping>> fgMapKey = TWipCheckStock.getFgMapKey();
                
                for (List<TWipFgMaping> wipFgStockKey : fgMapKey.values()) {
                    String newstring = new SimpleDateFormat("dd-MM-yyyy").format(wipFgStockKey.get(0).getReportDate());
                    
                    createCell(workbook, fstHeader, colNumber, fstHDRStyle);
                    createCell(workbook, fstHeader, colNumber+1, fstHDRStyle);
                    createCell(workbook, fstHeader, colNumber+2, fstHDRStyle);
                    sheet.getRow(4).getCell(colNumber).setCellValue(newstring);
                    createMergedRegion(sheet, 4, 4, colNumber, colNumber+2);
                    
                    createCell(workbook, sndHeader, colNumber, sndHDRStyle);
                    createCell(workbook, sheet.getRow(5), colNumber, sndHDRStyle).setValue("Stock");
                    createCell(workbook, sheet.getRow(5), colNumber+1, sndHDRStyle).setValue("Pending");
                    createCell(workbook, sheet.getRow(5), colNumber+2, sndHDRStyle).setValue("Total");
                    colNumber += 3;
                }
                
                createMergedRegion(sheet, 0, 0, 0, colNumber-1);
                
                Map<String, List<TWipFgMaping>> wipFgStockMap = TWipCheckStock.getFgMap();
		String customerCode, partCode, partDesc, wip = "";
                
                int rowNumber = 6;
                int count = 1;
                int FirstRow = 0;
                
                for (List<TWipFgMaping> wipFgStockList : wipFgStockMap.values()) {
                    int rowNumBefore = rowNumber;
                    HSSFRow rows = sheet.createRow(rowNumber); 
                    HSSFRow rows1 = sheet.createRow(rowNumber+1); 
                    HSSFRow rows2 = sheet.createRow(rowNumber+2); 
                    HSSFRow rows3 = sheet.createRow(rowNumber+3); 
                    HSSFRow wipTotalRow = sheet.createRow(rowNumber+1);  
                    HSSFRow fgStockRow = sheet.createRow(rowNumber+2);  
                    HSSFRow grandTotalRow = sheet.createRow(rowNumber+3);
                    
                    if (wipFgStockList.get(0).getWiporder() == 1) {  
                        FirstRow = rowNumber;
                        count = 1;
                        if (count == wipFgStockList.get(0).getCountProcess()) {                             
                            rowNumber = rowNumber + 3;
                            
                            createCell(workbook, wipTotalRow, 3, txtLeftBoldStyle).setValue("WIP Total");
                            createCell(workbook, fgStockRow, 3, txtLeftBoldStyle).setValue("FG Stock");
                            createCell(workbook, grandTotalRow, 3, txtLeftMediumBorderStyle).setValue("Grand Total"); 
                            
                            createMergedRegion(sheet, FirstRow, rowNumber, 0, 0);
                            createMergedRegion(sheet, FirstRow, rowNumber, 1, 1);
                            createMergedRegion(sheet, FirstRow, rowNumber, 2, 2);             
                            
                            colNumber = 4;
                            
                            for (List<TWipFgMaping> wipFgStockKey : fgMapKey.values()) {

                                Map<String, List<TWipFgMaping>> keyMap = TWipCheckStock.getFgMapDay();

                                String keyData = wipFgStockList.get(0).getCustomerid() + "" + wipFgStockList.get(0).getPartid()
                                            + wipFgStockList.get(0).getWip() + wipFgStockList.get(0).getWiporder()
                                            + wipFgStockKey.get(0).getReportDate(); 

                                Iterator<TWipFgMaping> iterator = keyMap.get(keyData).iterator();

                                while (iterator.hasNext()) {
                                    TWipFgMaping wipFG = iterator.next();
                                    //WIP TOTAL
                                    createCell(workbook, wipTotalRow, colNumber, txtRightStyle).setValue(
                                            (wipFG.getSumWip() == null) ? "0": String.valueOf(formatter.format(wipFG.getSumWip())));
                                    createCell(workbook, wipTotalRow, colNumber+1, txtRightStyle).setValue(
                                            (wipFG.getSumPending() == null) ? "0": String.valueOf(formatter.format(wipFG.getSumPending())));  
                                    createCell(workbook, wipTotalRow, colNumber+2, txtRightStyle).setValue(
                                            (wipFG.getSumTotal() == null) ? "0": String.valueOf(formatter.format(wipFG.getSumTotal())));
                                    //FG TOTAL
                                    createCell(workbook, fgStockRow, colNumber, txtRightStyle).setValue((wipFG.getFgBl() == null) ? "0": String.valueOf(formatter.format(wipFG.getFgBl())));
                                    createCell(workbook, fgStockRow, colNumber+1, txtRightStyle).setValue("");  
                                    createCell(workbook, fgStockRow, colNumber+2, txtRightStyle).setValue("");
                                    createMergedRegion(sheet, rowNumber-1, rowNumber-1, colNumber, colNumber+2);
                                    //GRAND TOTAL
                                    createCell(workbook, grandTotalRow, colNumber, txtRightMediumBorderStyle).setValue((wipFG.getGrandTotal() == null) ? "0": String.valueOf(formatter.format(wipFG.getGrandTotal())));
                                    createCell(workbook, grandTotalRow, colNumber+1, txtRightMediumBorderStyle).setValue("");  
                                    createCell(workbook, grandTotalRow, colNumber+2, txtRightMediumBorderStyle).setValue("");
                                    createMergedRegion(sheet, rowNumber, rowNumber, colNumber, colNumber+2);
                                }

                               colNumber += 3;
                           }
                        }
                    } else {
                        if (count == wipFgStockList.get(0).getCountProcess()) {                             
                            rowNumber = rowNumber + 3;
                            
                            createCell(workbook, wipTotalRow, 3, txtLeftBoldStyle).setValue("WIP Total");
                            createCell(workbook, fgStockRow, 3, txtLeftBoldStyle).setValue("FG Stock");
                            createCell(workbook, grandTotalRow, 3, txtLeftMediumBorderStyle).setValue("Grand Total"); 
                            
                            createMergedRegion(sheet, FirstRow, rowNumber, 0, 0);
                            createMergedRegion(sheet, FirstRow, rowNumber, 1, 1);
                            createMergedRegion(sheet, FirstRow, rowNumber, 2, 2);             
                            
                            colNumber = 4;
                            
                            for (List<TWipFgMaping> wipFgStockKey : fgMapKey.values()) {

                                Map<String, List<TWipFgMaping>> keyMap = TWipCheckStock.getFgMapDay();

                                String keyData = wipFgStockList.get(0).getCustomerid() + "" + wipFgStockList.get(0).getPartid()
                                            + wipFgStockList.get(0).getWip() + wipFgStockList.get(0).getWiporder()
                                            + wipFgStockKey.get(0).getReportDate(); 

                                Iterator<TWipFgMaping> iterator = keyMap.get(keyData).iterator();

                                while (iterator.hasNext()) {
                                    TWipFgMaping wipFG = iterator.next();
                                    //WIP TOTAL
                                    createCell(workbook, wipTotalRow, colNumber, txtRightStyle).setValue(
                                            (wipFG.getSumWip() == null) ? "0": String.valueOf(formatter.format(wipFG.getSumWip())));
                                    createCell(workbook, wipTotalRow, colNumber+1, txtRightStyle).setValue(
                                            (wipFG.getSumPending() == null) ? "0": String.valueOf(formatter.format(wipFG.getSumPending())));  
                                    createCell(workbook, wipTotalRow, colNumber+2, txtRightStyle).setValue(
                                            (wipFG.getSumTotal() == null) ? "0": String.valueOf(formatter.format(wipFG.getSumTotal())));
                                    //FG TOTAL
                                    createCell(workbook, fgStockRow, colNumber, txtRightStyle).setValue((wipFG.getFgBl() == null) ? "0": String.valueOf(formatter.format(wipFG.getFgBl())));
                                    createCell(workbook, fgStockRow, colNumber+1, txtRightStyle).setValue("");  
                                    createCell(workbook, fgStockRow, colNumber+2, txtRightStyle).setValue("");
                                    createMergedRegion(sheet, rowNumber-1, rowNumber-1, colNumber, colNumber+2);
                                    //GRAND TOTAL
                                    createCell(workbook, grandTotalRow, colNumber, txtRightMediumBorderStyle).setValue((wipFG.getGrandTotal() == null) ? "0": String.valueOf(formatter.format(wipFG.getGrandTotal())));
                                    createCell(workbook, grandTotalRow, colNumber+1, txtRightMediumBorderStyle).setValue("");  
                                    createCell(workbook, grandTotalRow, colNumber+2, txtRightMediumBorderStyle).setValue("");
                                    createMergedRegion(sheet, rowNumber, rowNumber, colNumber, colNumber+2);
                                }

                               colNumber += 3;
                           }
                        }
                    }
                    
                    customerCode = wipFgStockList.get(0).getCustomer();
                    partCode = wipFgStockList.get(0).getPartno();
                    partDesc = wipFgStockList.get(0).getPartname();
                    wip = wipFgStockList.get(0).getWiporder()+ ". " + wipFgStockList.get(0).getWip(); 
                    

                    createCell(workbook, rows, 0, txtCenterStyle).setValue(customerCode);
                    createCell(workbook, rows1, 0, txtCenterStyle).setValue(customerCode);
                    createCell(workbook, rows2, 0, txtCenterStyle).setValue(customerCode);
                    createCell(workbook, rows3, 0, txtCenterMediumBorderStyle).setValue(customerCode);
                    
                    createCell(workbook, rows, 1, txtCenterStyle).setValue(partCode);
                    createCell(workbook, rows1, 1, txtCenterStyle).setValue(partCode);
                    createCell(workbook, rows2, 1, txtCenterStyle).setValue(partCode);
                    createCell(workbook, rows3, 1, txtCenterMediumBorderStyle).setValue(partCode);
                    
                    createCell(workbook, rows, 2, txtCenterStyle).setValue(partDesc);
                    createCell(workbook, rows1, 2, txtCenterStyle).setValue(partDesc);
                    createCell(workbook, rows2, 2, txtCenterStyle).setValue(partDesc);
                    createCell(workbook, rows3, 2, txtCenterMediumBorderStyle).setValue(partDesc);
                    
                    createCell(workbook, rows, 3, txtLeftStyle).setValue(wip);

                    colNumber = 4;


                    for (List<TWipFgMaping> wipFgStockKey : fgMapKey.values()) {

                         Map<String, List<TWipFgMaping>> keyMap = TWipCheckStock.getFgMapDay();

                         String keyData = wipFgStockList.get(0).getCustomerid() + "" + wipFgStockList.get(0).getPartid()
                                     + wipFgStockList.get(0).getWip() + wipFgStockList.get(0).getWiporder()
                                     + wipFgStockKey.get(0).getReportDate(); 

                         Iterator<TWipFgMaping> iterator = keyMap.get(keyData).iterator();

                         while (iterator.hasNext()) {
                             TWipFgMaping wipFG = iterator.next();
                             
                             //DETAIL VALUES
                             createCell(workbook, sheet.getRow(rowNumBefore), colNumber, txtRightStyle).setValue(
                                     (wipFG.getStockAfterQty() == null) ? "0": String.valueOf(formatter.format(wipFG.getStockAfterQty())));
                             createCell(workbook, sheet.getRow(rowNumBefore), colNumber+1, txtRightStyle).setValue(
                                     (wipFG.getPreWipPdQty() == null) ? "0": String.valueOf(formatter.format(wipFG.getPreWipPdQty())));  
                             createCell(workbook, sheet.getRow(rowNumBefore), colNumber+2, txtRightStyle).setValue(
                                     (wipFG.getTotals() == null) ? "0": String.valueOf(formatter.format(wipFG.getTotals())));
                         } 
                        
                        colNumber += 3;
                    }

                 rowNumber++; 
                 count++;
                }                
	}
}
