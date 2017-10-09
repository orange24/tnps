package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.MReportType;

public interface MasterDao {

	List<MReportType> selectReportTypeList(MReportType MReportType);

}