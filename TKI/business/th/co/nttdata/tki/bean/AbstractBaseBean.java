package th.co.nttdata.tki.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

public class AbstractBaseBean {

	private Date createDate;
	private Date lastUpdate;
	private Integer pageCount = 10;
	private Integer pageNumber = 1;
	private Integer pageTotal = 1;
	private Integer[] pageDisplay = new Integer[] { 10, 20, 50, 100 };
	private String createBy = getUsername();
	private String updateBy = createBy;
	private String remark;
	private List<Message> errors = new ArrayList<Message>();
	private List<Message> infos = new ArrayList<Message>();
	private String statusFlag;

	private static final String getUsername() {
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			return "TEST";
		}
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageTotal(Integer pageTotal) {
		this.pageTotal = pageTotal;
	}

	public Integer getPageTotal() {
		return pageTotal;
	}

	public void setPageDisplay(Integer[] pageDisplay) {
		this.pageDisplay = pageDisplay;
	}

	public Integer[] getPageDisplay() {
		return pageDisplay;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

	public List<Message> getErrors() {
		return errors;
	}

	public void setErrors(List<Message> errors) {
		this.errors = errors;
	}

	public List<Message> getInfos() {
		return infos;
	}

	public void setInfos(List<Message> infos) {
		this.infos = infos;
	}

	public boolean hasErrors() {
		return errors.size() > 0;
	}

	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	public boolean isInsert() {
		return "i".equals(statusFlag);
	}

	public boolean isUpdate() {
		return "e".equals(statusFlag);
	}

	public boolean isDelete() {
		return "d".equals(statusFlag);
	}

	public void setInsert(boolean insert) {
	}

	public void setUpdate(boolean update) {
	}

	public void setDelete(boolean delete) {
	}

	public void getStatusFlagOrg() {
	}

	public void setStatusFlagOrg(String statusFlagOrg) {
	}

	public void getRowNum() {
	}

	public void setRowNum(String RowNum) {
	}

	public void getDeleteField() {
	}

	public void setDeleteField(String deleteField) {
	}

	public void getFlagOrg() {
	}

	public void setFlagOrg(String flagOrg) {
	}

}
