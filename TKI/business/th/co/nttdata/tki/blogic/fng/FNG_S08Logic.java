package th.co.nttdata.tki.blogic.fng;

import th.co.nttdata.tki.bean.FNG_S08PrintRequest;
import th.co.nttdata.tki.bean.TMultiLotTag;
import th.co.nttdata.tki.bean.TTagLabelDetail;

public interface FNG_S08Logic {

	/** Lot header as 6.04 loads it, with SNP taken from the FG master (null when the lot is unknown). */
	public TTagLabelDetail getLotDetail(String lotNo);

	/** Saves the lots, puts the ticked seqs (1-4) on a new ML and prints it. Throws MultiLotValidationException on a rule break. */
	public TMultiLotTag printMultiLot(FNG_S08PrintRequest req) throws Exception;

	public TMultiLotTag findByRefNo(String refNo);

	public TMultiLotTag reprint(String refNo, String printerName) throws Exception;

	public TMultiLotTag cancel(String refNo);

}
