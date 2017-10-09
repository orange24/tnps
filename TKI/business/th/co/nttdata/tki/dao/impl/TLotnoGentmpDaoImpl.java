package th.co.nttdata.tki.dao.impl;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TLotnoGentmpDao;

@Repository
@SuppressWarnings("unchecked")
public class TLotnoGentmpDaoImpl extends AbstractBaseDao implements
		TLotnoGentmpDao {

	@Override
	public void updateMergeLotNo() {
		update("t_lotno_gentmp.update_merge_lot");
	}

}