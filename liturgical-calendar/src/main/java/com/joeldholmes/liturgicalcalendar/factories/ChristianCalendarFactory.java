package com.joeldholmes.liturgicalcalendar.factories;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.DateTimeConstants;

import com.joeldholmes.liturgicalcalendar.exceptions.FactoryException;
import com.joeldholmes.liturgycommon.enums.ChristianSpecialDatesEnum;
import com.joeldholmes.liturgycommon.util.ErrorCodes;

public class ChristianCalendarFactory {

	private Map<Integer, Map<ChristianSpecialDatesEnum, DateTime>> eventDateMap = new HashMap<Integer, Map<ChristianSpecialDatesEnum, DateTime>>();
	private Map<Integer, Map<DateTime, Set<ChristianSpecialDatesEnum>>> dateEventMap = new HashMap<Integer, Map<DateTime, Set<ChristianSpecialDatesEnum>>>();
	
	private static ChristianCalendarFactory factory = new ChristianCalendarFactory();
	
	private ChristianCalendarFactory(){
		DateTime initDate = new DateTime(2010, 12, 25, 0, 0);
		for(int x=0; x<30; x++){
			createDateMap(initDate.plusYears(x));
		}
	}
	
	 public static ChristianCalendarFactory getInstance( ) {
	      return factory;
	   }
	
	public Map<ChristianSpecialDatesEnum, DateTime> getEventMap(int year){
		if(!eventDateMap.containsKey(year)){
			DateTime initDate = new DateTime(year, 12, 25, 0, 0);
			createDateMap(initDate);
		}
		return eventDateMap.get(year);
	}
	
	public Map<ChristianSpecialDatesEnum, DateTime> getEventMap(DateTime date) throws FactoryException{
		if(date==null){
			throw new FactoryException("Date cannot be null", ErrorCodes.NULL_INPUT);
		}
		int year = getStartOfAdvent(date).getYear();
		return getEventMap(year);
	}
	
	public Map<DateTime, Set<ChristianSpecialDatesEnum>> getDateMap(int year){
		if(!dateEventMap.containsKey(year)){
			DateTime initDate = new DateTime(year, 12, 25, 0, 0);
			createDateMap(initDate);
		}
		return dateEventMap.get(year);
	}

	public Map<DateTime, Set<ChristianSpecialDatesEnum>> getDateMap(DateTime date) throws FactoryException{
		if(date==null){
			throw new FactoryException("Date cannot be null", ErrorCodes.NULL_INPUT);
		}
		int year = getStartOfAdvent(date).getYear();
		return getDateMap(year);
	}

	public Set<ChristianSpecialDatesEnum> getEventsByDate(DateTime date) throws FactoryException{
		if(date==null){
			throw new FactoryException("Date cannot be null", ErrorCodes.NULL_INPUT);
		}
		return getDateMap(date.getYear()).get(date);
	}
	
