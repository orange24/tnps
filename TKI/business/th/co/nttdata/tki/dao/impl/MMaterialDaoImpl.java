package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MMaterial;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MMaterialDao;

@Repository
public class MMaterialDaoImpl extends AbstractBaseDao implements MMaterialDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MMaterial> getMaterialList(MMaterial MMaterial) {

		return (List<MMaterial>) queryForList("m_material.queryMaterial");
	}
	
	@Override
	public void save( MMaterial MMaterial ) {	
		update("m_material.update", MMaterial);
	}

	/**
	 * Get all material information when page is loaded
	 * @return return list of material information
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MMaterial> getMaterial() {
		return (List<MMaterial>) queryForList("m_material.prtS01GetMaterial");
	}
}