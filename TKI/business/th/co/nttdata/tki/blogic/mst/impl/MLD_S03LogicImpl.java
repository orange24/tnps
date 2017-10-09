package th.co.nttdata.tki.blogic.mst.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MMoldDetail;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.TMoldHistory;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.mst.MLD_S03Logic;
import th.co.nttdata.tki.dao.MMoldDao;
import th.co.nttdata.tki.dao.MMoldDetailDao;
import th.co.nttdata.tki.dao.TMoldHistoryDao;

@Service
public class MLD_S03LogicImpl extends AbstractBaseLogic implements MLD_S03Logic {

	@Autowired
	MMoldDao mMoldDao;
	@Autowired
	MMoldDetailDao detailDao;
	@Autowired
	TMoldHistoryDao tMoldHistoryDao;
	
	@Override
	public List<MPart> getPartNo(Integer customerId, Integer moldId) {
		MMoldDetail moldDetail = new MMoldDetail(); 
		moldDetail.setCustomerId(customerId);
		moldDetail.setMoldId(moldId);
		return mMoldDao.getPartNo(moldDetail);
	}
	
	@Override
	public List<MMoldDetail> getMoldName(Integer customerId, Integer partId) {
		MMoldDetail moldDetail = new MMoldDetail(); 
		moldDetail.setCustomerId(customerId);
		moldDetail.setPartId(partId);
		return mMoldDao.getMoldName(moldDetail);
	}

	@Override
	public List<MMoldDetail> getMoldNo(Integer moldId) {
		MMoldDetail moldDetail = new MMoldDetail(); 
		moldDetail.setMoldId(moldId);
		return detailDao.selectMoldNo(moldDetail);
	}
	
	@Override
	public List<MMoldDetail> getMoldNoDistinct(Integer moldId) {
		MMoldDetail moldDetail = new MMoldDetail(); 
		moldDetail.setMoldId(moldId);
		return detailDao.selectMoldNoDistinct(moldDetail);
	}

	@Override
	public TMoldHistory search(TMoldHistory mHist) {
		return tMoldHistoryDao.search(mHist);
	}

}
