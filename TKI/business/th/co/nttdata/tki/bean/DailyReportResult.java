package th.co.nttdata.tki.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class DailyReportResult extends AbstractBaseBean {
	private String id;
	private String wipName;
	private String machineNo;
	private String machineName;
	private Date reportDate;
	private String shiftType; 
	private Integer reportType;
	private String workOrderNumber;
	private String lotNo;
	private String customerNo;
	private String customerName;
	private String partNo;
	private String partName;
	private String remark;
	private String moldNo;
	private Integer usedCav;
	private Integer defaultCav;
	private Integer ok;
	private Integer ng;
	private Integer pending;
	private Integer qty;
	private List<Map<String, Object>> reasonList;
	private Integer reasonId;
	private Integer ngReason;
	
	public String getWipName() {
		return wipName;
	}
	public void setWipName(String wipName) {
		this.wipName = wipName;
	}
	public String getMachineNo() {
		return machineNo;
	}
	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}
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
	public String getShiftType() {
		return shiftType;
	}
	public void setShiftType(String shiftType) {
		this.shiftType = shiftType;
	}
	public Integer getReportType() {
		return reportType;
	}
	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}
	public String getWorkOrderNumber() {
		return workOrderNumber;
	}
	public void setWorkOrderNumber(String workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMoldNo() {
		return moldNo;
	}
	public void setMoldNo(String moldNo) {
		this.moldNo = moldNo;
	}
	public Integer getUsedCav() {
		return usedCav;
	}
	public void setUsedCav(Integer usedCav) {
		this.usedCav = usedCav;
	}
	public Integer getDefaultCav() {
		return defaultCav;
	}
	public void setDefaultCav(Integer defaultCav) {
		this.defaultCav = defaultCav;
	}
	public Integer getOk() {
		return ok;
	}
	public void setOk(Integer ok) {
		this.ok = ok;
	}
	public Integer getNg() {
		return ng;
	}
	public void setNg(Integer ng) {
		this.ng = ng;
	}
	public Integer getPending() {
		return pending;
	}
	public void setPending(Integer pending) {
		this.pending = pending;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public List<Map<String, Object>> getReasonList() {
		return reasonList;
	}
	public void setReasonList(List<Map<String, Object>> reasonList) {
		this.reasonList = reasonList;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	}
	public Integer getReasonId() {
		return reasonId;
	}
	public void setNgReason(Integer ngReason) {
		this.ngReason = ngReason;
	}
	public Integer getNgReason() {
		return ngReason;
	}
}
