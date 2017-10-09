package th.co.nttdata.tki.controller.dal;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import th.co.nttdata.tki.bean.MMachine;
import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.MReportType;
import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.bean.TDailyMC;
import th.co.nttdata.tki.blogic.dal.DAL_S02Logic;
import th.co.nttdata.tki.controller.AbstractBaseController;
import th.co.nttdata.tki.controller.cmm.CommonController;
import th.co.nttdata.tki.dao.MasterDao;

@Controller
public class DAL_S02Controller extends AbstractBaseController {

	private static final String PATH_URI = "DAL/DAL_S02";
	@Autowired
	private MasterDao masterDao;
	@Autowired
	private CommonController commonController;
	@Autowired
	private DAL_S02Logic dalS02Logic;

	@RequestMapping("/DAL_S02")
	public ModelAndView init() {
		return new ModelAndView(PATH_URI)
			.addObject("dailyMC", new TDailyMC())
			.addObject("reportTypeList", masterDao.selectReportTypeList(new MReportType().setReportCategory(new String[]{"wip"})))
			.addObject("wipMap", commonController.getWIPSel(1))
			.addObject("machineMap", commonController.getMachineNoActive(null))
			.addObject("customerMap", commonController.getCustomerSel())
			.addObject("partMap", commonController.getPartNameNo(null, null, null))
			.addObject("moldName",commonController.getMoldName(null,null))
			.addObject("moldNo", commonController.getMoldNo( null,null ))
			.addObject("minReportDate", settings.getProperty("DAL.reportDate.minDate", "-40d"));
	}

	@RequestMapping("/DAL_S02_edit")
	public ModelAndView edit( TDailyMC TDailyMC ) {
		Date reportDate = (Date) TDailyMC.getReportDate();
		TDailyMC.setReportDate(null);
		TDailyMC = dalS02Logic.edit(TDailyMC);

		Set<Integer> reasonNGKeySet = new HashSet<Integer>();
		Map<Integer,Integer> reasonInCatKeySet = new LinkedHashMap<Integer,Integer>();
		Map<Integer,Map<Integer,String>> reasonCatKeySet = new LinkedHashMap<Integer,Map<Integer,String>>();
		// <!-- Checking: if no 'ng reason'. -->
		Map<Integer,Integer> reasonMap = TDailyMC.getDetails().get(0).getReasons();
		if( reasonMap != null && reasonMap.size() > 0 ) {
			reasonNGKeySet = reasonMap.keySet();
		}

		// <!-- Checking: if no 'stop machine'. -->
		Map<Integer,Integer> stopMap = TDailyMC.getDetails().get(0).getStops();
		if( stopMap != null && stopMap.size() > 0 )
			for( Map.Entry<Integer,Integer> entry : stopMap.entrySet() ) {
				MReason MReason = new MReason();
				MReason.setReasonId(entry.getKey());
				MReason = commonController.getReason(MReason);
	
				// <!-- Providing the data. -->
				Integer reasonId = entry.getKey();
				Integer parentId = MReason.getParentReasonId();
				Map<Integer,String> reasonInCatMap = commonController.getReasonInCat(parentId, TDailyMC.getWip());
	
				reasonInCatKeySet.put(reasonId, parentId);
				reasonCatKeySet.put(reasonId, reasonInCatMap);
			}
		
		// <!-- Assigning: Yesterday. -->
		Calendar calendar = new GregorianCalendar(Locale.US);
		calendar.add( Calendar.DAY_OF_YEAR, -1 );
		calendar = DateUtils.truncate(calendar, Calendar.DATE);

		return new ModelAndView(PATH_URI)
			.addObject("dailyMC", TDailyMC)
			.addObject("reasonNGKeySet", reasonNGKeySet)
			.addObject("reasonCatKeySet", reasonCatKeySet)
			.addObject("reasonInCatKeySet", reasonInCatKeySet)
			.addObject("reportTypeList", masterDao.selectReportTypeList(new MReportType().setReportCategory(new String[]{"wip"})))
			.addObject("wipMap", commonController.getWIPSel(1))
			.addObject("machineMap", commonController.getMachineNo(TDailyMC.getWip()))
			.addObject("customerMap", commonController.getCustomerSel())
			.addObject("partMap", commonController.getPartNameNo(TDailyMC.getCustomerId(), TDailyMC.getWip(), TDailyMC.getReportType()))
			.addObject("moldName", commonController.getMoldNameByPartId(TDailyMC.getPartId(),reportDate))
			.addObject("moldNo", commonController.getMoldNoByMoldId(TDailyMC.getMoldId(),reportDate))
			.addObject("reasonNGMap", commonController.getReasonNG(TDailyMC.getWip()))
			.addObject("reasonCatMap", commonController.getReasonCat(TDailyMC.getWip()))
			.addObject("minDate", calendar.getTime());
	}

