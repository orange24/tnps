package th.co.nttdata.tki.batch.bean;

import java.util.Date;

public class FgDetail {
	public static final String EXPORTED = "Y";
	// For update exported flag of Nirvana
	private Date lastUpdateFr;
	private Date lastUpdateTo;
	private String exported;
	private String updateBy = getUsername();

	/**
	 * Get user name.
	 * 
	 * @return user name if not get return TEST.
	 */
	private static final String getUsername() {
		return "System";
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

	/**
	 * Gets the exported.
	 *
	 * @return the exported
	 */
	public String getExported() {
		return exported;
	}

	/**
	 * Changes the exported.
	 *
	 * @param exported - the exported to set
	 */
	public void setExported(String exported) {
		this.exported = exported;
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