package th.co.nttdata.tki.blogic.dal.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.TPending;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.dal.PND_S02Logic;
import th.co.nttdata.tki.dao.TPendingDao;

@Service
public class PND_S02LogicImpl extends AbstractBaseLogic implements PND_S02Logic {

	@Autowired
	TPendingDao TPendingDAO;
	
	@Override
	public TPending search(TPending TPending) {
		return TPendingDAO.queryHis(TPending);
	}
	
}
