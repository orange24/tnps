package th.co.nttdata.tki.blogic.dal;

import th.co.nttdata.tki.bean.MMachine;
import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.bean.TDailyMC;

public interface DAL_S02Logic {

	public TDailyMC check(TDailyMC TDailyMC);

	public void delete(TDailyMC TDailyMC);

	public TDailyMC edit(TDailyMC TDailyMC);

	public void save(TDailyMC TDailyMC);
	
	public MWorkOrder searchLotno(String lotNo, String wip,Integer reportType);
	
	public MWorkOrder getWorkOrderList(MWorkOrder mWorkOrder);

	public MMachine getMachine(String machineId);
	
	public MWorkOrder getLotByMold(String moldId, String moldNo);
}