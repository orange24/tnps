package th.co.nttdata.tki.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.Batch;
import th.co.nttdata.tki.dao.AbstractBaseDao;

@Repository
@SuppressWarnings("unchecked")
public class BatchDaoImpl extends AbstractBaseDao implements BatchDao {

	@Override
	public Batch getBatch(Batch batch) {
		return (Batch) queryForObject("batch.queryBatch", batch);
	}

	@Override
	public List<Batch> getBatchList(Batch batch) {
		return (List<Batch>) queryForList("batch.queryBatch", batch);
	}

	@Override
	public void updateRunBy(Batch batch) {
		update("batch.updateRunBy", batch);
	}

	@Override
	public List<Batch> getBatchStatus() {
		return (List<Batch>) queryForList("batch.queryBatchStatus");
	}

	@Override
	public void updateBatchControl(Batch batch) {
		// MERGE m_batch_control
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("batchcode", batch.getBatchCode());
		param.put("batchname", batch.getBatchName());
		param.put("batchstatus", batch.getBatchStatus());
		param.put("runby", batch.getUpdateBy());
		update("m_batch_control.insertMBatchControl", param);

	}
}