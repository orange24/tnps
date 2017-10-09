package th.co.nttdata.tki.batch.bean;

public class CfgSystemConfig {
	public static final String VALUE_KEY_NIR_FINANCE_M = "NIR_FINANCE_M";
	public static final String VALUE_KEY_NIR_MAX_REC = "NIR_MAX_REC";
	public static final String VALUE_KEY_NIR_SHARE_PATH = "NIR_SHARE_PATH";
	public static final String VALUE_KEY_NIR_TMP_PATH = "NIR_TMP_PATH";

	private String configKey;
	private String configValue;
	private String configDesc;

	/**
	 * Gets the configKey.
	 *
	 * @return the configKey
	 */
	public String getConfigKey() {
		return configKey;
	}

	/**
	 * Changes the configKey.
	 *
	 * @param configKey - the configKey to set
	 */
	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	/**
	 * Gets the configValue.
	 *
	 * @return the configValue
	 */
	public String getConfigValue() {
		return configValue;
	}

	/**
	 * Changes the configValue.
	 *
	 * @param configValue - the configValue to set
	 */
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	/**
	 * Gets the configDesc.
	 *
	 * @return the configDesc
	 */
	public String getConfigDesc() {
		return configDesc;
	}

	/**
	 * Changes the configDesc.
	 *
	 * @param configDesc - the configDesc to set
	 */
	public void setConfigDesc(String configDesc) {
		this.configDesc = configDesc;
	}
}
