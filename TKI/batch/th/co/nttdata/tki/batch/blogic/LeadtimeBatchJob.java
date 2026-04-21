package th.co.nttdata.tki.batch.blogic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import th.co.nttdata.tki.batch.bean.MLeadtime;
import th.co.nttdata.tki.batch.dao.LeadtimeBatchDao;

/**
 * Leadtime Batch Job (LDT_B01)
 *
 * Flow:
 *   [1/3] queryLeadtime — ดึง m_leadtime ทุก row ที่มี stFromDate4/5/6
 *   [2/3] parallel: คำนวณ stResult4/5/6 ต่อ row (แต่ละ row อิสระ)
 *   [3/3] batchUpdateLeadtime — UPDATE ทุก row ในครั้งเดียว
 *
 * Performance note:
 *   - แต่ละ row ทำ 4 queries × 3 slots = ถึง 12 DB calls → parallel ช่วยมาก
 *   - UPDATE ทำเป็น executeBatch() ครั้งเดียวหลัง parallel เสร็จ
 */
public class LeadtimeBatchJob extends TimerTask {

    private static final Logger log = Logger.getLogger(LeadtimeBatchJob.class);

    private static final String BATCH_CODE   = "LDT_B01";
    private static final String BATCH_NAME   = "Leadtime Calculate";
    private static final int    THREAD_COUNT = 4;

    private final DataSource dataSource;
    private final String     executedBy;

    public LeadtimeBatchJob(DataSource dataSource, String executedBy) {
        this.dataSource  = dataSource;
        this.executedBy  = executedBy;
    }

    @Override
    public void run() {
        LeadtimeBatchDao dao = new LeadtimeBatchDao(dataSource);
        long startMs = System.currentTimeMillis();

        // ── Guard: ห้าม run ซ้ำ ──────────────────────────────────────────────
        if (dao.isBatchRunning(BATCH_CODE)) {
            log.warn("[LDT_B01] Already RUNNING — skipped");
            return;
        }

        dao.upsertBatchControl(BATCH_CODE, BATCH_NAME, 1, executedBy);
        log.info("[LDT_B01] Started (by=" + executedBy + ")");

        ExecutorService exec = Executors.newFixedThreadPool(THREAD_COUNT);
        String  errorMsg = null;
        boolean success  = false;
        try {

            // ── [1/3] Query leadtime list ────────────────────────────────────
            long t = System.currentTimeMillis();
            List<MLeadtime> allRows = dao.queryLeadtime();
            log.info("[LDT_B01] [1/3] queryLeadtime=" + allRows.size()
                    + " rows (" + elapsed(t) + " ms)");

            if (allRows.isEmpty()) {
                log.info("[LDT_B01] No rows to process");
                success = true;
            } else {

                // ── [2/3] Parallel: คำนวณ stResult4/5/6 ต่อ row ────────────
                t = System.currentTimeMillis();
                List<MLeadtime> processed = calcParallel(exec, allRows, dao);
                log.info("[LDT_B01] [2/3] calcAvg done — "
                        + processed.size() + " rows (" + elapsed(t) + " ms)");

                // ── [3/3] Batch UPDATE ───────────────────────────────────────
                t = System.currentTimeMillis();
                dao.batchUpdateLeadtime(processed);
                log.info("[LDT_B01] [3/3] batchUpdate done (" + elapsed(t) + " ms)");

                success = true;
            }

        } catch (Exception e) {
            errorMsg = e.getMessage();
            log.error("[LDT_B01] Failed after " + elapsed(startMs) + " ms: " + errorMsg, e);
        } finally {
            exec.shutdown();
        }

        int finalStatus = success ? 0 : 2;
        dao.upsertBatchControl(BATCH_CODE, BATCH_NAME, finalStatus, executedBy,
                               success ? null : errorMsg);
        if (success) {
            log.info("[LDT_B01] Batch Control set SUCCESS (0) — total " + elapsed(startMs) + " ms");
        } else {
            log.info("[LDT_B01] Batch Control set FAILED (2)");
            throw new RuntimeException("[LDT_B01] Batch failed");
        }
    }

    // =========================================================================
    //  Parallel calculation
    // =========================================================================

