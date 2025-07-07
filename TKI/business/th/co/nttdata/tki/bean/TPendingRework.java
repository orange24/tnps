package th.co.nttdata.tki.bean;

public class TPendingRework extends AbstractBaseBean {
	private Integer pdReworkId;
	private Integer pdAdjustId;
	private String wip;
	private String wipName;
	private Integer reworkQty;
	
	public Integer getPdReworkId() {
		return pdReworkId;
	}
	public void setPdReworkId(Integer pdReworkId) {
		this.pdReworkId = pdReworkId;
	}
	public Integer getPdAdjustId() {
		return pdAdjustId;
	}
	public void setPdAdjustId(Integer pdAdjustId) {
		this.pdAdjustId = pdAdjustId;
	}
	public String getWip() {
		return wip;
	}
	public void setWip(String wip) {
		this.wip = wip;
	}
	public void setWipName(String wipName) {
		this.wipName = wipName;
	}
	public String getWipName() {
		return wipName;
	}
	public Integer getReworkQty() {
		return reworkQty;
	}
	public void setReworkQty(Integer reworkQty) {
		this.reworkQty = reworkQty;
	}

	public String toString() {
		String result = "{";
		result += "pdReworkId = " + pdReworkId + ",";
		result += "pdAdjustId = " + pdAdjustId + ",";
		result += "wip = " + wip + ",";
		result += "wipName = " + wipName + ",";
		result += "reworkQty = " + reworkQty;
		result += "}";
		return result;
	}
}
