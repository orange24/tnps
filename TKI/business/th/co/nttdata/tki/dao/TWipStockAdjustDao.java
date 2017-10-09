package th.co.nttdata.tki.dao;

import th.co.nttdata.tki.bean.TWipStockAdjust;

public interface TWipStockAdjustDao {

	public int insert(TWipStockAdjust wipStockAdjust);

	public TWipStockAdjust query(TWipStockAdjust wipStockAdjust);

	public TWipStockAdjust queryHis(TWipStockAdjust wipStockAdjust);

	public TWipStockAdjust queryWIP_R03(TWipStockAdjust wipStockAdjust);

	public TWipStockAdjust queryWIP_R04(TWipStockAdjust wipStockAdjust);

	public TWipStockAdjust queryEnable(TWipStockAdjust tWipStockAdjust,
			String suffix);
	
	public int adjustWipStock(TWipStockAdjust wipStockAdjust);
}
