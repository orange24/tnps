package th.co.nttdata.tki.blogic.mst;
import java.util.List;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.PartRoutingMaster;
import th.co.nttdata.tki.bean.ProcessMaster;
import th.co.nttdata.tki.bean.filter.FgMasterFilter;
import th.co.nttdata.tki.bean.filter.PartRoutingMasterFilter;

public interface S_PRT_S03Logic {
	public MCustomer getAllCustomer();
	public PartRoutingMaster  searchFgPartWipList(PartRoutingMaster  prMaster);
	public PartRoutingMaster  searchProcessList(PartRoutingMaster  prMaster);
	public PartRoutingMaster  selectProcess();
	public void saveProcessList(List<PartRoutingMaster>  prMasterList);
	public PartRoutingMaster  selectListForCopyPartRouting(PartRoutingMaster  prMaster);
	public void   deletePartDestination  (PartRoutingMaster  prMaster);
	public void   insertPartDestination  (PartRoutingMaster  prMaster);
	public List<PartRoutingMasterFilter> selectExportList(PartRoutingMasterFilter partRoutingMasterFilter);

}
   