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
	private String wipRework;
	private String remark;
	private List<TPendingAdjust> adjustList;
	private List<MWip> wipList;
	private List<MReason> reasonList;
	
	public String toString() {
		String result = "";
		result += "reportDate = " + reportDate + ", ";
		result += "reportDateFr = " + reportDateFr + ", ";
		result += "reportDateTo = " + reportDateTo + ", ";
		result += "customerId = " + customerId + ", ";
		result += "maxRecord = " + maxRecord + ", ";
		result += "wip = " + wip + ", ";
		result += "wipName = " + wipName + ", ";
		result += "partNo = " + partNo + ", ";
		result += "partName = " + partName + ", ";
		result += "workorderNo = " + workorderNo + ", ";
		result += "wipRework = " + wipRework + ", ";
		result += "remark = " + remark + ", ";
		result += "adjustList = " + adjustList + ", ";
		result += "wipList = " + wipList + ", ";
		result += "reasonList = " + reasonList + ", ";
		return result;
	}

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
		String[] wips = wip.split(",");
		boolean chkEmpty = true;
		for (String w : wips) {
			if ("".equals(w) == false) {
				chkEmpty = false;
				wip = w;
				break;
			}
		}
		if (chkEmpty) wip = "";
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
	public List<MReason> getReasonList() {
		return reasonList;
	}
	public void setReasonList(List<MReason> reasonList) {
		this.reasonList = reasonList;
	}
	public String getWipRework() {
		return wipRework;
	}
	public void setWipRework(String wipRework) {
		this.wipRework = wipRework;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}