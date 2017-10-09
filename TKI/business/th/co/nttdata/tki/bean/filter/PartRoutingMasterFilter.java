package th.co.nttdata.tki.bean.filter;
import java.util.ArrayList;
import java.util.List;

import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.bean.PartRoutingMaster;

public class PartRoutingMasterFilter extends AbstractBaseFilterBean{
	private String  customerId;
	private String  customerCode;
	private String  searchfgNo;
	private String  searchfgName;
	private String  searchpartNo;
	private String  searchpartName;
	private String  fgId;
	private String  fgNo;
	private String  fgName;
	private Integer partId;
	private String  partNo;
	private String  partName;
	private String  process;
	private String  wip;
	private String  wipName;
	private String  wipCode;
	private String  wipType;
	private String  isCalc;
    private String  wipCalc;
    private String  wipOrder;
	private List<String>  processList = new ArrayList<String>();
	private List<String>  wipCalcList = new ArrayList<String>();
	private List<Message> errors = new ArrayList<Message>();
	private List<Message> infos = new ArrayList<Message>();
	private List<PartRoutingMasterFilter>  partRoutinglist = new ArrayList<PartRoutingMasterFilter>();
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
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
	public Integer getPartId() {
		return partId;
	}
	public void setPartId(Integer partId) {
		this.partId = partId;
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
	public String getWipName() {
		return wipName;
	}
	public void setWipName(String wipName) {
		this.wipName = wipName;
	}
	public String getWipCode() {
		return wipCode;
	}
	public void setWipCode(String wipCode) {
		this.wipCode = wipCode;
	}
	public String getWipType() {
		return wipType;
	}
	public void setWipType(String wipType) {
		this.wipType = wipType;
	}
	public String getIsCalc() {
		return isCalc;
	}
	public void setIsCalc(String isCalc) {
		this.isCalc = isCalc;
	}
	public String getWipCalc() {
		return wipCalc;
	}
	public void setWipCalc(String wipCalc) {
		this.wipCalc = wipCalc;
	}
	public String getWipOrder() {
		return wipOrder;
	}
	public void setWipOrder(String wipOrder) {
		this.wipOrder = wipOrder;
	}
	public List<String> getProcessList() {
		return processList;
	}
	public void setProcessList(List<String> processList) {
		this.processList = processList;
	}
	public List<String> getWipCalcList() {
		return wipCalcList;
	}
	public void setWipCalcList(List<String> wipCalcList) {
		this.wipCalcList = wipCalcList;
	}
	public List<PartRoutingMasterFilter> getPartRoutinglist() {
		return partRoutinglist;
	}
	public void setPartRoutinglist(List<PartRoutingMasterFilter> partRoutinglist) {
		this.partRoutinglist = partRoutinglist;
	}
	public String getSearchfgNo() {
		return searchfgNo;
	}
	public void setSearchfgNo(String searchfgNo) {
		this.searchfgNo = searchfgNo;
	}
	public String getSearchfgName() {
		return searchfgName;
	}
	public void setSearchfgName(String searchfgName) {
		this.searchfgName = searchfgName;
	}
	public String getSearchpartNo() {
		return searchpartNo;
	}
	public void setSearchpartNo(String searchpartNo) {
		this.searchpartNo = searchpartNo;
	}
	public String getSearchpartName() {
		return searchpartName;
	}
	public void setSearchpartName(String searchpartName) {
		this.searchpartName = searchpartName;
	}
	public String getFgId() {
		return fgId;
	}
	public void setFgId(String fgId) {
		this.fgId = fgId;
	}
	public List<Message> getErrors() {
		return errors;
	}
	public void setErrors(List<Message> errors) {
		this.errors = errors;
	}
	public List<Message> getInfos() {
		return infos;
	}
	public void setInfos(List<Message> infos) {
		this.infos = infos;
	}

}
