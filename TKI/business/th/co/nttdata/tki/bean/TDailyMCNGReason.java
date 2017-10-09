package th.co.nttdata.tki.bean;

public class TDailyMCNGReason extends AbstractBaseBean {

	private Integer dailyMCId;
	private Integer ng;
	private Integer reasonId;
	private Integer reportTime;
	//MRDC_S17
	private String idRef;
	private String nMCStopTime;

	public Integer getDailyMCId() {
		return dailyMCId;
	}
	public void setDailyMCId(Integer dailyMCId) {
		this.dailyMCId = dailyMCId;
	}
	public Integer getNg() {
		return ng;
	}
	public void setNg(Integer ng) {
		this.ng = ng;
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
	public String getIdRef() {
		return idRef;
	}
	public void setIdRef(String idRef) {
		this.idRef = idRef;
	}
	public String getnMCStopTime() {
		return nMCStopTime;
	}
	public void setnMCStopTime(String nMCStopTime) {
		this.nMCStopTime = nMCStopTime;
	}
}