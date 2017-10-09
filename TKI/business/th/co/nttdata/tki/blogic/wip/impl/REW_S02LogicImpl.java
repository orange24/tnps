package th.co.nttdata.tki.blogic.wip.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.TReworkAdjust;
import th.co.nttdata.tki.blogic.wip.REW_S02Logic;
import th.co.nttdata.tki.dao.TReworkAdjustDao;

@Service
public class REW_S02LogicImpl implements REW_S02Logic {

	@Autowired
	TReworkAdjustDao TReworkAdjustDao;
	
	@Override
	public TReworkAdjust search(TReworkAdjust TReworkAdjust) {
		return TReworkAdjustDao.queryHis(TReworkAdjust);
	}

}
