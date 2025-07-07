package th.co.nttdata.tki.blogic.wip.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.TReworkAdjust;
import th.co.nttdata.tki.blogic.wip.REW_S01Logic;
import th.co.nttdata.tki.dao.TReworkAdjustDao;

@Service
public class REW_S01LogicImpl implements REW_S01Logic {

	@Autowired
	TReworkAdjustDao TReworkAdjustDao;

	@Override
	public TReworkAdjust search(TReworkAdjust TReworkAdjust) {
		return TReworkAdjustDao.query(TReworkAdjust);
	}

	@Override
	public void save(TReworkAdjust TReworkAdjust) {

		List<TReworkAdjust> adjList = new ArrayList<TReworkAdjust>();

		for (TReworkAdjust reworkAdj : TReworkAdjust.getAdjustList()) {
			if (reworkAdj.getOk() != null || reworkAdj.getNg() != null || reworkAdj.getStatus() != null) {
				if (reworkAdj.getOk() == null)
					reworkAdj.setOk(0);
				if (reworkAdj.getNg() == null)
					reworkAdj.setNg(0);
				adjList.add(reworkAdj);
			}
		}

		TReworkAdjust.setAdjustList(adjList);

		TReworkAdjustDao.insert(TReworkAdjust);
	}

}
