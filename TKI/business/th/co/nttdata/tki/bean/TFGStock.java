package th.co.nttdata.tki.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

public class TFGStock extends AbstractBaseBean {

	private Date reportDate;
	private Integer actualQty;
	private Integer balanceQty;
	private Integer customerId;
	private Integer deliveryQty;
	private Integer fgAdjust;
	private Integer fgBalance;
	private Integer fgId;
	private Integer fgIn;
	private Integer fgOut;
	private Integer fgStock;
	private Integer partId;
	private Integer reportDay;
	private Integer status;
	private Double weight;
	private String adjustReason;
	private String customerCode;
	private String customerName;
	private String fgName;
	private String fgNo;
	private String partNo;
	private String partName;
	private String moldNo;

	// MRDC_S16
	private Date dateFrom;
	private Date dateTo;
	private Double fgValue;
	private Double saleUnitPrice;
	private Double totalStock;
	private Double unitPrice;
	private Double wipValue;
	private Integer fgQty;
	private Integer stockWIP;
	private Integer maxRecord;
	private Integer monthFr;
	private Integer monthTo;
	private Integer wipQty;
	private Integer yearFr;
	private Integer yearTo;
	private Double unitWeight;
	private String idRef;
	private String yearMonth;
	private String currency;
	private String material;
	private String category;

	private List<TFGDetail> detailList;
	private List<TFGStock> tfgStockList;
	private Map<String, TFGStock> wipMap;

	// FNG_S05 criteria search fg adjust history
	private Date reportDateFrom;
	private Date reportDateTo;
	private Date createDateFrom;
	private Date createDateTo;

	// FNG_S04
	private transient MultipartFile fileImport;

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public void setActualQty(Integer actualQty) {
		this.actualQty = actualQty;
	}

	public Integer getActualQty() {
		return actualQty;
	}

	public void setBalanceQty(Integer balanceQty) {
		this.balanceQty = balanceQty;
	}

	public Integer getBalanceQty() {
		return balanceQty;
	}

	public void setDeliveryQty(Integer deliveryQty) {
		this.deliveryQty = deliveryQty;
	}

	public Integer getDeliveryQty() {
		return deliveryQty;
	}

	public Integer getFgAdjust() {
		return fgAdjust;
	}

	public void setFgAdjust(Integer fgAdjust) {
		this.fgAdjust = fgAdjust;
	}

	public Integer getFgBalance() {
		return fgBalance;
	}

	public void setFgBalance(Integer fgBalance) {
		this.fgBalance = fgBalance;
	}

	public Integer getFgId() {
		return fgId;
	}

	public void setFgId(Integer fgId) {
		this.fgId = fgId;
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

	public Integer getFgStock() {
		return fgStock;
	}

	public void setFgStock(Integer fgStock) {
		this.fgStock = fgStock;
	}

	public Integer getPartId() {
		return partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public void setReportDay(Integer reportDay) {
		this.reportDay = reportDay;
	}

	public Integer getReportDay() {
		return reportDay;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public String getAdjustReason() {
		return adjustReason;
	}

	public void setAdjustReason(String adjustReason) {
		this.adjustReason = adjustReason;
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

	public List<TFGDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<TFGDetail> detailList) {
		this.detailList = detailList;
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public List<TFGStock> getTfgStockList() {
		return tfgStockList;
	}

	public void setTfgStockList(List<TFGStock> tfgStockList) {
		this.tfgStockList = tfgStockList;
	}

	public Map<String, TFGStock> getWipMap() {
		return wipMap;
	}

	public void setWipMap(Map<String, TFGStock> wipMap) {
		this.wipMap = wipMap;
	}

	public Double getFgValue() {
		return fgValue;
	}

	public void setFgValue(Double fgValue) {
		this.fgValue = fgValue;
	}

	public Double getSaleUnitPrice() {
		return saleUnitPrice;
	}

	public void setSaleUnitPrice(Double saleUnitPrice) {
		this.saleUnitPrice = saleUnitPrice;
	}

	public Double getTotalStock() {
		return totalStock;
	}

	public void setTotalStock(Double totalStock) {
		this.totalStock = totalStock;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getWipValue() {
		return wipValue;
	}

	public void setWipValue(Double wipValue) {
		this.wipValue = wipValue;
	}

	public Integer getMaxRecord() {
		return maxRecord;
	}

	public void setMaxRecord(Integer maxRecord) {
		this.maxRecord = maxRecord;
	}

	public Integer getMonthFr() {
		return monthFr;
	}

	public void setMonthFr(Integer monthFr) {
		this.monthFr = monthFr;
	}

	public Integer getMonthTo() {
		return monthTo;
	}

	public void setMonthTo(Integer monthTo) {
		this.monthTo = monthTo;
	}

	public Integer getYearFr() {
		return yearFr;
	}

	public void setYearFr(Integer yearFr) {
		this.yearFr = yearFr;
	}

	public Integer getYearTo() {
		return yearTo;
	}

	public void setYearTo(Integer yearTo) {
		this.yearTo = yearTo;
	}

	public String getIdRef() {
		return idRef;
	}

	public void setIdRef(String idRef) {
		this.idRef = idRef;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public Integer getFgQty() {
		return fgQty;
	}

	public void setFgQty(Integer fgQty) {
		this.fgQty = fgQty;
	}

	public Integer getStockWIP() {
		return stockWIP;
	}

	public void setStockWIP(Integer stockWIP) {
		this.stockWIP = stockWIP;
	}

	public Integer getWipQty() {
		return wipQty;
	}

	public void setWipQty(Integer wipQty) {
		this.wipQty = wipQty;
	}

	public Date getReportDateFrom() {
		return reportDateFrom;
	}

	public void setReportDateFrom(Date reportDateFrom) {
		this.reportDateFrom = reportDateFrom;
	}

	public Date getReportDateTo() {
		return reportDateTo;
	}

	public void setReportDateTo(Date reportDateTo) {
		this.reportDateTo = reportDateTo;
	}

	public Date getCreateDateFrom() {
		return createDateFrom;
	}

	public void setCreateDateFrom(Date createDateFrom) {
		this.createDateFrom = createDateFrom;
	}

	public Date getCreateDateTo() {
		return createDateTo;
	}

	public void setCreateDateTo(Date createDateTo) {
		this.createDateTo = createDateTo;
	}

	@JsonIgnore
	public MultipartFile getFileImport() {
		return fileImport;
	}

	public void setFileImport(MultipartFile fileImport) {
		this.fileImport = fileImport;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Double getUnitWeight() {
		return unitWeight;
	}

	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getMoldNo() {
		return moldNo;
	}

	public void setMoldNo(String moldNo) {
		this.moldNo = moldNo;
	}
}