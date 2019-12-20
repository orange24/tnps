package th.co.nttdata.tki.excel;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

public abstract class AbstractExcelView
extends org.springframework.web.servlet.view.document.AbstractExcelView {

	private static final SimpleDateFormat fileFormatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
	private static final SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss", Locale.US);
	protected HSSFFont FONT_TITLE;
	protected HSSFFont FONT_HEADR;
	protected HSSFFont FONT_CONTT;

	protected String nullToEmpty( String obj ) {
		return obj == null ? "" : obj.toString().trim();
	}

	protected int nullToZero( Integer obj ) {
		return obj == null ? 0 : obj;
	}

	protected String intToString( Integer obj ) {
		return obj == null ? "0" : String.valueOf(obj);
	}

	protected String summaryInt( Integer[] obj ) {
		if( obj == null )
			return "0";

		// <!-- SUMMARY: -->
		int total = 0;
		for( Integer i : obj ) {
			total += (i == null ? 0 : i);
		}
		return String.valueOf(total);
	}

	protected String dateFormat( Date date ) {
		return dateFormatter.format(date);
	}

	protected String timeFormat( Date date ) {
		return timeFormatter.format(date);
	}

	protected MergedCell createMergedRegion( HSSFSheet sheet, int firstRow, int lastRow, int firstCol, int lastCol ) {

		sheet.addMergedRegion(new CellRangeAddress(
			firstRow, //first row (0-based)
			lastRow, //last row  (0-based)
			firstCol, //first column (0-based)
			lastCol  //last column  (0-based)
		));

		return new MergedCell(sheet, firstRow, lastRow, firstCol, lastCol);
	}
	protected Cell createCell( HSSFWorkbook wb, HSSFRow row, int cellIndex, Style style ) {

		Cell cell = new Cell( row.createCell(cellIndex) );
		cell.setStyle(style);

		return cell;
	}
	protected Style createStyle( HSSFWorkbook wb, short halign, short valign ) {

		CellStyle style = wb.createCellStyle();
		style.setAlignment(halign);
		style.setVerticalAlignment(valign);

		return new Style(wb, style);
	}

	protected class MergedCell {
		private int firstRow;
		private int lastRow;
		private int firstCol;
		private int lastCol;
		private HSSFSheet sheet;

		private MergedCell( HSSFSheet sheet, int firstRow, int lastRow, int firstCol, int lastCol ) {

			this.firstRow = firstRow;
			this.lastRow = lastRow;
			this.firstCol = firstCol;
			this.lastCol = lastCol;
			this.sheet = sheet;
		}
		public void cleanUp() {

			boolean isDeleted = false;
			for( int i = firstRow, maxI = lastRow + 1; i < maxI; i++ ) {
				for( int j = firstCol, maxJ = lastCol + 1; j < maxJ; j++ ) {
					if( !isDeleted ) {
						isDeleted = true;
						continue;
					} else {
						sheet.getRow(i).getCell(j).setCellValue("");
					}
				}
			}
		}
	}
	protected class Cell {
		private HSSFCell cell;
		private Style style;

		private Cell( HSSFCell cell ) {
			this.cell = cell;
		}

		public Cell setValueDate( Date date ) {
			if( date != null ) {
				setValue( dateFormat(date) );
			}
			return this;
		}
		public Cell setValueTime( Date date ) {
			if( date != null ) {
				setValue( timeFormat(date) );
			}
			return this;
		}
		public Cell setValueDateTime( Date date ) {
			if( date != null ) {
				setValue( dateFormat(date) +" "+ timeFormat(date) );
			}
			return this;
		}
		public Cell setValue( Double[] value ) {
			if( value != null ) {
				double val = 0.0;

				for( Double d : value )
					val += (d == null ? 0.0 : d);
				setValue(val);
			}

			return this;
		}
		public Cell setValue( Integer[] value ) {
			if( value != null ) {
				int val = 0;

				for( Integer d : value )
					val += (d == null ? 0 : d);
				setValue(val);
			}

			return this;
		}
		public Cell setValue( Integer value ) {
			if( value != null ) {
				setValue(Double.parseDouble(String.valueOf(value)));
			}
			return this;
		}
		public Cell setValue( Double value ) {
			setValue( value, false );
			return this;
		}
		public Cell setValue( Number value, boolean showZero ) {
			if( value != null && (showZero || value.doubleValue() != 0.0) ) {
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellValue(value.doubleValue());
			} else {
				setValue("");
			}
			return this;
		}
		public Cell setValue( String value ) {
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(value);
			return this;
		}
		public Cell setValue( Date value ) {
			cell.setCellValue(value);
			return this;
		}
		public Style getStyle() {
			return style;
		}
		public void setStyle( Style style ) {
			cell.setCellStyle(style.getCellStyle());
			this.style = style;
		}
	}
	protected class Style {
		private HSSFWorkbook workbook;
		private CellStyle style;

		private Style( HSSFWorkbook wb, CellStyle style ) {
			this.workbook = wb;
			this.style    = style;
		}

		public Style setFormat( String format ) {
			style.setDataFormat(workbook.createDataFormat().getFormat(format));
			return this;
		}

		public Style setFont( HSSFFont font ) {
			style.setFont(font);
			return this;
		}

		public Style setWrapText() {
			style.setWrapText(true);
			return this;
		}

		public Style setLeftBorder() {
			setLeftBorder(CellStyle.BORDER_THIN);
			return this;
		}
		public Style setTopBorder() {
			setTopBorder(CellStyle.BORDER_THIN);
			return this;
		}
		public Style setRightBorder() {
			setRightBorder(CellStyle.BORDER_THIN);
			return this;
		}
		public Style setBottomBorder() {
			setBottomBorder(CellStyle.BORDER_THIN);
			return this;
		}

		public Style setLeftBorder( short cellStyle ) {
			style.setBorderLeft(cellStyle);
			style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			return this;
		}
		public Style setTopBorder( short cellStyle ) {
			style.setBorderTop(cellStyle);
			style.setTopBorderColor(IndexedColors.BLACK.getIndex());
			return this;
		}
		public Style setRightBorder( short cellStyle ) {
			style.setBorderRight(cellStyle);
			style.setRightBorderColor(IndexedColors.BLACK.getIndex());
			return this;
		}
		public Style setBottomBorder( short cellStyle ) {
			style.setBorderBottom(cellStyle);
			style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			return this;
		}

		public Style setBgColor() {
			style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			return this;
		}
		
		public Style setWhiteBgColor() {
			style.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
		    style.setBorderBottom(CellStyle.BORDER_THIN);
		    style.setBottomBorderColor(IndexedColors.WHITE.getIndex());
			return this;
		}

		public CellStyle getCellStyle() {
			return style;
		}
		public void setCellStyle( CellStyle style ) {
			this.style = style;
		}
	}

	@Override
	protected void buildExcelDocument(
		 Map<String, Object> model
		,HSSFWorkbook workbook
		,HttpServletRequest request
		,HttpServletResponse response
	) throws Exception {

		try {

			// <!-- Assign: -->
			(FONT_TITLE = workbook.createFont()).setFontHeightInPoints( (short) 24 );
			(FONT_HEADR = workbook.createFont()).setBoldweight( Font.BOLDWEIGHT_BOLD );
			 FONT_CONTT = workbook.createFont();

			build( model, workbook );

			// <!-- Specify: XLS Filename. -->
//			response.setHeader("Content-Type","application/vnd.ms-excel");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename="+ getClass().getSimpleName().replaceFirst("ExcelView", "") +"_"+ fileFormatter.format(new GregorianCalendar(Locale.US).getTime()) +".xls");
			response.addCookie( new Cookie("downloadToken", String.valueOf(System.currentTimeMillis())) );
			response.flushBuffer();
		} catch( Exception e ) {

			e.printStackTrace();
/*
			String redirectPage =
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">" +
				"<html>" +
				"<head>" +
				"<script type=\"text/javascript\">" +
				"window.history.forward(1);" +
				"parent.location = \""+ request.getContextPath() +"/page/ERR/showInfo.jsp\";" +
				"</script>" +
				"</head>" +
				"</html>";

			response.setContentType("text/html; charset=UTF-8");

			PrintWriter out = new PrintWriter(response.getOutputStream());
			out.print(redirectPage);
			out.close();
*/
			throw e;
		}
	}

	protected abstract void build( Map<String, Object> model, HSSFWorkbook workbook ) throws Exception;
}