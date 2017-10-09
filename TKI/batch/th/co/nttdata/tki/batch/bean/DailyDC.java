package th.co.nttdata.tki.batch.bean;

public class DailyDC {
	private Integer dailymcId;
	private Integer qty;
	private Integer reportTime;
	private Integer stopMin;
	
	public Integer getDailymcId() {
		return dailymcId;
	}
	public void setDailymcId(Integer dailymcId) {
		this.dailymcId = dailymcId;
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
	public Integer getStopMin() {
		return stopMin;
	}
	public void setStopMin(Integer stopMin) {
		this.stopMin = stopMin;
	}
}
