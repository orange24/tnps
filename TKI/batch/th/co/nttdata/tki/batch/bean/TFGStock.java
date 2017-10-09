package th.co.nttdata.tki.batch.bean;

import java.util.Date;
import java.util.List;

public class TFGStock {
	private Date    reportDate;
	private Integer customerId;
	private Integer fgAdjust;
	private Integer fgBalance;
	private Integer fgId;
	private Integer fgIn;
	private Integer fgOut;
	private Integer fgStock;
	private List<TFGStock> tfgStockList;
	
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
	public List<TFGStock> getTfgStockList() {
		return tfgStockList;
	}
	public void setTfgStockList(List<TFGStock> tfgStockList) {
		this.tfgStockList = tfgStockList;
	}
}
