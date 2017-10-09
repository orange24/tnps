package th.co.nttdata.tki.dao;

import java.util.List;
import java.util.Map;

import th.co.nttdata.tki.bean.MFgPart;
import th.co.nttdata.tki.bean.MMold;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.MPartFilter;
import th.co.nttdata.tki.bean.MPartWip;

public interface MPartDao {

	public MPart getPart(MPart MPart);

	public List<MPart> getPartList();

	public List<MPart> getPartList(MPart MPart);

	public List<MPart> getPartInWip(MPart MPart);

	public Integer save(MPart part);

	public List<MPart> getFgList(MPart part);

	public abstract MPart savePartWip(MPart part);

	public abstract Integer saveFg(MPart part);

	public abstract Integer saveFgCustomer(MPart part);

	public abstract Integer saveFgPart(MPart part);

	public abstract MPart getPartMasterList(MPart part);

	public abstract MPart getPartNotSync(MPart part);

	public abstract List<MPartWip> getWipOfPart(MPart MPart);

	public abstract int update(MPart part);

	public abstract int updatePartWip(MPart part);

	public void delete(MPart part);

	public void deleteFgPart(MPart part);

	public void deletePartWip(MPart part);

	public void deleteFgCustomer(MPart part);

	public void deleteWorkOrder(MPart part);

	public void deleteMold(MPart part);

	public void deletePart(MPart part);

	public void deleteFg(MPart part);

	public MPart getMPart(Integer partId);

	public List<MPart> partAutoComplete(MPart MPart);

	public List<MPart> queryPartId(MPart MPart);

	public List<MPart> load();

	public List<MPart> getExportData(MPartFilter mPart);

	public void saveForInsert(MPart object);

	public void saveForUpdate(MPart object);

	public void saveForDelete(MPart object);

	public List<MPart> getPartMaster(MPart param);
	
	// Add new query for get part master with has a relation with machine.
	public List<MPart> getPartMasterWithRelation(MPart param);

	public List<MPart> getSnpByWip(Integer convertToInteger);
	
	List<MFgPart> getFgNoNameList(MFgPart part);

	List<MPart> getExportData(MPart list);

	public List<MPart> getPartEnableList(MPart mPart);

	public List<MPart> getPartInWipEnable(MPart mPart);

	Map<String, Integer> getAllPartMap();

}