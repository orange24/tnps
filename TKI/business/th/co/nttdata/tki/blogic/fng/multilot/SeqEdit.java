package th.co.nttdata.tki.blogic.fng.multilot;

/** One row of the lot seq list 6.04 (or 6.05) is about to save. */
public class SeqEdit {
	public final String lotSeqNo;
	public final int qty;
	public final boolean deleted;
	public final boolean flagPrint;

	public SeqEdit(String lotSeqNo, int qty, boolean deleted, boolean flagPrint) {
		this.lotSeqNo = lotSeqNo;
		this.qty = qty;
		this.deleted = deleted;
		this.flagPrint = flagPrint;
	}
}
