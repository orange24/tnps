package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.MMold;
import th.co.nttdata.tki.bean.MMoldDetail;
import th.co.nttdata.tki.bean.MPart;

public interface MMoldDao {

	public MMold getMold(MMold MMold);

	public List<MMold> getMoldList();

	public List<MMold> getMoldList(MMold MMold);
	
	public List<MPart> getPartNo(MMoldDetail mDetail);

	public int checkDupMoldName(MMoldDetail mDetail);

	public void add(MMoldDetail mdDetail);

	public void edit(MMoldDetail mDetail);

	public MMold getMold(MMoldDetail mDetail);

	public List<MMoldDetail> getMoldName(MMoldDetail moldDetail);

	public void delete(MMoldDetail mdDetail);
	
	public List<MMold> getMoldByPart(int param);
	
	public List<MMold> getCavByMold(int moldId, String moldNo);
	
	public List<MMoldDetail> getMoldNoByMold(int moldId);
}