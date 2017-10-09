package th.co.nttdata.tki.blogic.fng.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.TFG;
import th.co.nttdata.tki.bean.TFGDetail;
import th.co.nttdata.tki.blogic.fng.FNG_S02Logic;
import th.co.nttdata.tki.dao.TFGDetailDao;

@Service
public class FNG_S02LogicImpl implements FNG_S02Logic {
	@Autowired
	private TFGDetailDao tfgDetailDao;

	@Override
	public TFG search(TFG TFG) {
		return tfgDetailDao.query(TFG);
	}

	@Override
	public TFG exportFNG_R01(TFG TFG) {
		return tfgDetailDao.selectFNG_R01(TFG);
	}

	@Override
	public TFG exportFNG_R03(TFG TFG) {
		return tfgDetailDao.selectFNG_R03(TFG);
	}

	@Override
	public List<TFGDetail> getReportTypeList() {
		return tfgDetailDao.queryReportType();
	}

}
