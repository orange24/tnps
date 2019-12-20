package th.co.nttdata.tki.controller.wip;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.TWipCheckStock;
import th.co.nttdata.tki.blogic.wip.WIP_S06Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class WIP_S06Controller extends AbstractBaseController {

	private static final String PATH_URI = "WIP/WIP_S06";
	@Autowired
	private CommonController commonController;
	@Autowired
	private WIP_S06Logic wipS06Logic;
		
	@RequestMapping(value="/boxWIP06", method=RequestMethod.GET)
	public @ResponseBody Map<String,String> getWIP( Integer customerId, Integer partId ) {
		Map<String,String> map = new LinkedHashMap<String,String>();
		if(0 < partId || 0 < customerId){
			List<MPart> wips = wipS06Logic.getWIP(customerId, partId);
			map.put("", "-- All WIP --");
			for( MPart mPart : wips )
				map.put(mPart.getWip(), mPart.getWip() + " : " + mPart.getWipName());
		}else{
			map = commonController.getWIPAll();
		}
		return map;
	}

	@RequestMapping("/WIP_S06")
	public ModelAndView init() {
		TWipCheckStock TWipCheckStock = new TWipCheckStock();
		// <!-- Initial. -->
		Calendar current = new GregorianCalendar(Locale.US);
		//TWip.setMonth(current.get(Calendar.MONTH));
		//TWip.setYear(current.get(Calendar.YEAR));
                TWipCheckStock.setReportDateFr(current.getTime());              
                TWipCheckStock.setReportDateTo(current.getTime());

		return new ModelAndView(PATH_URI)
			.addObject("wip", TWipCheckStock)
			.addObject("customerMap", commonController.getCustomerAll())
			.addObject("partMap", commonController.getPartNameNoAll(null, null,null))
			.addObject("wipMap", commonController.getWIPAll());
	}
	@RequestMapping(value="/WIP_S06_search")
	public ModelAndView search( TWipCheckStock TWipCheckStock ) {
		TWipCheckStock = wipS06Logic.search( TWipCheckStock );

		return new ModelAndView(PATH_URI)
			.addObject("wip", TWipCheckStock)
			.addObject("customerMap", commonController.getCustomerAll())
			.addObject("partMap", commonController.getPartNameNoAll(TWipCheckStock.getCustomerId(), TWipCheckStock.getWip(),null))
			.addObject("wipMap", getWIP(TWipCheckStock.getCustomerId(),TWipCheckStock.getPartId()));
	}
	
	@RequestMapping(value="/WIP_S06_export" , method=RequestMethod.POST)
	public ModelAndView export(TWipCheckStock TWipCheckStock){
		return new ModelAndView("WIP_R06ExcelView")
		.addObject("customer", wipS06Logic.getMCustomer(TWipCheckStock.getCustomerId()))
		.addObject("part", wipS06Logic.getMPart(TWipCheckStock.getPartId()))
		.addObject("tWipFgStockList", wipS06Logic.search(TWipCheckStock));
	}
}