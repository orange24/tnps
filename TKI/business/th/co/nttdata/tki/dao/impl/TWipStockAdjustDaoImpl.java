package th.co.nttdata.tki.dao.impl;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.TWipStockAdjust;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TWipStockAdjustDao;

@Repository
@SuppressWarnings("unchecked")
public class TWipStockAdjustDaoImpl extends AbstractBaseDao implements
		TWipStockAdjustDao {

	@Override
	public int insert(TWipStockAdjust wipStockAdjust) {
		return super.update("t_wip_stockadjust.insert", wipStockAdjust);
	}

	@Override
	public TWipStockAdjust query(TWipStockAdjust wipStockAdjust) {
		return query(wipStockAdjust, "");
	}

	@Override
	public TWipStockAdjust queryHis(TWipStockAdjust wipStockAdjust) {
		return query(wipStockAdjust, "His");
	}

	@Override
	public TWipStockAdjust queryWIP_R03(TWipStockAdjust wipStockAdjust) {
		wipStockAdjust.setAdjustList(queryForList("t_wip_stockadjust.query",
				wipStockAdjust));

		return wipStockAdjust;
	}

	@Override
	public TWipStockAdjust queryWIP_R04(TWipStockAdjust wipStockAdjust) {
		wipStockAdjust.setAdjustList(queryForList("t_wip_stockadjust.queryHis",
				wipStockAdjust));
		return wipStockAdjust;
	}

	private TWipStockAdjust query(TWipStockAdjust wipStockAdjust, String suffix) {
		wipStockAdjust.setAdjustList(queryForList("t_wip_stockadjust.query"
				+ suffix, wipStockAdjust, getSkipResult(wipStockAdjust),
				wipStockAdjust.getPageCount()));
		calPageTotal("t_wip_stockadjust.count" + suffix, wipStockAdjust);
		return wipStockAdjust;
	}

	@Override
	public TWipStockAdjust queryEnable(TWipStockAdjust wipStockAdjust,
			String suffix) {
		wipStockAdjust.setAdjustList(queryForList(
				"t_wip_stockadjust.queryEnablePart" + suffix, wipStockAdjust,
				getSkipResult(wipStockAdjust), wipStockAdjust.getPageCount()));
		calPageTotal("t_wip_stockadjust.countEnablePart" + suffix,
				wipStockAdjust);
		return wipStockAdjust;
	}

	@Override
	public int adjustWipStock(TWipStockAdjust wipStockAdjust) {
		return update("t_wip_stockadjust.adjustWipStock", wipStockAdjust);
	}

}