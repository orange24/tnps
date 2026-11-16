package th.co.nttdata.tki.blogic.fng;

import java.util.List;

import th.co.nttdata.tki.bean.TFGStock;
import th.co.nttdata.tki.bean.TLotNo;
import th.co.nttdata.tki.bean.TLotSequence;
import th.co.nttdata.tki.bean.TTagLabelDetail;

public interface FNG_S06Logic {

	public TFGStock searchFGAdjustHistory(TFGStock tfgStock);

	public List<TLotNo> getLotNoAuto(String lotNo);

	public TTagLabelDetail getLotNoDetail(String lotNo);

	public TTagLabelDetail getLotNoDetailExisting(String lotNo);

	public List<TLotSequence> preview(TTagLabelDetail taglabel);

	public TTagLabelDetail printLabel(TTagLabelDetail tagLabel)
			throws Exception;

	public Integer getPrintQtyRemain(Integer tagId);

	/** Saves t_taglabel and its t_lot_sequence rows exactly as printLabel does; returns tagId. */
	public Integer saveTagAndSequences(TTagLabelDetail tagLabel);

	/** Sends raw printer bytes to the printer with this name; false = no printer by that name. */
	public boolean sendRaw(String printerName, byte[] data) throws Exception;

}
