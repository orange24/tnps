package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.bean.TLotSequence;
import th.co.nttdata.tki.bean.TTagLabelDetail;

public interface TLotSequenceDao {

	List<TLotSequence> getLotSeqExisting(TTagLabelDetail tagLabel);

	void insertLotSequence(TLotSequence insertLot);

	void updateLotSequence(TLotSequence updateLot);

	void deleteLotSequence(TLotSequence deleteLot);

	void deleteByTag(Integer tagId);

	List<TLotSequence> getOnlyLotSeqNoAuto(String lotNo);

	MWorkOrder getWorkOrderDetail(MWorkOrder workOrder);

	void updatePrintStatus(TLotSequence lot);

}