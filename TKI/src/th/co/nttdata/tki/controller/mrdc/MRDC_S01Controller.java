package th.co.nttdata.tki.controller.mrdc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.blogic.cmm.CommonLogic;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class MRDC_S01Controller extends AbstractBaseController{
	
	private static final String PATH_URI = "MRDC/MRDC_S01";
	
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private MRDC_S01Logic mrdc_s01Logic;
	
	@Autowired
	private CommonLogic commonLogic;
	
	@RequestMapping("/MRDC_S01")
	public ModelAndView init(){
		
		return new ModelAndView(PATH_URI)
		.addObject("deliveryPlan",new TDeliveryPlan())
		.addObject("partMap", commonController.getPartNameNo(null, null, null))
		.addObject("customerMap", commonController.getCustomerSel());
	}
	
	@RequestMapping("/MRDC_R01_export")
	public ModelAndView export(TDeliveryPlan TDeliveryPlan){
		MCustomer MCustomer = new MCustomer();
		MCustomer.setCustomerId(TDeliveryPlan.getCustomerId());
		MCustomer = commonLogic.getMCustomer(MCustomer);
		TDeliveryPlan.setCustomerName(MCustomer.getCustomerName());
		
		return new ModelAndView("MRDC_R01ExcelView")
		.addObject("productCompositionList1",mrdc_s01Logic.exportMRDC_R01ProductCompositionList1(TDeliveryPlan))
		.addObject("productCompositionList2",mrdc_s01Logic.exportMRDC_R01ProductCompositionList2(TDeliveryPlan));
	}
}
