package th.co.nttdata.tki.blogic.prd.impl;

import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MMachine;
import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.bean.TDCPlan;
import th.co.nttdata.tki.blogic.prd.PRD_S03Logic;
import th.co.nttdata.tki.dao.MCustomerDao;
import th.co.nttdata.tki.dao.MMachineDao;
import th.co.nttdata.tki.dao.MWipDao;
import th.co.nttdata.tki.dao.TDCPlanDao;
import th.co.nttdata.tki.dao.TDailyMCDetailDao;
import th.co.nttdata.tki.dao.TLotNoDao;

@Service
public class PRD_S03LogicImpl implements PRD_S03Logic {

	/* A MWipDao variable for accessing database layer */
	@Autowired
	private MWipDao mWipDao;

	/* A MMachineDao variable for accessing database layer */
	@Autowired
	private MMachineDao mMachineDao;

	/* A MCustomerDao variable for accessing database layer */
	@Autowired
	private MCustomerDao mCustomerDao;

	/* A TDCPlanDao variable for accessing database layer */
	@Autowired
	private TDCPlanDao tDCPlanDao;

	/* A TDailyMCDetailDao variable for accessing database layer */
	@Autowired
	private TDailyMCDetailDao tDailyMCDetailDao;

	/* A TLotNoDao variable for accessing database layer */
	@Autowired
	private TLotNoDao tLotNoDao;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private HttpServletRequest request;

	@Override
	public List<MWip> getWipByWipType() {
		return mWipDao.getWipByWipType();
	}

	@Override
	public List<MMachine> getMachineByWip(String param) {
		return mMachineDao.getMachineByWip(param);
	}

	@Override
	public List<MCustomer> getCustomer(String param) {
		return mCustomerDao.getCustomerMaster(param);
	}

