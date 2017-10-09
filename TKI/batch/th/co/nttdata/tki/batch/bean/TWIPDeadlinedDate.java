package th.co.nttdata.tki.batch.bean;

import java.util.Date;

public class TWIPDeadlinedDate {

	private Date 	reportDate;
	private Integer deadlineQty;
	private Integer colorId;
	private Integer partId;
	private Integer shiftId;
	private Integer wipOrder;
	private String 		wip;
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public Integer getDeadlineQty() {
		return deadlineQty;
	}
	public void setDeadlineQty(Integer deadlineQty) {
		this.deadlineQty = deadlineQty;
	}
	public Integer getColorId() {
		return colorId;
	}
	public void setColorId(Integer colorId) {
		this.colorId = colorId;
	}
	public Integer getPartId() {
		return partId;
	}
	public void setPartId(Integer partId) {
		this.partId = partId;
	}
	public Integer getShiftId() {
		return shiftId;
	}
	public void setShiftId(Integer shiftId) {
		this.shiftId = shiftId;
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
}
