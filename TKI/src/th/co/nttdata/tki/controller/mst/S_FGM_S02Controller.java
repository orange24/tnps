package th.co.nttdata.tki.controller.mst;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import th.co.nttdata.tki.bean.FgMaster;
import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.blogic.mst.S_FGM_S02Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class S_FGM_S02Controller extends AbstractBaseController {
	
	private static final String PATH_URI = "MST/S_FGM_S02";
	@Autowired
	private S_FGM_S02Logic  s_fgm_S02Logic;
	
	@RequestMapping("/S_FGM_S02")
	public ModelAndView init(FgMaster  fgMaster){
		return new ModelAndView(PATH_URI)
		.addObject("fgMaster", fgMaster)
		.addObject("custMap", getCustomerSel());
	}
	public  Map<Integer,String> getCustomerSel(){
		List<MCustomer> custList = s_fgm_S02Logic.getAllCustomer().getCustomerList();
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(Integer.MIN_VALUE , "-- Select Customer --");
		for (MCustomer cust : custList) {
			map.put(cust.getCustomerId(), cust.getCustomerCode());
		}
		return map;
	} 
	@RequestMapping(value = "/S_FGM_S02_searchList.html", method = RequestMethod.GET)
	public @ResponseBody
	    List<FgMaster> getCustomerFgMappingByCustomerId(FgMaster fgmaster){
		fgmaster  =  s_fgm_S02Logic.searchCustomerFgMappingByCustomerId(fgmaster);
		return  fgmaster.getFgMasterList();
	}
	@RequestMapping(value = "/S_FGM_S02_getFgNoList.html")
	public @ResponseBody
	    List<FgMaster>  getFgNoListByCustomerId(FgMaster fgmaster){
		fgmaster  =  s_fgm_S02Logic.searchCustomerFgMappingByCustomerId(fgmaster);
		return  fgmaster.getFgMasterList();
	}
	@RequestMapping(value="/S_FGM_S02_saveList" , method=RequestMethod.POST)
	@ResponseBody
	public FgMaster saveCustomerFgMappingList(@RequestBody  List<Map> param){
		FgMaster  fgMaster = new  FgMaster();
		try{
			List<FgMaster>  mFgList = new ArrayList<FgMaster>();
			for(Map  map : param){
				FgMaster  mfg = new FgMaster();
				BeanUtils.populate(mfg,map);
				mFgList.add(mfg);
			}
		  s_fgm_S02Logic.SaveCustomerFgMaster(mFgList);
		  setSaveInfo(fgMaster);
		}
		catch(Exception e){
			setSystemError(fgMaster,e);
		}
		return fgMaster;
	}
	@RequestMapping(value = "/S_FGM_S02_selectFGMasterList")
	public @ResponseBody List<FgMaster> getPartListByFgId(){
		   return  s_fgm_S02Logic.getFgList();
	}
}
