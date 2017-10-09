package th.co.nttdata.tki.bean;

import java.util.Date;

public class TWIPDeadlineDate extends AbstractBaseBean {

	private Date reportDate;
	private Integer deadlineQty;
	private String color;
	
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
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}