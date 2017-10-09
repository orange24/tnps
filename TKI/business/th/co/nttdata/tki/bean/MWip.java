package th.co.nttdata.tki.bean;

import java.util.Date;
import java.util.List;

public class MWip extends AbstractBaseBean {

	private Boolean isCalc;
	private Date syncDate; // add default date
	private String wip;
	private String wipName;
	private Integer wipType;
	private String wipGroup;
	private Boolean accessWip;
	private Integer wipOrder;
	private String remark;
	private Boolean isenable;

	public MWip() {
		super();
	}

	public MWip(String wip, String wipName) {
		this.wip = wip;
		this.wipName = wipName;
	}

	private String linkDB;
	private Integer wipTypeID;
	private String wipTypeName;
	private List<String> wipCode;
	private List<MWip> wipList;

	public Integer getWipTypeID() {
		return wipTypeID;
	}

	public void setWipTypeID(Integer wipTypeID) {
		this.wipTypeID = wipTypeID;
	}

	public String getWipTypeName() {
		return wipTypeName;
	}

	public void setWipTypeName(String wipTypeName) {

		this.wipTypeName = wipTypeName;
	}

	public Boolean getIsCalc() {
		return isCalc;
	}

	public void setIsCalc(Boolean isCalc) {
		this.isCalc = isCalc;
	}

	public Date getSyncDate() {
		return syncDate;
	}

	// public void setSyncDate(Date syncDate) {
	// this.syncDate = getCreateDate();
	// }
	public Integer getWipType() {
		return wipType;
	}

	public void setWipType(Integer wipType) {

		this.wipType = wipType;

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

	public Boolean getAccessWip() {
		return accessWip;
	}

	public void setAccessWip(Boolean accessWip) {
		this.accessWip = accessWip;
	}

	public Integer getWipOrder() {
		return wipOrder;
	}

	public void setWipOrder(Integer wipOrder) {
		this.wipOrder = wipOrder;
	}

	public List<MWip> getWipList() {
		return wipList;
	}

	public void setWipList(List<MWip> wipList) {
		this.wipList = wipList;
	}

	@Override
	public String getRemark() {
		return remark;
	}

	@Override
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getWipGroup() {
		return wipGroup;
	}

	public void setWipGroup(String wipGroup) {
		this.wipGroup = wipGroup;
	}

	public String getLinkDB() {
		return linkDB;
	}

	public void setLinkDB(String linkDB) {
		this.linkDB = linkDB;
	}

	public List<String> getWipCode() {
		return wipCode;
	}

	public void setWipCode(List<String> wipCode) {
		this.wipCode = wipCode;
	}

	public Boolean getIsenable() {
		return isenable;
	}

	public void setIsenable(Boolean isenable) {
		this.isenable = isenable;
	}

}
