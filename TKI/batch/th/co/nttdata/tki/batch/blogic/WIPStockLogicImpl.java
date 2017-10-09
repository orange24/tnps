package th.co.nttdata.tki.batch.blogic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import th.co.nttdata.batch.BatchLogicImpl;
import th.co.nttdata.tki.batch.bean.TWIPStock;

import com.ibatis.sqlmap.client.SqlMapClient;

@Service("WIPStock")
public class WIPStockLogicImpl extends BatchLogicImpl {

	private static final String BATCH_CODE = "WIP_B01";
	private static final String BATCH_NAME = "WIP Stock Calculate";

	@Autowired
	@Qualifier("sqlsvrMapClient")
	private SqlMapClient sqlMap;

	@Override
	public void processing() throws Exception {

		log.info("config data Initialize");
		List<TWIPStock> listDatePart = new ArrayList<TWIPStock>();
		List<TWIPStock> twipList = new ArrayList<TWIPStock>();
		List<TWIPStock> allPartList = new ArrayList<TWIPStock>();
		TWIPStock twip = null;
		TWIPStock twipLast = null;
		Integer maxWIPOrder = null;
		Integer checkWIP = null;
		Integer partId = null;
		Calendar reportDate = new GregorianCalendar(Locale.US);
		Calendar today = new GregorianCalendar(Locale.US);
		Boolean hasError = false;
		Integer sumIscalc = null;
		// query part and min date of update DC MC WK
		log.info("t_wip_stock_batch.queryMinDate is running.");

		Map<String, Object> p = new HashMap<String, Object>();
		p.put("executeDate",
				this.getExecuteDate() != null ? this.getExecuteDate()
						: new Date());
		p.put("partId", partId);
		p.put("runBy", getExecutedBy());
		p.put("retCode", 0);

		sqlMap.queryForObject("t_wip_stock_batch.selectWipStockCalculation", p);

		// listDatePart = sqlMap.queryForList("t_wip_stock_batch.queryMinDate",
		// p);
		//
		// for (TWIPStock datePart : listDatePart) {
		// //
		// log.info("t_wip_stock_batch.delectWIPStock is running.(Delete old WIP Stock)");
		// sqlMap.delete("t_wip_stock_batch.delectWIPStock", datePart);
		//
		// reportDate.setTime(datePart.getReportDate());
		// for (; today.get(Calendar.YEAR) >= reportDate.get(Calendar.YEAR)
		// && today.get(Calendar.DAY_OF_YEAR) >= reportDate
		// .get(Calendar.DAY_OF_YEAR); reportDate.add(
		// Calendar.DAY_OF_YEAR, 1)) {
		// // log.info(datePart.getPartId() +
		// // " : t_wip_stock_batch.checkIsIcalc is running.");
		// sumIscalc = (Integer) sqlMap.queryForObject(
		// "t_wip_stock_batch.checkIsIcalc", datePart.getPartId());
		//
		// if (null != sumIscalc) {
		// twip = new TWIPStock();
		// twip.setReportDate(reportDate.getTime());
		// twip.setPartId(datePart.getPartId());
		// // log.info(datePart.getPartId() + " - " +
		// // reportDate.getTime() +
		// // " : t_wip_stock_batch.insertQueryWIPStock is running.");
		// sqlMap.insert("t_wip_stock_batch.insertQueryWIPStock", twip);
		//
		// // log.info(datePart.getPartId() + " - " +
		// // reportDate.getTime() +
		// // " : t_wip_stock_batch.queryWipOrder is running.");
		// twipList = sqlMap.queryForList(
		// "t_wip_stock_batch.queryWipOrder", twip);
		//
		// // log.info(datePart.getPartId() + " - " +
		// // reportDate.getTime() + " : Find ");
		// for (TWIPStock wipStock : twipList) {
		// wipStock.setReportDate(reportDate.getTime());
		// // log.info(datePart.getPartId() + " - " +
		// // reportDate.getTime() +
		// // " : t_wip_stock_batch.queryFGId is running.");
		// wipStock.setFgId((Integer) sqlMap.queryForObject(
		// "t_wip_stock_batch.queryFGId",
		// wipStock.getPartId()));
		//
		// // log.info(datePart.getPartId() + " - " +
		// // reportDate.getTime() +
		// // " : t_wip_stock_batch.queryMaxWIPOrder is running.");
		// maxWIPOrder = (Integer) sqlMap.queryForObject(
		// "t_wip_stock_batch.queryMaxWIPOrder",
		// wipStock.getPartId());
		//
		// try {
		// // log.info(datePart.getPartId() + " - " +
		// // reportDate.getTime() +
		// // " : t_wip_stock_batch.checkExistnextWIP is running.");
		// checkWIP = (Integer) sqlMap.queryForObject(
		// "t_wip_stock_batch.checkExistnextWIP",
		// wipStock);
		//
		// if (checkWIP != null && checkWIP == 1) {
		// // log.info(datePart.getPartId() + " - " +
		// // reportDate.getTime() +
		// // " : t_wip_stock_batch.queryNextWIP is running.");
		// wipStock.setNextWIP((String) sqlMap
		// .queryForObject(
		// "t_wip_stock_batch.queryNextWIP",
		// wipStock));
		//
		// // log.info(datePart.getPartId() + " - " +
		// // reportDate.getTime() +
		// // " : t_wip_stock_batch.queryQtyNextWip is running.");
		// wipStock.setNextWIPQty((Integer) sqlMap
		// .queryForObject(
		// "t_wip_stock_batch.queryQtyNextWip",
		// wipStock));
		//
		// } else if (maxWIPOrder.compareTo(wipStock
		// .getWipOrder()) == 0) {
		// // log.info(datePart.getPartId() + " - " +
		// // reportDate.getTime() +
		// // " : t_taglabel_history.check_taglabel_changed is running.");
		// Integer newFgId = (Integer) sqlMap
		// .queryForObject(
		// "t_taglabel_history.check_taglabel_changed",
		// wipStock);
		// if (newFgId != null) {
		// wipStock.setFgId(newFgId);
		// }
		// // log.info(datePart.getPartId() + " - " +
		// // reportDate.getTime() +
		// // " : t_wip_stock_batch.queryFGIn is running.");
		// wipStock.setNextWIPQty((Integer) sqlMap
		// .queryForObject(
		// "t_wip_stock_batch.queryFGIn",
		// wipStock));
		// } else {
		// partId = wipStock.getPartId();
		//
		// // log.info(datePart.getPartId() + " - " +
		// // reportDate.getTime() +
		// // " : t_wip_stock_batch.queryAssyPart is running.");
		// wipStock.setPartId((Integer) sqlMap
		// .queryForObject(
		// "t_wip_stock_batch.queryAssyPart",
		// wipStock));
		//
		// // log.info(datePart.getPartId() + " - " +
		// // reportDate.getTime() +
		// // " : t_wip_stock_batch.queryNextWIP(ASSY) is running.");
		// wipStock.setNextWIP((String) sqlMap
		// .queryForObject(
		// "t_wip_stock_batch.queryNextWIP",
		// wipStock));
		//
		// // log.info(datePart.getPartId() + " - " +
		// // reportDate.getTime() +
		// // " : t_wip_stock_batch.queryQtyNextWip(ASSY) is running.");
		// wipStock.setNextWIPQty((Integer) sqlMap
		// .queryForObject(
		// "t_wip_stock_batch.queryQtyNextWip",
		// wipStock));
		//
		// wipStock.setPartId(partId);
		// }
		//
		// // log.info(datePart.getPartId() + " - " +
		// // reportDate.getTime() +
		// // " : t_wip_stock_batch.updateWIPStock is running.");
		// sqlMap.update("t_wip_stock_batch.updateWIPStock",
		// wipStock);
		//
		// } catch (Exception e) {
		// hasError = true;
		// log.info("************* " + datePart.getPartId()
		// + " - " + wipStock.getFgId());
		// log.error(e.toString());
		// }
		// }
		// }
		// }
		// }
		//
		// // Case WIP not update
		// log.info("t_wip_stock_batch.queryAllPartWip is running.");
		// allPartList =
		// sqlMap.queryForList("t_wip_stock_batch.queryAllPartWip",
		// p);
		//
		// if (allPartList.size() > 0) {
		// for (TWIPStock partWip : allPartList) {
		// // log.info(partWip.getPartId()
		// // + "-"
		// // + partWip.getWip()
		// // + " : t_wip_stock_batch.queryLastStock is running.");
		// twipLast = (TWIPStock) sqlMap.queryForObject(
		// "t_wip_stock_batch.queryLastStock", partWip);
		// reportDate.setTime(partWip.getReportDate());
		// reportDate.add(Calendar.DAY_OF_YEAR, 1);
		// if (twipLast != null) {
		// while (today.get(Calendar.YEAR) >= reportDate
		// .get(Calendar.YEAR)
		// && today.get(Calendar.DAY_OF_YEAR) >= reportDate
		// .get(Calendar.DAY_OF_YEAR)) {
		// twipLast.setReportDate(reportDate.getTime());
		// // log.info(partWip.getPartId()
		// // + " - "
		// // + reportDate.toString()
		// // + " : t_wip_stock_batch.insertAllWip is running.");
		// sqlMap.insert("t_wip_stock_batch.insertAllWip",
		// twipLast);
		// reportDate.add(Calendar.DAY_OF_YEAR, 1);
		// }
		// }
		// }
		// }

		if (hasError) {
			// update m_batch_control in case success but have exception.
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("batchcode", BATCH_CODE);
			param.put("batchname", BATCH_NAME);
			param.put("batchstatus", 3);
			param.put("runby", getExecutedBy());
			sqlMap.update("m_batch_control.updateMBatchControl", param);

		} else {
			// update m_batch_control in case success.
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("batchcode", BATCH_CODE);
			param.put("batchname", BATCH_NAME);
			param.put("batchstatus", 0);
			param.put("runby", getExecutedBy());
			sqlMap.update("m_batch_control.updateMBatchControl", param);
		}
	}

	@Override
	public void preProcessing() throws Exception {
		// MERGE m_batch_control
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 1);
		param.put("runby", getExecutedBy());
		sqlMap.insert("m_batch_control.insertMBatchControl", param);
	}

	@Override
	public void postException() throws Exception {
		// update m_batch_controlin case fail
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 2);
		param.put("runby", getExecutedBy());
		sqlMap.update("m_batch_control.updateMBatchControl", param);
	}

}
