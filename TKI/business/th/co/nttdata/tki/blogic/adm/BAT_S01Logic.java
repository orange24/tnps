package th.co.nttdata.tki.blogic.adm;

import java.util.List;

import th.co.nttdata.tki.bean.Batch;

public interface BAT_S01Logic {

	public Batch getBatch(Batch batch);

	public List<Batch> getBatchList();

	public void batchRun(Batch batch);

	List<Batch> getBatchStatus();

	void updateBatchControl(Batch batch);

}