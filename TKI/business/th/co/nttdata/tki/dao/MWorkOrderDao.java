package th.co.nttdata.tki.dao;

import java.util.List;
import java.util.Map;

import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.bean.TDCPlan;

public interface MWorkOrderDao {

	public Map<String, Object> getLotQty(String lotNo);

	public Boolean existsLotNo(MWorkOrder workOrder);

	public abstract List<MWorkOrder> getOnlyWorkOrderNo(MWorkOrder workOrder);
	
	public MWorkOrder search(MWorkOrder mWorkOrder);

	public MWorkOrder searchTPics(MWorkOrder mWorkOrder);
	
	public void delete(MWorkOrder mWorkOrder);
	
	public void syncLot(MWorkOrder mWorkOrder);

	public List<MWorkOrder> workOrderAutoComplete(MWorkOrder workOrder);
	
	void deleteWorkOrder(TDCPlan object);
	
	void insertWorkOrder(TDCPlan object);
}
