package th.co.nttdata.tki.bean;

import java.util.Date;
import java.util.List;

public class TPending extends AbstractBaseBean {

	private Date reportDate;
	private Date reportDateFr;
	private Date reportDateTo;
	private Integer customerId;
	private Integer maxRecord;
	private String wip;
	private String wipName;
	private String partNo;
	private String partName;
	private String workorderNo;
	private List<TPendingAdjust> adjustList;
	private List<MWip> wipList;

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public Date getReportDate() {
		return reportDate;
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
	public Integer getMaxRecord() {
		return maxRecord;
	}
	public void setMaxRecord(Integer maxRecord) {
		this.maxRecord = maxRecord;
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
	public String getWorkorderNo() {
		return workorderNo;
	}
	public void setWorkorderNo(String workorderNo) {
		this.workorderNo = workorderNo;
	}
	public List<TPendingAdjust> getAdjustList() {
		return adjustList;
	}
	public void setAdjustList(List<TPendingAdjust> adjustList) {
		this.adjustList = adjustList;
	}
	public List<MWip> getWipList() {
		return wipList;
	}
	public void setWipList(List<MWip> wipList) {
		this.wipList = wipList;
	}
}