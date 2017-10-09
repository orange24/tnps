package th.co.nttdata.tki.batch.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class MLeadtime {
	private BigDecimal  stResult4;
	private BigDecimal  stResult5;
	private BigDecimal  stResult6;
	private Integer 	partId;
	private Date    	stFromDate;
	private Date    	stFromDate4;
	private Date    	stFromDate5;
	private Date    	stFromDate6;
	private Date    	stToDate;
	private Date    	stToDate4;
	private Date    	stToDate5;
	private Date    	stToDate6;
	private Integer 	leadTimeId;
	private String  	wip;
	private List<DailyDC> dailyList;
	private List<MLeadtime> leadtimeList;

	public BigDecimal getStResult4() {
		return stResult4;
	}
	public void setStResult4(BigDecimal stResult4) {
		this.stResult4 = stResult4;
	}
	public BigDecimal getStResult5() {
		return stResult5;
	}
	public void setStResult5(BigDecimal stResult5) {
		this.stResult5 = stResult5;
	}
	public BigDecimal getStResult6() {
		return stResult6;
	}
	public void setStResult6(BigDecimal stResult6) {
		this.stResult6 = stResult6;
	}
	public Integer getPartId() {
		return partId;
	}
	public void setPartId(Integer partId) {
		this.partId = partId;
	}
	public Date getStFromDate() {
		return stFromDate;
	}
	public void setStFromDate(Date stFromDate) {
		this.stFromDate = stFromDate;
	}
	public Date getStFromDate4() {
		return stFromDate4;
	}
	public void setStFromDate4(Date stFromDate4) {
		this.stFromDate4 = stFromDate4;
	}
	public Date getStFromDate5() {
		return stFromDate5;
	}
	public void setStFromDate5(Date stFromDate5) {
		this.stFromDate5 = stFromDate5;
	}
	public Date getStFromDate6() {
		return stFromDate6;
	}
	public void setStFromDate6(Date stFromDate6) {
		this.stFromDate6 = stFromDate6;
	}
	public Date getStToDate() {
		return stToDate;
	}
	public void setStToDate(Date stToDate) {
		this.stToDate = stToDate;
	}
	public Date getStToDate4() {
		return stToDate4;
	}
	public void setStToDate4(Date stToDate4) {
		this.stToDate4 = stToDate4;
	}
	public Date getStToDate5() {
		return stToDate5;
	}
	public void setStToDate5(Date stToDate5) {
		this.stToDate5 = stToDate5;
	}
	public Date getStToDate6() {
		return stToDate6;
	}
	public void setStToDate6(Date stToDate6) {
		this.stToDate6 = stToDate6;
	}
	public Integer getLeadTimeId() {
		return leadTimeId;
	}
	public void setLeadTimeId(Integer leadTimeId) {
		this.leadTimeId = leadTimeId;
	}
	public String getWip() {
		return wip;
	}
	public void setWip(String wip) {
		this.wip = wip;
	}
	public List<DailyDC> getDailyList() {
		return dailyList;
	}
	public void setDailyList(List<DailyDC> dailyList) {
		this.dailyList = dailyList;
	}
	public List<MLeadtime> getLeadtimeList() {
		return leadtimeList;
	}
	public void setLeadtimeList(List<MLeadtime> leadtimeList) {
		this.leadtimeList = leadtimeList;
	}
}
