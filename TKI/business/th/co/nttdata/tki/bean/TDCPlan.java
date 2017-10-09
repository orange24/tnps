package th.co.nttdata.tki.bean;

import java.util.List;

/**
 * 
 * @author NDTH
 * @since July 18, 2013
 */
public class TDCPlan extends AbstractBaseBean {

	private Integer current;
	private Integer next;
	private Integer dcPlanId;
	private String dcPlanDate;
	private String wip;
	private String wipName;
	private Integer machineId;
	private String machineNo;
	private Integer seq;
	private String shift;
	private String shiftLabel;
	private Integer customerId;
	private String customerCode;
	private String customerName;
	private Integer quantity;
	private Integer qtyDefault;
	private Integer partId;
	private String machineFrom;
	private String machineTo;
	private String partName;
	private Integer snpWip;
	private Double st;
	private Integer reasonId;
	private String reasonCode;
	private Boolean genStatus;
	private Boolean genStatusDefault;
	private String genDate;
	private String genBy;
	private String createdBy;
	private String updatedBy;
	private String dateString;
	private Integer lineNumber;
	private String remark;
	private String partNo;
	private Integer reasonType;
	private Integer materialId;
	private String materialCode;
	private String materialGroup;
	private String reasonName;
	private String dcPlanDateFrom;
	private String dcPlanDateTo;
	private String generateLot;
	private String workOrderNo;
	private String lotNo;
	private String startLotNo;
	private String endLotNo;
	private Integer recordFound;
	private String printStatus;
	private Boolean printingStatus;
	private String printDate;
	private String printerName;
	private String printerId;
	private Integer wipOrder;
	private List<TDCPlan> planList;
	private List<MWip> wipList;
	private List<MReason> reasonList;
	private List<MMachine> machineList;
	private List<MMachine> machineToList;
	private List<MCustomer> customerList;
	private Integer moldId;
	private String moldName;
	private String moldNo;
	private String cavNo;

	public TDCPlan() {
	}

	public TDCPlan(String shift, String shiftLabel) {
		this.shift = shift;
		this.shiftLabel = shiftLabel;
	}

	public Integer getDcPlanId() {
		return dcPlanId;
	}

	public void setDcPlanId(Integer dcPlanId) {
		this.dcPlanId = dcPlanId;
	}

	public String getDcPlanDate() {
		return dcPlanDate;
	}

