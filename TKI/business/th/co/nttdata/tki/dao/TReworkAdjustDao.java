package th.co.nttdata.tki.dao;

import th.co.nttdata.tki.bean.TReworkAdjust;

public interface TReworkAdjustDao {

	public abstract int insert(TReworkAdjust reworkAdjust);

	public abstract TReworkAdjust query(TReworkAdjust reworkAdjust);

	public abstract TReworkAdjust queryHis(TReworkAdjust reworkAdjust);

}