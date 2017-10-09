package th.co.nttdata.tki.bean;

import java.util.Date;
import java.util.List;

public class MWorkOrder extends AbstractBaseBean {

	private Date workOrderDate;
	private Date workOrderDateFr;
	private Date workOrderDateTo;
	private Integer customerId;
	private Integer fgId;
	private Integer lotQty;
	private Integer moldId;
	private Integer partId;
	private Integer reportType;
	private Integer workOrderId;
	private Integer workOrderQty;
	private String customerCode;
	private String endLot;
	private String fgNo;
	private String fgName;
	private String fgType;
	private String linkDB;
	private String lotNo;
	private String lotSeqNo;
	private String lotSeqQty;
	private String partNo;
	private String partName;
	private String startLot;
	private String wip;
	private String workOrderNo;
	private List<MWorkOrder> mWorkOrderLst;
	private Integer tagId;
	private String moldNo;

	public Date getWorkOrderDate() {
		return workOrderDate;
	}

	public void setWorkOrderDate(Date workOrderDate) {
		this.workOrderDate = workOrderDate;
	}

	public Date getWorkOrderDateFr() {
		return workOrderDateFr;
	}

	public void setWorkOrderDateFr(Date workOrderDateFr) {
		this.workOrderDateFr = workOrderDateFr;
	}

	public Date getWorkOrderDateTo() {
		return workOrderDateTo;
	}

	public void setWorkOrderDateTo(Date workOrderDateTo) {
		this.workOrderDateTo = workOrderDateTo;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getFgId() {
		return fgId;
	}

	public void setFgId(Integer fgId) {
		this.fgId = fgId;
	}

	public Integer getLotQty() {
		return lotQty;
	}

	public void setLotQty(Integer lotQty) {
		this.lotQty = lotQty;
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

	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	public Integer getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(Integer workOrderId) {
		this.workOrderId = workOrderId;
	}

	public Integer getWorkOrderQty() {
		return workOrderQty;
	}

	public void setWorkOrderQty(Integer workOrderQty) {
		this.workOrderQty = workOrderQty;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getEndLot() {
		return endLot;
	}

	public void setEndLot(String endLot) {
		this.endLot = endLot;
	}

	public String getFgNo() {
		return fgNo;
	}

	public void setFgNo(String fgNo) {
		this.fgNo = fgNo;
	}

	public String getFgName() {
		return fgName;
	}

	public void setFgName(String fgName) {
		this.fgName = fgName;
	}

	public String getFgType() {
		return fgType;
	}

	public void setFgType(String fgType) {
		this.fgType = fgType;
	}

	public String getLinkDB() {
		return linkDB;
	}

	public void setLinkDB(String linkDB) {
		this.linkDB = linkDB;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public String getLotSeqNo() {
		return lotSeqNo;
	}

	public void setLotSeqNo(String lotSeqNo) {
		this.lotSeqNo = lotSeqNo;
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

	public String getStartLot() {
		return startLot;
	}

	public void setStartLot(String startLot) {
		this.startLot = startLot;
	}

	public String getWip() {
		return wip;
	}

	public void setWip(String wip) {
		this.wip = wip;
	}

	public String getWorkOrderNo() {
		return workOrderNo;
	}

	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}

	public List<MWorkOrder> getmWorkOrderLst() {
		return mWorkOrderLst;
	}

	public void setmWorkOrderLst(List<MWorkOrder> mWorkOrderLst) {
		this.mWorkOrderLst = mWorkOrderLst;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public String getLotSeqQty() {
		return lotSeqQty;
	}

	public void setLotSeqQty(String lotSeqQty) {
		this.lotSeqQty = lotSeqQty;
	}

	public String getMoldNo() {
		return moldNo;
	}

	public void setMoldNo(String moldNo) {
		this.moldNo = moldNo;
	}
}