package th.co.nttdata.tki.batch.bean;

import java.util.Date;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

public class MNirvanaSyncMasterBatch {
	public static final String IS_ENABLE_N = "N";
	public static final String IS_ENABLE_Y = "Y";
	public static final String SYNC_STATUS_NEW = "N";
	public static final String SYNC_STATUS_ERROR = "E";
	public static final String TRANS_TYPE_INSERT = "I";
	public static final String TRANS_TYPE_DELETE = "D";
	public static final String TRANS_TYPE_UPDATE = "U";
	private String currency;
	private String customerCode;
	private String customerName;
	private String dataCd;
	private Integer dataNo;
	private String errDesc;
	private String fgName;
	private String fgNo;
	private Double price;
	private Date syncDate;
	private Integer syncId;
	private String syncStatus;
	private String transType;
	private String uomId;
	private Double weight;
	private String isEnable;
	private Date createDate;
	private Date lastUpdate;
	private String createBy = getUsername();
	private String updateBy = createBy;

	// Criteria NIR_S01.
	private Date syncDateFrom;
	private Date syncDateTo;
	private List<MNirvanaSyncMasterBatch> mNirvanaSyncMasterList;

	/**
	 * Get user name.
	 * 
	 * @return user name login if not get return TEST
	 */
	private static final String getUsername() {
//		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			return "System";
//		}
//		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	/**
	 * Gets the currency.
	 *
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * Changes the currency.
	 *
	 * @param currency - the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * Gets the customerCode.
	 *
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * Changes the customerCode.
	 *
	 * @param customerCode - the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * Gets the customerName.
	 *
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * Changes the customerName.
	 *
	 * @param customerName - the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * Gets the dataCd.
	 *
	 * @return the dataCd
	 */
	public String getDataCd() {
		return dataCd;
	}

	/**
	 * Changes the dataCd.
	 *
	 * @param dataCd - the dataCd to set
	 */
	public void setDataCd(String dataCd) {
		this.dataCd = dataCd;
	}

	/**
	 * Gets the dataNo.
	 *
	 * @return the dataNo
	 */
	public Integer getDataNo() {
		return dataNo;
	}

	/**
	 * Changes the dataNo.
	 *
	 * @param dataNo - the dataNo to set
	 */
	public void setDataNo(Integer dataNo) {
		this.dataNo = dataNo;
	}

	/**
	 * Gets the errDesc.
	 *
	 * @return the errDesc
	 */
	public String getErrDesc() {
		return errDesc;
	}

	/**
	 * Changes the errDesc.
	 *
	 * @param errDesc - the errDesc to set
	 */
	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}

	/**
	 * Gets the fgName.
	 *
	 * @return the fgName
	 */
	public String getFgName() {
		return fgName;
	}

	/**
	 * Changes the fgName.
	 *
	 * @param fgName - the fgName to set
	 */
	public void setFgName(String fgName) {
		this.fgName = fgName;
	}

	/**
	 * Gets the fgNo.
	 *
	 * @return the fgNo
	 */
	public String getFgNo() {
		return fgNo;
	}

	/**
	 * Changes the fgNo.
	 *
	 * @param fgNo - the fgNo to set
	 */
	public void setFgNo(String fgNo) {
		this.fgNo = fgNo;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * Changes the price.
	 *
	 * @param price - the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * Gets the syncDate.
	 *
	 * @return the syncDate
	 */
	public Date getSyncDate() {
		return syncDate;
	}

	/**
	 * Changes the syncDate.
	 *
	 * @param syncDate - the syncDate to set
	 */
	public void setSyncDate(Date syncDate) {
		this.syncDate = syncDate;
	}

	/**
	 * Gets the syncId.
	 *
	 * @return the syncId
	 */
	public Integer getSyncId() {
		return syncId;
	}

	/**
	 * Changes the syncId.
	 *
	 * @param syncId - the syncId to set
	 */
	public void setSyncId(Integer syncId) {
		this.syncId = syncId;
	}

	/**
	 * Gets the syncStatus.
	 *
	 * @return the syncStatus
	 */
	public String getSyncStatus() {
		return syncStatus;
	}

	/**
	 * Changes the syncStatus.
	 *
	 * @param syncStatus - the syncStatus to set
	 */
	public void setSyncStatus(String syncStatus) {
		this.syncStatus = syncStatus;
	}

	/**
	 * Gets the transType.
	 *
	 * @return the transType
	 */
	public String getTransType() {
		return transType;
	}

	/**
	 * Changes the transType.
	 *
	 * @param transType - the transType to set
	 */
	public void setTransType(String transType) {
		this.transType = transType;
	}

	/**
	 * Gets the uomId.
	 *
	 * @return the uomId
	 */
	public String getUomId() {
		return uomId;
	}

	/**
	 * Changes the uomId.
	 *
	 * @param uomId - the uomId to set
	 */
	public void setUomId(String uomId) {
		this.uomId = uomId;
	}

	/**
	 * Gets the weight.
	 *
	 * @return the weight
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * Changes the weight.
	 *
	 * @param weight - the weight to set
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}

	/**
	 * Gets the isEnable.
	 *
	 * @return the isEnable
	 */
	public String getIsEnable() {
		return isEnable;
	}

	/**
	 * Changes the isEnable.
	 *
	 * @param isEnable - the isEnable to set
	 */
	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	/**
	 * Gets the createDate.
	 *
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Changes the createDate.
	 *
	 * @param createDate - the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the lastUpdate.
	 *
	 * @return the lastUpdate
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * Changes the lastUpdate.
	 *
	 * @param lastUpdate - the lastUpdate to set
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * Gets the createBy.
	 *
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * Changes the createBy.
	 *
	 * @param createBy - the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * Gets the updateBy.
	 *
	 * @return the updateBy
	 */
	public String getUpdateBy() {
		return updateBy;
	}

	/**
	 * Changes the updateBy.
	 *
	 * @param updateBy - the updateBy to set
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * Gets the syncDateFrom.
	 *
	 * @return the syncDateFrom
	 */
	public Date getSyncDateFrom() {
		return syncDateFrom;
	}

	/**
	 * Changes the syncDateFrom.
	 *
	 * @param syncDateFrom - the syncDateFrom to set
	 */
	public void setSyncDateFrom(Date syncDateFrom) {
		this.syncDateFrom = syncDateFrom;
	}

	/**
	 * Gets the syncDateTo.
	 *
	 * @return the syncDateTo
	 */
	public Date getSyncDateTo() {
		return syncDateTo;
	}

	/**
	 * Changes the syncDateTo.
	 *
	 * @param syncDateTo - the syncDateTo to set
	 */
	public void setSyncDateTo(Date syncDateTo) {
		this.syncDateTo = syncDateTo;
	}

	/**
	 * Gets the mNirvanaSyncMasterList.
	 *
	 * @return the mNirvanaSyncMasterList
	 */
	public List<MNirvanaSyncMasterBatch> getMNirvanaSyncMasterList() {
		return mNirvanaSyncMasterList;
	}

	/**
	 * Changes the mNirvanaSyncMasterList.
	 *
	 * @param mNirvanaSyncMasterList - the mNirvanaSyncMasterList to set
	 */
	public void setMNirvanaSyncMasterList(List<MNirvanaSyncMasterBatch> mNirvanaSyncMasterList) {
		this.mNirvanaSyncMasterList = mNirvanaSyncMasterList;
	}

}
