package th.co.nttdata.tki.bean;

import th.co.nttdata.tki.bean.filter.AbstractBaseFilterBean;

public class TDCPlanFilter extends AbstractBaseFilterBean {

	private String dcPlanDateFrom;
	private String dcPlanDateTo;
	private String wip;
	private String machineId;
	private String shift;
	private String customerId;
	private String reasonId;
	private String partNo;
	private String partName;
	private String generateLString;

	public String getDcPlanDateFrom() {
		return dcPlanDateFrom;
	}

	public void setDcPlanDateFrom(String dcPlanDateFrom) {
		this.dcPlanDateFrom = dcPlanDateFrom;
	}

	public String getDcPlanDateTo() {
		return dcPlanDateTo;
	}

	public void setDcPlanDateTo(String dcPlanDateTo) {
		this.dcPlanDateTo = dcPlanDateTo;
	}

	public String getWip() {
		return wip;
	}

	public void setWip(String wip) {
		this.wip = wip;
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getReasonId() {
		return reasonId;
	}

	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getGenerateLString() {
		return generateLString;
	}

	public void setGenerateLString(String generateLString) {
		this.generateLString = generateLString;
	}

}
