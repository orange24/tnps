package th.co.nttdata.tki.bean;


public class MPartWip extends AbstractBaseBean {

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("wip=").append(wip.getWip()).append("|");
		return b.toString();
	}
	
	private Integer partId;
	private MWip    wip = new MWip();
	private Integer tpicsOrder;
	
	public Integer getPartId() {
		return partId;
	}
	public void setPartId(Integer partId) {
		this.partId = partId;
	}
	public MWip getWip() {
		return wip;
	}
	public void setWip(MWip wip) {
		this.wip = wip;
	}
	public Integer getTpicsOrder() {
		return tpicsOrder;
	}
	public void setTpicsOrder(Integer tpicsOrder) {
		this.tpicsOrder = tpicsOrder;
	}
}