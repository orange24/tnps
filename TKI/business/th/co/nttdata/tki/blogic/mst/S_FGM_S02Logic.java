package th.co.nttdata.tki.blogic.mst;

import java.util.List;

import th.co.nttdata.tki.bean.FgMaster;
import th.co.nttdata.tki.bean.MCustomer;

public interface S_FGM_S02Logic {
	public  MCustomer getAllCustomer();
	public  FgMaster  searchCustomerFgMappingByCustomerId(FgMaster  fgmaster);
	public  void   SaveCustomerFgMaster(List<FgMaster>  cutomerFgMappingList);
	public List<FgMaster> getFgList();
}
