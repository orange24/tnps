package th.co.nttdata.tki.bean;

import java.util.Map;

public class TDailyMCWKDetail {

	private Double manPower;
	private Double timeUsed;
	private Integer customerId;
	private Integer dailyMCWKDetailId;
	private Integer dailyMCWKId;
	private Integer ng;
	private Integer ok;
	private Integer partId;
	private Integer pd;
	private Integer qty;
	private Integer reportTime;
	private Integer workOrderQty;
	private String lotNo;
	private String workOrderNo;
	private Map<Integer,Integer> ngReasonMap;

	public Double getManPower() {
		return manPower;
	}
	public void setManPower(Double manPower) {
		this.manPower = manPower;
	}
	public Double getTimeUsed() {
		return timeUsed;
	}
	public void setTimeUsed(Double timeUsed) {
		this.timeUsed = timeUsed;
	}
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
	public Integer getReportTime() {
		return reportTime;
	}
	public void setReportTime(Integer reportTime) {
		this.reportTime = reportTime;
	}
	public void setWorkOrderQty(Integer workOrderQty) {
		this.workOrderQty = workOrderQty;
	}
	public Integer getWorkOrderQty() {
		return workOrderQty;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	public String getWorkOrderNo() {
		return workOrderNo;
	}
	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}
	public void setNgReasonMap(Map<Integer,Integer> ngReasonMap) {
		this.ngReasonMap = ngReasonMap;
	}
	public Map<Integer,Integer> getNgReasonMap() {
		return ngReasonMap;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
}