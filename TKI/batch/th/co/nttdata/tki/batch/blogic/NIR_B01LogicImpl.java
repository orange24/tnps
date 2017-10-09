package th.co.nttdata.tki.batch.blogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import th.co.nttdata.batch.BatchLogicImpl;
import th.co.nttdata.tki.batch.bean.CfgInterfaceMapping;
import th.co.nttdata.tki.batch.bean.MNirvanaSyncMasterBatch;
import th.co.nttdata.tki.batch.bean.SsDataExch3;

import com.ibatis.sqlmap.client.SqlMapClient;

@Service("NIR_B01")
public class NIR_B01LogicImpl extends BatchLogicImpl {

	private static final String BATCH_CODE = "NIR_B01";
	private static final String BATCH_NAME = "16. Nirvana Sync – master data";

	private static final String ERROR_DUPLICATE = " is duplicate.";
	private static final String ERROR_WRONG_FORMAT = " has wrong format.";
	private static final String ERROR_MANDATORY = " is a mandatory.";
	private static final String ERROR_NOT_APPLICABLE = " is not applicable.";
	private static final String ERROR_NOT_EXIST = " does not exist.";
	private static final String ERROR_NOT_NUMBER_FORMAT = " is not number format.";
	private static final String ERROR_PROHIBITED = " is prohibited.";
	private static final String ERROR_TOTAL_LENGTH_INCORRECT = "Total length of data is incorrect.";

	@Autowired
	@Qualifier("sqlsvrMapClient")
	private SqlMapClient sqlMap;

	@Autowired
	private NirvanaService nirvanaService;

	@SuppressWarnings("unchecked")
	@Override
	public void processing() throws Exception {
		log.info("NIR_B01LogicImpl is running.");
		List<CfgInterfaceMapping> mappingList = sqlMap.queryForList("cfg_interface_mapping_batch.selectByMappingCd",
				CfgInterfaceMapping.MAPPING_CD_FG_MASTER);
		List<SsDataExch3> dataList = sqlMap.queryForList("Ss_dataexch3_batch.selectByRcvStaCd",
				SsDataExch3.RCV_STA_CD_1);
		List<MNirvanaSyncMasterBatch> mNirvanaSyncMasterList = transformToNirvanaSyncMaster(mappingList, dataList);

		// Insert data to m_nirvana_sync_master_batch
		log.info("m_nirvana_sync_master_batch.insert is running.");
		sqlMap.startBatch();
		for (MNirvanaSyncMasterBatch mNirvanaSyncMaster : mNirvanaSyncMasterList) {
			sqlMap.insert("m_nirvana_sync_master_batch.insert", mNirvanaSyncMaster);
		}
		sqlMap.executeBatch();

		// Update rcvStaCd after insert data to m_nirvana_sync_master_batch success
		log.info("Ss_dataexch3_batch.updateStatusCode is running.");
		sqlMap.startBatch();
		for (SsDataExch3 data : dataList) {
			data.setRcvStaCd(SsDataExch3.RCV_STA_CD_2);
			sqlMap.update("Ss_dataexch3_batch.updateStatusCode", data);
		}
		sqlMap.executeBatch();

//		log.info("Merge data to FG and customer.");
//		nirvanaService.mergeMasterData();

		// update m_batch_control in case success
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 0);
		param.put("runby", getExecutedBy());
		sqlMap.update("m_batch_control.updateMBatchControl", param);
		log.info("-----End NIR_B01-----");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void processingNoTx() throws Exception {
		log.info("Merge data to FG and customer.");
		nirvanaService.mergeMasterData();
	}

