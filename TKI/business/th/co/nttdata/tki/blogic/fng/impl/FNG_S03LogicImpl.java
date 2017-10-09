package th.co.nttdata.tki.blogic.fng.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.TDeliveryPlanDate;
import th.co.nttdata.tki.bean.TFG;
import th.co.nttdata.tki.bean.TFGStock;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.fng.FNG_S03Logic;
import th.co.nttdata.tki.dao.TFGStockDao;

@Service
public class FNG_S03LogicImpl extends AbstractBaseLogic implements FNG_S03Logic {

	@Autowired
	private TFGStockDao fgStockDao;

	@Override
	public TFG search(TFG tfg) {
		// <!-- Specify the 'stockHoldDay' property. -->
		tfg.setStockHoldDay(new Integer(settings.getProperty("FNG.FNG_S03.stockHoldDay", "4")));

		// <!-- Starting. -->
		Calendar strCal = new GregorianCalendar(tfg.getYear(), tfg.getMonth(), 1);
		Calendar endCal = new GregorianCalendar(tfg.getYear(), tfg.getMonth() + 1, 1);
		endCal.add(Calendar.DAY_OF_YEAR, -1);

		// <!-- Providing the report date. -->
		tfg.setStrDay(strCal.get(Calendar.DAY_OF_MONTH));
		tfg.setEndDay(endCal.get(Calendar.DAY_OF_MONTH));
		tfg.setReportDateFr(strCal.getTime());
		tfg.setReportDateTo(endCal.getTime());

		// <!-- Processing. -->
		Map<String, List<TFGStock>> stockMap = new LinkedHashMap<String, List<TFGStock>>();
		List<TFGStock> stockList = null;
		String key = "";
		for (MPart mPart : fgStockDao.queryPartId(tfg)) {
			key = mPart.getCustomerId() + "" + mPart.getFgId();
			stockMap.put(key, stockList = new LinkedList<TFGStock>());

			// <!-- Adjustment. -->
			if (tfg.getMonth() < 9) {
				tfg.setYearMonth(tfg.getYear() + "0" + (tfg.getMonth() + 1));
			} else {
				tfg.setYearMonth(tfg.getYear() + "" + (tfg.getMonth() + 1));
			}

			// <!-- Find: Balance quantity from the last month. -->
			TFGStock stock = fgStockDao.queryLastDayOfMonth(tfg, mPart);
			int prevDeliver = 0;
			int prevFGBalance = 0;
			if (stock != null) {
				stock.setReportDay(0);
				stock.setPartId(mPart.getPartId());
				// <!-- Set 'customer'. -->
				stock.setCustomerId(mPart.getCustomerId());
				stock.setCustomerCode(mPart.getCustomerCode());
				stock.setCustomerName(mPart.getCustomerName());

				stockList.add(stock);
				prevDeliver = (null == stock.getBalanceQty() ? 0 : stock.getBalanceQty());

				/*
				 * Add FG Stock Adjust to Calculate FG Stock
				 */
				if (stock.getFgAdjust() != null) {
					prevFGBalance = (null == stock.getFgAdjust() ? 0 : stock.getFgAdjust());
				} else {
					prevFGBalance = (null == stock.getFgBalance() ? 0 : stock.getFgBalance());
				}
			}

			List<TFGStock> stocks = fgStockDao.query(tfg, mPart);
			TFGStock[] stockArr = stocks.toArray(new TFGStock[stocks.size()]);

			// <!-- Providing the completion data. -->
			Integer actualQty = 0;
			Integer deliveryQty = 0;
			Integer fgIn = 0;
			Integer fgOut = 0;
			for (int i = 0; i < stockArr.length; i++) {
				TFGStock tfgStock = stockArr[i];

				// <!-- Set 'deliveryQty'(delivery balance). -->
				actualQty = (null == tfgStock.getActualQty() ? 0 : tfgStock.getActualQty());
				deliveryQty = (null == tfgStock.getDeliveryQty() ? 0 : tfgStock.getDeliveryQty());
				prevDeliver = (prevDeliver + actualQty) - deliveryQty;
				tfgStock.setBalanceQty(prevDeliver);

				// <!-- Set 'balanceQty'(FG balance). -->
				if (null != tfgStock.getFgBalance()) {
					fgIn = (null == tfgStock.getFgIn() ? 0 : tfgStock.getFgIn());
					fgOut = (null == tfgStock.getFgOut() ? 0 : tfgStock.getFgOut());
					prevFGBalance = (prevFGBalance + fgIn) - fgOut;
				}
				tfgStock.setFgBalance(prevFGBalance);

				/*
				 * Add FG Stock Adjust to Calculate FG Stock
				 */
				if (tfgStock.getFgAdjust() != null) {
					int adj = (null == tfgStock.getFgAdjust() ? 0 : tfgStock.getFgAdjust());
					prevFGBalance = adj;
				}
			}// End of FORLOOP

			calculateStatusColor(stockList, stockArr, tfg, mPart);
		}
		// end loop
		List<TFGStock> listStock;
		String fgNo = "";
		String fgName = "";
		Iterator<String> keyIterator = stockMap.keySet().iterator();
		while (keyIterator.hasNext()) {
			key = (String) keyIterator.next();
			listStock = (List<TFGStock>) stockMap.get(key);

			for (TFGStock tf : listStock) {
				if (null != tf.getFgNo() && !tf.getFgNo().equals("")) {
					fgNo = tf.getFgNo();
					fgName = tf.getFgName();
					break;
				}
			}
			listStock.get(0).setFgNo(fgNo);
			listStock.get(0).setFgName(fgName);
		}
		tfg.setStocksMap(stockMap);

		return tfg;
	}

