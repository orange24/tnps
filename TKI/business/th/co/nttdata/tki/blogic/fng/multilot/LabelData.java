package th.co.nttdata.tki.blogic.fng.multilot;

import java.util.ArrayList;
import java.util.List;

/** What goes on one 10x5 cm multi-lot sticker. */
public class LabelData {
	public String refNo;
	public String wip;
	public String customerName;
	public String fgNo;
	public String partName;
	public List<LabelLine> lines = new ArrayList<LabelLine>();
}
