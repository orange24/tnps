package th.co.nttdata.tki.blogic.mst;

import th.co.nttdata.tki.bean.MWorkOrder;

public interface LOT_S01Logic {
	public MWorkOrder search(MWorkOrder mWorkOrder);
	public void delete(MWorkOrder mWorkOrder);
}
