package th.co.nttdata.tki.dao;

import th.co.nttdata.tki.bean.TWip;

public interface TWipStockDao {

	public TWip getWipStockList();

	public TWip getWipStockList(TWip TWip);
	
	public TWip selectMRDC_S19(TWip tWip);
}