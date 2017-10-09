package th.co.nttdata.tki.batch.bean;

import java.util.Date;

import th.co.nttdata.tki.bean.AbstractBaseBean;

public class BatchDeliveryPlan extends AbstractBaseBean {
	private Integer fgId;
	private Date deliveryDate;
	private int productionQty;
	private Integer fgBalance;
	
	public Integer getFgId() {
		return fgId;
	}
	public void setFgId(Integer fgId) {
		this.fgId = fgId;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public int getProductionQty() {
		return productionQty;
	}
	public void setProductionQty(int productionQty) {
		this.productionQty = productionQty;
	}
	public Integer getFgBalance() {
		return fgBalance;
	}
	public void setFgBalance(Integer fgBalance) {
		this.fgBalance = fgBalance;
	}
}
