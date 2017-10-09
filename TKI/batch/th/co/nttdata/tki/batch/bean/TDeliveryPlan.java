package th.co.nttdata.tki.batch.bean;

import java.util.Date;

public class TDeliveryPlan {
	
	private Date 	deliveryDate;
	private Integer fgStock;
	private Integer fgId;
	private Integer productionQty;
	
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}	
	public Integer getFgStock() {
		return fgStock;
	}
	public void setFgStock(Integer fgStock) {
		this.fgStock = fgStock;
	}
	public Integer getFgId() {
		return fgId;
	}
	public void setFgId(Integer fgId) {
		this.fgId = fgId;
	}
	public Integer getProductionQty() {
		return productionQty;
	}
	public void setProductionQty(Integer productionQty) {
		this.productionQty = productionQty;
	}

}
