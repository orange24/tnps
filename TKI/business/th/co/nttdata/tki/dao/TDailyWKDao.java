package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.bean.TDCPlan;
import th.co.nttdata.tki.bean.TDailyWK;

public interface TDailyWKDao {

	public void delete(TDailyWK TDailyWK);

	public void insert(TDailyWK TDailyWK);

	public TDailyWK update(TDailyWK TDailyWK);

	public TDailyWK query(TDailyWK TDailyWK);

	public TDailyWK queryList(TDailyWK TDailyWK);

	public List<MWorkOrder> queryWorkOrderNoList(MWorkOrder MWorkOrder);
	
	public TDailyWK selectDAL_R03(TDailyWK TDailyWK);
	
	public Integer countDAL_R03();
	
	public TDailyWK selectMRDC_R10(TDailyWK tDailyWK);
	
	public Integer countMRDC_R10();
	
	public TDailyWK selectMRDC_R12(TDailyWK tDailyWK);
	
	public Integer countMRDC_R12();
	
	public TDailyWK selectMRDC_R13(TDailyWK tDailyWK);
	
	public Integer countMRDC_R13();
	
	public TDailyWK selectMRDC_R22(TDailyWK tDailyWK);
	
	public Integer countMRDC_R22();
	
	public TDailyWK selectMRDC_R02(TDailyWK tDailyWK);
	
	public Integer countMRDC_R02();
	
	public TDailyWK getCategoryList(TDailyWK TDailyWK);
	
	Integer checkWorkOrder(TDCPlan object);
}