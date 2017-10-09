package th.co.nttdata.tki.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TDailyMC extends AbstractBaseBean {

	private Date reportDate;
	private Date reportDateFr;
	private Date reportDateTo;
	private Integer customerId;
	private Integer dailyMCId;
	private Integer machineId;
	private Integer moldId;
	private Integer partId;
	private Integer cavUsed;
	private Integer cavDefault;
	private Integer reportType;
	private String cavNo;
	private String customerCode;
	private String customerName;
	private String dayWorker;
	private String nightWorker;
	private String[] lotNo;
	private String machineNo;
	private String moldNo;
	private String moldName;
	private String partName;
	private String partNo;
	private String reportName;
	private String wip;
	private String wipName;
	private List<TDailyMC> dailyMCList;
	private List<TDailyMCDetail> details;
	private String machineName;
	
	//MRDC_S09
	private Integer maxRecord;
	private Integer sorting;
	private String 	sortingName;
	
	//MRDC_S17
	private String actualType;
	private Map<String,TDailyMCNGReason> reasonMap;

	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
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
	public Date getReportDateTo() {
		return reportDateTo;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getDailyMCId() {
		return dailyMCId;
	}
	public void setDailyMCId(Integer dailyMCId) {
		this.dailyMCId = dailyMCId;
	}
	public Integer getMachineId() {
		return machineId;
	}
	public void setMachineId(Integer machineId) {
		this.machineId = machineId;
	}
	public Integer getMoldId() {
		return moldId;
	}
	public void setMoldId(Integer moldId) {
		this.moldId = moldId;
	}
	public Integer getPartId() {
		return partId;
	}
	public void setPartId(Integer partId) {
		this.partId = partId;
	}
	public Integer getCavUsed() {
		return cavUsed;
	}
	public void setCavUsed(Integer cavUsed) {
		this.cavUsed = cavUsed;
	}
	public void setCavDefault(Integer cavDefault) {
		this.cavDefault = cavDefault;
	}
	public Integer getCavDefault() {
		return cavDefault;
	}
	public Integer getReportType() {
		return reportType;
	}
	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}
	public String getCavNo() {
		return cavNo;
	}
	public void setCavNo(String cavNo) {
		this.cavNo = cavNo;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public String getDayWorker() {
		return dayWorker;
	}
	public void setDayWorker(String dayWorker) {
		this.dayWorker = dayWorker;
	}
	public String getNightWorker() {
		return nightWorker;
	}
	public void setNightWorker(String nightWorker) {
		this.nightWorker = nightWorker;
	}
	public void setLotNo(String[] lotNo) {
		this.lotNo = lotNo;
	}
	public String[] getLotNo() {
		return lotNo;
	}
	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}
	public void setMoldNo(String moldNo) {
		this.moldNo = moldNo;
	}
	public String getMoldNo() {
		return moldNo;
	}
	public String getMoldName() {
		return moldName;
	}
	public void setMoldName(String moldName) {
		this.moldName = moldName;
	}
	public String getMachineNo() {
		return machineNo;
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
	public void setDailyMCList(List<TDailyMC> dailyMCList) {
		this.dailyMCList = dailyMCList;
	}
	public List<TDailyMC> getDailyMCList() {
		return dailyMCList;
	}
	public List<TDailyMCDetail> getDetails() {
		return details;
	}
	public void setDetails(List<TDailyMCDetail> details) {
		this.details = details;
	}
	public Integer getMaxRecord() {
		return maxRecord;
	}
	public void setMaxRecord(Integer maxRecord) {
		this.maxRecord = maxRecord;
	}
	public Integer getSorting() {
		return sorting;
	}
	public void setSorting(Integer sorting) {
		this.sorting = sorting;
	}
	public String getSortingName() {
		return sortingName;
	}
	public void setSortingName(String sortingName) {
		this.sortingName = sortingName;
	}
	public Map<String, TDailyMCNGReason> getReasonMap() {
		return reasonMap;
	}
	public void setReasonMap(Map<String, TDailyMCNGReason> reasonMap) {
		this.reasonMap = reasonMap;
	}
	public String getActualType() {
		return actualType;
	}
	public void setActualType(String actualType) {
		this.actualType = actualType;
	}
}