package th.co.nttdata.tki.bean;

import java.util.Date;
import java.util.List;

public class MMoldDetail extends AbstractBaseBean {

	private Date endDate;
	private Date startDate;
	private Integer customerId;
	private Integer alertShot;
	private Integer guaranteeShot;
	private Integer initialShot;
	private Integer moldId;
	private Integer totalDCShot;
	private Integer totalFGSold;
	private Integer dcStatus;
	private Integer fgStatus;
	private Integer partId;
	private Integer statusActive;
	private Integer status[];
	private Integer qtyShot;
	private Integer productionQty;//For MRDC_R21
	private Integer salesQty;//For MRDC_R21
	private List<MMoldDetail> mDetailList;
	private List<MPart> mPartList;
	private String cavNo;
	private String customerCode;
	private String moldNo;
	private String moldNoEdit;
	private String moldName;
	private String partName;
	private String partNo;
	private String statusDcName;
	private String statusFgName;
	private String moldOrderSheet;
	
	//MRDC_S21
	private Integer categoryType;
	private Integer maxRecord;
	private Double  percent;

	public Integer getPartId() {
		return partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getStatusDcName() {
		return statusDcName;
	}

	public void setStatusDcName(String statusDcName) {
		this.statusDcName = statusDcName;
	}

	public String getStatusFgName() {
		return statusFgName;
	}

	public void setStatusFgName(String statusFgName) {
		this.statusFgName = statusFgName;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getCustomerId() {
		return customerId;
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

	public Integer getDcStatus() {
		return dcStatus;
	}

	public void setDcStatus(Integer dcStatus) {
		this.dcStatus = dcStatus;
	}

	public Integer getFgStatus() {
		return fgStatus;
	}

	public void setFgStatus(Integer fgStatus) {
		this.fgStatus = fgStatus;
	}

	public Integer getStatusActive() {
		return statusActive;
	}

	public void setStatusActive(Integer statusActive) {
		this.statusActive = statusActive;
	}

	public Integer[] getStatus() {
		return status;
	}

	public void setStatus(Integer[] status) {
		this.status = status;
	}

	public String getMoldNo() {
		return moldNo;
	}

	public void setMoldNo(String moldNo) {
		this.moldNo = moldNo;
	}

	public void setMoldName(String moldName) {
		this.moldName = moldName;
	}

	public String getMoldName() {
		return moldName;
	}

	public void setmDetailList(List<MMoldDetail> mDetailList) {
		this.mDetailList = mDetailList;
	}

	public List<MMoldDetail> getmDetailList() {
		return mDetailList;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setQtyShot(Integer qtyShot) {
		this.qtyShot = qtyShot;
	}

	public Integer getQtyShot() {
		return qtyShot;
	}

	public void setAlertShot(Integer alertShot) {
		this.alertShot = alertShot;
	}

	public Integer getAlertShot() {
		return alertShot;
	}

	public void setGuaranteeShot(Integer guaranteeShot) {
		this.guaranteeShot = guaranteeShot;
	}

	public Integer getGuaranteeShot() {
		return guaranteeShot;
	}

	public void setInitialShot(Integer initialShot) {
		this.initialShot = initialShot;
	}

	public Integer getInitialShot() {
		return initialShot;
	}

	public void setmPartList(List<MPart> mPartList) {
		this.mPartList = mPartList;
	}

	public List<MPart> getmPartList() {
		return mPartList;
	}

	public void setCavNo(String cavNo) {
		this.cavNo = cavNo;
	}

	public String getCavNo() {
		return cavNo;
	}

	public void setMoldNoEdit(String moldNoEdit) {
		this.moldNoEdit = moldNoEdit;
	}

	public String getMoldNoEdit() {
		return moldNoEdit;
	}

	public void setProductionQty(Integer productionQty) {
		this.productionQty = productionQty;
	}

	public Integer getProductionQty() {
		return productionQty;
	}

	public void setSalesQty(Integer salesQty) {
		this.salesQty = salesQty;
	}

	public Integer getSalesQty() {
		return salesQty;
	}

	public Integer getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(Integer categoryType) {
		this.categoryType = categoryType;
	}

	public Integer getMaxRecord() {
		return maxRecord;
	}

	public void setMaxRecord(Integer maxRecord) {
		this.maxRecord = maxRecord;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public String getMoldOrderSheet() {
		return moldOrderSheet;
	}

	public void setMoldOrderSheet(String moldOrderSheet) {
		this.moldOrderSheet = moldOrderSheet;
	}

}
