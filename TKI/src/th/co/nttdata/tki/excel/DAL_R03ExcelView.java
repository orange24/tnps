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
	// Col 6-9  : OK, NG, PD, Qty (Actual Qty)
	// Col 10   : TimeUsed (Time Min)
	// Col 11   : ManPower
	// Col 12   : STD. Time (min)
	// Col 13   : Grand Total Actual Time (min) = TimeUsed + TotalLossTime
	// Col 14   : Difference = STD.Time - Grand Total Actual Time
	// Col 15   : Percentage = Grand Total Actual Time / STD.Time
	// Col 16   : Total Loss Time (min)
	// Col 17   : Loss Time Reason (1 reason per sub-row)
	// Col 18   : Individual Reason Loss Time
	// Col 19   : Staff/Worker
	// Col 20-23: Grand Total Actual Qty (OK, NG, PD, Total)
	// Col 24+  : NG Reasons (dynamic)
	private static final int COL_STD_TIME        = 12;
	private static final int COL_GT_ACTUAL_TIME  = 13;
	private static final int COL_DIFFERENCE      = 14;
	private static final int COL_PERCENTAGE      = 15;
	private static final int COL_LOSS_TIME_TOTAL = 16;
	private static final int COL_LOSS_REASON     = 17;
	private static final int COL_LOSS_TIME_EACH  = 18;
	private static final int COL_STAFF           = 19;
	private static final int COL_GT_OK           = 20;
	private static final int COL_GT_NG           = 21;
	private static final int COL_GT_PD           = 22;
	private static final int COL_GT_TOTAL        = 23;
	private static final int COL_NG_REASON_START = 24;

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

		// data cell styles
		Style centerMergeStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setLeftBorder().setRightBorder().setTopBorder().setWrapText();
		Style centerExtraStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setLeftBorder().setRightBorder().setWrapText();
		Style leftMergeStyle   = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setTopBorder().setWrapText();
		Style leftExtraStyle   = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setWrapText();
		Style numMergeStyle    = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setRightBorder().setTopBorder().setWrapText();
		Style numExtraStyle    = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setRightBorder().setWrapText();
		Style lastRowCenter    = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setLeftBorder().setRightBorder().setTopBorder().setBottomBorder().setWrapText();
		Style lastRowLeft      = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setTopBorder().setBottomBorder().setWrapText();
		Style lastRowNum       = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setRightBorder().setTopBorder().setBottomBorder().setWrapText();

		// percentage styles (value stored as decimal e.g. 0.88 → displays "88%")
		Style pctMergeStyle    = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFormat("0%").setRightBorder().setTopBorder().setWrapText();
		Style pctLastStyle     = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFormat("0%").setRightBorder().setTopBorder().setBottomBorder().setWrapText();

		// reason / individual losstime cell
		Style reasonStyle      = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setTopBorder().setWrapText();
		Style reasonLastStyle  = createStyle(workbook, HSSFCellStyle.ALIGN_LEFT,   HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setTopBorder().setBottomBorder().setWrapText();
		Style ltEachStyle      = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setRightBorder().setTopBorder().setWrapText();
		Style ltEachLastStyle  = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFormat("#,##0").setRightBorder().setTopBorder().setBottomBorder().setWrapText();
		Style blankStyle       = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setTopBorder().setBottomBorder().setWrapText();
		Style ngStyle          = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setTopBorder().setBottomBorder().setWrapText();
		Style totalLabelStyle  = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER).setFont(FONT_HEADR).setLeftBorder().setRightBorder().setTopBorder(CellStyle.BORDER_MEDIUM).setBottomBorder(CellStyle.BORDER_MEDIUM).setBgColor().setWrapText();
		Style totalNumStyle    = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFont(FONT_HEADR).setFormat("#,##0").setRightBorder().setTopBorder(CellStyle.BORDER_MEDIUM).setBottomBorder(CellStyle.BORDER_MEDIUM).setBgColor().setWrapText();
		Style totalPctStyle    = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setFont(FONT_HEADR).setFormat("0%").setRightBorder().setTopBorder(CellStyle.BORDER_MEDIUM).setBottomBorder(CellStyle.BORDER_MEDIUM).setBgColor().setWrapText();
		Style totalBlankStyle  = createStyle(workbook, HSSFCellStyle.ALIGN_RIGHT,  HSSFCellStyle.VERTICAL_CENTER).setRightBorder().setTopBorder(CellStyle.BORDER_MEDIUM).setBottomBorder(CellStyle.BORDER_MEDIUM).setBgColor().setWrapText();

		// ── Sheet & Header ───────────────────────────────────────────────────
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow fstHeader = sheet.getRow(2);
		HSSFRow sndHeader = sheet.getRow(3);

		// ลบ merged regions เก่าจาก template ที่ col 12+ (template ยังมี LossTime/LossReason ที่ตำแหน่งเก่า)
		for (int i = sheet.getNumMergedRegions() - 1; i >= 0; i--) {
			CellRangeAddress rgn = sheet.getMergedRegion(i);
			if (rgn.getFirstRow() <= 3 && rgn.getLastRow() >= 2 && rgn.getFirstColumn() >= COL_STD_TIME) {
				sheet.removeMergedRegion(i);
			}
		}

		// Style สำหรับ header col ที่ merge ทั้ง 2 rows (top+bottom border MEDIUM)
		Style singleHdrStyle = createStyle(workbook, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER)
			.setFont(FONT_HEADR).setTopBorder(CellStyle.BORDER_MEDIUM).setBottomBorder(CellStyle.BORDER_MEDIUM).setRightBorder().setBgColor().setWrapText();

		// Col 12: STD. Time (min)
		createCell(workbook, fstHeader, COL_STD_TIME, singleHdrStyle).setValue("STD. Time\n(min)");
		createCell(workbook, sndHeader, COL_STD_TIME, singleHdrStyle);
		createMergedRegion(sheet, 2, 3, COL_STD_TIME, COL_STD_TIME);

		// Col 13: Grand Total Actual Time (min)
		createCell(workbook, fstHeader, COL_GT_ACTUAL_TIME, singleHdrStyle).setValue("Grand Total\nActual Time\n(min)");
		createCell(workbook, sndHeader, COL_GT_ACTUAL_TIME, singleHdrStyle);
		createMergedRegion(sheet, 2, 3, COL_GT_ACTUAL_TIME, COL_GT_ACTUAL_TIME);

		// Col 14: Difference
		createCell(workbook, fstHeader, COL_DIFFERENCE, singleHdrStyle).setValue("Difference");
		createCell(workbook, sndHeader, COL_DIFFERENCE, singleHdrStyle);
		createMergedRegion(sheet, 2, 3, COL_DIFFERENCE, COL_DIFFERENCE);

		// Col 15: Percentage
		createCell(workbook, fstHeader, COL_PERCENTAGE, singleHdrStyle).setValue("Percentage");
		createCell(workbook, sndHeader, COL_PERCENTAGE, singleHdrStyle);
		createMergedRegion(sheet, 2, 3, COL_PERCENTAGE, COL_PERCENTAGE);

		// Col 16: Total Loss Time (min) — เขียนใน Java เพราะ template มีที่ตำแหน่งเก่า (col 12)
		createCell(workbook, fstHeader, COL_LOSS_TIME_TOTAL, singleHdrStyle).setValue("Total Loss\nTime\n(min)");
		createCell(workbook, sndHeader, COL_LOSS_TIME_TOTAL, singleHdrStyle);
		createMergedRegion(sheet, 2, 3, COL_LOSS_TIME_TOTAL, COL_LOSS_TIME_TOTAL);

		// Col 17: Loss Time Reason — เขียนใน Java เพราะ template มีที่ตำแหน่งเก่า (col 13)
		createCell(workbook, fstHeader, COL_LOSS_REASON, singleHdrStyle).setValue("Loss Time Reason");
		createCell(workbook, sndHeader, COL_LOSS_REASON, singleHdrStyle);
		createMergedRegion(sheet, 2, 3, COL_LOSS_REASON, COL_LOSS_REASON);

		// Col 18: Loss Time (min) per reason
		createCell(workbook, fstHeader, COL_LOSS_TIME_EACH, singleHdrStyle).setValue("Loss Time\n(min)");
		createCell(workbook, sndHeader, COL_LOSS_TIME_EACH, singleHdrStyle);
		createMergedRegion(sheet, 2, 3, COL_LOSS_TIME_EACH, COL_LOSS_TIME_EACH);

		// Col 19: Worker
		createCell(workbook, fstHeader, COL_STAFF, singleHdrStyle).setValue("Worker");
		createCell(workbook, sndHeader, COL_STAFF, singleHdrStyle);
		createMergedRegion(sheet, 2, 3, COL_STAFF, COL_STAFF);

		// Cols 20-23: Grand Total Actual Qty
		createCell(workbook, fstHeader, COL_GT_OK,    fstHDRStyle).setValue("Grand Total Actual Qty");
		createCell(workbook, fstHeader, COL_GT_NG,    fstHDRStyle);
		createCell(workbook, fstHeader, COL_GT_PD,    fstHDRStyle);
		createCell(workbook, fstHeader, COL_GT_TOTAL, fstHDRStyle);
		createMergedRegion(sheet, 2, 2, COL_GT_OK, COL_GT_TOTAL);
		createCell(workbook, sndHeader, COL_GT_OK,    sndHDRStyle).setValue("OK");
		createCell(workbook, sndHeader, COL_GT_NG,    sndHDRStyle).setValue("NG");
		createCell(workbook, sndHeader, COL_GT_PD,    sndHDRStyle).setValue("PD");
		createCell(workbook, sndHeader, COL_GT_TOTAL, sndHDRStyle).setValue("Total");

		// NG reason dynamic headers
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

		// Grand Total accumulators
		long totalOk = 0, totalNg = 0, totalPd = 0, totalQty = 0;
		long totalTimeUsed = 0, totalManPower = 0, totalLossTime = 0, totalStdTime = 0;
		Map<Integer, Long> totalNgByReason = new LinkedHashMap<Integer, Long>();
		if (reasonList != null) {
			for (MReason r : reasonList) totalNgByReason.put(r.getReasonId(), 0L);
		}

		for ( TDailyWKDetail detail : dList ) {
			List<TDailyWKLossTime> ltList = detail.getLossTimeList();
			int subRows = (ltList == null || ltList.isEmpty()) ? 1 : ltList.size();
			int rowStart = rowNumber;
			int rowEnd   = rowNumber + subRows - 1;

			// ── Create all sub-rows first ──────────────────────────────────
			for (int s = 0; s < subRows; s++) {
				sheet.createRow(rowNumber + s);
			}

			boolean single = (subRows == 1);
			Style mCenter = single ? lastRowCenter : centerMergeStyle;
			Style mLeft   = single ? lastRowLeft   : leftMergeStyle;
			Style mNum    = single ? lastRowNum     : numMergeStyle;
			Style mPct    = single ? pctLastStyle   : pctMergeStyle;

			HSSFRow firstRow = sheet.getRow(rowStart);
			String shift = detail.getShift();

			// Cols 0-11: Date ... ManPower
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

			// คำนวณค่าสำหรับ 4 คอลัมน์ใหม่
			int stdTimeVal  = detail.getStdTime() != null ? detail.getStdTime() : 0;
			int timeUsedVal = detail.getTimeUsed() != null ? detail.getTimeUsed().intValue() : 0;
			int lossTimeVal = detail.getLossTime() != null ? detail.getLossTime() : 0;
			int gtActualTime = timeUsedVal + lossTimeVal;
			int difference   = stdTimeVal - gtActualTime;
			double pctValue  = (stdTimeVal > 0) ? (double) gtActualTime / stdTimeVal : 0.0;

			// Col 12: STD. Time
			if (stdTimeVal > 0) {
				createCell(workbook, firstRow, COL_STD_TIME, mNum).setValue(stdTimeVal);
			} else {
				createCell(workbook, firstRow, COL_STD_TIME, mNum);
			}
			// Col 13: Grand Total Actual Time
			createCell(workbook, firstRow, COL_GT_ACTUAL_TIME, mNum).setValue(gtActualTime);
			// Col 14: Difference
			createCell(workbook, firstRow, COL_DIFFERENCE, mNum).setValue(difference);
			// Col 15: Percentage
			if (stdTimeVal > 0) {
				createCell(workbook, firstRow, COL_PERCENTAGE, mPct).setValue(pctValue);
			} else {
				createCell(workbook, firstRow, COL_PERCENTAGE, mPct);
			}
			// Col 16: Total Loss Time
			createCell(workbook, firstRow, COL_LOSS_TIME_TOTAL, mNum).setValue(lossTimeVal);

			// accumulate grand total
			totalOk       += nullToZero(detail.getOk());
			totalNg       += nullToZero(detail.getNg());
			totalPd       += nullToZero(detail.getPd());
			totalQty      += nullToZero(detail.getOk()) + nullToZero(detail.getNg()) + nullToZero(detail.getPd());
			totalTimeUsed += timeUsedVal;
			totalManPower += detail.getManPower() != null ? detail.getManPower().longValue() : 0L;
			totalLossTime += lossTimeVal;
			totalStdTime  += stdTimeVal;

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
					Long prev = totalNgByReason.get(MReason.getReasonId());
					totalNgByReason.put(MReason.getReasonId(), (prev != null ? prev : 0L) + ngQty);
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
					Style ePct    = isLast ? pctLastStyle   : pctMergeStyle;
					for (int c = 0; c <= 11; c++) {
						if (c == 0) createCell(workbook, extraRow, c, eCenter);
						else if (c >= 3 && c <= 5) createCell(workbook, extraRow, c, eLeft);
						else createCell(workbook, extraRow, c, eNum);
					}
					createCell(workbook, extraRow, COL_STD_TIME,        eNum);
					createCell(workbook, extraRow, COL_GT_ACTUAL_TIME,  eNum);
					createCell(workbook, extraRow, COL_DIFFERENCE,      eNum);
					createCell(workbook, extraRow, COL_PERCENTAGE,      ePct);
					createCell(workbook, extraRow, COL_LOSS_TIME_TOTAL, eNum);
					createCell(workbook, extraRow, COL_STAFF,           eCenter);
					createCell(workbook, extraRow, COL_GT_OK,           eNum);
					createCell(workbook, extraRow, COL_GT_NG,           eNum);
					createCell(workbook, extraRow, COL_GT_PD,           eNum);
					createCell(workbook, extraRow, COL_GT_TOTAL,        eNum);
					colNumber = COL_NG_REASON_START;
					for (int r = 0; r < (reasonList != null ? reasonList.size() : 0); r++) {
						createCell(workbook, extraRow, colNumber++, ngStyle);
					}
				}

				// Merge all fixed-value columns across all sub-rows
				for (int c = 0; c <= 11; c++) {
					createMergedRegion(sheet, rowStart, rowEnd, c, c);
				}
				createMergedRegion(sheet, rowStart, rowEnd, COL_STD_TIME,        COL_STD_TIME);
				createMergedRegion(sheet, rowStart, rowEnd, COL_GT_ACTUAL_TIME,  COL_GT_ACTUAL_TIME);
				createMergedRegion(sheet, rowStart, rowEnd, COL_DIFFERENCE,      COL_DIFFERENCE);
				createMergedRegion(sheet, rowStart, rowEnd, COL_PERCENTAGE,      COL_PERCENTAGE);
				createMergedRegion(sheet, rowStart, rowEnd, COL_LOSS_TIME_TOTAL, COL_LOSS_TIME_TOTAL);
				createMergedRegion(sheet, rowStart, rowEnd, COL_STAFF,           COL_STAFF);
				createMergedRegion(sheet, rowStart, rowEnd, COL_GT_OK,           COL_GT_OK);
				createMergedRegion(sheet, rowStart, rowEnd, COL_GT_NG,           COL_GT_NG);
				createMergedRegion(sheet, rowStart, rowEnd, COL_GT_PD,           COL_GT_PD);
				createMergedRegion(sheet, rowStart, rowEnd, COL_GT_TOTAL,        COL_GT_TOTAL);
				int ngEnd = COL_NG_REASON_START + (reasonList != null ? reasonList.size() : 0) - 1;
				if (ngEnd >= COL_NG_REASON_START) {
					for (int c = COL_NG_REASON_START; c <= ngEnd; c++) {
						createMergedRegion(sheet, rowStart, rowEnd, c, c);
					}
				}
			}

			// ── Loss time reason sub-rows (COL_LOSS_REASON, COL_LOSS_TIME_EACH) ──
			if (ltList == null || ltList.isEmpty()) {
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

		// ── Grand Total Row ──────────────────────────────────────────────────
		long totalGtActualTime = totalTimeUsed + totalLossTime;
		long totalDifference   = totalStdTime - totalGtActualTime;
		double totalPctValue   = (totalStdTime > 0) ? (double) totalGtActualTime / totalStdTime : 0.0;

		HSSFRow totalRow = sheet.createRow(rowNumber);
		createCell(workbook, totalRow, 0, totalLabelStyle).setValue("Grand Total");
		for (int c = 1; c <= 5; c++) {
			createCell(workbook, totalRow, c, totalLabelStyle);
		}
		createMergedRegion(sheet, rowNumber, rowNumber, 0, 5);

		createCell(workbook, totalRow,  6, totalNumStyle).setValue((int) totalOk);
		createCell(workbook, totalRow,  7, totalNumStyle).setValue((int) totalNg);
		createCell(workbook, totalRow,  8, totalNumStyle).setValue((int) totalPd);
		createCell(workbook, totalRow,  9, totalNumStyle).setValue((int) totalQty);
		createCell(workbook, totalRow, 10, totalNumStyle).setValue((int) totalTimeUsed);
		createCell(workbook, totalRow, 11, totalNumStyle).setValue((int) totalManPower);
		createCell(workbook, totalRow, COL_STD_TIME,        totalStdTime > 0 ? totalNumStyle  : totalBlankStyle).setValue(totalStdTime > 0 ? (int) totalStdTime : null);
		createCell(workbook, totalRow, COL_GT_ACTUAL_TIME,  totalNumStyle).setValue((int) totalGtActualTime);
		createCell(workbook, totalRow, COL_DIFFERENCE,      totalNumStyle).setValue((int) totalDifference);
		createCell(workbook, totalRow, COL_PERCENTAGE,      totalStdTime > 0 ? totalPctStyle  : totalBlankStyle).setValue(totalStdTime > 0 ? totalPctValue : null);
		createCell(workbook, totalRow, COL_LOSS_TIME_TOTAL, totalNumStyle).setValue((int) totalLossTime);
		createCell(workbook, totalRow, COL_LOSS_REASON,     totalBlankStyle);
		createCell(workbook, totalRow, COL_LOSS_TIME_EACH,  totalBlankStyle);
		createCell(workbook, totalRow, COL_STAFF,           totalBlankStyle);
		createCell(workbook, totalRow, COL_GT_OK,           totalNumStyle).setValue((int) totalOk);
		createCell(workbook, totalRow, COL_GT_NG,           totalNumStyle).setValue((int) totalNg);
		createCell(workbook, totalRow, COL_GT_PD,           totalNumStyle).setValue((int) totalPd);
		createCell(workbook, totalRow, COL_GT_TOTAL,        totalNumStyle).setValue((int) totalQty);
		colNumber = COL_NG_REASON_START;
		if (reasonList != null) {
			for (MReason MReason : reasonList) {
				Long ngTotal = totalNgByReason.get(MReason.getReasonId());
				createCell(workbook, totalRow, colNumber++, totalNumStyle).setValue((int)(ngTotal != null ? ngTotal : 0L));
			}
		}
	}
}
