package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MMold;
import th.co.nttdata.tki.bean.MMoldDetail;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MMoldDao;

@Repository
@SuppressWarnings("unchecked")
public class MMoldDaoImpl extends AbstractBaseDao implements MMoldDao {

	@Override
	public MMold getMold( MMold MMold ) {
		return (MMold) queryForObject("m_mold.queryMold", MMold);
	}

	@Override
	public List<MMold> getMoldList() {
		return getMoldList( null );
	}

	@Override
	public List<MMold> getMoldList( MMold MMold ) {
		return (List<MMold>) queryForList("m_mold.queryMold", MMold);
	}

	@Override
	public int checkDupMoldName(MMoldDetail mDetail) {
		return (Integer) queryForObject("m_mold.checkDupMoldName",mDetail);
	}
	
	@Override
	public void add(MMoldDetail mdDetail){
		insert("m_mold.addMold",mdDetail);
	}

	@Override
	public void edit(MMoldDetail mDetail) {
		update("m_mold.updateMold",mDetail);
	}

	@Override
	public MMold getMold(MMoldDetail mDetail) {
		return (MMold) queryForObject("m_mold.query",mDetail) ;
	}

	@Override
	public List<MPart> getPartNo(MMoldDetail mDetail) {
		return (List<MPart>) queryForList("m_mold.queryPartNo",mDetail);
	}

	@Override
	public List<MMoldDetail> getMoldName(MMoldDetail mDetail) {
		return (List<MMoldDetail>) queryForList("m_mold.queryMoldName",mDetail);
	}

	@Override
	public void delete(MMoldDetail mdDetail) {
		delete("m_mold.delete",mdDetail);
	}

	@Override
	public List<MMold> getCavByMold(int param, String moldNo) {
		MMoldDetail md = new MMoldDetail();
		md.setMoldId(param);
		md.setMoldNo(moldNo);
		return (List<MMold>) queryForList("m_mold.queryCavByMold",md);
	}

	@Override
	public List<MMold> getMoldByPart(int param) {
		return (List<MMold>) queryForList("m_mold.queryMoldByPart",param);
	}

	@Override
	public List<MMoldDetail> getMoldNoByMold(int moldId) {
		return (List<MMoldDetail>) queryForList("m_mold.queryMoldNoByMold",moldId);
	}
}