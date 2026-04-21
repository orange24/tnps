package th.co.nttdata.tki.batch.blogic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import th.co.nttdata.tki.batch.dao.DailySummaryBatchDao;

/**
 * Daily Summary Batch Job (DAL_B01)
 * - query minDate จากข้อมูลที่ update ล่าสุด
 * - delete t_daily_summary >= minDate
 * - parallel insert Day+Night summary + MERGE pending ต่อแต่ละวัน
 */
public class DailySummaryBatchJob extends TimerTask {

    private static final Logger log = Logger.getLogger(DailySummaryBatchJob.class);

    private static final String BATCH_CODE  = "DAL_B01";
    private static final String BATCH_NAME  = "Daily Actual and Pending Summary";
    private static final int    THREAD_COUNT = 4;

    private final DataSource dataSource;
    private final Date       executeDate;
    private final String     executedBy;

    public DailySummaryBatchJob(DataSource dataSource, Date executeDate, String executedBy) {
        this.dataSource  = dataSource;
        this.executeDate = executeDate != null ? executeDate : new Date();
        this.executedBy  = executedBy;
    }

    @Override
    public void run() {
        DailySummaryBatchDao dao = new DailySummaryBatchDao(dataSource);
        long startMs = System.currentTimeMillis();

        // ── Guard: ห้าม run ซ้ำ ──────────────────────────────────────────────
        if (dao.isBatchRunning(BATCH_CODE)) {
            log.warn("[DAL_B01] Already RUNNING — skipped");
            return;
        }

        dao.upsertBatchControl(BATCH_CODE, BATCH_NAME, 1, executedBy);
        log.info("[DAL_B01] Started (executeDate=" + executeDate + ", by=" + executedBy + ")");

        ExecutorService exec = Executors.newFixedThreadPool(THREAD_COUNT);
        String  errorMsg = null;
        boolean success  = false;
        try {
            // ── [1/4] Query minDate ──────────────────────────────────────────
            long t = System.currentTimeMillis();
            Date minDate = dao.queryMinDate(executeDate);
            log.info("[DAL_B01] [1/4] queryMinDate=" + minDate + " (" + elapsed(t) + " ms)");

            // ── [2/4] Delete t_daily_summary >= minDate ──────────────────────
            t = System.currentTimeMillis();
            dao.deleteDailySummary(minDate);
            log.info("[DAL_B01] [2/4] deleteDailySummary done (" + elapsed(t) + " ms)");

            // ── Build date list from minDate to today ────────────────────────
            List<Date> dates = buildDateList(minDate);
            log.info("[DAL_B01] Processing " + dates.size() + " day(s) with " + THREAD_COUNT + " threads");

            // ── [3/4] Parallel: insert t_daily_summary (Day + Night) ─────────
            t = System.currentTimeMillis();
            runParallel(exec, dates, dao, true);
            log.info("[DAL_B01] [3/4] insertDailySummary done (" + elapsed(t) + " ms)");

            // ── [4/4] Parallel: MERGE t_pending (MC + WK + MCWK) ────────────
            t = System.currentTimeMillis();
            runParallel(exec, dates, dao, false);
            log.info("[DAL_B01] [4/4] mergePending done (" + elapsed(t) + " ms)");

            success = true;
        } catch (Exception e) {
            errorMsg = e.getMessage();
            log.error("[DAL_B01] Failed after " + elapsed(startMs) + " ms: " + errorMsg, e);
        } finally {
            exec.shutdown();
        }

        int finalStatus = success ? 0 : 2;
        dao.upsertBatchControl(BATCH_CODE, BATCH_NAME, finalStatus, executedBy,
                               success ? null : errorMsg);
        if (success) {
            log.info("[DAL_B01] Batch Control set SUCCESS (0) — total " + elapsed(startMs) + " ms");
        } else {
            log.info("[DAL_B01] Batch Control set FAILED (2)");
            throw new RuntimeException("[DAL_B01] Batch failed");
        }
    }

    /**
     * แบ่ง dates เป็น THREAD_COUNT partitions แล้วส่ง parallel
     * isSummary=true  → insert Day+Night summary
     * isSummary=false → MERGE pending MC+WK+MCWK
     */
    private void runParallel(ExecutorService exec, final List<Date> dates,
                             final DailySummaryBatchDao dao, final boolean isSummary)
            throws Exception {

        // partition by index
        List<List<Date>> partitions = new ArrayList<List<Date>>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            partitions.add(new ArrayList<Date>());
        }
        for (int i = 0; i < dates.size(); i++) {
            partitions.get(i % THREAD_COUNT).add(dates.get(i));
        }

        List<Future<Void>> futures = new ArrayList<Future<Void>>();
        for (int t = 0; t < THREAD_COUNT; t++) {
            final List<Date> partition = partitions.get(t);
            final int threadNo = t + 1;
            if (partition.isEmpty()) continue;

            futures.add(exec.submit(new Callable<Void>() {
                public Void call() throws Exception {
                    String phase = isSummary ? "summary" : "pending";
                    log.info("[DAL_B01] thread-" + threadNo + " [" + phase + "] start — "
                            + partition.size() + " dates");
                    for (Date date : partition) {
                        if (isSummary) {
                            dao.insertDailySummaryDay(date);
                            dao.insertDailySummaryNight(date);
                        } else {
                            dao.mergePendingMC(date);
                            dao.mergePendingWK(date);
                            dao.mergePendingMCWK(date);
                        }
                        log.info("[DAL_B01] thread-" + threadNo + " [" + phase + "] done date=" + date);
                    }
                    log.info("[DAL_B01] thread-" + threadNo + " [" + phase + "] finished");
                    return null;
                }
            }));
        }

        for (Future<Void> f : futures) {
            f.get(); // rethrow any exception from worker threads
        }
    }

    /** สร้าง list ของวันตั้งแต่ minDate จนถึงวันนี้ (inclusive) */
    private static List<Date> buildDateList(Date minDate) {
        List<Date> dates = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(minDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 23);
        today.set(Calendar.MINUTE, 59);
        today.set(Calendar.SECOND, 59);

        while (!cal.getTime().after(today.getTime())) {
            dates.add(cal.getTime());
            cal.add(Calendar.DATE, 1);
        }
        return dates;
    }

    private static long elapsed(long startMs) {
        return System.currentTimeMillis() - startMs;
    }
}
