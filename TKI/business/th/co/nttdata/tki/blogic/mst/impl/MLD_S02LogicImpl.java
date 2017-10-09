package th.co.nttdata.tki.blogic.mst.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MMold;
import th.co.nttdata.tki.bean.MMoldDetail;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.mst.MLD_S02Logic;
import th.co.nttdata.tki.dao.MMoldDao;
import th.co.nttdata.tki.dao.MMoldDetailDao;

@Service
public class MLD_S02LogicImpl extends AbstractBaseLogic implements MLD_S02Logic {

	@Autowired
	MMoldDetailDao detailDao;
	@Autowired
	MMoldDao moldDao;
	
	@Override
	public List<MMoldDetail> getMoldName() {
		return detailDao.getMoldName();
	}

	@Override
	public List<MMoldDetail> getMoldNo(MMoldDetail mDetail) {
		return detailDao.getMoldNo(mDetail);
	}
	
	@Override
	public List<MPart> searchPart(MMoldDetail mDetail){
		return detailDao.searchPart(mDetail);
	}

	@Override
	public int checkDupMoldNo(MMoldDetail mDetail){
		return detailDao.checkDupMoldNo(mDetail);
	}
	
	@Override
	public int checkMoldName(MMoldDetail mDetail) {
		return moldDao.checkDupMoldName(mDetail);
	}
	
	@Override
	public MMoldDetail getMoldDetail(MMoldDetail mDetail) {
		return detailDao.getMoldDetail(mDetail);
	}
	
	@Override
	public MMoldDetail add(MMoldDetail mDetail) {
		addEditMoldName(mDetail);
		detailDao.add(mDetail);
		return mDetail;
	}

	@Override
	public MMoldDetail edit(MMoldDetail mDetail) {
		addEditMoldName(mDetail);
		detailDao.edit(mDetail);
		return mDetail;
	}
	
	@Override
	public void addEditMoldName(MMoldDetail mDetail) {
		if (mDetail.getMoldId() == Integer.MIN_VALUE) {
			moldDao.add(mDetail);
			MMold mold = moldDao.getMold(mDetail);
			mDetail.setMoldId(mold.getMoldId());
		}else{
			if (mDetail.getMoldName() != null && !mDetail.getMoldName().equals("")) {
				moldDao.edit(mDetail);
			}
		}
	}

	@Override
	public void delete(MMoldDetail mDetail) {
		detailDao.delete(mDetail);
		int countMoldNo = detailDao.checkMoldName(mDetail);
		if (countMoldNo <= 0) {
			detailDao.deleteMoldPart(mDetail);
			moldDao.delete(mDetail);
		}
	}

	@Override
	public boolean checkRelateMold(MMoldDetail mMoldDetail) {
		int countRelate = detailDao.checkRelateMold(mMoldDetail);
		if (countRelate > 0) {
			return true;
		}
		return false;
	}

	@Override
	public int checkDupMoldNoEdit(MMoldDetail mDetail) {
		return detailDao.checkDupMoldNoEdit(mDetail);
	}
	
}
