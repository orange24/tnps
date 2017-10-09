package th.co.nttdata.tki.blogic.fng.impl;

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
import th.co.nttdata.tki.bean.TFGStock;
import th.co.nttdata.tki.blogic.fng.FNG_S04Logic;
import th.co.nttdata.tki.dao.MCustomerDao;
import th.co.nttdata.tki.dao.MFgMasterDao;
import th.co.nttdata.tki.dao.TFGStockDao;

@Service
public class FNG_S04LogicImpl implements FNG_S04Logic {
	@Autowired
	private TFGStockDao tfgStockDao;

	@Autowired
	private MCustomerDao mCustomerDao;

	@Autowired
	private MFgMasterDao mFgMasterDao;

	@Override
	public TFGStock searchStockAdjust(TFGStock tfgStock) {
		return tfgStockDao.queryStockAdjust(tfgStock);
	}

	@Override
	public void adjustStock(TFGStock tfgStock) {
		tfgStockDao.adjustStock(tfgStock);
	}

	@Override
	public TFGStock exportFNG_R04(TFGStock tfgStock) {
		tfgStock = tfgStockDao.queryFNG_R04(tfgStock);

		return tfgStock;
	}

	@Override
	public TFGStock importFNG_R04(TFGStock tfgStock) {
		tfgStock = validateDataImport(tfgStock);
		if (null == tfgStock.getErrors() || 0 == tfgStock.getErrors().size()) {
			if (null != tfgStock.getTfgStockList() && 0 < tfgStock.getTfgStockList().size()) {
				tfgStockDao.adjustFgStock(tfgStock);
				tfgStock.setTfgStockList(null);
			} else {
				tfgStock.getErrors().add(new Message("err.cmm.003", new String[] {}));
			}
		}

		return tfgStock;
	}

	private TFGStock validateDataImport(TFGStock tfgStock) {
		try {
			Workbook workbook = Workbook.getWorkbook(tfgStock.getFileImport().getInputStream());
			Sheet sheet = workbook.getSheet(0);

			List<TFGStock> dataImport = new ArrayList<TFGStock>();
			TFGStock rowData = null;
			String customer = null;
			String fgNo = null;
			String fgName = null;
			String stockDate = null;
			String fgStockBalance = null;
			String adjustStockQty = null;
			String adjustReason = null;
			Integer customerId = null;
			Integer fgId = null;
			Date reportDate = null;
			Integer adjustQty = null;
			Integer currentStock = null;

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			dateFormat.setLenient(false);

			//Get Data master to check data is exist
			Map<String, Integer> customerMap = mCustomerDao.getCustomerMap();
			Map<String, Integer> fgMap = mFgMasterDao.getAllFgMap();

			int rowsize = sheet.getRows();
			Cell[] cells = null;

			for (int line = 1; line < rowsize; line++) {
				cells = sheet.getRow(line);
				adjustStockQty = cells[5].getContents().trim();

				if (StringUtils.isNotEmpty(adjustStockQty)) {
					customer = cells[0].getContents().trim();
					fgNo = cells[1].getContents().trim();
					fgName = cells[2].getContents().trim();
					stockDate = cells[3].getContents().trim();
					fgStockBalance = cells[4].getContents().trim();
					adjustReason = cells[6].getContents().trim();
	
					/* Validate data */
					if (StringUtils.isEmpty(customer)) {
						tfgStock.getErrors().add(
								new Message("err.cmm.002", new String[] { String.valueOf(line), "Customer" }));
					} else {
						/* Validate customer exist on system data.*/
						if (customerMap.containsKey(customer)) {
							customerId = customerMap.get(customer);
						} else {
							tfgStock.getErrors().add(
									new Message("err.cmm.023", new String[] { String.valueOf(line), "Customer" }));
						}
					}
					if (StringUtils.isEmpty(fgNo)) {
						tfgStock.getErrors()
								.add(new Message("err.cmm.002", new String[] { String.valueOf(line), "FG No" }));
					} else {
						/* Validate FG exist on system data.*/
						if (fgMap.containsKey(fgNo)) {
							fgId = fgMap.get(fgNo);
						} else {
							tfgStock.getErrors().add(
									new Message("err.cmm.023", new String[] { String.valueOf(line), "FG No" }));
						}
					}
					if (StringUtils.isEmpty(fgName)) {
						tfgStock.getErrors().add(
								new Message("err.cmm.002", new String[] { String.valueOf(line), "FG Name" }));
					}
					if (StringUtils.isEmpty(stockDate)) {
						tfgStock.getErrors().add(
								new Message("err.cmm.002", new String[] { String.valueOf(line), "Stock Date" }));
					} else {
						/* Validate type date of stock date.*/
						try {
							reportDate = dateFormat.parse(stockDate);
						} catch (Exception ex) {
							tfgStock.getErrors().add(
									new Message("err.cmm.025", new String[] {
											String.valueOf(line),
											"Stock Date",
											"dd/MM/yyyy" }));
						}
					}
					if (StringUtils.isEmpty(fgStockBalance)) {
						tfgStock.getErrors().add(
								new Message("err.cmm.002", new String[] {
										String.valueOf(line),
										"Before Adjust Stock(Balance)" }));
					} else {
						/* Validate type integer of FG stock balance.*/
						try {
							fgStockBalance = fgStockBalance.replace(",", "");
							currentStock = Integer.parseInt(fgStockBalance);
						} catch (Exception ex) {
							tfgStock.getErrors().add(
									new Message("err.cmm.019", new String[] {
											String.valueOf(line),
											"Before Adjust Stock(Balance)" }));
						}
					}
					/* Validate type integer of adjust stock qty and value to adjust must be more than 0.*/
					try {
						adjustStockQty = adjustStockQty.replace(",", "");
						adjustQty = Integer.parseInt(adjustStockQty);
						if (adjustQty < 0) {
							tfgStock.getErrors().add(
									new Message("err.cmm.009", new String[] {
											String.valueOf(line),
											"Adjust Stock Qty(Balance)" }));
						}
					} catch (Exception ex) {
						tfgStock.getErrors().add(
								new Message("err.cmm.019", new String[] {
										String.valueOf(line),
										"Adjust Stock Qty(Balance)" }));
					}
					if (StringUtils.isEmpty(adjustReason)) {
						tfgStock.getErrors().add(
								new Message("err.cmm.002", new String[] { String.valueOf(line), "Adjust Reason" }));
					}
	
					if (null == tfgStock.getErrors() || 0 == tfgStock.getErrors().size()) {
						rowData = new TFGStock();
						rowData.setReportDate(reportDate);
						rowData.setCustomerId(customerId);
						rowData.setFgId(fgId);
						rowData.setFgBalance(currentStock);
						rowData.setFgAdjust(adjustQty);
						rowData.setAdjustReason(adjustReason);
						dataImport.add(rowData);
					}
				}
			}

			tfgStock.setTfgStockList(dataImport);

		} catch (Exception ex) {
			ex.printStackTrace();
			tfgStock.getErrors().add(new Message("err.cmm.007", new String[] {}));
		}

		return tfgStock;
	}

}
