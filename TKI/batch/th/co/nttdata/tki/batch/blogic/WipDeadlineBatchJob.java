package th.co.nttdata.tki.batch.blogic;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import th.co.nttdata.tki.batch.bean.TWIPDeadline;
import th.co.nttdata.tki.batch.bean.TWIPDeadlinedDate;
import th.co.nttdata.tki.batch.dao.WipDeadlineBatchDao;

/**
 * WIP Deadline Batch Job — แปลง WIPDeadlineLogicImpl เป็น TimerTask + parallel
 *
 * Threading strategy:
 *   Main thread  : prepareDeadlineTables → queryDeliveryPlan → bulk insert → finalize
 *   Worker threads: queryCapStockWIP(partId) + calculatePart — แบ่งตาม partition of parts
 *
 * Performance:
 *   - ดึง delivery plan ครั้งเดียว แล้วแบ่ง parts ให้ THREAD_COUNT threads คำนวณพร้อมกัน
 *   - bulk INSERT header + date ใน executeBatch ครั้งเดียวหลังคำนวณเสร็จทุก parts
 *   - ไม่ insert ทีละ part ใน loop เหมือนเดิม
 */
public class WipDeadlineBatchJob extends TimerTask {

    private static final Logger log = Logger.getLogger(WipDeadlineBatchJob.class);

    private static final String BATCH_CODE   = "WIP_B02";
    private static final String BATCH_NAME   = "15. TNPS - WIP Deadline Calculation";
    private static final int    THREAD_COUNT = 4;

    private final DataSource dataSource;
    private final Date       executeDate;
    private final String     executedBy;
    private final int        shiftHour;   // ชั่วโมงต่อ shift (default 12)
    private final int        ngRatio;     // NG ratio % (default 10)

    public WipDeadlineBatchJob(DataSource dataSource, Date executeDate,
                               String executedBy, Properties settings) {
        this.dataSource  = dataSource;
        this.executeDate = stripTime(executeDate != null ? executeDate : new Date());
        this.executedBy  = executedBy;
        this.shiftHour   = Integer.parseInt(settings.getProperty("WIP.deadline.defaultHour", "12"));
        this.ngRatio     = Integer.parseInt(settings.getProperty("Batch.NGRatio", "10"));
    }

    // =========================================================================
    //  Main flow
    // =========================================================================

