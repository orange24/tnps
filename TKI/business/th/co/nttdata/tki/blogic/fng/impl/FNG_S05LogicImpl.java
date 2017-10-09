package th.co.nttdata.tki.blogic.fng.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.TFGStock;
import th.co.nttdata.tki.blogic.fng.FNG_S05Logic;
import th.co.nttdata.tki.dao.TFGStockDao;

@Service
public class FNG_S05LogicImpl implements FNG_S05Logic {
	@Autowired
	private TFGStockDao tfgStockDao;
	
	@Override
	public TFGStock searchFGAdjustHistory(TFGStock tfgStock) {
		return tfgStockDao.queryFGAdjustHistory(tfgStock);
	}

}