	@Override
	public TFG exportFNG_R02(TFG tfg) {
		// <!-- Specify the 'stockHoldDay' property. -->
		tfg.setStockHoldDay(new Integer(settings.getProperty("FNG.FNG_S03.stockHoldDay", "3")));

		// <!-- Starting. -->
		Calendar strCal = new GregorianCalendar(tfg.getYear(), tfg.getMonth(), 1);
		Calendar endCal = new GregorianCalendar(tfg.getYear(), tfg.getMonth() + 1, 1);
		Calendar tmpCal = new GregorianCalendar();
		endCal.add(Calendar.DAY_OF_YEAR, -1);

		// <!-- Providing the report date. -->
		tfg.setStrDay(strCal.get(Calendar.DAY_OF_MONTH));
		tfg.setEndDay(endCal.get(Calendar.DAY_OF_MONTH));
		tfg.setReportDateFr(strCal.getTime());
		tfg.setReportDateTo(endCal.getTime());

		// <!-- Processing. -->
		Map<String, List<TFGStock>> stockMap = new LinkedHashMap<String, List<TFGStock>>();
		List<TFGStock> stockList = null;
		String key = "";
		int prevBalance = 0;
		int prevDeliver = 0;
		for (MPart mPart : fgStockDao.selectFNG_R02(tfg)) {
			tfg.setCustomerCode(mPart.getCustomerCode());
			tfg.setCustomerName(mPart.getCustomerName());
			key = mPart.getCustomerId() + "" + mPart.getFgId();
			stockMap.put(key, stockList = new LinkedList<TFGStock>());

			// <!-- Adjustment. -->
			if (tfg.getMonth() < 9) {
				tfg.setYearMonth(tfg.getYear() + "0" + (tfg.getMonth() + 1));
			} else {
				tfg.setYearMonth(tfg.getYear() + "" + (tfg.getMonth() + 1));
			}

			// <!-- Find: Balance quantity from the last month. -->
			TFGStock stock = fgStockDao.queryLastDayOfMonth(tfg, mPart);
			if (stock != null) {
				stock.setReportDay(0);
				// <!-- Set 'customer'. -->
				stock.setCustomerId(mPart.getCustomerId());
				stock.setCustomerCode(mPart.getCustomerCode());
				stock.setCustomerName(mPart.getCustomerName());

				stockList.add(stock);
				prevDeliver = (null == stock.getBalanceQty() ? 0 : stock.getBalanceQty());

				/*
				 * Add FG Stock Adjust to Calculate FG Stock
				 */
				if (stock.getFgAdjust() != null) {
					prevBalance = (null == stock.getFgAdjust() ? 0 : stock.getFgAdjust());
				} else {
					prevBalance = (null == stock.getFgBalance() ? 0 : stock.getFgBalance());
				}
			}

			List<TFGStock> stocks = fgStockDao.query(tfg, mPart);
			TFGStock[] stockArr = stocks.toArray(new TFGStock[stocks.size()]);

			// <!-- Providing the completion data. -->
			Integer actualQty = 0;
			Integer deliveryQty = 0;
			Integer fgIn = 0;
			Integer fgOut = 0;
			for (int i = 0; i < stockArr.length; i++) {
				TFGStock tfgStock = stockArr[i];
				
				// <!-- Set 'deliveryQty'(delivery balance). -->
				actualQty = (null == tfgStock.getActualQty() ? 0 : tfgStock.getActualQty());
				deliveryQty = (null == tfgStock.getDeliveryQty() ? 0 : tfgStock.getDeliveryQty());
				prevDeliver = (prevDeliver + actualQty) - deliveryQty;
				tfgStock.setBalanceQty(prevDeliver);

				// <!-- Set 'balanceQty'(FG balance). -->
				if (null != tfgStock.getFgBalance()) {
					fgIn = (null == tfgStock.getFgIn() ? 0 : tfgStock.getFgIn());
					fgOut = (null == tfgStock.getFgOut() ? 0 : tfgStock.getFgOut());
					prevBalance = (prevBalance + fgIn) - fgOut;
				}
				tfgStock.setFgBalance(prevBalance);

				/*
				 * Add FG Stock Adjust to Calculate FG Stock
				 */
				if (tfgStock.getFgAdjust() != null) {
					int adj = (null == tfgStock.getFgAdjust() ? 0 : tfgStock.getFgAdjust());
					prevBalance = adj;
				}
			}

			// <!-- Start Processing. -->
			for (int current = 0; current < stockArr.length; current++) {
				TFGStock tfgStock = stockArr[current];

				// <!-- Checking if 'reportDate' is the same month. -->
				tmpCal.setTime(tfgStock.getReportDate());
				if (tmpCal.get(Calendar.MONTH) == tfg.getMonth()) {
					stockList.add(tfgStock);
				}
			}
		}

		tfg.setStocksMap(stockMap);

		return tfg;
	}

