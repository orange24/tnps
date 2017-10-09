package th.co.nttdata.tki.blogic.mst;

import th.co.nttdata.tki.bean.MCustomer;

public interface CUS_S02Logic {
	public MCustomer search(MCustomer mCustomer);
	public void save(MCustomer mCustomer);
}
