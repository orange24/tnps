package th.co.nttdata.tki.excel;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;

import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.TDailyWK;
import th.co.nttdata.tki.bean.TDailyWKDetail;
import th.co.nttdata.tki.bean.TDailyWKLossTime;
import th.co.nttdata.tki.bean.TDailyWKNGReason;

public class DAL_R03ExcelView extends AbstractExcelView {

	// Column index constants
	// Col 0-5  : Date, Customer, WIP, PartNo, PartName, Shift
	// Col 6-12 : OK, NG, PD, Qty, TimeUsed, ManPower, LossTime(total)
	// Col 13   : Loss Time Reason (1 reason per sub-row)
	// Col 14   : Individual Reason Loss Time (NEW)
	// Col 15   : Staff/Worker
	// Col 16-19: Grand Total Actual Qty (OK, NG, PD, Total) — same values as cols 6-9
	// Col 20+  : NG Reasons (dynamic)
	private static final int COL_LOSS_REASON    = 13;
	private static final int COL_LOSS_TIME_EACH = 14;
	private static final int COL_STAFF          = 15;
	private static final int COL_GT_OK          = 16;
	private static final int COL_GT_NG          = 17;
	private static final int COL_GT_PD          = 18;
	private static final int COL_GT_TOTAL       = 19;
	private static final int COL_NG_REASON_START = 20;

	@Override
	protected void build( Map<String, Object> model, HSSFWorkbook workbook ) throws Exception {

		TDailyWK dailyWK = (TDailyWK) model.get("dailyWK");
		List<MReason> reasonList = (List<MReason>) model.get("reasonList");

		final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

		Map<String,TDailyWKNGReason> reasonMap = new LinkedHashMap<String,TDailyWKNGReason>();
		reasonMap = dailyWK.getReasonMap();

		// ── Styles ──────────────────────────────────────────────────────────
		Style fstHDRStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFont(FONT_HEADR).setTopBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setBgColor();
		Style sndHDRStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFont(FONT_HEADR).setTopBorder().setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setBgColor();

		// data cell styles — "first sub-row" of a group has top border, "extra sub-rows" do not
		Style centerMergeStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setLeftBorder().setRightBorder().setTopBorder().setWrapText();
		Style centerExtraStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setLeftBorder().setRightBorder().setWrapText();
		Style leftMergeStyle   = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setTopBorder().setWrapText();
		Style leftExtraStyle   = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setWrapText();
		Style numMergeStyle    = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setRightBorder().setTopBorder().setWrapText();
		Style numExtraStyle    = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setRightBorder().setWrapText();
		Style lastRowCenter    = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setLeftBorder().setRightBorder().setTopBorder().setBottomBorder().setWrapText();
		Style lastRowLeft      = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setTopBorder().setBottomBorder().setWrapText();
		Style lastRowNum       = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setRightBorder().setTopBorder().setBottomBorder().setWrapText();

		// reason / individual losstime cell — always bottom border on last sub-row
		Style reasonStyle      = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setTopBorder().setWrapText();
		Style reasonLastStyle  = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setTopBorder().setBottomBorder().setWrapText();
		Style ltEachStyle      = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setRightBorder().setTopBorder().setWrapText();
		Style ltEachLastStyle  = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setRightBorder().setTopBorder().setBottomBorder().setWrapText();
		Style blankStyle       = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setTopBorder().setBottomBorder().setWrapText();
		Style ngStyle          = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setTopBorder().setBottomBorder().setWrapText();
		// ── Sheet & Header ───────────────────────────────────────────────────
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow fstHeader = sheet.getRow(2);
		HSSFRow sndHeader = sheet.getRow(3);

		// Template อาจยังมี merged region เก่า (Worker/Grand Total ที่ col 14+) ค้างอยู่
		// ต้องลบก่อนแล้วค่อย re-write header ใหม่ในตำแหน่งที่ถูกต้อง
		for (int i = sheet.getNumMergedRegions() - 1; i >= 0; i--) {
			CellRangeAddress rgn = sheet.getMergedRegion(i);
			if (rgn.getFirstRow() <= 3 && rgn.getLastRow() >= 2 && rgn.getFirstColumn() >= COL_LOSS_TIME_EACH) {
				sheet.removeMergedRegion(i);
			}
		}

		// Style สำหรับ col header ที่ merge ทั้ง 2 rows (top+bottom border MEDIUM)
		Style singleHdrStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER)
			.setFont(FONT_HEADR).setTopBorder(CellStyle.BORDER_MEDIUM).setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setBgColor().setWrapText();

		// Col 14: Loss Time (min) — merge rows 2-3
		createCell(workbook, fstHeader, COL_LOSS_TIME_EACH, singleHdrStyle).setValue("Loss Time\n(min)");
		createCell(workbook, sndHeader, COL_LOSS_TIME_EACH, singleHdrStyle);
		createMergedRegion(sheet, 2, 3, COL_LOSS_TIME_EACH, COL_LOSS_TIME_EACH);

