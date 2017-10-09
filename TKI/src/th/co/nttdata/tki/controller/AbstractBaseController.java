package th.co.nttdata.tki.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import th.co.nttdata.tki.bean.AbstractBaseBean;
import th.co.nttdata.tki.bean.Message;

public abstract class AbstractBaseController {

	@Autowired
	protected Properties settings;
	
	private static final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS", Locale.US);
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    @ModelAttribute("now")
    public Date now() {
    	return new GregorianCalendar(Locale.US).getTime();
    }

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormatter, true){
			
            private static final String dateRegexp = "(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/][0-9]{4}";
            private static final String timeRegexp = "([01][0-9]|2[0-4])[:][0-5][0-9][:][0-5][0-9][.][0-9]{3}";

            @Override
            public void setAsText(String text) throws IllegalArgumentException {

                  try {
                         // <!-- Validating: -->
                         if( text == null || (text=text.trim()).length() < 1 )
                                setValue( null );
                         else if( text.matches( dateRegexp +" "+ timeRegexp ) )
                                setValue( dateTimeFormatter.parse(text) );
                         else if( text.matches( dateRegexp ) )
                                setValue( dateFormatter.parse(text) );
                         else
                                setValue( new Date() );
                  } catch ( Exception e ) {
                         e.printStackTrace();
                  }
            }
		});
	}

	@ExceptionHandler(Exception.class)
	public String handleException(Exception ex, HttpServletRequest request) {
		ex.printStackTrace();
		return "ERR/redirectPage";
	}

	private static Map<Integer,String> monthMap;
	protected final Map<Integer,String> getMonthMap() {
		if( monthMap != null )
			return monthMap;

		// <!-- Start Initial. -->
		monthMap = new LinkedHashMap<Integer,String>();
		monthMap.put(0, "January");
		monthMap.put(1, "February");
		monthMap.put(2, "March");
		monthMap.put(3, "April");
		monthMap.put(4, "May");
		monthMap.put(5, "June");
		monthMap.put(6, "July");
		monthMap.put(7, "August");
		monthMap.put(8, "September");
		monthMap.put(9, "October");
		monthMap.put(10, "November");
		monthMap.put(11, "December");

		return monthMap;
	}

	private static Map<Integer,String> yearMap;
	protected final Map<Integer,String> getYearMap() {
		if( yearMap != null )
			return yearMap;

		// <!-- Start Initial. -->
		Calendar currentCal = new GregorianCalendar(Locale.US);
		Integer str = new Integer(settings.getProperty("CMM.startYear", "2010"));
		Integer end = currentCal.get(Calendar.YEAR) + 1;

		yearMap = new LinkedHashMap<Integer,String>();
		while( str <= end ) {
			yearMap.put(str, str.toString());
			str += 1;
		}

		return yearMap;
	}

	private static Map<Integer,String> wipTypeDataMap = new LinkedHashMap<Integer,String>();
	protected final Map<Integer,String> getWipTypeMap(Boolean isSelected) {
		return getPropertiesMap(wipTypeDataMap, isSelected, "-- Select WIP Type --", "-- All WIP Types --", 
				"CMM.wipTypes", "1:Die cast;2:Machine(+Worker);3:Worker;");
	}
	
	private Map<Integer,String> getPropertiesMap(
			Map<Integer,String> dataMap, 
			Boolean isSelected, String selectString, String allString,
			String propertyKey, String defaultValue) {	

		Map<Integer,String> map = new LinkedHashMap<Integer,String>();
		if (isSelected != null) {
			map.put(Integer.MIN_VALUE, isSelected ? selectString : allString);
		}
		
		if (dataMap.isEmpty()) {
			// <!-- Start Initial. -->
			String[] reportTypes = settings.getProperty(propertyKey, defaultValue).split(";");

			for( String reportType : reportTypes ) {
				int seperatorIndex = reportType.indexOf(':');
	
				// <!-- Validation: if key is not a number. -->
				if( seperatorIndex < 0 )
					continue;
				String key = reportType.substring(0, seperatorIndex).trim();
				String val = reportType.substring(seperatorIndex + 1).trim();
				if( key.matches("^\\d{1,2}$") )
					dataMap.put(new Integer(key), val);
			}
		}

		map.putAll(dataMap);

		return map;
	}
	
	protected static final void setSystemError(AbstractBaseBean baseBean, Exception e) {
		String[] code = {e.toString()};
		baseBean.getErrors().add(new Message("err.cmm.007", null));
		baseBean.getErrors().add(new Message("err.cmm.015", code));
	}
	
	protected static final void setSaveInfo(AbstractBaseBean baseBean) {
		if (!baseBean.hasErrors())
			baseBean.getInfos().add(new Message("inf.cmm.002", null));
	}
}