package th.co.nttdata.tki.bean;

public class TDailyMCWKNGReason {

	private String  id;
	private Integer dailyMCWKDetailId;
	private Integer dailyMCWKId;
	private Integer ng;
	private Integer reasonId;

	public Integer getDailyMCWKDetailId() {
		return dailyMCWKDetailId;
	}
	public void setDailyMCWKDetailId(Integer dailyMCWKDetailId) {
		this.dailyMCWKDetailId = dailyMCWKDetailId;
	}
	public Integer getDailyMCWKId() {
		return dailyMCWKId;
	}
	public void setDailyMCWKId(Integer dailyMCWKId) {
		this.dailyMCWKId = dailyMCWKId;
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
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
}