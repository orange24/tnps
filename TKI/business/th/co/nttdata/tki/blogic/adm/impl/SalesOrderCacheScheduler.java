package th.co.nttdata.tki.blogic.adm.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import th.co.nttdata.tki.bean.Batch;
import th.co.nttdata.tki.blogic.adm.BAT_S01Logic;

/**
 * Scheduler for DAL_B02 - 19. Sales Order Detail Cache Refresh
 * Runs daily at 1:00 AM
 */
@Component
public class SalesOrderCacheScheduler {

	private static final Logger log = Logger.getLogger(SalesOrderCacheScheduler.class);

	@Autowired
	private BAT_S01Logic batLogic;

	/**
	 * Run DAL_B02 batch daily at 1:00 AM
	 * Cron: 0 0 1 * * ? = at 1:00:00 AM every day
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void refreshSalesOrderCache() {
		log.info("[Scheduler] Starting DAL_B02 - 19. Sales Order Detail Cache Refresh");

		try {
			Batch batch = new Batch();
			batch.setBatchCode("DAL_B02");
			batch.setBatchName("19. Sales Order Detail Cache Refresh");
			batch.setExecuteDate(new Date());
			batch.setUpdateBy("SYSTEM");

			batLogic.batchRun(batch);

			log.info("[Scheduler] DAL_B02 batch triggered successfully");
		} catch (Exception e) {
			log.error("[Scheduler] Failed to trigger DAL_B02 batch", e);
		}
	}
}
