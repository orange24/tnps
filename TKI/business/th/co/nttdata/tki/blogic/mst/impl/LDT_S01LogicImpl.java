package th.co.nttdata.tki.blogic.mst.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MLeadtime;
import th.co.nttdata.tki.blogic.mst.LDT_S01Logic;
import th.co.nttdata.tki.dao.MLeadtimeDao;

@Service
public class LDT_S01LogicImpl implements LDT_S01Logic{
	
	@Autowired
	MLeadtimeDao mLeadtimeDao;

	@Override
	public MLeadtime getLeadtimeList(MLeadtime mLeadtime) {
		return mLeadtimeDao.queryLeadtimeList(mLeadtime);
	}

	@Override
	public void saveLeadtime(MLeadtime mLeadtime) {
		int listSize = mLeadtime.getLeadTimeList().size();
		if(listSize != 0){
			List<MLeadtime> list = new ArrayList<MLeadtime>();
			for(int i=0; i<listSize; i++){
				MLeadtime lt = mLeadtime.getLeadTimeList().get(i);
				if((lt.getStUseNo()!=null)||(lt.getStQty1()!=null)||(lt.getStSec1()!=null)||
				(lt.getStResult1()!=null)||(lt.getStQty2()!=null)||(lt.getStSec2()!=null)||
				(lt.getStResult2()!=null)||(lt.getStQty3()!=null)||(lt.getStSec3()!=null)||
				(lt.getStResult3()!=null)||(lt.getStDateFr4()!=null)||(lt.getStDateTo4()!=null)||
				(lt.getStResult4()!=null)||(lt.getStDateFr5()!=null)||(lt.getStDateTo5()!=null)||
				(lt.getStResult5()!=null)||(lt.getStDateFr6()!=null)||(lt.getStDateTo6()!=null)||(lt.getStResult6()!=null)){
					list.add(lt);
				}
			}
			if(list.size() != 0){
				mLeadtime.setLeadTimeList(list);
				mLeadtimeDao.insertLeadtime(mLeadtime);
			}
		}
	}

	@Override
	public void setSTUseAll(MLeadtime mLeadtime) {
		List<MLeadtime> list = mLeadtimeDao.getWipPart();
		MLeadtime leadtime = new MLeadtime();
		for(int i=0; i<list.size(); i+=50){
			if((i+50) < list.size()){
				leadtime.setLeadTimeList(list.subList(i, (i+50)));
			}else{
				leadtime.setLeadTimeList(list.subList(i, list.size()));
			}
			mLeadtimeDao.insertPartWip(leadtime);
		}
		mLeadtimeDao.updateSTUseAll(mLeadtime);
		
		//update m_leadtime_config table
		mLeadtimeDao.deleteLeadTimeConfig();
		mLeadtimeDao.insertLeadTimeConfig(mLeadtime);
	}

	@Override
	public MLeadtime getLeadtimeConfig() {
		MLeadtime mLeadtime = mLeadtimeDao.queryLeadTimeConfig();
		return mLeadtime;
	}

}
