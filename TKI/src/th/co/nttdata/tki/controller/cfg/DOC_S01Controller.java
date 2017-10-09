package th.co.nttdata.tki.controller.cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MDocControl;
import th.co.nttdata.tki.blogic.cfg.DOC_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class DOC_S01Controller extends AbstractBaseController {
	private static final String PATH_URI = "CFG/DOC_S01";

	@Autowired
	public DOC_S01Logic doc_S01Logic;

	@RequestMapping("/DOC_S01")
	public ModelAndView init() {
		return new ModelAndView(PATH_URI).addObject("docNo", doc_S01Logic.getDocNo());
	}

	@RequestMapping("/DOC_S01_save")
	public ModelAndView runBatch(MDocControl docNo) {

		doc_S01Logic.saveDocNo(docNo);
        return new ModelAndView(PATH_URI)
        	.addObject("docNo", docNo);
    }
}
