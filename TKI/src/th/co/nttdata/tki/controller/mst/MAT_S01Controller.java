package th.co.nttdata.tki.controller.mst;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MMaterial;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.blogic.mst.MAT_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class MAT_S01Controller extends AbstractBaseController {

	private static final String PATH_URI = "MST/MAT_S01";	
	
	@Autowired
	private MAT_S01Logic mat_S01Logic;
	
	@RequestMapping("/MAT_S01")
	public ModelAndView init() {	
		MMaterial MMaterial = new MMaterial();
		List<MMaterial> materialList = mat_S01Logic.search(MMaterial);
		MMaterial.setMaterialList(materialList);
		
        return new ModelAndView(PATH_URI)
        .addObject("material",MMaterial);            
    }	
	
	@RequestMapping(value="/MAT_S01_save", method=RequestMethod.POST)
	public ModelAndView save(MMaterial MMaterial){		
		try {
			mat_S01Logic.save(MMaterial);	
			MMaterial.getInfos().add(new Message("inf.cmm.002", new String[] {}));
		} catch (Exception e) {
			setSystemError(MMaterial,e);
		}		
		return init();	
	}
}
