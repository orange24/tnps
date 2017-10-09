package th.co.nttdata.tki.batch.bean;

import java.util.Date;
import java.util.List;


public class TDailySummary {
	private Date reportDate;	
	private Integer ng;
	private Integer ok;
	private Integer partId;
	private Integer pd;
	private Integer qty;
	private Integer reportType;
	private String shift;
	private String wip;
	private List<TDailySummary> dailySumList;
	
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public Integer getNg() {
		return ng;
	}
	public void setNg(Integer ng) {
		this.ng = ng;
	}
	public Integer getOk() {
		return ok;
	}
	public void setOk(Integer ok) {
		this.ok = ok;
	}
	public Integer getPartId() {
		return partId;
	}
	public void setPartId(Integer partId) {
		this.partId = partId;
	}
	public Integer getPd() {
		return pd;
	}
	public void setPd(Integer pd) {
		this.pd = pd;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public Integer getReportType() {
		return reportType;
	}
	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getWip() {
		return wip;
	}
	public void setWip(String wip) {
		this.wip = wip;
	}
	public List<TDailySummary> getDailySumList() {
		return dailySumList;
	}
	public void setDailySumList(List<TDailySummary> dailySumList) {
		this.dailySumList = dailySumList;
	}	
}
