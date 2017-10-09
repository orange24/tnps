package th.co.nttdata.tki.controller.mst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.blogic.mst.LOT_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;

@Controller
public class LOT_S01Controller extends AbstractBaseController {
	
	private final static String PATH="MST/LOT_S01";
	
	@Autowired
	CommonController commonController;
	@Autowired
	LOT_S01Logic lot_s01Logic;
	
	@RequestMapping("/LOT_S01")
	public ModelAndView init(MWorkOrder mWorkOrder) {
		return new ModelAndView(PATH)
		.addObject("searchCriteria",mWorkOrder)
		.addObject("custMap", commonController.getCustomerAll());
	}
	
	@RequestMapping("/LOT_S01_search")
	public ModelAndView search(MWorkOrder mWorkOrder){
		mWorkOrder = lot_s01Logic.search(mWorkOrder);
		return init(mWorkOrder);
	}
	
	@RequestMapping("/LOT_S01_delete")
	public ModelAndView delete(MWorkOrder mWorkOrder){
		try {
			lot_s01Logic.delete(mWorkOrder);
			mWorkOrder.getInfos().add(new Message("inf.cmm.003", null));
		} catch (Exception e) {
			setSystemError(mWorkOrder,e);
		}
		return search(mWorkOrder);
	}
}
