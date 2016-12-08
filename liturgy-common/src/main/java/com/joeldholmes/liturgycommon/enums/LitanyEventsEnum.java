package com.joeldholmes.liturgycommon.enums;

import java.util.HashMap;
import java.util.Map;

public enum LitanyEventsEnum {
	ALL_SAINTS_DAY("All Saints Day"),
	ANNUNCIATION_OF_THE_LORD("Annunciation of the Lord"),
	ASCENSION_OF_THE_LORD("Ascension of the Lord"),
	ASH_WEDNESDAY("Ash Wednesday"),
	BAPTISM_OF_THE_LORD("Baptism of the Lord"),
	CANADIAN_THANKSGIVING_DAY("Canadian Thanksgiving Day"),
	CHRIST_IS_KING("Christ is King"),
	DAY_OF_PENTECOST("Day of Pentecost"),
	EASTER_EVENING("Easter Evening"),
	EASTER_VIGIL("Easter Vigil"),
	EIGHTH_SUNDAY_AFTER_THE_EPIPHANY("Eighth Sunday after the Epiphany"),
	EPIPHANY_OF_THE_LORD("Epiphany of the Lord"),
	FIFTH_SUNDAY_AFTER_THE_EPIPHANY("Fifth Sunday after the Epiphany"),
	FIFTH_SUNDAY_IN_LENT("Fifth Sunday in Lent"),
	FIFTH_SUNDAY_OF_EASTER("Fifth Sunday of Easter"),
	FIRST_SUNDAY_AFTER_CHRISTMAS_DAY("First Sunday after Christmas Day"),
	FIRST_SUNDAY_IN_LENT("First Sunday in Lent"),
	FIRST_SUNDAY_OF_ADVENT("First Sunday of Advent"),
	FOURTH_SUNDAY_AFTER_THE_EPIPHANY("Fourth Sunday after the Epiphany"),
	FOURTH_SUNDAY_IN_LENT("Fourth Sunday in Lent"),
	FOURTH_SUNDAY_OF_ADVENT("Fourth Sunday of Advent"),
	FOURTH_SUNDAY_OF_EASTER("Fourth Sunday of Easter"),
	GOOD_FRIDAY("Good Friday"),
	HOLY_CROSS("Holy Cross"),
	HOLY_NAME_OF_JESUS("Holy Name of Jesus"),
	HOLY_SATURDAY("Holy Saturday"),
	LITURGY_OF_THE_PALMS("Liturgy of the Palms"),
	LITURGY_OF_THE_PASSION("Liturgy of the Passion"),
	MAUNDY_THURSDAY("Maundy Thursday"),
	MONDAY_OF_HOLY_WEEK("Monday of Holy Week"),
	NATIVITY_OF_THE_LORD__PROPER_I("Nativity of the Lord - Proper I"),
	NATIVITY_OF_THE_LORD__PROPER_II("Nativity of the Lord - Proper II"),
	NATIVITY_OF_THE_LORD__PROPER_III("Nativity of the Lord - Proper III"),
	NEW_YEARS_DAY("New Year's Day"),
	NINTH_SUNDAY_AFTER_THE_EPIPHANY("Ninth Sunday after the Epiphany"),
	PRESENTATION_OF_THE_LORD("Presentation of the Lord"),
	PROPER_10("Proper 10"),
	PROPER_11("Proper 11"),
	PROPER_12("Proper 12"),
	PROPER_13("Proper 13"),
	PROPER_14("Proper 14"),
	PROPER_15("Proper 15"),
	PROPER_16("Proper 16"),
	PROPER_17("Proper 17"),
	PROPER_18("Proper 18"),
	PROPER_19("Proper 19"),
	PROPER_20("Proper 20"),
	PROPER_21("Proper 21"),
	PROPER_22("Proper 22"),
	PROPER_23("Proper 23"),
	PROPER_24("Proper 24"),
	PROPER_25("Proper 25"),
	PROPER_26("Proper 26"),
	PROPER_27("Proper 27"),
	PROPER_28("Proper 28"),
	PROPER_3("Proper 3"),
	PROPER_4("Proper 4"),
	PROPER_5("Proper 5"),
	PROPER_6("Proper 6"),
	PROPER_7("Proper 7"),
	PROPER_8("Proper 8"),
	PROPER_9("Proper 9"),
	RESURRECTION_OF_THE_LORD("Resurrection of the Lord"),
	SECOND_SUNDAY_AFTER_CHRISTMAS_DAY("Second Sunday after Christmas Day"),
	SECOND_SUNDAY_AFTER_THE_EPIPHANY("Second Sunday after the Epiphany"),
	SECOND_SUNDAY_IN_LENT("Second Sunday in Lent"),
	SECOND_SUNDAY_OF_ADVENT("Second Sunday of Advent"),
	SECOND_SUNDAY_OF_EASTER("Second Sunday of Easter"),
	SEVENTH_SUNDAY_AFTER_THE_EPIPHANY("Seventh Sunday after the Epiphany"),
	SEVENTH_SUNDAY_OF_EASTER("Seventh Sunday of Easter"),
	SIXTH_SUNDAY_AFTER_THE_EPIPHANY("Sixth Sunday after the Epiphany"),
	SIXTH_SUNDAY_OF_EASTER("Sixth Sunday of Easter"),
	THANKSGIVING_DAY_USA("Thanksgiving Day USA"),
	THIRD_SUNDAY_AFTER_THE_EPIPHANY("Third Sunday after the Epiphany"),
	THIRD_SUNDAY_IN_LENT("Third Sunday in Lent"),
	THIRD_SUNDAY_OF_ADVENT("Third Sunday of Advent"),
	THIRD_SUNDAY_OF_EASTER("Third Sunday of Easter"),
	TRANSFIGURATION_SUNDAY("Transfiguration Sunday"),
	TRINITY_SUNDAY("Trinity Sunday"),
	TUESDAY_OF_HOLY_WEEK("Tuesday of Holy Week"),
	VISITATION_OF_MARY_TO_ELIZABETH("Visitation of Mary to Elizabeth"),
	WEDNESDAY_OF_HOLY_WEEK("Wednesday of Holy Week");
	
	private static final Map<String, LitanyEventsEnum> LOOKUP = new HashMap<String, LitanyEventsEnum>();

	static{
		for(LitanyEventsEnum e: LitanyEventsEnum.values()){
			LOOKUP.put(e.getDisplayName(), e);
		}
	}
	
	private String displayName;
	
	LitanyEventsEnum(String displayName){
		this.displayName=displayName;
	}
	
	public String getDisplayName(){
		return this.displayName;  
	}
	
	
	public static LitanyEventsEnum fromDisplayName(String displayName){
		return LOOKUP.get(displayName);
	}
	
}
