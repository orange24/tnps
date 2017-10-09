package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.MMaterial;

public interface MMaterialDao {

	public List<MMaterial> getMaterialList(MMaterial MMaterial);
	public void save(MMaterial MMaterial);
	public List<MMaterial> getMaterial();

}