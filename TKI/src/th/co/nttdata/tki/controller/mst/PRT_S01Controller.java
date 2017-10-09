package th.co.nttdata.tki.controller.mst;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MMaterial;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.MPartFilter;
import th.co.nttdata.tki.blogic.mst.PRT_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

/**
 * @author NDTH
 * @since July 9, 2013
 */
@Controller
public class PRT_S01Controller extends AbstractBaseController {

	/* A variable for indicating the next screen */
	private static final String PATH_URI = "MST/PRT_S01";
	
	/* A variable for accessing business logic */
	@Autowired
	private PRT_S01Logic prt_S01Logic; 
	
	/**
	 * Indicate the page to choose
	 * @return the model and view for indication the screen
	 * @param none
	 */
	@RequestMapping("/PRT_S01")
	public ModelAndView init() {
		return new ModelAndView(PATH_URI);
	}
	
	/**
	 * Get all part information from DB
	 * @return collection of JSON objects for showing in grid view
	 * @param none
	 */
	@RequestMapping("/PRT_S01_search")
	public @ResponseBody List<MPart> search() {
		return prt_S01Logic.load();
	}
	
	/**
	 * Get material list from DB
	 * @return collection of JSON objects for showing in grid view
	 * @throws Exception
	 */
	@RequestMapping(value = "/PRT_S01_getMaterial")
	public @ResponseBody List<MMaterial> getMaterial() throws Exception {
		return prt_S01Logic.getMaterial();
	}
	
	/**
	 * Get part information based on filtered rows
	 * @param request: HttpServletRequest for getting request parameters
	 * @return the model and view for indication the screen
	 * @throws Exception
	 */
	@RequestMapping(value = "/PRT_S01_export", method = RequestMethod.POST)
	public ModelAndView export(MPartFilter mPart) throws Exception {
		return new ModelAndView("PRT_S01ExcelView").addObject("data", prt_S01Logic.getExportData(mPart));
	}
	
	/**
	 * Use CUD operation for part information
	 * @param param: collection of part information based on filtered rows
	 * @return the model and view for indication the screen
	 * @throws Exception
	 */
	@RequestMapping(value = "/PRT_S01_save")
	public @ResponseBody MPart save(@RequestBody List<Map<String, Object>> param) throws Exception {
		MPart object = new MPart();
		try{
			prt_S01Logic.save(param);
			setSaveInfo(object);
		}catch (Exception e) {
			setSystemError(object, e);
		}
		return object;
	}
}