package th.co.nttdata.tki.bean;

import java.util.ArrayList;
import java.util.List;

public class MCustomer extends AbstractBaseBean {

	private Boolean isCustomer;
	private Integer customerId;
	private String customerCode;
	private String customerName;
	private String vendorCode;
	private Boolean isBarcodeQty;

	private List<MCustomer> customerList = new ArrayList<MCustomer>();

	public MCustomer() {
		super();
	}

	public MCustomer(String customerCode, String customerName) {
		super();
		this.customerCode = customerCode;
		this.customerName = customerName;
	}

	public MCustomer(Integer customerId, String customerCode, String customerName) {
		this.customerId = customerId;
		this.customerCode = customerCode;
	}

	public void addCustomer(MCustomer c) {
		customerList.add(c);
	}

	public void setIsCustomer(Boolean isCustomer) {
		this.isCustomer = isCustomer;
	}

	public Boolean getIsCustomer() {
		return isCustomer;
	}

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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getvendorCode() {
		return vendorCode;
	}

	public void setvendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public List<MCustomer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<MCustomer> customerList) {
		this.customerList = customerList;
	}

	public Boolean getIsBarcodeQty() {
		return isBarcodeQty;
	}

	public void setIsBarcodeQty(Boolean isBarcodeQty) {
		this.isBarcodeQty = isBarcodeQty;
	}

}