	@SuppressWarnings("unused")
	private Integer convertToInteger(String param) {
		try {
			return Integer.parseInt(param);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public TDCPlan getDefaultValues() {
		TDCPlan object = new TDCPlan();
		List<MCustomer> customerList = new ArrayList<MCustomer>();
		customerList.add(new MCustomer(null, "--All--", null));
		object.setCustomerList(customerList);

		List<MMachine> machineList = new ArrayList<MMachine>();
		machineList.add(new MMachine(null, "--All--"));
		object.setMachineList(machineList);
		object.setMachineToList(machineList);

		return object;
	}

	@Override
	public List<TDCPlan> search(TDCPlan param) {
		return tDCPlanDao.prdS03Search(param);
	}

	@Override
	public TDCPlan save(List<Map<String, Object>> param) throws Exception {
		TDCPlan object = new TDCPlan();
		List<TDCPlan> deleteList = new ArrayList<TDCPlan>();
		object.setPlanList(this.convertToSave(param));
		for (TDCPlan obj : object.getPlanList()) {
			if (tDailyMCDetailDao.selectCheckInputActual(obj) == 0) {
				deleteList.add(obj);
			} else {
				object.getErrors().add(
						new Message("err.prd.002", new String[] { obj
								.getLotNo() }));
			}
		}
		if (!deleteList.isEmpty()) {
			object.setPlanList(deleteList);
			tLotNoDao.prdS03DeleteLotNo(object);
		}
		return object;
	}

	private List<TDCPlan> convertToSave(List<Map<String, Object>> param)
			throws Exception {
		ConvertUtils.register(new DateConverter(null), java.util.Date.class);
		List<TDCPlan> list = new ArrayList<TDCPlan>();
		TDCPlan object = null;
		for (int i = 0; i < param.size(); i++) {
			object = new TDCPlan();
			BeanUtils.populate(object, param.get(i));
//			System.out.println(i+".) "+param.get(i));
			list.add(object);
		}
		return list;
	}

	@Override
	public TDCPlan print(List<Map<String, Object>> param, TDCPlan object)
			throws Exception {
		List<TDCPlan> list = convertToSave(param);
		List<List<TDCPlan>> printList = new ArrayList<List<TDCPlan>>();
		if (null == request.getSession().getAttribute("prdS03Printer")) {
			request.getSession().setAttribute("prdS03Printer",
					list.get(0).getPrinterName());
		} else {
			request.getSession().setAttribute("prdS03Printer",
					list.get(0).getPrinterName());
		}
		String printerName = list.get(0).getPrinterName();

		for (TDCPlan obj : list) {
			List<TDCPlan> o = tLotNoDao.getDataForPrint(obj);
			if(o != null && o.size() > 0){
				printList.add(o);
			}
		}
		if (!printList.isEmpty()) {
			object = this.printJasperReport(this.convertObjectList(printList),
					printerName, object);
		}
		object.setPrinterName((String) request.getSession().getAttribute(
				"prdS03Printer"));
		for (TDCPlan obj : list) {
			if (!obj.getPrintingStatus().booleanValue()) {
				tLotNoDao.updatePrintStatus(obj);
			}
		}
		return object;
	}

	private TDCPlan printJasperReport(List<TDCPlan> printList,
			String printerName, TDCPlan param) throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("logo",
				servletContext.getRealPath("image/login/logo.gif"));
		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(
				printList);
		JasperReport jasperReport = JasperCompileManager
				.compileReport(servletContext
						.getRealPath("WEB-INF/classes/prd_s03.jrxml"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				parameters, beanDataSource);
		PrinterJob job = PrinterJob.getPrinterJob();
		PrintService[] services = PrintServiceLookup.lookupPrintServices(null,
				null);
		int selectedService = -1;
		for (int i = 0; i < services.length; i++) {
			if (printerName.equalsIgnoreCase(services[i].getName())) {
				selectedService = i;
				break;
			}
		}
		PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
		job.defaultPage(pageFormat);
		try {
			job.setPrintService(services[selectedService]);
			JRPrintServiceExporter exporter = new JRPrintServiceExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			/* Set the selected service and pass it as parameters */
			exporter.setParameter(
					JRPrintServiceExporterParameter.PRINT_SERVICE,
					services[selectedService]);
			exporter.setParameter(
					JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET,
					services[selectedService].getAttributes());
			PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
			printRequestAttributeSet.add(new Copies(1));
			printRequestAttributeSet.add(MediaSizeName.ISO_A5);
			exporter.setParameter(
					JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET,
					printRequestAttributeSet);
			exporter.setParameter(
					JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG,
					Boolean.FALSE);
			exporter.setParameter(
					JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG,
					Boolean.FALSE);
			exporter.exportReport();
			param.getInfos().add(new Message("inf.cmm.006", new String[] {}));
		} catch (Exception e) {
			e.printStackTrace();
			param.getErrors().add(
					new Message("err.prd.003", new String[] { "" }));
		}
		return param;
	}

	private List<TDCPlan> convertObjectList(List<List<TDCPlan>> printList) {
		List<TDCPlan> reportList = new ArrayList<TDCPlan>();
		for (int i = 0; i < printList.size(); i++) {
			List<TDCPlan> adjustList = this.ajustReportStatus(printList.get(i));
			reportList.addAll(adjustList);
		}
		return reportList;
	}

	private List<TDCPlan> ajustReportStatus(List<TDCPlan> list) {
		int allLine = 18;
		int adjust = allLine - list.size();
		if (adjust != 0) {
			for (int i = 0; i < adjust; i++) {
				TDCPlan tdc = new TDCPlan();
				if (list.size() > 0) {
					tdc.setReasonName(list.get(0).getReasonName());
				}
				list.add(tdc);
			}
		}
		return list;
	}

	@Override
	public List<MCustomer> getAllCustomer() {
		List<MCustomer> list = mCustomerDao.getAllCustomer();
		list.add(0, new MCustomer(null, "-- All Customer -- ", null));
		return list;
	}
}
