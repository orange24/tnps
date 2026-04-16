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

import th.co.nttdata.tki.batch.bean.MMoldShot;
import th.co.nttdata.tki.batch.dao.MoldShotBatchDao;

/**
 * Mold Shot Batch Job (MLD_B01)
 *
 * Flow:
 *   [1/5] Query min dates (DC per mold, FG global)
 *   [2/5] DC: parallel per (moldId, moldNo) — delete history + insert per date
 *   [3/5] FG: delete history
 *   [4/5] FG: sequential insert per date
 *   [5/5] Update m_mold_detail FG stats (once)
 *
 * Performance note:
 *   - DC inserts use INSERT...SELECT (no round-trip per row)
 *   - updateMoldDetailFG called once at the end (equivalent to per-row calls in original
 *     since it always aggregates full t_mold_fg_history)
 */
public class MoldShotBatchJob extends TimerTask {

    private static final Logger log = Logger.getLogger(MoldShotBatchJob.class);

    private static final String BATCH_CODE   = "MLD_B01";
    private static final String BATCH_NAME   = "Mold Shot Calculate";
    private static final int    THREAD_COUNT = 4;

    private final DataSource dataSource;
    private final Date       executeDate;
    private final String     executedBy;

    public MoldShotBatchJob(DataSource dataSource, Date executeDate, String executedBy) {
        this.dataSource  = dataSource;
        this.executeDate = executeDate != null ? executeDate : new Date();
        this.executedBy  = executedBy;
    }

    @Override
    public void run() {
        MoldShotBatchDao dao = new MoldShotBatchDao(dataSource);
        long startMs = System.currentTimeMillis();

        // ── Guard: ห้าม run ซ้ำ ──────────────────────────────────────────────
        if (dao.isBatchRunning(BATCH_CODE)) {
            log.warn("[MLD_B01] Already RUNNING — skipped");
            return;
        }

        dao.upsertBatchControl(BATCH_CODE, BATCH_NAME, 1, executedBy);
        log.info("[MLD_B01] Started (executeDate=" + executeDate + ", by=" + executedBy + ")");

        ExecutorService exec = Executors.newFixedThreadPool(THREAD_COUNT);
        boolean success = false;
        try {

            // ── [1/5] Query min dates ────────────────────────────────────────
            long t = System.currentTimeMillis();
            final List<MMoldShot> dcMolds = dao.queryMinDatesDC(executeDate);
            final Date minDateFG          = dao.queryMinDateFG(executeDate);
            log.info("[MLD_B01] [1/5] queryMinDates done — DC molds=" + dcMolds.size()
                    + " FG minDate=" + minDateFG + " (" + elapsed(t) + " ms)");

            // ── [2/5] DC: parallel per mold ──────────────────────────────────
            t = System.currentTimeMillis();
            if (!dcMolds.isEmpty()) {
                runParallelDC(exec, dcMolds, dao);
                log.info("[MLD_B01] [2/5] DC inserts done (" + elapsed(t) + " ms)");
            } else {
                log.info("[MLD_B01] [2/5] DC — no molds to process, skipped");
            }

            // ── [3/5] FG: delete history ─────────────────────────────────────
            t = System.currentTimeMillis();
            if (minDateFG != null) {
                dao.deleteFGHistory(minDateFG);
                log.info("[MLD_B01] [3/5] deleteFGHistory done (" + elapsed(t) + " ms)");
            } else {
                log.info("[MLD_B01] [3/5] FG — no updated fg_detail, skipped");
            }

            // ── [4/5] FG: insert per date (sequential) ───────────────────────
            t = System.currentTimeMillis();
            if (minDateFG != null) {
                List<Date> fgDates = buildDateList(minDateFG);
                log.info("[MLD_B01] [4/5] FG insertHistory — " + fgDates.size() + " date(s)");
                for (Date date : fgDates) {
                    dao.insertFGHistoryForDate(date);
                    log.info("[MLD_B01] [4/5] FG inserted date=" + date);
                }
                log.info("[MLD_B01] [4/5] FG insertHistory done (" + elapsed(t) + " ms)");
            } else {
                log.info("[MLD_B01] [4/5] FG insertHistory skipped");
            }

            // ── [5/5] Update m_mold_detail FG stats (once) ───────────────────
            t = System.currentTimeMillis();
            dao.updateMoldDetailFG();
            log.info("[MLD_B01] [5/5] updateMoldDetailFG done (" + elapsed(t) + " ms)");

            success = true;

        } catch (Exception e) {
            log.error("[MLD_B01] Failed after " + elapsed(startMs) + " ms: " + e.getMessage(), e);
        } finally {
            exec.shutdown();
        }

        int finalStatus = success ? 0 : 2;
        dao.upsertBatchControl(BATCH_CODE, BATCH_NAME, finalStatus, executedBy);
        if (success) {
            log.info("[MLD_B01] Batch Control set SUCCESS (0) — total " + elapsed(startMs) + " ms");
        } else {
            log.info("[MLD_B01] Batch Control set FAILED (2)");
            throw new RuntimeException("[MLD_B01] Batch failed");
        }
    }

