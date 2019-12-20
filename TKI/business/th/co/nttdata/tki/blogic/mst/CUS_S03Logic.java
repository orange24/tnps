package th.co.nttdata.tki.blogic.mst;

import java.util.List;

import th.co.nttdata.tki.bean.CustomerLine;
import th.co.nttdata.tki.bean.MCustomer;

public interface CUS_S03Logic {
	public  MCustomer getAllCustomer();
	public  CustomerLine  searchCustomerLineByCustomerId(CustomerLine  customerLine);
	public  void   saveCustomerLine(List<CustomerLine>  cutomerFgMappingList);
	public List<CustomerLine> getCustomerLineList();
}
