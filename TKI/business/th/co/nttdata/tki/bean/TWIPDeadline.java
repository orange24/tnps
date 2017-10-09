package th.co.nttdata.tki.bean;

import java.util.List;

public class TWIPDeadline extends AbstractBaseBean {

	private Integer capacity;
	private Integer customerId;
	private Integer isWip;
	private Integer partId;
	private Integer stock;
	private Integer wipOrder;
	private String customerCode;
	private String monthName;
	private String partNo;
	private String partName;
	private String wip;
	private String wipName;
	private List<TWIPDeadline> deadlinePartList;
	private List<TWIPDeadline> deadlineWIPList;
	private List<TWIPDeadlineDate> deadlineDateList;
	
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getIsWip() {
		return isWip;
	}
	public void setIsWip(Integer isWip) {
		this.isWip = isWip;
	}
	public Integer getPartId() {
		return partId;
	}
	public void setPartId(Integer partId) {
		this.partId = partId;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getWipOrder() {
		return wipOrder;
	}
	public void setWipOrder(Integer wipOrder) {
		this.wipOrder = wipOrder;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getMonthName() {
		return monthName;
	}
	public void setMonthName(String monthName) {
		this.monthName = monthName;
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
	public List<TWIPDeadline> getDeadlinePartList() {
		return deadlinePartList;
	}
	public void setDeadlinePartList(List<TWIPDeadline> deadlinePartList) {
		this.deadlinePartList = deadlinePartList;
	}
	public List<TWIPDeadline> getDeadlineWIPList() {
		return deadlineWIPList;
	}
	public void setDeadlineWIPList(List<TWIPDeadline> deadlineWIPList) {
		this.deadlineWIPList = deadlineWIPList;
	}
	public List<TWIPDeadlineDate> getDeadlineDateList() {
		return deadlineDateList;
	}
	public void setDeadlineDateList(List<TWIPDeadlineDate> deadlineDateList) {
		this.deadlineDateList = deadlineDateList;
	}
}