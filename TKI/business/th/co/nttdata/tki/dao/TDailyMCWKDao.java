package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.bean.TDailyMCWK;

public interface TDailyMCWKDao {

	public void delete(TDailyMCWK TDailyMCWK);

	public void insert(TDailyMCWK TDailyMCWK);

	public TDailyMCWK update(TDailyMCWK TDailyMCWK);

	public TDailyMCWK query(TDailyMCWK TDailyMCWK);

	public TDailyMCWK queryList(TDailyMCWK TDailyMCWK);

	public List<MWorkOrder> queryWorkOrderNoList(MWorkOrder MWorkOrder);

	public TDailyMCWK selectDAL_R02(TDailyMCWK tDailyMCWK);
	
	public Integer countDAL_R02();
}