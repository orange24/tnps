package th.co.nttdata.tki.controller.mst;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.filter.MCustomerFilter;
import th.co.nttdata.tki.blogic.mst.CUS_S01Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class CUS_S01Controller extends AbstractBaseController {

	private static final String PATH_URI = "MST/CUS_S01";

	@Autowired
	private CUS_S01Logic cus_S01Logic;

	@RequestMapping("/CUS_S01")
	public ModelAndView init() {
		return new ModelAndView(PATH_URI).addObject("search", new MCustomer());
	}

	@ResponseBody
	@RequestMapping(value = "/CUS_S01_init")
	public List<MCustomer> search() {
		return cus_S01Logic.search();
	}

	@RequestMapping(value = "/CUS_S01_save")
	@ResponseBody
	public MCustomer save(@RequestBody List<LinkedHashMap> mCustomers) {
		MCustomer customer = new MCustomer();

		try {
			cus_S01Logic.save(mCustomers);
			setSaveInfo(customer);
		} catch (Exception e) {
			setSystemError(customer, e);
		}

		return customer;
	}

	@RequestMapping(value = "/CUS_S01_export", method = RequestMethod.POST)
	public ModelAndView export(MCustomerFilter mCustomers) {
		return new ModelAndView("CUS_S01ExcelView").addObject("data",
				cus_S01Logic.search(mCustomers));
	}
}
