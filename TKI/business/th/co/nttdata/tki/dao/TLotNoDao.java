package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.TDCPlan;
import th.co.nttdata.tki.bean.TLotNo;

public interface TLotNoDao {

	List<TLotNo> getLotNoAuto(String lotNo);

	void deleteLotNo(TDCPlan object);

	void insertLotNo(TDCPlan object);

	void prdS03DeleteLotNo(TDCPlan object);

	List<TDCPlan> getDataForPrint(TDCPlan param);

	void updatePrintStatus(TDCPlan obj);
}