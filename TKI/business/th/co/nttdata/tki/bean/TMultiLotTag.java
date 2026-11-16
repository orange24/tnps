package th.co.nttdata.tki.bean;

import java.util.ArrayList;
import java.util.List;

public class TMultiLotTag extends AbstractBaseBean {
	private String refNo;
	private Integer customerId;
	private Integer fgId;
	private String wip;
	private String status;
	private Integer printCount;
	private String printerName;
	private String customerName;
	private String fgNo;
	private String fgName;
	private String wipName;
	private List<TMultiLotTagDetail> details = new ArrayList<TMultiLotTagDetail>();

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
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

	public String getWip() {
		return wip;
	}

	public void setWip(String wip) {
		this.wip = wip;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPrintCount() {
		return printCount;
	}

	public void setPrintCount(Integer printCount) {
		this.printCount = printCount;
	}

	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
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

	public String getWipName() {
		return wipName;
	}

	public void setWipName(String wipName) {
		this.wipName = wipName;
	}

	public List<TMultiLotTagDetail> getDetails() {
		return details;
	}

	public void setDetails(List<TMultiLotTagDetail> details) {
		this.details = details;
	}
}
