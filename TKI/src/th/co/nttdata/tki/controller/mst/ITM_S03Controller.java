package th.co.nttdata.tki.controller.mst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.blogic.mst.ITM_S03Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class ITM_S03Controller extends AbstractBaseController {

	private static final String PATH_URI = "MST/ITM_S03";
	
	@Autowired 
	private ITM_S03Logic itm_S03Logic;

	@RequestMapping("/ITM_S03")
	public ModelAndView init(MPart part) {		
		MPart partDB = itm_S03Logic.query(part);
		partDB.setInfos(part.getInfos());
		partDB.setErrors(part.getErrors());
		return new ModelAndView(PATH_URI)
			.addObject("mPart", partDB);
	}

	@RequestMapping("/ITM_S03_save")
	public ModelAndView save(MPart part) {
		try {
			itm_S03Logic.update(part);	
			setSaveInfo(part);
		} catch (Exception e) {
			setSystemError(part,e);
			e.printStackTrace();			
		}
		return init(part);
	}
}