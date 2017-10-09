package th.co.nttdata.tki.dao.impl;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.bean.TDeliveryPlanDate;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TDeliveryPlanDao;

@Repository
@SuppressWarnings("unchecked")
public class TDeliveryPlanDaoImpl extends AbstractBaseDao implements TDeliveryPlanDao {

	@Override
	public TDeliveryPlan getDateList( TDeliveryPlan TDeliveryPlan ) {
		List<TDeliveryPlanDate> dateList = (List<TDeliveryPlanDate>) queryForList("t_deliveryplan.selectDateList", TDeliveryPlan);
		TDeliveryPlan.setDateList(dateList);

		return TDeliveryPlan;
	}

	@Override
	public TDeliveryPlan getPlanList( TDeliveryPlan TDeliveryPlan ) {
		List<TDeliveryPlan> planList = (List<TDeliveryPlan>) queryForList("t_deliveryplan.selectPlanList", TDeliveryPlan);
		TDeliveryPlan.setPlanList(planList);

		return TDeliveryPlan;
	}
	
	@Override
	public TDeliveryPlan copyDate( TDeliveryPlan TDeliveryPlan ) {
		return (TDeliveryPlan) insert("t_deliveryplan.copyDate", TDeliveryPlan);
	}
	
	@Override
	public TDeliveryPlan copyPlan( TDeliveryPlan TDeliveryPlan ) {
		Integer newDeliveryPlanId = (Integer) insert("t_deliveryplan.copyPlan", TDeliveryPlan);
		TDeliveryPlan.setNewDeliveryPlanId(newDeliveryPlanId);
		
		return TDeliveryPlan;
	}
	
	@Override
	public TDeliveryPlan copyTime( TDeliveryPlan TDeliveryPlan ) {
		return (TDeliveryPlan) insert("t_deliveryplan.copyTime", TDeliveryPlan);
	}

	@Override
	public TDeliveryPlan copyBalanceOrder(TDeliveryPlan TDeliveryPlan) {
		TDeliveryPlan tDeliveryPlan = (TDeliveryPlan) insert("t_deliveryplan.copyBalanceOrder", TDeliveryPlan);
		return tDeliveryPlan;
	}
	
	@Override
	public Integer insertDate( TDeliveryPlanDate TDeliveryPlanDate ) {
		Integer detailPlanId = (Integer) queryForObject("t_deliveryplan.insertDate", TDeliveryPlanDate);
		TDeliveryPlanDate.setDetailPlanId(detailPlanId);

		return detailPlanId;
	}
	
	@Override
	public Integer insertPlan( TDeliveryPlan TDeliveryPlan ) {
		Integer deliveryPlanId = (Integer) queryForObject("t_deliveryplan.insertPlan", TDeliveryPlan);
		TDeliveryPlan.setDeliveryPlanId(deliveryPlanId);

		return deliveryPlanId;
	}
	
	@Override
	public Integer insertTime( TDeliveryPlanDate TDeliveryPlanDate ) {
		return (Integer) queryForObject("t_deliveryplan.insertTime", TDeliveryPlanDate);
	}
	
	@Override
	public String selectInsertFlag( TDeliveryPlan TDeliveryPlan ) {
		return (String) queryForObject("t_deliveryplan.selectInsertFlag", TDeliveryPlan);
	}
	
	@Override
	public TDeliveryPlan selectDLV_R01( TDeliveryPlan TDeliveryPlan ) {
		List<TDeliveryPlanDate> dateList = (List<TDeliveryPlanDate>) queryForList("t_deliveryplan.selectDLV_R01", TDeliveryPlan);
		TDeliveryPlan.setDateList(dateList);
		
		return TDeliveryPlan;
	}
	
	@Override
	public TDeliveryPlan selectDLV_R02( TDeliveryPlan TDeliveryPlan ) {
		List<TDeliveryPlan> planList = (List<TDeliveryPlan>) queryForList("t_deliveryplan.selectDLV_R02", TDeliveryPlan);
		TDeliveryPlan.setPlanList(planList);
		
		return TDeliveryPlan;
	}
	
	@Override
	public TDeliveryPlan selectMRDC_R11( TDeliveryPlan TDeliveryPlan ) {
		List<TDeliveryPlan> planList = (List<TDeliveryPlan>) queryForList("t_deliveryplan.selectMRDC_R11", TDeliveryPlan);
		TDeliveryPlan.setPlanList(planList);
		
		return TDeliveryPlan;
	}
	
	@Override
	public TDeliveryPlan selectMRDC_R14( TDeliveryPlan TDeliveryPlan ) {
		List<TDeliveryPlan> planList = (List<TDeliveryPlan>) queryForList("t_deliveryplan.selectMRDC_R14", TDeliveryPlan);
		TDeliveryPlan.setPlanList(planList);
		
		return TDeliveryPlan;
	}
	
