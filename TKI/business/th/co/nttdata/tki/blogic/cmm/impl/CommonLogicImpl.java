package th.co.nttdata.tki.blogic.cmm.impl;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MFgPart;
import th.co.nttdata.tki.bean.MMachine;
import th.co.nttdata.tki.bean.MMaterial;
import th.co.nttdata.tki.bean.MMenu;
import th.co.nttdata.tki.bean.MMold;
import th.co.nttdata.tki.bean.MMoldDetail;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.MPrinter;
import th.co.nttdata.tki.bean.MReason;
import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.bean.TDailyWK;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.cmm.CommonLogic;
import th.co.nttdata.tki.dao.MCustomerDao;
import th.co.nttdata.tki.dao.MMachineDao;
import th.co.nttdata.tki.dao.MMaterialDao;
import th.co.nttdata.tki.dao.MMenuDao;
import th.co.nttdata.tki.dao.MMoldDao;
import th.co.nttdata.tki.dao.MMoldDetailDao;
import th.co.nttdata.tki.dao.MPartDao;
import th.co.nttdata.tki.dao.MPrinterDao;
import th.co.nttdata.tki.dao.MReasonDao;
import th.co.nttdata.tki.dao.MWipDao;
import th.co.nttdata.tki.dao.MWorkOrderDao;
import th.co.nttdata.tki.dao.TDailyWKDao;

@Service
public class CommonLogicImpl extends AbstractBaseLogic implements CommonLogic {

	@Autowired
	private MCustomerDao customerDao;
	@Autowired
	private MMachineDao machineDao;
	@Autowired
	private MMoldDao moldDao;
	@Autowired
	private MMoldDetailDao moldDetailDao;
	@Autowired
	private MPartDao partDao;
	@Autowired
	private MReasonDao reasonDao;
	@Autowired
	private MWipDao wipDao;
	@Autowired
	private MWorkOrderDao workOrderDao;
	@Autowired
	private MMenuDao menuDao;
	@Autowired
	private TDailyWKDao tDailyWKDao;
	@Autowired
	private MMaterialDao mMaterialDao;
	@Autowired
	private MPrinterDao mPrinterDao;

	@Override
	public List<MCustomer> getCustomer() {
		return customerDao.getCustomerList();
	}

	@Override
	public List<MMachine> getMachineNo(String wip) {
		if (isBlank(wip)) {
			return new LinkedList<MMachine>();
		}

		MMachine MMachine = new MMachine();
		MMachine.setWip(wip);
		return machineDao.getMachineList(MMachine);
	}

	@Override
	public List<MMachine> getMachineNoActive(String wip) {
		if (isBlank(wip)) {
			return new LinkedList<MMachine>();
		}

		MMachine MMachine = new MMachine();
		MMachine.setWip(wip);
		return machineDao.getMachineListActive(MMachine);
	}

	@Override
	public List<MMachine> getMachineNoAll() {
		return machineDao.getMachineList();
	}

	@Override
	public MMold getMold(MMold MMold) {
		return moldDao.getMold(MMold);
	}

	@Override
	public List<MMold> getMoldList(MMold MMold) {
		if (MMold == null)
			return new LinkedList<MMold>();
		return moldDao.getMoldList(MMold);
	}

	@Override
	public List<MMoldDetail> getMoldName(Integer partId, Date reportDate) {
		if (partId == null)
			return new LinkedList<MMoldDetail>();

		MMoldDetail MMoldDetail = new MMoldDetail();
		MMoldDetail.setPartId(partId);
		MMoldDetail.setStartDate(reportDate);
		return moldDetailDao.getMoldName(MMoldDetail);
	}

	@Override
	public List<MMoldDetail> getMoldNo(Integer moldId, Date reportDate) {
		if (moldId == null)
			return new LinkedList<MMoldDetail>();

		MMoldDetail MMoldDetail = new MMoldDetail();
		MMoldDetail.setMoldId(moldId);
		MMoldDetail.setStartDate(reportDate);
		return moldDetailDao.getMoldNo(MMoldDetail);
	}