	/**
	 * Specify the status color.
	 * 
	 * @param stockList
	 * @param stockArr
	 * @param mPart
	 * @return
	 */
	private List<TFGStock> calculateStatusColor(List<TFGStock> stockList, TFGStock[] stockArr, TFG tfg, MPart mPart) {
		tfg.setFgId(mPart.getFgId());
		tfg.setCustomerId(mPart.getCustomerId());

		Calendar tmpCal = new GregorianCalendar();

		// Loop by stock 1 month
		for (int index = 0; index < stockArr.length; index++) {
			TFGStock fgStock = stockArr[index];
			int fgBalance = fgStock.getFgBalance() == null ? 0 : fgStock.getFgBalance();
			int deliveryBalance = fgStock.getBalanceQty() == null ? 0 : fgStock.getBalanceQty();

			if (deliveryBalance >= 0) {
				fgStock.setStatus(getColorStatus(index, tfg, fgBalance));

			} else {
				if (fgBalance <= Math.abs(deliveryBalance)) {
					fgStock.setStatus(3);
				} else {
					fgBalance -= Math.abs(deliveryBalance);
					fgStock.setStatus(getColorStatus(index, tfg, fgBalance));
				}
			}
			// <!-- Checking if 'reportDate' is the same month. -->
			tmpCal.setTime(fgStock.getReportDate());
			if (tmpCal.get(Calendar.MONTH) == tfg.getMonth()) {
				stockList.add(fgStock);
			}
		}

		return stockList;
	}