    /**
     * แบ่ง dcMolds เป็น THREAD_COUNT partitions แล้วแต่ละ thread process molds ที่ได้รับ:
     *   1. deleteDCHistory (moldId, moldNo, minDate)
     *   2. loop dates จาก minDate ถึงวันนี้ → insertDCHistoryForDate
     */
    private void runParallelDC(ExecutorService exec,
                               final List<MMoldShot> dcMolds,
                               final MoldShotBatchDao dao) throws Exception {
        final Date today = endOfDay(new Date());

        // partition by index
        List<List<MMoldShot>> partitions = new ArrayList<List<MMoldShot>>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            partitions.add(new ArrayList<MMoldShot>());
        }
        for (int i = 0; i < dcMolds.size(); i++) {
            partitions.get(i % THREAD_COUNT).add(dcMolds.get(i));
        }

        List<Future<Void>> futures = new ArrayList<Future<Void>>();
        for (int ti = 0; ti < THREAD_COUNT; ti++) {
            final List<MMoldShot> partition = partitions.get(ti);
            final int threadNo = ti + 1;
            if (partition.isEmpty()) continue;

            futures.add(exec.submit(new Callable<Void>() {
                public Void call() throws Exception {
                    log.info("[MLD_B01] thread-" + threadNo + " DC start — "
                            + partition.size() + " mold(s)");
                    for (MMoldShot mold : partition) {
                        int moldId    = mold.getMoldId();
                        String moldNo = mold.getMoldNo();
                        Date minDate  = mold.getReportDate();

                        dao.deleteDCHistory(moldId, moldNo, minDate);

                        List<Date> dates = buildDateListUntil(minDate, today);
                        for (Date date : dates) {
                            dao.insertDCHistoryForDate(moldId, moldNo, date);
                        }
                        log.info("[MLD_B01] thread-" + threadNo
                                + " moldId=" + moldId + " moldNo=" + moldNo
                                + " DONE — " + dates.size() + " date(s)");
                    }
                    log.info("[MLD_B01] thread-" + threadNo + " DC finished");
                    return null;
                }
            }));
        }

        for (Future<Void> f : futures) {
            f.get();
        }
    }

    /** สร้าง list วันจาก minDate ถึงวันนี้ (inclusive) */
    private static List<Date> buildDateList(Date minDate) {
        return buildDateListUntil(minDate, endOfDay(new Date()));
    }

    private static List<Date> buildDateListUntil(Date minDate, Date maxDate) {
        List<Date> dates = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(minDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        while (!cal.getTime().after(maxDate)) {
            dates.add(cal.getTime());
            cal.add(Calendar.DATE, 1);
        }
        return dates;
    }

    private static Date endOfDay(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    private static long elapsed(long startMs) {
        return System.currentTimeMillis() - startMs;
    }
}
