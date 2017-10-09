package th.co.nttdata.tki.bean;

import java.util.Date;
import java.util.List;

public class TFGJunk extends AbstractBaseBean {
	//WIP_S05 WIP Junk Adjustment
	private Date 	lastReportDate;
	private Double 	amount;
	private Double 	amountFr;
	private Double 	amountTo;
	private Integer customerId;
	private Integer fgId;
	private Integer	isEnable;
	private Integer junkId;
	private Integer junkQty;
	private Integer junkQtyMin;
	private Integer junkQtyMax;
	private Integer stockQty;
	private Integer stockQtyMin;
	private Integer stockQtyMax;
	private String 	customerCode;
	private String 	fgName;
	private String 	fgNo;
	private String 	isChecked;
	private String 	lotNo;
	private String 	monthYear;
	private String 	workOrderNo;
	private List<TFGJunk> junkList;

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
	public Integer getFgId() {
		return fgId;
	}
	public void setFgId(Integer fgId) {
		this.fgId = fgId;
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
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getFgName() {
		return fgName;
	}
	public void setFgName(String fgName) {
		this.fgName = fgName;
	}
	public String getFgNo() {
		return fgNo;
	}
	public void setFgNo(String fgNo) {
		this.fgNo = fgNo;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	public String getWorkOrderNo() {
		return workOrderNo;
	}
	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}
	public List<TFGJunk> getJunkList() {
		return junkList;
	}
	public void setJunkList(List<TFGJunk> junkList) {
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
