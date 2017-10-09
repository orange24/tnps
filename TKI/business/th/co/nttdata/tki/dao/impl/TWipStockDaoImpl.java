package th.co.nttdata.tki.dao.impl;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.bean.TWip;
import th.co.nttdata.tki.bean.TWipStock;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TWipStockDao;

@Repository
@SuppressWarnings("unchecked")
public class TWipStockDaoImpl extends AbstractBaseDao implements TWipStockDao {

	@Override
	public TWip getWipStockList() {
		return getWipStockList(null);
	}

	@Override
	public TWip getWipStockList(TWip TWip) {
		// <!-- Check if 'null'. -->
		if (TWip == null)
			TWip = new TWip();

		Map<String, List<TWipStock>> stockMap = new LinkedHashMap<String, List<TWipStock>>();
		List<TWipStock> stockList = queryForList("t_wip_stock.query", TWip);
		List<TWipStock> stocks = null;
		Iterator<TWipStock> iterator = stockList.iterator();
		String wip = "";
		String key = "";
		String customerId = "";
		String partId = "";
		String wipOrder = "";
		boolean isNextCustomer = false;
		boolean isNextPart = false;
		boolean isNextWip = false;
		boolean isNextOrder = false;
		Integer prev = null;
		Integer next = null;
		Integer ok = 0;
		Integer adj = 0;
		while (iterator.hasNext()) {
			TWipStock wipStock = iterator.next();
			isNextCustomer = !customerId.equals(String.valueOf(wipStock
					.getCustomerId()));
			isNextPart = !partId.equals(String.valueOf(wipStock.getPartId()));
			isNextWip = !(wip.equals(wipStock.getWip()));
			isNextOrder = !(wipOrder.equals(String.valueOf(wipStock
					.getWipOrder())));
			if (isNextCustomer || isNextPart || isNextWip || isNextOrder) {
				customerId = String.valueOf(wipStock.getCustomerId());
				partId = String.valueOf(wipStock.getPartId());
				wip = wipStock.getWip();
				key = wipStock.getCustomerId() + "" + wipStock.getPartId()
						+ wipStock.getWip() + wipStock.getWipOrder();
				wipOrder = String.valueOf(wipStock.getWipOrder());
				stockMap.put(key, stocks = new LinkedList<TWipStock>());
				prev = (null == wipStock.getPrevStock() ? 0 : wipStock
						.getPrevStock());
			}
			ok = (null == wipStock.getOk() ? 0 : wipStock.getOk());
			next = (null == wipStock.getNextWIPQty() ? 0 : wipStock
					.getNextWIPQty());
			adj = (null == wipStock.getAdjustStock() ? 0 : wipStock
					.getAdjustStock());

			/*
			 * Check Adjust stock is null value
			 */
			if (wipStock.getAdjustStock() == null) {
				/*
				 * Calculate wip stock from prev stock.
				 */
				prev = (prev + ok) - next;
				wipStock.setCurrentStock(prev);
			} else {
				/*
				 * The first, set current stock with calculate from prev stock
				 * (Show prev cal. in today) and calculate wip stock from adjust
				 * stock.(Show adjust cal. to next day)
				 */
				prev = (prev + ok) - next;
				wipStock.setCurrentStock(prev);
				prev = adj;
			}

			stocks.add(wipStock);
		}

		TWip.setStockMap(stockMap);
		if (stockMap.size() < 1)
			TWip.getInfos().add(new Message("inf.cmm.004", null));
		return TWip;
	}

	@Override
	public TWip selectMRDC_S19(TWip tWip) {
		tWip.setStockList(queryForList("t_wip_stock.queryMRDC_S19", tWip,
				getSkipResult(tWip), tWip.getPageCount()));
		calPageTotal("t_wip_stock.countMRDC_S19", tWip);
		return tWip;
	}
}