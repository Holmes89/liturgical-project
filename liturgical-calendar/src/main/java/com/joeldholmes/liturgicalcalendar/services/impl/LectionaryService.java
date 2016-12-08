package com.joeldholmes.liturgicalcalendar.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.joeldholmes.liturgicalcalendar.exceptions.FactoryException;
import com.joeldholmes.liturgicalcalendar.factories.ChristianCalendarFactory;
import com.joeldholmes.liturgicalcalendar.factories.LectionaryFactory;
import com.joeldholmes.liturgicalcalendar.services.interfaces.ILectionaryService;
import com.joeldholmes.liturgycommon.dto.LiturgyDTO;
import com.joeldholmes.liturgycommon.enums.ChristianSpecialDatesEnum;
import com.joeldholmes.liturgycommon.enums.LitanyEventsEnum;
import com.joeldholmes.liturgycommon.exceptions.ServiceException;
import com.joeldholmes.liturgycommon.util.ErrorCodes;;

public class LectionaryService implements ILectionaryService {


	ChristianCalendarFactory calFactory = ChristianCalendarFactory.getInstance();
	

	ChristianCalendarService calService = new ChristianCalendarService();
	
	LectionaryFactory lectFactory = LectionaryFactory.getInstance();
	
	public LectionaryService(){}
	
	
	public Set<String> getLectionaryVerses(DateTime now) throws ServiceException{
		try {
			DateTime advent = calFactory.getStartOfAdvent(now);
			int year = advent.getYear();
			String yearCode = Character.toString(calFactory.getLiturgicalYear(year));
			Map<ChristianSpecialDatesEnum, DateTime> eventMap = calFactory.getEventMap(year);
			Map<ChristianSpecialDatesEnum, LitanyEventsEnum> litCalMap = getLitCalMap(eventMap.keySet());
			Map<LitanyEventsEnum, Set<String>> lectionary = lectFactory.getLitYear(yearCode);
			Set<String> verses = new HashSet<String>();
			for(ChristianSpecialDatesEnum event: calService.getHolidayEnums(now)){
				LitanyEventsEnum litanyEventsEnum = litCalMap.get(event);
				verses.addAll(lectionary.get(litanyEventsEnum));
			}
			return verses;
		} catch (FactoryException e) {
			throw new ServiceException("Factory Exception occured", e);
		}
	}
	
	
	public Map<String, LiturgyDTO> getLectionary(DateTime now) throws ServiceException{
		if(now == null){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "DateTime cannot be null");
		}
		try {
			Map<String, LiturgyDTO> result = new HashMap<String, LiturgyDTO>();
			DateTime advent = calFactory.getStartOfAdvent(now);
			int year = advent.getYear();
			String yearCode = Character.toString(calFactory.getLiturgicalYear(year));
			Map<ChristianSpecialDatesEnum, DateTime> eventMap = calFactory.getEventMap(year);
			Map<ChristianSpecialDatesEnum, LitanyEventsEnum> litCalMap = getLitCalMap(eventMap.keySet());
			Map<LitanyEventsEnum, Set<String>> lectionary = lectFactory.getLitYear(yearCode);
			for(ChristianSpecialDatesEnum event: calService.getHolidayEnums(now)){
				LiturgyDTO dto = new LiturgyDTO();
				dto.date = now.toDate();
				LitanyEventsEnum litanyEventsEnum = litCalMap.get(event);
				dto.liturgicalDate=event.getDisplayName();
				dto.litany = lectionary.get(litanyEventsEnum);
				dto.liturgicalYear=yearCode.charAt(0);
				dto.year=now.getYear();
				result.put(event.getDisplayName(), dto);
			}
			return result;
		} catch (FactoryException e) {
			throw new ServiceException("Factory Exception occured", e);
		}
	}
	
	private Map<ChristianSpecialDatesEnum, LitanyEventsEnum> getLitCalMap(Set<ChristianSpecialDatesEnum> dates){
		Map<ChristianSpecialDatesEnum, LitanyEventsEnum> litCalMap = new LinkedHashMap<ChristianSpecialDatesEnum, LitanyEventsEnum>();
		
		litCalMap.put(ChristianSpecialDatesEnum.FIRST_SUNDAY_ADVENT, LitanyEventsEnum.FIRST_SUNDAY_OF_ADVENT);
		litCalMap.put(ChristianSpecialDatesEnum.SECOND_SUNDAY_ADVENT, LitanyEventsEnum.SECOND_SUNDAY_OF_ADVENT);
		litCalMap.put(ChristianSpecialDatesEnum.THIRD_SUNDAY_ADVENT, LitanyEventsEnum.THIRD_SUNDAY_OF_ADVENT);
		litCalMap.put(ChristianSpecialDatesEnum.FOURTH_SUNDAY_ADVENT, LitanyEventsEnum.FOURTH_SUNDAY_OF_ADVENT);
		litCalMap.put(ChristianSpecialDatesEnum.CHRISTMAS_EVE, LitanyEventsEnum.NATIVITY_OF_THE_LORD__PROPER_I);
		litCalMap.put(ChristianSpecialDatesEnum.CHRISTMAS_DAY, LitanyEventsEnum.NATIVITY_OF_THE_LORD__PROPER_II);
//		litCalMap.put(ChristianSpecialDatesEnum.CHRISTMAS_DAY, LitanyEventsEnum.NATIVITY_OF_THE_LORD__PROPER_III);
		litCalMap.put(ChristianSpecialDatesEnum.FIRST_SUNDAY_CHRISTMAS, LitanyEventsEnum.FIRST_SUNDAY_AFTER_CHRISTMAS_DAY);
		litCalMap.put(ChristianSpecialDatesEnum.HOLY_NAME_JESUS, LitanyEventsEnum.HOLY_NAME_OF_JESUS);
		litCalMap.put(ChristianSpecialDatesEnum.NEW_YEARS_DAY, LitanyEventsEnum.NEW_YEARS_DAY);
		litCalMap.put(ChristianSpecialDatesEnum.SECOND_SUNDAY_CHRISTMAS, LitanyEventsEnum.SECOND_SUNDAY_AFTER_CHRISTMAS_DAY);
		
		litCalMap.put(ChristianSpecialDatesEnum.EPIPHANY, LitanyEventsEnum.EPIPHANY_OF_THE_LORD);
		
		litCalMap.put(ChristianSpecialDatesEnum.FIRST_SUNDAY_EPIPHANY, LitanyEventsEnum.BAPTISM_OF_THE_LORD);
		litCalMap.put(ChristianSpecialDatesEnum.SECOND_SUNDAY_EPIPHANY, LitanyEventsEnum.SECOND_SUNDAY_AFTER_THE_EPIPHANY);
		litCalMap.put(ChristianSpecialDatesEnum.THIRD_SUNDAY_EPIPHANY, LitanyEventsEnum.THIRD_SUNDAY_AFTER_THE_EPIPHANY);
		litCalMap.put(ChristianSpecialDatesEnum.FOURTH_SUNDAY_EPIPHANY, LitanyEventsEnum.FOURTH_SUNDAY_AFTER_THE_EPIPHANY);
		litCalMap.put(ChristianSpecialDatesEnum.FIFTH_SUNDAY_EPIPHANY, LitanyEventsEnum.FIFTH_SUNDAY_AFTER_THE_EPIPHANY);
		litCalMap.put(ChristianSpecialDatesEnum.SIXTH_SUNDAY_EPIPHANY, LitanyEventsEnum.SIXTH_SUNDAY_AFTER_THE_EPIPHANY);
		litCalMap.put(ChristianSpecialDatesEnum.SEVENTH_SUNDAY_EPIPHANY, LitanyEventsEnum.SEVENTH_SUNDAY_AFTER_THE_EPIPHANY);
		litCalMap.put(ChristianSpecialDatesEnum.EIGHTH_SUNDAY_EPIPHANY, LitanyEventsEnum.EIGHTH_SUNDAY_AFTER_THE_EPIPHANY);
		litCalMap.put(ChristianSpecialDatesEnum.NINTH_SUNDAY_EPIPHANY, LitanyEventsEnum.NINTH_SUNDAY_AFTER_THE_EPIPHANY);
		
		litCalMap.put(ChristianSpecialDatesEnum.PRESENTATION_JESUS, LitanyEventsEnum.PRESENTATION_OF_THE_LORD);
		litCalMap.put(ChristianSpecialDatesEnum.TRANSFIGURATION_SUNDAY, LitanyEventsEnum.TRANSFIGURATION_SUNDAY);
		litCalMap.put(ChristianSpecialDatesEnum.ASH_WEDNESDAY, LitanyEventsEnum.ASH_WEDNESDAY);
		litCalMap.put(ChristianSpecialDatesEnum.FIRST_SUNDAY_LENT, LitanyEventsEnum.FIRST_SUNDAY_IN_LENT);
		litCalMap.put(ChristianSpecialDatesEnum.SECOND_SUNDAY_LENT, LitanyEventsEnum.SECOND_SUNDAY_IN_LENT);
		litCalMap.put(ChristianSpecialDatesEnum.THIRD_SUNDAY_LENT, LitanyEventsEnum.THIRD_SUNDAY_IN_LENT);
		litCalMap.put(ChristianSpecialDatesEnum.FOURTH_SUNDAY_LENT, LitanyEventsEnum.FOURTH_SUNDAY_IN_LENT);
		litCalMap.put(ChristianSpecialDatesEnum.FIFTH_SUNDAY_LENT, LitanyEventsEnum.FIFTH_SUNDAY_IN_LENT);
		litCalMap.put(ChristianSpecialDatesEnum.PALM_SUNDAY, LitanyEventsEnum.LITURGY_OF_THE_PALMS);
		litCalMap.put(ChristianSpecialDatesEnum.MONDAY_HOLY_WEEK, LitanyEventsEnum.MONDAY_OF_HOLY_WEEK);
		litCalMap.put(ChristianSpecialDatesEnum.TUESDAY_HOLY_WEEK, LitanyEventsEnum.TUESDAY_OF_HOLY_WEEK);
		litCalMap.put(ChristianSpecialDatesEnum.WEDNESDAY_HOLY_WEEK, LitanyEventsEnum.WEDNESDAY_OF_HOLY_WEEK);
		litCalMap.put(ChristianSpecialDatesEnum.MAUNDY_THURSDAY, LitanyEventsEnum.MAUNDY_THURSDAY);
		litCalMap.put(ChristianSpecialDatesEnum.GOOD_FRIDAY, LitanyEventsEnum.GOOD_FRIDAY);
		litCalMap.put(ChristianSpecialDatesEnum.HOLY_SATURDAY, LitanyEventsEnum.HOLY_SATURDAY);
		litCalMap.put(ChristianSpecialDatesEnum.EASTER_VIGIL, LitanyEventsEnum.EASTER_VIGIL);
//		litCalMap.put(ChristianSpecialDatesEnum.EASTER_VIGIL, LitanyEventsEnum.EASTER_EVENING);
		litCalMap.put(ChristianSpecialDatesEnum.EASTER, LitanyEventsEnum.RESURRECTION_OF_THE_LORD);
		litCalMap.put(ChristianSpecialDatesEnum.SECOND_SUNDAY_EASTER, LitanyEventsEnum.SECOND_SUNDAY_OF_EASTER);
		litCalMap.put(ChristianSpecialDatesEnum.ANNUNCIATION_LORD, LitanyEventsEnum.ANNUNCIATION_OF_THE_LORD);
		litCalMap.put(ChristianSpecialDatesEnum.THIRD_SUNDAY_EASTER, LitanyEventsEnum.THIRD_SUNDAY_OF_EASTER);
		litCalMap.put(ChristianSpecialDatesEnum.FOURTH_SUNDAY_EASTER, LitanyEventsEnum.FOURTH_SUNDAY_OF_EASTER);
		litCalMap.put(ChristianSpecialDatesEnum.FIFTH_SUNDAY_EASTER, LitanyEventsEnum.FIFTH_SUNDAY_OF_EASTER);
		litCalMap.put(ChristianSpecialDatesEnum.SIXTH_SUNDAY_EASTER, LitanyEventsEnum.SIXTH_SUNDAY_OF_EASTER);
		litCalMap.put(ChristianSpecialDatesEnum.ASCENSION, LitanyEventsEnum.ASCENSION_OF_THE_LORD);
		litCalMap.put(ChristianSpecialDatesEnum.ASCENSION_SUNDAY, LitanyEventsEnum.SEVENTH_SUNDAY_AFTER_THE_EPIPHANY);
		litCalMap.put(ChristianSpecialDatesEnum.PENTECOST, LitanyEventsEnum.DAY_OF_PENTECOST);
		litCalMap.put(ChristianSpecialDatesEnum.VISITATION_MARY, LitanyEventsEnum.VISITATION_OF_MARY_TO_ELIZABETH);
		litCalMap.put(ChristianSpecialDatesEnum.HOLY_CROSS, LitanyEventsEnum.HOLY_CROSS);
		litCalMap.put(ChristianSpecialDatesEnum.TRINITY_SUNDAY, LitanyEventsEnum.TRINITY_SUNDAY);
		
		List<ChristianSpecialDatesEnum> pentecostSundaysArray = Arrays.asList(new ChristianSpecialDatesEnum[]{
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
		
		List<ChristianSpecialDatesEnum> pentecostSundays = new ArrayList<ChristianSpecialDatesEnum>(pentecostSundaysArray);
		List<ChristianSpecialDatesEnum> tempSundays = new ArrayList<ChristianSpecialDatesEnum>(pentecostSundaysArray);
		Collections.reverse(tempSundays);
		
		List<LitanyEventsEnum> propersArray = Arrays.asList(new LitanyEventsEnum[]{
				LitanyEventsEnum.PROPER_3,
				LitanyEventsEnum.PROPER_4,
				LitanyEventsEnum.PROPER_5,
				LitanyEventsEnum.PROPER_6,
				LitanyEventsEnum.PROPER_7,
				LitanyEventsEnum.PROPER_8,
				LitanyEventsEnum.PROPER_9,
				LitanyEventsEnum.PROPER_10,
				LitanyEventsEnum.PROPER_11,
				LitanyEventsEnum.PROPER_12,
				LitanyEventsEnum.PROPER_13,
				LitanyEventsEnum.PROPER_14,
				LitanyEventsEnum.PROPER_15,
				LitanyEventsEnum.PROPER_16,
				LitanyEventsEnum.PROPER_17,
				LitanyEventsEnum.PROPER_18,
				LitanyEventsEnum.PROPER_19,
				LitanyEventsEnum.PROPER_20,
				LitanyEventsEnum.PROPER_21,
				LitanyEventsEnum.PROPER_22,
				LitanyEventsEnum.PROPER_23,
				LitanyEventsEnum.PROPER_24,
				LitanyEventsEnum.PROPER_25,
				LitanyEventsEnum.PROPER_26,
				LitanyEventsEnum.PROPER_27,
				LitanyEventsEnum.PROPER_28
		});
		
		List<LitanyEventsEnum> propers = new ArrayList<LitanyEventsEnum>(propersArray);
		
		for(ChristianSpecialDatesEnum cd: tempSundays){
			if(dates.contains(cd)){
				break;
			}
			propers.remove(0);
			pentecostSundays.remove(cd);
		}
				
		for(int x=0; x<pentecostSundays.size(); x++){
			litCalMap.put(pentecostSundays.get(x), propers.get(x));
		}
		
		litCalMap.put(ChristianSpecialDatesEnum.CHRIST_IS_KING, LitanyEventsEnum.CHRIST_IS_KING);
		litCalMap.put(ChristianSpecialDatesEnum.ALL_SAINTS_DAY, LitanyEventsEnum.ALL_SAINTS_DAY);
		litCalMap.put(ChristianSpecialDatesEnum.CANADIAN_THANKSGIVING, LitanyEventsEnum.CANADIAN_THANKSGIVING_DAY);
		litCalMap.put(ChristianSpecialDatesEnum.USA_THANKSGIVING, LitanyEventsEnum.THANKSGIVING_DAY_USA);
		return litCalMap;
	}
}
