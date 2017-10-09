package th.co.nttdata.tki.bean;

import java.util.Date;
import java.util.List;

public class TDeliveryPlanDate {

	private Date deliveryDate;
	private Date lastUpdate;
	private Integer balanceDeliveryQty;
	private Integer balanceOrderQty;
	private Integer custReqQty;
	private Integer deliveryDay;
	private Integer deliveryPlanId;
	private Integer deliveryQtyBack;
	private Integer deliveryQtyNormal;
	private Integer deliveryQtyTotal;
	private Integer detailPlanId;
	private String fgName;
	private String fgNo;
	private String updateBy;
	private Integer fgId;
	private Integer fgOut;
	private Integer forCastQty;
	private Integer productionQty;
	private Integer tkiCommitQty;
	private String reason;
	private List<TDeliveryPlanTime> timeList;
	
	// MRDC_S14
	private Double price;
	private Integer qtyResidual;
	private String partName;
	
	// MRDC_S11
	private Double amount;
	private Integer qty;
	private Integer month;
	private Integer year;
	
	// MRDC_R18
	private Integer deadlineQty;
	private Integer orderQty;
	private Integer salesQty;
	private Integer ng;
	private Integer ok;
	private Integer startStockQty;
	private Integer endStockQty;
	private Integer stockInQty;
	private Integer stockOutQty;
	private String wip;
	private String wipName;
	
	// MRDC_R032
	private Double unitWeight;
	private Integer id;
	private Integer customerId;
	private Integer partId;
	private Integer cavQty;
	private Integer lotQty;
	private Integer snp;
	private Integer reasondigit;
	private Integer totalQty;
	private String category;
	private String customerCode;
	private String customerName;
	private String materialName;
	private String partNo;
	private String reasonCode;
	private String reasonName;
	
	// MRDC_R15
	private Double cycleTimeOK;
	private Double cycleTimeTotal;
	private Double diecastCost;
	private Double materialCost;
	private Double materialUnitCost;
	private Double operationTimeSec;
	private Double processingCost;
	private Double ngCost;
	private Double sellingPrice;
	private Integer seq;
	private String ngRatio;
	private String ngYieldRatio;
	
