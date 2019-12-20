package th.co.nttdata.tki.bean;

import java.util.ArrayList;
import java.util.List;

public class CustomerLine extends AbstractBaseBean {

	private Integer customerId;
	private Integer customerLineId;
	private String customerLineName;
	private List<CustomerLine> customerLineList = new ArrayList<CustomerLine>();

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getCustomerLineId() {
		return customerLineId;
	}

	public void setCustomerLineId(Integer customerLineId) {
		this.customerLineId = customerLineId;
	}

	public String getCustomerLineName() {
		return customerLineName;
	}

	public void setCustomerLineName(String customerLineName) {
		this.customerLineName = customerLineName;
	}

	public List<CustomerLine> getCustomerLineList() {
		return customerLineList;
	}

	public void setCustomerLineList(List<CustomerLine> customerLineList) {
		this.customerLineList = customerLineList;
	}

}
