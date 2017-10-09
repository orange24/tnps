package th.co.nttdata.tki.bean;

import java.util.Date;
import java.util.List;

public class MMachine extends AbstractBaseBean {
	private Date endDate;
	private Date startDate;
	private String machineCost;
	private Integer machineId;
	private Integer sourceMachineId;
	private String machineName;
	private String machineNo;
	private String wip;
	private String wipDialog;
	private Integer customerId;
	private String customerCode;
	private Integer partId;
	private String partNo;
	private String partName;
	private String part;
	private Date diecastPlanDate;
	private List<MMachine> machineList;

	public MMachine() {
	}

	public MMachine(Integer machineId, String machineNo) {
		this.machineId = machineId;
		this.machineNo = machineNo;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Integer getMachineId() {
		return machineId;
	}

	public void setMachineId(Integer machineId) {
		this.machineId = machineId;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}

	public String getMachineNo() {
		return machineNo;
	}

	public String getWip() {
		return wip;
	}

	public void setWip(String wip) {
		this.wip = wip;
	}

	public List<MMachine> getMachineList() {
		return machineList;
	}

	public void setMachineList(List<MMachine> machineList) {
		this.machineList = machineList;
	}

	public String getMachineCost() {
		return machineCost;
	}

	public void setMachineCost(String machineCost) {
		this.machineCost = machineCost;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
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

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public Integer getSourceMachineId() {
		return sourceMachineId;
	}

	public void setSourceMachineId(Integer sourceMachineId) {
		this.sourceMachineId = sourceMachineId;
	}

	public Integer getPartId() {
		return partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public String getWipDialog() {
		return wipDialog;
	}

	public void setWipDialog(String wipDialog) {
		this.wipDialog = wipDialog;
	}

	public Date getDiecastPlanDate() {
		return diecastPlanDate;
	}

	public void setDiecastPlanDate(Date diecastPlanDate) {
		this.diecastPlanDate = diecastPlanDate;
	}

}