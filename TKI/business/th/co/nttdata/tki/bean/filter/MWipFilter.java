package th.co.nttdata.tki.bean.filter;

public class MWipFilter extends AbstractBaseFilterBean {
	private Boolean isCalc;
	private String wip;
	private String wipName;
	private Integer wipType;
	private String wipGroup;
	private Boolean accessWip;
	private Integer wipOrder;
	private String remark;
	private String wipTypeName;

	public Boolean getIsCalc() {
		return isCalc;
	}

	public void setIsCalc(Boolean isCalc) {
		this.isCalc = isCalc;
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

	public Integer getWipType() {
		return wipType;
	}

	public void setWipType(Integer wipType) {
		this.wipType = wipType;
	}

	public String getWipGroup() {
		return wipGroup;
	}

	public void setWipGroup(String wipGroup) {
		this.wipGroup = wipGroup;
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

	@Override
	public String getRemark() {
		return remark;
	}

	@Override
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getWipTypeName() {
		return wipTypeName;
	}

	public void setWipTypeName(String wipTypeName) {
		this.wipTypeName = wipTypeName;
	}

}
