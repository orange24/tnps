package th.co.nttdata.tki.dao.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MCalendar;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MCalendarDao;

@Repository
@SuppressWarnings("unchecked")
public class MCalendarDaoImpl extends AbstractBaseDao implements MCalendarDao {

	@Override
	public MCalendar[] getCalendarArray( Integer month, Integer year ) {
		Calendar strCal = new GregorianCalendar(year, month, 1);
		Calendar endCal = new GregorianCalendar(year, month + 1, 1);
		endCal.add(Calendar.DAY_OF_YEAR, -1);

		// <!-- Set to Initial. -->
		MCalendar MCalendar = new MCalendar();
		MCalendar.setCalendarDateFr(strCal.getTime());
		MCalendar.setCalendarDateTo(endCal.getTime());
		return getCalendarArray( MCalendar );
	}

	@Override
	public MCalendar[] getCalendarArray( MCalendar MCalendar ) {
		boolean isEmptyMCal = MCalendar == null;
		boolean isEmptyDtFr = MCalendar.getCalendarDateFr() == null;
		boolean isEmptyDtTo = MCalendar.getCalendarDateTo() == null;
		if( isEmptyMCal || isEmptyDtFr || isEmptyDtTo )
			return new MCalendar[] {};

		List<MCalendar> calendarList = getCalendarList( MCalendar );
		return calendarList == null || calendarList.size() < 1 ? new MCalendar[] {} : calendarList.toArray( new MCalendar[calendarList.size()] ); 
	}

	@Override
	public List<MCalendar> getCalendarList( Integer month, Integer year ) {
		Calendar strCal = new GregorianCalendar(year, month, 1);
		Calendar endCal = new GregorianCalendar(year, month + 1, 1);
		endCal.add(Calendar.DAY_OF_YEAR, -1);

		// <!-- Set to Initial. -->
		MCalendar MCalendar = new MCalendar();
		MCalendar.setCalendarDateFr(strCal.getTime());
		MCalendar.setCalendarDateTo(endCal.getTime());
		return getCalendarList( MCalendar );
	}

	@Override
	public List<MCalendar> getCalendarList( MCalendar MCalendar ) {
		return (List<MCalendar>) queryForList("m_calendar.queryCalendar", MCalendar);
	}
}