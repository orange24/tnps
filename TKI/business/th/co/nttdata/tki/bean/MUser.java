package th.co.nttdata.tki.bean;

import java.util.List;

public class MUser extends AbstractBaseBean {

	private Boolean enabled;
	private Boolean isLocked;
	private Integer roleId;
	private String roleName;
	private Integer status;
	private String email;
	private String fullName;
	private String oldPswrd;
	private String password;
	private String staffCode;
	private String userName;
	private String wip;
	private String wipName;
	private List<MWip> accessWip;	
	private List<MUser> userList;
	private List<MCustomer> custList;

	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public void setOldPswrd(String oldPswrd) {
		this.oldPswrd = oldPswrd;
	}
	public String getOldPswrd() {
		return oldPswrd;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getWip() {
		return wip;
	}
	public void setWip(String wip) {
		this.wip = wip;
	}
	public List<MUser> getUserList() {
		return userList;
	}
	public void setUserList(List<MUser> userList) {
		this.userList = userList;
	}
	public List<MWip> getAccessWip() {
		return accessWip;
	}
	public void setAccessWip(List<MWip> accessWip) {
		this.accessWip = accessWip;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getWipName() {
		return wipName;
	}
	public void setWipName(String wipName) {
		this.wipName = wipName;
	}
	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}
	public Boolean getIsLocked() {
		return isLocked;
	}
	public void setCustList(List<MCustomer> custList) {
		this.custList = custList;
	}
	public List<MCustomer> getCustList() {
		return custList;
	}
}
