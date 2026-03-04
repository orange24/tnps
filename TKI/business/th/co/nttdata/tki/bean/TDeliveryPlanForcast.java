package th.co.nttdata.tki.bean;

import java.util.Date;
import java.util.List;

public class TDeliveryPlanForcast {

	private Integer forcastId;
	private Integer deliveryPlanId;
	private Integer fgId;
	private String yearmonth;
	private Date lastUpdate;
	private Integer n1;
	private Integer n2;
	private Integer n3;

	public Integer getForcastId() {
		return forcastId;
	}
	public void setForcastId(Integer forcastId) {
		this.forcastId = forcastId;
	}
	public Integer getDeliveryPlanId() {
		return deliveryPlanId;
	}
	public void setDeliveryPlanId(Integer deliveryPlanId) {
		this.deliveryPlanId = deliveryPlanId;
	}
	public Integer getFgId() {
		return fgId;
	}
	public void setFgId(Integer fgId) {
		this.fgId = fgId;
	}
	public String getYearmonth() {
		return yearmonth;
	}
	public void setYearmonth(String yearmonth) {
		this.yearmonth = yearmonth;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public Integer getN1() {
		return n1;
	}
	public void setN1(Integer n1) {
		this.n1 = n1;
	}
	public Integer getN2() {
		return n2;
	}
	public void setN2(Integer n2) {
		this.n2 = n2;
	}
	public Integer getN3() {
		return n3;
	}
	public void setN3(Integer n3) {
		this.n3 = n3;
	}

}