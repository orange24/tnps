package th.co.nttdata.tki.batch.bean;

import java.util.List;

public class TpicsWorkorder {
	private Double lotqty;
	private Double woqty;
	private String lotdate;
	private String startlot;
	private String endlot;
	private String partno;
	private String updatedate;
	private String wono;
	private List<TpicsWorkorder> workorderList;
	
	public Double getLotqty() {
		return lotqty;
	}
	public void setLotqty(Double lotqty) {
		this.lotqty = lotqty;
	}
	public Double getWoqty() {
		return woqty;
	}
	public void setWoqty(Double woqty) {
		this.woqty = woqty;
	}
	public String getLotdate() {
		return lotdate;
	}
	public void setLotdate(String lotdate) {
		this.lotdate = lotdate;
	}
	public String getStartlot() {
		return startlot;
	}
	public void setStartlot(String startlot) {
		this.startlot = startlot;
	}
	public String getEndlot() {
		return endlot;
	}
	public void setEndlot(String endlot) {
		this.endlot = endlot;
	}
	public String getPartno() {
		return partno;
	}
	public void setPartno(String partno) {
		this.partno = partno;
	}
	public String getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}
	public String getWono() {
		return wono;
	}
	public void setWono(String wono) {
		this.wono = wono;
	}
	public List<TpicsWorkorder> getWorkorderList() {
		return workorderList;
	}
	public void setWorkorderList(List<TpicsWorkorder> workorderList) {
		this.workorderList = workorderList;
	}
	
}
