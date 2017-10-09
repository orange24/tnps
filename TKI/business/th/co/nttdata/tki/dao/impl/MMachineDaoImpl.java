package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MMachine;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MMachineDao;

@Repository
@SuppressWarnings("unchecked")
public class MMachineDaoImpl extends AbstractBaseDao implements MMachineDao {

	@Override
	public MMachine getMachine(MMachine MMachine) {
		return (MMachine) queryForObject("m_machine.queryMachine", MMachine);
	}

	@Override
	public List<MMachine> getMachineList() {
		return getMachineList(null);
	}

	@Override
	public List<MMachine> getMachineList(MMachine MMachine) {
		return queryForList("m_machine.queryMachine", MMachine);
	}

	@Override
	public List<MMachine> getMachineListActive(MMachine MMachine) {
		return queryForList("m_machine.queryMachineActive", MMachine);
	}

	@Override
	public MMachine queryList(MMachine MMachine) {
		MMachine.setMachineList(queryForList("m_machine.queryMachineList",
				MMachine, getSkipResult(MMachine), MMachine.getPageCount()));
		calPageTotal("m_machine.count", MMachine);
		return MMachine;
	}

	@Override
	public void insert(MMachine MMachine) {
		insert("m_machine.insert", MMachine);
	}

	@Override
	public MMachine getMaxDate(MMachine MMachine) {
		return (MMachine) queryForObject("m_machine.endDateMax", MMachine);
	}

	@Override
	public void update(MMachine MMachine) {
		update("m_machine.update", MMachine);
	}

	@Override
	public List<MMachine> getMachineCheck(MMachine MMachine) {
		return queryForList("m_machine.queryCheck", MMachine);
	}

	@Override
	public void delete(MMachine MMachine) {
		delete("m_machine.delete", MMachine);
	}

	@Override
	public List<MMachine> getMachineByWip(String param) {
		return queryForList("m_machine.select_machine_by_wip", param);
	}

	@Override
	public List<MMachine> getPartMachineList(MMachine MMchine) {
		return queryForList("m_machine.select_mapping", MMchine);
	}

	@Override
	public List<MMachine> getWipList() {
		return queryForList("m_machine.select_wip");
	}

	@Override
	public List<MMachine> getCopyPartMachineList(MMachine MMchine) {
		return queryForList("m_machine.select_copy_search", MMchine);
	}

	@Override
	public void DeleteMachinePartmappingbyMachineId(MMachine MMchine) {
		delete("m_machine.delete_mapping", MMchine);
	}

	@Override
	public void insertCopyMachinePartmapping(MMachine MMachine) {
		insert("m_machine.insert_copy_mapping", MMachine);
	}

	@Override
	public List<MMachine> getCustomerList(MMachine MMchine) {
		return queryForList("m_machine.select_cust", MMchine);
	}

	@Override
	public List<MMachine> getPartNoList(MMachine MMchine) {
		return queryForList("m_machine.select_part", MMchine);
	}

	@Override
	public void insertMachinePart(MMachine MMachine) {
		insert("m_machine.insert_mapping", MMachine);
	}

	@Override
	public void deleteMachinePart(MMachine MMachine) {
		delete("m_machine.delete_partmapping", MMachine);
	}

	@Override
	public List<MMachine> getMachineMasterByDiecastPlanDate(MMachine mMachine) {
		return queryForList("m_machine.select_Machine_diecastplandate",
				mMachine);
	}

}