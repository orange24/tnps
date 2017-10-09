package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.TDeliveryPlanDate;
import th.co.nttdata.tki.bean.TFG;
import th.co.nttdata.tki.bean.TFGStock;

public interface TFGStockDao {

	public void delete(TFGStock TFGStock);

	public TFG getFGStockList();

	public TFG getFGStockList(TFG TFG);

	public List<TFGStock> query(TFG TFG);

	public List<TFGStock> query(TFG TFG, MPart MPart);

	public List<MPart> queryPartId(TFG TFG);

	public List<MPart> selectFNG_R02(TFG TFG);

	public TFGStock queryLastDayOfMonth(TFG TFG, MPart MPart);

	public TFGStock queryStockAdjust(TFGStock tfgStock);

	public void adjustStock(TFGStock tfgStock);

	public TFGStock queryFGAdjustHistory(TFGStock tfgStock);

	public TFGStock selectMRDC_R16(TFGStock tfgStock);

	public Integer countMRDC_R16();

	public MWip queryWip(MWip MWip);

	public TFGStock queryFNG_R04(TFGStock tfgStock);

	public int adjustFgStock(TFGStock tfgStock);

	public List<TDeliveryPlanDate> queryDeliveryPlans(TFG tfg);
}