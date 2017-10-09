package th.co.nttdata.tki.controller.dlv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.TDeliveryPlan;
import th.co.nttdata.tki.blogic.DLV.DLV_S03Logic;
import th.co.nttdata.tki.blogic.cfg.DOC_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class DLV_S03Controller extends AbstractBaseController {

	private static final String PATH_URI = "DLV/DLV_S03";

	@Autowired
	private DLV_S03Logic dlv_S03Logic;

	@Autowired
	public DOC_S01Logic doc_S01Logic;
	
	@RequestMapping("/DLV_S03")
	public ModelAndView init(TDeliveryPlan TDeliveryPlan) {

        return new ModelAndView(PATH_URI)
        .addObject("deliveryPlan",TDeliveryPlan);
    }
	
	@RequestMapping(value="/DLV_S03_export", method=RequestMethod.POST)
	public ModelAndView export( TDeliveryPlan TDeliveryPlan ) {
		
		return new ModelAndView("DLV_R02ExcelView")
		.addObject("deliveryPlan", dlv_S03Logic.exportDLV_R02(TDeliveryPlan))
		.addObject("docControl",  doc_S01Logic.getDocNo())		
		;
	}
}
