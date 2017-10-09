package th.co.nttdata.tki.bean;

import java.util.List;

public class MRole extends AbstractBaseBean {

	private Boolean isLocked;
	private Integer roleId;
	private String roleDesc;
	private String roleName;	
	private List<MRole> roleList;
	private List<MultiChoice> menuList;
	private List<MultiChoice> actionList;

	public Boolean getIsLocked() {
		return isLocked;
	}
	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public List<MRole> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<MRole> roleList) {
		this.roleList = roleList;
	}
	public List<MultiChoice> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<MultiChoice> menuList) {
		this.menuList = menuList;
	}
	public List<MultiChoice> getActionList() {
		return actionList;
	}
	public void setActionList(List<MultiChoice> actionList) {
		this.actionList = actionList;
	}
}