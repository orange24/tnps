package th.co.nttdata.tki.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TDailyWK extends AbstractBaseBean {

	private Date reportDate;
	private Date reportDateFr;
	private Date reportDateTo;
	private Integer customerId;
	private Integer dailyWKId;
	private Integer machineId;
	private Integer maxRecord;
	private Integer partId;
	private Integer reportType;
	private String customerCode;
	private String partName;
	private String partNo;
	private String reportName;
	private String shift;
	private String shiftName;
	private String wip;
	private String wipName;
	private String workOrderNo;
	private String currency;
	private String[] shifts;
	private List<TDailyWK> dailyWKList;
	private List<TDailyWKDetail> dailyWKDetailList;
	private Map<String, TDailyWKNGReason> reasonMap;

	// MRDC_R22
	private String machineName;
	private String materialType;

	// MRDC_R02
	private Integer materialId;
	private String category;
	private String materialName;
	private List<String> categoryList;

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public void setReportDateFr(Date reportDateFr) {
		this.reportDateFr = reportDateFr;
	}

	public Date getReportDateFr() {
		return reportDateFr;
	}

	public void setReportDateTo(Date reportDateTo) {
		this.reportDateTo = reportDateTo;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Date getReportDateTo() {
		return reportDateTo;
	}

	public Integer getDailyWKId() {
		return dailyWKId;
	}

	public void setDailyWKId(Integer dailyWKId) {
		this.dailyWKId = dailyWKId;
	}

	public Integer getMachineId() {
		return machineId;
	}

	public void setMachineId(Integer machineId) {
		this.machineId = machineId;
	}

	public Integer getMaxRecord() {
		return maxRecord;
	}

	public void setMaxRecord(Integer maxRecord) {
		this.maxRecord = maxRecord;
	}

	public Integer getPartId() {
		return partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportName() {
		return reportName;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getShift() {
		return shift;
	}

	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}

	public String getShiftName() {
		return shiftName;
	}

	public String getWip() {
		return wip;
	}

	public void setWip(String wip) {
		this.wip = wip;
	}

	public void setWipName(String wipName) {
		this.wipName = wipName;
	}

	public String getWipName() {
		return wipName;
	}

	public String getWorkOrderNo() {
		return workOrderNo;
	}

	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}

	public void setShifts(String[] shifts) {
		this.shifts = shifts;
	}

	public String[] getShifts() {
		return shifts;
	}

	public void setDailyWKList(List<TDailyWK> dailyWKList) {
		this.dailyWKList = dailyWKList;
	}

	public List<TDailyWK> getDailyWKList() {
		return dailyWKList;
	}

	public void setDailyWKDetailList(List<TDailyWKDetail> dailyWKDetailList) {
		this.dailyWKDetailList = dailyWKDetailList;
	}

	public void setReasonMap(Map<String, TDailyWKNGReason> reasonMap) {
		this.reasonMap = reasonMap;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
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

	public List<String> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<String> categoryList) {
		this.categoryList = categoryList;
	}

	public Map<String, TDailyWKNGReason> getReasonMap() {
		return reasonMap;
	}

	public List<TDailyWKDetail> getDailyWKDetailList() {
		return dailyWKDetailList;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}