package th.co.nttdata.tki.controller.mst;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MMachine;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.blogic.cmm.CommonLogic;
import th.co.nttdata.tki.blogic.mst.MCH_S01Logic;
import th.co.nttdata.tki.blogic.mst.MCH_S02Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MCH_S02Controller extends AbstractBaseController{
	
	private static final String PATH_URI = "MST/MCH_S02";
	private static final String PATH_URI_S01 = "MST/MCH_S01";
	private final String CRITERIA = "criteria";
	private Map<String,String> wipMap;
	private static final int wipType1 = 1;
	private static final int wipType3 = 3;	
	
	@Autowired
	private CommonController commonControl;
	
	@Autowired
	private CommonLogic commonLogic;
	
	@Autowired
	private MCH_S02Logic mch_S02Logic;
	
	@Autowired
	private MCH_S01Logic mch_S01Logic;
	
	public @ResponseBody Map<String,String> getMachinelist(){
		List<MMachine> machines = commonLogic.getMachineNo(null);		
		Map<String,String> map = new LinkedHashMap<String,String>();
		map.put("", "--Add New Machine No.--");
		for( MMachine MMachine : machines )
			map.put(MMachine.getMachineId().toString(), MMachine.getMachineName());
		return map;
	}
	
	@RequestMapping("/MCH_S02")
	public ModelAndView init(MMachine MMachine){		
		wipMap=commonControl.getWIPSel(wipType1);
		wipMap.putAll(commonControl.getWIPSel(wipType3));
		
		return new ModelAndView(PATH_URI)
		.addObject("mMachine",MMachine)
		.addObject("wipMap",wipMap)
		.addObject("machineMap",getMachinelist());
	}
	
	@RequestMapping(value="/MCH_S02_check", method=RequestMethod.POST)
	public @ResponseBody MMachine check(MMachine MMachine) {		
		try {
			MMachine.setMachineList(mch_S02Logic.getMachineList(MMachine));
			if (MMachine.getMachineList() == null) {
				MMachine = new MMachine();
			}
		} catch( Exception e ) {
			e.printStackTrace();
			setSystemError(MMachine,e);
		}
		return MMachine;
	}
		
	@RequestMapping("/MCH_S02_save")	
	public @ResponseBody MMachine save(MMachine MMachine){		
		try {
			MMachine = mch_S02Logic.save(MMachine);
		} catch (Exception e) {
			e.printStackTrace();
			setSystemError(MMachine,e);
		}
		
		return MMachine;
	}
	
	@RequestMapping("/MCH_S02_edit")
	public ModelAndView edit(MMachine MMachine){		
		MMachine = mch_S02Logic.getMachine(MMachine);		
		return init(MMachine);
	}
	@RequestMapping("/MCH_S02_delete")
	public ModelAndView delete(MMachine MMachine, HttpSession session){
		try{
			mch_S02Logic.delete(MMachine);			
			if(session.getAttribute(CRITERIA)!=null){
				MMachine = (MMachine)session.getAttribute(CRITERIA);
				MMachine = mch_S01Logic.getMachineList(MMachine);
			}
			MMachine.getInfos().clear();
			MMachine.getInfos().add(new Message("inf.cmm.003", null));
			return new ModelAndView(PATH_URI_S01)
			.addObject("machine",MMachine)
	        .addObject("wipMap",wipMap);
		}catch(Exception e){
			setSystemError(MMachine,e);
			return init(MMachine)
			.addObject("machineMap", getMachinelist());
		}
	}	
	
}