		// Col 15: Worker — merge rows 2-3
		createCell(workbook, fstHeader, COL_STAFF, singleHdrStyle).setValue("Worker");
		createCell(workbook, sndHeader, COL_STAFF, singleHdrStyle);
		createMergedRegion(sheet, 2, 3, COL_STAFF, COL_STAFF);

		// Cols 16-19: Grand Total Actual Qty
		createCell(workbook, fstHeader, COL_GT_OK,    fstHDRStyle).setValue("Grand Total Actual Qty");
		createCell(workbook, fstHeader, COL_GT_NG,    fstHDRStyle);
		createCell(workbook, fstHeader, COL_GT_PD,    fstHDRStyle);
		createCell(workbook, fstHeader, COL_GT_TOTAL, fstHDRStyle);
		createMergedRegion(sheet, 2, 2, COL_GT_OK, COL_GT_TOTAL);
		createCell(workbook, sndHeader, COL_GT_OK,    sndHDRStyle).setValue("OK");
		createCell(workbook, sndHeader, COL_GT_NG,    sndHDRStyle).setValue("NG");
		createCell(workbook, sndHeader, COL_GT_PD,    sndHDRStyle).setValue("PD");
		createCell(workbook, sndHeader, COL_GT_TOTAL, sndHDRStyle).setValue("Total");

		// NG reason dynamic headers (start at COL_NG_REASON_START)
		int colNumber = COL_NG_REASON_START;
		if (reasonList.size() > 0) {
			for ( MReason MReason : reasonList ) {
				createCell(workbook, fstHeader, colNumber, fstHDRStyle);
				createCell(workbook, sndHeader, colNumber, sndHDRStyle).setValue(MReason.getReasonCode());
				colNumber += 1;
			}
			createMergedRegion(sheet, 2, 2, COL_NG_REASON_START, colNumber - 1);
			sheet.getRow(2).getCell(COL_NG_REASON_START).setCellValue("NG Reason");
		}

		// ── Data Rows ────────────────────────────────────────────────────────
		int rowNumber = 4;
		List<TDailyWKDetail> dList = dailyWK.getDailyWKDetailList();

