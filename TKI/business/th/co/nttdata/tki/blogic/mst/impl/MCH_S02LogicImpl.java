package th.co.nttdata.tki.blogic.mst.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MMachine;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.mst.MCH_S02Logic;
import th.co.nttdata.tki.dao.MMachineDao;

@Service
public class MCH_S02LogicImpl extends AbstractBaseLogic implements MCH_S02Logic{
	
	@Autowired
	MMachineDao mMachineDao	;
	
	@Override
	public MMachine getMachine(MMachine MMachine) {		
		return mMachineDao.getMachine(MMachine);
	}

	@Override
	public MMachine save(MMachine MMachine) {
		boolean isDup = false;
		MMachine machine = MMachine;
		String machineName = MMachine.getMachineName();
		Date maxDate = mMachineDao.getMaxDate(MMachine).getEndDate();
		Date startDate = MMachine.getStartDate();
		
		if(MMachine.getMachineId()== null){				
			machine.setMachineName("");
		}else{
			if (MMachine.getCreateDate() == null) {	
				machine.setMachineId(null);
			}
		}
		if(MMachine.getCreateDate()==null){
			isDup = (mMachineDao.getMachineList(machine).size())>0?true:false;	
		}else{
			isDup = (mMachineDao.getMachineCheck(machine).size())>0?true:false;
		}
		if(isDup){
			MMachine.getErrors().add(new Message("err.cmm.011",new String[]{"Machine "}));
		}			
		if(maxDate != null){				
			if((startDate.before(maxDate))||(startDate.equals(maxDate))){
				MMachine.getErrors().add(new Message("err.mch.001",new String[]{}));
			}
		}
		if(!(MMachine.getErrors().size()>0)){
			if(MMachine.getMachineCost() == ""){
				MMachine.setMachineCost("0.000");
			}else{
				String[] num = MMachine.getMachineCost().split(",");
				String cost  = "";
			    for(String a:num){
			    	cost+=a;
			    }
			    MMachine.setMachineCost(cost);
			}
			
			if (MMachine.getCreateDate() == null) {			
				MMachine.setMachineName(machineName);
				mMachineDao.insert(MMachine);				
			}else{	
				if(MMachine.getEndDate()==null){
					DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					Date enddate = null;
					try {
						enddate = formatter.parse("31/12/9999");
					} catch (ParseException e) {
						e.printStackTrace();
					}
					MMachine.setEndDate(enddate);					
				}
				mMachineDao.update(MMachine);			
			}
		}
		return MMachine;
	}	

	@Override
	public List<MMachine> getMachineList(MMachine MMachine) {
		return mMachineDao.getMachineList(MMachine);
	}
	
	@Override
	public void delete(MMachine MMchine) {
		List<MMachine> list = new ArrayList<MMachine>();
		list.add(MMchine);
		MMchine.setMachineList(list);
		mMachineDao.delete(MMchine);		
	}

}
