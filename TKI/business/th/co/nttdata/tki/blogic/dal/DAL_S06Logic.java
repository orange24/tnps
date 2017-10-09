package th.co.nttdata.tki.blogic.dal;

import java.util.List;

import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.bean.TDailyMCWK;

public interface DAL_S06Logic {

	public TDailyMCWK check(TDailyMCWK TDailyMCWK);

	public void delete(TDailyMCWK TDailyMCWK);

	public TDailyMCWK edit(TDailyMCWK TDailyMCWK);

	public void save(TDailyMCWK TDailyMCWK);

	public List<MWorkOrder> getWorkOrderNoList(MWorkOrder MWorkOrder);

	public List<MPart> getPartNo(Integer customerId, String wip);
}