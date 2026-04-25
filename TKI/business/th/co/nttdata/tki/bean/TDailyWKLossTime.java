package th.co.nttdata.tki.bean;

public class TDailyWKLossTime {

    private Integer lossTimeDetailId;
    private Integer dailyWKId;
    private Integer dailyWKDetailId;
    private Integer lossTimeReasonId;
    private Integer lossTime;
    private String  lossTimeReasonName;

    public Integer getLossTimeDetailId() { return lossTimeDetailId; }
    public void setLossTimeDetailId(Integer lossTimeDetailId) { this.lossTimeDetailId = lossTimeDetailId; }

    public Integer getDailyWKId() { return dailyWKId; }
    public void setDailyWKId(Integer dailyWKId) { this.dailyWKId = dailyWKId; }

    public Integer getDailyWKDetailId() { return dailyWKDetailId; }
    public void setDailyWKDetailId(Integer dailyWKDetailId) { this.dailyWKDetailId = dailyWKDetailId; }

    public Integer getLossTimeReasonId() { return lossTimeReasonId; }
    public void setLossTimeReasonId(Integer lossTimeReasonId) { this.lossTimeReasonId = lossTimeReasonId; }

    public Integer getLossTime() { return lossTime; }
    public void setLossTime(Integer lossTime) { this.lossTime = lossTime; }

    public String getLossTimeReasonName() { return lossTimeReasonName; }
    public void setLossTimeReasonName(String lossTimeReasonName) { this.lossTimeReasonName = lossTimeReasonName; }
}
