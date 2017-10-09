package th.co.nttdata.tki.batch.bean;

import java.util.List;

public class TpicsCbom {
	private Double cbom_usedqty;
	private Integer cbom_oyak;
	private Integer cbom_level;
	private String cbom_fg;
	private String cbom_wc;
	private String cbom_item;
	private String cbom_process;
	private List<TpicsCbom> cbomList;
	
	public Double getCbom_usedqty() {
		return cbom_usedqty;
	}
	public void setCbom_usedqty(Double cbom_usedqty) {
		this.cbom_usedqty = cbom_usedqty;
	}
	public Integer getCbom_oyak() {
		return cbom_oyak;
	}
	public void setCbom_oyak(Integer cbom_oyak) {
		this.cbom_oyak = cbom_oyak;
	}
	public Integer getCbom_level() {
		return cbom_level;
	}
	public void setCbom_level(Integer cbom_level) {
		this.cbom_level = cbom_level;
	}
	public String getCbom_fg() {
		return cbom_fg;
	}
	public void setCbom_fg(String cbom_fg) {
		this.cbom_fg = cbom_fg;
	}
	public String getCbom_wc() {
		return cbom_wc;
	}
	public void setCbom_wc(String cbom_wc) {
		this.cbom_wc = cbom_wc;
	}
	public String getCbom_item() {
		return cbom_item;
	}
	public void setCbom_item(String cbom_item) {
		this.cbom_item = cbom_item;
	}
	public String getCbom_process() {
		return cbom_process;
	}
	public void setCbom_process(String cbom_process) {
		this.cbom_process = cbom_process;
	}
	public List<TpicsCbom> getCbomList() {
		return cbomList;
	}
	public void setCbomList(List<TpicsCbom> cbomList) {
		this.cbomList = cbomList;
	}
}
