package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.TFG;
import th.co.nttdata.tki.bean.TFGDetail;

public interface TFGDetailDao {

	public List<TFGDetail> insert(TFG TFG);

	public TFG query(TFG TFG);

	public int insertRow(TFG TFG);

	public TFG selectFNG_R01(TFG TFG);

	public TFG selectFNG_R03(TFG TFG);

	public List<TFGDetail> queryReportType();

	public Boolean checkLotNo(TFGDetail workOrder);

	public Boolean checkLotNotExist(TFGDetail t);

	public void insertFgDetail(TFG tfg);
	
	public Boolean checkOutNotIn(TFGDetail workOrder);
}
