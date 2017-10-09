package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.bean.TDeliveryPlanDate;

public interface TDeliveryPlanDao {

	public TDeliveryPlan getDateList(TDeliveryPlan TDeliveryPlan);

	public TDeliveryPlan getPlanList(TDeliveryPlan TDeliveryPlan);

	public TDeliveryPlan copyDate(TDeliveryPlan TDeliveryPlan);

	public TDeliveryPlan copyPlan(TDeliveryPlan TDeliveryPlan);

	public TDeliveryPlan copyTime(TDeliveryPlan TDeliveryPlan);

	public TDeliveryPlan copyBalanceOrder(TDeliveryPlan TDeliveryPlan);

	public Integer insertDate(TDeliveryPlanDate TDeliveryPlanDate);

	public Integer insertPlan(TDeliveryPlan TDeliveryPlan);

	public Integer insertTime(TDeliveryPlanDate TDeliveryPlanDate);

	public String selectInsertFlag(TDeliveryPlan TDeliveryPlan);

	public TDeliveryPlan selectDLV_R01(TDeliveryPlan TDeliveryPlan);

	public TDeliveryPlan selectDLV_R02(TDeliveryPlan TDeliveryPlan);

	public TDeliveryPlan selectMRDC_R11(TDeliveryPlan TDeliveryPlan);

	public TDeliveryPlan selectMRDC_R14(TDeliveryPlan TDeliveryPlan);

	public Integer countMRDC_R14();

	public void deleteByFg(TDeliveryPlan TDeliveryPlan);

	public void deleteByPlan(TDeliveryPlan TDeliveryPlan);

	public Integer countDate(TDeliveryPlan TDeliveryPlan);

	public TDeliveryPlan selectMRDC_R18Summary(TDeliveryPlan TDeliveryPlan);

	public TDeliveryPlan selectMRDC_R18ProcessList2_Order(
			TDeliveryPlan TDeliveryPlan);

	public TDeliveryPlan selectMRDC_R18ProcessList2_Sales(
			TDeliveryPlan TDeliveryPlan);

	public TDeliveryPlan queryWipOfPart(TDeliveryPlan TDeliveryPlan);

	public TDeliveryPlan selectMRDC_R18ProcessList3(TDeliveryPlan TDeliveryPlan);

	public TDeliveryPlan selectMRDC_R18ProcessList4(TDeliveryPlan TDeliveryPlan);

	public TDeliveryPlan selectMRDC_R18ProcessList5(TDeliveryPlan TDeliveryPlan);

	public TDeliveryPlan selectMRDC_R01ProductCompositionList1(
			TDeliveryPlan TDeliveryPlan);

	public TDeliveryPlan selectMRDC_R01ProductCompositionList2(
			TDeliveryPlan TDeliveryPlan);

	public TDeliveryPlan selectMRDC_R04ProductMaster(TDeliveryPlan TDeliveryPlan);

	public Integer countMRDC_R04ProductMaster();

	public TDeliveryPlan searchMRDC_S031(TDeliveryPlan TDeliveryPlan);

	public TDeliveryPlan searchMRDC_S032(TDeliveryPlan TDeliveryPlan);

	public TDeliveryPlan selectMRDC_R15(TDeliveryPlan TDeliveryPlan);

	public Integer countMRDC_R15();

	public TDeliveryPlan selectMRDC_R20(TDeliveryPlan TDeliveryPlan);

	public List<MPart> getFgList(TDeliveryPlan TDeliveryPlan);

	public TDeliveryPlan getWipList(TDeliveryPlan TDeliveryPlan);

	public Integer insertBalanceOrder(TDeliveryPlanDate TDeliveryPlanDate);

	public List<TDeliveryPlan> getAllFgByCustomer(TDeliveryPlan tDeliveryPlan);
}