	@Override
	public MPart getPart(MPart MPart) {
		return partDao.getPart(MPart);
	}

	@Override
	public List<MPart> getPartNo(Integer customerId, String wip) {
		// <!-- Validating Parameter. -->
		if (customerId == null)
			return new LinkedList<MPart>();

		MPart MPart = new MPart();
		MPart.setCustomerId(customerId);
		MPart.setWip(wip);

		return isBlank(MPart.getWip()) ? partDao.getPartList(MPart) : partDao
				.getPartInWip(MPart);
	}

	@Override
	public List<MPart> getPartNo(MPart MPart) {
		return partDao.getPartList(MPart);
	}

	@Override
	public List<MPart> getFgList(Integer customerId) {
		MPart part = new MPart();
		part.setCustomerId(customerId);
		return partDao.getFgList(part);
	}

	@Override
	public MReason getReason(MReason MReason) {
		return reasonDao.getReason(MReason);
	}

	@Override
	public List<MReason> getReasonMCStopList() {
		return reasonDao.getReasonMCStopList();
	}

	@Override
	public List<MReason> getReasonCat(String wip) {
		MReason MReason = new MReason();
		MReason.setReasonType(2);
		MReason.setWip(wip);
		return reasonDao.getReasonInWip(MReason);
	}

	@Override
	public List<MReason> getReasonInCat(Integer parentReasonId, String wip) {
		MReason MReason = new MReason();
		MReason.setParentReasonId(parentReasonId);
		MReason.setReasonType(2);
		MReason.setWip(wip);
		return reasonDao.getReasonInWip(MReason);
	}

	@Override
	public List<MReason> getReasonNG(String wip) {
		MReason MReason = new MReason();
		MReason.setReasonType(1);
		MReason.setWip(wip);
		return reasonDao.getReasonInWip(MReason);
	}

	@Override
	public List<MWip> getWIP(Integer wipType) {
		if (wipType == null)
			wipType = -1;

		switch (wipType) {
		case 1:
			return wipDao.getWipMachine();
		case 2:
			return wipDao.getWipWorker();
		case 3:
			return wipDao.getWipMachineWorker();
		default:
			return wipDao.getWip();
		}
	}

	@Override
	public List<MWip> getWIPMachine() {
		return wipDao.getWipMachine();
	}

	@Override
	public List<MWip> getWIPWorker() {
		return wipDao.getWipWorker();
	}

	@Override
	public List<MWip> getWIPMachineWorker() {
		return wipDao.getWipMachineWorker();
	}

	@Override
	public List<MWorkOrder> getOnlyWorkOrderNo(MWorkOrder workOrder) {
		return workOrderDao.getOnlyWorkOrderNo(workOrder);
	}

	@Override
	public List<MMenu> getMenuByLoginUser() {
		return menuDao.getMenuByLoginUser();
	}

	@Override
	public TDailyWK getCategory(TDailyWK TDailyWK) {
		return tDailyWKDao.getCategoryList(TDailyWK);
	}

	@Override
	public List<MMaterial> getMaterialList(MMaterial MMaterial) {
		return mMaterialDao.getMaterialList(MMaterial);
	}

	@Override
	public MCustomer getMCustomer(MCustomer MCustomer) {
		return customerDao.getMCustomer(MCustomer.getCustomerId());
	}

	@Override
	public List<MPart> partAutoComplete(MPart MPart) {
		return partDao.partAutoComplete(MPart);
	}

	@Override
	public List<MWorkOrder> workOrderAutoComplete(MWorkOrder workOrder) {
		return workOrderDao.workOrderAutoComplete(workOrder);
	}

	@Override
	public List<MPart> queryPartId(MPart MPart) {
		return partDao.queryPartId(MPart);
	}

	@Override
	public List<MFgPart> getFgNoNameList(Integer customerId) {
		MFgPart part = new MFgPart();
		part.setCustomerId(customerId);
		return partDao.getFgNoNameList(part);
	}

	@Override
	public List<MPrinter> getAllPrinterName() {
		return mPrinterDao.getAllPrinter();
	}
}