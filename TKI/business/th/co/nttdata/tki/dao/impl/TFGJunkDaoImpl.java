package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.PrintTagChangedHistory;
import th.co.nttdata.tki.bean.TFGJunk;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TFGJunkDao;

@Repository
@SuppressWarnings("unchecked")
public class TFGJunkDaoImpl extends AbstractBaseDao implements TFGJunkDao{

	@Override
	public TFGJunk queryFGJunk(TFGJunk tFGJunk) {
		tFGJunk.setJunkList((List<TFGJunk>)queryForList("t_fg_junk.queryFGJunk",tFGJunk,getSkipResult(tFGJunk),tFGJunk.getPageCount()));
		calPageTotal("t_fg_junk.count", tFGJunk);
		return tFGJunk;
	}

	@Override
	public void insertFGJunk(TFGJunk tFGJunk) {
		insert("t_fg_junk.insertFGJunk",tFGJunk);
	}
	
	@Override
	public Integer getCurrentStock(TFGJunk tFGJunk) {
		return (Integer) queryForObject("t_fg_junk.getCurrentStock",tFGJunk);
	}

	

	
}
