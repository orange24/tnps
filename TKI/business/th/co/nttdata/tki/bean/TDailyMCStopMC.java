package th.co.nttdata.tki.bean;

public class TDailyMCStopMC extends AbstractBaseBean {

	private Integer dailyMCId;
	private Integer reasonId;
	private Integer reportTime;
	private Integer stopMinute;

	public Integer getDailyMCId() {
		return dailyMCId;
	}
	public void setDailyMCId(Integer dailyMCId) {
		this.dailyMCId = dailyMCId;
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