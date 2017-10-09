package th.co.nttdata.tki.blogic.wip;

import java.util.List;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.TWip;

public interface WIP_S02Logic {

	public TWip search(TWip TWip);
	
	public List<MPart> getWIP( Integer customerId, Integer partId ) ;

	public MCustomer getMCustomer(Integer customerId);

	public MPart getMPart(Integer partId);
}