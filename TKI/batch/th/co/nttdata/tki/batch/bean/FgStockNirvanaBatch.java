package th.co.nttdata.tki.batch.bean;

import java.util.Date;
import java.util.List;

public class FgStockNirvanaBatch {
	private String prefix;
	private String transId;
	private String divisionId;
	private String project;
	private Date transDate;
	private String internalReference;
	private String externalReference;
	private Integer year;
	private Integer period;
	private String transactionId;
	private String whsId;
	private String itemId;
	private String uomId;
	private Integer quantity;
	private Integer amount;
	private String lotNo;
	private String binId;
	private String reference1;
	private String reference2;
	private String source;
	private String documentNo;
	private Integer fgIn;

	// Criteria NIR_R01.
	private List<FgStockNirvanaBatch> fgStockNirvanaList;
	private Integer fromRecord;
	private Integer toRecord;
	// Criteria NIR_R01.
	private Date lastUpdateFr;
	private Date lastUpdateTo;

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
	 * Gets the transId.
	 *
	 * @return the transId
	 */
	public String getTransId() {
		return transId;
	}

	/**
	 * Changes the transId.
	 *
	 * @param transId - the transId to set
	 */
	public void setTransId(String transId) {
		this.transId = transId;
	}

	/**
	 * Gets the divisionId.
	 *
	 * @return the divisionId
	 */
	public String getDivisionId() {
		return divisionId;
	}

	/**
	 * Changes the divisionId.
	 *
	 * @param divisionId - the divisionId to set
	 */
	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}

	/**
	 * Gets the project.
	 *
	 * @return the project
	 */
	public String getProject() {
		return project;
	}

	/**
	 * Changes the project.
	 *
	 * @param project - the project to set
	 */
	public void setProject(String project) {
		this.project = project;
	}

	/**
	 * Gets the transDate.
	 *
	 * @return the transDate
	 */
	public Date getTransDate() {
		return transDate;
	}

	/**
	 * Changes the transDate.
	 *
	 * @param transDate - the transDate to set
	 */
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	/**
	 * Gets the internalReference.
	 *
	 * @return the internalReference
	 */
	public String getInternalReference() {
		return internalReference;
	}

	/**
	 * Changes the internalReference.
	 *
	 * @param internalReference - the internalReference to set
	 */
	public void setInternalReference(String internalReference) {
		this.internalReference = internalReference;
	}

	/**
	 * Gets the externalReference.
	 *
	 * @return the externalReference
	 */
	public String getExternalReference() {
		return externalReference;
	}

	/**
	 * Changes the externalReference.
	 *
	 * @param externalReference - the externalReference to set
	 */
	public void setExternalReference(String externalReference) {
		this.externalReference = externalReference;
	}

	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}

	/**
	 * Changes the year.
	 *
	 * @param year - the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}

	/**
	 * Gets the period.
	 *
	 * @return the period
	 */
	public Integer getPeriod() {
		return period;
	}

	/**
	 * Changes the period.
	 *
	 * @param period - the period to set
	 */
	public void setPeriod(Integer period) {
		this.period = period;
	}

	/**
	 * Gets the transactionId.
	 *
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * Changes the transactionId.
	 *
	 * @param transactionId - the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * Gets the whsId.
	 *
	 * @return the whsId
	 */
	public String getWhsId() {
		return whsId;
	}

	/**
	 * Changes the whsId.
	 *
	 * @param whsId - the whsId to set
	 */
	public void setWhsId(String whsId) {
		this.whsId = whsId;
	}

	/**
	 * Gets the itemId.
	 *
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * Changes the itemId.
	 *
	 * @param itemId - the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
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
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * Changes the quantity.
	 *
	 * @param quantity - the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public Integer getAmount() {
		return amount;
	}

	/**
	 * Changes the amount.
	 *
	 * @param amount - the amount to set
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
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
	 * Gets the binId.
	 *
	 * @return the binId
	 */
	public String getBinId() {
		return binId;
	}

	/**
	 * Changes the binId.
	 *
	 * @param binId - the binId to set
	 */
	public void setBinId(String binId) {
		this.binId = binId;
	}

	/**
	 * Gets the reference1.
	 *
	 * @return the reference1
	 */
	public String getReference1() {
		return reference1;
	}

	/**
	 * Changes the reference1.
	 *
	 * @param reference1 - the reference1 to set
	 */
	public void setReference1(String reference1) {
		this.reference1 = reference1;
	}

	/**
	 * Gets the reference2.
	 *
	 * @return the reference2
	 */
	public String getReference2() {
		return reference2;
	}

	/**
	 * Changes the reference2.
	 *
	 * @param reference2 - the reference2 to set
	 */
	public void setReference2(String reference2) {
		this.reference2 = reference2;
	}

	/**
	 * Gets the source.
	 *
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Changes the source.
	 *
	 * @param source - the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * Gets the documentNo.
	 *
	 * @return the documentNo
	 */
	public String getDocumentNo() {
		return documentNo;
	}

	/**
	 * Changes the documentNo.
	 *
	 * @param documentNo - the documentNo to set
	 */
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	/**
	 * Gets the fgIn.
	 *
	 * @return the fgIn
	 */
	public Integer getFgIn() {
		return fgIn;
	}

	/**
	 * Changes the fgIn.
	 *
	 * @param fgIn - the fgIn to set
	 */
	public void setFgIn(Integer fgIn) {
		this.fgIn = fgIn;
	}

	/**
	 * Gets the fgStockNirvanaList.
	 *
	 * @return the fgStockNirvanaList
	 */
	public List<FgStockNirvanaBatch> getFgStockNirvanaList() {
		return fgStockNirvanaList;
	}

	/**
	 * Changes the fgStockNirvanaList.
	 *
	 * @param fgStockNirvanaList - the fgStockNirvanaList to set
	 */
	public void setFgStockNirvanaList(List<FgStockNirvanaBatch> fgStockNirvanaList) {
		this.fgStockNirvanaList = fgStockNirvanaList;
	}

	/**
	 * Gets the fromRecord.
	 *
	 * @return the fromRecord
	 */
	public Integer getFromRecord() {
		return fromRecord;
	}

	/**
	 * Changes the fromRecord.
	 *
	 * @param fromRecord - the fromRecord to set
	 */
	public void setFromRecord(Integer fromRecord) {
		this.fromRecord = fromRecord;
	}

	/**
	 * Gets the toRecord.
	 *
	 * @return the toRecord
	 */
	public Integer getToRecord() {
		return toRecord;
	}

	/**
	 * Changes the toRecord.
	 *
	 * @param toRecord - the toRecord to set
	 */
	public void setToRecord(Integer toRecord) {
		this.toRecord = toRecord;
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
