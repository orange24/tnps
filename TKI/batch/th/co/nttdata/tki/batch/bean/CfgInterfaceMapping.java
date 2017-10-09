package th.co.nttdata.tki.batch.bean;

public class CfgInterfaceMapping {
	public static final String MAPPING_CD_FG_MASTER = "FG_MASTER";

	private Integer mappingId;
	private String mappingCd;
	private Integer fieldSeq;
	private String fieldName;
	private String fieldType;
	private Integer fieldStartPosition;
	private Integer fieldNumberLength;
	private Integer fieldDecimalLength;
	private String fieldDateFormat;
	private Integer fieldTotalLength;
	private String fieldRemark;
	private boolean isMandatory;
	private boolean isEnable;

	/**
	 * Gets the mappingId.
	 *
	 * @return the mappingId
	 */
	public Integer getMappingId() {
		return mappingId;
	}

	/**
	 * Changes the mappingId.
	 *
	 * @param mappingId - the mappingId to set
	 */
	public void setMappingId(Integer mappingId) {
		this.mappingId = mappingId;
	}

	/**
	 * Gets the mappingCd.
	 *
	 * @return the mappingCd
	 */
	public String getMappingCd() {
		return mappingCd;
	}

	/**
	 * Changes the mappingCd.
	 *
	 * @param mappingCd - the mappingCd to set
	 */
	public void setMappingCd(String mappingCd) {
		this.mappingCd = mappingCd;
	}

	/**
	 * Gets the fieldSeq.
	 *
	 * @return the fieldSeq
	 */
	public Integer getFieldSeq() {
		return fieldSeq;
	}

	/**
	 * Changes the fieldSeq.
	 *
	 * @param fieldSeq - the fieldSeq to set
	 */
	public void setFieldSeq(Integer fieldSeq) {
		this.fieldSeq = fieldSeq;
	}

	/**
	 * Gets the fieldName.
	 *
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * Changes the fieldName.
	 *
	 * @param fieldName - the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * Gets the fieldType.
	 *
	 * @return the fieldType
	 */
	public String getFieldType() {
		return fieldType;
	}

	/**
	 * Changes the fieldType.
	 *
	 * @param fieldType - the fieldType to set
	 */
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	/**
	 * Gets the fieldStartPosition.
	 *
	 * @return the fieldStartPosition
	 */
	public Integer getFieldStartPosition() {
		return fieldStartPosition;
	}

	/**
	 * Changes the fieldStartPosition.
	 *
	 * @param fieldStartPosition - the fieldStartPosition to set
	 */
	public void setFieldStartPosition(Integer fieldStartPosition) {
		this.fieldStartPosition = fieldStartPosition;
	}

	/**
	 * Gets the fieldNumberLength.
	 *
	 * @return the fieldNumberLength
	 */
	public Integer getFieldNumberLength() {
		return fieldNumberLength;
	}

	/**
	 * Changes the fieldNumberLength.
	 *
	 * @param fieldNumberLength - the fieldNumberLength to set
	 */
	public void setFieldNumberLength(Integer fieldNumberLength) {
		this.fieldNumberLength = fieldNumberLength;
	}

	/**
	 * Gets the fieldDecimalLength.
	 *
	 * @return the fieldDecimalLength
	 */
	public Integer getFieldDecimalLength() {
		return fieldDecimalLength;
	}

	/**
	 * Changes the fieldDecimalLength.
	 *
	 * @param fieldDecimalLength - the fieldDecimalLength to set
	 */
	public void setFieldDecimalLength(Integer fieldDecimalLength) {
		this.fieldDecimalLength = fieldDecimalLength;
	}

	/**
	 * Gets the fieldDateFormat.
	 *
	 * @return the fieldDateFormat
	 */
	public String getFieldDateFormat() {
		return fieldDateFormat;
	}

	/**
	 * Changes the fieldDateFormat.
	 *
	 * @param fieldDateFormat - the fieldDateFormat to set
	 */
	public void setFieldDateFormat(String fieldDateFormat) {
		this.fieldDateFormat = fieldDateFormat;
	}

	/**
	 * Gets the fieldTotalLength.
	 *
	 * @return the fieldTotalLength
	 */
	public Integer getFieldTotalLength() {
		return fieldTotalLength;
	}

	/**
	 * Changes the fieldTotalLength.
	 *
	 * @param fieldTotalLength - the fieldTotalLength to set
	 */
	public void setFieldTotalLength(Integer fieldTotalLength) {
		this.fieldTotalLength = fieldTotalLength;
	}

	/**
	 * Gets the fieldRemark.
	 *
	 * @return the fieldRemark
	 */
	public String getFieldRemark() {
		return fieldRemark;
	}

	/**
	 * Changes the fieldRemark.
	 *
	 * @param fieldRemark - the fieldRemark to set
	 */
	public void setFieldRemark(String fieldRemark) {
		this.fieldRemark = fieldRemark;
	}

	/**
	 * Gets the isMandatory.
	 *
	 * @return the isMandatory
	 */
	public boolean isMandatory() {
		return isMandatory;
	}

	/**
	 * Changes the isMandatory.
	 *
	 * @param isMandatory - the isMandatory to set
	 */
	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	/**
	 * Gets the isEnable.
	 *
	 * @return the isEnable
	 */
	public boolean isEnable() {
		return isEnable;
	}

	/**
	 * Changes the isEnable.
	 *
	 * @param isEnable - the isEnable to set
	 */
	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

}