	public void setDcPlanDate(String dcPlanDate) {
		this.dcPlanDate = dcPlanDate;
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

	public Integer getMachineId() {
		return machineId;
	}

	public void setMachineId(Integer machineId) {
		this.machineId = machineId;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getPartId() {
		return partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public Integer getSnpWip() {
		return snpWip;
	}

	public void setSnpWip(Integer snpWip) {
		this.snpWip = snpWip;
	}

	public Double getSt() {
		return st;
	}

	public void setSt(Double st) {
		this.st = st;
	}

	public Integer getReasonId() {
		return reasonId;
	}

	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	}

	public Boolean getGenStatus() {
		return genStatus;
	}

	public void setGenStatus(Boolean genStatus) {
		this.genStatus = genStatus;
	}

	public String getGenDate() {
		return genDate;
	}

	public void setGenDate(String genDate) {
		this.genDate = genDate;
	}

	public String getGenBy() {
		return genBy;
	}

	public void setGenBy(String genBy) {
		this.genBy = genBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	@Override
	public String getRemark() {
		return remark;
	}

	@Override
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public List<TDCPlan> getPlanList() {
		return planList;
	}

	public void setPlanList(List<TDCPlan> planList) {
		this.planList = planList;
	}

	public List<MWip> getWipList() {
		return wipList;
	}

	public void setWipList(List<MWip> wipList) {
		this.wipList = wipList;
	}

	public List<MReason> getReasonList() {
		return reasonList;
	}

	public void setReasonList(List<MReason> reasonList) {
		this.reasonList = reasonList;
	}

	public List<MMachine> getMachineList() {
		return machineList;
	}

	public void setMachineList(List<MMachine> machineList) {
		this.machineList = machineList;
	}

	public List<MCustomer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<MCustomer> customerList) {
		this.customerList = customerList;
	}

	public Integer getReasonType() {
		return reasonType;
	}

	public void setReasonType(Integer reasonType) {
		this.reasonType = reasonType;
	}

	public String getMachineNo() {
		return machineNo;
	}

	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getReasonName() {
		return reasonName;
	}

	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}

	public String getDcPlanDateFrom() {
		return dcPlanDateFrom;
	}

	public void setDcPlanDateFrom(String dcPlanDateFrom) {
		this.dcPlanDateFrom = dcPlanDateFrom;
	}

	public String getDcPlanDateTo() {
		return dcPlanDateTo;
	}

	public void setDcPlanDateTo(String dcPlanDateTo) {
		this.dcPlanDateTo = dcPlanDateTo;
	}

	public String getGenerateLot() {
		return generateLot;
	}

	public void setGenerateLot(String generateLot) {
		this.generateLot = generateLot;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getWorkOrderNo() {
		return workOrderNo;
	}

	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}

	public String getStartLotNo() {
		return startLotNo;
	}

	public void setStartLotNo(String startLotNo) {
		this.startLotNo = startLotNo;
	}

	public String getEndLotNo() {
		return endLotNo;
	}

	public void setEndLotNo(String endLotNo) {
		this.endLotNo = endLotNo;
	}

	public String getShiftLabel() {
		return shiftLabel;
	}

	public void setShiftLabel(String shiftLabel) {
		this.shiftLabel = shiftLabel;
	}

	public Integer getRecordFound() {
		return recordFound;
	}

	public void setRecordFound(Integer recordFound) {
		this.recordFound = recordFound;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMaterialGroup() {
		return materialGroup;
	}

	public void setMaterialGroup(String materialGroup) {
		this.materialGroup = materialGroup;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public String getMachineFrom() {
		return machineFrom;
	}

	public void setMachineFrom(String machineFrom) {
		this.machineFrom = machineFrom;
	}

	public String getMachineTo() {
		return machineTo;
	}

	public void setMachineTo(String machineTo) {
		this.machineTo = machineTo;
	}

	public List<MMachine> getMachineToList() {
		return machineToList;
	}

	public void setMachineToList(List<MMachine> machineToList) {
		this.machineToList = machineToList;
	}

	public String getPrintStatus() {
		return printStatus;
	}

	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}

	public Boolean getPrintingStatus() {
		return printingStatus;
	}

	public void setPrintingStatus(Boolean printingStatus) {
		this.printingStatus = printingStatus;
	}

	public String getPrintDate() {
		return printDate;
	}

	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}

	public Integer getWipOrder() {
		return wipOrder;
	}

	public void setWipOrder(Integer wipOrder) {
		this.wipOrder = wipOrder;
	}

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
	}

	public Integer getNext() {
		return next;
	}

	public void setNext(Integer next) {
		this.next = next;
	}

	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	public Boolean getGenStatusDefault() {
		return genStatusDefault;
	}

	public void setGenStatusDefault(Boolean genStatusDefault) {
		this.genStatusDefault = genStatusDefault;
	}

	public String getPrinterId() {
		return printerId;
	}

	public void setPrinterId(String printerId) {
		this.printerId = printerId;
	}

	public Integer getQtyDefault() {
		return qtyDefault;
	}

	public void setQtyDefault(Integer qtyDefault) {
		this.qtyDefault = qtyDefault;
	}

	public Integer getMoldId() {
		return moldId;
	}

	public void setMoldId(Integer moldId) {
		this.moldId = moldId;
	}

	public String getMoldName() {
		return moldName;
	}

	public void setMoldName(String moldName) {
		this.moldName = moldName;
	}
	
	public String getMoldNo() {
		return moldNo;
	}

	public void setMoldNo(String moldNo) {
		this.moldNo = moldNo;
	}

	public String getCavNo() {
		return cavNo;
	}

	public void setCavNo(String cavNo) {
		this.cavNo = cavNo;
	}

	@Override
	public String toString(){
		String result = "";
		result += "moldId = "+this.moldId+", moldName = "+this.moldName+" , moldNo = "+this.moldNo;
		
		return result;		
	}
	
}