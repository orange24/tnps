package th.co.nttdata.tki.blogic.fng;

import th.co.nttdata.tki.bean.TFGStock;

public interface FNG_S04Logic {
	
	public TFGStock searchStockAdjust(TFGStock tfgStock);
	
	public void adjustStock(TFGStock tfgStock);
	
	public TFGStock exportFNG_R04(TFGStock tfgStock);

	public TFGStock importFNG_R04(TFGStock tfgStock);
}
