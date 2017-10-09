package th.co.nttdata.tki.blogic.fng;

import java.util.List;
import java.util.Map;

import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.bean.TFG;
import th.co.nttdata.tki.bean.TFGDetail;
import th.co.nttdata.tki.bean.TLotSequence;

public interface FNG_S01Logic {

	public Map<String, Object> findLotQty(String lotNo);

	public void save(TFG tfg);

	public Boolean checkLotNo(TFGDetail workOrder);

	public List<TLotSequence> getOnlyLotSeqNoAuto(String lotSeqNo);

	public MWorkOrder getWorkOrderDetail(MWorkOrder workOrder);

	public TFG clearDuplicateLotSeqNo(TFG tfg);

	public TFG skipBlankRow(TFG tfg);

	public TFG checkNotExist(TFG tfg);

}