	/**
	 * transform data sysn. from Nirvana.
	 * 
	 * @param mappingList - list of mapping to split data.
	 * @param dataList - list of data sysn. from nirvana.
	 * @return list of object MNirvanaSyncMaster
	 * @throws SQLException - if error occur.
	 */
	private List<MNirvanaSyncMasterBatch> transformToNirvanaSyncMaster(List<CfgInterfaceMapping> mappingList,
			List<SsDataExch3> dataList) throws SQLException {
		Date transDate = new Date();
		List<MNirvanaSyncMasterBatch> mNirvanaSyncMasterList = new ArrayList<MNirvanaSyncMasterBatch>();
		Integer totalLength = (Integer) sqlMap.queryForObject("cfg_interface_mapping_batch.sumTotalLength",
				CfgInterfaceMapping.MAPPING_CD_FG_MASTER);
		MNirvanaSyncMasterBatch mNirvanaSyncMaster = null;
		String dataArea = "";
		List<String> err = null;
		boolean isNullTransType = false;
		log.info("transform to NirvanaSyncMaster");
		for (SsDataExch3 data : dataList) {
			log.info("@ dataCd = "
					+ data.getDataCd()
					+ " DataNo = "
					+ data.getDataNo()
					+ " EntSysCd = "
					+ data.getEntSysCd());

			mNirvanaSyncMaster = new MNirvanaSyncMasterBatch();
			mNirvanaSyncMaster.setSyncDate(transDate);
			err = new ArrayList<String>();
			mNirvanaSyncMaster.setDataCd(data.getDataCd());
			mNirvanaSyncMaster.setDataNo(data.getDataNo());
			if (0 == data.getDataLength().compareTo(totalLength)) {
				dataArea = data.getDataArea().trim();

				// Trans.Type
				mNirvanaSyncMaster.setTransType(splitData(mappingList.get(0), dataArea));
				isNullTransType = StringUtils.isEmpty(mNirvanaSyncMaster.getTransType());
				if (isNullTransType) {
					err.add(mappingList.get(0).getFieldName() + ERROR_MANDATORY);
				} else if (MNirvanaSyncMasterBatch.TRANS_TYPE_DELETE.equals(mNirvanaSyncMaster.getTransType())) {
					err.add(mappingList.get(0).getFieldName()
							+ "("
							+ mNirvanaSyncMaster.getTransType()
							+ ")"
							+ ERROR_PROHIBITED);
				} else if (!(MNirvanaSyncMasterBatch.TRANS_TYPE_INSERT.equals(mNirvanaSyncMaster.getTransType()) || MNirvanaSyncMasterBatch.TRANS_TYPE_UPDATE.equals(mNirvanaSyncMaster.getTransType()))) {
					err.add(mappingList.get(0).getFieldName()
							+ "("
							+ mNirvanaSyncMaster.getTransType()
							+ ")"
							+ ERROR_NOT_APPLICABLE);
				}
				// ItemID
				mNirvanaSyncMaster.setFgNo(splitData(mappingList.get(1), dataArea));
				if (StringUtils.isEmpty(mNirvanaSyncMaster.getFgNo())) {
					err.add(mappingList.get(1).getFieldName() + ERROR_MANDATORY);
				} else if (!isNullTransType
						&& MNirvanaSyncMasterBatch.TRANS_TYPE_INSERT.endsWith(mNirvanaSyncMaster.getTransType())
						&& isDuplicateFgNo(mNirvanaSyncMaster.getFgNo())) {
					err.add(mappingList.get(1).getFieldName()
							+ "("
							+ mNirvanaSyncMaster.getFgNo()
							+ ")"
							+ ERROR_DUPLICATE);
				}
				// Item Name
				mNirvanaSyncMaster.setFgName(splitData(mappingList.get(2), dataArea));
				if (StringUtils.isEmpty(mNirvanaSyncMaster.getFgName())) {
					err.add(mappingList.get(2).getFieldName() + ERROR_MANDATORY);
				}
				// UOMID
				mNirvanaSyncMaster.setUomId(splitData(mappingList.get(5), dataArea));
				if (StringUtils.isEmpty(mNirvanaSyncMaster.getUomId())) {
//					err.add(mappingList.get(5).getFieldName() + ERROR_MANDATORY);
				} else if (isNotExistUom(mNirvanaSyncMaster.getUomId())) {
					err.add(mappingList.get(5).getFieldName()
							+ "("
							+ mNirvanaSyncMaster.getUomId()
							+ ")"
							+ ERROR_NOT_EXIST);
				}
				// Weight
				String weightData = splitData(mappingList.get(12), dataArea);
				try {
					if (StringUtils.isNotEmpty(weightData)) {
						Double weight = Double.valueOf(weightData).doubleValue();
						if (null == weight) {
//							err.add(mappingList.get(12).getFieldName() + ERROR_MANDATORY);
						} else if (isNumberFormatWrong(weight.toString(), mappingList.get(12).getFieldDecimalLength())) {
							err.add(mappingList.get(12).getFieldName() + ERROR_WRONG_FORMAT);
						} else {
							mNirvanaSyncMaster.setWeight(weight);
						}
					} else {
//						err.add(mappingList.get(12).getFieldName() + ERROR_MANDATORY);
					}
				} catch (NumberFormatException nfe) {
					err.add(mappingList.get(12).getFieldName() + "(" + weightData + ")" + ERROR_NOT_NUMBER_FORMAT);
				}
				// Price Currency
				mNirvanaSyncMaster.setCurrency(splitData(mappingList.get(14), dataArea));
				if (StringUtils.isEmpty(mNirvanaSyncMaster.getCurrency())) {
//					err.add(mappingList.get(14).getFieldName() + ERROR_MANDATORY);
				} else if (isNotExistCurrency(mNirvanaSyncMaster.getCurrency())) {
					err.add(mappingList.get(14).getFieldName()
							+ "("
							+ mNirvanaSyncMaster.getCurrency()
							+ ")"
							+ ERROR_NOT_EXIST);
				}
				// Exclusive VAT Price 1
				String priceData = splitData(mappingList.get(16), dataArea);
				try {
					if (StringUtils.isNotEmpty(priceData)) {
						Double price = Double.valueOf(priceData).doubleValue();
						if (null == price) {
//							err.add(mappingList.get(16).getFieldName() + ERROR_MANDATORY);
						} else if (isNumberFormatWrong(price.toString(), mappingList.get(16).getFieldDecimalLength())) {
							err.add(mappingList.get(16).getFieldName() + ERROR_WRONG_FORMAT);
						} else {
							mNirvanaSyncMaster.setPrice(price);
						}
					} else {
//						err.add(mappingList.get(16).getFieldName() + ERROR_MANDATORY);
					}
				} catch (NumberFormatException nfe) {
					err.add(mappingList.get(16).getFieldName() + "(" + priceData + ")" + ERROR_NOT_NUMBER_FORMAT);
				}
				// Customer Code
				mNirvanaSyncMaster.setCustomerCode(splitData(mappingList.get(17), dataArea));
				if (StringUtils.isEmpty(mNirvanaSyncMaster.getCustomerCode())) {
//					err.add(mappingList.get(17).getFieldName() + ERROR_MANDATORY);
				}
				// Customer Name
				mNirvanaSyncMaster.setCustomerName(splitData(mappingList.get(18), dataArea));
				if (StringUtils.isEmpty(mNirvanaSyncMaster.getCustomerName())) {
//					err.add(mappingList.get(18).getFieldName() + ERROR_MANDATORY);
				}
				// Active Status
				mNirvanaSyncMaster.setIsEnable(splitData(mappingList.get(19), dataArea));
				if (StringUtils.isEmpty(mNirvanaSyncMaster.getIsEnable())) {
					err.add(mappingList.get(19).getFieldName() + ERROR_MANDATORY);
				} else if (isWrongFormatEnable(mNirvanaSyncMaster.getIsEnable())) {
					err.add(mappingList.get(19).getFieldName()
							+ "("
							+ mNirvanaSyncMaster.getIsEnable()
							+ ")"
							+ ERROR_NOT_APPLICABLE);
				}
			} else {
				err.add(ERROR_TOTAL_LENGTH_INCORRECT);
			}

			// Case not has error message set sync status = N If has error sync status = E and set error description.
			if (0 == err.size()) {
				mNirvanaSyncMaster.setSyncStatus(MNirvanaSyncMasterBatch.SYNC_STATUS_NEW);
			} else {
				mNirvanaSyncMaster.setSyncStatus(MNirvanaSyncMasterBatch.SYNC_STATUS_ERROR);
				mNirvanaSyncMaster.setErrDesc(StringUtils.join(err.toArray(), ","));
			}

			mNirvanaSyncMasterList.add(mNirvanaSyncMaster);
		}

		return mNirvanaSyncMasterList;
	}

