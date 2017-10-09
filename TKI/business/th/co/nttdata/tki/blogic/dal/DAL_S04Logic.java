package th.co.nttdata.tki.blogic.dal;

import java.util.List;

import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.bean.TDailyWK;

public interface DAL_S04Logic {

	public TDailyWK check(TDailyWK TDailyWK);

	public void delete(TDailyWK TDailyWK);

	public TDailyWK edit(TDailyWK TDailyWK);

	public void save(TDailyWK TDailyWK);

	public List<MWorkOrder> getWorkOrderNoList(MWorkOrder MWorkOrder);

	public List<MPart> getPartNo(Integer customerId, String wip);
}