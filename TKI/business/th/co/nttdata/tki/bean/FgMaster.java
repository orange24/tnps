package th.co.nttdata.tki.bean;

import java.util.ArrayList;
import java.util.List;

public class FgMaster extends AbstractBaseBean {

	private Integer customerId;
	private String customerCode;
	private Integer fgId;
	private String fgNo;
	private String fgName;
	private String uom;
	private Integer snpFG;
	private Double weight;
	private Double price;
	private String vendorFgNo;
	private Integer classifyBusinessId;
	private String classifyBiz;
	private Integer placeId;
	private String place;
	private Integer subBusinessId;
	private String subBusiness;
	private Boolean isenable;
	private String description;
	private String uomDesc;
	private String currency;
	private String currencyDesc;
	private List<FgMaster> fgMasterList = new ArrayList<FgMaster>();

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

	public Integer getFgId() {
		return fgId;
	}

	public void setFgId(Integer fgId) {
		this.fgId = fgId;
	}

	public String getFgNo() {
		return fgNo;
	}

	public void setFgNo(String fgNo) {
		this.fgNo = fgNo;
	}

	public String getFgName() {
		return fgName;
	}

	public void setFgName(String fgName) {
		this.fgName = fgName;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public Integer getSnpFG() {
		return snpFG;
	}

	public void setSnpFG(Integer snpFG) {
		this.snpFG = snpFG;
	}

	public String getVendorFgNo() {
		return vendorFgNo;
	}

	public void setVendorFgNo(String vendorFgNo) {
		this.vendorFgNo = vendorFgNo;
	}

	public Integer getClassifyBusinessId() {
		return classifyBusinessId;
	}

	public void setClassifyBusinessId(Integer classifyBusinessId) {
		this.classifyBusinessId = classifyBusinessId;
	}

	public String getClassifyBiz() {
		return classifyBiz;
	}

	public void setClassifyBiz(String classifyBiz) {
		this.classifyBiz = classifyBiz;
	}

	public Integer getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Integer placeId) {
		this.placeId = placeId;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Integer getSubBusinessId() {
		return subBusinessId;
	}

	public void setSubBusinessId(Integer subBusinessId) {
		this.subBusinessId = subBusinessId;
	}

	public String getSubBusiness() {
		return subBusiness;
	}

	public void setSubBusiness(String subBusiness) {
		this.subBusiness = subBusiness;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Boolean getIsenable() {
		return isenable;
	}

	public void setIsenable(Boolean isenable) {
		this.isenable = isenable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<FgMaster> getFgMasterList() {
		return fgMasterList;
	}

	public void setFgMasterList(List<FgMaster> fgMasterList) {
		this.fgMasterList = fgMasterList;
	}

	public String getUomDesc() {
		return uomDesc;
	}

	public void setUomDesc(String uomDesc) {
		this.uomDesc = uomDesc;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyDesc() {
		return currencyDesc;
	}

	public void setCurrencyDesc(String currencyDesc) {
		this.currencyDesc = currencyDesc;
	}

}