    @Override
    public void run() {
        log.info("[WIP_B02] Start — executeDate=" + executeDate);

        WipDeadlineBatchDao dao  = new WipDeadlineBatchDao(dataSource);
        ExecutorService     exec = Executors.newFixedThreadPool(THREAD_COUNT);

        // ── Batch Control: ป้องกัน run ซ้ำ ───────────────────────────────────
        if (dao.isBatchRunning(BATCH_CODE)) {
            log.warn("[WIP_B02] status=1 กำลัง run อยู่ — abort เพื่อป้องกันซ้ำ");
            exec.shutdown();
            return;
        }
        dao.upsertBatchControl(BATCH_CODE, BATCH_NAME, 1, executedBy);
        log.info("[WIP_B02] Batch Control set RUNNING (status=1)");

        String  errorMsg  = null;
        boolean success   = false;
        long batchStart = System.currentTimeMillis();
        try {
            // ── Step 1: เตรียม table ──────────────────────────────────────────
            log.info("[WIP_B02] [1/5] prepareDeadlineTables...");
            long t1 = System.currentTimeMillis();
            dao.prepareDeadlineTables(executeDate);
            log.info("[WIP_B02] [1/5] prepareDeadlineTables done (" + elapsed(t1) + " ms)");

            // ── Step 2: Fetch delivery plan ───────────────────────────────────
            log.info("[WIP_B02] [2/5] queryDeliveryPlan...");
            long t2 = System.currentTimeMillis();
            List<TWIPDeadline> dlvList = dao.queryDeliveryPlan();
            log.info("[WIP_B02] [2/5] queryDeliveryPlan done — parts=" + dlvList.size()
                    + " (" + elapsed(t2) + " ms)");
            if (dlvList.isEmpty()) {
                errorMsg = "ไม่มี delivery plan";
                log.warn("[WIP_B02] " + errorMsg + " — abort");
                return;
            }

            // ── Step 3: คำนวณแต่ละ part แบบ parallel ─────────────────────────
            log.info("[WIP_B02] [3/5] processPartsParallel — " + dlvList.size()
                    + " parts / " + THREAD_COUNT + " threads...");
            long t3 = System.currentTimeMillis();
            List<TWIPDeadline> allDeadlines = processPartsParallel(dlvList, dao, exec);
            log.info("[WIP_B02] [3/5] processPartsParallel done — headers="
                    + allDeadlines.size() + " (" + elapsed(t3) + " ms)");

            // ── Step 4: Bulk INSERT ────────────────────────────────────────────
            int totalDates = 0;
            for (TWIPDeadline d : allDeadlines) {
                if (d.getWipDeadlinedDateList() != null) totalDates += d.getWipDeadlinedDateList().size();
            }
            log.info("[WIP_B02] [4/5] bulkInsertDeadline — headers=" + allDeadlines.size()
                    + " dates=" + totalDates + "...");
            long t4 = System.currentTimeMillis();
            dao.bulkInsertDeadline(allDeadlines);
            log.info("[WIP_B02] [4/5] bulkInsertDeadline done (" + elapsed(t4) + " ms)");

            // ── Step 5: Finalize ───────────────────────────────────────────────
            log.info("[WIP_B02] [5/5] finalizeDeadline...");
            long t5 = System.currentTimeMillis();
            dao.finalizeDeadline();
            log.info("[WIP_B02] [5/5] finalizeDeadline done (" + elapsed(t5) + " ms)");

            success = true;
            log.info("[WIP_B02] ===== DONE — total elapsed=" + elapsed(batchStart) + " ms =====");

        } catch (Exception e) {
            errorMsg = e.getMessage();
            log.error("[WIP_B02] Failed after " + elapsed(batchStart) + " ms: " + errorMsg, e);
            throw new RuntimeException("[WIP_B02] Batch failed", e);
        } finally {
            exec.shutdown();
            int finalStatus = success ? 0 : 2;
            dao.upsertBatchControl(BATCH_CODE, BATCH_NAME, finalStatus, executedBy,
                                   success ? null : errorMsg);
            log.info("[WIP_B02] Batch Control set " + (success ? "SUCCESS (0)" : "FAILED (2)"));
        }
    }

    // =========================================================================
    //  Parallel processing
    // =========================================================================

    private List<TWIPDeadline> processPartsParallel(
            List<TWIPDeadline> dlvList,
            final WipDeadlineBatchDao dao,
            ExecutorService exec)
            throws InterruptedException, ExecutionException {

        // settings ที่ใช้ร่วมกัน — ประกาศ final เพื่อใช้ใน anonymous Callable
        final int        shiftPerDay  = 24 / shiftHour;
        final int        shiftStep    = (shiftPerDay == 2) ? 2 : 1;
        final int        shiftLast    = (shiftPerDay == 2) ? 3 : 4;
        final BigDecimal shiftHourBD  = new BigDecimal(shiftHour);
        final int        ngRatioVal   = this.ngRatio;
        final Date       startDate    = stripTime(new Date());

        // แบ่ง parts เป็น partitions ตาม THREAD_COUNT
        int partSize = Math.max(1, (int) Math.ceil((double) dlvList.size() / THREAD_COUNT));
        List<List<TWIPDeadline>> partitions = new ArrayList<List<TWIPDeadline>>();
        for (int i = 0; i < dlvList.size(); i += partSize) {
            int end = Math.min(i + partSize, dlvList.size());
            partitions.add(new ArrayList<TWIPDeadline>(dlvList.subList(i, end)));
        }

        // submit Callable ต่อ partition
        List<Future<List<TWIPDeadline>>> futures = new ArrayList<Future<List<TWIPDeadline>>>();
        for (int i = 0; i < partitions.size(); i++) {
            final List<TWIPDeadline> partition = partitions.get(i);
            final int threadNo = i + 1;
            futures.add(exec.submit(new Callable<List<TWIPDeadline>>() {
                public List<TWIPDeadline> call() throws Exception {
                    log.info("[WIP_B02] thread-" + threadNo + " start — "
                            + partition.size() + " parts");
                    long tThread = System.currentTimeMillis();
                    List<TWIPDeadline> result = new ArrayList<TWIPDeadline>();
                    int done = 0;
                    for (TWIPDeadline dlvp : partition) {
                        log.info("[WIP_B02] thread-" + threadNo
                                + " [" + (++done) + "/" + partition.size() + "]"
                                + " partId=" + dlvp.getPartId()
                                + " queryCapStockWIP...");
                        long tPart = System.currentTimeMillis();
                        List<TWIPDeadline> wipList = dao.queryCapStockWIP(dlvp.getPartId());
                        log.info("[WIP_B02] thread-" + threadNo
                                + " partId=" + dlvp.getPartId()
                                + " wipStages=" + wipList.size()
                                + " calculatePart...");
                        List<TWIPDeadline> deadlines = calculatePart(
                                dlvp, wipList,
                                shiftPerDay, shiftStep, shiftLast,
                                shiftHourBD, ngRatioVal, startDate);
                        result.addAll(deadlines);
                        log.info("[WIP_B02] thread-" + threadNo
                                + " partId=" + dlvp.getPartId()
                                + " DONE — deadlines=" + deadlines.size()
                                + " (" + elapsed(tPart) + " ms)");
                    }
                    log.info("[WIP_B02] thread-" + threadNo + " finished — "
                            + partition.size() + " parts"
                            + " (" + elapsed(tThread) + " ms)");
                    return result;
                }
            }));
        }

        // รวม results จากทุก thread
        List<TWIPDeadline> allDeadlines = new ArrayList<TWIPDeadline>();
        for (Future<List<TWIPDeadline>> f : futures) {
            allDeadlines.addAll(f.get());
        }
        return allDeadlines;
    }

