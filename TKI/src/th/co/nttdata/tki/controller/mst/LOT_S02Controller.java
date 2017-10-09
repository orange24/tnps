package th.co.nttdata.tki.controller.mst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.blogic.mst.LOT_S02Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class LOT_S02Controller extends AbstractBaseController {
	
	private final static String PATH_URI="MST/LOT_S02";
	
	@Autowired
	LOT_S02Logic lot_S02Logic;
	
	@RequestMapping("/LOT_S02")
	public ModelAndView init(MWorkOrder mWorkOrder) {
		return new ModelAndView(PATH_URI)
		.addObject("mWorkOrderCriteria",mWorkOrder);
	}
	
	@RequestMapping("/LOT_S02_search")
	public ModelAndView search(MWorkOrder mWorkOrder){
		mWorkOrder = lot_S02Logic.searchTPics(mWorkOrder);
		return init(mWorkOrder);
	}
	
	@RequestMapping("LOT_S02_sync")
	public ModelAndView syncLot(MWorkOrder mWorkOrder){
		try {
			lot_S02Logic.syncLot(mWorkOrder);
			setSaveInfo(mWorkOrder);
		} catch (Exception e) {
			setSystemError(mWorkOrder,e);
		}
		return search(mWorkOrder);
	}
}
