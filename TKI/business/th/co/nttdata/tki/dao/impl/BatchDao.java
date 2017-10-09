package th.co.nttdata.tki.dao.impl;

import java.util.List;

import th.co.nttdata.tki.bean.Batch;

public interface BatchDao {

	public Batch getBatch(Batch batch);

	public List<Batch> getBatchList(Batch batch);

	public void updateRunBy(Batch batch);

	List<Batch> getBatchStatus();

	public void updateBatchControl(Batch batch);

}