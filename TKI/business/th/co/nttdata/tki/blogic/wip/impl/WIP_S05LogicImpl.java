package th.co.nttdata.tki.blogic.wip.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.TWipJunk;
import th.co.nttdata.tki.bean.TWipStockAdjust;
import th.co.nttdata.tki.blogic.wip.WIP_S05Logic;
import th.co.nttdata.tki.dao.TWipJunkDao;
import th.co.nttdata.tki.dao.TWipStockAdjustDao;

@Service
public class WIP_S05LogicImpl implements WIP_S05Logic{
	
	@Autowired
	TWipJunkDao tWipJunkDao;
	
	@Autowired
	TWipStockAdjustDao tWipStockAdjustDao;

	@Override
	public TWipJunk searchWipJunk(TWipJunk tWipJunk) {
		tWipJunk = tWipJunkDao.queryWipJunk(tWipJunk);
		return tWipJunk;
	}

	public Date generateDateWithZeroTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder str = new StringBuilder();
		str.append(sdf.format(new Date())).append(" 00:00:00.000");
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(str.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	@Override
	public void adjustWipJunk(TWipJunk tWipJunk) {
		List<TWipJunk> junkList = tWipJunk.getJunkList();
		if(0 < junkList.size()){
			for(TWipJunk wipJunk : tWipJunk.getJunkList()){
				if(null != wipJunk.getIsChecked()){
					tWipJunkDao.insertWipJunk(wipJunk);
					Integer currentStock = tWipJunkDao.getCurrentStock(wipJunk);
					TWipStockAdjust tWipStockAdjust = new TWipStockAdjust();
					TWipStockAdjust tWipStockAdjustTemp = new TWipStockAdjust();
					List<TWipStockAdjust> tWipStockAdjusts = new LinkedList<TWipStockAdjust>(); 
					// Get Current Date is Time (00.00.00.000).
					Date date = generateDateWithZeroTime();
					tWipStockAdjust.setReportDate(date);
					tWipStockAdjustTemp.setWip(wipJunk.getWip());
					tWipStockAdjustTemp.setPartId(wipJunk.getPartId());
					tWipStockAdjustTemp.setCurrentStock(currentStock);
					tWipStockAdjustTemp.setAdjustStock(currentStock-wipJunk.getJunkQty());
					tWipStockAdjustTemp.setAdjustReason("Adjusted by WIPJunk");
					tWipStockAdjusts.add(tWipStockAdjustTemp);
					tWipStockAdjust.setAdjustList(tWipStockAdjusts);
					tWipStockAdjustDao.insert(tWipStockAdjust);
				}
			}
		}
	}
	
}
