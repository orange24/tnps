package th.co.nttdata.tki.dao;

import java.util.List;
import java.util.Map;

import th.co.nttdata.tki.bean.FgMaster;
import th.co.nttdata.tki.bean.filter.FgMasterFilter;

public interface MFgMasterDao {
	public List<FgMaster> getFgMastertSearchList(FgMaster fgMaster);

	public List<FgMaster> getUomList();

	public List<FgMaster> getClassifyBusiness();

	public List<FgMaster> getPlace();

	public List<FgMaster> getSubbusiness();

	public List<FgMasterFilter> getFgMasterList(FgMasterFilter fgCustomer);

	public void insertFgMaster(FgMaster fgMaster);

	public void updateFgMaster(FgMaster fgMaster);

	public void deleteFgMaster(FgMaster fgMaster);

	public FgMaster searchCustomerFgMappingByCustomerId(FgMaster fgmaster);

	public void insertCustomerFgMaster(FgMaster fgMaster);

	public void deleteCustomerFgMaster(FgMaster fgMaster);

	public List<FgMaster> getFgList();

	public Map<String, Integer> getAllFgMap();

	public List<FgMaster> getCurrencyList();
}
