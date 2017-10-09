package th.co.nttdata.tki.bean;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TDailyMCWK extends AbstractBaseBean {

	private Date reportDate;
	private Date reportDateFr;
	private Date reportDateTo;
	private Integer customerId;
	private Integer machineId;
	private Integer partId;
	private Integer dailyMCWKId;
	private Integer reportType;
	private Integer maxRecord;
	private String customerCode;
	private String machineNo;
	private String machineName;
	private String partName;
	private String partNo;
	private String reportName;
	private String shift;
	private String shiftName;
	private String staff;
	private String wip;
	private String wipName;
	private String[] shifts;
	private List<TDailyMCWK> dailyMCWKList;
	private List<TDailyMCWKDetail> detailList;
	private List<Map<Integer,Integer>> stopMCList = new LinkedList<Map<Integer,Integer>>();
	private Map<String, TDailyMCWKNGReason> reasonMap;

	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public Date getReportDateFr() {
		return reportDateFr;
	}
	public void setReportDateFr(Date reportDateFr) {
		this.reportDateFr = reportDateFr;
	}
	public Date getReportDateTo() {
		return reportDateTo;
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
	public Integer getMachineId() {
		return machineId;
	}
	public void setMachineId(Integer machineId) {
		this.machineId = machineId;
	}
	public void setPartId(Integer partId) {
		this.partId = partId;
	}
	public Integer getPartId() {
		return partId;
	}
	public Integer getDailyMCWKId() {
		return dailyMCWKId;
	}
	public void setDailyMCWKId(Integer dailyMCWKId) {
		this.dailyMCWKId = dailyMCWKId;
	}
	public Integer getReportType() {
		return reportType;
	}
	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}
	public Integer getMaxRecord() {
		return maxRecord;
	}
	public void setMaxRecord(Integer maxRecord) {
		this.maxRecord = maxRecord;
	}
	public String getMachineNo() {
		return machineNo;
	}
	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
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
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReportName() {
		return reportName;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	public String getShiftName() {
		return shiftName;
	}
	public String getStaff() {
		return staff;
	}
	public void setStaff(String staff) {
		this.staff = staff;
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
	public String[] getShifts() {
		return shifts;
	}
	public void setShifts(String[] shifts) {
		this.shifts = shifts;
	}
	public void setDailyMCWKList(List<TDailyMCWK> dailyMCWKList) {
		this.dailyMCWKList = dailyMCWKList;
	}
	public List<TDailyMCWK> getDailyMCWKList() {
		return dailyMCWKList;
	}
	public void setDetailList(List<TDailyMCWKDetail> detailList) {
		this.detailList = detailList;
	}
	public List<TDailyMCWKDetail> getDetailList() {
		return detailList;
	}
	public void setStopMCList(List<Map<Integer,Integer>> stopMCList) {
		this.stopMCList = stopMCList;
	}
	public List<Map<Integer,Integer>> getStopMCList() {
		return stopMCList;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setReasonMap(Map<String, TDailyMCWKNGReason> reasonMap) {
		this.reasonMap = reasonMap;
	}
	public Map<String, TDailyMCWKNGReason> getReasonMap() {
		return reasonMap;
	}
}