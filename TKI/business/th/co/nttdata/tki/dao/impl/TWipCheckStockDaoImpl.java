package th.co.nttdata.tki.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.bean.TWipCheckStock;
import th.co.nttdata.tki.bean.TWipFgMaping;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TWipCheckStockDao;

@Repository
@SuppressWarnings("unchecked")
public class TWipCheckStockDaoImpl extends AbstractBaseDao implements TWipCheckStockDao {

        @Override
	public TWipCheckStock getWipFg() {
		return getWipFg(null);
	}

	@Override
	public TWipCheckStock getWipFg(TWipCheckStock TWipChekStock) {
		if (TWipChekStock == null)
			TWipChekStock = new TWipCheckStock();
                
                Map<String, List<TWipFgMaping>> fgMap = new LinkedHashMap<String, List<TWipFgMaping>>();
                Map<Date, List<TWipFgMaping>> fgMapKey = new LinkedHashMap<Date, List<TWipFgMaping>>();
                Map<String, List<TWipFgMaping>> fgMapDay = new LinkedHashMap<String, List<TWipFgMaping>>();
//                Map<String, List<TWipFgMaping>> fgMapPartDay = new LinkedHashMap<String, List<TWipFgMaping>>();
                
                List<TWipFgMaping> fgList = queryForList("t_wip_check_stock.query", TWipChekStock);
                List<TWipFgMaping> fgs = null;
                List<TWipFgMaping> fgsKey = null;
                List<TWipFgMaping> fgsDay = null;
                
                Iterator<TWipFgMaping> iterator = fgList.iterator();
		String keyCusPartWip = "";
//                String keyCusPartDay = "";
                Date keyDay;
                String keyCusPartWipProcessDay = "";
//                Integer afterQty = 0;
//                Integer adjQty = 0;
//                Integer currentQty = 0;
//                Integer pdQty = 0;
//                Integer totals = 0;
//                Integer sumWip = 0;
//                Integer sumPd = 0;
//                Integer sumTotal = 0;
//                Integer wipOrd = 0;
                
                while (iterator.hasNext()) {
                                                           
                    TWipFgMaping wipFG = iterator.next();        
                    
                    keyDay = wipFG.getReportDate();     
                    keyCusPartWip = wipFG.getCustomerid() + "" + wipFG.getPartid() + wipFG.getWip() + wipFG.getWiporder();  
                    keyCusPartWipProcessDay = wipFG.getCustomerid() + "" + wipFG.getPartid() + wipFG.getWip() + wipFG.getWiporder() + wipFG.getReportDate(); 
                    
                    fgMapKey.put(keyDay, fgsKey = new LinkedList<TWipFgMaping>());                                     
                    fgMap.put(keyCusPartWip, fgs = new LinkedList<TWipFgMaping>());
                    fgMapDay.put(keyCusPartWipProcessDay, fgsDay = new LinkedList<TWipFgMaping>());
                    
//                    afterQty = (null == wipFG.getStockAfterQty() ? 0 : wipFG.getStockAfterQty());
//                    pdQty = (null == wipFG.getPreWipPdQty() ? 0 :wipFG.getPreWipPdQty());  
//                    
//                    if (wipFG.getStockAdjustQty()!= null) {
//                       adjQty = (null == wipFG.getStockAdjustQty() ? 0 : wipFG.getStockAdjustQty()); 
//                       wipOrd = wipFG.getWiporder();
//                    }
//
//                    if (wipFG.getStockAdjustQty()!= null) {
//                        afterQty = afterQty + adjQty;   
//                        wipFG.setStockAdjustQty(adjQty);
//                    }
//                    else{
//                        if (wipFG.getWiporder()== wipOrd){
//                            afterQty = afterQty + adjQty; 
//                        }
//                    }
//                    
//                    wipFG.setCurrentStockQty(afterQty);
//
//                    totals = afterQty + pdQty;
//                    wipFG.setTotals(totals);
//                    
//                    Date date1 = wipFG.getReportDate();
//                    Calendar cal = Calendar.getInstance();
//                    cal.setTime(date1);
//                    int day1 = cal.get(Calendar.DATE);
//                    
//                    if (day1 == wipFG.getReportDay()){
//                        sumWip += afterQty;
//                        sumPd += pdQty;
//                        sumTotal += totals;
//                    }
//
//                    wipFG.setSumWip(sumWip);
//                    wipFG.setSumPending(sumPd);
//                    wipFG.setSumTotal(sumTotal);
                    
                    fgsDay.add(wipFG);
                    fgsKey.add(wipFG);
                    fgs.add(wipFG);
                }
                                             
                TWipChekStock.setFgMap(fgMap);
                TWipChekStock.setFgMapKey(fgMapKey);
                TWipChekStock.setFgMapDay(fgMapDay);
                
		if (fgMap.size() < 1)
			TWipChekStock.getInfos().add(new Message("inf.cmm.004", null));
		return TWipChekStock;
	}        
}