package th.co.nttdata.tki.blogic.mst.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MMaterial;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.mst.MAT_S01Logic;
import th.co.nttdata.tki.dao.MMaterialDao;

@Service
public class MAT_S01LogicImpl extends AbstractBaseLogic implements MAT_S01Logic  {
	
	@Autowired
	private MMaterialDao mMaterialDao;

	@Override
	public List<MMaterial> search(MMaterial MMaterial) {
		
		return mMaterialDao.getMaterialList(MMaterial);
	}
	
	@Override
	public void save(MMaterial MMaterial) {
		
		mMaterialDao.save(MMaterial);
	}
}
