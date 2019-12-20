package th.co.nttdata.tki.controller.mst;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.CustomerLine;
import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.blogic.mst.CUS_S03Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class CUS_S03Controller extends AbstractBaseController {
	
	private static final String PATH_URI = "MST/CUS_S03";
	@Autowired
	private CUS_S03Logic  cus_S03Logic;
	
	@RequestMapping("/CUS_S03")
	public ModelAndView init(CustomerLine  customerLine){
		return new ModelAndView(PATH_URI)
		.addObject("customerLine", customerLine)
		.addObject("custMap", getCustomerSel());
	}
	public  Map<Integer,String> getCustomerSel(){
		List<MCustomer> custList = cus_S03Logic.getAllCustomer().getCustomerList();
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(Integer.MIN_VALUE , "-- Select Customer --");
		for (MCustomer cust : custList) {
			map.put(cust.getCustomerId(), cust.getCustomerCode());
		}
		return map;
	} 
	@RequestMapping(value = "/CUS_S03_getCustomerLineList.html")
	public @ResponseBody
	    List<CustomerLine>  getFgNoListByCustomerId(CustomerLine customerLine){
		customerLine  =  cus_S03Logic.searchCustomerLineByCustomerId(customerLine);
		return  customerLine.getCustomerLineList();
	}
	@RequestMapping(value="/CUS_S03_saveList" , method=RequestMethod.POST)
	@ResponseBody
	public CustomerLine saveCustomerLineList(@RequestBody  List<Map> param){
		CustomerLine  customerLine = new  CustomerLine();
		try{
			BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
			List<CustomerLine>  lineList = new ArrayList<CustomerLine>();
			for(Map  map : param){
				CustomerLine  mfg = new CustomerLine();
				BeanUtils.populate(mfg,map);
				lineList.add(mfg);
			}
		  cus_S03Logic.saveCustomerLine(lineList);
		  setSaveInfo(customerLine);
		}
		catch(Exception e){
			setSystemError(customerLine,e);
		}
		return customerLine;
	}
	@RequestMapping(value = "/CUS_S03_selectCustomerLineList")
	public @ResponseBody List<CustomerLine> getCustomerLineList(){
		   return  cus_S03Logic.getCustomerLineList();
	}
}
