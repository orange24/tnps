package th.co.nttdata.tki.blogic.fng;

import java.util.List;

import th.co.nttdata.tki.bean.PrintTagChangedHistory;
import th.co.nttdata.tki.bean.TFGJunk;

public interface FNG_S07Logic {
	
	//public TFGJunk searchFGJunk(TFGJunk tfgJunk);
	
	public List<PrintTagChangedHistory> searchTag(PrintTagChangedHistory tfgJunk);
	
}
