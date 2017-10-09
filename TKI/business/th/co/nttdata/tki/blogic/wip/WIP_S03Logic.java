package th.co.nttdata.tki.blogic.wip;

import th.co.nttdata.tki.bean.TWipStockAdjust;

public interface WIP_S03Logic {
	public TWipStockAdjust search(TWipStockAdjust TWipStockAdjust);

	public void save(TWipStockAdjust TWipStockAdjust);
	
	public TWipStockAdjust exportWIP_R03(TWipStockAdjust TWipStockAdjust);

	public TWipStockAdjust importWIP_R03(TWipStockAdjust TWipStockAdjust);
}
