package th.co.nttdata.tki.bean;

import java.util.List;

public class TTagLabelDetail extends AbstractBaseBean {
	private String lotNo;
	private String wip;
	private String wipName;
	private Integer customerId;
	private Integer fgId;
	private Integer snp_wip;
	private Integer qty;
	private String labelType;
	private String vendorCode;
	private String vendorFgNo;
	private Integer tagId;
	private String printerId;
	private String printerName;
	private Integer printQtyRemain;
	private String moldName;
	private String moldNo;

	private List<TLotSequence> lotSequenceList;

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

	public Integer getQty() {
		return qty;
	}

	public Integer getSnp_wip() {
		return snp_wip;
	}

	public void setSnp_wip(Integer snp_wip) {
		this.snp_wip = snp_wip;
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

	public String getWipName() {
		return wipName;
	}

	public void setWipName(String wipName) {
		this.wipName = wipName;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public String getPrinterId() {
		return printerId;
	}

	public void setPrinterId(String printerId) {
		this.printerId = printerId;
	}

	public List<TLotSequence> getLotSequenceList() {
		return lotSequenceList;
	}

	public void setLotSequenceList(List<TLotSequence> lotSequenceList) {
		this.lotSequenceList = lotSequenceList;
	}

	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	public Integer getPrintQtyRemain() {
		return printQtyRemain;
	}

	public void setPrintQtyRemain(Integer printQtyRemain) {
		this.printQtyRemain = printQtyRemain;
	}

	public String getMoldName() {
		return moldName;
	}

	public void setMoldName(String moldName) {
		this.moldName = moldName;
	}

	public String getMoldNo() {
		return moldNo;
	}

	public void setMoldNo(String moldNo) {
		this.moldNo = moldNo;
	}
}