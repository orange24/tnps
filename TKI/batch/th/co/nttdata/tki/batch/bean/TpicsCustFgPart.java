package th.co.nttdata.tki.batch.bean;

import java.util.List;

public class TpicsCustFgPart {
	private String custcode;
	private String custname;
	private String fgname;
	private String fgno;
	private String partno;
	private String partname;
	private List<TpicsCustFgPart> custFgPartList;
	
	public String getCustcode() {
		return custcode;
	}
	public void setCustcode(String custcode) {
		this.custcode = custcode;
	}
	public String getCustname() {
		return custname;
	}
	public void setCustname(String custname) {
		this.custname = custname;
	}
	public String getFgname() {
		return fgname;
	}
	public void setFgname(String fgname) {
		this.fgname = fgname;
	}
	public String getFgno() {
		return fgno;
	}
	public void setFgno(String fgno) {
		this.fgno = fgno;
	}
	public String getPartno() {
		return partno;
	}
	public void setPartno(String partno) {
		this.partno = partno;
	}
	public String getPartname() {
		return partname;
	}
	public void setPartname(String partname) {
		this.partname = partname;
	}
	public List<TpicsCustFgPart> getCustFgPartList() {
		return custFgPartList;
	}
	public void setCustFgPartList(List<TpicsCustFgPart> custFgPartList) {
		this.custFgPartList = custFgPartList;
	}
}
