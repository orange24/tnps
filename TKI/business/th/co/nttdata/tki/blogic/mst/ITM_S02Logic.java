package th.co.nttdata.tki.blogic.mst;

import java.util.List;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MPart;

public interface ITM_S02Logic {

	public MPart query(MPart part);
	public MPart save(MPart part);
	public List<MCustomer> getTpicCustomerList();

}