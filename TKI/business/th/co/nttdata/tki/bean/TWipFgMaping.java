package th.co.nttdata.tki.bean;

import java.util.Date;

public class TWipFgMaping extends AbstractBaseBean {
    private Date reportDate;
    private Integer customerid;
    private String customer;
    private Integer partid;
    private String partno;
    private String partname;
    private String wip;
    private String wipname;
    private Integer wiporder;
    private Integer reportDay;
    private Integer stockAfterQty;
    private Integer stockAdjustQty;
    private Integer currentStockQty;
    private Integer preWipPdQty;
    private Integer totals;
    private Integer countProcess;
    private Integer fgBl;
    private Integer sumWip;
    private Integer sumPending;
    private Integer sumTotal;
    private Integer grandTotal;
    
    public Integer getCurrentStockQty() {
        return currentStockQty;
    }
    public void setCurrentStockQty(Integer currentStockQty) {
        this.currentStockQty = currentStockQty;
    }
    public void setStockAdjustQty(Integer stockAdjustQty) {
	this.stockAdjustQty = stockAdjustQty;
    }
    public Integer getStockAdjustQty() {
	return stockAdjustQty;
    }    
    public void setGrandTotal(Integer grandTotal) {
	this.grandTotal = grandTotal;
    }
    public Integer getGrandTotal() {
	return grandTotal;
    }    
    public void setSumPending(Integer sumPending) {
	this.sumPending = sumPending;
    }
    public Integer getSumPending() {
	return sumPending;
    }    
    public void setSumTotal(Integer sumTotal) {
	this.sumTotal = sumTotal;
    }    
    public Integer getSumTotal() {
	return sumTotal;
    }    
    public void setSumWip(Integer sumWip) {
	this.sumWip = sumWip;
    }
    public Integer getSumWip() {
	return sumWip;
    }
    public void setFgBl(Integer fgBl) {
	this.fgBl = fgBl;
    }
    public Integer getFgBl() {
	return fgBl;
    }
    public void setCountProcess(Integer countProcess) {
	this.countProcess = countProcess;
    }
    public Integer getCountProcess() {
	return countProcess;
    }
    public void setReportDate(Date reportDate) {
	this.reportDate = reportDate;
    }
    public Date getReportDate() {
	return reportDate;
    }
    public void setCustomerid(Integer customerid) {
	this.customerid = customerid;
    }
    public Integer getCustomerid() {
	return customerid;
    }
    public void setCustomer(String customer) {
	this.customer = customer;
    }
    public String getCustomer() {
	return customer;
    }
    public void setPartid(Integer partid) {
	this.partid = partid;
    }
    public Integer getPartid() {
	return partid;
    }
    public void setPartno(String partno) {
	this.partno = partno;
    }
    public String getPartno() {
	return partno;
    }
    public void setPartname(String partname) {
	this.partname = partname;
    }
    public String getPartname() {
	return partname;
    }
    public void setWip(String wip) {
	this.wip = wip;
    }
    public String getWip() {
	return wip;
    }
    public void setWipname(String wipname) {
	this.wipname = wipname;
    }
    public String getWipname() {
	return wipname;
    }
    public void setWiporder(Integer wiporder) {
	this.wiporder = wiporder;
    }
    public Integer getWiporder() {
	return wiporder;
    }
    public void setReportDay(Integer reportDay) {
	this.reportDay = reportDay;
    }
    public Integer getReportDay() {
	return reportDay;
    }
    public void setStockAfterQty(Integer stockAfterQty) {
	this.stockAfterQty = stockAfterQty;
    }
    public Integer getStockAfterQty() {
	return stockAfterQty;
    }
    public void setPreWipPdQty(Integer preWipPdQty) {
	this.preWipPdQty = preWipPdQty;
    }
    public Integer getPreWipPdQty() {
	return preWipPdQty;
    }
    public void setTotals(Integer totals) {
	this.totals = totals;
    }
    public Integer getTotals() {
	return totals;
    }    
}
