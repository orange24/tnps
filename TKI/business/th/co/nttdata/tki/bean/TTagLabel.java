package th.co.nttdata.tki.bean;

public class TTagLabel extends AbstractBaseBean {

	private Integer tagId;
	private String lotNo;
	private String wip;
	private Integer customerId;
	private Integer fgId;
	private Integer snp;
	private Integer qty;
	private String labelType;
	private String vendorCode;
	private String vendorFgNo;

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

	public String getWip() {
		return wip;
	}

	public void setWip(String wip) {
		this.wip = wip;
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

	public Integer getSnp() {
		return snp;
	}

	public void setSnp(Integer snp) {
		this.snp = snp;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
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

}