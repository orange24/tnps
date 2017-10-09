package th.co.nttdata.tki.blogic.wip.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.bean.TWipStockAdjust;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.wip.WIP_S03Logic;
import th.co.nttdata.tki.dao.MCustomerDao;
import th.co.nttdata.tki.dao.MPartDao;
import th.co.nttdata.tki.dao.MWipDao;
import th.co.nttdata.tki.dao.TWipStockAdjustDao;

@Service
public class WIP_S03LogicImpl extends AbstractBaseLogic implements WIP_S03Logic {

	@Autowired
	private TWipStockAdjustDao wipStockAdjust;

	@Autowired
	private MCustomerDao mCustomerDao;

	@Autowired
	private MPartDao mPartDao;

	@Autowired
	private MWipDao mWipDao;

	@Override
	public TWipStockAdjust search(TWipStockAdjust TWipStockAdjust) {
		TWipStockAdjust = wipStockAdjust.queryEnable(TWipStockAdjust, "");
		return TWipStockAdjust;
	}

	@Override
	public void save(TWipStockAdjust TWipStockAdjust) {
		List<TWipStockAdjust> wipList = new ArrayList<TWipStockAdjust>();
		for (TWipStockAdjust twip : TWipStockAdjust.getAdjustList()) {
			if (null != twip.getAdjustReason() && !twip.getAdjustReason().equals("")) {
				wipList.add(twip);
			}
		}
		TWipStockAdjust.setAdjustList(wipList);

		wipStockAdjust.insert(TWipStockAdjust);
	}

	@Override
	public TWipStockAdjust exportWIP_R03(TWipStockAdjust TWipStockAdjust) {
		TWipStockAdjust = wipStockAdjust.queryWIP_R03(TWipStockAdjust);

		return TWipStockAdjust;
	}

	@Override
	public TWipStockAdjust importWIP_R03(TWipStockAdjust TWipStockAdjust) {
		TWipStockAdjust = validateDataImport(TWipStockAdjust);
		if (null == TWipStockAdjust.getErrors() || 0 == TWipStockAdjust.getErrors().size()) {
			if (null != TWipStockAdjust.getAdjustList() && 0 < TWipStockAdjust.getAdjustList().size()) {
				wipStockAdjust.adjustWipStock(TWipStockAdjust);
				TWipStockAdjust.setAdjustList(null);
			} else {
				TWipStockAdjust.getErrors().add(new Message("err.cmm.003", new String[] {}));
			}
		}

		return TWipStockAdjust;
	}

