package th.co.nttdata.tki.bean;

import java.util.List;
import java.util.Map;

public class MPart extends AbstractBaseBean {

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(" Part No=").append(partNo).append(",Part Id=").append(partId)
				.append(",Choose=").append(choose).append(",Part List=[")
				.append(partList).append("],Wip List={").append(wipList)
				.append("}\n");
		return b.toString();
	}

	private String createdBy;
	private Integer customerId;
	private String customerCode;
	private String customerName;
	private Integer machineId;
	private Integer fgId;
	private String fgNo;
	private String fgName;
	private Integer partId;
	private String partName;
	private String partNo;
	private String wip;
	private String wipName;
	private String lot;
	private String remark;
	private String materialId;
	private String materialName;
	private boolean choose;
	private Integer snpWip;
	private String isEnable;
	private String linkDB;
	private String updatedBy;
	private String material;
	private String lineNumber;
	private List<MPartWip> wipList;
	private List<MPart> partList;
	private Map<String, Object> partSync;

	// DLV_S02
	private String isPlan;

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

	public Integer getPartId() {
		return partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<MPartWip> getWipList() {
		return wipList;
	}

	public void setWipList(List<MPartWip> wipList) {
		this.wipList = wipList;
	}

	public List<MPart> getPartList() {
		return partList;
	}

	public void setPartList(List<MPart> partList) {
		this.partList = partList;
	}

	public Map<String, Object> getPartSync() {
		return partSync;
	}

	public void setPartSync(Map<String, Object> partSync) {
		this.partSync = partSync;
	}

	public String getIsPlan() {
		return isPlan;
	}

	public void setIsPlan(String isPlan) {
		this.isPlan = isPlan;
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

	public Integer getFgId() {
		return fgId;
	}

	public void setFgId(Integer fgId) {
		this.fgId = fgId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public boolean getChoose() {
		return choose;
	}

	public void setChoose(boolean choose) {
		this.choose = choose;
	}

	public String getLinkDB() {
		return linkDB;
	}

	public void setLinkDB(String linkDB) {
		this.linkDB = linkDB;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	public Integer getMachineId() {
		return machineId;
	}

	public void setMachineId(Integer machineId) {
		this.machineId = machineId;
	}

	public Integer getSnpWip() {
		return snpWip;
	}

	public void setSnpWip(Integer snpWip) {
		this.snpWip = snpWip;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

}