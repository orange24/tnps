package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MUser;
import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MUserAccessWipDao;

@Repository
@SuppressWarnings("unchecked")
public class MUserAccessWipDaoImpl extends AbstractBaseDao implements MUserAccessWipDao {

	@Override
	public List<MWip> getWip( MUser MUser ) {
		return (List<MWip>) queryForList("m_user_access_wip.queryWip", MUser);
	}

	@Override
	public List<MWip> query( MUser MUser ) {
		return (List<MWip>) queryForList("m_user_access_wip.queryAllWip", MUser);
	}

	@Override
	public void insert( MUser MUser ) {
		insert("m_user_access_wip.insert", MUser);
	}

	@Override
	public void delete( MUser MUser ) {
		delete("m_user_access_wip.delete", MUser);
	}
}