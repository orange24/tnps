package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.MMenu;

public interface MMenuDao {

	public List<MMenu> getMenuByLoginUser();
}