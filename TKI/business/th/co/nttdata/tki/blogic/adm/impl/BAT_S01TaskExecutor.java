package th.co.nttdata.tki.blogic.adm.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import th.co.nttdata.batch.BatchLogic;

@Component
public class BAT_S01TaskExecutor {
//	@Autowired
//	@Qualifier("LOT_B01")
//	private BatchLogic LOT_B01;
//	@Async
//	public void runLOT_B01() throws Exception{
//		System.out.println(" Starting batch...");
//		try {
//			Thread.sleep(5000);
//			LOT_B01.executeBatch();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//			throw e;
//		}
//		System.out.println(" Batch Complete.");
//	}
//	
//	@Autowired
//	@Qualifier("FGStock")
//	private BatchLogic FGStock;
//	@Async
//	public void runFNG_B01() throws Exception{	
//		System.out.println(" Starting batch...");
//		try {
//			Thread.sleep(5000);
//			FGStock.executeBatch();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//			throw e;
//		}
//		System.out.println(" Batch Complete.");
//	}
//	
//	@Autowired
//	@Qualifier("DaillySummary")
//	private BatchLogic DaillySummary;
//	@Async
//	public void runDAL_B01() throws Exception{		
//		System.out.println(" Starting batch...");
//		try {
//			Thread.sleep(5000);
//			DaillySummary.executeBatch();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//			throw e;
//		}
//		System.out.println(" Batch Complete.");
//	}
//	
//	@Autowired
//	@Qualifier("WIPStock")
//	private BatchLogic WIPStock;
//	@Async
//	public void runWIP_B01() throws Exception{		
//		System.out.println(" Starting batch...");
//		try {
//			Thread.sleep(5000);
//			WIPStock.executeBatch();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//			throw e;
//		}
//		System.out.println(" Batch Complete.");
//	}
//
//	@Autowired
//	@Qualifier("MoldShot")
//	private BatchLogic MoldShot;
//	@Async
//	public void runMLD_B01() throws Exception{	
//		System.out.println(" Starting batch...");
//		try {
//			Thread.sleep(5000);
//			MoldShot.executeBatch();
//			
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//			throw e;
//		}
//		System.out.println(" Batch Complete.");
//	}
//	
//	@Autowired
//	@Qualifier("Leadtime")
//	private BatchLogic Leadtime;
//	@Async
//	public void runLDT_B01() throws Exception{
//		System.out.println(" Starting batch...");
//		try {
//			Thread.sleep(5000);
//			Leadtime.executeBatch();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//			throw e;
//		}
//		System.out.println(" Batch Complete.");
//	}
//	
//	@Async
//	public void runWIP_B02() throws Exception{
//	
//		System.out.println(" Starting batch...");
//		try {
//			Thread.sleep(5000);
//
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//			throw e;
//		}
//		System.out.println(" Batch Complete.");
//	}
}
