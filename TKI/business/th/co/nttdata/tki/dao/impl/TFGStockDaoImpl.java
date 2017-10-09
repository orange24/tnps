package th.co.nttdata.tki.dao.impl;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.TDeliveryPlanDate;
import th.co.nttdata.tki.bean.TFG;
import th.co.nttdata.tki.bean.TFGStock;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TFGStockDao;

@Repository
@SuppressWarnings("unchecked")
public class TFGStockDaoImpl extends AbstractBaseDao implements TFGStockDao {

	@Override
	public void delete(TFGStock TFGStock) {
		delete("t_fg_stock.delete", TFGStock);
	}

	@Override
	public TFG getFGStockList() {
		return getFGStockList(null);
	}

	@Override
	public TFG getFGStockList(TFG TFG) {
		// <!-- Check if 'null'. -->
		if (TFG == null)
			TFG = new TFG();

		Map<Integer, List<TFGStock>> stockMap = new LinkedHashMap<Integer, List<TFGStock>>();
		List<TFGStock> stockList = query(TFG);
		List<TFGStock> stocks = null;
		Iterator<TFGStock> iterator = stockList.iterator();
		int partId = Integer.MIN_VALUE;
		while (iterator.hasNext()) {
			TFGStock fgStock = iterator.next();
			boolean isNextPart = partId != fgStock.getPartId();
			if (isNextPart) {
				partId = fgStock.getPartId();
				stockMap.put(partId, stocks = new LinkedList<TFGStock>());
			}

			stocks.add(fgStock);
		}

		TFG.setStockMap(stockMap);
		return TFG;
	}

	@Override
	public List<TFGStock> query(TFG TFG) {
		return (List<TFGStock>) queryForList("t_fg_stock.query", TFG);
	}

	@Override
	public List<TFGStock> query(TFG TFG, MPart MPart) {
		// <!-- Check if 'null'. -->
		if (TFG == null)
			TFG = new TFG();

		// <!-- Store the data. -->
		Integer fgId = TFG.getFgId();
		Integer customerId = TFG.getCustomerId();
		if (MPart != null) {
			TFG.setFgId(MPart.getFgId());
			TFG.setCustomerId(MPart.getCustomerId());
		}

		try {
			return query(TFG);
		} finally {
			TFG.setFgId(fgId);
			TFG.setCustomerId(customerId);
		}
	}

	@Override
	public List<MPart> queryPartId(TFG TFG) {

		List<MPart> MPartList = (List<MPart>) queryForList("t_fg_stock.queryPart", TFG, getSkipResult(TFG),
				TFG.getPageCount());
		calPageTotal("t_fg_stock.count", TFG);
		return MPartList;
	}

	@Override
	public TFGStock queryLastDayOfMonth(TFG TFG, MPart MPart) {
		// <!-- Check if 'null'. -->
		if (TFG == null)
			TFG = new TFG();

		// <!-- Store the data. -->
		Integer fgId = TFG.getFgId();
		if (MPart != null) {
			TFG.setFgId(MPart.getFgId());
		}

		try {
			TFG criteria = new TFG();
			BeanUtils.copyProperties(TFG, criteria);
			criteria.setCustomerId(MPart.getCustomerId());

			TFGStock t = new TFGStock();
			t = (TFGStock) queryForObject("t_fg_stock.queryLastDayOfMonth", criteria);
			if (t == null)
				t = new TFGStock();
			Object temp = queryForObject("t_fg_stock.queryDeliveryBalanceOfDay0", criteria);
			Integer balanceQty = null;
			if (temp != null) {
				balanceQty = (Integer) temp;
			}
			t.setBalanceQty(balanceQty);
			return t;
		} finally {
			TFG.setFgId(fgId);
		}
	}

	@Override
	public TFGStock queryStockAdjust(TFGStock tfgStock) {
		tfgStock.setTfgStockList((List<TFGStock>) queryForList("t_fg_stock_adjust.queryFGAdjust", tfgStock,
				getSkipResult(tfgStock), tfgStock.getPageCount()));
		calPageTotal("t_fg_stock_adjust.count", tfgStock);
		return tfgStock;
	}

	@Override
	public void adjustStock(TFGStock tfgStock) {
		insert("t_fg_stock_adjust.insertFGAdjust", tfgStock);
		update("t_fg_stock_adjust.updateFGStock", tfgStock);
	}

	@Override
	public List<MPart> selectFNG_R02(TFG TFG) {
		List<MPart> MPartList = (List<MPart>) queryForList("t_fg_stock.queryPart", TFG);
		return MPartList;
	}

	@Override
	public TFGStock queryFGAdjustHistory(TFGStock tfgStock) {
		tfgStock.setTfgStockList((List<TFGStock>) queryForList("t_fg_stock_adjust.queryFGAdjustHis", tfgStock,
				getSkipResult(tfgStock), tfgStock.getPageCount()));
		calPageTotal("t_fg_stock_adjust.countHis", tfgStock);
		return tfgStock;
	}

	@Override
	public TFGStock selectMRDC_R16(TFGStock tfgStock) {
		List<TFGStock> stockList = (List<TFGStock>) queryForList("t_fg_stock.queryMRDC_R16", tfgStock);
		tfgStock.setWipMap((Map<String, TFGStock>) queryForMap("t_fg_stock.queryMRDC_R16WIP", tfgStock, "idRef"));
		tfgStock.setTfgStockList(stockList);

		return tfgStock;
	}

	@Override
	public Integer countMRDC_R16() {

		return (Integer) queryForObject("t_fg_stock.countMRDC_R16");
	}

	@Override
	public MWip queryWip(MWip MWip) {
		List<MWip> wipList = (List<MWip>) queryForList("t_fg_stock.queryWip", MWip);
		MWip.setWipList(wipList);

		return MWip;
	}

	@Override
	public TFGStock queryFNG_R04(TFGStock tfgStock) {
		tfgStock.setTfgStockList((List<TFGStock>) queryForList("t_fg_stock_adjust.queryFGAdjust", tfgStock));

		return tfgStock;
	}

	@Override
	public int adjustFgStock(TFGStock tfgStock) {
		return update("t_fg_stock_adjust.adjustFgStock", tfgStock);
	}

	@Override
	public List<TDeliveryPlanDate> queryDeliveryPlans(TFG tfg) {
		return (List<TDeliveryPlanDate>) queryForList("t_fg_stock.queryDeliveryPlans", tfg);
	}
}