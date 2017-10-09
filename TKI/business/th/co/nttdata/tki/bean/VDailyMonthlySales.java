package th.co.nttdata.tki.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class VDailyMonthlySales extends AbstractBaseBean {
	private BigDecimal	nPrice;
	private BigDecimal	nSaleUnitPrice;
	private Date 		dReportDate;
	private Date 		reportDateFr;
	private Date 		reportDateTo;
	private Integer 	maxRecord;
	private Integer		nSalesQty;
	private Integer		sCustomerId;
	private String		sCareerSheetNo;
	private String		sCategory;
	private String 		sCode;
	private String 		sCustomerDeptCode;
	private String 		sCustomerCode;
	private String 		sCustomerName;	
	private String 		sPartName;
	private String 		sPartNo;
	private Map<String,VDailyMonthlySales> vDailyMonthlySalesMap;
	private List<VDailyMonthlySales> vDailyMonthlySalesList;
	
	//MRDC_S06
	private Date 		dDeliveryDate;
	private Double 		nMaterialCost;
	private Double 		nProcessingCost;
	private Double 		nUnitWeight;
	private Double 		nOrderReceiptionUnitPrice;
	private Double 		nOrderReceiptionPrice;
	private Integer 	nDeliveryQty;
	private Integer 	nOrderReceiptionQty;
	private String 		id;
	private String 		sMaterial;
	private String 		sOrderReceiptType;
	
	//MRDC_S07
	private String 		sReportTypeName;
	
	public BigDecimal getnPrice() {
		return nPrice;
	}
	public void setnPrice(BigDecimal nPrice) {
		this.nPrice = nPrice;
	}
	public BigDecimal getnSaleUnitPrice() {
		return nSaleUnitPrice;
	}
	public void setnSaleUnitPrice(BigDecimal nSaleUnitPrice) {
		this.nSaleUnitPrice = nSaleUnitPrice;
	}
	public Date getdReportDate() {
		return dReportDate;
	}
	public void setdReportDate(Date dReportDate) {
		this.dReportDate = dReportDate;
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
	public Integer getMaxRecord() {
		return maxRecord;
	}
	public void setMaxRecord(Integer maxRecord) {
		this.maxRecord = maxRecord;
	}
	public Integer getnSalesQty() {
		return nSalesQty;
	}
	public void setnSalesQty(Integer nSalesQty) {
		this.nSalesQty = nSalesQty;
	}
	public Integer getsCustomerId() {
		return sCustomerId;
	}
	public void setsCustomerId(Integer sCustomerId) {
		this.sCustomerId = sCustomerId;
	}
	public String getsCareerSheetNo() {
		return sCareerSheetNo;
	}
	public void setsCareerSheetNo(String sCareerSheetNo) {
		this.sCareerSheetNo = sCareerSheetNo;
	}
	public String getsCategory() {
		return sCategory;
	}
	public void setsCategory(String sCategory) {
		this.sCategory = sCategory;
	}
	public String getsCode() {
		return sCode;
	}
	public void setsCode(String sCode) {
		this.sCode = sCode;
	}
	public String getsCustomerDeptCode() {
		return sCustomerDeptCode;
	}
	public void setsCustomerDeptCode(String sCustomerDeptCode) {
		this.sCustomerDeptCode = sCustomerDeptCode;
	}
	public String getsCustomerCode() {
		return sCustomerCode;
	}
	public void setsCustomerCode(String sCustomerCode) {
		this.sCustomerCode = sCustomerCode;
	}
	public String getsCustomerName() {
		return sCustomerName;
	}
	public void setsCustomerName(String sCustomerName) {
		this.sCustomerName = sCustomerName;
	}
	public String getsPartName() {
		return sPartName;
	}
	public void setsPartName(String sPartName) {
		this.sPartName = sPartName;
	}
	public String getsPartNo() {
		return sPartNo;
	}
	public void setsPartNo(String sPartNo) {
		this.sPartNo = sPartNo;
	}
	public Map<String, VDailyMonthlySales> getvDailyMonthlySalesMap() {
		return vDailyMonthlySalesMap;
	}
	public void setvDailyMonthlySalesMap(
			Map<String, VDailyMonthlySales> vDailyMonthlySalesMap) {
		this.vDailyMonthlySalesMap = vDailyMonthlySalesMap;
	}
	public List<VDailyMonthlySales> getvDailyMonthlySalesList() {
		return vDailyMonthlySalesList;
	}
	public void setvDailyMonthlySalesList(
			List<VDailyMonthlySales> vDailyMonthlySalesList) {
		this.vDailyMonthlySalesList = vDailyMonthlySalesList;
	}
	public Date getdDeliveryDate() {
		return dDeliveryDate;
	}
	public void setdDeliveryDate(Date dDeliveryDate) {
		this.dDeliveryDate = dDeliveryDate;
	}
	public Double getnMaterialCost() {
		return nMaterialCost;
	}
	public void setnMaterialCost(Double nMaterialCost) {
		this.nMaterialCost = nMaterialCost;
	}
	public Double getnProcessingCost() {
		return nProcessingCost;
	}
	public void setnProcessingCost(Double nProcessingCost) {
		this.nProcessingCost = nProcessingCost;
	}
	public Double getnUnitWeight() {
		return nUnitWeight;
	}
	public void setnUnitWeight(Double nUnitWeight) {
		this.nUnitWeight = nUnitWeight;
	}
	public Integer getnDeliveryQty() {
		return nDeliveryQty;
	}
	public void setnDeliveryQty(Integer nDeliveryQty) {
		this.nDeliveryQty = nDeliveryQty;
	}
	public Integer getnOrderReceiptionQty() {
		return nOrderReceiptionQty;
	}
	public void setnOrderReceiptionQty(Integer nOrderReceiptionQty) {
		this.nOrderReceiptionQty = nOrderReceiptionQty;
	}
	public Double getnOrderReceiptionUnitPrice() {
		return nOrderReceiptionUnitPrice;
	}
	public void setnOrderReceiptionUnitPrice(Double nOrderReceiptionUnitPrice) {
		this.nOrderReceiptionUnitPrice = nOrderReceiptionUnitPrice;
	}
	public Double getnOrderReceiptionPrice() {
		return nOrderReceiptionPrice;
	}
	public void setnOrderReceiptionPrice(Double nOrderReceiptionPrice) {
		this.nOrderReceiptionPrice = nOrderReceiptionPrice;
	}
	public String getsOrderReceiptType() {
		return sOrderReceiptType;
	}
	public void setsOrderReceiptType(String sOrderReceiptType) {
		this.sOrderReceiptType = sOrderReceiptType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getsMaterial() {
		return sMaterial;
	}
	public void setsMaterial(String sMaterial) {
		this.sMaterial = sMaterial;
	}
	public String getsReportTypeName() {
		return sReportTypeName;
	}
	public void setsReportTypeName(String sReportTypeName) {
		this.sReportTypeName = sReportTypeName;
	}
}
