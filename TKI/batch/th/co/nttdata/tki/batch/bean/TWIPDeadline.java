package th.co.nttdata.tki.batch.bean;

import java.math.BigDecimal;
import java.util.List;

public class TWIPDeadline {
	
	private BigDecimal 	capacity;
	private Integer		isWIP;
	private Integer 	partId;
	private Integer		stock;
	private Integer 	wipOrder;
	private String 		wip;
	private List<TWIPDeadline> wipDeadlineList;
	private List<TWIPDeadlinedDate> wipDeadlinedDateList;
	
	public BigDecimal getCapacity() {
		return capacity;
	}
	public void setCapacity(BigDecimal capacity) {
		this.capacity = capacity;
	}
	public Integer getIsWIP() {
		return isWIP;
	}
	public void setIsWIP(Integer isWIP) {
		this.isWIP = isWIP;
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
	public String getWip() {
		return wip;
	}
	public void setWip(String wip) {
		this.wip = wip;
	}
	public List<TWIPDeadline> getWipDeadlineList() {
		return wipDeadlineList;
	}
	public void setWipDeadlineList(List<TWIPDeadline> wipDeadlineList) {
		this.wipDeadlineList = wipDeadlineList;
	}
	public List<TWIPDeadlinedDate> getWipDeadlinedDateList() {
		return wipDeadlinedDateList;
	}
	public void setWipDeadlinedDateList(List<TWIPDeadlinedDate> wipDeadlinedDateList) {
		this.wipDeadlinedDateList = wipDeadlinedDateList;
	}	
}
