package th.co.nttdata.tki.blogic.fng.multilot;

/** One lot seq the user ticked in 6.05, as read back from the DB after saving. */
public class Candidate {
	public final String lotSeqNo;
	public final int qty;
	public final int customerId;
	public final int fgId;
	public final String wip;
	public final String labelType;
	/** already scanned IN in 6.01 (t_fg_detail.fgin > 0); a printed seq that is not IN may go on a QR tag */
	public final boolean scannedIn;
	/** refno of the active ML this seq is already on, null if none */
	public final String activeRefNo;

	public Candidate(String lotSeqNo, int qty, int customerId, int fgId,
			String wip, String labelType, boolean scannedIn, String activeRefNo) {
		this.lotSeqNo = lotSeqNo;
		this.qty = qty;
		this.customerId = customerId;
		this.fgId = fgId;
		this.wip = wip;
		this.labelType = labelType;
		this.scannedIn = scannedIn;
		this.activeRefNo = activeRefNo;
	}
}
