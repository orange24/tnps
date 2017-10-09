package th.co.nttdata.tki.dao;

import th.co.nttdata.tki.bean.TWipJunk;

public interface TWipJunkDao {
	
	public TWipJunk queryWipJunk(TWipJunk tWipJunk);

	public void insertWipJunk(TWipJunk tWipJunk);
	
	public Integer getCurrentStock(TWipJunk tWipJunk);
}
