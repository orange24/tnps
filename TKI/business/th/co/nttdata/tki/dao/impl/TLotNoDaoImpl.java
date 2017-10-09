package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.TDCPlan;
import th.co.nttdata.tki.bean.TLotNo;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TLotNoDao;

@Repository
@SuppressWarnings("unchecked")
public class TLotNoDaoImpl extends AbstractBaseDao implements TLotNoDao {

	@Override
	public List<TLotNo> getLotNoAuto(String lotNo) {
		return queryForList("t_lotno.select_lotno_auto", lotNo);
	}

	@Override
	public void deleteLotNo(TDCPlan object) {
		delete("t_lotno.delete_lotno", object);
	}

	@Override
	public void insertLotNo(TDCPlan object) {
		insert("t_lotno.insert_lotno", object);
	}

	@Override
	public void prdS03DeleteLotNo(TDCPlan object) {
		delete("t_lotno.prdS03_delete_lotno", object);
	}

	@Override
	public List<TDCPlan> getDataForPrint(TDCPlan param) {
		return queryForList("t_lotno.select_print", param);
	}

	@Override
	public void updatePrintStatus(TDCPlan obj) {
		update("t_lotno.update_print_status", obj);

	}
}