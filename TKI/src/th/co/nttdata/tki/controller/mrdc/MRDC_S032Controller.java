package th.co.nttdata.tki.controller.mrdc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S032Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class MRDC_S032Controller extends AbstractBaseController{
	
	private static final String PATH_URI = "MRDC/MRDC_S032";
	
	@Autowired
	private MRDC_S032Logic mrdc_s032Logic;
	
	@RequestMapping(value="/MRDC_S032_search")
	public ModelAndView searchMRDC_S032(TDeliveryPlan TDeliveryPlan){
		TDeliveryPlan = mrdc_s032Logic.searchMRDC_S032(TDeliveryPlan);
		
		return new ModelAndView(PATH_URI)
		.addObject("deliveryPlan",TDeliveryPlan)
		.addObject("monthMap", getMonthMap());
	}
}
