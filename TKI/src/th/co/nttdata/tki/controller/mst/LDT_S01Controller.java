package th.co.nttdata.tki.controller.mst;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MLeadtime;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.blogic.cmm.CommonLogic;
import th.co.nttdata.tki.blogic.mst.LDT_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class LDT_S01Controller extends AbstractBaseController {
	
	private static final String PATH_URI = "MST/LDT_S01";
	
	@Autowired
	private CommonController commonControl;
	
	@Autowired
	private CommonLogic commonLogic;
	
	@Autowired
	private LDT_S01Logic ldtS01Logic;
	
	public @ResponseBody Map<Integer,String> getCustomerAll() {
		List<MCustomer> customers = commonLogic.getCustomer();

		Map<Integer,String> map = new LinkedHashMap<Integer,String>();
		map.put(null, "-- All Customer --");
		for( MCustomer MCustomer : customers )
			map.put(MCustomer.getCustomerId(), MCustomer.getCustomerCode());
		return map;
	}
	
	@RequestMapping("/LDT_S01")
	public ModelAndView init(){
		return new ModelAndView(PATH_URI)
		.addObject("mLeadtime",new MLeadtime())
		.addObject("wipMap",commonControl.getWIPAll())
		.addObject("customerMap",getCustomerAll());
	}
	
	@RequestMapping(value="/LDT_S01_search",method=RequestMethod.POST)
	public ModelAndView search(MLeadtime mLeadtime){
		mLeadtime = ldtS01Logic.getLeadtimeList(mLeadtime);
		return new ModelAndView(PATH_URI)
		.addObject("mLeadtime",mLeadtime)
		.addObject("wipMap",commonControl.getWIPAll())
		.addObject("customerMap",getCustomerAll());
	}
	
	@RequestMapping(value="/LDT_S01_save",method=RequestMethod.POST)
	public ModelAndView save(MLeadtime mLeadtime){
		ldtS01Logic.saveLeadtime(mLeadtime);
		mLeadtime = ldtS01Logic.getLeadtimeList(mLeadtime);
		mLeadtime.getInfos().clear();
		mLeadtime.getInfos().add(new Message("inf.cmm.002",null));
		return new ModelAndView(PATH_URI)
		.addObject("mLeadtime",mLeadtime)
		.addObject("wipMap",commonControl.getWIPSel())
		.addObject("customerMap",getCustomerAll());
	}
	

	@RequestMapping(value = "/LDT_S01_export", method = RequestMethod.POST)
	public ModelAndView export(MLeadtime mLeadtime) {
		mLeadtime = ldtS01Logic.getLeadtimeExportList(mLeadtime);
		return new ModelAndView("LDT_R01ExcelView")
				.addObject("mLeadtime",mLeadtime)
				.addObject("wipMap",commonControl.getWIPAll())
				.addObject("customerMap",getCustomerAll());
	}
	
}
