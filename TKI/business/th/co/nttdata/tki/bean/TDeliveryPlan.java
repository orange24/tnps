package th.co.nttdata.tki.bean;

import java.util.Date;
import java.util.List;

public class TDeliveryPlan extends AbstractBaseBean {

	private Boolean isPlanExists; 
	private Integer customerId;
	private Integer dayOfMonth;
	private Integer deliveryPlanId;
	private Integer newDeliveryPlanId;
	private Integer fgId;
	private Integer revision;
	private Integer month;
	private Integer year;
	private String customerCode;
	private String customerName;
	private String fgName;
	private String fgNo;
	private String monthName;
	private String yearmonth;
	private String insertFlag;
	private String currency;
	private Integer countDate;
	private List<TDeliveryPlanDate> dateList;
	private List<TDeliveryPlan> planList;
	
	// use export DLV_R02
	private Date deliveryDate;
	private Integer deliveryQty;
	private Integer timeHr;
	private Integer timeMin;
	
	// MRDC_S14
	private Double  saleUnitPrice;
	private Integer maxRecord;
	private Integer monthFr;
	private Integer monthTo;
	private Integer partId;
	private Integer yearFr;
	private Integer yearTo;
	private Integer outputCategory;
	private String  partName;
	
	// MRDC_S11
	private Double unitPrice;
	private String material;
	private String  partNo;
	
	// MRDC_R18
	private Double unitWeight;
	private Integer normalQty;
	private Integer returnQty;
	private Integer orderQty;
	private Integer forecastQty;
	private Integer remainQty;
	private String materialName;
	private String yearmonthFr;
	private String yearmonthTo;
	private List<MWip> wipList;
	private List<TDeliveryPlanDate> date2OrderList;
	private List<TDeliveryPlanDate> date2SalesList;
	private List<TDeliveryPlanDate> date3List;
	private List<TDeliveryPlanDate> date4List;
	private List<TDeliveryPlanDate> date5List;
	
	// MRDC_R01
	private Double diecastCost;
	private Double materialCost;
	private Double processingCost;
	private Integer cavQty;
	private Integer stockControl;
	private String category;
	private String wip;
	private String wipName;
	private String outSource;
	private String workOrderNo;
	private List<TDeliveryPlan> plan2List;
	
	// MRDC_R04
	private Date moldUpdatedDate;
	private Integer qty;
	private Integer cycleTimeOK;
	private Integer materialUnitCost;
	private Integer numberTimeMoldChange;
	private Integer initialShotQty;
	private Integer maxShotQty;
	private String cavNo;
	private String remark;
	private String moldName;
	private String moldNo;
	private String productRate;
	
	// MRDC_S03
	private Integer materialId;
	private Integer ng;
	
