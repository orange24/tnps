package th.co.nttdata.tki.dao.impl;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.CfgSystemConfigDao;

@Repository
public class CfgSystemConfigDaoImpl extends AbstractBaseDao implements CfgSystemConfigDao {

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public Integer getNirAccountFiscalMonth() {
		return Integer.valueOf(getConfigValueByKey("NIR_FINANCE_M"));
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public Integer getNirMaxRowExportPerfile() {
		return Integer.valueOf(getConfigValueByKey("NIR_MAX_REC"));
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public String getNirSharePath() {
		return getConfigValueByKey("NIR_SHARE_PATH");
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public String getNirTempPath() {
		return getConfigValueByKey("NIR_TMP_PATH");
	}

	/**
	 * Get value configuration by key.
	 * 
	 * @param key - key to get value.
	 * @return value configuration
	 */
	private String getConfigValueByKey(String key) {
		return (String) queryForObject("cfg_system_config.getConfigValueByKey", key);
	}

}
