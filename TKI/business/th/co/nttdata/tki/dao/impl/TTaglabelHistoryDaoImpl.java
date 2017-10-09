package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.PrintTagChangedHistory;
import th.co.nttdata.tki.bean.TTaglabelHistory;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TTaglabelHistoryDao;

@Repository
@SuppressWarnings("unchecked")
public class TTaglabelHistoryDaoImpl extends AbstractBaseDao implements
		TTaglabelHistoryDao {

	@Override
	public void insertHistory(TTaglabelHistory history) {
		insert("t_taglabel_history.insert_history", history);
	}
	
	@Override
	public List<PrintTagChangedHistory> queryTagChanged(PrintTagChangedHistory tfgJunk) {
		return queryForList("t_taglabel_history.queryTagChanged",tfgJunk);
	}

}
