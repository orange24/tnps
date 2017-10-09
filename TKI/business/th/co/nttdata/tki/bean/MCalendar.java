package th.co.nttdata.tki.bean;

import java.util.Date;

public class MCalendar extends AbstractBaseBean {

	private Boolean isWorkDay;
	private Date calendarDate;
	private Date calendarDateFr;
	private Date calendarDateTo;
	private Integer day;
	private String description;

	public Boolean getIsWorkDay() {
		return isWorkDay;
	}
	public void setIsWorkDay(Boolean isWorkDay) {
		this.isWorkDay = isWorkDay;
	}
	public Date getCalendarDate() {
		return calendarDate;
	}
	public void setCalendarDate(Date calendarDate) {
		this.calendarDate = calendarDate;
	}
	public void setCalendarDateFr(Date calendarDateFr) {
		this.calendarDateFr = calendarDateFr;
	}
	public Date getCalendarDateFr() {
		return calendarDateFr;
	}
	public void setCalendarDateTo(Date calendarDateTo) {
		this.calendarDateTo = calendarDateTo;
	}
	public Date getCalendarDateTo() {
		return calendarDateTo;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}