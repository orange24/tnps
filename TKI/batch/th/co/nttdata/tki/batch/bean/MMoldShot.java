package th.co.nttdata.tki.batch.bean;

import java.util.Date;

public class MMoldShot {
	private Date reportDate;
	private Integer cavUsed;
	private Integer moldId;
	private Integer qty;
	private Integer reportTime;
	private Integer totalDCShot;
	private Integer totalFGSold;
	private String moldNo;

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Integer getCavUsed() {
		return cavUsed;
	}

	public void setCavUsed(Integer cavUsed) {
		this.cavUsed = cavUsed;
	}

	public Integer getMoldId() {
		return moldId;
	}

	public void setMoldId(Integer moldId) {
		this.moldId = moldId;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getReportTime() {
		return reportTime;
	}

	public void setReportTime(Integer reportTime) {
		this.reportTime = reportTime;
	}

	public void setTotalDCShot(Integer totalDCShot) {
		this.totalDCShot = totalDCShot;
	}

	public Integer getTotalDCShot() {
		return totalDCShot;
	}

	public void setTotalFGSold(Integer totalFGSold) {
		this.totalFGSold = totalFGSold;
	}

	public Integer getTotalFGSold() {
		return totalFGSold;
	}

	public void setMoldNo(String moldNo) {
		this.moldNo = moldNo;
	}

	public String getMoldNo() {
		return moldNo;
	}
}
