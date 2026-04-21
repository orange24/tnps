package th.co.nttdata.tki.batch.blogic;

import java.util.Date;
import java.util.TimerTask;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import th.co.nttdata.tki.batch.dao.FGStockBatchDao;
import th.co.nttdata.tki.batch.dao.FGStockBatchDao.ProgressCallback;

/**
 * FG Stock Batch Job (FNG_B01)
 *
 * Flow:
 *   [1/4] Query minDate + maxDate
 *   [2/4] Delete t_fg_stock >= minDate
 *   [3/4] Insert FG Stock day-by-day (sequential — each day depends on previous)
 *   [4/4] Update batch control SUCCESS/FAILED
 *
 * Performance note:
 *   - Single DB connection + single PreparedStatement reused for entire insert loop
 *     (avoids pool get/release and SQL Server re-parse on every date)
 *   - Cannot parallelize by date: each day reads t_fg_stock(yesterday)
 */
public class FGStockBatchJob extends TimerTask {

    private static final Logger log = Logger.getLogger(FGStockBatchJob.class);

    private static final String BATCH_CODE = "FNG_B01";
    private static final String BATCH_NAME = "FG Stock Calculate";

    private final DataSource dataSource;
    private final Date       executeDate;
    private final String     executedBy;

    public FGStockBatchJob(DataSource dataSource, Date executeDate, String executedBy) {
        this.dataSource  = dataSource;
        this.executeDate = executeDate != null ? executeDate : new Date();
        this.executedBy  = executedBy;
    }

    @Override
    public void run() {
        FGStockBatchDao dao = new FGStockBatchDao(dataSource);
        long startMs = System.currentTimeMillis();

        // ── Guard: ห้าม run ซ้ำ ──────────────────────────────────────────────
        if (dao.isBatchRunning(BATCH_CODE)) {
            log.warn("[FNG_B01] Already RUNNING — skipped");
            return;
        }

        dao.upsertBatchControl(BATCH_CODE, BATCH_NAME, 1, executedBy);
        log.info("[FNG_B01] Started (executeDate=" + executeDate + ", by=" + executedBy + ")");

        String  errorMsg = null;
        boolean success  = false;
        try {

            // ── [1/4] Query dates ────────────────────────────────────────────
            long t = System.currentTimeMillis();
            Date minDate = dao.queryMinDate(executeDate);
            Date maxDate = dao.queryMaxDate();
            log.info("[FNG_B01] [1/4] minDate=" + minDate + " maxDate=" + maxDate
                    + " (" + elapsed(t) + " ms)");

            // ── [2/4] Delete ─────────────────────────────────────────────────
            t = System.currentTimeMillis();
            dao.deleteFGStock(minDate);
            log.info("[FNG_B01] [2/4] deleteFGStock done (" + elapsed(t) + " ms)");

            // ── [3/4] Insert loop ────────────────────────────────────────────
            t = System.currentTimeMillis();
            log.info("[FNG_B01] [3/4] insertFGStock starting...");

            int totalDays = dao.insertFGStockLoop(minDate, maxDate, new ProgressCallback() {
                public void onDate(Date date, int done, int total) {
                    // log ทุก 10 วัน เพื่อไม่ให้ log ท่วม
                    if (done % 10 == 0 || done == total) {
                        log.info("[FNG_B01] [3/4] inserted [" + done + "/" + total + "] date=" + date);
                    }
                }
            });

            log.info("[FNG_B01] [3/4] insertFGStock done — " + totalDays
                    + " day(s) (" + elapsed(t) + " ms)");

            success = true;

        } catch (Exception e) {
            errorMsg = e.getMessage();
            log.error("[FNG_B01] Failed after " + elapsed(startMs) + " ms: " + errorMsg, e);
        }

        int finalStatus = success ? 0 : 2;
        dao.upsertBatchControl(BATCH_CODE, BATCH_NAME, finalStatus, executedBy,
                               success ? null : errorMsg);
        if (success) {
            log.info("[FNG_B01] [4/4] Batch Control set SUCCESS (0) — total " + elapsed(startMs) + " ms");
        } else {
            log.info("[FNG_B01] [4/4] Batch Control set FAILED (2)");
            throw new RuntimeException("[FNG_B01] Batch failed");
        }
    }

    private static long elapsed(long startMs) {
        return System.currentTimeMillis() - startMs;
    }
}
