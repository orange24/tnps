package th.co.nttdata.tki.blogic.fng.multilot;

import java.util.Date;

/** One lot seq on a multi-lot sticker. */
public class LabelLine {
	public String lotSeqNo;
	public int qty;
	/** t_lotno.printdate of the lot (3.03 lot card print date); null prints "DC" with no date */
	public Date dateCode;
}
