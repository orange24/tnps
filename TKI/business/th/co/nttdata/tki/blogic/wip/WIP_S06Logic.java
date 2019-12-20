package th.co.nttdata.tki.blogic.wip;

import java.util.List;
import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.TWipCheckStock;

public interface WIP_S06Logic {

	public TWipCheckStock search(TWipCheckStock TWipCheckStock);
	
	public List<MPart> getWIP( Integer customerId, Integer partId ) ;

	public MCustomer getMCustomer(Integer customerId);

	public MPart getMPart(Integer partId);
}