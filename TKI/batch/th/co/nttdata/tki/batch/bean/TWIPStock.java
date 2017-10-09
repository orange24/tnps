package th.co.nttdata.tki.batch.bean;

import java.util.Date;
import java.util.List;

public class TWIPStock {
	private Date reportDate;
	private Integer adjustStock;
	private Integer customerId;
	private Integer currentStock;
	private Integer fgId;
	private Integer nextWIPQty;
	private Integer ng;
	private Integer ok;
	private Integer partId;
	private Integer pd;
	private Integer prevStock;
	private Integer qty;
	private Integer reportDay;
	private Integer wipOrder;
	private String nextWIP;
	private String wip;
	private List<TWIPStock> twipStockList;

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

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(Integer currentStock) {
		this.currentStock = currentStock;
	}

	public Integer getFgId() {
		return fgId;
	}

	public void setFgId(Integer fgId) {
		this.fgId = fgId;
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

	public Integer getReportDay() {
		return reportDay;
	}

	public void setReportDay(Integer reportDay) {
		this.reportDay = reportDay;
	}

	public Integer getWipOrder() {
		return wipOrder;
	}

	public void setWipOrder(Integer wipOrder) {
		this.wipOrder = wipOrder;
	}

	public String getNextWIP() {
		return nextWIP;
	}

	public void setNextWIP(String nextWIP) {
		this.nextWIP = nextWIP;
	}

	public String getWip() {
		return wip;
	}

	public void setWip(String wip) {
		this.wip = wip;
	}

	public List<TWIPStock> getTwipStockList() {
		return twipStockList;
	}

	public void setTwipStockList(List<TWIPStock> twipStockList) {
		this.twipStockList = twipStockList;
	}

}
