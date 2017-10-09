package th.co.nttdata.tki.dao.impl;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MDocControl;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MDocDao;

@Repository
@SuppressWarnings("unchecked")
public class MDocDaoImpl  extends AbstractBaseDao implements MDocDao {

	@Override
	public MDocControl getDocNo() {
		return (MDocControl) queryForObject("m_doccontrol.queryDoc");
	}

	@Override
	public int updateDocno(MDocControl doc) {
		return update("m_doccontrol.update_doc", doc);
	}

}
