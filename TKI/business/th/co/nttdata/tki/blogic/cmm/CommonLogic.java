package th.co.nttdata.tki.blogic.cmm;

import java.util.Date;
import java.util.List;

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

public interface CommonLogic {

	public List<MCustomer> getCustomer();

	public List<MMachine> getMachineNo(String wip);

	public List<MMachine> getMachineNoActive(String wip);

	public List<MMachine> getMachineNoAll();

	public MMold getMold(MMold MMold);

	public List<MMold> getMoldList(MMold MMold);

	public List<MMoldDetail> getMoldName(Integer partId, Date reportDate);

	public List<MMoldDetail> getMoldNo(Integer moldId, Date reportDate);

	public MPart getPart(MPart MPart);

	public List<MPart> getPartNo(Integer customerId, String wip);

	public List<MPart> getPartNo(MPart MPart);

	public MReason getReason(MReason MReason);

	public List<MReason> getReasonMCStopList();

	public List<MReason> getReasonCat(String wip);

	public List<MReason> getReasonInCat(Integer parentReasonId, String wip);

	public List<MReason> getReasonNG(String wip);

	public List<MWip> getWIP(Integer wipType);

	public List<MWip> getWIPMachine();

	public List<MWip> getWIPWorker();

	public List<MWip> getWIPMachineWorker();

	public List<MPart> getFgList(Integer customerId);

	public List<MWorkOrder> getOnlyWorkOrderNo(MWorkOrder workOrder);

	public List<MMenu> getMenuByLoginUser();

	public TDailyWK getCategory(TDailyWK TDailyWK);

	public List<MMaterial> getMaterialList(MMaterial MMaterial);

	public MCustomer getMCustomer(MCustomer MCustomer);

	public List<MPart> partAutoComplete(MPart MPart);

	public List<MWorkOrder> workOrderAutoComplete(MWorkOrder workOrder);

	public List<MPart> queryPartId(MPart MPart);

	public List<MFgPart> getFgNoNameList(Integer customerId);

	public List<MPrinter> getAllPrinterName();

}