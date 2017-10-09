package th.co.nttdata.tki.blogic.mst;

import java.util.List;

import th.co.nttdata.tki.bean.MMoldDetail;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.TMoldHistory;

public interface MLD_S03Logic {

	public List<MPart> getPartNo(Integer customerId, Integer moldId);

	public List<MMoldDetail> getMoldName(Integer customerId, Integer partId);

	public List<MMoldDetail> getMoldNo(Integer moldId);

	public TMoldHistory search(TMoldHistory mHist);

	public List<MMoldDetail> getMoldNoDistinct(Integer moldId);

}
