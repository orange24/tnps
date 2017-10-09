package th.co.nttdata.tki.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MFgPart;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.MPartFilter;
import th.co.nttdata.tki.bean.MPartWip;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MPartDao;

@Repository
@SuppressWarnings("unchecked")
public class MPartDaoImpl extends AbstractBaseDao implements MPartDao {

	@Autowired
	protected Properties settings;
 
	@Override
	public MPart getPart(MPart MPart) {
		return (MPart) queryForObject("m_part.queryPart", MPart);
	}

	@Override
	public List<MPart> getPartList() {
		return getPartList(null);
	}

	@Override
	public List<MPart> getPartList(MPart MPart) {
		return queryForList("m_part.queryPart", MPart);
	}

	@Override
	public List<MPart> getPartEnableList(MPart MPart) {
		return queryForList("m_part.queryPartEnable", MPart);
	}

	@Override
	public List<MPart> getPartInWip(MPart MPart) {
		return queryForList("m_part_wip.queryPartInWip", MPart);
	}

	@Override
	public List<MPartWip> getWipOfPart(MPart MPart) {
		return queryForList("m_part_wip.queryPartWip", MPart.getPartId());
	}

	@Override
	public MPart getPartNotSync(MPart part) {
		part.setLinkDB(settings.getProperty("CMM.linkedServer", "\\\\172.16.12.200\\pipe\\sql\\query"));
		part.setPartList(queryForList("m_part.queryPartNotSync", part, getSkipResult(part), part.getPageCount()));
		if (part.getPartList().size() > 0) {
			part.setPageTotal(calPageTotal(part.getPartList().get(0).getPageTotal(), part.getPageCount()));
			Map<String, MPart> partWip = queryForMap("m_part.queryPartWip", part, "fgNo");
			// if(!partWip.isEmpty()){
			for (MPart p : part.getPartList()) {
				// if( partWip.get(p.getFgNo()) != null ) {
				p.setWipList(partWip.get(p.getFgNo()).getWipList());
				// }
			}
			// }
		} else {
			part.setPageTotal(0);
		}
		checkItemNotFound(part);
		return part;
	}

	@Override
	public MPart getPartMasterList(MPart part) {
		part.setPartList(queryForList("m_part.queryPartMaster", part, getSkipResult(part), part.getPageCount()));
		// calPageTotal("m_part.countPartMaster", part);
		return part;
	}

	@Override
	public Integer save(MPart part) {
		return (Integer) queryForObject("m_part.merge", part);
	}

	@Override
	public int update(MPart part) {
		return update("m_part.update", part);
	}

	@Override
	public MPart savePartWip(MPart part) {
		update("m_part_wip.merge", part);
		return part;
	}

	@Override
	public int updatePartWip(MPart part) {
		return update("m_part_wip.update", part);
	}

	@Override
	public List<MPart> getFgList(MPart part) {
		return queryForList("m_fg.queryFG", part);
	}

	@Override
	public Integer saveFg(MPart part) {
		return (Integer) queryForObject("m_fg.merge", part);
	}

	@Override
	public Integer saveFgPart(MPart part) {
		return (Integer) queryForObject("m_fg.mergePart", part);
	}

	@Override
	public Integer saveFgCustomer(MPart part) {
		return (Integer) queryForObject("m_fg.mergeCustomer", part);
	}

	@Override
	public void deleteFgPart(MPart part) {
		delete("m_fg.deletePart", part);
	}

	@Override
	public void deletePartWip(MPart part) {
		delete("m_part_wip.delete", part);
	}

	@Override
	public void deleteFgCustomer(MPart part) {
		delete("m_fg.deleteCustomer", part);
	}

	@Override
	public void deletePart(MPart part) {
		delete("m_part.delete", part);
	}

	@Override
	public void deleteFg(MPart part) {
		delete("m_fg.deleteFg", part);
	}

	@Override
	public void deleteWorkOrder(MPart part) {
		delete("m_workorder.deleteWorkOrder", part);
	}

	@Override
	public void deleteMold(MPart part) {
		delete("m_mold.deleteMold", part);
	}

