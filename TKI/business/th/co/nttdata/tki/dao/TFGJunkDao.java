package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.PrintTagChangedHistory;
import th.co.nttdata.tki.bean.TFGJunk;

public interface TFGJunkDao {
	
	public TFGJunk queryFGJunk(TFGJunk tFGJunk);

	public void insertFGJunk(TFGJunk tFGJunk);
	
	public Integer getCurrentStock(TFGJunk tFGJunk);
	

}
