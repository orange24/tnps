package th.co.nttdata.tki.batch.blogic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Timer;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.batch.dao.BatchScheduleDao;

/**
 * DB-driven Batch Scheduler
 *
 * ทำงานทุก 1 นาที แล้ว query m_batch_schedule เพื่อดูว่ามี batch ไหน
 * ที่ตั้งเวลาไว้ตรงกับเวลาปัจจุบัน (รูปแบบ "HH:mm") และ enabled=1
 *
 * DDL (รัน 1 ครั้ง):
 * <pre>
 * CREATE TABLE m_batch_schedule (
 *     batchcode    VARCHAR(20)  NOT NULL PRIMARY KEY,
 *     scheduletime VARCHAR(5)   NOT NULL,   -- "HH:mm" เช่น "23:00"
 *     enabled      BIT          NOT NULL DEFAULT 1,
 *     description  NVARCHAR(100)    NULL
 * );
 * -- ตัวอย่าง:
 * INSERT INTO m_batch_schedule VALUES ('WIP_B01', '23:00', 1, N'WIP Stock daily');
 * INSERT INTO m_batch_schedule VALUES ('WIP_B02', '23:30', 1, N'WIP Deadline daily');
 * </pre>
 */
@Service
public class BatchSchedulerService {

    private static final Logger log = Logger.getLogger(BatchSchedulerService.class);

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Autowired
    @Qualifier("settings")
    private Properties settings;

    /**
     * ทำงานทุกนาที — ตรวจว่ามี batch ที่ต้องรัน ณ เวลานี้หรือไม่
     */
    @Scheduled(cron = "0 * * * * *")
    public void runScheduled() {
        String currentTime = new SimpleDateFormat("HH:mm").format(new Date());
        BatchScheduleDao dao = new BatchScheduleDao(dataSource);
        List<String> batchCodes = dao.queryScheduledBatches(currentTime);

        if (batchCodes.isEmpty()) return;

        log.info("[Scheduler] time=" + currentTime + " — found " + batchCodes.size() + " batch(es)");
        for (String batchCode : batchCodes) {
            try {
                runBatch(batchCode, new Date(), "SYSTEM");
            } catch (Exception e) {
                log.error("[Scheduler] Failed to start batchCode=" + batchCode + ": " + e.getMessage(), e);
            }
        }
    }

    /**
     * รัน batch ตาม batchCode ที่ระบุ (ใช้ได้ทั้งจาก scheduler และ manual trigger)
     *
     * @param batchCode  รหัส batch เช่น "WIP_B01", "FNG_B01"
     * @param executeDate วันที่ประมวลผล (ปกติ = วันนี้)
     * @param runBy      ชื่อ user หรือ "SYSTEM"
     */
    public void runBatch(String batchCode, Date executeDate, String runBy) {
        if ("FNG_B01".equals(batchCode)) {
            Timer timer = new Timer("fng-stock-batch", true);
            timer.schedule(new FGStockBatchJob(dataSource, executeDate, runBy), 0L);

        } else if ("LDT_B01".equals(batchCode)) {
            Timer timer = new Timer("ldt-leadtime-batch", true);
            timer.schedule(new LeadtimeBatchJob(dataSource, runBy), 0L);

        } else if ("MLD_B01".equals(batchCode)) {
            Timer timer = new Timer("mld-shot-batch", true);
            timer.schedule(new MoldShotBatchJob(dataSource, executeDate, runBy), 0L);

        } else if ("DAL_B01".equals(batchCode)) {
            Timer timer = new Timer("dal-summary-batch", true);
            timer.schedule(new DailySummaryBatchJob(dataSource, executeDate, runBy), 0L);

        } else if ("WIP_B01".equals(batchCode)) {
            Timer timer = new Timer("wip-stock-batch", true);
            timer.schedule(new WipStockBatchJob(dataSource, executeDate, runBy), 0L);

        } else if ("WIP_B02".equals(batchCode)) {
            Timer timer = new Timer("wip-deadline-batch", true);
            timer.schedule(new WipDeadlineBatchJob(dataSource, executeDate, runBy, settings), 0L);

        } else {
            log.warn("[Scheduler] Unknown batchCode=" + batchCode + " — skipped");
        }
    }
}
