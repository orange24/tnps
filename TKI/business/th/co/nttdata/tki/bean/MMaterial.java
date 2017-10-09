package th.co.nttdata.tki.bean;

import java.util.List;

public class MMaterial extends AbstractBaseBean {

	private Double materialCost;
	private Integer materialId;
	private String  materialCode;
	private String  materialName;
	private List<MMaterial> materialList;
	
	public Double getMaterialCost() {
		return materialCost;
	}
	public void setMaterialCost(Double materialCost) {
		this.materialCost = materialCost;
	}
	public Integer getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public List<MMaterial> getMaterialList() {
		return materialList;
	}
	public void setMaterialList(List<MMaterial> materialList) {
		this.materialList = materialList;
	}
}