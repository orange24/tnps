package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.TWipJunk;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TWipJunkDao;

@Repository
@SuppressWarnings("unchecked")
public class TWipJunkDaoImpl extends AbstractBaseDao implements TWipJunkDao{

	@Override
	public TWipJunk queryWipJunk(TWipJunk tWipJunk) {
		tWipJunk.setJunkList((List<TWipJunk>)queryForList("t_wip_junk.queryWipJunk",tWipJunk,getSkipResult(tWipJunk),tWipJunk.getPageCount()));
		calPageTotal("t_wip_junk.count", tWipJunk);
		return tWipJunk;
	}

	@Override
	public void insertWipJunk(TWipJunk tWipJunk) {
		insert("t_wip_junk.insertWipJunk",tWipJunk);
	}
	
	@Override
	public Integer getCurrentStock(TWipJunk tWipJunk) {
		return (Integer) queryForObject("t_wip_junk.getCurrentStock",tWipJunk);
	}

}
