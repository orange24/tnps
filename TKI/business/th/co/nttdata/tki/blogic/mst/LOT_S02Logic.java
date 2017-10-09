package th.co.nttdata.tki.blogic.mst;

import th.co.nttdata.tki.bean.MWorkOrder;

public interface LOT_S02Logic {
	public MWorkOrder searchTPics(MWorkOrder mWorkOrder);
	public void syncLot(MWorkOrder mWorkOrder);
}
