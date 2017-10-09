package th.co.nttdata.tki.dao.impl;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.TNirExportTotal;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TNirExportTotalDao;

@Repository
public class TNirExportTotalDaoImpl extends AbstractBaseDao implements TNirExportTotalDao {

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public Integer getExportTotalByMonth(Integer month) {
		return (Integer) queryForObject("t_nir_export_total.getExportTotalByMonth", month);
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAllExportTotalData() {
		delete("t_nir_export_total.deleteAllExportTotalData");
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void insertNewMonth(TNirExportTotal tNirExportTotal) {
		insert("t_nir_export_total.insertNewMonth", tNirExportTotal);
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void updateExportTotal(TNirExportTotal tNirExportTotal) {
		update("t_nir_export_total.updateExportTotal", tNirExportTotal);
	}

}
