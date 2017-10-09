package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MMenu;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MMenuDao;

@Repository
@SuppressWarnings("unchecked")
public class MMenuDaoImpl extends AbstractBaseDao implements MMenuDao {

	@Override
	public List<MMenu> getMenuByLoginUser() {
		return (List<MMenu>) queryForList("m_menu.queryMenu", new MMenu());
	}

}