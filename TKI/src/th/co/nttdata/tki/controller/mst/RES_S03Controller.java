package th.co.nttdata.tki.controller.mst;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.blogic.cmm.CommonLogic;
import th.co.nttdata.tki.blogic.mst.RES_S03Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class RES_S03Controller extends AbstractBaseController{
	
	private static final String PATH_URI = "MST/RES_S03";
	
	@Autowired
	private CommonLogic commonLogic;
	
	@Autowired
	RES_S03Logic res_s03Logic;
	
	public @ResponseBody Map<String,String> getWIPSel( ) {
		List<MWip> wips = commonLogic.getWIP(null);

		Map<String,String> map = new LinkedHashMap<String,String>();
		map.put("", "--Select WIP--");
		for( MWip MWip : wips )
			map.put(MWip.getWip(),MWip.getWip() + " : " + MWip.getWipName());
		return map;
	}
	
	@RequestMapping("/RES_S03")
	public ModelAndView init(){
		return new ModelAndView(PATH_URI)
		.addObject("mReason",new MReason())
		.addObject("wip", getWIPSel());
	}
	
	@RequestMapping("/RES_S03_search")
	public ModelAndView search(MReason mReason) {
		mReason = res_s03Logic.searchResson(mReason);
		return new ModelAndView(PATH_URI)
		.addObject("mReason",mReason)
		.addObject("wip",getWIPSel());
	}
	
	@RequestMapping("/RES_S03_save")
	public ModelAndView save(MReason mReason){
		try{
			res_s03Logic.saveReasonUse(mReason);
			mReason.getInfos().add(new Message("inf.cmm.002", null));
			mReason = res_s03Logic.searchResson(mReason);			
		}catch(Exception e){
			setSystemError(mReason,e);
		}
		return this.search(mReason);
	} 

}
