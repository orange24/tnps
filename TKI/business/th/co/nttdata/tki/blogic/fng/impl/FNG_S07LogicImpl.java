package th.co.nttdata.tki.blogic.fng.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.PrintTagChangedHistory;
import th.co.nttdata.tki.blogic.fng.FNG_S07Logic;
import th.co.nttdata.tki.dao.TFGJunkDao;
import th.co.nttdata.tki.dao.TFGStockDao;
import th.co.nttdata.tki.dao.TTaglabelHistoryDao;

@Service
public class FNG_S07LogicImpl implements FNG_S07Logic {
	
	@Autowired
	TTaglabelHistoryDao tTagLabelHistoryDao;
	
	@Autowired
	TFGStockDao tfgStockDao;

	@Override
	public List<PrintTagChangedHistory> searchTag(PrintTagChangedHistory tfgJunk) {
		return tTagLabelHistoryDao.queryTagChanged(tfgJunk);
	}


}
