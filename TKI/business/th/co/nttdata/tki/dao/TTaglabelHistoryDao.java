package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.PrintTagChangedHistory;
import th.co.nttdata.tki.bean.TTaglabelHistory;

public interface TTaglabelHistoryDao {

	void insertHistory(TTaglabelHistory history);

	List<PrintTagChangedHistory> queryTagChanged(PrintTagChangedHistory tfgJunk);

}
