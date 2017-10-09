package th.co.nttdata.tki.blogic.mst;

import th.co.nttdata.tki.bean.MPart;

public interface ITM_S01Logic {

	public MPart query(MPart part);
	
	public void delete(MPart part);
	
	public void deleteCustomer(MPart part);

}