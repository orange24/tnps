package th.co.nttdata.tki.batch.bean;

import java.util.List;

public class TpicsXCust {
	private String cust;
	private String name;
	private List<TpicsXCust> xcustList;
	
	public String getCust() {
		return cust;
	}
	public void setCust(String cust) {
		this.cust = cust;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<TpicsXCust> getXcustList() {
		return xcustList;
	}
	public void setXcustList(List<TpicsXCust> xcustList) {
		this.xcustList = xcustList;
	}
}
