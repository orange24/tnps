package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.PrintTagLabel;
import th.co.nttdata.tki.bean.TTagLabelDetail;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TTagLabelDao;

@Repository
@SuppressWarnings("unchecked")
public class TTagLabelDaoImpl extends AbstractBaseDao implements TTagLabelDao {

	@Override
	public TTagLabelDetail getLotNoDetail(String lotNo) {
		return (TTagLabelDetail) queryForObject(
				"t_taglabel.select_taglabel_by_lotno", lotNo);
	}

	@Override
	public TTagLabelDetail getLotNoDetailExisting(String lotNo) {
		return (TTagLabelDetail) queryForObject(
				"t_taglabel.select_lotno_existing", lotNo);
	}

	@Override
	public void insertTaglabel(TTagLabelDetail tagLabel) {
		insert("t_taglabel.insert_taglabel", tagLabel);
	}

	@Override
	public void updateTaglabel(TTagLabelDetail tagLabel) {
		update("t_taglabel.update_taglabel", tagLabel);
	}

	@Override
	public List<PrintTagLabel> selectPrint(String lotNo) {
		return queryForList("t_taglabel.select_print", lotNo);
	}

	@Override
	public void deleteByLotNo(TTagLabelDetail tagLabel) {
		delete("t_taglabel.delete_by_lotNo", tagLabel);

	}

	@Override
	public Integer getPrintQtyRemain(Integer tagId) {
		return (Integer) queryForObject("t_taglabel.getPrintQtyRemain", tagId);
	}

}
