package com.joeldholmes.liturgicalcalendar.services.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import com.joeldholmes.liturgicalcalendar.exceptions.FactoryException;
import com.joeldholmes.liturgicalcalendar.factories.ChristianCalendarFactory;
import com.joeldholmes.liturgicalcalendar.services.interfaces.IChristianCalendarService;
import com.joeldholmes.liturgycommon.enums.ChristianSpecialDatesEnum;
import com.joeldholmes.liturgycommon.exceptions.ServiceException;
import com.joeldholmes.liturgycommon.util.ErrorCodes;

public class ChristianCalendarService implements IChristianCalendarService {
	
	ChristianCalendarFactory calFactory= ChristianCalendarFactory.getInstance();

	public ChristianCalendarService(){}
	
	public DateTime getEaster(int year){
		return calFactory.getEasterDate(year);
	}

	
	public Map<String, Date> getHolidays(DateTime date) throws ServiceException {
		if(date ==null){
			throw new ServiceException("Date cannot be null", ErrorCodes.NULL_INPUT);
		}
		Map<String, Date> holidayMap = new HashMap<String, Date>();
		try{
			Map<ChristianSpecialDatesEnum, DateTime> eventMap = calFactory.getEventMap(date);
			for(ChristianSpecialDatesEnum key: eventMap.keySet()){
				holidayMap.put(key.getDisplayName(), eventMap.get(key).toDate());
			}
		}catch(FactoryException e){
			throw new ServiceException("Factory Error", e);
		}
		return holidayMap;
	}

	
	public Set<ChristianSpecialDatesEnum> getHolidayEnums(DateTime date) throws ServiceException {
		if(date ==null){
			throw new ServiceException("Date cannot be null", ErrorCodes.NULL_INPUT);
		}
		date = cleanDate(date);
		try{
			Map<DateTime, Set<ChristianSpecialDatesEnum>> dateMap = calFactory.getDateMap(date);
			Set<ChristianSpecialDatesEnum> holidayEnums = new HashSet<ChristianSpecialDatesEnum>();
			if(dateMap.containsKey(date)){
				holidayEnums = dateMap.get(date);
			}
			return holidayEnums;
		}catch(FactoryException e){
			throw new ServiceException("Factory Error", e);
		}
	}
	
	
	public Set<String> getHoliday(DateTime date) throws ServiceException {
		if(date ==null){
			throw new ServiceException("Date cannot be null", ErrorCodes.NULL_INPUT);
		}
		Set<String> holidays = new HashSet<String>();
		for(ChristianSpecialDatesEnum h: getHolidayEnums(date)){
			holidays.add(h.getDisplayName());
		}
		return holidays;
	}

	private DateTime cleanDate(DateTime date){
		return date.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
	}

}
