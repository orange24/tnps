package th.co.nttdata.tki.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TDailyWKDetail {

	private Date reportDate;
	private Double manPower;
	private Double timeUsed;
	private Integer customerId;
	private Integer dailyWKId;
	private Integer dailyWKDetailId;
	private Integer ng;
	private Integer ok;
	private Integer partId;
	private Integer pd;
	private Integer qty;
	private Integer workOrderQty;
	private String customerCode;
	private String datailRef;// idRef of DAL_R03
	private String lotNo;
	private String machineName;
	private String partName;
	private String partNo;
	private String shift;
	private String shiftName;
	private String wip;
	private String staff;
	private String workOrderNo;
	private String currency;
	private List<TDailyWKNGReason> reasonList;
	private Map<Integer, Integer> ngReasonMap;

	// MRDC_S12
	private Double secondQty;
	private Double shotQty;
	private Double standardProductionPrice;
	private Double actualOperatingProductivity;
	private Double actualProductionPrice;
	private Integer id;
	private Integer machineId;
	private String ngRatio;
	private String nOperationTime;
	private String wipName;
	private String customerName;
	private String typeName;

	// MRDC_R13
	private Double actualProductivity;
	private Double standardProductivity;
	private Double standardProduction;
	private Double actualProduction;
	private Double diff;
	private Double percentage;

	// MRDC_R22
	private Integer totalQty;
	private String materialType;

	// MRDC_R02
	private Double unitWeight;
	private Double saleUnitPrice;
	private String category;
	private String materialName;

	// MRDC_R10
	private Double sortingTimeMin;
	private Double weightPerUnit;
	private String material;
	private String sortingTime;

	public Double getManPower() {
		return manPower;
	}

	public void setManPower(Double manPower) {
		this.manPower = manPower;
	}

	public Double getTimeUsed() {
		return timeUsed;
	}

	public void setTimeUsed(Double timeUsed) {
		this.timeUsed = timeUsed;
	}

	public Double getSecondQty() {
		return secondQty;
	}

	public void setSecondQty(Double secondQty) {
		this.secondQty = secondQty;
	}

	public Double getShotQty() {
		return shotQty;
	}

	public void setShotQty(Double shotQty) {
		this.shotQty = shotQty;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMachineId() {
		return machineId;
	}

	public void setMachineId(Integer machineId) {
		this.machineId = machineId;
	}

	public String getNgRatio() {
		return ngRatio;
	}

	public void setNgRatio(String ngRatio) {
		this.ngRatio = ngRatio;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getDailyWKId() {
		return dailyWKId;
	}

	public void setDailyWKId(Integer dailyWKId) {
		this.dailyWKId = dailyWKId;
	}

	public Integer getDailyWKDetailId() {
		return dailyWKDetailId;
	}

	public void setDailyWKDetailId(Integer dailyWKDetailId) {
		this.dailyWKDetailId = dailyWKDetailId;
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

	public Integer getPartId() {
		return partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
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

	public void setWorkOrderQty(Integer workOrderQty) {
		this.workOrderQty = workOrderQty;
	}

	public Integer getWorkOrderQty() {
		return workOrderQty;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getStaff() {
		return staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

	public String getWorkOrderNo() {
		return workOrderNo;
	}

	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}

	public void setNgReasonMap(Map<Integer, Integer> ngReasonMap) {
		this.ngReasonMap = ngReasonMap;
	}

	public Double getActualProductivity() {
		return actualProductivity;
	}

	public void setActualProductivity(Double actualProductivity) {
		this.actualProductivity = actualProductivity;
	}

	public Double getStandardProductivity() {
		return standardProductivity;
	}

	public void setStandardProductivity(Double standardProductivity) {
		this.standardProductivity = standardProductivity;
	}

	public Double getStandardProduction() {
		return standardProduction;
	}

	public void setStandardProduction(Double standardProduction) {
		this.standardProduction = standardProduction;
	}

	public Double getActualProduction() {
		return actualProduction;
	}

	public void setActualProduction(Double actualProduction) {
		this.actualProduction = actualProduction;
	}

	public Double getDiff() {
		return diff;
	}

	public void setDiff(Double diff) {
		this.diff = diff;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public Integer getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Integer totalQty) {
		this.totalQty = totalQty;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public Double getUnitWeight() {
		return unitWeight;
	}

	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}

	public Double getSaleUnitPrice() {
		return saleUnitPrice;
	}

	public void setSaleUnitPrice(Double saleUnitPrice) {
		this.saleUnitPrice = saleUnitPrice;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Double getSortingTimeMin() {
		return sortingTimeMin;
	}

	public void setSortingTimeMin(Double sortingTimeMin) {
		this.sortingTimeMin = sortingTimeMin;
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

	public String getSortingTime() {
		return sortingTime;
	}

	public void setSortingTime(String sortingTime) {
		this.sortingTime = sortingTime;
	}

	public Map<Integer, Integer> getNgReasonMap() {
		return ngReasonMap;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public void setDatailRef(String datailRef) {
		this.datailRef = datailRef;
	}

	public String getDatailRef() {
		return datailRef;
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

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getShift() {
		return shift;
	}

	public String getShiftName() {
		return shiftName;
	}

	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}

	public String getWip() {
		return wip;
	}

	public void setWip(String wip) {
		this.wip = wip;
	}

	public String getWipName() {
		return wipName;
	}

	public void setWipName(String wipName) {
		this.wipName = wipName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<TDailyWKNGReason> getReasonList() {
		return reasonList;
	}

	public void setReasonList(List<TDailyWKNGReason> reasonList) {
		this.reasonList = reasonList;
	}

	public String getnOperationTime() {
		return nOperationTime;
	}

	public void setnOperationTime(String nOperationTime) {
		this.nOperationTime = nOperationTime;
	}

	public Double getStandardProductionPrice() {
		return standardProductionPrice;
	}

	public void setStandardProductionPrice(Double standardProductionPrice) {
		this.standardProductionPrice = standardProductionPrice;
	}

	public Double getActualOperatingProductivity() {
		return actualOperatingProductivity;
	}

	public void setActualOperatingProductivity(
			Double actualOperatingProductivity) {
		this.actualOperatingProductivity = actualOperatingProductivity;
	}

	public Double getActualProductionPrice() {
		return actualProductionPrice;
	}

	public void setActualProductionPrice(Double actualProductionPrice) {
		this.actualProductionPrice = actualProductionPrice;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}