	/**
	 * Split data according configuration mapping.
	 * 
	 * @param cfgInterfaceMapping - mapping
	 * @param data - data
	 * @return value of string
	 */
	private String splitData(CfgInterfaceMapping cfgInterfaceMapping, String data) {
		String value = "";
		if (null != cfgInterfaceMapping && StringUtils.isNotEmpty(data)) {
			StringBuffer strData = new StringBuffer(data);
			Integer endIndex = cfgInterfaceMapping.getFieldStartPosition() + cfgInterfaceMapping.getFieldTotalLength();
			value = strData.substring(cfgInterfaceMapping.getFieldStartPosition(), endIndex).trim().toString();
		}
		return value;
	}

	/**
	 * Checking FG duplicate key in master.
	 * 
	 * @param fgNo - key to check
	 * @return data is duplicate return true If not duplicate return false.
	 * @throws SQLException - occur when has error
	 */
	private boolean isDuplicateFgNo(String fgNo) throws SQLException {
		Integer isDuplicate = (Integer) sqlMap.queryForObject("m_fg_batch.isDuplicateFgNo", fgNo);
		return null != isDuplicate;
	}

	/**
	 * Checking UOM not exist in master.
	 * 
	 * @param uomId - key to check
	 * @return data is not exist return true If exist return false.
	 * @throws SQLException - occur when has error
	 */
	private boolean isNotExistUom(String uomId) throws SQLException {
		Integer isExist = (Integer) sqlMap.queryForObject("m_uom_batch.isExist", uomId);
		return null == isExist;
	}

