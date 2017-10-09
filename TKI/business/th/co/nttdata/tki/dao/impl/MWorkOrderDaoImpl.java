package th.co.nttdata.tki.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.bean.TDCPlan;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MWorkOrderDao;

@Repository
@SuppressWarnings("unchecked")
public class MWorkOrderDaoImpl extends AbstractBaseDao implements MWorkOrderDao {

	@Value("#{settings['CMM.linkedServer']}")
	private String linkedServer = "\\\\172.16.12.200\\pipe\\sql\\query";
	
	@Override
	public Map<String, Object> getLotQty( String lotNo ) {
		Map<String, Object> map = (Map<String, Object>) queryForObject("m_workorder.queryLotQty", lotNo);
		if (map != null) {
			Integer fgIn = (Integer) map.get("fgIn");
			Integer fgOut = (Integer) map.get("fgOut");
			Integer endLotQty = (Integer) map.get("endLotQty");
			if (lotNo.equals(map.get("endLot").toString()) && 0 != endLotQty) {
				map.put("lotQtyIn", endLotQty - fgIn);
				map.put("lotQtyOut", endLotQty - fgOut);
			} else {
				System.out.println(map);
				Integer lotQty = (Integer) map.get("lotQty");
				map.put("lotQtyIn", lotQty - fgIn);
				map.put("lotQtyOut", lotQty - fgOut);
			}
			map.put("lotNo", lotNo);
		}
		return map;
	}
	
	@Override
	public List<MWorkOrder> getOnlyWorkOrderNo( MWorkOrder workOrder ) {
		return queryForList("m_workorder.queryOnlyWoNo", workOrder);
	}
	
	@Override
	public Boolean existsLotNo( MWorkOrder workOrder ) {
		return (Integer)queryForObject("m_workorder.existsLotNo", workOrder) > 0;
	}

	@Override
	public MWorkOrder search(MWorkOrder mWorkOrder) {
		mWorkOrder.setmWorkOrderLst( 
				(List<MWorkOrder>) queryForList("m_workorder.querySearch", mWorkOrder,getSkipResult(mWorkOrder), mWorkOrder.getPageCount()));
		calPageTotal("m_workorder.count", mWorkOrder);
		return mWorkOrder;
	}

	@Override
	public void delete(MWorkOrder mWorkOrder) {
		delete("m_workorder.delete",mWorkOrder);
	}

	@Override
	public MWorkOrder searchTPics(MWorkOrder mWorkOrder) {
		mWorkOrder.setLinkDB(linkedServer);
		mWorkOrder.setmWorkOrderLst(queryForList("m_workorder.querySearchTPics",mWorkOrder,getSkipResult(mWorkOrder),mWorkOrder.getPageCount()));
		calPageTotal("m_workorder.countTPics", mWorkOrder);
		return mWorkOrder;
	}

	@Override
	public void syncLot(MWorkOrder mWorkOrder) {
		insert("m_workorder.querySyncLot", mWorkOrder);
	}
	
	@Override
	public List<MWorkOrder> workOrderAutoComplete( MWorkOrder workOrder ) {
		return (List<MWorkOrder>) queryForList("m_workorder.workOrderAutoComplete", workOrder);
	}

	@Override
	public void deleteWorkOrder(TDCPlan object) {
		delete("m_workorder.delete_workorder",object);
	}

	@Override
	public void insertWorkOrder(TDCPlan object) {
		insert("m_workorder.insert_workorder", object);
	}
	
}