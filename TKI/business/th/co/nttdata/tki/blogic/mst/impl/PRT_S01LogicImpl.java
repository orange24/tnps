package th.co.nttdata.tki.blogic.mst.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MMaterial;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.MPartFilter;
import th.co.nttdata.tki.blogic.mst.PRT_S01Logic;
import th.co.nttdata.tki.dao.MMaterialDao;
import th.co.nttdata.tki.dao.MPartDao;

/**
 * @author NDTH
 * @since July 9, 2013
 */
@Service
public class PRT_S01LogicImpl implements PRT_S01Logic, Serializable {

	private static final long serialVersionUID = 5499897684667573172L;

	/* A variable named mPartDao for accessing data from DB */
	@Autowired
	private MPartDao mPartDao;

	/* A variable name mMaterialDao for accessing data from DB */
	@Autowired
	private MMaterialDao mMaterialDao;

	/**
	 * Load all part information from M_PART table
	 * 
	 * @param none
	 * @return collection part information for initial display
	 */
	@Override
	public List<MPart> load() {
		return mPartDao.load();
	}

	/**
	 * Load all material information from M_Material table
	 * 
	 * @param none
	 * @return collection part information for initial display
	 */
	@Override
	public List<MMaterial> getMaterial() {
		return mMaterialDao.getMaterial();
	}

	/**
	 * Get part information for exporting excel document
	 * 
	 * @param a
	 *            collection of identities whose are java script objects
	 * @return collection part information for exporting part information
	 */
	@Override
	public List<MPart> getExportData(MPartFilter mPart) throws Exception {
		return mPartDao.getExportData(mPart);
	}

	/**
	 * Update M_PART table based on the statusFlag Insert new part information
	 * to DB if statusFlag is 'i' Update existing part information to DB if
	 * statusFlag is 'e' Delete existing part information to DB if statusFlag is
	 * 'd'
	 * 
	 * @param collection
	 *            of objects based on keys and values pair
	 * @return none
	 */
	@Override
	public void save(List<Map<String, Object>> param) throws Exception {
		List<MPart> insertList = new ArrayList<MPart>();
		List<MPart> updateList = new ArrayList<MPart>();
		List<MPart> deleteList = new ArrayList<MPart>();
		MPart object = new MPart();
		List<MPart> data = this.convertToSave(param);
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).isInsert()) {
				insertList.add(data.get(i));
			} else if (data.get(i).isUpdate()) {
				updateList.add(data.get(i));
			} else if (data.get(i).isDelete()) {
				deleteList.add(data.get(i));
			}
		}
		if (!deleteList.isEmpty()) {
			object.setPartList(deleteList);
			mPartDao.saveForDelete(object);
		}
		if (!insertList.isEmpty()) {
			object.setPartList(insertList);
			mPartDao.saveForInsert(object);
		}
		if (!updateList.isEmpty()) {
			object.setPartList(updateList);
			mPartDao.saveForUpdate(object);
		}
	}

	/**
	 * Convert collections of part information to list of MPart Objects Purpose
	 * to persist objects to M_PART table
	 * 
	 * @param param
	 *            for converting collections of part information to list of
	 *            MPart Objects
	 * @return list of MPart Objects for persisting to DB
	 * @throws Exception
	 */
	private List<MPart> convertToSave(List<Map<String, Object>> param)
			throws Exception {
		List<MPart> list = new ArrayList<MPart>();
		MPart object = null;
		for (int i = 0; i < param.size(); i++) {
			object = new MPart();
			BeanUtils.populate(object, param.get(i));
			list.add(object);
		}
		return list;
	}
}
