package th.co.nttdata.tki.bean.filter;

import java.util.ArrayList;
import java.util.List;

import th.co.nttdata.tki.bean.MCustomer;

public class MCustomerFilter extends AbstractBaseFilterBean {
	private String customerId;
	private String customerCode;
	private String customerName;
	private String vendorCode;
	private List<MCustomerFilter> customerList = new ArrayList<MCustomerFilter>();
	
	
	public List<MCustomerFilter> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<MCustomerFilter> customerList) {
		this.customerList = customerList;
	}


	public MCustomerFilter() {
		super();
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
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

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

}
