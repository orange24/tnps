package th.co.nttdata.tki.blogic.DLV.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.bean.TDeliveryPlanDate;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.DLV.DLV_S02Logic;
import th.co.nttdata.tki.dao.TDeliveryPlanDao;

@Service
public class DLV_S02LogicImpl extends AbstractBaseLogic implements DLV_S02Logic {

	@Autowired
	private TDeliveryPlanDao tDeliveryPlanDao;
	
	@Override
	public TDeliveryPlan search(TDeliveryPlan TDeliveryPlan) {
		TDeliveryPlan = tDeliveryPlanDao.getDateList(TDeliveryPlan);
		if (TDeliveryPlan.getDeliveryPlanId() != null) {
			TDeliveryPlan.setInsertFlag(tDeliveryPlanDao
					.selectInsertFlag(TDeliveryPlan));
			TDeliveryPlan.setCountDate(tDeliveryPlanDao
					.countDate(TDeliveryPlan));
		}

		return TDeliveryPlan;
	}

	@Override
	public synchronized TDeliveryPlan save(TDeliveryPlan TDeliveryPlan) {
		boolean isHavePlanDate = false;
		Integer deliveryPlanId = tDeliveryPlanDao.insertPlan(TDeliveryPlan);
		TDeliveryPlan.setDeliveryPlanId(deliveryPlanId);
		// <!-- Insert: t_deliveryplan_date -->
		for (TDeliveryPlanDate TDeliveryPlanDate : TDeliveryPlan.getDateList()) {
			// <!-- Filtering: insert t_deliveryplan_date -->
			if (null == TDeliveryPlanDate.getProductionQty()
					&& null == TDeliveryPlanDate.getDetailPlanId()) {
				// <!-- Insert: t_deliveryplan_date_0 -->
				if (null != TDeliveryPlanDate.getBalanceOrderQty()
						&& null == TDeliveryPlanDate.getDeliveryDate()) {
					TDeliveryPlanDate.setDeliveryPlanId(TDeliveryPlan
							.getDeliveryPlanId());
					TDeliveryPlanDate.setFgId(TDeliveryPlan.getFgId());
					tDeliveryPlanDao.insertBalanceOrder(TDeliveryPlanDate);
				}
				continue;
			}
			isHavePlanDate = true;
			TDeliveryPlanDate.setDeliveryPlanId(deliveryPlanId);
			tDeliveryPlanDao.insertDate(TDeliveryPlanDate);
			tDeliveryPlanDao.insertTime(TDeliveryPlanDate);
		}
		// <!-- Insert: t_deliveryplan_date on date 1 -->
		if (!isHavePlanDate) {
			List<TDeliveryPlanDate> listDate = TDeliveryPlan.getDateList();
			if (null != listDate && 1 < listDate.size()) {
				TDeliveryPlanDate planDate1 = listDate.get(1);
				planDate1.setDeliveryPlanId(deliveryPlanId);
				tDeliveryPlanDao.insertDate(planDate1);
				tDeliveryPlanDao.insertTime(planDate1);
			}
		}

		TDeliveryPlan.getInfos().add(
				new Message("inf.cmm.002", new String[] {}));
		return TDeliveryPlan;
	}

	@Override
	public TDeliveryPlan exportDLV_R01(TDeliveryPlan TDeliveryPlan) {

		return tDeliveryPlanDao.selectDLV_R01(TDeliveryPlan);
	}

	@Override
	public void deleteByFg(TDeliveryPlan TDeliveryPlan) {
		tDeliveryPlanDao.deleteByFg(TDeliveryPlan);
		TDeliveryPlan.getInfos().add(
				new Message("inf.cmm.003", new String[] {}));
	}

	@Override
	public TDeliveryPlan countDate(TDeliveryPlan TDeliveryPlan) {
		if (TDeliveryPlan.getDeliveryPlanId() != null) {
			TDeliveryPlan.setCountDate(tDeliveryPlanDao
					.countDate(TDeliveryPlan));
		}

		return TDeliveryPlan;
	}

	@Override
	public TDeliveryPlan insertFlag(TDeliveryPlan TDeliveryPlan) {
		if (TDeliveryPlan.getDeliveryPlanId() != null) {
			TDeliveryPlan.setInsertFlag(tDeliveryPlanDao
					.selectInsertFlag(TDeliveryPlan));
		}

		return TDeliveryPlan;
	}

	@Override
	public List<MPart> getFgList(TDeliveryPlan TDeliveryPlan) {

		return tDeliveryPlanDao.getFgList(TDeliveryPlan);
	}

	@Override
	public TDeliveryPlan deliveryPlanReport(TDeliveryPlan tDeliveryPlan) {
		List<TDeliveryPlan> fgList = tDeliveryPlanDao
				.getAllFgByCustomer(tDeliveryPlan);
		List<TDeliveryPlanDate> dateList = new ArrayList<TDeliveryPlanDate>();

		for (TDeliveryPlan tmp : fgList) {
			tDeliveryPlan.setFgId(tmp.getFgId());
			TDeliveryPlan plan = this.search(tDeliveryPlan);
			List<TDeliveryPlanDate> newDateList = new ArrayList<TDeliveryPlanDate>();
			for (TDeliveryPlanDate tmpDate : plan.getDateList()) {
				tmpDate.setFgId(tmp.getFgId());
				tmpDate.setFgNo(tmp.getFgNo());
				tmpDate.setFgName(tmp.getFgName());
				newDateList.add(tmpDate);
			}
			dateList.addAll(newDateList);
		}
		tDeliveryPlan.setDateList(dateList);
		return tDeliveryPlan;
	}
}
