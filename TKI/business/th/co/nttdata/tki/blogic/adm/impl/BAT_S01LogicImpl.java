package th.co.nttdata.tki.blogic.adm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import th.co.nttdata.batch.BatchLogic;
import th.co.nttdata.batch.BatchRunner;
import th.co.nttdata.tki.bean.Batch;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.adm.BAT_S01Logic;
import th.co.nttdata.tki.dao.impl.BatchDao;

@Service
public class BAT_S01LogicImpl extends AbstractBaseLogic implements BAT_S01Logic {

	@Autowired
	private BatchDao batchDao;

	@Override
	public Batch getBatch(Batch batch) {
		return batchDao.getBatch(batch);
	}

	@Override
	public List<Batch> getBatchList() {
		return batchDao.getBatchList(null);
	}

	@Autowired
	@Qualifier("Leadtime")
	private BatchLogic Leadtime;
	@Autowired
	@Qualifier("LOT_B01")
	private BatchLogic LOT_B01;
	@Autowired
	@Qualifier("FGStock")
	private BatchLogic FGStock;
	@Autowired
	@Qualifier("DaillySummary")
	private BatchLogic DaillySummary;
	@Autowired
	@Qualifier("WIPStock")
	private BatchLogic WIPStock;
	@Autowired
	@Qualifier("MoldShot")
	private BatchLogic MoldShot;
	@Autowired
	@Qualifier("Deadline")
	private BatchLogic Deadline;
	@Autowired
	@Qualifier("TPC_B01")
	private BatchLogic TPC_B01;
	@Autowired
	@Qualifier("TPC_B02")
	private BatchLogic TPC_B02;
	@Autowired
	@Qualifier("TPC_B03")
	private BatchLogic TPC_B03;
	@Autowired
	@Qualifier("TPC_B04")
	private BatchLogic TPC_B04;
	@Autowired
	@Qualifier("TPC_B05")
	private BatchLogic TPC_B05;
	@Autowired
	@Qualifier("TPC_B06")
	private BatchLogic TPC_B06;
	@Autowired
	@Qualifier("TPC_B07")
	private BatchLogic TPC_B07;
	@Autowired
	@Qualifier("TPC_B08")
	private BatchLogic TPC_B08;
	@Autowired
	@Qualifier("TPC_B09")
	private BatchLogic TPC_B09;
	@Autowired
	@Qualifier("NIR_B01")
	private BatchLogic NIR_B01;
	@Autowired
	@Qualifier("NIR_B02")
	private BatchLogic NIR_B02;
	@Autowired
	@Qualifier("RES_ADJ")
	private BatchLogic RES_ADJ;
	

	@Async
	public void batchRun(Batch batch) {
		try {
			if ("FNG_B01".equals(batch.getBatchCode())) {
				BatchRunner.execute(FGStock, batch.getUpdateBy(), batch.getExecuteDate());
			} else if ("LDT_B01".equals(batch.getBatchCode())) {
				BatchRunner.execute(Leadtime, batch.getUpdateBy(), batch.getExecuteDate());
			} else if ("LOT_B01".equals(batch.getBatchCode())) {
				BatchRunner.execute(LOT_B01, batch.getUpdateBy(), batch.getExecuteDate());
			} else if ("MLD_B01".equals(batch.getBatchCode())) {
				BatchRunner.execute(MoldShot, batch.getUpdateBy(), batch.getExecuteDate());
			} else if ("DAL_B01".equals(batch.getBatchCode())) {
				BatchRunner.execute(DaillySummary, batch.getUpdateBy(), batch.getExecuteDate());
			} else if ("WIP_B01".equals(batch.getBatchCode())) {
				BatchRunner.execute(WIPStock, batch.getUpdateBy(), batch.getExecuteDate());
			} else if ("WIP_B02".equals(batch.getBatchCode())) {
				BatchRunner.execute(Deadline, batch.getUpdateBy(), batch.getExecuteDate());
			} else if ("TPC_B01".equals(batch.getBatchCode())) {
				BatchRunner.execute(TPC_B01, batch.getUpdateBy(), batch.getExecuteDate());
			} else if ("TPC_B02".equals(batch.getBatchCode())) {
				BatchRunner.execute(TPC_B02, batch.getUpdateBy(), batch.getExecuteDate());
			} else if ("TPC_B03".equals(batch.getBatchCode())) {
				BatchRunner.execute(TPC_B03, batch.getUpdateBy(), batch.getExecuteDate());
			} else if ("TPC_B04".equals(batch.getBatchCode())) {
				BatchRunner.execute(TPC_B04, batch.getUpdateBy(), batch.getExecuteDate());
			} else if ("TPC_B05".equals(batch.getBatchCode())) {
				BatchRunner.execute(TPC_B05, batch.getUpdateBy(), batch.getExecuteDate());
			} else if ("TPC_B06".equals(batch.getBatchCode())) {
				BatchRunner.execute(TPC_B06, batch.getUpdateBy(), batch.getExecuteDate());
			} else if ("TPC_B07".equals(batch.getBatchCode())) {
				BatchRunner.execute(TPC_B07, batch.getUpdateBy(), batch.getExecuteDate());
			} else if ("TPC_B08".equals(batch.getBatchCode())) {
				BatchRunner.execute(TPC_B08, batch.getUpdateBy(), batch.getExecuteDate());
			} else if ("TPC_B09".equals(batch.getBatchCode())) {
				BatchRunner.execute(TPC_B09, batch.getUpdateBy(), batch.getExecuteDate());
			} else if ("NIR_B01".equals(batch.getBatchCode())) {
				BatchRunner.execute(NIR_B01, batch.getUpdateBy(), batch.getExecuteDate());
			} else if ("NIR_B02".equals(batch.getBatchCode())) {
				BatchRunner.execute(NIR_B02, batch.getUpdateBy(), batch.getExecuteDate());
			} else if ("RES_ADJ".equals(batch.getBatchCode())) {
				BatchRunner.execute(RES_ADJ, batch.getUpdateBy(), batch.getExecuteDate());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Batch> getBatchStatus() {
		return batchDao.getBatchStatus();
	}

	@Override
	public void updateBatchControl(Batch batch) {
		batchDao.updateBatchControl(batch);

	}

}
