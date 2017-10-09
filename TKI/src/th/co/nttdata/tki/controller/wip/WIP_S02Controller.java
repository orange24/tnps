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
import th.co.nttdata.tki.bean.TWip;
import th.co.nttdata.tki.blogic.wip.WIP_S02Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class WIP_S02Controller extends AbstractBaseController {

	private static final String PATH_URI = "WIP/WIP_S02";
	@Autowired
	private CommonController commonController;
	@Autowired
	private WIP_S02Logic wipS02Logic;
		
	@RequestMapping(value="/boxWIP", method=RequestMethod.GET)
	public @ResponseBody Map<String,String> getWIP( Integer customerId, Integer partId ) {
		Map<String,String> map = new LinkedHashMap<String,String>();
		if(0 < partId || 0 < customerId){
			List<MPart> wips = wipS02Logic.getWIP(customerId, partId);
			map.put("", "-- All WIP --");
			for( MPart mPart : wips )
				map.put(mPart.getWip(), mPart.getWip() + " : " + mPart.getWipName());
		}else{
			map = commonController.getWIPAll();
		}
		return map;
	}

	@RequestMapping("/WIP_S02")
	public ModelAndView init() {
		TWip TWip = new TWip();
		// <!-- Initial. -->
		Calendar current = new GregorianCalendar(Locale.US);
		TWip.setMonth(current.get(Calendar.MONTH));
		TWip.setYear(current.get(Calendar.YEAR));

		return new ModelAndView(PATH_URI)
			.addObject("wip", TWip)
			.addObject("customerMap", commonController.getCustomerAll())
			.addObject("partMap", commonController.getPartNameNoAll(null, null,null))
			.addObject("wipMap", commonController.getWIPAll())
			.addObject("monthMap", getMonthMap())
			.addObject("yearMap", getYearMap());
	}

	@RequestMapping(value="/WIP_S02_search")
	public ModelAndView search( TWip TWip ) {
		TWip = wipS02Logic.search( TWip );

		return new ModelAndView(PATH_URI)
			.addObject("wip", TWip)
			.addObject("customerMap", commonController.getCustomerAll())
			.addObject("partMap", commonController.getPartNameNoAll(TWip.getCustomerId(), TWip.getWip(),null))
			.addObject("wipMap", getWIP(TWip.getCustomerId(),TWip.getPartId()))
			.addObject("monthMap", getMonthMap())
			.addObject("yearMap", getYearMap());
	}
	
	@RequestMapping(value="/WIP_S02_export" , method=RequestMethod.POST)
	public ModelAndView export(TWip TWip){
		return new ModelAndView("WIP_R02ExcelView")
		.addObject("customer", wipS02Logic.getMCustomer(TWip.getCustomerId()))
		.addObject("part", wipS02Logic.getMPart(TWip.getPartId()))
		.addObject("tWipStockList", wipS02Logic.search(TWip));
	}
}