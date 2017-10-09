package th.co.nttdata.tki.bean;

import java.util.List;

public class MReason extends AbstractBaseBean {

	private Integer parentReasonId;	
	private Integer reasonId;
	private Integer reasonType;
	private Integer wipType;
	private Integer[] reasonTypeCode;	
	private String  description;
	private String  reasonCode;
	private String  reasonName;
	private String  reasonTypeName;
	private String  wip;
	private String  wipName;
	private String  parentReasonCode;
	private String  parentReasonName;
	private List<MReason> reasonList;

	public MReason() {
		super();
	}
	
	public MReason(Integer reasonId, String reasonName) {
		this.reasonId = reasonId;
		this.reasonName = reasonName;
	}
	public Integer getParentReasonId() {
		return parentReasonId;
	}
	public void setParentReasonId(Integer parentReasonId) {
		this.parentReasonId = parentReasonId;
	}
	public Integer getReasonId() {
		return reasonId;
	}
	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	}
	public Integer getReasonType() {
		return reasonType;
	}
	public void setReasonType(Integer reasonType) {
		this.reasonType = reasonType;
	}
	public Integer getWipType() {
		return wipType;
	}
	public void setWipType(Integer wipType) {
		this.wipType = wipType;
	}
	public void setReasonTypeCode(Integer[] reasonTypeCode) {
		this.reasonTypeCode = reasonTypeCode;
	}
	public Integer[] getReasonTypeCode() {
		return reasonTypeCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getReasonName() {
		return reasonName;
	}
	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}
	public void setReasonTypeName(String reasonTypeName) {
		this.reasonTypeName = reasonTypeName;
	}
	public String getReasonTypeName() {
		return reasonTypeName;
	}
	public String getWip() {
		return wip;
	}
	public void setWip(String wip) {
		this.wip = wip;
	}
	public String getWipName() {
		return wipName;
	}
	public void setWipName(String wipName) {
		this.wipName = wipName;
	}
	public void setReasonList(List<MReason> reasonList) {
		this.reasonList = reasonList;
	}
	public List<MReason> getReasonList() {
		return reasonList;
	}
	public String getParentReasonCode() {
		return parentReasonCode;
	}
	public void setParentReasonCode(String parentReasonCode) {
		this.parentReasonCode = parentReasonCode;
	}
	public void setParentReasonName(String parentReasonName) {
		this.parentReasonName = parentReasonName;
	}
	public String getParentReasonName() {
		return parentReasonName;
	}
}