package th.co.nttdata.tki.controller.adm;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.Batch;
import th.co.nttdata.tki.blogic.adm.BAT_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class BAT_S01Controller extends AbstractBaseController {

	private static final String PATH_URI = "ADM/BAT_S01";
	
	@Autowired
	public BAT_S01Logic bat_S01Logic;

	@RequestMapping("/BAT_S01")
	public ModelAndView init() {
		List<Batch> batchList = bat_S01Logic.getBatchList();
		Batch batch = bat_S01Logic.getBatch(batchList.get(0));
		
        return new ModelAndView(PATH_URI)
        	.addObject("batch",batch)
        	.addObject("bacthMap",getBatchlist());
    }	
	
	@RequestMapping(value="/BAT_S01_Batch", method=RequestMethod.POST)
	public @ResponseBody Batch getBatch(Batch batch) {
		try {
			batch = bat_S01Logic.getBatch(batch);
		} catch( Exception e ) {
			setSystemError(batch,e);
		}
		return batch;
	}

	@RequestMapping(value = "/BAT_S01_getBatchStatus", method = RequestMethod.POST)
	public @ResponseBody List<Batch> getBatchStatus() throws Exception {
		List<Batch> batchStatus = bat_S01Logic.getBatchStatus();
		return batchStatus;
	}

	@RequestMapping("/BAT_S01_run")
	public ModelAndView runBatch(Batch batch) {
		Date executeDate = batch.getExecuteDate();
		batch = getBatch(batch);
		batch.setExecuteDate(executeDate);
		bat_S01Logic.batchRun(batch);
		batch.setBatchStatus(1);
		bat_S01Logic.updateBatchControl(batch);
		bat_S01Logic.batchRun(batch);
		batch = getBatch(batch);
		
        return new ModelAndView(PATH_URI)
        	.addObject("batch", batch)
        	.addObject("bacthMap",getBatchlist());
    }
			
	public @ResponseBody Map<String,String> getBatchlist(){
		List<Batch> batchList = bat_S01Logic.getBatchList();
		Map<String,String> map = new LinkedHashMap<String,String>();
		for( Batch batch : batchList )
			map.put(""+batch.getBatchId(), batch.getBatchName());
		return map;
	}
}
