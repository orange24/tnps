package th.co.nttdata.tki.bean;


public class TDeliveryPlanTime extends AbstractBaseBean {

	private Integer deliveryQty;
	private Integer detailPlanId;
	private Integer timeHr;
	private Integer timeMin;

	public Integer getDeliveryQty() {
		return deliveryQty;
	}
	public void setDeliveryQty(Integer deliveryQty) {
		this.deliveryQty = deliveryQty;
	}
	public Integer getDetailPlanId() {
		return detailPlanId;
	}
	public void setDetailPlanId(Integer detailPlanId) {
		this.detailPlanId = detailPlanId;
	}
	public Integer getTimeHr() {
		return timeHr;
	}
	public void setTimeHr(Integer timeHr) {
		this.timeHr = timeHr;
	}
	public Integer getTimeMin() {
		return timeMin;
	}
	public void setTimeMin(Integer timeMin) {
		this.timeMin = timeMin;
	}
}