package th.co.nttdata.tki.bean;

public class TDailyMCWKStopMC {

	private Integer dailyMCWKId;
	private Integer parentReasonId;
	private Integer reasonId;
	private Integer reportTime;
	private Integer stopMinute;

	public void setDailyMCWKId(Integer dailyMCWKId) {
		this.dailyMCWKId = dailyMCWKId;
	}
	public Integer getDailyMCWKId() {
		return dailyMCWKId;
	}
	public Integer getParentReasonId() {
		return parentReasonId;
	}
	public void setParentReasonId(Integer parentReasonId) {
		this.parentReasonId = parentReasonId;
	}
	public Integer getReasonId() {
		return reasonId;
	}
	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	}
	public Integer getReportTime() {
		return reportTime;
	}
	public void setReportTime(Integer reportTime) {
		this.reportTime = reportTime;
	}
	public Integer getStopMinute() {
		return stopMinute;
	}
	public void setStopMinute(Integer stopMinute) {
		this.stopMinute = stopMinute;
	}
}