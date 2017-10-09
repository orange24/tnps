package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MMold;
import th.co.nttdata.tki.bean.VProductProcesMaster;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.VProductProcesMasterDao;

@Repository
@SuppressWarnings("unchecked")
public class VProductProcesMasterDaoImpl extends AbstractBaseDao implements VProductProcesMasterDao{

	@Override
	public VProductProcesMaster selectMRDC_S05(VProductProcesMaster vPro) {
		vPro.setvList((List<VProductProcesMaster>)queryForList("t_wip_stock.queryMRDC_S05",vPro));
		return vPro;
	}

	@Override
	public List<MMold> selectMoldByPart(Integer partId) {
		return (List<MMold>)queryForList("m_mold.queryMoldMRDC_R05",partId);
	}
	
	@Override
	public Integer countMRDC_S05() {
		
		return (Integer) queryForObject("t_wip_stock.countMRDC_R05");
	}

}
