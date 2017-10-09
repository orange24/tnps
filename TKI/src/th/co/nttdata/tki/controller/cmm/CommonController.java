package th.co.nttdata.tki.controller.cmm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MFgPart;
import th.co.nttdata.tki.bean.MMachine;
import th.co.nttdata.tki.bean.MMaterial;
import th.co.nttdata.tki.bean.MMold;
import th.co.nttdata.tki.bean.MMoldDetail;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.MPrinter;
import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.bean.TDailyWK;
import th.co.nttdata.tki.blogic.cmm.CommonLogic;
import th.co.nttdata.tki.controller.AbstractBaseController;

@Controller
public class CommonController extends AbstractBaseController {

	@Autowired
	private CommonLogic commonLogic;

	@RequestMapping(value = "/boxCustomer1", method = RequestMethod.GET)
	public @ResponseBody
	Map<Integer, String> getCustomerSel() {
		return getCustomer("-- Select Customer --");
	}

	@RequestMapping(value = "/boxCustomer2", method = RequestMethod.GET)
	public @ResponseBody
	Map<Integer, String> getCustomerAll() {
		return getCustomer("-- All Customer --");
	}

	@RequestMapping(value = "/boxCustomer3", method = RequestMethod.POST)
	public @ResponseBody
	Map<Integer, String> getCustomerSelPost() {
		return getCustomer("-- Select Customer --");
	}

	public @ResponseBody
	Map<Integer, String> getCustomer(String startValue) {
		List<MCustomer> customers = commonLogic.getCustomer();

		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(Integer.MIN_VALUE, startValue);
		for (MCustomer MCustomer : customers)
			map.put(MCustomer.getCustomerId(), MCustomer.getCustomerCode());
		return map;
	}

	@RequestMapping(value = "/machineList", method = RequestMethod.GET)
	public @ResponseBody
	List<MMachine> getMachineList(@RequestParam String wip) {
		return commonLogic.getMachineNo(wip);
	}

	@RequestMapping(value = "/machineListActive", method = RequestMethod.GET)
	public @ResponseBody
	List<MMachine> getMachineListActive(@RequestParam String wip) {
		return commonLogic.getMachineNoActive(wip);
	}

