package th.co.nttdata.tki.bean;

import java.util.Date;
import java.util.List;

public class TMoldHistory extends AbstractBaseBean {
	private Date    startDateHist;
	private Date    endDateHist;
	private Date    reportDate;
	private Integer customerId;
	private Integer partId;
	private Integer moldHistoryId;
	private Integer moldId;
	private Integer totalDCShot;
	private Integer totalFGSold;
	private String  moldNo;
	private String  moldName;
	private String  customerCode;
	private String  partNo;
	private String  partName;
	private List<TMoldHistory> tMoldHistList;
	
	public String getMoldName() {
		return moldName;
	}

	public void setMoldName(String moldName) {
		this.moldName = moldName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
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

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Integer getMoldHistoryId() {
		return moldHistoryId;
	}

	public void setMoldHistoryId(Integer moldHistoryId) {
		this.moldHistoryId = moldHistoryId;
	}

	public Integer getMoldId() {
		return moldId;
	}

	public void setMoldId(Integer moldId) {
		this.moldId = moldId;
	}

	public Integer getTotalDCShot() {
		return totalDCShot;
	}

	public void setTotalDCShot(Integer totalDCShot) {
		this.totalDCShot = totalDCShot;
	}

	public Integer getTotalFGSold() {
		return totalFGSold;
	}

	public void setTotalFGSold(Integer totalFGSold) {
		this.totalFGSold = totalFGSold;
	}

	public Date getStartDateHist() {
		return startDateHist;
	}

	public void setStartDateHist(Date startDateHist) {
		this.startDateHist = startDateHist;
	}

	public Date getEndDateHist() {
		return endDateHist;
	}

	public void setEndDateHist(Date endDateHist) {
		this.endDateHist = endDateHist;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getPartId() {
		return partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public String getMoldNo() {
		return moldNo;
	}

	public void setMoldNo(String moldNo) {
		this.moldNo = moldNo;
	}

	public void settMoldHistList(List<TMoldHistory> tMoldHistList) {
		this.tMoldHistList = tMoldHistList;
	}

	public List<TMoldHistory> gettMoldHistList() {
		return tMoldHistList;
	}
}
