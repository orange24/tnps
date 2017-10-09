package th.co.nttdata.tki.bean;

import java.util.ArrayList;
import java.util.List;

public class ProcessMaster extends AbstractBaseBean{
	private Integer customerId;
	private String  customerCode;
	private String  fgNo;
	private String  fgName;
	private Integer partId;
	private Integer destinationPartId;
	private String  partNo;
	private String  partName;
	private String  process;
	private String  wip;
	private String  wipName;
	private String  wipCode;
	private String  wipType;
	private Boolean isCalc;
    private String  wipCalc;
    private Integer wipOrder;
	private List<String>  processList = new ArrayList<String>();
	private List<String>  wipCalcList = new ArrayList<String>();
	private List<ProcessMaster>  processMasterList = new ArrayList<ProcessMaster>();
	
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getFgNo() {
		return fgNo;
	}
	public void setFgNo(String fgNo) {
		this.fgNo = fgNo;
	}
	public String getFgName() {
		return fgName;
	}
	public void setFgName(String fgName) {
		this.fgName = fgName;
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
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	
	public String getWip() {
		return wip;
	}
	public void setWip(String wip) {
		this.wip = wip;
	}
	
	public Boolean getIsCalc() {
		return isCalc;
	}
	public void setIsCalc(Boolean isCalc) {
		if("null".equals(isCalc)){
			isCalc = false;
		}
		else
		  this.isCalc = isCalc;
	}
	public List<String> getProcessList() {
		return processList;
	}
	public void setProcessList(List<String> processList) {
		this.processList = processList;
	}
	public String getWipCalc() {
		return wipCalc;
	}
	public void setWipCalc(String wipCalc) {
		this.wipCalc = wipCalc;
	}
	public List<String> getWipCalcList() {
		return wipCalcList;
	}
	public void setWipCalcList(List<String> wipCalcList) {
		this.wipCalcList = wipCalcList;
	}
	public Integer getPartId() {
		return partId;
	}
	public void setPartId(Integer partId) {
		this.partId = partId;
	}
	public Integer getWipOrder() {
		return wipOrder;
	}
	public void setWipOrder(Integer wipOrder) {
		this.wipOrder = wipOrder;
	}
	public String getWipName() {
		return wipName;
	}
	public void setWipName(String wipName) {
		this.wipName = wipName;
	}
	public String getWipType() {
		return wipType;
	}
	public void setWipType(String wipType) {
		this.wipType = wipType;
	}
	public String getWipCode() {
		return wipCode;
	}
	public void setWipCode(String wipCode) {
		this.wipCode = wipCode;
	}
	public Integer getDestinationPartId() {
		return destinationPartId;
	}
	public void setDestinationPartId(Integer destinationPartId) {
		this.destinationPartId = destinationPartId;
	}
	public List<ProcessMaster> getProcessMasterList() {
		return processMasterList;
	}
	public void setProcessMasterList(List<ProcessMaster> processMasterList) {
		this.processMasterList = processMasterList;
	}

}
