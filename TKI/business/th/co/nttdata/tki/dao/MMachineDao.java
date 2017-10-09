package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.MMachine;

public interface MMachineDao {

	public MMachine getMachine(MMachine MMachine);

	public List<MMachine> getMachineList();

	public List<MMachine> getMachineList(MMachine MMachine);

	public List<MMachine> getMachineListActive(MMachine MMachine);

	public MMachine queryList(MMachine MMachine);

	public void insert(MMachine MMachine);

	public MMachine getMaxDate(MMachine MMachine);

	public void update(MMachine MMachine);

	public List<MMachine> getMachineCheck(MMachine MMachine);

	public void delete(MMachine MMachine);

	public List<MMachine> getMachineByWip(String param);

	public List<MMachine> getPartMachineList(MMachine MMchine);

	public List<MMachine> getCopyPartMachineList(MMachine MMchine);

	public List<MMachine> getWipList();

	public List<MMachine> getCustomerList(MMachine MMchine);

	public List<MMachine> getPartNoList(MMachine MMchine);

	public void DeleteMachinePartmappingbyMachineId(MMachine MMchine);

	public void insertCopyMachinePartmapping(MMachine MMachine);

	public void insertMachinePart(MMachine MMachine);

	public void deleteMachinePart(MMachine MMachine);

	public List<MMachine> getMachineMasterByDiecastPlanDate(MMachine mMachine);
}