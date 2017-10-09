package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.MCalendar;

public interface MCalendarDao {

	public MCalendar[] getCalendarArray(Integer month, Integer year);

	public MCalendar[] getCalendarArray(MCalendar MCalendar);

	public List<MCalendar> getCalendarList(Integer month, Integer year);

	public List<MCalendar> getCalendarList(MCalendar MCalendar);
}