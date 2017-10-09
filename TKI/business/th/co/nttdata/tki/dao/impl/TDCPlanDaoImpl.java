package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.TDCPlan;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TDCPlanDao;

@Repository
public class TDCPlanDaoImpl extends AbstractBaseDao implements TDCPlanDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<TDCPlan> search(TDCPlan param) {
		return queryForList("t_dcplan.select_search", param);
	}

	@Override
	public void insertDCPlan(TDCPlan object) {
		System.out.println(object.toString());
		insert("t_dcplan.insert_dcplan", object);
	}

	@Override
	public void updateDCPlan(TDCPlan object) {
		update("t_dcplan.update_dcplan", object);
	}

	@Override
	public void deleteDCPlan(TDCPlan object) {
		delete("t_dcplan.delete_dcplan", object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TDCPlan prdS02Search(TDCPlan param) {
		calPageTotal("t_dcplan.count", param);
		param.setRecordFound(queryForList("t_dcplan.prdS02_select_search",
				param).size());
		param.setPlanList(queryForList("t_dcplan.prdS02_select_search", param,
				getSkipResult(param), param.getPageCount()));
		return param;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TDCPlan> getDataForGenerate(TDCPlan param) {
		return queryForList("t_dcplan.prdS02_select_search", param);
	}

	@Override
	public void updateGenStatus(TDCPlan object) {
		update("t_dcplan.update_genstatus", object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TDCPlan getExportData(TDCPlan param) {
		param.setPlanList(queryForList("t_dcplan.prdS02_select_search", param));
		return param;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TDCPlan> prdS03Search(TDCPlan param) {
		return queryForList("t_dcplan.prdS03_select_search", param);
	}

	@Override
	public Integer workOrderExiting(TDCPlan tdcPlan) {
		return (Integer) queryForObject("t_dcplan.work_order_exiting", tdcPlan);
	}

}