	@RequestMapping(value="/DAL_S02_check", method=RequestMethod.POST)
	public @ResponseBody TDailyMC check( TDailyMC TDailyMC ) {
		try {
			TDailyMC = dalS02Logic.check(TDailyMC);
		} catch( Exception e ) {
			setSystemError(TDailyMC,e);
		}

		return TDailyMC;
	}

	@RequestMapping(value="/DAL_S02_delete", method=RequestMethod.GET)
	public ModelAndView delete( TDailyMC TDailyMC ) {
		try {
			dalS02Logic.delete(TDailyMC);
		} catch( Exception e ) {
			setSystemError(TDailyMC,e);
		}

		return new ModelAndView(PATH_URI)
			.addObject("dailyMC", TDailyMC)
			.addObject("reportTypeList", masterDao.selectReportTypeList(new MReportType().setReportCategory(new String[]{"wip"})))
			.addObject("wipMap", commonController.getWIPSel(1))
			.addObject("machineMap", commonController.getMachineNoActive(null))
			.addObject("customerMap", commonController.getCustomerSel())
			.addObject("partMap", commonController.getPartNameNo(null, null, null))
			.addObject("moldName",commonController.getMoldName(null,null))
			.addObject("moldNo", commonController.getMoldNo( null,null ));
	}

	@RequestMapping(value="/DAL_S02_save", method=RequestMethod.POST)
	public @ResponseBody TDailyMC save( TDailyMC TDailyMC ) {
		try {
			dalS02Logic.save(TDailyMC);
		} catch( Exception e ) {
			setSystemError(TDailyMC,e);
		}

		return TDailyMC;
	}
	
	@RequestMapping(value="/DAL_S02_lotno", method=RequestMethod.POST)
	public @ResponseBody MWorkOrder DisplayCPByLotno( String lotNo, String wip,Integer reportType  ) {
		return dalS02Logic.searchLotno(lotNo,wip,reportType);
	}
	
	@RequestMapping(value="/DAL_S02_workOrderNoList")
	public @ResponseBody List<Map<String, String>> workOrderNoList( MWorkOrder mWorkOrder ) {
		List<Map<String, String>> workOrderList = new LinkedList<Map<String, String>>();
		mWorkOrder = dalS02Logic.getWorkOrderList(mWorkOrder);
		for( MWorkOrder obj : mWorkOrder.getmWorkOrderLst()) {
			Map<String,String> map = new LinkedHashMap<String,String>();
			map.put("label", obj.getWorkOrderNo());
			map.put("workOrderNo", obj.getWorkOrderNo());
			workOrderList.add(map);
		}
		return workOrderList;
	}
	
	@RequestMapping(value="/DAL_S02_getMachine")
	public @ResponseBody MMachine getMachine(String machineId){
		return dalS02Logic.getMachine(machineId);
	}
	
	@RequestMapping(value="/DAL_S02_getLotByMold")
	public @ResponseBody MWorkOrder getLotByMold(String moldId, String moldNo){
		return dalS02Logic.getLotByMold(moldId, moldNo);
	}
	
	
}