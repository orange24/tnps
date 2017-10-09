package th.co.nttdata.tki.batch.bean;

import java.util.List;

public class TpicsXSect {
	private String bumo;
	private String name;
	private String bunr;
	private String busho;
	private List<TpicsXSect> xsectList;
	
	public String getBumo() {
		return bumo;
	}
	public void setBumo(String bumo) {
		this.bumo = bumo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBunr() {
		return bunr;
	}
	public void setBunr(String bunr) {
		this.bunr = bunr;
	}
	public String getBusho() {
		return busho;
	}
	public void setBusho(String busho) {
		this.busho = busho;
	}
	public List<TpicsXSect> getXsectList() {
		return xsectList;
	}
	public void setXsectList(List<TpicsXSect> xsectList) {
		this.xsectList = xsectList;
	}
}
