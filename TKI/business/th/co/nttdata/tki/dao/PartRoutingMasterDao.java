package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.PartRoutingMaster;
import th.co.nttdata.tki.bean.filter.PartRoutingMasterFilter;

public interface PartRoutingMasterDao {
	public List<PartRoutingMaster> searchFgPartWipList(
			PartRoutingMaster prMaster);

	public List<PartRoutingMaster> searchProcessList(PartRoutingMaster prMaster);

	public List<PartRoutingMaster> selectProcess();

	public void insertProcessList(PartRoutingMaster prMaster);

	public void deleteProcessList(PartRoutingMaster prMaster);

	public List<PartRoutingMaster> selectListForCopyPartRouting(
			PartRoutingMaster prMaster);

	public void deletePartDestination(PartRoutingMaster prMaster);

	public void insertPartDestination(PartRoutingMaster prMaster);

	public List<PartRoutingMasterFilter> selectExportList(
			PartRoutingMasterFilter partRoutingMasterFilter);
}
