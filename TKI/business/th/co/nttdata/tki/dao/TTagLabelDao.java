package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.PrintTagLabel;
import th.co.nttdata.tki.bean.TTagLabelDetail;

public interface TTagLabelDao {

	TTagLabelDetail getLotNoDetail(String lotNo);

	TTagLabelDetail getLotNoDetailExisting(String lotNo);

	void insertTaglabel(TTagLabelDetail tagLabel);

	void updateTaglabel(TTagLabelDetail tagLabel);

	List<PrintTagLabel> selectPrint(String lotNo);

	void deleteByLotNo(TTagLabelDetail tagLabel);

	Integer getPrintQtyRemain(Integer tagId);

}