	private TWipStockAdjust validateDataImport(TWipStockAdjust tWipStockAdjust) {
		try {
			Workbook workbook = Workbook.getWorkbook(tWipStockAdjust.getFileImport().getInputStream());
			Sheet sheet = workbook.getSheet(0);

			List<TWipStockAdjust> dataImport = new ArrayList<TWipStockAdjust>();
			TWipStockAdjust rowData = null;
			String wipName = null;
			String customer = null;
			String partNo = null;
			String partName = null;
			String stockDate = null;
			String wipStockQty = null;
			String adjustStockQty = null;
			String adjustReason = null;
			String wip = null;
			Integer partId = null;
			Date reportDate = null;
			Integer adjustQty = null;
			Integer currentStock = null;

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			dateFormat.setLenient(false);

			//Get Data master to check data is exist
			Map<String, String> wipMap = mWipDao.getAllWipMap();
			Map<String, Integer> customerMap = mCustomerDao.getCustomerMap();
			Map<String, Integer> partMap = mPartDao.getAllPartMap();

			int rowsize = sheet.getRows();
			Cell[] cells = null;

			for (int line = 1; line < rowsize; line++) {
				cells = sheet.getRow(line);
				adjustStockQty = cells[6].getContents().trim();

				if (StringUtils.isNotEmpty(adjustStockQty)) {
					wipName = cells[0].getContents().trim();
					customer = cells[1].getContents().trim();
					partNo = cells[2].getContents().trim();
					partName = cells[3].getContents().trim();
					stockDate = cells[4].getContents().trim();
					wipStockQty = cells[5].getContents().trim();
					adjustReason = cells[7].getContents().trim();
					
					/* Validate data */
					if (StringUtils.isEmpty(wipName)) {
						tWipStockAdjust.getErrors().add(
								new Message("err.cmm.002", new String[] { String.valueOf(line), "WIP" }));
					} else {
						/* Validate WIP exist on system data.*/
						if (wipMap.containsKey(wipName)) {
							wip = wipMap.get(wipName);
						} else {
							tWipStockAdjust.getErrors().add(
									new Message("err.cmm.023", new String[] { String.valueOf(line), "WIP" }));
						}
					}
					if (StringUtils.isEmpty(customer)) {
						tWipStockAdjust.getErrors().add(
								new Message("err.cmm.002", new String[] { String.valueOf(line), "Customer" }));
					} else {
						/* Validate customer exist on system data.*/
						if (!customerMap.containsKey(customer)) {
							tWipStockAdjust.getErrors().add(
									new Message("err.cmm.023", new String[] { String.valueOf(line), "Customer" }));
						}
					}
					if (StringUtils.isEmpty(partNo)) {
						tWipStockAdjust.getErrors().add(
								new Message("err.cmm.002", new String[] { String.valueOf(line), "Part No" }));
					} else {
						/* Validate part exist on system data.*/
						if (partMap.containsKey(partNo)) {
							partId = partMap.get(partNo);
						} else {
							tWipStockAdjust.getErrors().add(
									new Message("err.cmm.023", new String[] { String.valueOf(line), "Part No" }));
						}
					}
					if (StringUtils.isEmpty(partName)) {
						tWipStockAdjust.getErrors().add(
								new Message("err.cmm.002", new String[] { String.valueOf(line), "Part Name" }));
					}
					if (StringUtils.isEmpty(stockDate)) {
						tWipStockAdjust.getErrors().add(
								new Message("err.cmm.002", new String[] { String.valueOf(line), "Stock Date" }));
					} else {
						/* Validate type date of stock date.*/
						try {
							reportDate = dateFormat.parse(stockDate);
						} catch (Exception ex) {
							tWipStockAdjust.getErrors().add(
									new Message("err.cmm.025", new String[] {
											String.valueOf(line),
											"Stock Date",
											"dd/MM/yyyy" }));
						}
					}
					if (StringUtils.isEmpty(wipStockQty)) {
						tWipStockAdjust.getErrors().add(
								new Message("err.cmm.002", new String[] { String.valueOf(line), "WIP Stock Qty" }));
					} else {
						/* Validate type integer of WIP stock qty.*/
						try {
							wipStockQty = wipStockQty.replace(",", "");
							currentStock = Integer.parseInt(wipStockQty);
						} catch (Exception ex) {
							tWipStockAdjust.getErrors().add(
									new Message("err.cmm.019", new String[] { String.valueOf(line), "WIP Stock Qty" }));
						}
					}
					/* Validate type integer of adjust stock qty and value to adjust must be more than 0.*/
					try {
						adjustStockQty = adjustStockQty.replace(",", "");
						adjustQty = Integer.parseInt(adjustStockQty);
						if (adjustQty < 0) {
							tWipStockAdjust.getErrors().add(
									new Message("err.cmm.009",
											new String[] { String.valueOf(line), "Adjust Stock Qty" }));
						}
					} catch (Exception ex) {
						tWipStockAdjust.getErrors().add(
								new Message("err.cmm.019", new String[] { String.valueOf(line), "Adjust Stock Qty" }));
					}
					if (StringUtils.isEmpty(adjustReason)) {
						tWipStockAdjust.getErrors().add(
								new Message("err.cmm.002", new String[] { String.valueOf(line), "Adjust Reason" }));
					}
	
					if (null == tWipStockAdjust.getErrors() || 0 == tWipStockAdjust.getErrors().size()) {
						rowData = new TWipStockAdjust();
						rowData.setReportDate(reportDate);
						rowData.setWip(wip);
						rowData.setPartId(partId);
						rowData.setCurrentStock(currentStock);
						rowData.setAdjustStock(adjustQty);
						rowData.setAdjustReason(adjustReason);
						dataImport.add(rowData);
					}
				}
			}

			tWipStockAdjust.setAdjustList(dataImport);

		} catch (Exception ex) {
			ex.printStackTrace();
			tWipStockAdjust.getErrors().add(new Message("err.cmm.007", new String[] {}));
		}

		return tWipStockAdjust;
	}
}