	private List<TDeliveryPlanDate> getDeliveryPlans(TFG tfg) {
		Integer limitDay = new Integer(30);
		TFG criteria = new TFG();
		BeanUtils.copyProperties(tfg, criteria);
		Calendar reportDateFrom = new GregorianCalendar();
		reportDateFrom.setTime(criteria.getReportDateFr());

		List<TDeliveryPlanDate> deliveryPlans = new ArrayList<TDeliveryPlanDate>();
		List<TDeliveryPlanDate> deliveryPlansMonth = null;

		Calendar endCal = new GregorianCalendar();
		endCal.setTime(criteria.getReportDateTo());
		endCal.add(Calendar.DAY_OF_YEAR, limitDay);
		int endMonth = endCal.get(Calendar.MONTH);
		int startMonth = reportDateFrom.get(Calendar.MONTH);
		if (startMonth > endMonth) {
			endMonth += 12;
		}

		Calendar endReportDate = (Calendar) endCal.clone();
		Calendar reportDateFr = new GregorianCalendar();
		reportDateFr.setTime(criteria.getReportDateFr());
		Calendar reportDateTo = new GregorianCalendar();
		reportDateTo.setTime(criteria.getReportDateTo());
		do {
			// Get delivery plan to compare
			deliveryPlansMonth = fgStockDao.queryDeliveryPlans(criteria);
			deliveryPlans.addAll(deliveryPlansMonth);

			// <!-- Adjustment criteria. -->
			startMonth++;
			if (startMonth < 9) {
				criteria.setYearMonth(criteria.getYear() + "0" + (startMonth + 1));

			} else if (startMonth > 11) {
				// Case next year
				criteria.setYearMonth((criteria.getYear() + 1) + "0" + (startMonth - 11));

			} else {
				criteria.setYearMonth(criteria.getYear() + "" + (startMonth + 1));

			}

			reportDateFr.setTime(criteria.getReportDateTo());
			reportDateFr.add(Calendar.DAY_OF_YEAR, 1);
			criteria.setReportDateFr(reportDateFr.getTime());
			if (endReportDate.get(Calendar.MONTH) == endMonth) {
				reportDateTo.add(Calendar.DAY_OF_YEAR, limitDay);

			} else if (endReportDate.get(Calendar.MONTH) < endMonth) {
				reportDateTo.add(Calendar.MONTH, 1);
				reportDateTo.add(Calendar.DAY_OF_YEAR, -1);
			}
			criteria.setReportDateTo(reportDateTo.getTime());

		} while (startMonth <= endMonth);

		return deliveryPlans;

	}

	private Integer getNextDeliveryPlanDate(int index, List<TDeliveryPlanDate> deliveryPlans) {
		Integer limitDay = new Integer(30);
		Integer nextDeliveryPlan = 0;
		TDeliveryPlanDate tDeliveryPlanDate = null;

		if (deliveryPlans != null && deliveryPlans.size() > 0) {
			for (int i = 1; i <= limitDay; i++) {
				tDeliveryPlanDate = deliveryPlans.get(index + i);
				if (null != tDeliveryPlanDate) {
					if (null != tDeliveryPlanDate.getBalanceDeliveryQty()
							&& tDeliveryPlanDate.getBalanceDeliveryQty() != 0) {
						nextDeliveryPlan = tDeliveryPlanDate.getBalanceDeliveryQty();
						break;
					}
				}
			}
		}

		return nextDeliveryPlan;
	}

	private Integer summaryDeliveryPlan(int index, List<TDeliveryPlanDate> deliveryPlans) {
		Integer stockHoldDay = new Integer(settings.getProperty("FNG.FNG_S03.stockHoldDay", "4"));
		Integer limitDay = new Integer(30);
		Integer sumDeliveryPlan = 0;
		int havePlan = 0;
		int count = 0;
		TDeliveryPlanDate tDeliveryPlanDate = null;

		if (deliveryPlans != null && deliveryPlans.size() > 0) {
			while (havePlan < stockHoldDay) {
				index++;
				tDeliveryPlanDate = deliveryPlans.get(index);
				if (null != tDeliveryPlanDate) {
					if (null != tDeliveryPlanDate.getBalanceDeliveryQty()
							&& tDeliveryPlanDate.getBalanceDeliveryQty() != 0) {
						sumDeliveryPlan += tDeliveryPlanDate.getBalanceDeliveryQty();
						havePlan++;
					}
					count++;
				}

				if (count >= limitDay) {
					break;
				}
			}
		}

		return sumDeliveryPlan;
	}

	private Integer getColorStatus(int index, TFG tfg, int fgBalance) {
		Integer status = null;
		// Get delivery plan to compare
		List<TDeliveryPlanDate> deliveryPlans = getDeliveryPlans(tfg);

		if (fgBalance < this.getNextDeliveryPlanDate(index, deliveryPlans)) {
			status = 3;
		} else {
			Integer sumdelivery = summaryDeliveryPlan(index, deliveryPlans);
			if (fgBalance == 0 && fgBalance == sumdelivery) {
				status = null;
			} else if (fgBalance < sumdelivery) {
				status = 2;
			} else if (fgBalance >= sumdelivery) {
				status = 1;
			}
		}

		return status;
	}
}