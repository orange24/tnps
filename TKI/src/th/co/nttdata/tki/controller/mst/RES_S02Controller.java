package th.co.nttdata.tki.controller.mst;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.blogic.cmm.CommonLogic;
import th.co.nttdata.tki.blogic.mst.RES_S01Logic;
import th.co.nttdata.tki.blogic.mst.RES_S02Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class RES_S02Controller extends AbstractBaseController{

	private static final String PATH_URI = "MST/RES_S02";
	private static final String PATH_URI_S01 = "MST/RES_S01";
	private final String CRITERIA = "criteria";
	
	@Autowired
	private CommonLogic commonLogic;
	
	@Autowired
	RES_S01Logic res_s01Logic;
	
	@Autowired
	private RES_S02Logic res_s02Logic;
	
	public @ResponseBody Map<String,String> getParentReason(){
		List<MReason> list = res_s02Logic.getParentReason();
		Map<String,String> parentMap = new LinkedHashMap<String,String>();
		parentMap.put("", "--Select Parent Reason--");
		for( MReason mReason : list){
			parentMap.put(mReason.getReasonId().toString(),mReason.getReasonCode()+" : "+mReason.getReasonName());
		}
		return parentMap;
	}
	
	public @ResponseBody Map<String,String> getWIPSel( ) {
		List<MWip> wips = commonLogic.getWIP(null);

		Map<String,String> map = new LinkedHashMap<String,String>();
		map.put("", "--Only Reason--");
		for( MWip MWip : wips )
			map.put(MWip.getWip(),MWip.getWipName());
		return map;
	}
	
	@RequestMapping("/RES_S02")
	public ModelAndView init(MReason mReason){
		return new ModelAndView(PATH_URI)
		.addObject("mReason",mReason)
		.addObject("parentReason",getParentReason());
	}
	
	@RequestMapping("/RES_S02_save")
	public @ResponseBody MReason save(MReason mReason){
		try{
			res_s02Logic.save(mReason);
		}catch( Exception e){
			setSystemError(mReason,e);
		}
		return mReason;
	}
	
	@RequestMapping("/RES_S02_edit")
	public @ResponseBody MReason edit(MReason mReason){
		try{
			res_s02Logic.edit(mReason);
		}catch( Exception e){
			setSystemError(mReason,e);
		}
		return mReason;
	}
	
	@RequestMapping("/RES_S02_view")
	public ModelAndView view(MReason mReason){
		mReason = res_s02Logic.getReasonById(mReason);
		return init(mReason);
	}
	
	@RequestMapping("/RES_S02_delete")
	public ModelAndView delete(MReason mReason, HttpSession session){
		try{
			res_s02Logic.delete(mReason);
			if(session.getAttribute(CRITERIA)!=null){
				mReason = (MReason)session.getAttribute(CRITERIA);
				mReason = res_s01Logic.searchResson(mReason);
			}
			mReason.getInfos().clear();
			mReason.getInfos().add(new Message("inf.cmm.003", null));
			return new ModelAndView(PATH_URI_S01)
			.addObject("mReason",mReason)
			.addObject("wip",getWIPSel());
		}catch(Exception e){
			mReason.getErrors().add(new Message("err.res.001", null));
			return init(mReason);
		}
	}
}
