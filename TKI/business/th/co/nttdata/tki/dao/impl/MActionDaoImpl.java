package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MAction;
import th.co.nttdata.tki.bean.MRole;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MActionDao;

@Repository
@SuppressWarnings("unchecked")
public class MActionDaoImpl extends AbstractBaseDao implements MActionDao {

	@Override
	public List<MAction> getAction( MRole MRole ) {
		return (List<MAction>) queryForList("m_action.queryAction", MRole);
	}
}