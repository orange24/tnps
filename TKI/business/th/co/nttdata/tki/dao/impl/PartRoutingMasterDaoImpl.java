package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.PartRoutingMaster;
import th.co.nttdata.tki.bean.filter.PartRoutingMasterFilter;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.PartRoutingMasterDao;

@Repository
public class PartRoutingMasterDaoImpl extends AbstractBaseDao implements
		PartRoutingMasterDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<PartRoutingMaster> searchFgPartWipList(
			PartRoutingMaster prMaster) {
		return queryForList("m_partRoutingMaster.select_fg_part_wip", prMaster);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PartRoutingMaster> searchProcessList(PartRoutingMaster prMaster) {
		return queryForList("m_partRoutingMaster.select_part_wip", prMaster);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PartRoutingMaster> selectProcess() {
		return queryForList("m_partRoutingMaster.select_process");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PartRoutingMaster> selectListForCopyPartRouting(
			PartRoutingMaster prMaster) {
		return queryForList("m_partRoutingMaster.select_part_row", prMaster);
	}

	@Override
	public void insertPartDestination(PartRoutingMaster prMaster) {
		insert("m_partRoutingMaster.insert_copy_row", prMaster);
	}

	@Override
	public void deletePartDestination(PartRoutingMaster prMaster) {
		delete("m_partRoutingMaster.delete_mapping_dest", prMaster);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PartRoutingMasterFilter> selectExportList(
			PartRoutingMasterFilter partRoutingMasterFilter) {
		return queryForList("m_partRoutingMaster.select_export",
				partRoutingMasterFilter);
	}

	@Override
	public void insertProcessList(PartRoutingMaster prMaster) {
		insert("m_partRoutingMaster.insert_part_wip", prMaster);

	}

	@Override
	public void deleteProcessList(PartRoutingMaster prMaster) {
		delete("m_partRoutingMaster.delete_part_wip", prMaster);

	}
}
