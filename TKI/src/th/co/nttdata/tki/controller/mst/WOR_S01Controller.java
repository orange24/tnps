package th.co.nttdata.tki.controller.mst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MWorker;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.blogic.mst.WOR_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class WOR_S01Controller extends AbstractBaseController{
	
	private static final String PATH_URI = "MST/WOR_S01";
	
	@Autowired
	private WOR_S01Logic worS01Logic;
	
	@RequestMapping("/WOR_S01")
	public ModelAndView init(){
		MWorker mWorker = (MWorker)worS01Logic.getMWorker();
		return new ModelAndView(PATH_URI)
		.addObject("mWorker",mWorker);
	}
	
	@RequestMapping(value="/WOR_S01_save",method=RequestMethod.POST)
	public ModelAndView save(MWorker mWorker){
		worS01Logic.save(mWorker);
		mWorker.getInfos().clear();
		mWorker.getInfos().add(new Message("inf.cmm.002",null));
		return new ModelAndView(PATH_URI)
		.addObject("mWorker",mWorker);
	}
}
