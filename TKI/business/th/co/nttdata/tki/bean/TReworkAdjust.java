package th.co.nttdata.tki.bean;

import java.util.Date;
import java.util.List;

public class TReworkAdjust extends AbstractBaseBean {
	private Integer pdReworkId;

	private String wipFr;

	private String wipTo;

	private Date reportDate;

	private Date reportDateFr;

	private Date reportDateTo;

	private Integer customerId;

	private String customerCode;

	private String partNo;

	private String partName;

	private String wipRework;

	private String ngReason;

	private String reworkRemark;

	private Integer status;

	private Integer reworkQty;

	private Integer ok;

	private Integer ng;

	private List<TReworkAdjust> adjustList;

	public String getWipRework() {
		return this.wipRework;
	}

	public void setWipRework(String wipRework) {
		this.wipRework = wipRework;
	}

	public String getNgReason() {
		return this.ngReason;
	}

	public void setNgReason(String ngReason) {
		this.ngReason = ngReason;
	}

	public String getReworkRemark() {
		return this.reworkRemark;
	}

	public void setReworkRemark(String reworkRemark) {
		this.reworkRemark = reworkRemark;
	}

	public Integer getPdReworkId() {
		return this.pdReworkId;
	}

	public void setPdReworkId(Integer pdReworkId) {
		this.pdReworkId = pdReworkId;
	}

	public String getWipFr() {
		return this.wipFr;
	}

	public void setWipFr(String wipFr) {
		this.wipFr = wipFr;
	}

	public String getWipTo() {
		return this.wipTo;
	}

	public void setWipTo(String wipTo) {
		this.wipTo = wipTo;
	}

	public Date getReportDate() {
		return this.reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Date getReportDateFr() {
		return this.reportDateFr;
	}

	public void setReportDateFr(Date reportDateFr) {
		this.reportDateFr = reportDateFr;
	}

	public Date getReportDateTo() {
		return this.reportDateTo;
	}

	public void setReportDateTo(Date reportDateTo) {
		this.reportDateTo = reportDateTo;
	}

	public Integer getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerCode() {
		return this.customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getPartNo() {
		return this.partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getPartName() {
		return this.partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getReworkQty() {
		return this.reworkQty;
	}

	public void setReworkQty(Integer reworkQty) {
		this.reworkQty = reworkQty;
	}

	public Integer getOk() {
		return this.ok;
	}

	public void setOk(Integer ok) {
		this.ok = ok;
	}

	public Integer getNg() {
		return this.ng;
	}

	public void setNg(Integer ng) {
		this.ng = ng;
	}

	public List<TReworkAdjust> getAdjustList() {
		return this.adjustList;
	}

	public void setAdjustList(List<TReworkAdjust> adjustList) {
		this.adjustList = adjustList;
	}
}