    // =========================================================================
    //  Per-part calculation  (แปลงจาก WIPDeadlineLogicImpl.processing() loop body)
    //  static — ไม่ใช้ instance state → thread-safe
    // =========================================================================

    private static List<TWIPDeadline> calculatePart(
            TWIPDeadline dlvp,
            List<TWIPDeadline> wipList,
            int shiftPerDay, int shiftStep, int shiftLast,
            BigDecimal shiftHour, int ngRatio,
            Date startDate) {

        List<TWIPDeadline> deadlineList = new ArrayList<TWIPDeadline>();
        int countDetail = shiftPerDay * 30;

        // ── NG row ─────────────────────────────────────────────────────────
        TWIPDeadline tWIPNg = new TWIPDeadline();
        tWIPNg.setPartId(dlvp.getPartId());
        tWIPNg.setWip("+NG");
        tWIPNg.setWipOrder(250);
        tWIPNg.setIsWIP(0);
        tWIPNg.setCapacity(new BigDecimal(ngRatio));

        List<TWIPDeadlinedDate> ngDateList = new ArrayList<TWIPDeadlinedDate>();
        for (TWIPDeadlinedDate dl : dlvp.getWipDeadlinedDateList()) {
            TWIPDeadlinedDate d = new TWIPDeadlinedDate();
            d.setShiftId(1);
            d.setReportDate(dl.getReportDate());
            int dlNGRatio = new BigDecimal(ngRatio)
                    .add(new BigDecimal(100))
                    .multiply(new BigDecimal(dl.getDeadlineQty()))
                    .divide(new BigDecimal(100))
                    .setScale(0, BigDecimal.ROUND_UP)
                    .intValue();
            d.setDeadlineQty(dlNGRatio);
            ngDateList.add(d);
        }
        tWIPNg.setWipDeadlinedDateList(ngDateList);
        deadlineList.add(tWIPNg); // index 0 = NG

        // ── FG row ─────────────────────────────────────────────────────────
        TWIPDeadline tWIPFg = new TWIPDeadline();
        tWIPFg.setPartId(dlvp.getPartId());
        tWIPFg.setWip("FG");
        tWIPFg.setWipOrder(245);
        tWIPFg.setIsWIP(0);
        tWIPFg.setStock(dlvp.getStock());

        int stockQTY = dlvp.getStock() != null ? dlvp.getStock() : 0;
        List<TWIPDeadlinedDate> fgDateList  = new ArrayList<TWIPDeadlinedDate>();
        List<TWIPDeadlinedDate> wipQTYList  = new ArrayList<TWIPDeadlinedDate>(); // carry forward ข้าม WIP stages

        // iterate NG dates ย้อนหลัง (เก่าสุด → ใหม่สุด) แล้วหัก FG stock
        for (int i = ngDateList.size() - 1; i >= 0; i--) {
            TWIPDeadlinedDate dl = ngDateList.get(i);
            TWIPDeadlinedDate d  = new TWIPDeadlinedDate();
            d.setShiftId(1);
            d.setWipOrder(245);
            d.setReportDate(dl.getReportDate());
            stockQTY -= dl.getDeadlineQty();
            if (stockQTY >= 0) {
                d.setDeadlineQty(0);
                d.setColorId(3);
            } else {
                d.setColorId(1);
                d.setDeadlineQty(Math.abs(stockQTY));
                stockQTY = 0;
            }
            fgDateList.add(d);
            // copy สำหรับ wipQTYList (ใช้เป็น input WIP แรก)
            TWIPDeadlinedDate copy = new TWIPDeadlinedDate();
            copy.setShiftId(d.getShiftId());
            copy.setWipOrder(d.getWipOrder());
            copy.setReportDate(d.getReportDate());
            copy.setDeadlineQty(d.getDeadlineQty());
            copy.setColorId(d.getColorId());
            wipQTYList.add(copy);
        }
        tWIPFg.setWipDeadlinedDateList(fgDateList);
        deadlineList.add(tWIPFg); // index 1 = FG

        // ── PROD row ───────────────────────────────────────────────────────
        TWIPDeadline tWIPProd = new TWIPDeadline();
        tWIPProd.setPartId(dlvp.getPartId());
        tWIPProd.setWip("PROD");
        tWIPProd.setWipOrder(255);
        tWIPProd.setIsWIP(0);
        tWIPProd.setStock(null);
        List<TWIPDeadlinedDate> prodDates = new ArrayList<TWIPDeadlinedDate>();
        for (TWIPDeadlinedDate dl : dlvp.getWipDeadlinedDateList()) {
            TWIPDeadlinedDate d = new TWIPDeadlinedDate();
            d.setShiftId(1);
            d.setReportDate(dl.getReportDate());
            d.setDeadlineQty(dl.getDeadlineQty());
            prodDates.add(d);
        }
        tWIPProd.setWipDeadlinedDateList(prodDates);
        deadlineList.add(tWIPProd); // index 2 = PROD

        // ── Fill missing days (30-day window) สำหรับ NG, FG, PROD ──────────
        for (TWIPDeadline twdl : deadlineList) {
            if (twdl.getWipDeadlinedDateList().size() != countDetail) {
                List<TWIPDeadlinedDate> dlList = new ArrayList<TWIPDeadlinedDate>();
                Calendar stDate = new GregorianCalendar(Locale.US);
                stDate.setTime(startDate);
                for (int n = 0; n < 30; n++) {
                    for (int s = shiftLast; s >= 1; s -= shiftStep) {
                        TWIPDeadlinedDate placeholder = new TWIPDeadlinedDate();
                        placeholder.setShiftId(s);
                        placeholder.setReportDate(stDate.getTime());
                        dlList.add(placeholder);
                        for (TWIPDeadlinedDate existing : twdl.getWipDeadlinedDateList()) {
                            Calendar rpDate = new GregorianCalendar(Locale.US);
                            rpDate.setTime(existing.getReportDate());
                            int existingShift = existing.getShiftId() != null ? existing.getShiftId() : 1;
                            if (rpDate.get(Calendar.YEAR)        == stDate.get(Calendar.YEAR)
                                    && rpDate.get(Calendar.DAY_OF_YEAR) == stDate.get(Calendar.DAY_OF_YEAR)
                                    && existingShift == s) {
                                dlList.remove(dlList.size() - 1);
                                dlList.add(existing);
                                break;
                            }
                        }
                    }
                    stDate.add(Calendar.DATE, 1);
                }
                twdl.setWipDeadlinedDateList(dlList);
            }
        }

        // ── Add colors for FG (deadlineList.get(1)) ────────────────────────
        List<TWIPDeadlinedDate> fgList = deadlineList.get(1).getWipDeadlinedDateList();
        Integer redInit = null;
        for (int i = 0; i < fgList.size(); i++) {
            if (Integer.valueOf(1).equals(fgList.get(i).getColorId())) { redInit = i; break; }
        }
        Integer lastIndexData = null;
        for (int i = fgList.size() - 1; i >= 0; i--) {
            if (Integer.valueOf(1).equals(fgList.get(i).getColorId())) { lastIndexData = i; break; }
        }
        if (redInit != null) {
            // fill red from redInit+1 → lastIndexData
            int j = redInit + 1;
            while (j <= lastIndexData) { fgList.get(j).setColorId(1); j++; }
            // 6 entries before redInit → yellow
            for (int a = 1; a <= 6; a++) {
                redInit--;
                if (redInit >= 0) fgList.get(redInit).setColorId(2); else break;
            }
            // remaining before yellow → green
            while (redInit > 0) { redInit--; fgList.get(redInit).setColorId(3); }
        } else {
            // no red: find last green and fill backward
            for (int i = fgList.size() - 1; i >= 0; i--) {
                if (Integer.valueOf(3).equals(fgList.get(i).getColorId())) {
                    while (i > 0) { i--; fgList.get(i).setColorId(3); }
                    break;
                }
            }
        }

        // ── WIP calculation (loop wipList DESC by wipOrder) ────────────────
        int sumStock = 0;
        for (int j = wipList.size() - 1; j >= 0; j--) {
            TWIPDeadline wip = wipList.get(j);
            List<TWIPDeadlinedDate> wipShiftQTYList     = new ArrayList<TWIPDeadlinedDate>();
            List<TWIPDeadlinedDate> wipShiftQTYPrevList = new ArrayList<TWIPDeadlinedDate>();

            wip.setCapacity(wip.getCapacity().setScale(0, BigDecimal.ROUND_UP));
            BigDecimal capacity = wip.getCapacity();

            if (capacity.compareTo(BigDecimal.ZERO) != 0) {
                // ถ้าไม่ใช่ WIP สุดท้าย: ใช้ capacity ที่น้อยกว่าระหว่าง current กับ next
                if (j != wipList.size() - 1) {
                    TWIPDeadline wipNext = wipList.get(j + 1);
                    if (wipNext.getCapacity().compareTo(BigDecimal.ZERO) != 0
                            && capacity.compareTo(wipNext.getCapacity()) > 0) {
                        capacity = wipNext.getCapacity().setScale(0, BigDecimal.ROUND_UP);
                    }
                }
                // capacity per shift = capacity * shiftHour (ชม.)
                capacity = capacity.multiply(shiftHour)
                                   .round(new MathContext(0, RoundingMode.UP));

                // loop แต่ละ FG qty entry (wipQTYList) ย้อนหลัง
                for (int i = wipQTYList.size() - 1; i >= 0; i--) {
                    TWIPDeadlinedDate tWIPDeDatePrev = wipQTYList.get(i);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(tWIPDeDatePrev.getReportDate());
                    int shift         = tWIPDeDatePrev.getShiftId() != null ? tWIPDeDatePrev.getShiftId() : 1;
                    int shiftRemainQty = 0;

                    if (tWIPDeDatePrev.getWipOrder() != null && tWIPDeDatePrev.getWipOrder() == 245) {
                        // FG entry → เริ่มจาก shiftLast ของวันก่อน
                        shift = shiftLast;
                        calendar.add(Calendar.DATE, -1);
                    } else {
                        if (wipShiftQTYList.size() > 0) {
                            TWIPDeadlinedDate lastWipShift = wipShiftQTYList.get(wipShiftQTYList.size() - 1);
                            Calendar calendarLastWip = Calendar.getInstance();
                            calendarLastWip.setTime(lastWipShift.getReportDate());

                            if (calendarLastWip.before(calendar)
                                    || (calendarLastWip.equals(calendar)
                                        && tWIPDeDatePrev.getShiftId() != null
                                        && lastWipShift.getShiftId()   != null
                                        && tWIPDeDatePrev.getShiftId() > lastWipShift.getShiftId())) {
                                calendar = calendarLastWip;
                                shift    = lastWipShift.getShiftId() != null ? lastWipShift.getShiftId() : 1;
                                if (capacity.compareTo(new BigDecimal(
                                        lastWipShift.getDeadlineQty() != null ? lastWipShift.getDeadlineQty() : 0)) < 1) {
                                    if (lastWipShift.getShiftId() != null && lastWipShift.getShiftId() == 1) {
                                        shift = shiftLast;
                                        calendar.add(Calendar.DATE, -1);
                                    } else {
                                        shift = Math.abs(shift - shiftStep);
                                    }
                                } else {
                                    shiftRemainQty = lastWipShift.getDeadlineQty() != null
                                            ? lastWipShift.getDeadlineQty() : 0;
                                    wipShiftQTYList    .remove(wipShiftQTYList    .size() - 1);
                                    wipShiftQTYPrevList.remove(wipShiftQTYPrevList.size() - 1);
                                }
                            } else {
                                if (tWIPDeDatePrev.getShiftId() != null && tWIPDeDatePrev.getShiftId() == 1) {
                                    shift = shiftLast;
                                    calendar.add(Calendar.DATE, -1);
                                } else {
                                    shift = shift - shiftStep;
                                }
                            }
                        } else {
                            if (tWIPDeDatePrev.getShiftId() != null && tWIPDeDatePrev.getShiftId() == 1) {
                                shift = shiftLast;
                                calendar.add(Calendar.DATE, -1);
                            } else {
                                shift = shift - shiftStep;
                            }
                        }
                    }

                    // fill qty ลงแต่ละ shift จนหมด
                    int rawQty = (tWIPDeDatePrev.getDeadlineQty() != null ? tWIPDeDatePrev.getDeadlineQty() : 0)
                               + shiftRemainQty;
                    BigDecimal remainQTY = new BigDecimal(rawQty);
                    while (remainQTY.intValue() > 0) {
                        TWIPDeadlinedDate tWIPDeShift = new TWIPDeadlinedDate();
                        tWIPDeShift.setReportDate(calendar.getTime());
                        tWIPDeShift.setShiftId(shift);
                        if (capacity.compareTo(remainQTY) >= 0)
                            tWIPDeShift.setDeadlineQty(remainQTY.intValue());
                        else
                            tWIPDeShift.setDeadlineQty(capacity.intValue());
                        wipShiftQTYList.add(tWIPDeShift);
                        // copy สำหรับ carry-forward ไป WIP ถัดไป
                        TWIPDeadlinedDate target = new TWIPDeadlinedDate();
                        target.setReportDate(tWIPDeShift.getReportDate());
                        target.setShiftId(tWIPDeShift.getShiftId());
                        target.setDeadlineQty(tWIPDeShift.getDeadlineQty());
                        wipShiftQTYPrevList.add(target);

                        remainQTY = remainQTY.subtract(capacity);
                        shift -= shiftStep;
                        if (shift <= 0) {
                            shift = shiftLast;
                            calendar.add(Calendar.DATE, -1);
                        }
                    }
                }
            }

            // ── Fill missing days (30-day window) สำหรับ WIP stage นี้ ─────
            List<TWIPDeadlinedDate> dlList = new ArrayList<TWIPDeadlinedDate>();
            if (wipShiftQTYList.size() != countDetail) {
                Calendar stDate = new GregorianCalendar(Locale.US);
                stDate.setTime(startDate);
                for (int n = 0; n < 30; n++) {
                    for (int s = 1; s <= shiftLast; s += shiftStep) {
                        TWIPDeadlinedDate placeholder = new TWIPDeadlinedDate();
                        placeholder.setShiftId(s);
                        placeholder.setReportDate(stDate.getTime());
                        dlList.add(placeholder);
                        for (TWIPDeadlinedDate tWIPdl : wipShiftQTYList) {
                            Calendar rpDate = new GregorianCalendar(Locale.US);
                            rpDate.setTime(tWIPdl.getReportDate());
                            if (rpDate.get(Calendar.YEAR)        == stDate.get(Calendar.YEAR)
                                    && rpDate.get(Calendar.DAY_OF_YEAR) == stDate.get(Calendar.DAY_OF_YEAR)
                                    && placeholder.getShiftId().equals(tWIPdl.getShiftId())) {
                                dlList.remove(dlList.size() - 1);
                                dlList.add(tWIPdl);
                                break;
                            }
                        }
                    }
                    stDate.add(Calendar.DATE, 1);
                }
                // รวม qty ที่ตกอยู่ก่อน startDate เข้า index 0
                int remainPrev = 0;
                Calendar stDate1 = new GregorianCalendar(Locale.US);
                stDate1.setTime(startDate);
                for (TWIPDeadlinedDate tWIPdl : wipShiftQTYList) {
                    Calendar rpDate = new GregorianCalendar(Locale.US);
                    rpDate.setTime(tWIPdl.getReportDate());
                    if (tWIPdl.getDeadlineQty() != null) {
                        boolean beforeStart =
                                rpDate.get(Calendar.YEAR) < stDate1.get(Calendar.YEAR)
                                || (rpDate.get(Calendar.YEAR)        == stDate1.get(Calendar.YEAR)
                                    && rpDate.get(Calendar.DAY_OF_YEAR) < stDate1.get(Calendar.DAY_OF_YEAR));
                        if (beforeStart) remainPrev += tWIPdl.getDeadlineQty();
                    }
                }
                if (!dlList.isEmpty() && dlList.get(0).getDeadlineQty() != null) {
                    remainPrev += dlList.get(0).getDeadlineQty();
                }
                if (remainPrev != 0) dlList.get(0).setDeadlineQty(remainPrev);
            }

            // ── Calculate color for WIP ──────────────────────────────────────
            int     sumDailly  = 0;
            int     stepColor  = (shiftPerDay == 2) ? 1 : 2;
            int     lastDataQTY = 0;
            boolean isRed      = false;
            sumStock += (wip.getStock() != null ? wip.getStock() : 0);

            for (int t = dlList.size() - 1; t >= 0; t--) {
                if (dlList.get(t).getDeadlineQty() != null) { lastDataQTY = t; break; }
            }

            for (int a = 0; a < dlList.size(); a++) {
                TWIPDeadlinedDate detail = dlList.get(a);
                sumDailly += (detail.getDeadlineQty() == null ? 0 : detail.getDeadlineQty());
                if (shiftPerDay == 4 && a < dlList.size() - 1) {
                    a++;
                    detail = dlList.get(a);
                    sumDailly += (detail.getDeadlineQty() == null ? 0 : detail.getDeadlineQty());
                }
                if (sumDailly > sumStock && sumDailly != 0) {
                    if (shiftPerDay == 4 && (detail.getShiftId() != null) && (detail.getShiftId() % 2) == 0) {
                        a--;
                        detail = dlList.get(a);
                    }
                    // 1 = Red
                    detail.setColorId(1);
                    int d = a + 1;
                    while (d >= 0 && d <= lastDataQTY) { dlList.get(d).setColorId(1); d++; }
                    // 2 = Yellow (6 entries ก่อนหน้า)
                    for (int b = 0; b < 6; b++) {
                        a -= stepColor;
                        if (a >= 0) dlList.get(a).setColorId(2); else break;
                    }
                    // 3 = Green (ที่เหลือก่อนหน้า)
                    while (true) {
                        a -= stepColor;
                        if (a >= 0) dlList.get(a).setColorId(3); else break;
                    }
                    isRed = true;
                    break;
                }
            }
            if (!isRed) {
                if (lastDataQTY != 0 && capacity.compareTo(BigDecimal.ZERO) > 0) {
                    for (int c = 0; c <= lastDataQTY; c++) dlList.get(c).setColorId(3);
                } else if (!dlList.isEmpty() && dlList.get(0).getDeadlineQty() != null) {
                    dlList.get(0).setColorId(3);
                }
            }

            wip.setWipDeadlinedDateList(dlList);
            deadlineList.add(wip);

            // carry forward wipShiftQTYPrevList → wipQTYList ของ WIP ถัดไป
            if (wipShiftQTYPrevList.size() > 0) {
                wipQTYList = new ArrayList<TWIPDeadlinedDate>();
                for (int t = wipShiftQTYPrevList.size() - 1; t >= 0; t--) {
                    wipQTYList.add(wipShiftQTYPrevList.get(t));
                }
            }
        }

        return deadlineList;
    }

    // =========================================================================
    //  Utility
    // =========================================================================

    private static long elapsed(long startMs) {
        return System.currentTimeMillis() - startMs;
    }

    private static Date stripTime(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 0); c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);      c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }
}