	@RequestMapping(value = "/boxMachineNo", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, String> getMachineNo(@RequestParam String wip) {
		List<MMachine> machines = commonLogic.getMachineNo(wip);

		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("", "-- Select Machine --");
		for (MMachine MMachine : machines)
			map.put(MMachine.getMachineId().toString(),
					MMachine.getMachineName());
		return map;
	}

	@RequestMapping(value = "/boxMachineNoActive", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, String> getMachineNoActive(@RequestParam String wip) {
		List<MMachine> machines = commonLogic.getMachineNoActive(wip);

		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("", "-- Select Machine --");
		for (MMachine MMachine : machines)
			map.put(MMachine.getMachineId().toString(),
					MMachine.getMachineName());
		return map;
	}

	@RequestMapping(value = "/boxMachineAllInWIP", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, String> getMachineAllInWIP(@RequestParam String wip) {
		List<MMachine> machines = commonLogic.getMachineNo(wip);

		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("", "-- All Machine --");
		for (MMachine MMachine : machines)
			map.put(MMachine.getMachineId().toString(),
					MMachine.getMachineName());
		return map;
	}

	@RequestMapping(value = "/boxMachineAll", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, String> getMachineNoAll() {
		List<MMachine> machines = commonLogic.getMachineNoAll();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(String.valueOf(Integer.MIN_VALUE), "-- All Machine --");
		for (MMachine MMachine : machines) {
			map.put(MMachine.getMachineId().toString(),
					MMachine.getMachineName());
		}
		return map;
	}

	@RequestMapping(value = "/getMold", method = RequestMethod.GET)
	public @ResponseBody
	MMold getMold(MMold MMold) {
		return commonLogic.getMold(MMold);
	}

	@RequestMapping(value = "/boxMoldName", method = RequestMethod.GET)
	public @ResponseBody
	Map<Integer, String> getMoldName(@RequestParam Integer partId,
			@RequestParam Date reportDate) {
		List<MMoldDetail> moldLst = commonLogic.getMoldName(partId, reportDate);

		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(Integer.MIN_VALUE, "-- Select Mold Name --");
		for (MMoldDetail mMoldDetail : moldLst) {
			map.put(mMoldDetail.getMoldId(), mMoldDetail.getMoldName());
		}
		return map;
	}

	@RequestMapping(value = "/boxPrinterName", method = RequestMethod.POST)
	public @ResponseBody
	List<MPrinter> getAllPrinterName() {
		Class[] classes = PrintServiceLookup.class.getDeclaredClasses();
		for (int i = 0; i < classes.length; i++) {
		    if ("javax.print.PrintServiceLookup$Services".equals(classes[i].getName())) {
		                sun.awt.AppContext.getAppContext().remove(classes[i]);
		        //System.out.println("Refreshed Printer List");
		        break;
		    }
		}

		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();

		PrintService[] prnSvcs = PrintServiceLookup.lookupPrintServices(flavor, aset);
		
		List<MPrinter> printerList = new ArrayList<MPrinter>();
		printerList.add(0,
				new MPrinter("null", " -- Please Select Printer -- "));
		if (prnSvcs.length > 0)
		{
			int i = 0;
			while ( i < prnSvcs.length )
			{
				MPrinter temp = new MPrinter(prnSvcs[i].getName(),
						prnSvcs[i].getName());
				printerList.add(i + 1, temp);
				i++;
			}
		}
		return printerList;
	}

	@RequestMapping(value = "/boxMoldNo", method = RequestMethod.GET)
	public @ResponseBody
	List<MMoldDetail> getMoldNo(@RequestParam Integer moldId,
			@RequestParam Date reportDate) {
		return commonLogic.getMoldNo(moldId, reportDate);
	}

	public Map<Integer, String> getMoldNameByPartId(Integer partId,
			Date reportDate) {
		return getMoldName(partId, reportDate);
	}

	public Map<String, String> getMoldNoByMoldId(Integer moldId, Date reportDate) {

		Map<String, String> moldNoMap = new HashMap<String, String>();
		for (MMoldDetail MMoldDetail : getMoldNo(moldId, reportDate)) {
			moldNoMap.put(MMoldDetail.getMoldNo(), MMoldDetail.getMoldNo());
		}
		return moldNoMap;
	}

	@RequestMapping(value = "/getPart", method = RequestMethod.GET)
	public @ResponseBody
	MPart getPart(MPart MPart) {
		return commonLogic.getPart(MPart);
	}

	@RequestMapping(value = "/boxPartNo", method = RequestMethod.GET)
	public @ResponseBody
	Map<Integer, String> getPartNo(Integer customerId,
			@RequestParam String wip) {
		List<MPart> parts = commonLogic.getPartNo(customerId, wip);

		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(Integer.MIN_VALUE, "-- Select Part --");
		for (MPart MPart : parts)
			map.put(MPart.getPartId(), MPart.getPartNo());
		return map;
	}

	@RequestMapping(value = "/boxPartNameNo", method = RequestMethod.GET)
	public @ResponseBody
	Map<Integer, String> getPartNameNo(Integer customerId,
			@RequestParam String wip, Integer reportType) {
		if (reportType != null && reportType != 1)
			wip = "";
		List<MPart> parts = commonLogic.getPartNo(customerId, wip);

		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(Integer.MIN_VALUE, "-- Select Part --");
		for (MPart MPart : parts)
			map.put(MPart.getPartId(),
					MPart.getPartName() + " : " + MPart.getPartNo());
		return map;
	}

	@RequestMapping(value = "/boxPartNameNoAll", method = RequestMethod.GET)
	public @ResponseBody
	Map<Integer, String> getPartNameNoAll(@RequestParam Integer customerId,
			@RequestParam String wip, Integer reportType) {
		if (reportType != null && reportType != 1)
			wip = "";
		List<MPart> parts = commonLogic.getPartNo(customerId, wip);

		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(Integer.MIN_VALUE, "-- All Part --");
		for (MPart MPart : parts)
			map.put(MPart.getPartId(),
					MPart.getPartName() + " : " + MPart.getPartNo());
		return map;
	}

	@RequestMapping(value = "/txtPartNo", method = RequestMethod.GET)
	public @ResponseBody
	List<MPart> getPartNo(MPart part) {
		return commonLogic.getPartNo(part);
	}

	@RequestMapping(value = "/boxFgNameNo", method = RequestMethod.GET)
	public @ResponseBody
	List<MPart> getFgNameNo(@RequestParam Integer customerId) {
		return commonLogic.getFgList(customerId);
	}

	@RequestMapping(value = "/boxFgNoNameByCustomer", method = RequestMethod.POST)
	public @ResponseBody
	List<MFgPart> getFgNoName(@RequestParam Integer customerId) {
		return commonLogic.getFgNoNameList(customerId);
	}

	@RequestMapping(value = "/getReason", method = RequestMethod.GET)
	public @ResponseBody
	MReason getReason(MReason MReason) {
		return commonLogic.getReason(MReason);
	}

	@RequestMapping(value = "/boxReasonCat", method = RequestMethod.GET)
	public @ResponseBody
	Map<Integer, String> getReasonCat(@RequestParam String wip) {
		List<MReason> reasons = commonLogic.getReasonCat(wip);

		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(Integer.MIN_VALUE, "-- Select Category --");
		for (MReason MReason : reasons)
			map.put(MReason.getReasonId(), MReason.getReasonCode() + " : "
					+ MReason.getReasonName());
		return map;
	}

	@RequestMapping(value = "/boxReasonInCat", method = RequestMethod.GET)
	public @ResponseBody
	Map<Integer, String> getReasonInCat(@RequestParam Integer parentReasonId,
			@RequestParam String wip) {
		List<MReason> reasons = commonLogic.getReasonInCat(parentReasonId, wip);

		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(Integer.MIN_VALUE, "-- Select Reason --");
		for (MReason MReason : reasons)
			map.put(MReason.getReasonId(), MReason.getReasonCode() + " : "
					+ MReason.getReasonName());
		return map;
	}

	@RequestMapping(value = "/boxReasonNG", method = RequestMethod.GET)
	public @ResponseBody
	Map<Integer, String> getReasonNG(@RequestParam String wip) {
		List<MReason> reasons = commonLogic.getReasonNG(wip);

		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(Integer.MIN_VALUE, "-- Selct NG Reason --");
		for (MReason MReason : reasons)
			map.put(MReason.getReasonId(), MReason.getReasonCode() + ":"
					+ MReason.getReasonName());
		return map;
	}

	@RequestMapping(value = "/getReasonNGList", method = RequestMethod.GET)
	public @ResponseBody
	List<MReason> getReasonNGList(@RequestParam String wip) {
		return commonLogic.getReasonNG(wip);
	}

	@RequestMapping(value = "/boxWIP1", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, String> getWIPSel() {
		return getWIPCode(null, "-- Select WIP --");
	}

	@RequestMapping(value = "/boxWIP2", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, String> getWIPAll() {
		return getWIPCode(null, "-- All WIP --");
	}

	public @ResponseBody
	Map<String, String> getWIPSel(Integer wipType) {
		return getWIPCode(wipType, "-- Select WIP --");
	}

	public @ResponseBody
	Map<String, String> getWIPAll(Integer wipType) {
		return getWIPCode(wipType, "-- All WIP --");
	}

	protected Map<String, String> getWIPCode(Integer wipType, String startValue) {
		List<MWip> wips = commonLogic.getWIP(wipType);

		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("", startValue);
		for (MWip MWip : wips)
			map.put(MWip.getWip(), MWip.getWip() + " : " + MWip.getWipName());
		return map;
	}

	@RequestMapping("/txtOnlyWorkOrderNo")
	public @ResponseBody
	List<MWorkOrder> getOnlyWoNo(MWorkOrder workOrder) {
		return commonLogic.getOnlyWorkOrderNo(workOrder);
	}

	public @ResponseBody
	Map<String, String> getMaterialType() {
		String[] materialType = settings
				.getProperty(
						"MRDC.MaterialType",
						"AL1:AL1;AL2:AL2;AL3:AL3;AL4:AL4;AL5:AL5;AL6:AL6;AL7:AL7;AL8:AL8;ZN1:ZN1;ZN2:ZN2;ZN3:ZN3;ZN4:ZN4;ZN5:ZN5;ZN6:ZN6;Other:Other;")
				.split(";");

		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("", "-- All Material Type --");
		for (String mType : materialType) {
			int seperatorIndex = mType.indexOf(':');
			String key = mType.substring(0, seperatorIndex).trim();
			String val = mType.substring(seperatorIndex + 1).trim();
			map.put(key, val);
		}
		return map;
	}

	public @ResponseBody
	Map<Integer, String> getMaterialCodeName() {
		MMaterial MMaterial = new MMaterial();
		List<MMaterial> m = commonLogic.getMaterialList(MMaterial);

		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(Integer.MIN_VALUE, "-- All Material Type --");
		for (MMaterial mMaterial : m)
			map.put(mMaterial.getMaterialId(), mMaterial.getMaterialCode()
					+ " : " + mMaterial.getMaterialName());
		return map;
	}

	public @ResponseBody
	Map<String, String> getCategory() {
		TDailyWK TDailyWK = new TDailyWK();
		TDailyWK = commonLogic.getCategory(TDailyWK);

		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("", "-- All Category Type --");
		for (String category : TDailyWK.getCategoryList())
			map.put(category, category);
		return map;
	}

	@RequestMapping("/partAutoComplete")
	public @ResponseBody
	List<MPart> partAutoComplete(MPart MPart) {
		MPart.setCustomerId(MPart.getCustomerId() < 0 ? null : MPart
				.getCustomerId());
		MPart.setWip(MPart.getWip() == "" ? null : MPart.getWip());
		return commonLogic.partAutoComplete(MPart);
	}

	@RequestMapping("/workOrderAutoComplete")
	public @ResponseBody
	List<MWorkOrder> workOrderAutoComplete(MWorkOrder MWorkOrder) {
		MWorkOrder.setPartId(MWorkOrder.getPartId() < 0 ? null : MWorkOrder
				.getPartId());
		return commonLogic.workOrderAutoComplete(MWorkOrder);
	}

	@RequestMapping("/queryPartId")
	public @ResponseBody
	MPart queryPartId(MPart MPart) {
		MPart.setCustomerId(MPart.getCustomerId() < 0 ? null : MPart
				.getCustomerId());
		MPart.setWip(MPart.getWip() == "" ? null : MPart.getWip());
		List<MPart> partList = commonLogic.queryPartId(MPart);
		if (partList.size() == 1) {
			MPart.setPartId(partList.get(0).getPartId());
		} else {
			MPart.setPartId(-1);
		}

		return MPart;
	}

	public @ResponseBody
	Map<String, String> stockMonthYear() {
		// criteria on WIP & FG Junk screen.
		Calendar currentCal = new GregorianCalendar(Locale.US);
		Integer currentMonth = currentCal.get(Calendar.MONTH);
		Integer currentYear = currentCal.get(Calendar.YEAR);

		Map<Integer, String> monthMap = getMonthMap();
		Map<String, String> stockMonth = new LinkedHashMap<String, String>();
		// currentYear + "-" + (currentMonth + 1) + "-01"
		stockMonth.put(String.format("%04d-%02d-%02d", currentYear,
				(currentMonth + 1), 1), monthMap.get(currentMonth) + " "
				+ currentYear);
		if (0 < currentMonth) {
			// currentYear + "-" + (currentMonth) + "-01"
			stockMonth.put(String.format("%04d-%02d-%02d", currentYear,
					(currentMonth), 1), monthMap.get(currentMonth - 1) + " "
					+ currentYear);
		} else {
			// currentYear-1 + "-" + Integer.valueOf(12) + "-01"
			stockMonth
					.put(String
							.format("%04d-%02d-%02d", currentYear - 1, 12, 1),
							monthMap.get(Integer.valueOf(11)) + " "
									+ (currentYear - 1));
		}
		return stockMonth;
	}
}
