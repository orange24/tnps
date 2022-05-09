package th.co.nttdata.tki.blogic.fng;

import java.util.List;

import th.co.nttdata.tki.bean.TFG;
import th.co.nttdata.tki.bean.TFGDetail;

public interface FNG_S02Logic {
	public abstract TFG search(TFG TFG);
	public TFG exportFNG_R01(TFG TFG);
	public TFG exportFNG_R03(TFG TFG);
	public TFG exportFNG_R05(TFG TFG);
	public List<TFGDetail> getReportTypeList();
}