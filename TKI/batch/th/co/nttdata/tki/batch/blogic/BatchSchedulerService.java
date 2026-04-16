package th.co.nttdata.tki.batch.blogic;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Timer;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BatchSchedulerService {

    private static final Logger log = Logger.getLogger(BatchSchedulerService.class);

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    /**
     * รัน WIP Stock batch ทุกวัน 5 ทุ่ม (23:00)
     *
     * @Scheduled ต้องเป็น void และไม่มี parameter
     * WipStockBatchJob รับ startDate = วันนี้ → process ถึงวันนี้ (1 วัน)
     * ถ้าต้องการ run ย้อนหลัง ให้เรียก runManual(startDate) แทน
     */
    //@Scheduled(fixedRate = 5000)
    @Scheduled(cron = "0 0 23 * * *")
    public void runScheduled() {
        log.info("[Scheduler] WIP Stock batch triggered at 23:00");
        runInternal(new Date());
    }

    /**
     * สำหรับ manual trigger จาก UI หรือ admin
     * @param startDate วันแรกที่ต้องการ recalculate (process ต่อเนื่องถึงวันนี้)
     */
    public void runManual(Date startDate) {
        log.info("[Scheduler] WIP Stock batch triggered manually, startDate=" + startDate);
        runInternal(startDate);
    }

    private void runInternal(Date startDate) {
//    	System.out.println("งานทำงานตอน: " + LocalDateTime.now());
        // ใช้ Timer daemon thread เพื่อไม่ block scheduler thread ของ Spring
        Timer timer = new Timer("wip-stock-batch", true);
        timer.schedule(new WipStockBatchJob(dataSource, startDate, "SYSTEM"), 0L);
    }
}
