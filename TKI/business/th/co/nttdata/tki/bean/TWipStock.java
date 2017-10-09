package th.co.nttdata.tki.bean;

import java.util.Date;

public class TWipStock extends AbstractBaseBean {

	private Date reportDate;
	private Integer adjustStock;
	private Integer customerId;
	private Integer currentStock;
	private Integer nextWIPQty;
	private Integer ng;
	private Integer ok;
	private Integer partId;
	private Integer pd;
	private Integer prevStock;
	private Integer qty;
	private Integer reportDay;
	private Integer wipOrder;
	private String wip;
	private String wipName;
	
	//MRDC_S19
	private Double inventoryUnitPrice;
	private Double nSaleUnitPrice;
	private Double stockValue;
	private String 	fgUnitPrice;
	private String 	fgStockValue;
	private String 	saleUnitPrice;
	private String 	wipStockValue;
	private String 	wipUnitPrice;
	private Integer carriedBalance;
	private Integer stockBalance;
	private Integer inQty;
	private Integer outQty;
	private String 	customer;
	private String 	category;
	private String 	fgNo;
	private String 	partName;
	private String 	partNo;

	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public Integer getAdjustStock() {
		return adjustStock;
	}
	public void setAdjustStock(Integer adjustStock) {
		this.adjustStock = adjustStock;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public Integer getCurrentStock() {
		return currentStock;
	}
	public void setCurrentStock(Integer currentStock) {
		this.currentStock = currentStock;
	}
	public Integer getNextWIPQty() {
		return nextWIPQty;
	}
	public void setNextWIPQty(Integer nextWIPQty) {
		this.nextWIPQty = nextWIPQty;
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
	public Integer getPartId() {
		return partId;
	}
	public void setPartId(Integer partId) {
		this.partId = partId;
	}
	public Integer getPd() {
		return pd;
	}
	public void setPd(Integer pd) {
		this.pd = pd;
	}
	public Integer getPrevStock() {
		return prevStock;
	}
	public void setPrevStock(Integer prevStock) {
		this.prevStock = prevStock;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public void setReportDay(Integer reportDay) {
		this.reportDay = reportDay;
	}
	public Integer getReportDay() {
		return reportDay;
	}
	public void setWipOrder(Integer wipOrder) {
		this.wipOrder = wipOrder;
	}
	public Integer getWipOrder() {
		return wipOrder;
	}
	public String getWip() {
		return wip;
	}
	public void setWip(String wip) {
		this.wip = wip;
	}
	public void setWipName(String wipName) {
		this.wipName = wipName;
	}
	public Double getInventoryUnitPrice() {
		return inventoryUnitPrice;
	}
	public void setInventoryUnitPrice(Double inventoryUnitPrice) {
		this.inventoryUnitPrice = inventoryUnitPrice;
	}
	public Double getnSaleUnitPrice() {
		return nSaleUnitPrice;
	}
	public void setnSaleUnitPrice(Double nSaleUnitPrice) {
		this.nSaleUnitPrice = nSaleUnitPrice;
	}
	public Double getStockValue() {
		return stockValue;
	}
	public void setStockValue(Double stockValue) {
		this.stockValue = stockValue;
	}
	public String getWipName() {
		return wipName;
	}
	public Integer getCarriedBalance() {
		return carriedBalance;
	}
	public void setCarriedBalance(Integer carriedBalance) {
		this.carriedBalance = carriedBalance;
	}
	public Integer getStockBalance() {
		return stockBalance;
	}
	public void setStockBalance(Integer stockBalance) {
		this.stockBalance = stockBalance;
	}
	public Integer getInQty() {
		return inQty;
	}
	public void setInQty(Integer inQty) {
		this.inQty = inQty;
	}
	public Integer getOutQty() {
		return outQty;
	}
	public void setOutQty(Integer outQty) {
		this.outQty = outQty;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getFgNo() {
		return fgNo;
	}
	public void setFgNo(String fgNo) {
		this.fgNo = fgNo;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
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
	public String getFgUnitPrice() {
		return fgUnitPrice;
	}
	public void setFgUnitPrice(String fgUnitPrice) {
		this.fgUnitPrice = fgUnitPrice;
	}
	public String getFgStockValue() {
		return fgStockValue;
	}
	public void setFgStockValue(String fgStockValue) {
		this.fgStockValue = fgStockValue;
	}
	public String getSaleUnitPrice() {
		return saleUnitPrice;
	}
	public void setSaleUnitPrice(String saleUnitPrice) {
		this.saleUnitPrice = saleUnitPrice;
	}
	public String getWipStockValue() {
		return wipStockValue;
	}
	public void setWipStockValue(String wipStockValue) {
		this.wipStockValue = wipStockValue;
	}
	public String getWipUnitPrice() {
		return wipUnitPrice;
	}
	public void setWipUnitPrice(String wipUnitPrice) {
		this.wipUnitPrice = wipUnitPrice;
	}
}