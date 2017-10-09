package th.co.nttdata.tki.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TDailyMCDetail {

	private Integer dailyMCId;
	private Integer ng;
	private Integer ok;
	private Integer pd;
	private Integer qty;
	private Integer reportTime;
	private List<TDailyMCNGReason> reasonList;
	private Map<Integer,Integer> reasons;
	private Map<Integer,Integer> stops;
	
	//MRDC_R09
	private Date 	operationDate;
	private Double 	nStandardProductivity;
	private Double 	nStandardProductionPrice;
	private Double 	nActualOperatingProductivity;
	private Double 	nActualProductionPrice;
	private Double 	nDiff;
	private Double 	nPercentage;
	private Double 	okShotQty;
	private Double 	shotQty;
	private Double 	weightPerUnit;
	private Integer cavQty;
	private Integer partId;
	private Integer trialQty;
	private String 	actualTime;
	private String 	nonTime;
	private String 	shift;
	private String 	partName;
	private String 	partNo;
	private String 	category;
	private String 	careerSheetNo;
	private String 	process ;
	private String 	machine;
	private String 	worker;
	private String 	mold;
	private String 	material;
	
	//MRDC_R17
	private Double 	nOKDiecastingCost;
	private Integer moldId;
	private Integer	nTotalQty;
	private String  idRef;
	private String  sMoldNo;
	private String 	nOKRatio;
	private String 	nMachineStopTime;
	private String 	nOKCycelTime;
	private String 	nActualOperationTime;

	public Integer getDailyMCId() {
		return dailyMCId;
	}
	public void setDailyMCId(Integer dailyMCId) {
		this.dailyMCId = dailyMCId;
	}
	public Integer getNg() {
		return ng;
	}
	public void setNg(Integer ng) {
		this.ng = ng;
	}
	public Integer getOk() {
		return ok;
	}
	public void setOk(Integer ok) {
		this.ok = ok;
	}
	public Integer getPd() {
		return pd;
	}
	public void setPd(Integer pd) {
		this.pd = pd;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public Integer getReportTime() {
		return reportTime;
	}
	public void setReportTime(Integer reportTime) {
		this.reportTime = reportTime;
	}
	public void setReasonList(List<TDailyMCNGReason> reasonList) {
		this.reasonList = reasonList;
	}
	public List<TDailyMCNGReason> getReasonList() {
		return reasonList;
	}
	public Map<Integer, Integer> getReasons() {
		return reasons;
	}
	public void setReasons(Map<Integer, Integer> reasons) {
		this.reasons = reasons;
	}
	public Map<Integer, Integer> getStops() {
		return stops;
	}
	public void setStops(Map<Integer, Integer> stops) {
		this.stops = stops;
	}
	public Date getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}
	public Integer getCavQty() {
		return cavQty;
	}
	public void setCavQty(Integer cavQty) {
		this.cavQty = cavQty;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCareerSheetNo() {
		return careerSheetNo;
	}
	public void setCareerSheetNo(String careerSheetNo) {
		this.careerSheetNo = careerSheetNo;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}
	public String getWorker() {
		return worker;
	}
	public void setWorker(String worker) {
		this.worker = worker;
	}
	public String getMold() {
		return mold;
	}
	public void setMold(String mold) {
		this.mold = mold;
	}
	public Double getnOKDiecastingCost() {
		return nOKDiecastingCost;
	}
	public void setnOKDiecastingCost(Double nOKDiecastingCost) {
		this.nOKDiecastingCost = nOKDiecastingCost;
	}
	public Integer getMoldId() {
		return moldId;
	}
	public void setMoldId(Integer moldId) {
		this.moldId = moldId;
	}
	public Integer getnTotalQty() {
		return nTotalQty;
	}
	public void setnTotalQty(Integer nTotalQty) {
		this.nTotalQty = nTotalQty;
	}
	public String getnOKRatio() {
		return nOKRatio;
	}
	public void setnOKRatio(String nOKRatio) {
		this.nOKRatio = nOKRatio;
	}
	public String getnMachineStopTime() {
		return nMachineStopTime;
	}
	public void setnMachineStopTime(String nMachineStopTime) {
		this.nMachineStopTime = nMachineStopTime;
	}
	public String getnOKCycelTime() {
		return nOKCycelTime;
	}
	public void setnOKCycelTime(String nOKCycelTime) {
		this.nOKCycelTime = nOKCycelTime;
	}
	public String getnActualOperationTime() {
		return nActualOperationTime;
	}
	public void setnActualOperationTime(String nActualOperationTime) {
		this.nActualOperationTime = nActualOperationTime;
	}
	public String getIdRef() {
		return idRef;
	}
	public void setIdRef(String idRef) {
		this.idRef = idRef;
	}
	public String getsMoldNo() {
		return sMoldNo;
	}
	public void setsMoldNo(String sMoldNo) {
		this.sMoldNo = sMoldNo;
	}
	public Double getOkShotQty() {
		return okShotQty;
	}
	public void setOkShotQty(Double okShotQty) {
		this.okShotQty = okShotQty;
	}
	public Double getShotQty() {
		return shotQty;
	}
	public void setShotQty(Double shotQty) {
		this.shotQty = shotQty;
	}
	public String getActualTime() {
		return actualTime;
	}
	public void setActualTime(String actualTime) {
		this.actualTime = actualTime;
	}
	public String getNonTime() {
		return nonTime;
	}
	public void setNonTime(String nonTime) {
		this.nonTime = nonTime;
	}
	public Integer getPartId() {
		return partId;
	}
	public void setPartId(Integer partId) {
		this.partId = partId;
	}
	public Integer getTrialQty() {
		return trialQty;
	}
	public void setTrialQty(Integer trialQty) {
		this.trialQty = trialQty;
	}
	public Double getWeightPerUnit() {
		return weightPerUnit;
	}
	public void setWeightPerUnit(Double weightPerUnit) {
		this.weightPerUnit = weightPerUnit;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public Double getnStandardProductivity() {
		return nStandardProductivity;
	}
	public void setnStandardProductivity(Double nStandardProductivity) {
		this.nStandardProductivity = nStandardProductivity;
	}
	public Double getnStandardProductionPrice() {
		return nStandardProductionPrice;
	}
	public void setnStandardProductionPrice(Double nStandardProductionPrice) {
		this.nStandardProductionPrice = nStandardProductionPrice;
	}
	public Double getnActualOperatingProductivity() {
		return nActualOperatingProductivity;
	}
	public void setnActualOperatingProductivity(Double nActualOperatingProductivity) {
		this.nActualOperatingProductivity = nActualOperatingProductivity;
	}
	public Double getnActualProductionPrice() {
		return nActualProductionPrice;
	}
	public void setnActualProductionPrice(Double nActualProductionPrice) {
		this.nActualProductionPrice = nActualProductionPrice;
	}
	public Double getnDiff() {
		return nDiff;
	}
	public void setnDiff(Double nDiff) {
		this.nDiff = nDiff;
	}
	public Double getnPercentage() {
		return nPercentage;
	}
	public void setnPercentage(Double nPercentage) {
		this.nPercentage = nPercentage;
	}
}