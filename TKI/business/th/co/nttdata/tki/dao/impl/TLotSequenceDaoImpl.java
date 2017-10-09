package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.bean.TLotSequence;
import th.co.nttdata.tki.bean.TTagLabelDetail;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TLotSequenceDao;

@Repository
@SuppressWarnings("unchecked")
public class TLotSequenceDaoImpl extends AbstractBaseDao implements
		TLotSequenceDao {

	@Override
	public List<TLotSequence> getLotSeqExisting(TTagLabelDetail tagLabel) {
		return queryForList("t_lot_sequence.select_lotseq_existing", tagLabel);
	}

	@Override
	public void insertLotSequence(TLotSequence insertLot) {
		insert("t_lot_sequence.insert_lot_sequence", insertLot);

	}

	@Override
	public void updateLotSequence(TLotSequence updateLot) {
		update("t_lot_sequence.edit_lot_sequence", updateLot);
	}

	@Override
	public void deleteLotSequence(TLotSequence deleteLot) {
		delete("t_lot_sequence.delete_lot_sequence", deleteLot);
	}

	@Override
	public void deleteByTag(Integer tagId) {
		delete("t_lot_sequence.delete_by_tag", tagId);
	}

	@Override
	public List<TLotSequence> getOnlyLotSeqNoAuto(String lotSeqNo) {
		return queryForList("t_lot_sequence.select_lotseq_autocom", lotSeqNo);
	}

	@Override
	public MWorkOrder getWorkOrderDetail(MWorkOrder workOrder) {
		return (MWorkOrder) queryForObject("t_lot_sequence.select_lotseq",
				workOrder);
	}

	@Override
	public void updatePrintStatus(TLotSequence lot) {
		update("t_lot_sequence.update_print_status", lot);
	}
}