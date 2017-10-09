package th.co.nttdata.tki.bean;

import java.util.ArrayList;
import java.util.List;

public class MFgPart extends AbstractBaseBean {

	private Integer customerId = 0;
	private String customerCode;
	private String customerName;
	private String remark;
	private String vendorCode;
	private String fg;
	private String fgNo;
	private String fgId;
	private String fgName;
	private String partId;
	private String partNo;
	private String partName;
	private String smaterialName;
	private String materialId;
	private Integer rowNo;
	private List<MFgPart> customerList = new ArrayList<MFgPart>();

	public Integer getRowNo() {
		return rowNo;
	}

	public void setRowNo(Integer rowNo) {
		this.rowNo = rowNo;
	}

	public String getSmaterialName() {
		return smaterialName;
	}

	public void setSmaterialName(String smaterialName) {
		this.smaterialName = smaterialName;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getFgId() {
		return fgId;
	}

	public void setFgId(String fgId) {
		this.fgId = fgId;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getFg() {
		return fg;
	}

	public void setFg(String fg) {
		this.fg = fg;
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

	public List<MFgPart> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<MFgPart> customerList) {
		this.customerList = customerList;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Override
	public String getRemark() {
		return remark;
	}

	@Override
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
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

}
