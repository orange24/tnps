package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.TDCPlan;

public interface TDCPlanDao {

	List<TDCPlan> search(TDCPlan param);

	void insertDCPlan(TDCPlan object);

	void updateDCPlan(TDCPlan object);

	void deleteDCPlan(TDCPlan object);

	TDCPlan prdS02Search(TDCPlan param);

	TDCPlan getExportData(TDCPlan param);

	List<TDCPlan> getDataForGenerate(TDCPlan param);

	void updateGenStatus(TDCPlan param);

	List<TDCPlan> prdS03Search(TDCPlan param);

	Integer workOrderExiting(TDCPlan tdcPlan);

}
