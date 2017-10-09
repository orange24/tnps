package th.co.nttdata.tki.bean;

public class PrintTagLabel extends AbstractBaseBean {

	private Integer tagId;
	private String lotNo;
	private Integer customerId;
	private String customerName;
	private Integer fgId;
	private String fgNo;
	private String fgName;
	private String labelType;
	private String vendorCode;
	private String vendorFgNo;
	private String partId;
	private String partNo;
	private String partName;
	private String lotSeqNo;
	private Integer lotSeqQty;
	private Integer flagPrint;
	private Boolean isBarcodeQty = false;

	public Boolean getIsBarcodeQty() {
		return isBarcodeQty;
	}

	public void setIsBarcodeQty(Boolean isBarcodeQty) {
		this.isBarcodeQty = isBarcodeQty;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getFgId() {
		return fgId;
	}

	public void setFgId(Integer fgId) {
		this.fgId = fgId;
	}

	public String getLabelType() {
		return labelType;
	}

	public void setLabelType(String labelType) {
		this.labelType = labelType;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getVendorFgNo() {
		return vendorFgNo;
	}

	public void setVendorFgNo(String vendorFgNo) {
		this.vendorFgNo = vendorFgNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
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

	public String getLotSeqNo() {
		return lotSeqNo;
	}

	public void setLotSeqNo(String lotSeqNo) {
		this.lotSeqNo = lotSeqNo;
	}

	public Integer getLotSeqQty() {
		return lotSeqQty;
	}

	public void setLotSeqQty(Integer lotSeqQty) {
		this.lotSeqQty = lotSeqQty;
	}

	public Integer getFlagPrint() {
		return flagPrint;
	}

	public void setFlagPrint(Integer flagPrint) {
		this.flagPrint = flagPrint;
	}

}