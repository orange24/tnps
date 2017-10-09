package th.co.nttdata.tki.batch.bean;

import java.util.Date;

public class TStockExportHistory {
	private Integer exportId;
	private Date reportDate;
	private String prefix;
	private Integer fgId;
	private String fgNo;
	private String lotNo;
	private Integer qty;
	private String exportFileName;
	private Date exportDate;
	private String exportBy;

	// Condition to insert NIR_B02
	private Date lastUpdateFr;
	private Date lastUpdateTo;

	/**
	 * Gets the exportId.
	 *
	 * @return the exportId
	 */
	public Integer getExportId() {
		return exportId;
	}

	/**
	 * Changes the exportId.
	 *
	 * @param exportId - the exportId to set
	 */
	public void setExportId(Integer exportId) {
		this.exportId = exportId;
	}

	/**
	 * Gets the reportDate.
	 *
	 * @return the reportDate
	 */
	public Date getReportDate() {
		return reportDate;
	}

	/**
	 * Changes the reportDate.
	 *
	 * @param reportDate - the reportDate to set
	 */
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	/**
	 * Gets the prefix.
	 *
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * Changes the prefix.
	 *
	 * @param prefix - the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * Gets the fgId.
	 *
	 * @return the fgId
	 */
	public Integer getFgId() {
		return fgId;
	}

	/**
	 * Changes the fgId.
	 *
	 * @param fgId - the fgId to set
	 */
	public void setFgId(Integer fgId) {
		this.fgId = fgId;
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
	 * Gets the lotNo.
	 *
	 * @return the lotNo
	 */
	public String getLotNo() {
		return lotNo;
	}

	/**
	 * Changes the lotNo.
	 *
	 * @param lotNo - the lotNo to set
	 */
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	/**
	 * Gets the qty.
	 *
	 * @return the qty
	 */
	public Integer getQty() {
		return qty;
	}

	/**
	 * Changes the qty.
	 *
	 * @param qty - the qty to set
	 */
	public void setQty(Integer qty) {
		this.qty = qty;
	}

	/**
	 * Gets the exportFileName.
	 *
	 * @return the exportFileName
	 */
	public String getExportFileName() {
		return exportFileName;
	}

	/**
	 * Changes the exportFileName.
	 *
	 * @param exportFileName - the exportFileName to set
	 */
	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}

	/**
	 * Gets the exportDate.
	 *
	 * @return the exportDate
	 */
	public Date getExportDate() {
		return exportDate;
	}

	/**
	 * Changes the exportDate.
	 *
	 * @param exportDate - the exportDate to set
	 */
	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}

	/**
	 * Gets the exportBy.
	 *
	 * @return the exportBy
	 */
	public String getExportBy() {
		return exportBy;
	}

	/**
	 * Changes the exportBy.
	 *
	 * @param exportBy - the exportBy to set
	 */
	public void setExportBy(String exportBy) {
		this.exportBy = exportBy;
	}

	/**
	 * Gets the lastUpdateFr.
	 *
	 * @return the lastUpdateFr
	 */
	public Date getLastUpdateFr() {
		return lastUpdateFr;
	}

	/**
	 * Changes the lastUpdateFr.
	 *
	 * @param lastUpdateFr - the lastUpdateFr to set
	 */
	public void setLastUpdateFr(Date lastUpdateFr) {
		this.lastUpdateFr = lastUpdateFr;
	}

	/**
	 * Gets the lastUpdateTo.
	 *
	 * @return the lastUpdateTo
	 */
	public Date getLastUpdateTo() {
		return lastUpdateTo;
	}

	/**
	 * Changes the lastUpdateTo.
	 *
	 * @param lastUpdateTo - the lastUpdateTo to set
	 */
	public void setLastUpdateTo(Date lastUpdateTo) {
		this.lastUpdateTo = lastUpdateTo;
	}

}
