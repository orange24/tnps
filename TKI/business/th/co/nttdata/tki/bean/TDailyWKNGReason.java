package th.co.nttdata.tki.bean;

public class TDailyWKNGReason {

	private Integer dailyWKId;
	private Integer dailyWKDetailId;
	private Integer reasonId;
	private Integer ng;
	private String idRef;

	public void setDailyWKId(Integer dailyWKId) {
		this.dailyWKId = dailyWKId;
	}
	public Integer getDailyWKId() {
		return dailyWKId;
	}
	public Integer getDailyWKDetailId() {
		return dailyWKDetailId;
	}
	public void setDailyWKDetailId(Integer dailyWKDetailId) {
		this.dailyWKDetailId = dailyWKDetailId;
	}
	public Integer getReasonId() {
		return reasonId;
	}
	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	}
	public Integer getNg() {
		return ng;
	}
	public void setNg(Integer ng) {
		this.ng = ng;
	}
	public void setIdRef(String idRef) {
		this.idRef = idRef;
	}
	public String getIdRef() {
		return idRef;
	}
}