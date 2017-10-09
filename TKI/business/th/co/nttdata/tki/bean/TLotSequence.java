package th.co.nttdata.tki.bean;

import java.util.ArrayList;
import java.util.List;

public class TLotSequence extends AbstractBaseBean {
	private Integer tagId;
	private Integer seq;
	private String lotSeqNo;
	private Integer lotSeqQty;
	private Integer flagPrint;
	private Integer printStatus;
	private Integer isReadOnly;
	private List<TLotSequence> lotSequenceList = new ArrayList<TLotSequence>();

	public void addLotSequence(TLotSequence lotSequence) {
		this.lotSequenceList.add(lotSequence);
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getLotSeqNo() {
		return lotSeqNo;
	}

	public void setLotSeqNo(String lotSeqNo) {
		this.lotSeqNo = lotSeqNo;
	}

	public Integer getLotSeqQty() {
		return lotSeqQty;
	}

	public void setLotSeqQty(Integer lotSeqQty) {
		this.lotSeqQty = lotSeqQty;
	}

	public Integer getFlagPrint() {
		return flagPrint;
	}

	public void setFlagPrint(Integer flagPrint) {
		this.flagPrint = flagPrint;
	}

	public Integer getIsReadOnly() {
		return isReadOnly;
	}

	public void setIsReadOnly(Integer isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	public List<TLotSequence> getLotSequenceList() {
		return lotSequenceList;
	}

	public void setLotSequenceList(List<TLotSequence> lotSequenceList) {
		this.lotSequenceList = lotSequenceList;
	}

	public Integer getPrintStatus() {
		return printStatus;
	}

	public void setPrintStatus(Integer printStatus) {
		this.printStatus = printStatus;
	}

}