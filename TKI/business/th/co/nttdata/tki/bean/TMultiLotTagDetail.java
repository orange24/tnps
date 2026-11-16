package th.co.nttdata.tki.bean;

import java.util.Date;

public class TMultiLotTagDetail extends AbstractBaseBean {
	private String refNo;
	private Integer lineNo;
	private String lotSeqNo;
	private Integer qty;
	private Date dateCode;
	private Integer customerId;
	private String customerCode;
	private Integer fgId;
	private String fgNo;
	private String fgName;
	private Integer lotSeqQty;

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public Integer getLineNo() {
		return lineNo;
	}

	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}

	public String getLotSeqNo() {
		return lotSeqNo;
	}

	public void setLotSeqNo(String lotSeqNo) {
		this.lotSeqNo = lotSeqNo;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Date getDateCode() {
		return dateCode;
	}

	public void setDateCode(Date dateCode) {
		this.dateCode = dateCode;
	}

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

	public Integer getFgId() {
		return fgId;
	}

	public void setFgId(Integer fgId) {
		this.fgId = fgId;
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

	public Integer getLotSeqQty() {
		return lotSeqQty;
	}

	public void setLotSeqQty(Integer lotSeqQty) {
		this.lotSeqQty = lotSeqQty;
	}
}
