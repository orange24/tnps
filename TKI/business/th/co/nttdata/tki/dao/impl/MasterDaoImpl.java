package th.co.nttdata.tki.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MReportType;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MasterDao;

@Repository
public class MasterDaoImpl extends AbstractBaseDao implements MasterDao {

	@Override
	public List<MReportType> selectReportTypeList( MReportType MReportType ) {

		List<MReportType> reportTypeList = queryForList("master.m_reporttype", MReportType); 
		return reportTypeList;
	}
}