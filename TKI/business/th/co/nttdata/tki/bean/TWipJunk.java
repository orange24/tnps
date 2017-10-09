package th.co.nttdata.tki.bean;

import java.util.Date;
import java.util.List;

public class TWipJunk extends AbstractBaseBean {
	
	//WIP_S05 WIP Junk Adjustment
	private Date 	lastReportDate;
	private Double 	amount;
	private Double 	amountFr;
	private Double 	amountTo;
	private Integer customerId;
	private Integer	isEnable;
	private Integer junkId;
	private Integer junkQty;
	private Integer junkQtyMin;
	private Integer junkQtyMax;
	private Integer partId;
	private Integer stockQty;
	private Integer stockQtyMin;
	private Integer stockQtyMax;
	private String 	isChecked;
	private String 	customerCode;
	private String 	lotNo;
	private String 	monthYear;
	private String 	partName;
	private String 	partNo;
	private String 	wip;
	private String 	workOrderNo;
	private List<TWipJunk> junkList;

	public Date getLastReportDate() {
		return lastReportDate;
	}
	public void setLastReportDate(Date lastReportDate) {
		this.lastReportDate = lastReportDate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getAmountFr() {
		return amountFr;
	}
	public void setAmountFr(Double amountFr) {
		this.amountFr = amountFr;
	}
	public Double getAmountTo() {
		return amountTo;
	}
	public void setAmountTo(Double amountTo) {
		this.amountTo = amountTo;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}
	public Integer getJunkId() {
		return junkId;
	}
	public void setJunkId(Integer junkId) {
		this.junkId = junkId;
	}
	public Integer getJunkQty() {
		return junkQty;
	}
	public void setJunkQty(Integer junkQty) {
		this.junkQty = junkQty;
	}
	public Integer getJunkQtyMin() {
		return junkQtyMin;
	}
	public void setJunkQtyMin(Integer junkQtyMin) {
		this.junkQtyMin = junkQtyMin;
	}
	public Integer getJunkQtyMax() {
		return junkQtyMax;
	}
	public void setJunkQtyMax(Integer junkQtyMax) {
		this.junkQtyMax = junkQtyMax;
	}
	public Integer getPartId() {
		return partId;
	}
	public void setPartId(Integer partId) {
		this.partId = partId;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
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
	public List<TWipJunk> getJunkList() {
		return junkList;
	}
	public void setJunkList(List<TWipJunk> junkList) {
		this.junkList = junkList;
	}
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
	public String getMonthYear() {
		return monthYear;
	}
	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}
	public Integer getStockQtyMin() {
		return stockQtyMin;
	}
	public void setStockQtyMin(Integer stockQtyMin) {
		this.stockQtyMin = stockQtyMin;
	}
	public Integer getStockQtyMax() {
		return stockQtyMax;
	}
	public void setStockQtyMax(Integer stockQtyMax) {
		this.stockQtyMax = stockQtyMax;
	}
	public Integer getStockQty() {
		return stockQty;
	}
	public void setStockQty(Integer stockQty) {
		this.stockQty = stockQty;
	}
	
}