	// MRDC_R20
	private Double yieldPercent;
	private Double st;
	private Integer custreqNoYield;
	private Integer commitNoYield;
	private Integer forecastNoYield;
	private Integer custreqYield;
	private Integer commitYield;
	private Integer forecastYield;
	private Integer qty1;
	private Integer qty2;
	private Integer qty3;
	private Integer qty4;
	private Integer qty5;
	private Integer qty6;
	private Integer stock;
	private Integer wipOrder;
	private String stTimeNoYield;
	private String stTimeYield;
	private String yearMonth1;
	private String yearMonth2;
	private String yearMonth3;
	private String yearMonth4;
	private String yearMonth5;
	private String yearMonth6;
	private String machineName;

	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Integer getBalanceDeliveryQty() {
		return balanceDeliveryQty;
	}
	public void setBalanceDeliveryQty(Integer balanceDeliveryQty) {
		this.balanceDeliveryQty = balanceDeliveryQty;
	}
	public Integer getBalanceOrderQty() {
		return balanceOrderQty;
	}
	public void setBalanceOrderQty(Integer balanceOrderQty) {
		this.balanceOrderQty = balanceOrderQty;
	}
	public Integer getCustReqQty() {
		return custReqQty;
	}
	public void setCustReqQty(Integer custReqQty) {
		this.custReqQty = custReqQty;
	}
	public void setDeliveryDay(Integer deliveryDay) {
		this.deliveryDay = deliveryDay;
	}
	public Integer getDeliveryDay() {
		return deliveryDay;
	}
	public Integer getDeliveryPlanId() {
		return deliveryPlanId;
	}
	public void setDeliveryPlanId(Integer deliveryPlanId) {
		this.deliveryPlanId = deliveryPlanId;
	}
	public Integer getDeliveryQtyBack() {
		return deliveryQtyBack;
	}
	public void setDeliveryQtyBack(Integer deliveryQtyBack) {
		this.deliveryQtyBack = deliveryQtyBack;
	}
	public Integer getDeliveryQtyNormal() {
		return deliveryQtyNormal;
	}
	public void setDeliveryQtyNormal(Integer deliveryQtyNormal) {
		this.deliveryQtyNormal = deliveryQtyNormal;
	}
	public Integer getDeliveryQtyTotal() {
		return deliveryQtyTotal;
	}
	public void setDeliveryQtyTotal(Integer deliveryQtyTotal) {
		this.deliveryQtyTotal = deliveryQtyTotal;
	}
	public Integer getDetailPlanId() {
		return detailPlanId;
	}
	public void setDetailPlanId(Integer detailPlanId) {
		this.detailPlanId = detailPlanId;
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
	public Integer getFgId() {
		return fgId;
	}
	public void setFgId(Integer fgId) {
		this.fgId = fgId;
	}
	public void setFgOut(Integer fgOut) {
		this.fgOut = fgOut;
	}
	public Integer getFgOut() {
		return fgOut;
	}
	public Integer getForCastQty() {
		return forCastQty;
	}
	public void setForCastQty(Integer forCastQty) {
		this.forCastQty = forCastQty;
	}
	public Integer getProductionQty() {
		return productionQty;
	}
	public void setProductionQty(Integer productionQty) {
		this.productionQty = productionQty;
	}
	public Integer getTkiCommitQty() {
		return tkiCommitQty;
	}
	public void setTkiCommitQty(Integer tkiCommitQty) {
		this.tkiCommitQty = tkiCommitQty;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public List<TDeliveryPlanTime> getTimeList() {
		return timeList;
	}
	public void setTimeList(List<TDeliveryPlanTime> timeList) {
		this.timeList = timeList;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Integer getQtyResidual() {
		return qtyResidual;
	}
	public void setQtyResidual(Integer qtyResidual) {
		this.qtyResidual = qtyResidual;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
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
	public Integer getDeadlineQty() {
		return deadlineQty;
	}
	public void setDeadlineQty(Integer deadlineQty) {
		this.deadlineQty = deadlineQty;
	}
	public Integer getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(Integer orderQty) {
		this.orderQty = orderQty;
	}
	public Integer getSalesQty() {
		return salesQty;
	}
	public void setSalesQty(Integer salesQty) {
		this.salesQty = salesQty;
	}
	public Integer getNg() {
		return ng;
	}
	public void setNg(Integer ng) {
		this.ng = ng;
	}
	public Integer getOk() {
		return ok;
	}
	public void setOk(Integer ok) {
		this.ok = ok;
	}
	public Integer getStartStockQty() {
		return startStockQty;
	}
	public void setStartStockQty(Integer startStockQty) {
		this.startStockQty = startStockQty;
	}
	public Integer getEndStockQty() {
		return endStockQty;
	}
	public void setEndStockQty(Integer endStockQty) {
		this.endStockQty = endStockQty;
	}
	public Integer getStockInQty() {
		return stockInQty;
	}
	public void setStockInQty(Integer stockInQty) {
		this.stockInQty = stockInQty;
	}
	public Integer getStockOutQty() {
		return stockOutQty;
	}
	public void setStockOutQty(Integer stockOutQty) {
		this.stockOutQty = stockOutQty;
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
	public Double getUnitWeight() {
		return unitWeight;
	}
	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getCavQty() {
		return cavQty;
	}
	public void setCavQty(Integer cavQty) {
		this.cavQty = cavQty;
	}
	public Integer getLotQty() {
		return lotQty;
	}
	public void setLotQty(Integer lotQty) {
		this.lotQty = lotQty;
	}
	public Integer getSnp() {
		return snp;
	}
	public void setSnp(Integer snp) {
		this.snp = snp;
	}
	public Integer getReasondigit() {
		return reasondigit;
	}
	public void setReasondigit(Integer reasondigit) {
		this.reasondigit = reasondigit;
	}
	public Integer getTotalQty() {
		return totalQty;
	}
	public void setTotalQty(Integer totalQty) {
		this.totalQty = totalQty;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
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
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getReasonName() {
		return reasonName;
	}
	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}
	public Double getCycleTimeOK() {
		return cycleTimeOK;
	}
	public void setCycleTimeOK(Double cycleTimeOK) {
		this.cycleTimeOK = cycleTimeOK;
	}
	public Double getCycleTimeTotal() {
		return cycleTimeTotal;
	}
	public void setCycleTimeTotal(Double cycleTimeTotal) {
		this.cycleTimeTotal = cycleTimeTotal;
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
	public Double getMaterialUnitCost() {
		return materialUnitCost;
	}
	public void setMaterialUnitCost(Double materialUnitCost) {
		this.materialUnitCost = materialUnitCost;
	}
	public Double getOperationTimeSec() {
		return operationTimeSec;
	}
	public void setOperationTimeSec(Double operationTimeSec) {
		this.operationTimeSec = operationTimeSec;
	}
	public Double getProcessingCost() {
		return processingCost;
	}
	public void setProcessingCost(Double processingCost) {
		this.processingCost = processingCost;
	}
	public Double getNgCost() {
		return ngCost;
	}
	public void setNgCost(Double ngCost) {
		this.ngCost = ngCost;
	}
	public Double getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getNgRatio() {
		return ngRatio;
	}
	public void setNgRatio(String ngRatio) {
		this.ngRatio = ngRatio;
	}
	public String getNgYieldRatio() {
		return ngYieldRatio;
	}
	public void setNgYieldRatio(String ngYieldRatio) {
		this.ngYieldRatio = ngYieldRatio;
	}
	public Double getYieldPercent() {
		return yieldPercent;
	}
	public void setYieldPercent(Double yieldPercent) {
		this.yieldPercent = yieldPercent;
	}
	public Double getSt() {
		return st;
	}
	public void setSt(Double st) {
		this.st = st;
	}
	public Integer getCommitNoYield() {
		return commitNoYield;
	}
	public void setCommitNoYield(Integer commitNoYield) {
		this.commitNoYield = commitNoYield;
	}
	public Integer getForecastNoYield() {
		return forecastNoYield;
	}
	public void setForecastNoYield(Integer forecastNoYield) {
		this.forecastNoYield = forecastNoYield;
	}
	public Integer getCommitYield() {
		return commitYield;
	}
	public void setCommitYield(Integer commitYield) {
		this.commitYield = commitYield;
	}
	public Integer getForecastYield() {
		return forecastYield;
	}
	public void setForecastYield(Integer forecastYield) {
		this.forecastYield = forecastYield;
	}
	public Integer getQty1() {
		return qty1;
	}
	public void setQty1(Integer qty1) {
		this.qty1 = qty1;
	}
	public Integer getQty2() {
		return qty2;
	}
	public void setQty2(Integer qty2) {
		this.qty2 = qty2;
	}
	public Integer getQty3() {
		return qty3;
	}
	public void setQty3(Integer qty3) {
		this.qty3 = qty3;
	}
	public Integer getQty4() {
		return qty4;
	}
	public void setQty4(Integer qty4) {
		this.qty4 = qty4;
	}
	public Integer getQty5() {
		return qty5;
	}
	public void setQty5(Integer qty5) {
		this.qty5 = qty5;
	}
	public Integer getQty6() {
		return qty6;
	}
	public void setQty6(Integer qty6) {
		this.qty6 = qty6;
	}
	public Integer getWipOrder() {
		return wipOrder;
	}
	public void setWipOrder(Integer wipOrder) {
		this.wipOrder = wipOrder;
	}
	public String getYearMonth1() {
		return yearMonth1;
	}
	public void setYearMonth1(String yearMonth1) {
		this.yearMonth1 = yearMonth1;
	}
	public String getYearMonth2() {
		return yearMonth2;
	}
	public void setYearMonth2(String yearMonth2) {
		this.yearMonth2 = yearMonth2;
	}
	public String getYearMonth3() {
		return yearMonth3;
	}
	public void setYearMonth3(String yearMonth3) {
		this.yearMonth3 = yearMonth3;
	}
	public String getYearMonth4() {
		return yearMonth4;
	}
	public void setYearMonth4(String yearMonth4) {
		this.yearMonth4 = yearMonth4;
	}
	public String getYearMonth5() {
		return yearMonth5;
	}
	public void setYearMonth5(String yearMonth5) {
		this.yearMonth5 = yearMonth5;
	}
	public String getYearMonth6() {
		return yearMonth6;
	}
	public void setYearMonth6(String yearMonth6) {
		this.yearMonth6 = yearMonth6;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public String getStTimeNoYield() {
		return stTimeNoYield;
	}
	public void setStTimeNoYield(String stTimeNoYield) {
		this.stTimeNoYield = stTimeNoYield;
	}
	public String getStTimeYield() {
		return stTimeYield;
	}
	public void setStTimeYield(String stTimeYield) {
		this.stTimeYield = stTimeYield;
	}
	public Integer getCustreqNoYield() {
		return custreqNoYield;
	}
	public void setCustreqNoYield(Integer custreqNoYield) {
		this.custreqNoYield = custreqNoYield;
	}
	public Integer getCustreqYield() {
		return custreqYield;
	}
	public void setCustreqYield(Integer custreqYield) {
		this.custreqYield = custreqYield;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
}