	@Override
	public Integer countMRDC_R14() {
		
		return (Integer) queryForObject("t_deliveryplan.countMRDC_R14");
	}
	
	@Override
	public void deleteByFg( TDeliveryPlan TDeliveryPlan ) {	
		delete("t_deliveryplan.deleteTimeByFg", TDeliveryPlan);
		delete("t_deliveryplan.deleteDateByFg", TDeliveryPlan);
	}
	
	@Override
	public void deleteByPlan( TDeliveryPlan TDeliveryPlan ) {	
		delete("t_deliveryplan.deleteTimeByPlan", TDeliveryPlan);
		delete("t_deliveryplan.deleteDateByPlan", TDeliveryPlan);
		delete("t_deliveryplan.deletePlanByPlan", TDeliveryPlan);
		delete("t_deliveryplan.deleteBalanceOrderByPlan", TDeliveryPlan);
	}
	
	@Override
	public Integer countDate( TDeliveryPlan TDeliveryPlan ) {
		return (Integer) queryForObject("t_deliveryplan.countDate", TDeliveryPlan);
	}
	
	@Override
	public TDeliveryPlan selectMRDC_R18Summary( TDeliveryPlan TDeliveryPlan ) {
		List<TDeliveryPlan> planList = (List<TDeliveryPlan>) queryForList("t_deliveryplan.selectMRDC_R18_ProcessList1", TDeliveryPlan);
		TDeliveryPlan.setPlanList(planList);
		
		return TDeliveryPlan;
	}
	
	@Override
	public TDeliveryPlan selectMRDC_R18ProcessList2_Order( TDeliveryPlan TDeliveryPlan ) {
		List<TDeliveryPlanDate> date2OrderList = (List<TDeliveryPlanDate>) queryForList("t_deliveryplan.selectMRDC_R18_ProcessList2_Order", TDeliveryPlan);
		TDeliveryPlan.setDate2OrderList(date2OrderList);
		
		return TDeliveryPlan;
	}
	
	@Override
	public TDeliveryPlan selectMRDC_R18ProcessList2_Sales( TDeliveryPlan TDeliveryPlan ) {
		List<TDeliveryPlanDate> date2SalesList = (List<TDeliveryPlanDate>) queryForList("t_deliveryplan.selectMRDC_R18_ProcessList2_Sales", TDeliveryPlan);
		TDeliveryPlan.setDate2SalesList(date2SalesList);
		
		return TDeliveryPlan;
	}
	
	@Override
	public TDeliveryPlan queryWipOfPart( TDeliveryPlan TDeliveryPlan ) {
		List<MWip> wipList = (List<MWip>) queryForList("t_deliveryplan.queryWipOfPart", TDeliveryPlan);
		TDeliveryPlan.setWipList(wipList);
		
		return TDeliveryPlan;
	}
	
	@Override
	public TDeliveryPlan selectMRDC_R18ProcessList3( TDeliveryPlan TDeliveryPlan ) {
		List<TDeliveryPlanDate> dateList = (List<TDeliveryPlanDate>) queryForList("t_deliveryplan.selectMRDC_R18_ProcessList3", TDeliveryPlan);
		TDeliveryPlan.setDate3List(dateList);
		
		return TDeliveryPlan;
	}
	
	@Override
	public TDeliveryPlan selectMRDC_R18ProcessList4( TDeliveryPlan TDeliveryPlan ) {
		List<TDeliveryPlanDate> dateList = (List<TDeliveryPlanDate>) queryForList("t_deliveryplan.selectMRDC_R18_ProcessList4", TDeliveryPlan);
		TDeliveryPlan.setDate4List(dateList);
		
		return TDeliveryPlan;
	}
	
	@Override
	public TDeliveryPlan selectMRDC_R18ProcessList5( TDeliveryPlan TDeliveryPlan ) {
		List<TDeliveryPlanDate> dateList = (List<TDeliveryPlanDate>) queryForList("t_deliveryplan.selectMRDC_R18_ProcessList5", TDeliveryPlan);
		TDeliveryPlan.setDate5List(dateList);
		
		return TDeliveryPlan;
	}
	
	@Override
	public TDeliveryPlan selectMRDC_R01ProductCompositionList1( TDeliveryPlan TDeliveryPlan ) {
		List<TDeliveryPlan> planList = (List<TDeliveryPlan>) queryForList("t_deliveryplan.selectMRDC_R01ProductCompositionList1", TDeliveryPlan);
		TDeliveryPlan.setPlanList(planList);
		
		return TDeliveryPlan;
	}
	
