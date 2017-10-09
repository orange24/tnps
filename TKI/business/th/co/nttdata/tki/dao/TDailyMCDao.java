package th.co.nttdata.tki.dao;

import th.co.nttdata.tki.bean.MMachine;
import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.bean.TDailyMC;

public interface TDailyMCDao {

	public void delete(TDailyMC TDailyMC);

	public void insert(TDailyMC TDailyMC);

	public TDailyMC update(TDailyMC TDailyMC);

	public TDailyMC query(TDailyMC TDailyMC);

	public TDailyMC queryList(TDailyMC TDailyMC);

	public TDailyMC queryLotNo(TDailyMC TDailyMC);
	
	public MWorkOrder querysearchLotno(String lotNo, String wip,Integer reportType);
	
	public MWorkOrder getDCByLotno(String lotNo, String wip,Integer reportType);
	
	public boolean queryWorkOrder(MWorkOrder MWorkOrder);
	
	public MWorkOrder queryWorkOrderList(MWorkOrder MWorkOrder);

	public TDailyMC selectDAL_R01(TDailyMC TDailyMC);
	
	public Integer countDAL_R01();
	
	public TDailyMC selectMRDC_R09(TDailyMC TDailyMC);
	
	public Integer countMRDC_R09();

	public MMachine getMachine(String machineId);
	
	public MWorkOrder getLotByMold(int moldId, String moldNo);
}
