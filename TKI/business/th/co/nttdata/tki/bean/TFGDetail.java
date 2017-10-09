package th.co.nttdata.tki.bean;

import java.util.Date;

public class TFGDetail extends AbstractBaseBean {

	private Date reportDate;
	private Integer customerId;
	private Integer fgId;
	private Integer fgIn;
	private Integer fgOut;
	private Integer partId;
	private Integer reportType;
	private String customerCode;
	private String workOrderNo;
	private String lotNo;
	private String lotSeqNo;
	private String fgName;
	private String fgNo;
	private String reportTypeName;
	private String fgType;
	private String fullLotSeqNo;
	private String moldNo;

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getFgIn() {
		return fgIn;
	}

	public void setFgIn(Integer fgIn) {
		this.fgIn = fgIn;
	}

	public Integer getFgOut() {
		return fgOut;
	}

	public void setFgOut(Integer fgOut) {
		this.fgOut = fgOut;
	}

	public Integer getPartId() {
		return partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public Integer getFgId() {
		return fgId;
	}

	public void setFgId(Integer fgId) {
		this.fgId = fgId;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public String getLotSeqNo() {
		return lotSeqNo;
	}

	public void setLotSeqNo(String lotSeqNo) {
		this.lotSeqNo = lotSeqNo;
	}

	public String getWorkOrderNo() {
		return workOrderNo;
	}

	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}

	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	public String getFgName() {
		return fgName;
	}

	public void setFgName(String fgName) {
		this.fgName = fgName;
	}

	public String getFgNo() {
		return fgNo;
	}

	public void setFgNo(String fgNo) {
		this.fgNo = fgNo;
	}

	public String getReportTypeName() {
		return reportTypeName;
	}

	public void setReportTypeName(String reportTypeName) {
		this.reportTypeName = reportTypeName;
	}

	public String getFgType() {
		return fgType;
	}

	public void setFgType(String fgType) {
		this.fgType = fgType;
	}

	public String getFullLotSeqNo() {
		return fullLotSeqNo;
	}

	public void setFullLotSeqNo(String fullLotSeqNo) {
		this.fullLotSeqNo = fullLotSeqNo;
	}

	public String getMoldNo() {
		return moldNo;
	}

	public void setMoldNo(String moldNo) {
		this.moldNo = moldNo;
	}
	
}