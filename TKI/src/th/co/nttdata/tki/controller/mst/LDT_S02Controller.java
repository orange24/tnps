package th.co.nttdata.tki.controller.mst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MLeadtime;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.blogic.mst.LDT_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class LDT_S02Controller extends AbstractBaseController {
	
	private static final String PATH_URI = "MST/LDT_S02";
	
	@Autowired
	private LDT_S01Logic ldtS01Logic;
	
	@RequestMapping("/LDT_S02")
	public ModelAndView init(){
		MLeadtime mLeadtime = (MLeadtime)ldtS01Logic.getLeadtimeConfig();
		if(null == mLeadtime){
			mLeadtime = new MLeadtime();
		}
		return new ModelAndView(PATH_URI)
		.addObject("mLeadtime", mLeadtime);
	}
	
	@RequestMapping(value="/LDT_S02_save",method=RequestMethod.POST)
	public ModelAndView save(MLeadtime mLeadtime){
		ldtS01Logic.setSTUseAll(mLeadtime);
		mLeadtime.getInfos().clear();
		mLeadtime.getInfos().add(new Message("inf.cmm.002",null));
		return new ModelAndView(PATH_URI)
		.addObject("mLeadtime", mLeadtime);
	}

}
