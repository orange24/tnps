package th.co.nttdata.tki.batch.bean;

import java.util.Date;
import java.util.List;

public class TpicsClotseqno {
	private Date 	createdDate;
	private Integer qty;
	private String 	WH;
	private String 	WHFlag;
	private String 	lotNo;
	private String 	seqNo;
	private List<TpicsClotseqno> clotseqnoList;
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public String getWH() {
		return WH;
	}
	public void setWH(String wH) {
		WH = wH;
	}
	public String getWHFlag() {
		return WHFlag;
	}
	public void setWHFlag(String wHFlag) {
		WHFlag = wHFlag;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public List<TpicsClotseqno> getClotseqnoList() {
		return clotseqnoList;
	}
	public void setClotseqnoList(List<TpicsClotseqno> clotseqnoList) {
		this.clotseqnoList = clotseqnoList;
	}
	
}
