package th.co.nttdata.tki.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TFG extends AbstractBaseBean {

	private Date reportDate;
	private Date reportDateFr;
	private Date reportDateTo;
	private Integer customerId;
	private Integer fgId;
	private Integer month;
	private Integer partId;
	private Integer stockHoldDay;
	private Integer strDay;
	private Integer endDay;
	private Integer year;
	private String customerCode;
	private String customerName;
	private String fgName;
	private String fgNo;
	private String fgStockType;
	private String wip;
	private String yearMonth;
	private Integer reportType;
	private List<TFGDetail> details;
	private List<TFGStock> stockList;
	private Map<String, TFGDetail> detailMap;
	private Map<Integer, List<TFGStock>> stockMap;
	private Map<String, List<TFGStock>> stocksMap;
	private String moldNo;

	// criteria of Nirvana export
	private String nirvanaExportStatus;

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Date getReportDateFr() {
		return reportDateFr;
	}

	public void setReportDateFr(Date reportDateFr) {
		this.reportDateFr = reportDateFr;
	}

	public Date getReportDateTo() {
		return reportDateTo;
	}

	public void setReportDateTo(Date reportDateTo) {
		this.reportDateTo = reportDateTo;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public void setFgId(Integer fgId) {
		this.fgId = fgId;
	}

	public Integer getFgId() {
		return fgId;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getMonth() {
		return month;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public Integer getPartId() {
		return partId;
	}

	public void setStockHoldDay(Integer stockHoldDay) {
		this.stockHoldDay = stockHoldDay;
	}

	public Integer getStockHoldDay() {
		return stockHoldDay;
	}

	public void setStrDay(Integer strDay) {
		this.strDay = strDay;
	}

	public Integer getStrDay() {
		return strDay;
	}

	public void setEndDay(Integer endDay) {
		this.endDay = endDay;
	}

	public Integer getEndDay() {
		return endDay;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getYear() {
		return year;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setWip(String wip) {
		this.wip = wip;
	}

	public String getWip() {
		return wip;
	}

	public List<TFGDetail> getDetails() {
		return details;
	}

	public void setDetails(List<TFGDetail> details) {
		this.details = details;
	}

	public void setStockMap(Map<Integer, List<TFGStock>> stockMap) {
		this.stockMap = stockMap;
	}

	public Map<Integer, List<TFGStock>> getStockMap() {
		return stockMap;
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

	public String getFgStockType() {
		return fgStockType;
	}

	public void setFgStockType(String fgStockType) {
		this.fgStockType = fgStockType;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public Map<String, TFGDetail> getDetailMap() {
		return detailMap;
	}

	public void setDetailMap(Map<String, TFGDetail> detailMap) {
		this.detailMap = detailMap;
	}

	public List<TFGStock> getStockList() {
		return stockList;
	}

	public void setStockList(List<TFGStock> stockList) {
		this.stockList = stockList;
	}

	public Map<String, List<TFGStock>> getStocksMap() {
		return stocksMap;
	}

	public void setStocksMap(Map<String, List<TFGStock>> stocksMap) {
		this.stocksMap = stocksMap;
	}

	/**
	 * Gets the nirvanaExportStatus.
	 *
	 * @return the nirvanaExportStatus
	 */
	public String getNirvanaExportStatus() {
		return nirvanaExportStatus;
	}

	/**
	 * Changes the nirvanaExportStatus.
	 *
	 * @param nirvanaExportStatus - the nirvanaExportStatus to set
	 */
	public void setNirvanaExportStatus(String nirvanaExportStatus) {
		this.nirvanaExportStatus = nirvanaExportStatus;
	}

	public String getMoldNo() {
		return moldNo;
	}

	public void setMoldNo(String moldNo) {
		this.moldNo = moldNo;
	}

}