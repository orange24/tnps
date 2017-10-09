package th.co.nttdata.tki.bean.filter;

import java.util.ArrayList;
import java.util.List;

public class FgMasterFilter extends AbstractBaseFilterBean {

	private String fgId;
	private String fgNo;
	private String fgName;
	private String uom;
	private String snpFG;
	private String weight;
	private String price;
	private String currency;
	private String vendorFgNo;
	private String classifyBusinessId;
	private String classifyBiz;
	private String placeId;
	private String place;
	private String subBusinessId;
	private String subBusiness;
	private String isenable;
	private List<FgMasterFilter> fgMasterFilterList = new ArrayList<FgMasterFilter>();

	public String getFgId() {
		return fgId;
	}

	public void setFgId(String fgId) {
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

	public String getSnpFG() {
		return snpFG;
	}

	public void setSnpFG(String snpFG) {
		this.snpFG = snpFG;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getVendorFgNo() {
		return vendorFgNo;
	}

	public void setVendorFgNo(String vendorFgNo) {
		this.vendorFgNo = vendorFgNo;
	}

	public String getClassifyBusinessId() {
		return classifyBusinessId;
	}

	public void setClassifyBusinessId(String classifyBusinessId) {
		this.classifyBusinessId = classifyBusinessId;
	}

	public String getClassifyBiz() {
		return classifyBiz;
	}

	public void setClassifyBiz(String classifyBiz) {
		this.classifyBiz = classifyBiz;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getSubBusinessId() {
		return subBusinessId;
	}

	public void setSubBusinessId(String subBusinessId) {
		this.subBusinessId = subBusinessId;
	}

	public String getSubBusiness() {
		return subBusiness;
	}

	public void setSubBusiness(String subBusiness) {
		this.subBusiness = subBusiness;
	}

	public String getIsenable() {
		return isenable;
	}

	public void setIsenable(String isenable) {
		this.isenable = isenable;
	}

	public List<FgMasterFilter> getFgMasterFilterList() {
		return fgMasterFilterList;
	}

	public void setFgMasterFilterList(List<FgMasterFilter> fgMasterFilterList) {
		this.fgMasterFilterList = fgMasterFilterList;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