	@Override
	public TDeliveryPlan selectMRDC_R01ProductCompositionList2( TDeliveryPlan TDeliveryPlan ) {
		List<TDeliveryPlan> planList = (List<TDeliveryPlan>) queryForList("t_deliveryplan.selectMRDC_R01ProductCompositionList2", TDeliveryPlan);
		TDeliveryPlan.setPlan2List(planList);
		
		return TDeliveryPlan;
	}
	
	@Override
	public TDeliveryPlan selectMRDC_R04ProductMaster( TDeliveryPlan TDeliveryPlan ) {
		List<TDeliveryPlan> planList = (List<TDeliveryPlan>) queryForList("t_deliveryplan.selectMRDC_R04ProductMaster", TDeliveryPlan);
		TDeliveryPlan.setPlanList(planList);
		
		return TDeliveryPlan;
	}
	
	@Override
	public Integer countMRDC_R04ProductMaster() {
		
		return (Integer) queryForObject("t_deliveryplan.countMRDC_R04ProductMaster");
	}
	
	@Override
	public TDeliveryPlan searchMRDC_S031( TDeliveryPlan TDeliveryPlan ) {
		List<TDeliveryPlanDate> dateList = (List<TDeliveryPlanDate>) queryForList("t_deliveryplan.searchMRDC_S031", TDeliveryPlan,getSkipResult(TDeliveryPlan), TDeliveryPlan.getPageCount());
		TDeliveryPlan.setDateList(dateList);
		calPageTotal("t_deliveryplan.countMRDC_S031", TDeliveryPlan);
		
		return TDeliveryPlan;
	}
	
	@Override
	public TDeliveryPlan searchMRDC_S032( TDeliveryPlan TDeliveryPlan ) {
		List<TDeliveryPlanDate> dateList = (List<TDeliveryPlanDate>) queryForList("t_deliveryplan.searchMRDC_S032", TDeliveryPlan);
		TDeliveryPlan.setDate3List(dateList);
		
		return TDeliveryPlan;
	}
	
	@Override
	public TDeliveryPlan selectMRDC_R15( TDeliveryPlan TDeliveryPlan ) {
		List<TDeliveryPlanDate> dateList = (List<TDeliveryPlanDate>) queryForList("t_deliveryplan.selectMRDC_R15", TDeliveryPlan);
		TDeliveryPlan.setDateList(dateList);
		
		return TDeliveryPlan;
	}
	
	@Override
	public Integer countMRDC_R15() {
		
		return (Integer) queryForObject("t_deliveryplan.countMRDC_R15");
	}
	
	@Override
	public TDeliveryPlan selectMRDC_R20( TDeliveryPlan TDeliveryPlan ) {
		DecimalFormat dFormat = new DecimalFormat("00");
		Map<String,Object> param = new HashMap<String,Object>();
		if(TDeliveryPlan.getCustomerId()< 0) TDeliveryPlan.setCustomerId(null);
		if(TDeliveryPlan.getPartNo() == "") TDeliveryPlan.setPartNo(null);
		if(TDeliveryPlan.getPartName() == "") TDeliveryPlan.setPartName(null);
		param.put("month", TDeliveryPlan.getYear()+dFormat.format(TDeliveryPlan.getMonth()+1));
		param.put("customerid", TDeliveryPlan.getCustomerId());
		param.put("partno", TDeliveryPlan.getPartNo());
		param.put("partname", TDeliveryPlan.getPartName());
		param.put("wip", TDeliveryPlan.getWip());
		param.put("sorting", TDeliveryPlan.getSorting());
		
		List<TDeliveryPlanDate> dateList = (List<TDeliveryPlanDate>) queryForList("t_deliveryplan.selectMRDC_R20", param);
		TDeliveryPlan.setDateList(dateList);
		
		return TDeliveryPlan;
	}
	
	@Override
	public List<MPart> getFgList(TDeliveryPlan TDeliveryPlan) {
		return (List<MPart>) queryForList("t_deliveryplan.queryFG", TDeliveryPlan);
	}

	@Override
	public TDeliveryPlan getWipList(TDeliveryPlan TDeliveryPlan) {
		List<TDeliveryPlan> wipList = (List<TDeliveryPlan>) queryForList("t_deliveryplan.queryWipInPart", TDeliveryPlan);
		TDeliveryPlan.setPlan2List(wipList);		
		return TDeliveryPlan;
	}

	@Override
	public Integer insertBalanceOrder(TDeliveryPlanDate TDeliveryPlanDate) {
		Integer result = (Integer) queryForObject("t_deliveryplan.insertBalanceOrder", TDeliveryPlanDate);
		return result;
	}

	@Override
	public List<TDeliveryPlan> getAllFgByCustomer(TDeliveryPlan tDeliveryPlan) {
		List<TDeliveryPlan> list = (List<TDeliveryPlan>) queryForList("t_deliveryplan.selectFgByCustomerAndDate", tDeliveryPlan);
		return list;
	}
}