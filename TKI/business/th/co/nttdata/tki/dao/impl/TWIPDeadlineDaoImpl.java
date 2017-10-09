package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.bean.TWIPDeadline;
import th.co.nttdata.tki.bean.TWIPDeadlineDate;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TWIPDeadlineDao;

@Repository
@SuppressWarnings("unchecked")
public class TWIPDeadlineDaoImpl extends AbstractBaseDao implements TWIPDeadlineDao {

	@Override
	public TWIPDeadline getDeadlineList( TWIPDeadline TWIPDeadline ) {
		List<TWIPDeadline> deadlinePartList = (List<TWIPDeadline>) queryForList("t_wip_deadline.queryDeadline", TWIPDeadline);
		List<TWIPDeadlineDate> deadlineDateList = (List<TWIPDeadlineDate>) queryForList("t_wip_deadline.queryDeadlineDate", TWIPDeadline);
		if(deadlinePartList.size() < 1){
			TWIPDeadline.getErrors().add(new Message("inf.cmm.004", new String[] {}));
			return TWIPDeadline;
		}
		
		Integer toRecord = 0;
		Integer to = TWIPDeadline.getPageNumber()* TWIPDeadline.getPageCount();
		if(deadlinePartList.size() < to){
			toRecord = deadlinePartList.size();
		}else{
			toRecord = to;
		}
		TWIPDeadline.setPageTotal(calPageTotal(deadlinePartList.size(), TWIPDeadline.getPageCount()));
		checkItemNotFound(TWIPDeadline);
		
		TWIPDeadline.setDeadlinePartList(deadlinePartList.subList(getSkipResult(TWIPDeadline), toRecord));
		TWIPDeadline.setDeadlineDateList(deadlineDateList);
		
		return TWIPDeadline;
	}
	
	@Override
	public TWIPDeadline selectWIP_R01( TWIPDeadline TWIPDeadline ) {
		List<TWIPDeadline> deadlinePartList = (List<TWIPDeadline>) queryForList("t_wip_deadline.queryDeadline", TWIPDeadline);
		TWIPDeadline.setDeadlinePartList(deadlinePartList);
		
		return TWIPDeadline;
	}
}