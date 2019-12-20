package th.co.nttdata.tki.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TWip extends AbstractBaseBean {

	private Date reportDate;
	private Date reportDateFr;
	private Date reportDateTo;
	private Integer customerId;
	private Integer month;
	private Integer partId;
	private Integer strDay;
	private Integer endDay;
	private Integer year;
	private String wip;
	private Map<String,List<TWipStock>> stockMap;

    //TwipFG
    private Map<String,List<TWipFgMaping>> fgMap;
    private List<TWipFgMaping> fgList;
    private Map<Date,List<TWipFgMaping>> fgMapKey;
    private List<TWipFgMaping> fgListKey;
    private Map<String,List<TWipFgMaping>> fgMapDay;
    private List<TWipFgMaping> fgListDay;
    private Map<String,List<TWipFgMaping>> fgMapPartDay;
    private List<TWipFgMaping> fgListPartDay;
    
	//MRDC_S19
	private Integer sorting;
	private String 	category;
	private String 	partName;
	private String 	partNo;
	private List<TWipStock> stockList;
	
	public List<TWipStock> getStockList() {
		return stockList;
	}
	public void setStockList(List<TWipStock> stockList) {
		this.stockList = stockList;
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
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public Date getReportDate() {
		return reportDate;
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
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getPartId() {
		return partId;
	}
	public void setPartId(Integer partId) {
		this.partId = partId;
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
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getWip() {
		return wip;
	}
	public void setWip(String wip) {
		this.wip = wip;
	}
	public void setStockMap(Map<String,List<TWipStock>> stocks) {
		this.stockMap = stocks;
	}
	public Map<String,List<TWipStock>> getStockMap() {
		return stockMap;
	}
	public Integer getSorting() {
		return sorting;
	}
	public void setSorting(Integer sorting) {
		this.sorting = sorting;
	}
	public Map<String,List<TWipFgMaping>> getFgMap() {
		return fgMap;
	}
	public void setFgMap(Map<String,List<TWipFgMaping>> fgMap) {
		this.fgMap = fgMap;
	}
	public List<TWipFgMaping> getFgList() {
		return fgList;
	}
	public void setFgList(List<TWipFgMaping> fgList) {
		this.fgList = fgList;
	}
	public List<TWipFgMaping> getFgListKey() {
		return fgListKey;
	}
	public void setFgListKey(List<TWipFgMaping> fgListKey) {
		this.fgListKey = fgListKey;
	}
	public Map<Date,List<TWipFgMaping>> getFgMapKey() {
		return fgMapKey;
	}
	public void setFgMapKey(Map<Date,List<TWipFgMaping>> fgMapKey) {
		this.fgMapKey = fgMapKey;
	}
	public Map<String,List<TWipFgMaping>> getFgMapDay() {
		return fgMapDay;
	}
	public void setFgMapDay(Map<String,List<TWipFgMaping>> fgMapDay) {
		this.fgMapDay = fgMapDay;
	}
	public List<TWipFgMaping> getFgListDay() {
		return fgListDay;
	}
	public void setFgListDay(List<TWipFgMaping> fgListDay) {
		this.fgListDay = fgListDay;
	}
	public Map<String,List<TWipFgMaping>> getFgMapPartDay() {
		return fgMapPartDay;
	}
	public void setFgMapPartDay(Map<String,List<TWipFgMaping>> fgMapPartDay) {
		this.fgMapPartDay = fgMapPartDay;
	}
	public List<TWipFgMaping> getFgListPartDay() {
		return fgListPartDay;
	}
	public void setFgListPartDay(List<TWipFgMaping> fgListPartDay) {
		this.fgListPartDay = fgListPartDay;
	}
}