package th.co.nttdata.tki.bean;

import java.util.Date;

public class DailyReport extends AbstractBaseBean {
	private String id;
	private String wip;
	private Integer[] reportType;
	private Date reportDateFr;
	private Date reportDateTo;
	private Character shiftDay;
	private Character shiftNight;
	private Character shiftAll;
	private Integer custermerId;
	private Integer[] partId;
	private Integer machineId;
	private String moldNo;
	private String workOrderNo;
	private String lotNo;
	
	public Character getShiftAll() {
		return shiftAll;
	}
	public void setShiftAll(Character shiftAll) {
		this.shiftAll = shiftAll;
	}
	public String getWip() {
		return wip;
	}
	public void setWip(String wip) {
		this.wip = wip;
	}
	public Integer[] getReportType() {
		return reportType;
	}
	public void setReportType(Integer[] reportType) {
		this.reportType = reportType;
	}
	public Date getReportDateFr() {
		return reportDateFr;
	}
	public void setReportDateFr(Date reportDateFr) {
		this.reportDateFr = reportDateFr;
	}
	public Date getReportDateTo() {
		return reportDateTo;
	}
	public void setReportDateTo(Date reportDateTo) {
		this.reportDateTo = reportDateTo;
	}
	public Character getShiftDay() {
		return shiftDay;
	}
	public void setShiftDay(Character shiftDay) {
		this.shiftDay = shiftDay;
	}
	public Character getShiftNight() {
		return shiftNight;
	}
	public void setShiftNight(Character shiftNight) {
		this.shiftNight = shiftNight;
	}
	public Integer getCustermerId() {
		return custermerId;
	}
	public void setCustermerId(Integer custermerId) {
		this.custermerId = custermerId;
	}
	public Integer[] getPartId() {
		return partId;
	}
	public void setPartId(Integer[] partId) {
		this.partId = partId;
	}
	public Integer getMachineId() {
		return machineId;
	}
	public void setMachineId(Integer machineId) {
		this.machineId = machineId;
	}
	public String getMoldNo() {
		return moldNo;
	}
	public void setMoldNo(String moldNo) {
		this.moldNo = moldNo;
	}
	public String getWorkOrderNo() {
		return workOrderNo;
	}
	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
}
