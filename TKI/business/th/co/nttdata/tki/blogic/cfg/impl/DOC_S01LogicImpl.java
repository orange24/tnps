package th.co.nttdata.tki.blogic.cfg.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MDocControl;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.cfg.DOC_S01Logic;
import th.co.nttdata.tki.dao.MDocDao;

@Service
public class DOC_S01LogicImpl extends AbstractBaseLogic implements DOC_S01Logic {

	@Autowired
	MDocDao mDocDAO;

	@Override
	public MDocControl getDocNo() {
		return mDocDAO.getDocNo();
	}

	@Override
	public void saveDocNo(MDocControl docControl) {
		mDocDAO.updateDocno(docControl);
	}

}
