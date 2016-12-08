package com.joeldholmes.liturgycommon.dto;

import java.util.Date;
import java.util.Set;

import com.joeldholmes.liturgycommon.enums.LitanyEventsEnum;

public class LiturgyDTO {

	public Date date;
	
	public int year;
	
	public LitanyEventsEnum liturgicalDate;
	
	public char liturgicalYear;
	
	public Set<String> litany;
}