	@Override
	public void delete(MPart part) {
		deleteFgPart(part);
		deletePartWip(part);
		deleteFgCustomer(part);
		deleteWorkOrder(part);
		deleteMold(part);
		deletePart(part);
		deleteFg(part);
	}

	@Override
	public MPart getMPart(Integer partId) {
		return (MPart) queryForObject("m_part.queryMPart", partId);
	}

	@Override
	public List<MPart> partAutoComplete(MPart MPart) {
		List<MPart> list = null;
		if (MPart.getCustomerId() != null && MPart.getWip() != null) {
			list = queryForList("m_part.partOfCusWipAuto", MPart);
		} else if (MPart.getCustomerId() == null && MPart.getWip() != null) {
			list = queryForList("m_part.partOfWipAuto", MPart);
		} else {
			list = queryForList("m_part.partOfCusAuto", MPart);
		}

		return list;
	}

	@Override
	public List<MPart> queryPartId(MPart MPart) {
		List<MPart> partList = null;
		if (MPart.getCustomerId() != null && MPart.getWip() != null) {
			partList = queryForList("m_part.partIdOfCusWipAuto", MPart);
		} else if (MPart.getCustomerId() == null && MPart.getWip() != null) {
			partList = queryForList("m_part.partIdOfWipAuto", MPart);
		} else {
			partList = queryForList("m_part.partIdOfCusAuto", MPart);
		}

		return partList;
	}

	/**
	 * Get all part information when page is loaded
	 * 
	 * @return return list of part information
	 */
	@Override
	public List<MPart> load() {
		return queryForList("m_part.prt_S01Load");
	}

	/**
	 * Save new part information to M_PART table
	 * 
	 * @param an
	 *            object consisting of list for inserting collection of part
	 *            information
	 * @return none
	 */
	@Override
	public void saveForInsert(MPart object) {
		insert("m_part.prt_S01SaveInsert", object);
	}

	/**
	 * Update part information from M_PART table
	 * 
	 * @param an
	 *            object consisting of list for updating collection of part
	 *            information
	 * @return none
	 */
	@Override
	public void saveForUpdate(MPart object) {
		update("m_part.prt_S01SaveUpdate", object);
	}

	/**
	 * Delete part information from M_PART table
	 * 
	 * @param an
	 *            object consisting of list for deleting collection of part
	 *            information
	 * @return none
	 */
	@Override
	public void saveForDelete(MPart object) {
		delete("m_part.prt_S01SaveDelete", object);
	}

	/**
	 * Get collection of part information form M_PART for exporting excel
	 * document
	 * 
	 * @param collection
	 *            of part identity for getting consistent value from DB
	 * @return collection of part object based on parameters
	 */
	@Override
	public List<MPart> getExportData(MPartFilter mPart) {
		return queryForList("m_part.prt_S01ExportData", mPart);
	}

	@Override
	public List<MPart> getPartMaster(MPart param) {
		return queryForList("m_part.select_part_by_cust_machine", param);
	}
	
	// Add new query for get part master with has a relation with machine.
	@Override
	public List<MPart> getPartMasterWithRelation(MPart param) {
		return queryForList("m_part.select_part_mapping_by_cust_machine", param);
	}

	@Override
	public List<MPart> getSnpByWip(Integer param) {
		return queryForList("m_part.select_snp", param);
	}

	@Override
	public List<MFgPart> getFgNoNameList(MFgPart part) {
		return queryForList("m_fg.select_fg_by_cust", part);
	}

	@Override
	public List<MPart> getExportData(MPart list) {
		return null;
	}
	
	@Override
	public List<MPart> getPartInWipEnable(MPart mPart) {
		return queryForList("m_part_wip.queryPartInWipEnable", mPart);
	}

	@Override
	public Map<String, Integer> getAllPartMap() {
		List<MPart> mParts = (List<MPart>) queryForList("m_part.queryMPart", null);
		Map<String, Integer> partMap = new HashMap<String, Integer>();
		for (MPart mPart : mParts) {
			partMap.put(mPart.getPartNo(), mPart.getPartId());
		}

		return partMap;
	}

}