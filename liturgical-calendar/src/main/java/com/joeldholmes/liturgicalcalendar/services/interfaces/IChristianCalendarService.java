package com.joeldholmes.liturgicalcalendar.services.interfaces;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.joeldholmes.liturgycommon.enums.ChristianSpecialDatesEnum;
import com.joeldholmes.liturgycommon.exceptions.ServiceException;

public interface IChristianCalendarService {

	DateTime getEaster(int year);
	
	Map<String, Date> getHolidays(DateTime date) throws ServiceException;
	
	/**
	 * Returns a set of holiday names for specific dates. If nothing happens on this date get specific Sunday.
	 * 
	 * @param date
	 * @return Set of holidays on specific date, if date has no holidays it will go to the start of the week. 
	 * @throws ServiceException
	 */
	Set<ChristianSpecialDatesEnum> getHolidayEnums(DateTime date) throws ServiceException;
	
	Set<String> getHoliday(DateTime date) throws ServiceException;

}
