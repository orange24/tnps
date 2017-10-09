package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.MMoldDetail;
import th.co.nttdata.tki.bean.MPart;

public interface MMoldDetailDao {
	public MMoldDetail search(MMoldDetail mDetail);
	public List<MMoldDetail> getMoldName();
	public List<MMoldDetail> getMoldName(MMoldDetail MMoldDetail);
	public List<MMoldDetail> getMoldNo(MMoldDetail MMoldDetail);
	public List<MMoldDetail> selectMoldNo(MMoldDetail MMoldDetail);
	public MMoldDetail getMoldDetail(MMoldDetail mDetail);
	public void add(MMoldDetail mDetail);
	public List<MMoldDetail> getMoldDetailList(MMoldDetail mDetail);
	public void updateEndDate(MMoldDetail mDetail);
	public int checkDupMoldNo(MMoldDetail mDetail);
	public void edit(MMoldDetail mDetail);
	public List<MPart> searchPart(MMoldDetail mMoldDetail);
	public void delete(MMoldDetail mdDetail);
	public MMoldDetail selectMLD_R01(MMoldDetail mDetail);
	public int checkRelateMold(MMoldDetail mdDetail);
	public int checkMoldName(MMoldDetail mdDetail);
	public void deleteMoldPart(MMoldDetail mdDetail);
	public int checkDupMoldNoEdit(MMoldDetail mDetail);
	public MMoldDetail exportMRDC_R21(MMoldDetail mdDetail);
	public Integer countMRDC_R21();
	public List<MMoldDetail> selectMoldNoDistinct(MMoldDetail MMoldDetail);
}