		for ( TDailyWKDetail detail : dList ) {
			List<TDailyWKLossTime> ltList = detail.getLossTimeList();
			int subRows = (ltList == null || ltList.isEmpty()) ? 1 : ltList.size();
			int rowStart = rowNumber;
			int rowEnd   = rowNumber + subRows - 1;

			// ── Create all sub-rows first ──────────────────────────────────
			for (int s = 0; s < subRows; s++) {
				sheet.createRow(rowNumber + s);
			}

			// ── Merged columns (0-12, COL_STAFF, COL_GT_OK..COL_GT_TOTAL, NG cols) ──
			// Use bottom border only on last sub-row; top border only on first sub-row.
			// When subRows==1, first==last so use full-bordered styles.
			boolean single = (subRows == 1);

			// Helper styles for merged columns depend on whether it's a single or multi row group
			Style mCenter = single ? lastRowCenter : centerMergeStyle;
			Style mLeft   = single ? lastRowLeft   : leftMergeStyle;
			Style mNum    = single ? lastRowNum     : numMergeStyle;

			HSSFRow firstRow = sheet.getRow(rowStart);
			String shift = detail.getShift();

			createCell(workbook, firstRow,  0, mCenter).setValue(dateFormatter.format(detail.getReportDate()));
			createCell(workbook, firstRow,  1, mCenter).setValue(detail.getCustomerCode());
			createCell(workbook, firstRow,  2, mCenter).setValue(detail.getWip());
			createCell(workbook, firstRow,  3, mLeft  ).setValue(detail.getPartNo());
			createCell(workbook, firstRow,  4, mLeft  ).setValue(detail.getPartName());
			createCell(workbook, firstRow,  5, mLeft  ).setValue("N".equals(shift) ? "Night" : "Day");
			createCell(workbook, firstRow,  6, mNum   ).setValue(detail.getOk());
			createCell(workbook, firstRow,  7, mNum   ).setValue(detail.getNg());
			createCell(workbook, firstRow,  8, mNum   ).setValue(detail.getPd());
			createCell(workbook, firstRow,  9, mNum   ).setValue(new Integer[]{ detail.getOk(), detail.getNg(), detail.getPd() });
			createCell(workbook, firstRow, 10, mNum   ).setValue(detail.getTimeUsed());
			createCell(workbook, firstRow, 11, mNum   ).setValue(detail.getManPower());
			createCell(workbook, firstRow, 12, mNum   ).setValue(detail.getLossTime());

			createCell(workbook, firstRow, COL_STAFF,    mCenter).setValue(detail.getStaff());
			createCell(workbook, firstRow, COL_GT_OK,    mNum   ).setValue(detail.getOk());
			createCell(workbook, firstRow, COL_GT_NG,    mNum   ).setValue(detail.getNg());
			createCell(workbook, firstRow, COL_GT_PD,    mNum   ).setValue(detail.getPd());
			createCell(workbook, firstRow, COL_GT_TOTAL, mNum   ).setValue(new Integer[]{ detail.getOk(), detail.getNg(), detail.getPd() });

			// NG reasons — always on first sub-row, merged down
			colNumber = COL_NG_REASON_START;
			if (reasonList.size() > 0) {
				for ( MReason MReason : reasonList ) {
					String key = detail.getDatailRef() + ":" + MReason.getReasonId();
					TDailyWKNGReason reason = reasonMap.get(key);
					int ngQty = 0;
					if (reason != null && key.equals(reason.getIdRef())) {
						ngQty += nullToZero(reason.getNg());
					}
					createCell(workbook, firstRow, colNumber, ngStyle).setValue(ngQty);
					colNumber++;
				}
			}

			// If multi-row group: fill extra sub-rows with empty styled cells for merged cols
			if (!single) {
				for (int s = 1; s < subRows; s++) {
					HSSFRow extraRow = sheet.getRow(rowStart + s);
					boolean isLast = (s == subRows - 1);
					Style eCenter = isLast ? lastRowCenter : centerExtraStyle;
					Style eLeft   = isLast ? lastRowLeft   : leftExtraStyle;
					Style eNum    = isLast ? lastRowNum     : numExtraStyle;
					for (int c = 0; c <= 12; c++) {
						if (c == 0) createCell(workbook, extraRow, c, eCenter);
						else if (c >= 3 && c <= 5) createCell(workbook, extraRow, c, eLeft);
						else createCell(workbook, extraRow, c, eNum);
					}
					createCell(workbook, extraRow, COL_STAFF,    eCenter);
					createCell(workbook, extraRow, COL_GT_OK,    eNum);
					createCell(workbook, extraRow, COL_GT_NG,    eNum);
					createCell(workbook, extraRow, COL_GT_PD,    eNum);
					createCell(workbook, extraRow, COL_GT_TOTAL, eNum);
					colNumber = COL_NG_REASON_START;
					for (int r = 0; r < (reasonList != null ? reasonList.size() : 0); r++) {
						createCell(workbook, extraRow, colNumber++, ngStyle);
					}
				}

				// Merge cols 0-12, COL_STAFF, blank cols, NG cols across all sub-rows
				for (int c = 0; c <= 12; c++) {
					createMergedRegion(sheet, rowStart, rowEnd, c, c);
				}
				createMergedRegion(sheet, rowStart, rowEnd, COL_STAFF,    COL_STAFF);
				createMergedRegion(sheet, rowStart, rowEnd, COL_GT_OK,    COL_GT_OK);
				createMergedRegion(sheet, rowStart, rowEnd, COL_GT_NG,    COL_GT_NG);
				createMergedRegion(sheet, rowStart, rowEnd, COL_GT_PD,    COL_GT_PD);
				createMergedRegion(sheet, rowStart, rowEnd, COL_GT_TOTAL, COL_GT_TOTAL);
				int ngEnd = COL_NG_REASON_START + (reasonList != null ? reasonList.size() : 0) - 1;
				if (ngEnd >= COL_NG_REASON_START) {
					for (int c = COL_NG_REASON_START; c <= ngEnd; c++) {
						createMergedRegion(sheet, rowStart, rowEnd, c, c);
					}
				}
			}

			// ── Loss time reason sub-rows (cols 13-14) ────────────────────
			if (ltList == null || ltList.isEmpty()) {
				// No reasons — blank cells in cols 13-14
				createCell(workbook, firstRow, COL_LOSS_REASON,    reasonLastStyle);
				createCell(workbook, firstRow, COL_LOSS_TIME_EACH, ltEachLastStyle);
			} else {
				for (int s = 0; s < subRows; s++) {
					HSSFRow ltRow = sheet.getRow(rowStart + s);
					boolean isLast = (s == subRows - 1);
					TDailyWKLossTime lt = ltList.get(s);
					createCell(workbook, ltRow, COL_LOSS_REASON,    isLast ? reasonLastStyle : reasonStyle)
						.setValue(lt.getLossTimeReasonName() != null ? lt.getLossTimeReasonName() : "");
					createCell(workbook, ltRow, COL_LOSS_TIME_EACH, isLast ? ltEachLastStyle : ltEachStyle)
						.setValue(lt.getLossTime());
				}
			}

			rowNumber = rowEnd + 1;
		}

	}
}