	// MRDC_R20
	private Integer sorting;

	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Boolean getIsPlanExists() {
		return isPlanExists;
	}
	public void setIsPlanExists(Boolean isPlanExists) {
		this.isPlanExists = isPlanExists;
	}
	public void setDayOfMonth(Integer dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}
	public Integer getDayOfMonth() {
		return dayOfMonth;
	}
	public Integer getDeliveryPlanId() {
		return deliveryPlanId;
	}
	public void setDeliveryPlanId(Integer deliveryPlanId) {
		this.deliveryPlanId = deliveryPlanId;
	}
	public void setNewDeliveryPlanId(Integer newDeliveryPlanId) {
		this.newDeliveryPlanId = newDeliveryPlanId;
	}
	public Integer getNewDeliveryPlanId() {
		return newDeliveryPlanId;
	}
	public void setFgId(Integer fgId) {
		this.fgId = fgId;
	}
	public Integer getFgId() {
		return fgId;
	}
	public Integer getRevision() {
		return revision;
	}
	public void setRevision(Integer revision) {
		this.revision = revision;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
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
	public String getMonthName() {
		return monthName;
	}
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	public String getYearmonth() {
		return yearmonth;
	}
	public void setYearmonth(String yearmonth) {
		this.yearmonth = yearmonth;
	}
	public String getInsertFlag() {
		return insertFlag;
	}
	public void setInsertFlag(String insertFlag) {
		this.insertFlag = insertFlag;
	}
	public List<TDeliveryPlanDate> getDateList() {
		return dateList;
	}
	public void setDateList(List<TDeliveryPlanDate> dateList) {
		this.dateList = dateList;
	}
	public List<TDeliveryPlanDate> getDate3List() {
		return date3List;
	}
	public void setDate3List(List<TDeliveryPlanDate> date3List) {
		this.date3List = date3List;
	}
	public Integer getCountDate() {
		return countDate;
	}
	public void setCountDate(Integer countDate) {
		this.countDate = countDate;
	}
	public void setPlanList(List<TDeliveryPlan> planList) {
		this.planList = planList;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Integer getDeliveryQty() {
		return deliveryQty;
	}
	public void setDeliveryQty(Integer deliveryQty) {
		this.deliveryQty = deliveryQty;
	}
	public Integer getTimeHr() {
		return timeHr;
	}
	public void setTimeHr(Integer timeHr) {
		this.timeHr = timeHr;
	}
	public Integer getTimeMin() {
		return timeMin;
	}
	public void setTimeMin(Integer timeMin) {
		this.timeMin = timeMin;
	}
	public Double getSaleUnitPrice() {
		return saleUnitPrice;
	}
	public void setSaleUnitPrice(Double saleUnitPrice) {
		this.saleUnitPrice = saleUnitPrice;
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
	public Integer getPartId() {
		return partId;
	}
	public void setPartId(Integer partId) {
		this.partId = partId;
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
	public Integer getOutputCategory() {
		return outputCategory;
	}
	public void setOutputCategory(Integer outputCategory) {
		this.outputCategory = outputCategory;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public Double getUnitWeight() {
		return unitWeight;
	}
	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}
	public Integer getNormalQty() {
		return normalQty;
	}
	public void setNormalQty(Integer normalQty) {
		this.normalQty = normalQty;
	}
	public Integer getReturnQty() {
		return returnQty;
	}
	public void setReturnQty(Integer returnQty) {
		this.returnQty = returnQty;
	}
	public Integer getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(Integer orderQty) {
		this.orderQty = orderQty;
	}
	public Integer getForecastQty() {
		return forecastQty;
	}
	public void setForecastQty(Integer forecastQty) {
		this.forecastQty = forecastQty;
	}
	public Integer getRemainQty() {
		return remainQty;
	}
	public void setRemainQty(Integer remainQty) {
		this.remainQty = remainQty;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getYearmonthFr() {
		return yearmonthFr;
	}
	public void setYearmonthFr(String yearmonthFr) {
		this.yearmonthFr = yearmonthFr;
	}
	public String getYearmonthTo() {
		return yearmonthTo;
	}
	public void setYearmonthTo(String yearmonthTo) {
		this.yearmonthTo = yearmonthTo;
	}
	public List<MWip> getWipList() {
		return wipList;
	}
	public void setWipList(List<MWip> wipList) {
		this.wipList = wipList;
	}
	public List<TDeliveryPlanDate> getDate2OrderList() {
		return date2OrderList;
	}
	public void setDate2OrderList(List<TDeliveryPlanDate> date2OrderList) {
		this.date2OrderList = date2OrderList;
	}
	public List<TDeliveryPlanDate> getDate2SalesList() {
		return date2SalesList;
	}
	public void setDate2SalesList(List<TDeliveryPlanDate> date2SalesList) {
		this.date2SalesList = date2SalesList;
	}
	public List<TDeliveryPlanDate> getDate4List() {
		return date4List;
	}
	public void setDate4List(List<TDeliveryPlanDate> date4List) {
		this.date4List = date4List;
	}
	public List<TDeliveryPlanDate> getDate5List() {
		return date5List;
	}
	public void setDate5List(List<TDeliveryPlanDate> date5List) {
		this.date5List = date5List;
	}
	public Double getDiecastCost() {
		return diecastCost;
	}
	public void setDiecastCost(Double diecastCost) {
		this.diecastCost = diecastCost;
	}
	public Double getMaterialCost() {
		return materialCost;
	}
	public void setMaterialCost(Double materialCost) {
		this.materialCost = materialCost;
	}
	public Double getProcessingCost() {
		return processingCost;
	}
	public void setProcessingCost(Double processingCost) {
		this.processingCost = processingCost;
	}
	public Integer getCavQty() {
		return cavQty;
	}
	public void setCavQty(Integer cavQty) {
		this.cavQty = cavQty;
	}
	public Integer getStockControl() {
		return stockControl;
	}
	public void setStockControl(Integer stockControl) {
		this.stockControl = stockControl;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getWip() {
		return wip;
	}
	public void setWip(String wip) {
		this.wip = wip;
	}
	public String getWipName() {
		return wipName;
	}
	public void setWipName(String wipName) {
		this.wipName = wipName;
	}
	public String getOutSource() {
		return outSource;
	}
	public void setOutSource(String outSource) {
		this.outSource = outSource;
	}
	public String getWorkOrderNo() {
		return workOrderNo;
	}
	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}
	public List<TDeliveryPlan> getPlan2List() {
		return plan2List;
	}
	public void setPlan2List(List<TDeliveryPlan> plan2List) {
		this.plan2List = plan2List;
	}
	public Date getMoldUpdatedDate() {
		return moldUpdatedDate;
	}
	public void setMoldUpdatedDate(Date moldUpdatedDate) {
		this.moldUpdatedDate = moldUpdatedDate;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public Integer getCycleTimeOK() {
		return cycleTimeOK;
	}
	public void setCycleTimeOK(Integer cycleTimeOK) {
		this.cycleTimeOK = cycleTimeOK;
	}
	public Integer getMaterialUnitCost() {
		return materialUnitCost;
	}
	public void setMaterialUnitCost(Integer materialUnitCost) {
		this.materialUnitCost = materialUnitCost;
	}
	public Integer getNumberTimeMoldChange() {
		return numberTimeMoldChange;
	}
	public void setNumberTimeMoldChange(Integer numberTimeMoldChange) {
		this.numberTimeMoldChange = numberTimeMoldChange;
	}
	public Integer getInitialShotQty() {
		return initialShotQty;
	}
	public void setInitialShotQty(Integer initialShotQty) {
		this.initialShotQty = initialShotQty;
	}
	public Integer getMaxShotQty() {
		return maxShotQty;
	}
	public void setMaxShotQty(Integer maxShotQty) {
		this.maxShotQty = maxShotQty;
	}
	public String getCavNo() {
		return cavNo;
	}
	public void setCavNo(String cavNo) {
		this.cavNo = cavNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getProductRate() {
		return productRate;
	}
	public void setProductRate(String productRate) {
		this.productRate = productRate;
	}
	public Integer getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}
	public Integer getNg() {
		return ng;
	}
	public void setNg(Integer ng) {
		this.ng = ng;
	}
	public Integer getSorting() {
		return sorting;
	}
	public void setSorting(Integer sorting) {
		this.sorting = sorting;
	}
	public List<TDeliveryPlan> getPlanList() {
		return planList;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}