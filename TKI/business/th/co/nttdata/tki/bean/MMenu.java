package th.co.nttdata.tki.bean;

import java.util.List;

public class MMenu extends AbstractBaseBean {

	private Boolean enabled;
	private Integer menuParentId;
	private String  menuParentName;
	private Integer menuId;
	private Integer rank;
	private String menuName;
	private String menuUrl;
	private List<MMenu> menuList;
 
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public Integer getMenuParentId() {
		return menuParentId;
	}
	public void setMenuParentId(Integer menuParentId) {
		this.menuParentId = menuParentId;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public String getMenuParentName() {
		return menuParentName;
	}
	public void setMenuParentName(String menuParentName) {
		this.menuParentName = menuParentName;
	}
	public List<MMenu> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<MMenu> menuList) {
		this.menuList = menuList;
	}
}