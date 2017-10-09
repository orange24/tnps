package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.MMold;
import th.co.nttdata.tki.bean.VProductProcesMaster;

public interface VProductProcesMasterDao {
	
	public VProductProcesMaster selectMRDC_S05(VProductProcesMaster vPro);
	
	public Integer countMRDC_S05();
	
	public List<MMold> selectMoldByPart(Integer partId);
}
