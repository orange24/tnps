package th.co.nttdata.tki.batch.bean;

import java.util.List;

public class XBAIK {
	private Double APRICE;
	private Double INPUTDATE;
	private Double PRICE;
	private Double SOUSUUOUT;
	private Double SOUOUTKINGAKU;
	private Double TVOL;
	private Integer BID;
	private String BUNR;
	private String CCODE;
	private String CODE;
	private String CUST;
	private String FDATE;
	private String TDATE;
	private String USED;
	private List<XBAIK> xbaikList;
	
	public Double getAPRICE() {
		return APRICE;
	}
	public void setAPRICE(Double aPRICE) {
		APRICE = aPRICE;
	}
	public Double getINPUTDATE() {
		return INPUTDATE;
	}
	public void setINPUTDATE(Double iNPUTDATE) {
		INPUTDATE = iNPUTDATE;
	}
	public Double getPRICE() {
		return PRICE;
	}
	public void setPRICE(Double pRICE) {
		PRICE = pRICE;
	}
	public Double getSOUSUUOUT() {
		return SOUSUUOUT;
	}
	public void setSOUSUUOUT(Double sOUSUUOUT) {
		SOUSUUOUT = sOUSUUOUT;
	}
	public Double getSOUOUTKINGAKU() {
		return SOUOUTKINGAKU;
	}
	public void setSOUOUTKINGAKU(Double sOUOUTKINGAKU) {
		SOUOUTKINGAKU = sOUOUTKINGAKU;
	}
	public Double getTVOL() {
		return TVOL;
	}
	public void setTVOL(Double tVOL) {
		TVOL = tVOL;
	}
	public Integer getBID() {
		return BID;
	}
	public void setBID(Integer bID) {
		BID = bID;
	}
	public String getBUNR() {
		return BUNR;
	}
	public void setBUNR(String bUNR) {
		BUNR = bUNR;
	}
	public String getCCODE() {
		return CCODE;
	}
	public void setCCODE(String cCODE) {
		CCODE = cCODE;
	}
	public String getCODE() {
		return CODE;
	}
	public void setCODE(String cODE) {
		CODE = cODE;
	}
	public String getCUST() {
		return CUST;
	}
	public void setCUST(String cUST) {
		CUST = cUST;
	}
	public String getFDATE() {
		return FDATE;
	}
	public void setFDATE(String fDATE) {
		FDATE = fDATE;
	}
	public String getTDATE() {
		return TDATE;
	}
	public void setTDATE(String tDATE) {
		TDATE = tDATE;
	}
	public String getUSED() {
		return USED;
	}
	public void setUSED(String uSED) {
		USED = uSED;
	}
	public List<XBAIK> getXbaikList() {
		return xbaikList;
	}
	public void setXbaikList(List<XBAIK> xbaikList) {
		this.xbaikList = xbaikList;
	}
}