    /**
     * แบ่ง allRows เป็น THREAD_COUNT partitions แล้ว submit Callable ต่อ partition
     * แต่ละ thread คำนวณ stResult4/5/6 ต่อ MLeadtime ที่ได้รับ
     */
    private List<MLeadtime> calcParallel(ExecutorService exec,
                                         final List<MLeadtime> allRows,
                                         final LeadtimeBatchDao dao) throws Exception {
        // partition by index
        List<List<MLeadtime>> partitions = new ArrayList<List<MLeadtime>>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            partitions.add(new ArrayList<MLeadtime>());
        }
        for (int i = 0; i < allRows.size(); i++) {
            partitions.get(i % THREAD_COUNT).add(allRows.get(i));
        }

        List<Future<List<MLeadtime>>> futures = new ArrayList<Future<List<MLeadtime>>>();
        for (int ti = 0; ti < THREAD_COUNT; ti++) {
            final List<MLeadtime> partition = partitions.get(ti);
            final int threadNo = ti + 1;
            if (partition.isEmpty()) continue;

            futures.add(exec.submit(new Callable<List<MLeadtime>>() {
                public List<MLeadtime> call() throws Exception {
                    log.info("[LDT_B01] thread-" + threadNo + " start — "
                            + partition.size() + " rows");
                    for (MLeadtime lt : partition) {
                        calcRow(lt, dao);
                    }
                    log.info("[LDT_B01] thread-" + threadNo + " finished");
                    return partition;
                }
            }));
        }

        List<MLeadtime> result = new ArrayList<MLeadtime>();
        for (Future<List<MLeadtime>> f : futures) {
            result.addAll(f.get());
        }
        return result;
    }

    /**
     * คำนวณ stResult4, stResult5, stResult6 สำหรับ 1 MLeadtime row
     */
    private static void calcRow(MLeadtime lt, LeadtimeBatchDao dao) throws Exception {
        if (lt.getStFromDate4() != null && lt.getStToDate4() != null) {
            lt.setStResult4(calAvgResult(dao, lt.getWip(), lt.getPartId(),
                    lt.getStFromDate4(), lt.getStToDate4()));
        }
        if (lt.getStFromDate5() != null && lt.getStToDate5() != null) {
            lt.setStResult5(calAvgResult(dao, lt.getWip(), lt.getPartId(),
                    lt.getStFromDate5(), lt.getStToDate5()));
        }
        if (lt.getStFromDate6() != null && lt.getStToDate6() != null) {
            lt.setStResult6(calAvgResult(dao, lt.getWip(), lt.getPartId(),
                    lt.getStFromDate6(), lt.getStToDate6()));
        }
    }

    /**
     * คำนวณค่าเฉลี่ย leadtime สำหรับ wip+partId ในช่วง fromDate–toDate
     * Logic เหมือน LeadtimeLogicImpl.calAvgResult() ทุกประการ
     */
    private static BigDecimal calAvgResult(LeadtimeBatchDao dao,
                                           String wip, int partId,
                                           Date fromDate, Date toDate) throws Exception {
        // 1. avg จาก MCWK + WK
        List<BigDecimal> avg = dao.queryAvg(wip, partId, fromDate, toDate);

        // 2. total ok qty จาก MC
        int qtyAll = dao.queryDaily(wip, partId, fromDate, toDate);

        // 3. count MC records
        int size = dao.queryDailySize(wip, partId, fromDate, toDate);

        // 4. stop time (seconds)
        int stopMinute = dao.queryStopMC(wip, partId, fromDate, toDate);

        // 5. actual time = (size * 12h * 3600s) - stop_seconds
        BigDecimal time = new BigDecimal((long) size * 12 * 60 * 60 - stopMinute);

        if (qtyAll == 0) {
            avg.add(BigDecimal.ZERO);
        } else {
            avg.add(time.divide(new BigDecimal(qtyAll), 5, RoundingMode.HALF_UP));
        }

        // 6. ลบ zeros (preserve original loop logic)
        for (int a = 0, max = avg.size(); a < max; a++) {
            if (avg.get(a).compareTo(BigDecimal.ZERO) == 0) {
                avg.remove(a);
                max--;
                a--;
            }
        }

        // 7. เฉลี่ย
        BigDecimal avgSum = BigDecimal.ZERO;
        for (BigDecimal av : avg) {
            avgSum = avgSum.add(av);
        }
        if (avgSum.compareTo(BigDecimal.ZERO) > 0 && avg.size() > 0) {
            return avgSum.divide(new BigDecimal(avg.size()), 2, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }

    private static long elapsed(long startMs) {
        return System.currentTimeMillis() - startMs;
    }
}