	private void createDateMap(DateTime date){
		LinkedHashMap<ChristianSpecialDatesEnum, DateTime> eventMap = new LinkedHashMap<ChristianSpecialDatesEnum, DateTime>();
		date = date.withTimeAtStartOfDay();
		
		//Advent
		DateTime adventStart = getStartOfAdvent(date);
		DateTime followingAdventStart = getStartOfAdvent(date.plusYears(1));
		DateTime easterDate = getEasterDate(adventStart.getYear()+1);
		DateTime ashWednesday = easterDate.minusWeeks(6).withDayOfWeek(DateTimeConstants.WEDNESDAY);
	
		eventMap.put(ChristianSpecialDatesEnum.FIRST_SUNDAY_ADVENT, adventStart);
		eventMap.put(ChristianSpecialDatesEnum.SECOND_SUNDAY_ADVENT, adventStart.plusWeeks(1));
		eventMap.put(ChristianSpecialDatesEnum.THIRD_SUNDAY_ADVENT, adventStart.plusWeeks(2));
		eventMap.put(ChristianSpecialDatesEnum.FOURTH_SUNDAY_ADVENT, adventStart.plusWeeks(3));
		
		DateTime christmasEve = new DateTime(adventStart.getYear(), 12, 24, 0, 0);
		eventMap.put(ChristianSpecialDatesEnum.CHRISTMAS_EVE, christmasEve);
		
		DateTime christmasDate = new DateTime(adventStart.getYear(), 12, 25, 0, 0);
		eventMap.put(ChristianSpecialDatesEnum.CHRISTMAS_DAY, christmasDate);
		
		DateTime newYearsDate = new DateTime(adventStart.getYear()+1, 1, 1, 0, 0);
		eventMap.put(ChristianSpecialDatesEnum.NEW_YEARS_DAY, newYearsDate);
		eventMap.put(ChristianSpecialDatesEnum.HOLY_NAME_JESUS, newYearsDate);
		
		DateTime firstSundayChristmas;
		if(christmasDate.dayOfWeek().get()==DateTimeConstants.SUNDAY){
			firstSundayChristmas= christmasDate.plusWeeks(1);
		}
		else{
			firstSundayChristmas= christmasDate.withDayOfWeek(DateTimeConstants.SUNDAY);
		}
		eventMap.put(ChristianSpecialDatesEnum.FIRST_SUNDAY_CHRISTMAS, firstSundayChristmas);
		
		
		DateTime epiphany = new DateTime(adventStart.getYear()+1, 1, 6, 0, 0);
		if(epiphany.isAfter(firstSundayChristmas.plusWeeks(1))){
			eventMap.put(ChristianSpecialDatesEnum.SECOND_SUNDAY_CHRISTMAS, firstSundayChristmas.plusWeeks(1));
		}
		
		eventMap.put(ChristianSpecialDatesEnum.EPIPHANY, epiphany);
		
		DateTime epiphanyFirstSunday;
		if(epiphany.dayOfWeek().get()==DateTimeConstants.SUNDAY){
			epiphanyFirstSunday = epiphany.plusWeeks(1);
		}
		else{
			epiphanyFirstSunday = epiphany.withDayOfWeek(DateTimeConstants.SUNDAY);
		}
		
		eventMap.put(ChristianSpecialDatesEnum.FIRST_SUNDAY_EPIPHANY, epiphanyFirstSunday);
		List<ChristianSpecialDatesEnum> epiphanySundays = Arrays.asList(new ChristianSpecialDatesEnum[]{
				ChristianSpecialDatesEnum.SECOND_SUNDAY_EPIPHANY,
				ChristianSpecialDatesEnum.THIRD_SUNDAY_EPIPHANY,
				ChristianSpecialDatesEnum.FOURTH_SUNDAY_EPIPHANY,
				ChristianSpecialDatesEnum.FIFTH_SUNDAY_EPIPHANY,
				ChristianSpecialDatesEnum.SIXTH_SUNDAY_EPIPHANY,
				ChristianSpecialDatesEnum.SEVENTH_SUNDAY_EPIPHANY,
				ChristianSpecialDatesEnum.EIGHTH_SUNDAY_EPIPHANY,
				ChristianSpecialDatesEnum.NINTH_SUNDAY_EPIPHANY
		});
		
	
		DateTime transfigurationSunday = ashWednesday.minusDays(3);
		eventMap.put(ChristianSpecialDatesEnum.TRANSFIGURATION_SUNDAY, transfigurationSunday);
		
		DateTime tempDate = epiphanyFirstSunday.plusWeeks(1);
		int weekCount =0;
		while(tempDate.isBefore(transfigurationSunday)){
			eventMap.put(epiphanySundays.get(weekCount), tempDate);
			tempDate = tempDate.plusWeeks(1);
			weekCount++;
		}
	
		eventMap.put(ChristianSpecialDatesEnum.ASH_WEDNESDAY, ashWednesday);
		DateTime firstSundayLent = ashWednesday.withDayOfWeek(DateTimeConstants.SUNDAY);
		eventMap.put(ChristianSpecialDatesEnum.FIRST_SUNDAY_LENT, firstSundayLent);
		eventMap.put(ChristianSpecialDatesEnum.SECOND_SUNDAY_LENT, firstSundayLent.plusWeeks(1));
		eventMap.put(ChristianSpecialDatesEnum.THIRD_SUNDAY_LENT, firstSundayLent.plusWeeks(2));
		eventMap.put(ChristianSpecialDatesEnum.FOURTH_SUNDAY_LENT, firstSundayLent.plusWeeks(3));
		eventMap.put(ChristianSpecialDatesEnum.FIFTH_SUNDAY_LENT, firstSundayLent.plusWeeks(4));
		eventMap.put(ChristianSpecialDatesEnum.PALM_SUNDAY, firstSundayLent.plusWeeks(5));
		
		eventMap.put(ChristianSpecialDatesEnum.ANNUNCIATION_LORD, new DateTime(adventStart.getYear()+1, 3, 25, 0, 0));
		eventMap.put(ChristianSpecialDatesEnum.HOLY_CROSS, new DateTime(adventStart.getYear()+1, 3, 25, 0, 0));
		
		eventMap.put(ChristianSpecialDatesEnum.MONDAY_HOLY_WEEK, easterDate.withDayOfWeek(DateTimeConstants.MONDAY));
		eventMap.put(ChristianSpecialDatesEnum.TUESDAY_HOLY_WEEK, easterDate.withDayOfWeek(DateTimeConstants.TUESDAY));
		eventMap.put(ChristianSpecialDatesEnum.WEDNESDAY_HOLY_WEEK, easterDate.withDayOfWeek(DateTimeConstants.WEDNESDAY));
		eventMap.put(ChristianSpecialDatesEnum.MAUNDY_THURSDAY, easterDate.withDayOfWeek(DateTimeConstants.THURSDAY));
		eventMap.put(ChristianSpecialDatesEnum.GOOD_FRIDAY, easterDate.withDayOfWeek(DateTimeConstants.FRIDAY));
		eventMap.put(ChristianSpecialDatesEnum.HOLY_SATURDAY, easterDate.withDayOfWeek(DateTimeConstants.SATURDAY));
		eventMap.put(ChristianSpecialDatesEnum.EASTER_VIGIL, easterDate.withDayOfWeek(DateTimeConstants.SATURDAY));
		eventMap.put(ChristianSpecialDatesEnum.EASTER, easterDate);
		eventMap.put(ChristianSpecialDatesEnum.SECOND_SUNDAY_EASTER, easterDate.plusWeeks(1));
		eventMap.put(ChristianSpecialDatesEnum.THIRD_SUNDAY_EASTER, easterDate.plusWeeks(2));
		eventMap.put(ChristianSpecialDatesEnum.FOURTH_SUNDAY_EASTER, easterDate.plusWeeks(3));
		eventMap.put(ChristianSpecialDatesEnum.FIFTH_SUNDAY_EASTER, easterDate.plusWeeks(4));
		eventMap.put(ChristianSpecialDatesEnum.SIXTH_SUNDAY_EASTER, easterDate.plusWeeks(5));
		eventMap.put(ChristianSpecialDatesEnum.ASCENSION, easterDate.plusDays(39));
		eventMap.put(ChristianSpecialDatesEnum.ASCENSION_SUNDAY, easterDate.plusWeeks(6));
		eventMap.put(ChristianSpecialDatesEnum.PENTECOST, easterDate.plusWeeks(7));
		eventMap.put(ChristianSpecialDatesEnum.TRINITY_SUNDAY, easterDate.plusWeeks(8));
		
		eventMap.put(ChristianSpecialDatesEnum.CANADIAN_THANKSGIVING, nthWeekdayOfMonth(DateTimeConstants.MONDAY, 10, adventStart.getYear()+1, 2));
		eventMap.put(ChristianSpecialDatesEnum.USA_THANKSGIVING, nthWeekdayOfMonth(DateTimeConstants.THURSDAY, 11, adventStart.getYear()+1, 4));
		eventMap.put(ChristianSpecialDatesEnum.ALL_SAINTS_DAY, new DateTime(adventStart.getYear()+1, 11, 1, 0, 0));
		
		List<ChristianSpecialDatesEnum> pentecostSundays = Arrays.asList(new ChristianSpecialDatesEnum[]{
				ChristianSpecialDatesEnum.SECOND_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.THIRD_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.FOURTH_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.FIFTH_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.SIXTH_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.SEVENTH_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.EIGHTH_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.NINTH_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.TENTH_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.ELEVENTH_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.TWELFTH_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.THIRTEENTH_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.FOURTHEENTH_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.FIFTHEENTH_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.SIXTHEENTH_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.SEVENTEENTH_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.EIGHTEENTH_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.NINTEENTH_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.TWENTIETH_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.TWENTY_FIRST_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.TWENTY_SECOND_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.TWENTY_THIRD_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.TWENTY_FOURTH_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.TWENTY_FIFTH_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.TWENTY_SIXTH_SUNDAY_PENTECOST,
				ChristianSpecialDatesEnum.TWENTY_SEVENTH_SUNDAY_PENTECOST
		});
		tempDate = easterDate.plusWeeks(9);
		weekCount =0;
		while(tempDate.isBefore(followingAdventStart.minusWeeks(1))){
			eventMap.put(pentecostSundays.get(weekCount), tempDate);
			tempDate = tempDate.plusWeeks(1);
			weekCount++;
		}
		
		eventMap.put(ChristianSpecialDatesEnum.CHRIST_IS_KING, tempDate);
		eventDateMap.put(adventStart.getYear(), eventMap);
		
		
		dateEventMap.put(adventStart.getYear(), generateDateEventMap(eventMap));
	}
	
