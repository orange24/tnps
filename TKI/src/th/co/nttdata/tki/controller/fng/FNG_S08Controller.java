package th.co.nttdata.tki.controller.fng;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.FNG_S08PrintRequest;
import th.co.nttdata.tki.bean.TMultiLotTag;
import th.co.nttdata.tki.bean.TTagLabelDetail;
import th.co.nttdata.tki.blogic.fng.FNG_S08Logic;
import th.co.nttdata.tki.blogic.fng.MultiLotValidationException;
import th.co.nttdata.tki.controller.AbstractBaseController;

/** 6.05 Print Multi-Lot Tag (QR). Lot search and preview reuse the FNG_S06_* endpoints. */
@Controller
public class FNG_S08Controller extends AbstractBaseController {

	private static final String PATH_URI = "FNG/FNG_S08";
	private static final String SESSION_PRINTER = "FNG_S08printerName";

	@Autowired
	private FNG_S08Logic fng_S08Logic;

	private static HttpSession session() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request.getSession();
	}

	private static void rememberPrinter(String printerName) {
		if (StringUtils.isNotEmpty(printerName)) {
			session().setAttribute(SESSION_PRINTER, printerName);
		}
	}

	@RequestMapping("/FNG_S08")
	public ModelAndView init() {
		TTagLabelDetail searchCriteria = new TTagLabelDetail();
		String printerName = (String) session().getAttribute(SESSION_PRINTER);
		if (StringUtils.isNotEmpty(printerName)) {
			searchCriteria.setPrinterName(printerName);
		}
		return new ModelAndView(PATH_URI).addObject("searchCriteria", searchCriteria);
	}

	@RequestMapping(value = "/FNG_S08_getDetailByLotNo")
	public @ResponseBody
	TTagLabelDetail getDetailByLotNo(String lotNo) {
		return fng_S08Logic.getLotDetail(lotNo);
	}

	@RequestMapping(value = "/FNG_S08_print", method = RequestMethod.POST)
	public @ResponseBody
	TMultiLotTag print(@RequestBody FNG_S08PrintRequest req) {
		TMultiLotTag result = new TMultiLotTag();
		try {
			rememberPrinter(req.getPrinterName());
			result = fng_S08Logic.printMultiLot(req);
		} catch (MultiLotValidationException e) {
			// rolled back: nothing was saved or printed
			result = new TMultiLotTag();
			result.getErrors().addAll(e.getErrors());
		} catch (Exception e) {
			result = new TMultiLotTag();
			setSystemError(result, e);
		}
		return result;
	}

	@RequestMapping(value = "/FNG_S08_find")
	public @ResponseBody
	TMultiLotTag find(String refNo) {
		return fng_S08Logic.findByRefNo(StringUtils.trimToEmpty(refNo).toUpperCase());
	}

	@RequestMapping(value = "/FNG_S08_reprint", method = RequestMethod.POST)
	public @ResponseBody
	TMultiLotTag reprint(String refNo, String printerName) {
		TMultiLotTag result = new TMultiLotTag();
		try {
			rememberPrinter(printerName);
			result = fng_S08Logic.reprint(StringUtils.trimToEmpty(refNo).toUpperCase(), printerName);
		} catch (Exception e) {
			result = new TMultiLotTag();
			setSystemError(result, e);
		}
		return result;
	}

	@RequestMapping(value = "/FNG_S08_cancel", method = RequestMethod.POST)
	public @ResponseBody
	TMultiLotTag cancel(String refNo) {
		TMultiLotTag result = new TMultiLotTag();
		try {
			result = fng_S08Logic.cancel(StringUtils.trimToEmpty(refNo).toUpperCase());
			setSaveInfo(result);
		} catch (Exception e) {
			result = new TMultiLotTag();
			setSystemError(result, e);
		}
		return result;
	}
}
