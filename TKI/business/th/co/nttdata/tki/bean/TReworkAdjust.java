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
	private Integer status;
	private Integer reworkQty;
	private Integer ok;
	private Integer ng;
	private List<TReworkAdjust> adjustList;

	public Integer getPdReworkId() {
		return pdReworkId;
	}
	public void setPdReworkId(Integer pdReworkId) {
		this.pdReworkId = pdReworkId;
	}
	public String getWipFr() {
		return wipFr;
	}
	public void setWipFr(String wipFr) {
		this.wipFr = wipFr;
	}
	public String getWipTo() {
		return wipTo;
	}
	public void setWipTo(String wipTo) {
		this.wipTo = wipTo;
	}
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
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getReworkQty() {
		return reworkQty;
	}
	public void setReworkQty(Integer reworkQty) {
		this.reworkQty = reworkQty;
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
	public List<TReworkAdjust> getAdjustList() {
		return adjustList;
	}
	public void setAdjustList(List<TReworkAdjust> adjustList) {
		this.adjustList = adjustList;
	}
}