	private Map<DateTime, Set<ChristianSpecialDatesEnum>> generateDateEventMap(LinkedHashMap<ChristianSpecialDatesEnum, DateTime> eventMap) {
		TreeMap<DateTime, Set<ChristianSpecialDatesEnum>> dateMap = new TreeMap<DateTime, Set<ChristianSpecialDatesEnum>>(DateTimeComparator.getInstance());
		for(ChristianSpecialDatesEnum event : eventMap.keySet()){
			DateTime eventDate = eventMap.get(event);
			if(!dateMap.containsKey(eventDate)){
				dateMap.put(eventDate, new HashSet<ChristianSpecialDatesEnum>());
			}
			dateMap.get(eventDate).add(event);
		}
		return dateMap;
	}

	public DateTime getEasterDate(int year) {
		int a = year % 19;
		int b = year / 100;
		int c = year % 100;
		int d = b / 4;
		int e = b % 4;
		int f = (b + 8) / 25;
		int g = (b - f + 1) / 3;
		int h = (19 * a + b - d - g + 15) % 30;
		int i = c / 4;
		int k = c % 4;
		int l = (32 + 2 * e + 2 * i - h - k) % 7;
		int m = (a + 11 * h + 22 * l) / 451;
		int n = (h + l - 7 * m + 114) / 31;
		int p = (h + l - 7 * m + 114) % 31;
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.clear();
		calendar.set(year, n-1, p + 1);
		return new DateTime(calendar.getTime());
	}
	
	public char getLiturgicalYear(int yearStart){
		switch(yearStart%3){
			case 0:
				return 'a';
			case 1:
				return 'b';
			default:
				return 'c';
		}
	}
	
	public DateTime getStartOfAdvent(DateTime now){
		int year = now.getYear();
		DateTime christmasThisYear = new DateTime(year, 12, 25, 0, 0);
		DateTime firstSundayInAdvent = christmasThisYear.minusWeeks(4).withDayOfWeek(DateTimeConstants.SUNDAY);
		if(firstSundayInAdvent.isBefore(now)){
			return firstSundayInAdvent;
		}
		christmasThisYear = new DateTime(year-1, 12, 25, 0, 0);
		return christmasThisYear.minusWeeks(4).withDayOfWeek(DateTimeConstants.SUNDAY);
	}
	
	private DateTime nthWeekdayOfMonth(int dayOfWeek, int month, int year, int n) {
		DateTime start = new DateTime(year, month, 1, 0 ,0);
		DateTime date = start.withDayOfWeek(dayOfWeek);
	    return (date.isBefore(start)) ? date.plusWeeks(n) : date.plusWeeks(n - 1);
	}
}
