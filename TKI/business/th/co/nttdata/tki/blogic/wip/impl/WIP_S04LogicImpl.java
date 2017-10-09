package th.co.nttdata.tki.blogic.wip.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.TWipStockAdjust;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.wip.WIP_S04Logic;
import th.co.nttdata.tki.dao.TWipStockAdjustDao;

@Service
public class WIP_S04LogicImpl extends AbstractBaseLogic implements WIP_S04Logic  {
	
	@Autowired
	private TWipStockAdjustDao wipStockAdjust;

	@Override
	public TWipStockAdjust search(TWipStockAdjust TWipStockAdjust) {		
		TWipStockAdjust = wipStockAdjust.queryHis(TWipStockAdjust);		
		return TWipStockAdjust;
	}

	@Override
	public TWipStockAdjust exportWIP_R04(TWipStockAdjust TWipStockAdjust){		
		TWipStockAdjust = wipStockAdjust.queryWIP_R04(TWipStockAdjust);		
		return TWipStockAdjust;
	}

}