	/**
	 * Check number format.
	 * 
	 * @param value - value to check
	 * @param digit - decimal digit
	 * @return true when value is not match format if match format return false.
	 */
	private boolean isNumberFormatWrong(String value, Integer digit) {
		String format = "^[-]?\\d{0,22}+(\\.)?+(\\d{0," + digit + "})$";
		return !value.matches(format);
	}

	/**
	 * Checking currency exist in master.
	 * 
	 * @param currencyId - key to check
	 * @return data is not exist return true If exist return false.
	 * @throws SQLException - occur when has error
	 */
	private boolean isNotExistCurrency(String currencyId) throws SQLException {
		Integer isExist = (Integer) sqlMap.queryForObject("m_currency_batch.isExist", currencyId);
		return null == isExist;
	}

	/**
	 * Checking is enable format(Y & N).
	 * 
	 * @param value - value for check
	 * @return true when value is wrong format if match format return false.
	 */
	private boolean isWrongFormatEnable(String value) {
		return !(MNirvanaSyncMasterBatch.IS_ENABLE_Y.equals(value) || MNirvanaSyncMasterBatch.IS_ENABLE_N.equals(value));
	}

	@Override
	public void preProcessing() throws Exception {
		// MERGE m_batch_control
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 1);
		param.put("runby", getExecutedBy());
		sqlMap.insert("m_batch_control.insertMBatchControl", param);
	}

	@Override
	public void postException() throws Exception {
		// update m_batch_controlin case fail
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("batchcode", BATCH_CODE);
		param.put("batchname", BATCH_NAME);
		param.put("batchstatus", 2);
		param.put("runby", getExecutedBy());
		sqlMap.update("m_batch_control.updateMBatchControl", param);
	}

}
