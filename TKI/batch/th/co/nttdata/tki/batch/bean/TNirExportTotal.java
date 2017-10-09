package th.co.nttdata.tki.batch.bean;

import java.util.Date;

public class TNirExportTotal {
	private Integer month;
	private Integer exportTotal;
	private Date lastUpdate;
	private String updateBy;

	/**
	 * Gets the month.
	 *
	 * @return the month
	 */
	public Integer getMonth() {
		return month;
	}

	/**
	 * Changes the month.
	 *
	 * @param month - the month to set
	 */
	public void setMonth(Integer month) {
		this.month = month;
	}

	/**
	 * Gets the exportTotal.
	 *
	 * @return the exportTotal
	 */
	public Integer getExportTotal() {
		return exportTotal;
	}

	/**
	 * Changes the exportTotal.
	 *
	 * @param exportTotal - the exportTotal to set
	 */
	public void setExportTotal(Integer exportTotal) {
		this.exportTotal = exportTotal;
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

}
