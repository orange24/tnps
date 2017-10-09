package th.co.nttdata.tki.controller.mst;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.PartRoutingMaster;
import th.co.nttdata.tki.blogic.mst.PRM_S01Logic;
import th.co.nttdata.tki.blogic.mst.S_PRT_S03Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class PRM_S01Controller  extends AbstractBaseController{
	
	private static final String PATH_URI = "MST/PRM_S01";
	@Autowired
	private S_PRT_S03Logic  s_Prt_S03Liogic;
	@RequestMapping("/PRM_S01")
	public ModelAndView init(PartRoutingMaster  prMaster){
		return new ModelAndView(PATH_URI)
		.addObject("prMaster", prMaster)
		.addObject("custMap", getCustomerSel());
	}
	public  Map<Integer,String> getCustomerSel(){
		List<MCustomer> custList = s_Prt_S03Liogic.getAllCustomer().getCustomerList();
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(Integer.MIN_VALUE , "-- Select Customer --");
		for (MCustomer cust : custList) {
			map.put(cust.getCustomerId(), cust.getCustomerCode());
		}
		return map;
	} 

}
